tv4.language('es');

var DaoFactory = function(schema, url) {
	
	var parseResponse = function(response){
		return (response.code == 200 ? response.json(): null);
	}

	return {
		findById: function(id) {
			return Bappse.GET(url + '/' + id).pre(parseResponse);
		},
		findAll: function(filters) {
			return Bappse.GET(url).data(filters).pre(parseResponse);
		},
		count: function(filters) {
			return Bappse.GET(url + '/length').data(filters).pre(parseResponse);
		},
		update: function(object) {
			if(!tv4.validate(object, schema)) {
				sweetAlert(tv4.error.message);
				throw new Error("Validation Error");
			}

			return Bappse.PUT(url).header('Content-Type', 'application/json')
				.data(object).pre(parseResponse);
		},
		persist: function(object) {
			if(!tv4.validate(object, schema)) {
				sweetAlert(tv4.error.message);
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
$dao.role = DaoFactory($schema.role, '/api_v1/roles');



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