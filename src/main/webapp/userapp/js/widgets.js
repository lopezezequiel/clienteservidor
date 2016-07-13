
$widgets = (function() {
	
	var $ = function() {}
	
	$.html = function(element) {
		var parent = document.createElement('div');
		parent.appendChild(element);
		return parent.innerHTML;
	}
	
	$.a = function(href, text) {
		var a = document.createElement('a');
		var text = document.createTextNode(text);
		a.appendChild(text);
		a.setAttribute('href', href);
		return a;
	}

	$.li = function(childs) {
		var li = document.createElement('li');
		for(var i=0; i<childs.length; i++) {
			li.appendChild(childs[i]);
		}
		return li;
	}

	$.ul = function(childs) {
		var ul = document.createElement('ul');
		for(var i=0; i<childs.length; i++) {
			ul.appendChild(childs[i]);
		}
		return ul;
	}
	
	$.pagination = function(currentPage, totalPages, size) {
		var firstPage = Math.max(currentPage - size, 0);
		var lastPage = Math.min(firstPage + size * 2, totalPages);
		firstPage = Math.max(lastPage - size * 2, 0);
		
		var links = [];

		links.push($.li([$.a('#sucursales/page0', '<<')]));
		var range = Bappse.range(firstPage, lastPage);
		for(key in range) {
			var value = range[key];
			var li = $.li([$.a('#sucursales/page' + value, value)]);
			if(value == currentPage) {
				li.setAttribute('class', 'active');
			}
			links.push(li);
		}

		links.push($.li([$.a('#sucursales/page' + (totalPages - 1), '>>')]));
	
		var ul = $.ul(links);
		ul.setAttribute('class', 'pagination');
		return ul;
	}
	
	return $;
	
})();