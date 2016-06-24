var $service = {}

/*
 * Pagination
 */
$service.pagination = function(currentPage, totalPages, size) {
	var firstPage = Math.max(currentPage - size, 0);
	var lastPage = Math.min(firstPage + size * 2, totalPages);
	firstPage = Math.max(lastPage - size * 2, 0);

	pagination = {
		firstPage: 0,
		lastPage: totalPages - 1
	}
	pagination.pages = Bappse.map(Bappse.range(firstPage, lastPage), function(key, value) {
		return {
			page: value,
			active: (value == currentPage),
			
		};
	});

	return pagination;
}


/*
 * Category Service
 */
$service.category = {};

$service.category.persist = function(form) {
	var category = Bappse.getFormData(form);
	category.parent = parseInt(category.parent);

	$dao.category.persist(category).success(function(category) {
		Bappse.setHash('categories');
	}).ok();
}

$service.category.update = function(form) {
	var category = Bappse.getFormData(form);
	category.id = parseInt(category.id);
	category.parent = parseInt(category.parent);

	$dao.category.update(category).success(function(category) {
		Bappse.setHash('categories');
	}).ok();
}

$service.category.remove = function(id) {
	$dao.category.deleteById(id).success(function() {
		Bappse.setHash('categories');
	}).ok();
}


/*
 * Province Service
 */
$service.province = {};

$service.province.persist = function(form) {
	var province = Bappse.getFormData(form);

	$dao.province.persist(province).success(function(province) {
		Bappse.setHash('provinces');
	}).ok();
}

$service.province.update = function(form) {
	var province = Bappse.getFormData(form);
	province.id = parseInt(province.id);

	$dao.province.update(province).success(function(province) {
		Bappse.setHash('provinces');
	}).ok();
}

$service.province.remove = function(id) {
	$dao.province.deleteById(id).success(function() {
		Bappse.setHash('provinces');
	}).ok();
}


/*
 * Province Service
 */
$service.unit = {};

$service.unit.persist = function(form) {
	var unit = Bappse.getFormData(form);

	$dao.unit.persist(unit).success(function(unit) {
		Bappse.setHash('units');
	}).ok();
}

$service.unit.update = function(form) {
	var unit = Bappse.getFormData(form);
	unit.id = parseInt(unit.id);

	$dao.unit.update(unit).success(function(unit) {
		Bappse.setHash('units');
	}).ok();
}

$service.unit.remove = function(id) {
	$dao.unit.deleteById(id).success(function() {
		Bappse.setHash('units');
	}).ok();
}


/*
 * Product Service
 */
$service.product = {};

$service.product.persist = function(form) {
	var product = Bappse.getFormData(form);
	product.number = parseFloat(product.number);
	product.unit = parseInt(product.unit);
	product.category = parseInt(product.category);

	$dao.product.persist(product).success(function(product) {
		Bappse.setHash('products');
	}).ok();
}

$service.product.update = function(form) {
	var product = Bappse.getFormData(form);
	product.id = parseInt(product.id);
	product.number = parseFloat(product.number);
	product.unit = parseInt(product.unit);
	product.category = parseInt(product.category);

	$dao.product.update(product).success(function(product) {
		Bappse.setHash('products');
	}).ok();
}

$service.product.remove = function(id) {
	$dao.product.deleteById(id).success(function() {
		Bappse.setHash('products');
	}).ok();
}


/*
 * Locality Service
 */
$service.locality = {};

$service.locality.loadDepartments = function(select, target_id) {
	var target = document.getElementById(target_id);
	var province_id = parseInt(select.options[select.selectedIndex].value);

	target.disabled = true;
	select.removeChild(select.options[0]);
	
	$dao.department.findAll({province: province_id}).success(function(departments) {
		Bappse.removeChilds(target);

		Bappse.each(departments, function(index, department) {
			var option = document.createElement('option');
			option.value = department.id;
			option.appendChild(document.createTextNode(department.name));
			target.appendChild(option);
			target.disabled = false;
		});
	}).ok();
}

$service.locality.persist = function(form) {
	var locality = Bappse.getFormData(form);
	locality.department = parseInt(locality.department);

	$dao.locality.persist(locality).success(function(locality) {
		Bappse.setHash('localities');
	}).ok();
}

$service.locality.update = function(form) {
	var locality = Bappse.getFormData(form);
	locality.id = parseInt(locality.id);
	locality.department = parseInt(locality.department);

	$dao.locality.update(locality).success(function(locality) {
		Bappse.setHash('localities');
	}).ok();
}

$service.locality.remove = function(id) {
	$dao.locality.deleteById(id).success(function() {
		Bappse.setHash('localities');
	}).ok();
}


/*
 * Department Service
 */
$service.department = {};

$service.department.persist = function(form) {
	var department = Bappse.getFormData(form);
	department.province = parseInt(department.province);

	$dao.department.persist(department).success(function(department) {
		Bappse.setHash('departments');
	}).ok();
}

