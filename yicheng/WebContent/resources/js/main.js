var SAVE_TYPE_CREATE = 0;
var SAVE_TYPE_UPDATE = 1;

var MATERIAL_TYPE_LEATHER = 0;
var MATERIAL_TYPE_FABRIC = 1;
var MATERIAL_TYPE_SUPPORT = 2;

function checkDouble(str) {
	 var re = /^[0-9]+.?[0-9]*$/;
	 if(re.test(str)) {
	 	return true;
	 }
     return false;
}

function checkInt(str) {
	var re = /^\d+$/;
	if(re.test(str)) {
		return true;
	}
	return false;
}

function checkDateFormat(dateStr) {
	var datePattern = /^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1]))$/;
	return datePattern.test(dateStr);
}

function checkLength(str, minLength, maxLength) {
	if(!str) {
		return false;
	}
	str = str.trim();
	if(str.length < minLength || str.length > maxLength) {
		return false;
	}

	return true;
}