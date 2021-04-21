/**
 * jQuery serializeObject
 * 
 * @copyright 2014, macek <paulmacek@gmail.com>
 * @link https://github.com/macek/jquery-serialize-object
 * @license BSD
 * @version 2.5.0
 */
// (function(global, factory) {
//
// // AMD
// if (typeof define === "function" && define.amd) {
// define(["exports", "jquery"], function(exports, $) {
// return factory(exports, $);
// });
// }
//
// // CommonJS
// else if (typeof exports !== "undefined") {
// var $ = require("jquery");
// factory(exports, $);
// }
//
// // Browser
// else {
// console.log(global);
// factory(root, (root.jQuery || root.Zepto || root.ender || root.$));
// }
//
// }(this, function(exports, $) {
(function(global, factory) {

	"use strict";

	if (typeof module === "object" && typeof module.exports === "object") {

		// For CommonJS and CommonJS-like environments where a proper `window`
		// is present, execute the factory and get jQuery.
		// For environments that do not have a `window` with a `document`
		// (such as Node.js), expose a factory as module.exports.
		// This accentuates the need for the creation of a real `window`.
		// e.g. var jQuery = require("jquery")(window);
		// See ticket #14549 for more info.
		module.exports = global.document ? factory(global, true) : function(w) {
			if (!w.document) {
				throw new Error("jQuery requires a window with a document");
			}
			return factory(w);
		};
	} else {
		factory(global);
	}

	// Pass this if window is not defined yet
})
(
		typeof window !== "undefined" ? window : this,
				
function(window, noGlobal) {

	var patterns = {
		validate : /^[a-z_][a-z0-9_]*(?:\.{1}[a-z0-9_]+([\[][0-9]*[\]]){0,1})*$/i, // /^[a-z_][a-z0-9_]*(?:\[(?:\d*|[a-z0-9_]+)\])*$/i,
		key : /[a-z0-9_]+|(?=\[\])/gi,
		// /((?<=\])|(?<=[.]))([a-z0-9_]*)|([a-z0-9_]*)((?=\[)|(?=[.]))/gi,
		// /[a-z0-9_]+|(?=\[[0-9]*\])/gi, 
		// /[a-z0-9_]+|(?=\[\])/gi,
		push : /^$/,
		fixed : /^\d+$/,
		named : /^[a-z0-9_]+$/i
	};

	function FormSerializer(helper, $form) {

		// private variables
		var data = {}, pushes = {};

		// private API
		function build(base, key, value) {
			base[key] = value;
			return base;
		}

		function makeObject(root, value) {

			var keys = root.match(patterns.key), k;

			// nest, nest, ..., nest
			while ((k = keys.pop()) !== undefined) {
				// foo[]
				if (patterns.push.test(k)) {
					var idx = incrementPush(root.replace(/\[\]$/, ''));
					value = build([], idx, value);
				}

				// foo[n]
				else if (patterns.fixed.test(k)) {
					value = build([], k, value);
				}

				// foo; foo[bar]
				else if (patterns.named.test(k)) {
					value = build({}, k, value);
				}
			}

			return value;
		}

		function incrementPush(key) {
			if (pushes[key] === undefined) {
				pushes[key] = 0;
			}

			return pushes[key]++;
		}

		function encode(pair) {
			switch ($('[name="' + pair.name + '"]', $form)
					.attr("type")) {
			case "checkbox":
				return pair.value === "on" ? true : pair.value;
			default:
				return pair.value;
			}
		}

		function addPair(pair) {
			if (!patterns.validate.test(pair.name))
				return this;
			var obj = makeObject(pair.name, encode(pair));
			data = helper.extend(true, data, obj);
			return this;
		}

		function addPairs(pairs) {
			if (!helper.isArray(pairs)) {
				throw new Error(
						"formSerializer.addPairs expects an Array");
			}
			for (var i = 0, len = pairs.length; i < len; i++) {
				this.addPair(pairs[i]);
			}
			return this;
		}

		function serialize() {
			return data;
		}

		function serializeJSON() {
			return JSON.stringify(serialize());
		}

		// public API
		this.addPair = addPair;
		this.addPairs = addPairs;
		this.serialize = serialize;
		this.serializeJSON = serializeJSON;
	}

	FormSerializer.patterns = patterns;

	FormSerializer.serializeObject = function serializeObject() {
		return new FormSerializer($, this).addPairs(
				this.serializeArray()).serialize();
	};

	FormSerializer.serializeJSON = function serializeJSON() {
		return new FormSerializer($, this).addPairs(
				this.serializeArray()).serializeJSON();
	};

	if (typeof $.fn !== "undefined") {
		$.fn.serializeObject = FormSerializer.serializeObject;
		$.fn.serializeJSON = FormSerializer.serializeJSON;
	}

	// exports.FormSerializer = FormSerializer;
	window.FormSerializer = FormSerializer;

	return FormSerializer;
	//}));
});
