package com.clienteservidor.preciosclaros.restapi;

import java.util.Collection;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.Category;
import com.clienteservidor.preciosclaros.service.CategoryService;

@RestController
@RequestMapping("/api_v1/categories")
public class CategoryController extends GenericController<CategoryService>{

	@RequestMapping("")
	public Collection<Category> findAll(
		@RequestParam(value="parent", required=false) Integer parentId,
		@RequestParam(value="q", required=false) String query,
		@RequestParam(value="offset", required=false) Integer offset,
		@RequestParam(value="limit", required=false) Integer limit) {

        return service.findAll(offset, limit, parentId, query);
	}
	
	@RequestMapping("length")
	public int length(
		@RequestParam(value="parent", required=false) Integer parentId,
		@RequestParam(value="q", required=false) String query){

        return service.length(parentId, query);
	}

	@RequestMapping("{id}")
	public Category findById(@PathVariable("id") int id) {
        Category category = service.findById(id);

        return category;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable("id") int id){
        Category category = service.findById(id);

       	service.delete(category);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public Category persist(@RequestBody Category category){
        service.persist(category);
        return category;
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	public Category update(@RequestBody Category category){
		System.out.println(category.getId());
        service.update(category);
        return category;
	}
	
}
