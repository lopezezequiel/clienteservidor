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

$service.stopSubmit = function(event) {
	if(!event) {
		event = window.event;
	}
	event.preventDefault();
	return false;
}

$service.search = function(event) {
	event = event || window.event;
	if(event.keyCode == 13) {
		var target = event.target || event.srcElement;
		Bappse.setHash('buscar?q=' + target.value)
	}
}

$service.remove = function(target) {
	id = parseInt(target.getAttribute('data-id'));
	$cart.remove(id);

	target.onclick = function() { $service.add(target); }
	target.className = 'glyphicon glyphicon-ok';
}

$service.add = function(target) {
	id = parseInt(target.getAttribute('data-id'));
	$cart.add(id);

	target.onclick = function() { $service.remove(target); }
	target.className = 'glyphicon glyphicon-remove';
}

$service.cart = {}
$service.cart.remove = function(target) {
	id = parseInt(target.getAttribute('data-id'));
	$cart.remove(id);
	
	var row = target.parentElement.parentElement;
	row.parentElement.removeChild(row);
}

$user = {}
$service.setUser = function(user) {
	$user = user;
	$views.userOptions.render(
		document.getElementById('user-options'),
		$user
	);
}

$service.getUser = function(user) {
	return $user;
}

$service.signin = function(form) {
	var data = Bappse.getFormData(form);
	var auth = 'Basic ' + btoa(data.mail + ':' + data.password);
	
	Bappse.GET('/api_v1/auth/signin')
		.header('Authentication', auth)
		.success(function(response) {
			$service.setUser(response.json());
			Bappse.setHash('carrito');
		})
		.error(function(r) {
			console.log(r);
		}).ok();
}

$service.signup = function(form) {
	var user = Bappse.getFormData(form);

	Bappse.POST('/api_v1/regularUsers')
		.header('Content-Type', 'application/json')
		.data(user)
		.success(function(response) {
			$service.setUser(response.json());
			Bappse.setHash('carrito');
		})
		.error(function(r) {
			console.log(r);
		}).ok();
}

