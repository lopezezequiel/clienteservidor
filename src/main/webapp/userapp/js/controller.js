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
			var l = [];
			for(p in products) {
				l.push(products[p]);
			}
			return l;
		}
	}
})();

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
		console.log(length);
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

Bappse.route(/ubicacion/, function() {
	$service.user.setLocation();
});

Bappse.setHash('carrito');
$service.user.setLocation();