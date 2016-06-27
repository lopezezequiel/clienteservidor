package com.clienteservidor.preciosclaros.restapi;

import java.util.Collection;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.Province;
import com.clienteservidor.preciosclaros.restapi.responsestatus.ResourceNotFoundException;
import com.clienteservidor.preciosclaros.service.ProvinceService;

@RestController
@RequestMapping("/api_v1/provinces")
public class ProvinceController extends GenericController<ProvinceService>{

	@RequestMapping("")
	public Collection<Province> findAll(
		@RequestParam(value="offset", required=false) Integer offset,
		@RequestParam(value="limit", required=false) Integer limit,
		@RequestParam(value="q", required=false) String query) {

        return service.findAll(offset, limit, query);
	}
	
	@RequestMapping("length")
	public int length(
		@RequestParam(value="q", required=false) String query) {

        return service.length(query);
	}

	@RequestMapping("{id}")
	public Province findById(@PathVariable("id") int id) {
        Province province = service.findById(id);

        if(province == null) {
        	throw new ResourceNotFoundException();
        }

        return province;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable("id") int id){
        Province province = service.findById(id);

        if(province == null) {
        	throw new ResourceNotFoundException();
        }

        service.delete(province);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public Province persist(@RequestBody Province province){
        service.persist(province);
        return province;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Province update(@PathVariable("id") int id, @RequestBody Province category){
        service.update(id, category);
        return category;
	}
	
}
