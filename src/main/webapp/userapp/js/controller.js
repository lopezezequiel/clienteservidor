/*
 * Cart
 */

var app = document.getElementById('app');
var $cart = (function() {

	var products = {}

	return {
		add: function(id) {
			$dao.product.findById(id).success(function(product) {
				products[product.id] = product;
			}).ok();
		},
	
		remove: function(id) {
			if(id in products) {
				delete products[id];
			}
		},
		
		getAll: function() {
			var p = {};
			for(key in products) {
				p[key] = products[key];
			}

			return p;
		},

		empty: function() {
			products = {};
		}
	}
})();

/*
 * Routing
 */

Bappse.route(/^ubicacion$/, function() {
	$map.show();
});

Bappse.route(/^carrito$/, function() {
	$views.cart.render(app, {products: $cart.getAll()});
});

Bappse.route(/^buscar(?:\/page([0-9]+))?\?q=(.*)$/, function(page, query) {
	page = parseInt(page || 0);
	
	Bappse.AjaxQueue(
			$dao.product.findAll({
				offset: page * $config.pagination.itemsByPage,
				limit: $config.pagination.itemsByPage,
				q: query
			}),
			$dao.product.count({q: query})
	).success(function(products, length) {
		$views.search.render(
			app,{
				products: products,
				pagination: $service.pagination(
					page,
					Math.ceil(length/$config.pagination.itemsByPage),
					$config.pagination.size
				),
				q: query
			}
		);
	}).ok();

});


Bappse.route(/^vaciar-carrito$/, function() {
	$cart.empty();
	Bappse.setHash('carrito');
});

Bappse.route(/^signin$/, function() {
	$views.signin.render(app);
});

Bappse.route(/^signup$/, function() {
	$views.signup.render(app);
});

Bappse.route(/^signout$/, function() {
	Bappse.deleteCookie('JSESSIONID', '/');
	Bappse.setHash('carrito');
});

Bappse.route(/^sucursales(?:\/page([0-9]+))?$/, function(page) {
	page = parseInt(page || 0);
	
	var filter = {
		offset: page * $config.pagination.itemsByPage,
		limit: $config.pagination.itemsByPage,
	}
		
	if (location) {
		filter['latitude'] = $map.getPosition().lat();
		filter['longitude'] = $map.getPosition().lng();
	}

	Bappse.AjaxQueue(
		$dao.branch.findAll(filter),
		$dao.branch.count(filter),
		$dao.firm.findAll({}, true)
	).success(function(branches, length, firms) {
		for(var i=0; i<branches.length; i++) {
			branches[i].firm = firms[branches[i].firm]; 
		}

		$views.branches.render(app, {
			branches: branches,
			pagination: $service.pagination(
				page,
				Math.ceil(length/$config.pagination.itemsByPage),
				$config.pagination.size
			)
		});
	}).ok();
});

Bappse.route(/^sucursales\/(\d+)$/, function(id) {
	if($cart.getAll().length == 0){
		Bappse.setHash('carrito');
	}

	var ajax = Bappse.AjaxQueue(
		$dao.branch.findById(parseInt(id)),
		Bappse.GET('/api_v1/branches/'+id+'/products?ids='+Object.keys($cart.getAll()).join(','))
			.pre(function(response) {
				if(response.success) {
					var products = response.json();
					var index = {};
					for(var i=0; i<products.length; i++) {
						index[products[i].id] = products[i];
					}
					return index;
				}
				return response;
	}))

	if(Object.keys($cart.getAll()).length == 0) {
		ajax = $dao.branch.findById(parseInt(id));
	}
	
	ajax.success(function(branch, products) {
		
		$dao.firm.findById(branch.firm).success(function(firm) {
			branch.firm = firm;
			var cartProducts = $cart.getAll();
			if(!products) {
				for(id in cartProducts) {
					if(!(id in products)) {
						products[id] = cartProducts[id];
					}
				}
			}
			
			$views.branch.render(app, {
				branch: branch,
				products: products
			});
		}).ok();
	}).ok();
});

Bappse.route(/^sucursales\/(\d+)\/productos$/, function(id) {
	if($cart.getAll().length == 0){
		Bappse.setHash('carrito');
	}
	Bappse.AjaxQueue(
		$dao.branch.findById(parseInt(id)),
		Bappse.GET('/api_v1/branches/'+id+'/products')
			.pre(function(response) {
				if(response.success) {
					return response.json();
				}
				return response;
			})
	).success(function(branch, products) {
		$dao.firm.findById(branch.firm).success(function(firm) {
			branch.firm = firm;
			$views.branchProducts.render(app, {
				branch: branch,
				products: products
			});
		}).ok();
	}).ok();
});

/*
 * Init
 */

Bappse.GET('/api_v1/user')
	.success(function(response) {
		$service.setUser(response.json());
	})
	.error(function(response) {
		$service.setUser({});
	}).ok();

if(Bappse.getHash() == '') {
	Bappse.setHash('#carrito');	
}