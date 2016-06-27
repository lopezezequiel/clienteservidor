package com.clienteservidor.preciosclaros.restapi;

import java.util.Collection;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.Product;
import com.clienteservidor.preciosclaros.service.ProductService;

@RestController
@RequestMapping("/api_v1/products")
public class ProductController extends GenericController<ProductService> {
	
	@RequestMapping("")
	public Collection<Product> list(
			@RequestParam(value="offset", required=false) Integer offset,
			@RequestParam(value="limit", required=false) Integer limit,
			@RequestParam(value="q", required=false) String query,
			@RequestParam(value="category", required=false) Integer category){
	
	    return (Collection<Product>) service.findAll(offset, limit, query, category);
	}
	
	@RequestMapping("length")
	public int length(
			@RequestParam(value="q", required=false) String query,
			@RequestParam(value="category", required=false) Integer category){
	
	    return service.length(query, category);
	}
	
	@RequestMapping("{id}")
	public Product get(@PathVariable("id") int id){
	    Product product= service.findById(id);
	    return product;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable("id") int id){
        Product product = service.findById(id);
        service.delete(product);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Product persist(@RequestBody Product product){
        service.persist(product);
        return product;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Product update(@PathVariable("id") int id, @RequestBody Product product){
        service.update(id, product);
        return product;
	}

}
