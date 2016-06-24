package com.clienteservidor.preciosclaros.restapi;

import java.util.Collection;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.Firm;
import com.clienteservidor.preciosclaros.restapi.responsestatus.ResourceNotFoundException;
import com.clienteservidor.preciosclaros.service.FirmService;

@RestController
@RequestMapping("/api_v1/firms")
public class FirmController extends GenericController<FirmService> {
	
	@RequestMapping("")
	public Collection<Firm> getFirmList(
		@RequestParam(value="offset", required=false) Integer offset,
		@RequestParam(value="limit", required=false) Integer limit,
		@RequestParam(value="company", required=false) Integer companyId,
		@RequestParam(value="q", required=false) String query){

	   	Collection<Firm> firm = service.findAll(offset, limit, companyId, query);
        return firm;
	}
	
	@RequestMapping("length")
	public int getFirmLength(
		@RequestParam(value="company", required=false) Integer companyId,
		@RequestParam(value="q", required=false) String query){

	   	int length = service.length(companyId, query);
        return length;
	}

	@RequestMapping("{id}")
	public Firm getCategory(@PathVariable("id") int id) {
		Firm firm = service.findById(id);

        if(firm == null) {
        	throw new ResourceNotFoundException();
        }

        return firm;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable("id") int id){
        Firm firm = service.findById(id);

        if(firm == null) {
        	throw new ResourceNotFoundException();
        }

        service.delete(firm);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Firm persist(@RequestBody Firm firm){
        service.persist(firm);
        return firm;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Firm update(@PathVariable("id") int id, @RequestBody Firm firm){
        service.update(id, firm);
        return firm;
	}

}
