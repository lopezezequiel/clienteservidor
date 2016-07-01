var $map = (function(mapId, position, onInit, onClose) {
	
	var map;
	var marker;
	var dom = document.getElementById(mapId);

	var $ = function() {}

	//called by google maps
	$.init = function () {

		map = new google.maps.Map(dom, {
			zoom: 4,
			center: position,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		});
		
		$.setPosition(map.getCenter());


		$.hide();

		// Create the search box and link it to the UI element.
		var searchInput = document.createElement('input');
		searchInput.setAttribute('id', 'pac-input');
		searchInput.setAttribute('class', 'controls');
		searchInput.setAttribute('type', 'text');
		searchInput.setAttribute('placeholder', 'Buscar');
		var searchBox = new google.maps.places.SearchBox(searchInput);
		map.controls[google.maps.ControlPosition.TOP_LEFT].push(searchInput);

		var mapOk = document.createElement('button');
		mapOk.setAttribute('class', 'controls');
		mapOk.appendChild(document.createTextNode('OK'));
		mapOk.onclick = function() {
			$.hide();
			if(onClose) {
				onClose.call(onClose);
			}
		}
		map.controls[google.maps.ControlPosition.TOP_LEFT].push(mapOk);
		

		//Listeners
		map.addListener('bounds_changed', function() {
			searchBox.setBounds(map.getBounds());
		});
	  
		searchBox.addListener('places_changed', function() {
			var places = searchBox.getPlaces();

			if (places.length == 0) {
			  return;
			}

			var place = places[0];
			$.setPosition(place.geometry.location);
			if (place.geometry.viewport) {
				// Only geocodes have viewport.
				map.fitBounds(place.geometry.viewport);
			}
			
		});

		google.maps.event.addListener(map, 'click', function(event) {
			$.setPosition(event.latLng);
			return false;
		});
		
		if(onInit) {
			onInit.call(onInit);
		}
	}
	
	
	$.show = function() {
		dom.style.display = 'block';
		google.maps.event.trigger(map, 'resize');
		$.setPosition(marker.getPosition());
	}
	
	
	$.hide = function() {
		dom.style.display = 'none';
	}
	
	$.getPosition = function() {
		
		if(marker) {	
			return marker.getPosition();
		}
	}
	
	
	$.setPosition = function(latLng) {
		if(marker) {
			marker.setMap(null);
		}

		marker = new google.maps.Marker({
			position: latLng, 
			map: map
		});
	}
	
	$.geolocate = function() {
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function(position) {
				var pos = {
					lat: position.coords.latitude,
					lng: position.coords.longitude
				};

				$.setPosition(pos);
			}, function() {
				//error
			});
		}
	}
	
	return $;
	
})('map', {lat:-38.416097, lng:-63.61667199999999}, function() {
		document.getElementById('location').src = '/img/icons/placeholderok.png';
		document.getElementById('location').onclick = function() {
			Bappse.setHash('#ubicacion');
		}
	}, function() {Bappse.setHash('carrito');});