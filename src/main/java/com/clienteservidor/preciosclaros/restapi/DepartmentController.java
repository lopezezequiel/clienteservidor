package com.clienteservidor.preciosclaros.restapi;


import java.util.Collection;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.Department;
import com.clienteservidor.preciosclaros.service.DepartmentService;

@RestController
@RequestMapping("/api_v1/departments")
public class DepartmentController extends GenericController<DepartmentService> {
	
	@RequestMapping("")
	public Collection<Department> getDepartmentList(
		@RequestParam(value="offset", required=false) Integer offset,
		@RequestParam(value="limit", required=false) Integer limit,
		@RequestParam(value="province", required=false) Integer provinceId,
		@RequestParam(value="q", required=false) String query){

	   	Collection<Department> departments = service.findAll(offset, limit, provinceId, query);
        return departments;
	}
	
	
	@RequestMapping("length")
	public int getLength(
		@RequestParam(value="province", required=false) Integer provinceId,
		@RequestParam(value="q", required=false) String query){

	   	int departmentsLenght = service.length(provinceId, query);
        return departmentsLenght;
	}

	@RequestMapping("{id}")
	public Department getDepartment(@PathVariable("id") int id) {
		Department department = service.findById(id);

        return department;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable("id") int id){
        Department department = service.findById(id);

        service.delete(department);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Department persist(@RequestBody Department department){
        service.persist(department);
        return department;
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public Department update(@RequestBody Department department){
        service.update(department);
        return department;
	}

}
