package com.clienteservidor.preciosclaros.restapi;

import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.Branch;
import com.clienteservidor.preciosclaros.model.BranchProduct;
import com.clienteservidor.preciosclaros.model.Product;
import com.clienteservidor.preciosclaros.serialization.CommaSeparatedListToIntegerListDeserializer;
import com.clienteservidor.preciosclaros.service.BranchService;

@RestController
@RequestMapping("/api_v1/branches")
public class BranchController extends GenericController<BranchService> {

	@RequestMapping("")
	public Collection<Branch> findAll(
			@RequestParam(value="offset", required=false) Integer offset,
			@RequestParam(value="limit", required=false) Integer limit,
			@RequestParam(value="firm", required=false) Integer firmId,
			@RequestParam(value="products", required=false) String idsString,
			@RequestParam(value="locality", required=false) Integer localityId,
			@RequestParam(value="latitude", required=false) Double latitude,
			@RequestParam(value="longitude", required=false) Double longitude){


		List<Integer> list = (idsString == null) ? null :
			CommaSeparatedListToIntegerListDeserializer.deserialize(idsString);

		
	   	return service.findAll(offset, limit, firmId, list, localityId, 
	   			latitude, longitude);
	}
	
	@RequestMapping("length")
	public int length(
			@RequestParam(value="firm", required=false) Integer firmId,
			@RequestParam(value="products", required=false) String idsString,
			@RequestParam(value="locality", required=false) Integer localityId){


		List<Integer> list = (idsString == null) ? null :
			CommaSeparatedListToIntegerListDeserializer.deserialize(idsString);
		
	   	return service.length(firmId, localityId, list);
	}
	
	@RequestMapping("{id}")
	public Branch findById(@PathVariable("id") int id) {
        return service.findById(id);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable("id") int id){
        service.delete(service.findById(id));
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public Branch persist(@RequestBody Branch branch){
        service.persist(branch);
        return branch;
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	public Branch update(@RequestBody Branch branch){
        service.update(branch);
        return branch;
	}

	/*
	 * Branch Products
	 */
	@RequestMapping("{id}/products")
	public Collection<Product> findAllProducts(
		@PathVariable("id") int branchId,
		@RequestParam(value="ids", required=false) String idsString,
		@RequestParam(value="offset", required=false) Integer offset,
		@RequestParam(value="limit", required=false) Integer limit){


		List<Integer> list = (idsString == null) ? null :
			CommaSeparatedListToIntegerListDeserializer.deserialize(idsString);
	   	
	   	return service.findAllProducts(offset, limit, branchId, list, false);
	}

	@RequestMapping("{id}/products/{product_id}")
	public Product findProductById(
		@PathVariable("id") int branchId,
		@PathVariable("product_id") int productId){
		
	   	return service.findProductById(branchId, productId);
	}

/*
	@RequestMapping(value = "{id}/products", method = RequestMethod.POST)
	public void batchUpdateProducts(
		@PathVariable("id") int branchId,
		@RequestBody Collection<BranchProduct> collection){
		
	   	service.batchUpdateProducts(branchId, collection);
	}

	
*/
	
	/*
	 * 
	 */
	@RequestMapping(value = "{id}/products", method = RequestMethod.POST)
	public void updateProducts(
		@PathVariable("id") int branchId,
		@RequestBody BranchProduct branchProduct){
		
	   	service.updateBranchProduct(branchId, branchProduct);
	}

	@RequestMapping(value = "{id}/products/{ean}", method = RequestMethod.DELETE)
	public void delete(
			@PathVariable("id") int branchId,
			@PathVariable("ean") String ean){
		
	   	service.deleteBranchProduct(branchId, ean);
	}
}
