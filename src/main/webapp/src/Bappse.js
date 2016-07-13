var Bappse = (function() {

	var $ = {};
	
	$.assert = function(flag, message) {
		if(!flag) {
			message = message || "Assertion failed";
			throw (typeof Error !== 'undefined') ? new Error(message) : message;
		}
	}
	
	$.assertType = function(flag, message) {
		if(!flag) {
			message = message || "Assertion failed";
			throw (typeof TypeError !== 'undefined') ? new TypeError(message) : message;
		}
	}

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
	
	//from https://developer.mozilla.org/es/docs/Web/JavaScript/Referencia/Objetos_globales/Number/isNaN
	$.isNaN = Number.isNaN || function(value) {
	    return typeof value === "number" && isNaN(value);
	}

	$.isDate = function(value) {
		return value instanceof Date && !isNaN(value.valueOf())  && ("undefined" !== typeof value.getDate); ;
	}
	
	$.equal = function(o1, o2) {
		
		//same object
		if(o1 === o2) {
			return true;
		}

		//NaN
		if ($.isNaN(o1) && $.isNaN(o2)) {
	         return true;
	    }

		//string
		if(($.isString(o1) && $.isString(o2))
			|| ($.isDate(o1) && $.isDate(o2))
			|| ($.isFunction(o1) && $.isFunction(o2))
			|| ($.isNumber(o1) && $.isNumber(o2))) {
			return o1.toString() === o2.toString();
		}
		
		//regexp
		if($.isRegex(o1) && $.isRegex(o2)) {
			return ((o1.toString() === o2.toString() ||
				(o1.source === o2.source) && 
				(o1.global === o2.global) && 
				(o1.ignoreCase === o2.ignoreCase) &&
				(o1.multiline === o2.multiline)));
		}

		//array
		if($.isArray(o1) && $.isArray(o2)) {
			if(o1.length != o2.length) {
				return false;
			}

			for(var i=0; i<o1.length; i++) {
				if(!$.equal(o1[i], o2[i])) {
					return false;
				}
			}

			return true;
		}

		//type
		if(typeof o1 != typeof o2) {
			return false;
		}

		//object
		if (!(o1 instanceof Object && o2 instanceof Object)
			|| (o1.constructor !== o2.constructor)
			|| (o1.isPrototypeOf(o2) || o2.isPrototypeOf(o1))) {
	        return false;
	    }

		//keys
		if(!$.equal(Object.keys(o1), Object.keys(o2))) {
			return false;
		}
		
		//values
		for(key in o1) {
			if(!$.equal(o1[key], o2[key])) {
				return false;
			}
		}
		
		return true;
	}

	$.each = function(object, fn) {
		$.assertType($.isFunction(fn), 'fn must be a Function');
		if($.isArray(object)) {
			for(var i=0; i<object.length; i++) {
				if(fn.call(object[i], i, object[i]) === false) {
					break;
				}
			}
		} else {
			for(key in object) {
				if(fn.call(object[key], key, object[key]) === false) {
					break;
				}
			}
		}
	}

	$.map = function(object, fn, nullable) {
		
		$.assertType($.isFunction(fn), 'fn must be a Function');

		var map = [], i = 0;

		$.each(object, function(key, value) {
			var result = fn.call(value, key, value);
			if(nullable === true || result !== null) {
				map.push(result);
			}
		});
		
		return map;
	}

	$.toArray = function(object) {

		var map = [], i = 0;

		$.each(object, function(key, value) {
			map.push(value);
		});
		
		return map;
	}

	$.filter = function(object, fn) {
		
		$.assertType($.isFunction(fn), 'fn must be a Function');

		var map = $.isArray(object) ? [] : {}, i = 0;

		$.each(object, function(key, value) {
			if(fn.call(value, key, value)) {
				map[key] = value;
			}
		});
		
		return map;
	}

	$.every = function(object, fn) {
		$.assertType($.isFunction(fn), 'fn must be a Function');

		if($.isArray(object)) {
			for(var i=0; i<object.length; i++) {
				if(!fn.call(object[i], i, object[i])) {
					return false;
				}
			}
		} else {
			for(key in object) {
				if(!fn.call(object[key], key, object[key])) {
					return false;
				}
			}			
		}

		return true;
	}

	$.some = function(object, fn) {
		$.assertType($.isFunction(fn), 'fn must be a Function');

		if($.isArray(object)) {
			for(var i=0; i<object.length; i++) {
				if(fn.call(object[i], i, object[i])) {
					return true;
				}
			}
		} else {
			for(key in object) {
				if(fn.call(object[key], key, object[key])) {
					return true;
				}
			}			
		}
		
		return false;
	}

	$.isIn = function(object, value) {
		return $.some(object, function(k, v) {
			return $.equal(v, value);
		});
	}

	$.merge = function() {
		arguments = Array.prototype.slice.call(arguments);

		//if all arguments are arrays
		if($.every(arguments, function(key, value){
			return $.isArray(value);
		})) {
			return Array.prototype.concat.apply([], arguments);
		}
		
	    var object = {}, i = 0, o;
	    $.each(arguments, function(_, argument) {
	    	$.each(argument, function(key, value) {
	    		object[key] = value;
	    	});
	    });
	    return object;
	}
	
	$.clone = function(object) {
		if($.isArray(object)) {
			return Array.prototype.slice.call(object);
		} else {
			newObject = {}
			for(key in object) {
				newObject[key] = object[key];
			}
			return newObject;
		}
	}

	//http://stackoverflow.com/questions/7376598/in-javascript-how-do-i-check-if-an-array-has-duplicate-values
	$.hasDuplicates = function(array) {
		$.assertType($.isArray(array), 'array must be an array');
	    return (new Set(array)).size !== array.length;
	}

	if(!Set) {
		$.hasDuplicates = function(array) {
			$.assertType($.isArray(array), 'array must be an array');

		    var values = [], i = 0;
		    for (; i < array.length; ++i) {
		        var value = array[i];
		        if (values.indexOf(value) !== -1) {
		            return true;
		        }
		        values.push(value);
		    }

		    return false;
		}
	}

	$.removeChilds = function(element) {
		$.assertType($.isElement(element), 'element must be a DOM Element');
		while (element.firstChild) {
		   element.removeChild(element.firstChild);
		}
	}
	
	$.range = function(start, stop, step) {
		var step = step || 1, array = [], x = start;

		$.assertType($.isNumber(start), 'start must be a number');
		$.assertType($.isNumber(stop), 'stop must be a number');
		$.assertType($.isNumber(step), 'step must be a number');
		
		$.assert((stop > start && step > 0) || (stop < start && step < 0),
			'if step > 0 => stop > start; if step < 0 => stop < start'
		);
	
		var i = start;
		
		if(step>0) { 
			while(i<stop) {
				array.push(i);
				i += step;
			}
		} else { 
			while(i>stop) {
				array.push(i);
				i += step;
			}			
		}

		return array;
	}

	$.getFormData = function(form) {
		var control, name, data = {}, i = 0;
		
		$.assertType($.isElement(form) && form.tagName.toLowerCase() == 'form', 'form must be a DOM form');

		for (; i<form.elements.length; i++) {
			control = form.elements[i];
			name = control.getAttribute('name');
			if(name != null) {
				
				if(control.tagName.toLowerCase() == 'select' && control.hasAttribute('multiple')) {
					data[name] = $.toArray($.map(control.options, function(_, option) {
						return option.selected ? option.value : null;
					}));
				} else {
					data[name] = control.value;
				}
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

		url = url || window.location.href;
		$.assertType($.isString(url), 'url must be string type');

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
	
	//https://developer.mozilla.org/es/docs/Web/JavaScript/Referencia/Objetos_globales/Object/create#See_also
	$.create = function (object) {
		var F = function () {};
		if (arguments.length > 1) {
			throw Error('Second argument not supported');
        }
        
		if (object === null) { 
            throw Error('Cannot set a null [[Prototype]]');
        }
          
        if (typeof object != 'object') { 
        	throw TypeError('Argument must be an object');
        }
        
        F.prototype = object;
        return new F();
	};
	
	$.inherits = function(parentClass) {
		var subClass = function() {
			parentClass.call(this);
		}
		subClass.prototype = $.create(parentClass.prototype);
		return subClass;

		//Another way
		var subClass = function(){}
		subClass.prototype = new parentClass();
		return subClass;
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

	var Ajax = function(method, url) {
		
		var response = null, $response = null;
		var xhr = $.getXHR();
		var pre = null;
		var handlers = {
			include: [],
			exclude: [],
			error: []
		}

		var config = {
			data: null,
			queryParams: '',
			timeout: 0,
			headers: {
				'content-type': 'application/x-www-form-urlencoded;charset=UTF-8',
				'x-requested-with': 'XMLHttpRequest'	
			}
		};


		$.assertType($.isString(method), 'method must be string type');
		method = method.toUpperCase();
		$.assert($.isIn(['GET', 'POST', 'PUT', 'DELETE'], method), 'Method not supported');
		$.assertType($.isString(url), 'url must be string type');
		$.assert(!!xhr, 'Browser does not support ajax');

		var getResponse = function(xhr) {
			return {
				text: xhr.responseText,
				xml: xhr.responseXML,
				json: function() {
					try {
						return JSON.parse(xhr.responseText);
					} catch(e) {
						return null;
					}
	 			},
				code: xhr.status
			}
		};

		this.error = function(callback) {
			$.assertType($.isFunction(callback), 'callback must be a Function');
			handlers.error.push(callback);
			return this;
		}

		this.pre = function(fn) {
			$.assertType($.isFunction(fn), 'fn must be a Function');
			pre = fn;
			return this;
		}
		
		this.success = function() {
			arguments = Array.prototype.slice.call(arguments);
			$.assert(arguments.length > 0, 'this function must be have at least one parameter');

			var callback = arguments.pop();
			$.assertType($.isFunction(callback), 'last parameter must be a Function');

			if(arguments.length == 0) {
				handlers.exclude.push({
					codes: [], 
					callback: callback
				});
				
				if(response) {
					callback.call(callback, $response);
				}
				
			} else {
				var include = [], exclude = [], code;
				$.each(arguments, function(_, code) {
					$.assertType($.isInteger(code), 'Invalid code: ' + code + '. code must be an Integer');
					
					if(code < 0) {
						exclude.push(code * -1);
					} else {
						include.push(code);
					}
				});

				if(exclude.length > 0) {
					handlers.exclude.push({
						codes: exclude, 
						callback: callback
					});
					
					if(response && !$.isIn(exclude, response.code)) {
						callback.call(callback, $response);
					}
				} else {
					handlers.include.push({
						codes: include, 
						callback: callback
					});
					
					if(response && $.isIn(include, response.code)) {
						callback.call(callback, $response);
					}						
				}
			}
			
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
			$.assertType($.isInteger(timeout), 'timeout must be an Integer');
			config.timeout = timeout;
			return this;
		}

		this.header = function(name, value) {
			
			$.assertType($.isString(name), 'name must be string type');
			$.assertType($.isString(value) || value === null, 'value must be string type');

			name = name.toLowerCase();
			config.headers[name] = value;
			if(value === null) {
				delete config.headers[name];
			}
			
			return this;
		}

		this.ok = function() {

			//this method can be executed once
			if(response) {
				return this;
			}


			//Open connection
			xhr.open(method, url + config.queryParams, true);

			//Set timeout
			xhr.timeout = config.timeout;

			//Set Headers
			for(key in config.headers) {
				xhr.setRequestHeader(key, config.headers[key]);	
			}
			
			//handle error
			xhr.onerror = function(error) {
				$.each(handlers.error, function(_, fn) {
					fn.call(fn, error);
				});
			}

			//Set callbacks
			xhr.onreadystatechange = function () {
				if (xhr.readyState != 4) return;

				var r = getResponse(xhr);
				$response = !pre ? r : pre(r);

				$.each(handlers.include, function(_, handler) {
					if($.isIn(handler.codes, r.code)) {
						handler.callback.call(handler.callback, $response);
					}
				});

				$.each(handlers.exclude, function(_, handler) {
					if(!$.isIn(handler.codes, r.code)) {
						handler.callback.call(handler.callback, $response);
					}
				});
			
				response = r;
			}
			
			//Send data
			if(config.headers['content-type'].toLowerCase() == 'application/json') {
				xhr.send(JSON.stringify(config.data));	
			} else {
				xhr.send(config.data);
			}
			
			return this;
		}
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

	var AjaxQueue = function(args) {
		
		var queue = [];
		var error = false;
		var handlers = {
			success: [],
			error: []
		}

		$.each(args, function(index, ajax) {

			$.assertType(ajax instanceof Ajax, 'All parameters must be Ajax type');

			ajax.success(function(response) {
				addResponse(index, response, false);
			});

			ajax.error(function(error) {
				addResponse(index, error, true);
			});
		});

		var addResponse = function(index, response, $error) {

			error = $error || error;
			queue[index] = response;
			
			if(args.length == queue.length) {				
				$.each(error ? handlers.error : handlers.success, function(_, callback) {
					callback.apply(callback, queue);
				});
			}
		}

		this.success = function(callback) {
			$.assertType($.isFunction(callback), 'fn must be a Function');
			handlers.success.push(callback);
			if(args.length == queue.length && !error) {
				callback.apply(callback, queue);
			}
			return this;
		}

		this.error = function(callback) {
			$.assertType($.isFunction(callback), 'fn must be a Function');
			handlers.error.push(callback);
			
			if(args.length == queue.length && error) {
				callback.apply(callback, queue);
			}
			return this;
		}

		this.ok = function() {
			
			if(args.length == queue.length) {
				return this;
			}
			
			$.each(args, function(_, ajax) {
				ajax.ok();
			});
			
			return this;
		}
	}
	
	$.AjaxQueue = function() {
		return new AjaxQueue([].slice.call(arguments));
	}

	$.addEvent = function(target, type, callback) {
		
		$.assertType($.isElement(target) || target === window, 'target must be a DOM element');
		$.assertType($.isString(type), 'type must be string type');
		$.assertType($.isFunction(callback), 'callback must be a function');

		target.addEventListener(type, callback);		
	}

	//for <IE9
	if(!document.addEventListener) {
		
		$.assertType($.isElement(target) || target != window, 'target must be a DOM element');
		$.assertType($.isString(type), 'type must be string type');
		$.assertType($.isFunction(callback), 'callback must be a function');

		$.addEvent = function(target, type, callback) {
			target.attachEvent('on'+type, function() {
				//TODO fix this
				window.event.target = window.event.srcElement;
				callback.call(callback, window.event);
			});
		}
	}

	$.getHash = function(){
		return window.location.hash.replace(/^#/, '');
	}
	
	$.setHash = function(hash) {
		window.location.hash = hash;
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
		
		return function(a, b) {
			
			if($.isRegex(a) && $.isFunction(b)) {

				var route = {
						pattern: a,
						callback: b
				}

				tryRoute(
					$.parseURL().hash.replace(/^#/, ''),
					route
				);

				routes.push(route);
				
			} else if($.isString(a)){
				window.location.hash = hash;				
			} else if(arguments.length == 0) {
				route();
			}


		}
	})();

	return $;
})();

var $ = Bappse;
var testTrue = function(flag, message) {
	if(!flag) {
		console.log(message);
	}
}

var testFalse = function(flag, message) {
	if(flag) {
		console.log(message);
	}
}

/*
//test assert
Bappse.assert(true, 'Fail: assert(true)');
Bappse.assert(false, 'OK: assert(false)');

//test assertType
Bappse.assertType(true, 'Fail: assertType(true)');
Bappse.assertType(false, 'OK: assertType(false)');
*/


//test isInteger
testTrue($.isInteger(5), 'Fail: isInteger(5)');
testFalse($.isInteger(5.3), 'Fail: isInteger(5.3)');
testFalse($.isInteger('5'), 'Fail: isInteger("5")');
testFalse($.isInteger([5]), 'Fail: isInteger([5])');
testFalse($.isInteger({}), 'Fail: isInteger({})');
testFalse($.isInteger(function(){}), 'Fail: isInteger(function(){})');
testFalse($.isInteger(true), 'Fail: isInteger(true)');
testFalse($.isInteger(/.*/), 'Fail: isInteger(/.*/)');
testFalse($.isInteger(document), 'Fail: isInteger(document)');
testFalse($.isInteger(window), 'Fail: isInteger(window)');
testFalse($.isInteger(document.createElement('a')), 'Fail: isInteger(document.createElement("a"))');

//test isNumber
testTrue($.isNumber(5), 'Fail: isNumber(5)');
testTrue($.isNumber(5.3), 'Fail: isNumber(5.3)');
testFalse($.isNumber('5'), 'Fail: isNumber("5")');
testFalse($.isNumber([5]), 'Fail: isNumber([5])');
testFalse($.isNumber({}), 'Fail: isNumber({})');
testFalse($.isNumber(function(){}), 'Fail: isNumber(function(){})');
testFalse($.isNumber(true), 'Fail: isNumber(true)');
testFalse($.isNumber(/.*/), 'Fail: isNumber(/.*/)');
testFalse($.isNumber(document), 'Fail: isNumber(document)');
testFalse($.isNumber(window), 'Fail: isNumber(window)');
testFalse($.isNumber(document.createElement('a')), 'Fail: isNumber(document.createElement("a"))');

//test isFunction
testTrue($.isFunction(function(){}), 'Fail: isFunction(function(){})');
testFalse($.isFunction(5), 'Fail: isFunction(5)');
testFalse($.isFunction(5.3), 'Fail: isFunction(5.3)');
testFalse($.isFunction('5'), 'Fail: isFunction("5")');
testFalse($.isFunction([5]), 'Fail: isFunction([5])');
testFalse($.isFunction({}), 'Fail: isFunction({})');
testFalse($.isFunction(true), 'Fail: isFunction(true)');
testFalse($.isFunction(/.*/), 'Fail: isFunction(/.*/)');
testFalse($.isFunction(document), 'Fail: isFunction(document)');
testFalse($.isFunction(window), 'Fail: isFunction(window)');
testFalse($.isFunction(document.createElement('a')), 'Fail: isFunction(document.createElement("a"))');

//test isString
testTrue($.isString('5'), 'Fail: isString("5")');
testTrue($.isString(''), 'Fail: isString("")');
testFalse($.isString([]), 'Fail: isString([])');
testFalse($.isString([5]), 'Fail: isString([5])');
testFalse($.isString(5), 'Fail: isString(5)');
testFalse($.isString(5.3), 'Fail: isString(5.3)');
testFalse($.isString({}), 'Fail: isString({})');
testFalse($.isString(function(){}), 'Fail: isString(function(){})');
testFalse($.isString(true), 'Fail: isString(true)');
testFalse($.isString(/.*/), 'Fail: isString(/.*/)');
testFalse($.isString(document), 'Fail: isString(document)');
testFalse($.isString(window), 'Fail: isString(window)');
testFalse($.isString(document.createElement('a')), 'Fail: isString(document.createElement("a"))');

//test isArray
testTrue($.isArray([]), 'Fail: isArray([])');
testTrue($.isArray([5]), 'Fail: isArray([5])');
testFalse($.isArray(5), 'Fail: isArray(5)');
testFalse($.isArray(5.3), 'Fail: isArray(5.3)');
testFalse($.isArray('5'), 'Fail: isArray("5")');
testFalse($.isArray({}), 'Fail: isArray({})');
testFalse($.isArray(function(){}), 'Fail: isArray(function(){})');
testFalse($.isArray(true), 'Fail: isArray(true)');
testFalse($.isArray(/.*/), 'Fail: isArray(/.*/)');
testFalse($.isArray(document), 'Fail: isArray(document)');
testFalse($.isArray(window), 'Fail: isArray(window)');
testFalse($.isArray(document.createElement('a')), 'Fail: isArray(document.createElement("a"))');

//test isRegex
testTrue($.isRegex(/.*/), 'Fail: isRegex(/.*/)');
testFalse($.isRegex([]), 'Fail: isRegex([])');
testFalse($.isRegex([5]), 'Fail: isRegex([5])');
testFalse($.isRegex(5), 'Fail: isRegex(5)');
testFalse($.isRegex(5.3), 'Fail: isRegex(5.3)');
testFalse($.isRegex('5'), 'Fail: isRegex("5")');
testFalse($.isRegex({}), 'Fail: isRegex({})');
testFalse($.isRegex(function(){}), 'Fail: isRegex(function(){})');
testFalse($.isRegex(true), 'Fail: isRegex(true)');
testFalse($.isRegex(document), 'Fail: isRegex(document)');
testFalse($.isRegex(window), 'Fail: isRegex(window)');
testFalse($.isRegex(document.createElement('a')), 'Fail: isRegex(document.createElement("a"))');

//test isNode
testTrue($.isNode(document), 'Fail: isNode(document)');
testTrue($.isNode(document.createElement('a')), 'Fail: isNode(document.createElement("a"))');
testFalse($.isNode('5'), 'Fail: isNode("5")');
testFalse($.isNode(''), 'Fail: isNode("")');
testFalse($.isNode([]), 'Fail: isNode([])');
testFalse($.isNode([5]), 'Fail: isNode([5])');
testFalse($.isNode(5), 'Fail: isNode(5)');
testFalse($.isNode(5.3), 'Fail: isNode(5.3)');
testFalse($.isNode({}), 'Fail: isNode({})');
testFalse($.isNode(function(){}), 'Fail: isNode(function(){})');
testFalse($.isNode(true), 'Fail: isNode(true)');
testFalse($.isNode(/.*/), 'Fail: isNode(/.*/)');
testFalse($.isNode(window), 'Fail: isNode(window)');

//test isElement
testTrue($.isElement(document.createElement('a')), 'Fail: isElement(document.createElement("a"))');
testFalse($.isElement('5'), 'Fail: isElement("5")');
testFalse($.isElement(''), 'Fail: isElement("")');
testFalse($.isElement([]), 'Fail: isElement([])');
testFalse($.isElement([5]), 'Fail: isElement([5])');
testFalse($.isElement(5), 'Fail: isElement(5)');
testFalse($.isElement(5.3), 'Fail: isElement(5.3)');
testFalse($.isElement({}), 'Fail: isElement({})');
testFalse($.isElement(function(){}), 'Fail: isElement(function(){})');
testFalse($.isElement(true), 'Fail: isElement(true)');
testFalse($.isElement(/.*/), 'Fail: isElement(/.*/)');
testFalse($.isElement(document), 'Fail: isElement(document)');
testFalse($.isElement(window), 'Fail: isElement(window)');

//test isNaN
testFalse($.isNaN(123));
testFalse($.isNaN(-1.23));
testFalse($.isNaN(5-2));
testFalse($.isNaN(0));
testFalse($.isNaN('123'));
testFalse($.isNaN('Hello'));
testFalse($.isNaN('2005/12/12'));
testFalse($.isNaN(''));
testFalse($.isNaN(true));
testFalse($.isNaN(undefined));
testFalse($.isNaN('NaN'));
testTrue($.isNaN(NaN));
testTrue($.isNaN(0 / 0));

//test equal
testTrue($.equal(document.createElement('a'), document.createElement('a')), 'Fail: equal(document.createElement("a"), document.createElement("a"))');
testTrue($.equal('5', '5'), 'Fail: equal("5", "5")');
testTrue($.equal('', ''), 'Fail: equal("", "")');
testTrue($.equal([], []), 'Fail: equal([], [])');
testTrue($.equal([5], [5]), 'Fail: equal([5], [5])');
testTrue($.equal([5, 3, 5], [5, 3, 5]), 'Fail: equal([5, 3, 5], [5, 3, 5])');
testTrue($.equal(5, 5), 'Fail: equal(5, 5)');
testTrue($.equal(5.3, 5.3), 'Fail: equal(5.3, 5.3)');
testTrue($.equal({}, {}), 'Fail: equal({}, {})');
testTrue($.equal({a: 2, b:3}, {a: 2, b:3}), 'Fail: equal({a: 2, b:3}, {a: 2, b:3})');
testFalse($.equal({a: 2, b:3}, {a: 2, j:3}), 'Fail: equal({a: 2, b:3}, {a: 2, b:3})');
testFalse($.equal({a: 2, b:3}, {a: 2, b:4}), 'Fail: equal({a: 2, b:3}, {a: 2, b:4})');
testTrue($.equal(function(){}, function(){}), 'Fail: equal(function(){}, function(){})');
testTrue($.equal(true, true), 'Fail: equal(true, true)');
testTrue($.equal(/.*/ig, /.*/gi), 'Fail: equal(/.*/, /.*/)');
testTrue($.equal(document, document), 'Fail: equal(document, document)');
testTrue($.equal(window, window), 'Fail: equal(window, window)');

//test Date
testTrue($.isDate(new Date()), 'Fail: isDate(new Date())');

//test each
testTrue(function() {
	var a = [];
	$.each([1, 2, 3], function(k, v) {
		a.push(v);
	});
	return $.equal(a, [1, 2, 3]);
}(), 'Fail: each');

testTrue(function() {
	var a = [];
	$.each([1, 2, 3, 4, 5], function(k, v) {
		a.push(v);
		if(v == 3) {
			return false;
		}
	});
	return $.equal(a, [1, 2, 3]);
}(), 'Fail: each');

testTrue(function() {
	var a = {};
	$.each({a:1, b:2, c:3}, function(k, v) {
		a[k] = v;
	});
	return $.equal(a, {a:1, b:2, c:3});
}(), 'Fail: each');

testTrue(function() {
	var a = {};
	$.each({a:1, b:2, c:3, d:4, e:5}, function(k, v) {
		a[k] = v;
		if(v == 3) {
			return false;
		}
	});
	return $.equal(a, {a:1, b:2, c:3});
}(), 'Fail: each');

//test map
testTrue($.equal($.map([1,2,3], function(k, v) { return v; }), [1, 2, 3]), 'Fail: map');
testTrue($.equal($.map({a:1, b:2, c:3}, function(k, v) { return v; }), [1, 2, 3]), 'Fail: map');

//test every
testTrue($.every([1, 2, 3], function(k, v) { return v > 0; }), 'Fail: every');
testTrue($.every({a:1, b:2, c:3}, function(k, v) { return v > 0; }), 'Fail: every');
testFalse($.every([1, 2, 3], function(k, v) { return v > 1; }), 'Fail: every');
testFalse($.every({a:1, b:2, c:3}, function(k, v) { return v > 1; }), 'Fail: every');

//test some
testTrue($.some([1, 2, 3], function(k, v) { return v > 0; }), 'Fail: some');
testTrue($.some({a:1, b:2, c:3}, function(k, v) { return v > 0; }), 'Fail: some');
testTrue($.some([1, 2, 3], function(k, v) { return v > 1; }), 'Fail: some');
testTrue($.some({a:1, b:2, c:3}, function(k, v) { return v > 1; }), 'Fail: some');
testFalse($.some([1, 2, 3], function(k, v) { return v < 1; }), 'Fail: some');
testFalse($.some({a:1, b:2, c:3}, function(k, v) { return v < 1; }), 'Fail: some');

//test isIn
testTrue($.isIn([1, 2, 3], 2), 'Fail: isIn([1, 2, 3], 2)');
testTrue($.isIn({a:1, b:2, c:3}, 2), 'Fail: isIn({a:1, b:2, c:3}, 2)');
testFalse($.isIn([1, 2, 3], 5), 'Fail: isIn([1, 2, 3], 5)');
testFalse($.isIn({a:1, b:2, c:3}, 5), 'Fail: isIn({a:1, b:2, c:3}, 5)');

//test merge
testTrue($.equal($.merge([1, 2, 3], [1, 2, 3]), [1, 2, 3, 1, 2, 3]), 'Fail: merge([1, 2, 3], [1, 2, 3])');
testTrue($.equal($.merge({a:1, b:2, c:3}, {c:1, d:2, e:3}), {a:1, b:2, c:1, d:2, e:3}), 'Fail: merge({a:1, b:2, c:3}, {c:1, d:2, e:3})');
testFalse($.equal($.merge([1, 2, 3], [1, 2, 3]), [1, 2, 3, 1, 2, 5]), 'Fail: merge([1, 2, 3], [1, 2, 3]), [1, 2, 3, 1, 2, 5])');
testFalse($.equal($.merge({a:1, b:2, c:3}, {c:1, d:2, e:3}), {a:1, b:2, c:1, d:2, e:2}), 'Fail: merge({a:1, b:2, c:3}, {c:1, d:2, e:3})');
testTrue($.equal($.merge([1, 2, 3], {a: 1, b:4}), {0:1, 1:2, 2:3, a: 1, b:4}), 'Fail: merge($.merge([1, 2, 3], {a: 1, b:4}), {0:1, 1:2, 2:3, a: 1, b:4})');
testFalse($.equal($.merge([1, 2, 3], {a: 1, b:4}), {0:1, 1:2, 2:3, a: 1, c:4}), 'Fail: merge($.merge([1, 2, 3], {a: 1, b:4}), {0:1, 1:2, 2:3, a: 1, c:4})');

//test clone
testTrue($.equal($.clone({a:1, b:2}), {a:1, b:2}), 'Fail: clone({a:1, b:2})');
testTrue($.equal($.clone([1, 2, 3]), [1, 2, 3]), 'Fail: clone([1, 2, 3])');
testFalse($.clone({a:1, b:2}) == {a:1, b:2}, 'Fail: clone({a:1, b:2})');
testFalse($.clone([1, 2, 3]) == [1, 2, 3], 'Fail: clone([1, 2, 3])');

//test hasDuplicates
testFalse($.hasDuplicates([1, 2, 3, 4]), '$.hasDuplicates([1, 2, 3, 4])');
testTrue($.hasDuplicates([1, 2, 3, 4, 3]), '$.hasDuplicates([1, 2, 3, 4, 3])');

//TODO test removeChilds

//test range
testTrue($.equal($.range(1, 5, 1), [1, 2, 3, 4]), 'Fail: range(1, 5, 1)');
testTrue($.equal($.range(1, 5.1, 1), [1, 2, 3, 4, 5]), 'Fail: range(1, 5.1, 1)');
//TODO testTrue($.equal($.range(1, 5, 1.1), [1, 2.1, 3.2, 4.3, 5.5]), 'Fail: range(1, 5, 1.1)');
//TODO testTrue($.equal($.range(1, 5.6, 1.1), [1, 2.1, 3.2, 4.3, 5.5]), 'Fail: range(1, 5.6, 1.1)');

//TODO test $.getFormData

//TODO test $.toQueryParams

//TODO test $.parseURL

//test inherits
testTrue(function() {
	var A = function() {
		this.sayHello = function() {
			return 'Hello';
		}
	}
	
	var B = $.inherits(A);

	var c = new B();

	return $.every([c instanceof A, c instanceof B, c.sayHello() === 'Hello'], function(k, v) { return v; });
}(), 'Fail: inherits');
