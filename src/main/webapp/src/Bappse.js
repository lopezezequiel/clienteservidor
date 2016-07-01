var Bappse = (function() {

	var Errors = {
		BrowserIsNotSupported: function(feature) {
			this.name = 'BrowserIsNotSupported';
			this.message = 'The browser does not support ' + feature;
		},
		MethodIsNotSupported: function(method) {
			this.name = 'MethodIsNotSupported';
			this.message = method + ' method is not supported';
		},
		ParameterIsRequired: function(name) {
			this.name = 'ParameterIsRequired';
			this.message = name + ' is required'; 
		}
	}

	var Ajax = function(method, url) {

		var config = {
			user: '',
			password: '',
			success: [],
			error: [],
			always: [],
			code: [],
			data: null,
			queryParams: '',
			timeout: 0,
			pre: null,
			headers: {
				'content-type': 'application/x-www-form-urlencoded;charset=UTF-8',
				'x-requested-with': 'XMLHttpRequest'	
			}
		};

		var xhr = $.getXHR();

		var getResponse = function(xhr) {
			var response = {
				text: xhr.responseText,
				xml: xhr.responseXML,
				json: function() {
					try {
						return JSON.parse(xhr.responseText);
					} catch(e) {
					}
				},
				status: xhr.status,
				success: ($.inArray([200, 304], xhr.status) ? true : false)
			}

			if(config.pre) {
				response = config.pre.call(config.pre, response);
			}

			return response;
		};

		this.auth = function(user, password) {

			if(!$.isString(user)) {
				throw new TypeError('user must be string type');
			}

			if(password == null) {
				password = '';
			}

			if(!$.isString(password)) {
				throw new TypeError('password must be string type');
			}
			
			config.user = user;
			config.password = password;

			return this;
		}
		
		this.success = function(fn) {
			if(!$.isFunction(fn)) {
				throw new TypeError('fn must be a Function');
			}

			config.success.push(fn);
			return this;
		}
		
		this.pre = function(fn) {
			if(!$.isFunction(fn)) {
				throw new TypeError('fn must be a Function');
			}

			config.pre = fn;

			return this;
		}
		
		this.error = function(fn) {
			if(!$.isFunction(fn)) {
				throw new TypeError('fn must be a Function');
			}

			config.error.push(fn);
			return this;
		}
		
		this.always = function(fn) {
			if(!$.isFunction(fn)) {
				throw new TypeError('fn must be a Function');
			}

			config.always.push(fn);
			return this;
		}
		
		this.code = function(code, fn) {

			if(!$.isInteger(code)) {
				throw new TypeError('code must be an Integer');
			}

			if(!$.isFunction(fn)) {
				throw new TypeError('fn must be a Function');
			}

			if(!$.isArray(config.code[code])) {
				config.code[code] = [];
			}

			config.code[code].push(fn);

			return this;
		}

		this.data = function(data) {
			if(method == 'GET') {
				var queryParams = $.toQueryParams(data);
				if(queryParams != '') {
					config.queryParams = '?' + queryParams;
				}
			} else {
				config.data = data;
			}
			
			return this;
		}

		this.timeout = function(timeout) {
			if(!$.isInteger(timeout)) {
				throw new TypeError('timeout must be an Integer');
			}

			config.timeout = timeout;

			return this;
		}

		this.header = function(name, value) {

			if(!$.isString(name)) {
				throw new TypeError('name must be string type');
			}

			if(!$.isString(value)) {
				throw new TypeError('value must be string type');
			}

			name = name.toLowerCase();

			if(name == 'content-type' && !value) {
				delete config.headers[name];
			} else {
				config.headers[name] = value;
			}
			
			return this;
		}

		this.ok = function() {
			try {

				//Open conecction
				xhr.open(
					method,
					url + config.queryParams,
					true, 
					config.user,
					config.password
				);

				//Set timeout
				xhr.timeout = config.timeout;

				//Set Headers
				for(key in config.headers) {
					xhr.setRequestHeader(key, config.headers[key]);	
				}

				//Set callbacks
				xhr.onreadystatechange = function () {
					if (xhr.readyState != 4) return;

					var response = getResponse(xhr);

					//depending on the status code
					var fns = config.code[xhr.status];
					if($.isArray(fns)) {
						$.each(fns, function(index, fn) {
							fn.call(fn, response);
						});
					}

					if($.inArray([200, 304], xhr.status)) {//success
						$.each(config.success, function(index, fn) {
							fn.call(fn, response);
						});
					} else {//error
						$.each(config.error, function(index, fn) {
							fn.call(fn, response);
						});
					}

					//always
					$.each(config.always, function(index, fn) {
						fn.call(fn, response, $.inArray([200, 304], xhr.status));
					});
				}
				
				//Send data
				if(config.headers['content-type'].toLowerCase() == 'application/json') {
					xhr.send(JSON.stringify(config.data));	
				} else {
					xhr.send(config.data);
				}
			} catch (e) {
				//TODO
				console.log(e);				
			}
		}

		if(!$.isString(url)){
			throw new TypeError('url must be string type');
		};

		if(!xhr) {
			throw new Errors.BrowserIsNotSupported('ajax');
		}	
	}
	
	var $ = {};

	//polyfill from https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Number/isInteger
	$.isInteger = Number.isInteger || function(value) {
	    return typeof value === 'number' && isFinite(value) && 
           Math.floor(value) === value;
	}

	$.isNumber = function(value) {
	    return typeof value === 'number' && isFinite(value);
	}

	$.isFunction = function(value) {
		return (typeof value) == 'function';
	}

	//https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/typeof
	//typeof /s/ === 'function'; Chrome 1-12 Non-conform to ECMAScript 5.1
	if((typeof /./) === 'function') {
		$.isFunction = function(value) {
			return Object.prototype.toString.call(value) == '[object Function]';
		}
	}
	
	$.isString = function(value) {
		return typeof value === 'string' || value instanceof String;
	}

	//polyfill from https://developer.mozilla.org/es/docs/Web/JavaScript/Referencia/Objetos_globales/Array/isArray
	$.isArray = Array.isArray || function(value) {
		return Object.prototype.toString.call(value) === '[object Array]';
	}

	$.isRegex = function(value) {
		return Object.prototype.toString.call(value) === '[object RegExp]';
	}

	//from http://stackoverflow.com/questions/384286/javascript-isdom-how-do-you-check-if-a-javascript-object-is-a-dom-object
	//Returns true if it is a DOM node
	$.isNode = function(object) {
		return (
				typeof Node === "object" ? object instanceof Node : 
				object && typeof object === "object"
				&& typeof object.nodeType === "number" 
				&& typeof object.nodeName==="string"
		);
	}

	//from http://stackoverflow.com/questions/384286/javascript-isdom-how-do-you-check-if-a-javascript-object-is-a-dom-object
	//Returns true if it is a DOM element    
	$.isElement = function(object) {
		return (
			typeof HTMLElement === "object" ? object instanceof HTMLElement : //DOM2
			object && typeof object === "object" && object !== null
			&& object.nodeType === 1 && typeof object.nodeName==="string"
		);
	}

	$.removeChilds = function(element) {
		if(!$.isElement(element)) {
			throw TypeError('element must be a DOM Element');
		}
		while (element.firstChild) {
		   element.removeChild(element.firstChild);
		}
	}
	
	$.range = function(start, stop, step) {

	
		if(!$.isNumber(start)) {
			throw new TypeError('start must be a number');
		}

		if(!$.isNumber(stop)) {
			throw new TypeError('stop must be a number');
		}

		step = step || 1;
		if(!$.isNumber(step)) {
			throw new TypeError('step must be a number');
		}
	
		var array = [];
		var x = start;

		while((step > 0 && x < stop) || (step < 0 && x > stop)) {
			array.push(x);
			x += step;
		}

		return array;
	}

	$.getFormData = function(form) {
		var control, name;
		var data = {};

		for (var i=0; i<form.elements.length; i++) {
			control = form.elements[i];
			name = control.getAttribute('name');
			if(name != null) {
				data[name] = control.value;
			}
		}
		
		return data;
	}

	$.toQueryParams = function(data) {
		var params = [];
		for(var p in data) {
			params.push(encodeURIComponent(p) + "=" + encodeURIComponent(data[p]));
		}
		return params.join("&");
	}

	//just a trick from https://gist.github.com/jlong/2428561
	//TODO replace
	$.parseURL = function(url) {

		if(!url) {
			url = window.location.href;
		}
		
		if(!$.isString(url)) {
			throw new TypeError('url must be string type');
		}

		var a = document.createElement('a');
		a.href = url;

		return {
			protocol: a.protocol,
			hostname: a.hostname,
			port: a.port,
			pathname: a.pathname,
			search: a.search,
			hash: a.hash,
			host: a.host,
			username: a.username,
			password: a.password,
			href: a.href
		}
	}

	$.copyProperties = function(object) {
		var properties = {};
		for(key in object) {
			properties[key] = object[key];
		}
		return properties;
	}

	$.GET = function(url) {
		return new Ajax('GET', url);
	}

	$.POST = function(url) {
		return new Ajax('POST', url);
	}

	$.PUT = function(url) {
		return new Ajax('PUT', url);
	}

	$.DELETE = function(url) {
		return new Ajax('DELETE', url);
	}

	$.getXHR = function() {
		var xhrList = [
			function () {return new XMLHttpRequest()},
			function () {return new ActiveXObject("Msxml2.XMLHTTP")},
			function () {return new ActiveXObject("Msxml3.XMLHTTP")},
			function () {return new ActiveXObject("Microsoft.XMLHTTP")}
		];

		var xhr = null;

		for (var i=0; i<xhrList.length; i++) {
			try {
				xhr = xhrList[i]();
			} catch (e) {
				continue;
			}
			break;
		}
		
		return xhr;
	}

	$.each = function(array, fn) {
		if(!$.isFunction(fn)) {
			throw new TypeError('fn must be a Function');
		}
		
		if(!$.isArray(array)) {
			throw new TypeError('array must be an Array');
		}

		for(var i=0; i<array.length; i++) {
			fn.call(array[i], i, array[i]);
		}
	}

	$.map = function(array, fn) {
		var map = [];
		
		$.each(array, function(key, value) {
			map.push(fn.call(fn, key, value));
		});
		
		return map;
	}

	$.every = function(array, fn) {
		if(!$.isFunction(fn)) {
			throw new TypeError('fn must be a Function');
		}
		
		if(!$.isArray(array)) {
			throw new TypeError('array must be an Array');
		}

		for(var i=0; i<array.length; i++) {
			if(!fn.call(array[i], i, array[i])) {
				return false;
			}
		}
		
		return true;
	}

	$.some = function(array, fn) {
		if(!$.isFunction(fn)) {
			throw new TypeError('fn must be a Function');
		}
		
		if(!$.isArray(array)) {
			throw new TypeError('array must be an Array');
		}

		for(var i=0; i<array.length; i++) {
			if(fn.call(array[i], i, array[i])) {
				return true;
			}
		}
		
		return false;
	}

	$.inArray = function(array, value) {
		return $.some(array, function(k, v) {
			return v === value;
		});
	}

	//http://stackoverflow.com/questions/7376598/in-javascript-how-do-i-check-if-an-array-has-duplicate-values
	$.hasDuplicates = function(array) {
	    return (new Set(array)).size !== array.length;
	}

	
	$.merge = function(object1,object2){
	    var object3 = {};
	    for (var attrname in object1) { object3[attrname] = object1[attrname]; }
	    for (var attrname in object2) { object3[attrname] = object2[attrname]; }
	    return object3;
	}

	if(!Set) {
		$.hasDuplicates = function(array) {
		    var valuesSoFar = [];
		    for (var i = 0; i < array.length; ++i) {
		        var value = array[i];
		        if (valuesSoFar.indexOf(value) !== -1) {
		            return true;
		        }
		        valuesSoFar.push(value);
		    }
		    return false;
		}
	}
	
	$.inherits = function(parentClass, childClass) {
		childClass.prototype = Object.create(parentClass.prototype);
	}

	$.compileTemplateFromString = function(string) {
		if(!$.isString(string)) {
			throw new TypeError('string must be string type');
		}
		
		return new function() {
			var template = Handlebars.compile(string);

			this.render = function(target, data) {

				if(!$.isElement(target)) {
					throw new TypeError('target must be a DOM Element');
				}

				target.innerHTML = template(data);
			}
		}();
	}

	$.compileTemplateFromURL = function(url) {
		var template = null;

		return {
			render: function(target, data) {
				
				if(!$.isElement(target)) {
					throw new Error('target must be a DOM Element');
				}

				if(template == null) {
					$.GET(url).success(function(response) {
						template = Handlebars.compile(response.text);
						target.innerHTML = template(data);
					}).ok();
				} else {
					target.innerHTML = template(data);
				}
			}
		}
	}	

	var AjaxQueue = function(args) {
		var queue = [];
		var successList = [];
		var alwaysFn, successFn, errorFn;

		$.each(args, function(index, ajax) {

			if(!(ajax instanceof Ajax)) {
				throw new TypeError('All parameters must be Ajax type');
			}

			ajax.always(function(response, success) {
				queue[index] = response;
				successList[index] = success;
				checkQueue();
			});
		});

		var checkQueue = function() {

			if(args.length == queue.length) {
				if(alwaysFn) {
					alwaysFn.apply(alwaysFn, queue);					
				}

				if(successFn && $.every(successList, function(index, success) {
					return success;
				})){
					successFn.apply(successFn, queue);
				} else if(errorFn && $.some(successList, function(index, success) {
					return !success;
				})){
					errorFn.apply(errorFn, queue);
				}
			}
		}

		this.always = function(callback) {
			if(!$.isFunction(callback)) {
				throw new TypeError('fn must be a Function');
			}

			alwaysFn = callback;
			
			return this;
		}

		this.success = function(callback) {
			if(!$.isFunction(callback)) {
				throw new TypeError('fn must be a Function');
			}

			successFn = callback;
			
			return this;
		}

		this.error = function(callback) {
			if(!$.isFunction(callback)) {
				throw new TypeError('fn must be a Function');
			}

			errorFn = callback;
			
			return this;
		}

		this.ok = function() {
			$.each(args, function(index, ajax) {
				ajax.ok();
			});
		}
	}
	
	$.AjaxQueue = function() {
		return new AjaxQueue([].slice.call(arguments));
	}

	$.addEvent = function(target, type, callback) {
		if(!$.isElement(target) && target!=window) {
			throw new TypeError('target must be a DOM element');
		}

		if(!$.isString(type)) {
			throw new TypeError('type must be string type');
		}

		if(!$.isFunction(callback)) {
			throw new TypeError('callback must be a function');
		}

		target.addEventListener(type, callback);		
	}

	//for <IE9
	if(!document.addEventListener) {
		if(!$.isElement(target) && target!=window) {
			throw new TypeError('target must be a DOM element');
		}

		if(!$.isString(type)) {
			throw new TypeError('type must be string type');
		}

		if(!$.isFunction(callback)) {
			throw new TypeError('callback must be a function');
		}

		$.addEvent = function(target, type, callback) {
			target.attachEvent('on'+type, function() {
				//TODO fix this
				window.event.target = window.event.srcElement;
				callback.call(callback, window.event);
			});
		}
	}
	
	$.HTTP_UNAUTHORIZED = 401;
	
	$.setCookie = function(name, value, milliseconds, path) {
		if(!$.isString(name)) {
			throw new TypeError('name must be string');
		}

		if(!$.isString(value)) {
			throw new TypeError('value must be string');
		}
		

		if(!$.isInteger(milliseconds)) {
			throw new TypeError('milliseconds must be integer');
		}
		

		if(path != null && !$.isString(name)) {
			throw new TypeError('path must be string');
		}
		
	    var date = new Date();
	    date.setTime(date.getTime() + milliseconds);
	    document.cookie = name + '=' + value 
	    	+ '; expires=' + date.toUTCString()
	    	+ ((path != undefined) ? ('; path=' + path) : '');
	}

	$.getCookie = function(name) {
		if(!$.isString(name)) {
			throw new TypeError('name must be string');
		}
		
		var cookies = document.cookie.split(';');
		name = name + '=';
		for(var i=0; i<cookies.length; i++) {
			cookie = cookies[i].trim();
			if(cookie.indexOf(name) == 0) {
				return cookie.substring(name.length);
			}
		}
	}
	
	$.deleteCookie = function(name, path) {
		$.setCookie(name, '', -1, path);
	}

	$.route = (function() {

		var routes = [];
		
		var tryRoute = function(hash, route) {
			if(route.pattern.test(hash)) {
				route.callback.apply(
						route.callback,
						hash.match(route.pattern).slice(1)
				);
			}
		}

		var route = function() {
			var hash = $.getHash();

			$.each(routes, function(index, route) {
				tryRoute(hash, route);
			});
		}

		$.addEvent(window, 'hashchange', route);

		return function(pattern, callback) {
			
			
			if(!$.isRegex(pattern)) {
				throw new TypeError('pattern must be a RegExp');
			}

			if(!$.isFunction(callback)) {
				throw new Error('callback must be a Function');
			}

			var route = {
					pattern: pattern,
					callback: callback
			}

			tryRoute(
				$.parseURL().hash.replace(/^#/, ''),
				route
			);

			routes.push(route);
		}
	})();
	
	$.setHash = function(hash) {
		window.location.hash = hash;
	}
	
	$.getHash = function() {
		return $.parseURL().hash.replace(/^#/, '');
	}

	return $;


})();
