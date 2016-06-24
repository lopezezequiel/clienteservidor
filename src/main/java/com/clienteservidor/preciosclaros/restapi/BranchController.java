package com.clienteservidor.preciosclaros.restapi;

import java.util.Collection;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.Branch;
import com.clienteservidor.preciosclaros.restapi.responsestatus.ResourceNotFoundException;
import com.clienteservidor.preciosclaros.service.BranchService;

@RestController
@RequestMapping("/api_v1/branches")
public class BranchController extends GenericController<BranchService> {
	@RequestMapping("")
	public Collection<Branch> findAll(
			@RequestParam(value="offset", required=false) Integer offset,
			@RequestParam(value="limit", required=false) Integer limit,
			@RequestParam(value="firm", required=false) Integer firmId,
			@RequestParam(value="locality", required=false) Integer localityId){

	   	Collection<Branch> branchs = service.findAll(offset, limit, firmId, localityId);
        return branchs;
	}
	
	@RequestMapping("length")
	public int length(
			@RequestParam(value="firm", required=false) Integer firmId,
			@RequestParam(value="locality", required=false) Integer localityId){

	   	int count = service.length(firmId, localityId);
        return count;
	}
	
	@RequestMapping("{id}")
	public Branch findById(@PathVariable("id") int id) {
        Branch branch = service.findById(id);

        if(branch == null) {
        	throw new ResourceNotFoundException();
        }

        return branch;
	}
	


	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable("id") int id){
        Branch branch = service.findById(id);

        if(branch == null) {
        	throw new ResourceNotFoundException();
        }

        service.delete(branch);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public Branch persist(@RequestBody Branch branch){
        service.persist(branch);
        return branch;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Branch update(@PathVariable("id") int id, @RequestBody Branch branch){
        service.update(id, branch);
        return branch;
	}
}
