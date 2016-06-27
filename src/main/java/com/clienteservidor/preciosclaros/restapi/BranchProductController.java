package com.clienteservidor.preciosclaros.restapi;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.BranchProduct;
import com.clienteservidor.preciosclaros.service.BranchProductService;

@RestController
@RequestMapping("/api_v1/branches/{id}/products")
public class BranchProductController extends GenericController<BranchProductService> {

	@RequestMapping("")
	public Collection<BranchProduct> list(
		@PathVariable("id") int branchId,
		@RequestParam(value="eans", required=false) String eansString,
		@RequestParam(value="offset", required=false) Integer offset,
		@RequestParam(value="limit", required=false) Integer limit){

		List<String> eans = null;
		if(eansString != null) {
			if(!Pattern.matches("^\\d{13}(,\\d{13})*$", eansString)) {
				//TODO
				throw new RuntimeException();
			}
			
			eans = Arrays.asList(eansString.split(","));
			System.out.println(eans.toString());
		}
		
		
	   	return service.findAll(offset, limit, branchId, eans, false);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public void batchUpdate(
		@PathVariable("id") int branchId,
		@RequestBody Collection<BranchProduct> collection){
		
	   	service.batchUpdate(branchId, collection);
	}

	@RequestMapping(value = "{ean}", method = RequestMethod.POST)
	public void update(
		@PathVariable("id") int branchId,
		@PathVariable("ean") String ean,
		@RequestBody BranchProduct branchProduct){
		
	   	service.update(branchId, ean, branchProduct);
	}

	@RequestMapping(value = "{ean}", method = RequestMethod.DELETE)
	public void delete(
		@PathVariable("id") int branchId,
		@PathVariable("ean") String ean){
		
	   	service.logicalDelete(branchId, ean);
	}
}