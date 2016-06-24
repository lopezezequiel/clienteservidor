package com.clienteservidor.preciosclaros.restapi;

import java.util.Collection;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.Locality;
import com.clienteservidor.preciosclaros.restapi.responsestatus.ResourceNotFoundException;
import com.clienteservidor.preciosclaros.service.LocalityService;

@RestController
@RequestMapping("/api_v1/localities")
public class LocalityController extends GenericController<LocalityService> {
	
	@RequestMapping("")
	public Collection<Locality> getCityList(
		@RequestParam(value="offset", required=false) Integer offset,
		@RequestParam(value="limit", required=false) Integer limit,
		@RequestParam(value="q", required=false) String query,
		@RequestParam(value="department", required=false) Integer departmentId){

	   	Collection<Locality> localities = service.findAll(offset, limit, query, departmentId);
        return localities;
	}
	
	@RequestMapping("length")
	public int length(
		@RequestParam(value="q", required=false) String query,
		@RequestParam(value="department", required=false) Integer departmentId){

	   	int length= service.length(query,departmentId);
        return length;
	}

	@RequestMapping("{id}")
	public Locality getCategory(@PathVariable("id") int id) {
		Locality locality = service.findById(id);

        if(locality == null) {
        	throw new ResourceNotFoundException();
        }

        return locality;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable("id") int id){
        Locality locality = service.findById(id);

        if(locality == null) {
        	throw new ResourceNotFoundException();
        }

        service.delete(locality);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Locality persist(@RequestBody Locality locality){
        service.persist(locality);
        return locality;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Locality update(@PathVariable("id") int id, @RequestBody Locality locality){
        service.update(id, locality);
        return locality;
	}

}
