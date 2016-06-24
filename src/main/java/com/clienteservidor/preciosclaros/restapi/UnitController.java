package com.clienteservidor.preciosclaros.restapi;

import java.util.Collection;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.Unit;
import com.clienteservidor.preciosclaros.restapi.responsestatus.ResourceNotFoundException;
import com.clienteservidor.preciosclaros.service.UnitService;

@RestController
@RequestMapping("/api_v1/units")
public class UnitController extends GenericController<UnitService>{

	@RequestMapping("")
	public Collection<Unit> findAll(
		@RequestParam(value="offset", required=false) Integer offset,
		@RequestParam(value="limit", required=false) Integer limit,
		@RequestParam(value="q", required=false) String query) {

        return service.findAll(offset, limit, query);
	}

	@RequestMapping("{id}")
	public Unit findById(@PathVariable("id") int id) {
        Unit unit = service.findById(id);

        if(unit == null) {
        	throw new ResourceNotFoundException();
        }

        return unit;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable("id") int id){
        Unit unit = service.findById(id);

        if(unit == null) {
        	throw new ResourceNotFoundException();
        }

        service.delete(unit);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public Unit persist(@RequestBody Unit unit){
        service.persist(unit);
        return unit;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Unit update(@PathVariable("id") int id, @RequestBody Unit unit){
        service.update(id, unit);
        return unit;
	}
	
}
