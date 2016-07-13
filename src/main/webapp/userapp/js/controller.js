var app = document.getElementById('app');


Bappse.route(/^verify-(.*)$/, function(key) {
	Bappse.GET('/api_v1/enable')
	.data({key: key})
	.success(function(response) {
		
		var data = {
			message: 'Su cuenta ha sido verificada correctamente. <a href="/userapp/index.html#signin">Ir a la página de inicio de sesión</a>',
		}
		
		if(response.code != 200) {
			
			data = {
				message: 'Código inválido.'
			}
		}
		
		$views.verify.render(
			app,
			data
		);
	})
	.error(function() {
		sweetAlert('Se produjo un error');
		Bappse.setHash('carrito');
	})
	.ok();
});


Bappse.route(/^ubicacion$/, function() {
	if(!$map.ready()) {
		return;
	}
	$map.show();
});

Bappse.route(/^carrito$/, function() {
	$dao.cart.getAll()
	.success(200, function(products) {
		$views.cart.render(
			app,
			{products: products}
		);
	})
	.ok();
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
	})
	.error(function() {
		sweetAlert('Se produjo un error');
	})
	.ok();

});


Bappse.route(/^vaciar-carrito$/, function() {
	$dao.cart.empty()
	.success(200, function() {
		Bappse.setHash('carrito');
	})
	.success(-200, function() {
		alert('Se produjo un error');
	})
	.error(function() {
		alert('Se produjo un error');
	})
	.ok();
});

Bappse.route(/^signin$/, function() {
	$views.signin.render(app);
});

Bappse.route(/^signup$/, function() {
	$views.signup.render(app);
});

Bappse.route(/^signout$/, function() {
	$dao.session.signout()
	.success(200, function() {
		window.location = '/userapp/index.html';
		//$service.updateUserOptions();
	})
	.success(-200, function() {
		alert('Se produjo un error');
	})
	.error(function() {
		alert('Se produjo un error');
	})
	.ok();
});

Bappse.route(/^sucursales(?:\/page([0-9]+))?$/, function(page) {
	page = parseInt(page || 0);
	
	var filter = {
		offset: page * $config.pagination.itemsByPage,
		limit: $config.pagination.itemsByPage,
	}
		
	if (location) {
		filter['latitude'] = $map.getPosition().lat;
		filter['longitude'] = $map.getPosition().lng;
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
			pagination: $widgets.html($widgets.pagination(
					page,
					Math.ceil(length/$config.pagination.itemsByPage),
					$config.pagination.size
				))
		});
	}).error(function(){
		sweetAlert('Se produjo un error');
	}).ok();
});

Bappse.route(/^sucursales\/(\d+)$/, function(id) {
	
	Bappse.AjaxQueue(
		$dao.cart.getAll(true),
		$dao.branch.findById(parseInt(id))
	)
	.success(function(cartProducts, branch) {

		var queue = [];
		
		var ajax = Bappse.AjaxQueue(
			$dao.firm.findById(branch.firm),
			$dao.branchProduct.getAll(id, {
				ids: Object.keys(cartProducts).join(',')
			}, true)
		);

		if(Object.keys(cartProducts) == 0) {
			ajax = $dao.firm.findById(branch.firm);
		}

		ajax
		.success(function(firm, branchProducts) {
			branch.firm = firm;

			if(Object.keys(cartProducts).length != 0) {
				for(id in cartProducts) {
					if(!(id in branchProducts)) {
						branchProducts[id] = cartProducts[id];
					}
				}
			}

			$views.branch.render(app, {
				branch: branch,
				products: branchProducts
			});
		})
		.error(function() {
			sweetAlert('Se produjo un error');
		})
		.ok();

	})
	.error(function() {
		sweetAlert('Se produjo un error');
	})
	.ok();
});

Bappse.route(/^categorias(?:\/(\d+))?$/, function(id) {
	id = id || 1;

	Bappse.AjaxQueue(
		$dao.category.findById(id),
		$dao.category.findAll({parent: id})
	)
	.success(function(category, categories) {
		$views.categories.render(app, { 
			category: category,
			categories: categories
		});
	})
	.error(function(categories) {
		sweetAlert('Se produjo un error');
	})
	.ok();
});

Bappse.route(/^categorias\/(\d+)\/productos$/, function(id) {
	$dao.product.findAll({
		category: parseInt(id)
	})
	.success(200, function(products) {
		$views.products.render(
			app,{ products: products }
		);
	})
	.success(-200, function() {
		sweetAlert('Se produjo un error');
	})
	.error(function() {
		sweetAlert('Se produjo un error');
	})
	.ok();
});

Bappse.route(/^sucursales\/(\d+)\/productos$/, function(id) {
	Bappse.AjaxQueue(
		$dao.branch.findById(parseInt(id)),
		$dao.branchProduct.getAll(parseInt(id))
	).success(function(branch, products) {
		$views.branchProducts.render(app, {
			branch: branch,
			products: products
		});
	}).ok();
});

Bappse.route(/^sucursales\/(\d+)\/productos\/(\d+)$/, function(id, idp) {
	$dao.branchProduct.getAll(id, {ids: [parseInt(idp)]})
	.success(200, function(products) {
		$views.branchProduct.render(app, {
			product: products[0]
		});
	}).ok();
});

Bappse.route(/^productos\/(\d+)$/, function(id) {
	$dao.product.findById(parseInt(id))
	.success(200, function(product) {
		$views.product.render(app, {
			product: product
		});
	}).ok();
});

/*
 * Init
 */


if(Bappse.getHash() == '') {
	Bappse.setHash('#carrito');	
}

$service.updateUserOptions();