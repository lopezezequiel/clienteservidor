var DaoFactory = function(schema, url) {
	
	var parseResponse = function(response){
		return (response.code == 200 ? response.json(): response);
	}

	var parseIndex = function(response) {

		if(response.code == 200) {
			var objects = response.json();
			var index = {};
			for(var i=0; i<objects.length; i++) {
				index[objects[i].id] = objects[i]; 
			}
			return index;
		}

		return response;
	}

	return {
		findById: function(id) {
			return Bappse.GET(url + '/' + id).pre(parseResponse);
		},
		findAll: function(filters, indexed) {
			console.log(filters);
			fn = (indexed === true) ? parseIndex : parseResponse;
			return Bappse.GET(url).data(filters).pre(fn);
		},
		count: function(filters) {
			return Bappse.GET(url + '/length').data(filters).pre(parseResponse);
		},
		update: function(object) {
			if(!tv4.validate(object, schema)) {
				console.log(tv4.error);
				throw new Error("Validation Error");
			}

			return Bappse.PUT(url).header('Content-Type', 'application/json')
				.data(object).pre(parseResponse);
		},
		persist: function(object) {
			if(!tv4.validate(object, schema)) {
				console.log(tv4.error);
				throw new Error("Validation Error");
			}

			return Bappse.POST(url).header('Content-Type', 'application/json')
				.data(object).pre(parseResponse);
		},
		deleteById: function(id) {
			return Bappse.DELETE(url + '/' + id).pre(parseResponse);
		},
		remove: function(object) {
			if(!Bappse.isInteger(object.id)) {
				throw TypeError();
			}
			return Bappse.DELETE(url + '/' + object.id).pre(parseResponse);
		}
	}
}

$dao = {};
$dao.category = DaoFactory($schema.category, '/api_v1/categories');
$dao.branch = DaoFactory($schema.branch, '/api_v1/branches');
$dao.firm = DaoFactory($schema.firm, '/api_v1/firms');
$dao.locality = DaoFactory($schema.locality, '/api_v1/localities');
$dao.department = DaoFactory($schema.department, '/api_v1/departments');
$dao.province = DaoFactory($schema.province, '/api_v1/provinces');
$dao.company = DaoFactory($schema.company, '/api_v1/companies');
$dao.product = DaoFactory($schema.product, '/api_v1/products');
$dao.unit = DaoFactory($schema.unit, '/api_v1/units');
$dao.user = DaoFactory($schema.user, '/api_v1/users');
$dao.branchProduct = {}
$dao.branchProduct.getAll = function(branchId, filters, indexed) {
	var parseResponse = function(response){
		return (response.code == 200 ? response.json(): response);
	}

	var parseIndex = function(response) {

		if(response.code == 200) {
			var objects = response.json();
			var index = {};
			for(var i=0; i<objects.length; i++) {
				index[objects[i].id] = objects[i]; 
			}
			return index;
		}

		return response;
	}
	fn = (indexed === true) ? parseIndex : parseResponse;
	return Bappse.GET('/api_v1/branches/'+branchId+'/products').data(filters).pre(fn);
}

$dao.cart =  (function() {
	var parseResponse = function(response){
		return (response.code == 200 ? response.json(): response);
	}
	
	var parseIndex = function(response) {

		if(response.code == 200) {
			var objects = response.json();
			var index = {};
			for(var i=0; i<objects.length; i++) {
				index[objects[i].id] = objects[i]; 
			}
			return index;
		}

		return response;
	}
	
	return {
		add: function(id) {
			return Bappse.POST('/api_v1/session/user/cart/'+id)
			.pre(parseResponse);
		},
		remove: function(id) {
			return Bappse.DELETE('/api_v1/session/user/cart/'+id);
		},
		getAll: function(indexed) {

			var fn = (indexed === true) ? parseIndex : parseResponse;
			return Bappse.GET('/api_v1/session/user/cart')
			.pre(fn);				
		},
		empty: function() {
			return Bappse.DELETE('/api_v1/session/user/cart')
			.pre(parseResponse);
		}
	}
})();

$dao.session = (function() {
	var parseResponse = function(response){
		return (response.code == 200 ? response.json(): response);
	}

	return {
		getUser: function() {
			return Bappse.GET('/api_v1/session/user').pre(parseResponse);
		},
		signin: function(username, password) {
			return Bappse.POST('/api_v1/session/signin')
			.header('Authorization', 'Basic ' + btoa(username + ':' + password))
			.pre(parseResponse);
		},
		signout: function() {
			return Bappse.POST('/api_v1/session/signout').pre(parseResponse);
		},
		getCart: function() {
			return cart;
		}
	}
})();