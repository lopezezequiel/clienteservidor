package com.clienteservidor.preciosclaros.restapi;

import java.util.Collection;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.Company;
import com.clienteservidor.preciosclaros.restapi.responsestatus.ResourceNotFoundException;
import com.clienteservidor.preciosclaros.service.CompanyService;

@RestController
@RequestMapping("/api_v1/companies")
public class CompanyController extends GenericController<CompanyService> {
	
	@RequestMapping("")
	public Collection<Company> getCompanyList(
		@RequestParam(value="offset", required=false) Integer offset,
		@RequestParam(value="limit", required=false) Integer limit,
		@RequestParam(value="q", required=false) String query){

	   	Collection<Company> companies = service.findAll(offset, limit, query);
        return companies;
	}
	
	@RequestMapping("length")
	public int length(
		@RequestParam(value="q", required=false) String query){

	   	int length = service.length(query);
        return length;
	}

	@RequestMapping("{id}")
	public Company getCompany(@PathVariable("id") int id) {
		Company company = service.findById(id);

        if(company == null) {
        	throw new ResourceNotFoundException();
        }

        return company;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable("id") int id){
        Company company = service.findById(id);

        if(company == null) {
        	throw new ResourceNotFoundException();
        }

        service.delete(company);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Company persist(@RequestBody Company company){
        service.persist(company);
        return company;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Company update(@PathVariable("id") int id, @RequestBody Company company){
        service.update(id, company);
        return company;
	}

}