$service.department.update = function(form) {
	var department = Bappse.getFormData(form);
	department.id = parseInt(department.id);
	department.province = parseInt(department.province);

	$dao.department.update(department).success(function(department) {
		Bappse.setHash('departments');
	}).ok();
}

$service.department.remove = function(form) {
	var department = Bappse.getFormData(form);
	department.id = parseInt(department.id);	
	$dao.department.remove(department).success(function() {
		Bappse.setHash('departments');
	}).ok();
}


/*
 * Company Service
 */
$service.company = {};

$service.company.persist = function(form) {
	var company = Bappse.getFormData(form);

	$dao.company.persist(company).success(function(company) {
		Bappse.setHash('companies');
	}).ok();
}

$service.company.update = function(form) {
	var company = Bappse.getFormData(form);
	company.id = parseInt(company.id);
	
	$dao.company.update(company).success(function(company) {
		Bappse.setHash('companies');
	}).ok();
}

$service.company.remove = function(form) {
	var company = Bappse.getFormData(form);
	company.id = parseInt(company.id);	
	$dao.company.remove(company).success(function() {
		Bappse.setHash('companies');
	}).ok();
}


/*
 * Firm Service
 */
$service.firm = {};

$service.firm.persist = function(form) {
	var firm = Bappse.getFormData(form);
	firm.company = parseInt(firm.company);

	$dao.firm.persist(firm).success(function(firm) {
		Bappse.setHash('firms');
	}).ok();
}

$service.firm.update = function(form) {
	var firm = Bappse.getFormData(form);
	firm.id = parseInt(firm.id);
	firm.company = parseInt(firm.company);
	
	$dao.firm.update(firm).success(function(firm) {
		Bappse.setHash('firms');
	}).ok();
}

$service.firm.remove = function(form) {
	var firm = Bappse.getFormData(form);
	firm.id = parseInt(firm.id);

	$dao.firm.remove(firm).success(function() {
		Bappse.setHash('firms');
	}).ok();
}


/*
 * Firm Service
 */
$service.branch = {};

$service.branch.persist = function(form) {
	var branch = Bappse.getFormData(form);
	branch.firm = parseInt(branch.firm);
	branch.locality = parseInt(branch.locality);
	branch.latitude = parseFloat(branch.latitude);
	branch.longitude = parseFloat(branch.longitude);

	$dao.branch.persist(branch).success(function(branch) {
		Bappse.setHash('branches');
	}).ok();
}

$service.branch.update = function(form) {
	var branch = Bappse.getFormData(form);
	branch.id = parseInt(branch.id);
	branch.firm = parseInt(branch.firm);
	branch.locality = parseInt(branch.locality);
	branch.latitude = parseFloat(branch.latitude);
	branch.longitude = parseFloat(branch.longitude);
	
	$dao.branch.update(branch).success(function(branch) {
		Bappse.setHash('branches');
	}).ok();
}

$service.branch.remove = function(form) {
	var branch = Bappse.getFormData(form);
	branch.id = parseInt(branch.id);

	$dao.branch.remove(branch).success(function() {
		Bappse.setHash('branches');
	}).ok();
}



$service.branch.loadFirms = function(select, target_id) {
	var target = document.getElementById(target_id);
	var company_id = parseInt(select.options[select.selectedIndex].value);

	target.disabled = true;
	select.removeChild(select.options[0]);
	
	$dao.firm.findAll({company: company_id}).success(function(firms) {
		Bappse.removeChilds(target);

		Bappse.each(firms, function(index, firm) {
			var option = document.createElement('option');
			option.value = firm.id;
			option.appendChild(document.createTextNode(firm.name));
			target.appendChild(option);
		});
		
		target.disabled = false;
	}).ok();
}

$service.branch.loadDepartments = function(select, target_id) {
	var target = document.getElementById(target_id);
	var province_id = parseInt(select.options[select.selectedIndex].value);

	target.disabled = true;
	select.removeChild(select.options[0]);
	
	$dao.department.findAll({province: province_id}).success(function(departments) {
		Bappse.removeChilds(target);

		var option = document.createElement('option');
		option.appendChild(document.createTextNode('Seleccione un departamento'));
		target.appendChild(option);
		
		Bappse.each(departments, function(index, department) {
			option = document.createElement('option');
			option.value = department.id;
			option.appendChild(document.createTextNode(department.name));
			target.appendChild(option);
		});
		
		target.disabled = false;
	}).ok();
}

$service.branch.loadLocalities = function(select, target_id) {
	var target = document.getElementById(target_id);
	var department_id = parseInt(select.options[select.selectedIndex].value);

	target.disabled = true;
	select.removeChild(select.options[0]);
	
	$dao.locality.findAll({department: department_id}).success(function(localities) {
		Bappse.removeChilds(target);

		Bappse.each(localities, function(index, locality) {
			var option = document.createElement('option');
			option.value = locality.id;
			option.appendChild(document.createTextNode(locality.name));
			target.appendChild(option);
		});
		
		target.disabled = false;
	}).ok();
}