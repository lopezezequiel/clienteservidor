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
	target.src = '/img/icons/plus.png';
}

$service.add = function(target) {
	id = parseInt(target.getAttribute('data-id'));
	$cart.add(id);

	target.onclick = function() { $service.remove(target); }
	target.src = '/img/icons/minus.png';
}

$service.cart = {}
$service.cart.remove = function(target) {
	id = parseInt(target.getAttribute('data-id'));
	$cart.remove(id);
	
	var row = target.parentElement.parentElement;
	row.parentElement.removeChild(row);
}

$service.user = {}

$service.user.setLocation = function() {
	//Try HTML5 geolocation.
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			var location = {
				latitude: position.coords.latitude,
				longitude: position.coords.longitude
		    };
			$user.location = location;
			document.getElementById('location').src = '/img/icons/placeholderok.png';
		});
	
	} else {
		
	}
}