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

$service.index = {}
$service.index.search = function(event) {
	event = event || window.event;
	if(event.keyCode == 13) {
		var target = event.target || event.srcElement;
		Bappse.setHash('buscar?q=' + target.value)
	}
}

$service.search = {}
$service.search.removeFromCart = function(target) {
	var id = parseInt(target.getAttribute('data-id'));
	$dao.cart.remove(id)
	.success(200, function() {
		target.onclick = function() { $service.search.addToCart(target); }
		target.className = 'glyphicon glyphicon-ok';
	})
	.success(-200, function() {
		sweetAlert('Se produjo un error');
	}).error(function() {
		sweetAlert('Se produjo un error');
	}).ok();
}

$service.search.addToCart = function(target) {
	var id = parseInt(target.getAttribute('data-id'));
	$dao.cart.add(id)
	.success(200, function() {
		target.onclick = function() { $service.search.removeFromCart(target); }
		target.className = 'glyphicon glyphicon-remove';
	})
	.success(-200, function() {
		sweetAlert('Se produjo un error');
	}).error(function() {
		sweetAlert('Se produjo un error');
	}).ok();
}

$service.cart = {}
$service.cart.remove = function(target) {
	var id = parseInt(target.getAttribute('data-id'));
	$dao.cart.remove(id)
	.success(200, function() {
		var tr = target.parentNode.parentNode;
		tr.parentNode.removeChild(tr);
	})
	.success(-200, function() {
		sweetAlert('Se produjo un error');
	}).error(function() {
		sweetAlert('Se produjo un error');
	}).ok();
}

$service.updateUserOptions = function() {
	$dao.session.getUser()
	.success(200, function(user) {
		$views.userOptions.render(
			document.getElementById('user-options'),
			user
		);

		if(Bappse.isIn(user.userRoles, 'ROLE_ADMIN_C1')) {
			window.location = '/adminapp/index.html';	
		} else if(Bappse.isIn(user.userRoles, 'ROLE_ADMIN_C2')) {
			window.location = '/adminapp/index.html';	
		}
	})
	.success(-200, function() {
		sweetAlert('Se produjo un error');
	})
	.error(function() {
		sweetAlert('Se produjo un error');
	})
	.ok();
}

$service.signin = function(form) {
	var data = Bappse.getFormData(form);
	$dao.session.signin(data.mail, data.password)
	.success(200, function() {
		$service.updateUserOptions();
		Bappse.setHash('carrito');
	})
	.success(401, function() {
		sweetAlert('User is not in the sudoers file');
	})
	.success(-200, -401, function() {
		sweetAlert('Se produjo un error');
	})
	.error(function() {
		sweetAlert('Se produjo un error');
	})
	.ok();	
}

$service.signup = function(form) {
	var user = Bappse.getFormData(form);
	console.log(user);

	$dao.user.persist(user)
	.success(200, function() {
		swal({
			title: 'Usuario registrado', 
			text: 'Te enviamos un correo de confirmación',
			type: 'success',
			closeOnConfirm: true
		}, function() {
			Bappse.setHash('signin');
		});
	})
	.success(409, function(response) {
		sweeetAlert('El correo ' + user.mail + ' ya se encuentra registrado');
	})
	.success(-409, -200, function(response) {
		sweetAlert('Falló registro');
	})
	.error(function() {
		sweetAlert('Falló registro');
	})
	.ok();
}