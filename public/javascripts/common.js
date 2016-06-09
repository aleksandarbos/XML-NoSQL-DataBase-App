function clearInputs(button) {
	var inputs = $(button).parents("form").find('input');
	$(inputs).each(function(index) {
		if ($(this).attr("type") != "button" && $(this).attr("type") != "submit" )
			$(this).val("");
	});
	var inputs = $(button).parents("form").find('select');
	$(inputs).each(function(index) {
		$(this).val("");
	});
}

function checkVotes(input) {
	var inputs = $(input).parents("div").find('input');
	var value0 = $(inputs[1]).val();
	var value1 = $(inputs[2]).val();
	var value2 = $(inputs[3]).val();
	var errCount = 0;
	
	if (isNaN(value0) || value0 < 0 || (value0 % 1 != 0)) {
		$(inputs[1]).css("border", "1px solid #F00");
		errCount++;
	} else {
		$(inputs[1]).css("border", "1px solid #CCC");
	}
	if (isNaN(value1) || value1 < 0 || (value1 % 1 != 0)) {
		$(inputs[2]).css("border", "1px solid #F00");
		errCount++;
	} else {
		$(inputs[2]).css("border", "1px solid #CCC");
	}
	if (isNaN(value2) || value2 < 0 || (value2 % 1 != 0)) {
		$(inputs[3]).css("border", "1px solid #F00");
		errCount++;
	} else {
		$(inputs[3]).css("border", "1px solid #CCC");
	}
	
	if (errCount > 0 || value0 == "" || value1 == "" || value2 == "") {
		disableSubmit(input)
		return false;
	}
	
	var value0 = parseInt($(inputs[1]).val(),10);
	var value1 = parseInt($(inputs[2]).val(),10);
	var value2 = parseInt($(inputs[3]).val(),10);
	var sum = value0+value1+value2;
	if (sum > 80) {
		alert("Upozorenje! Glasao je veci broj odbornika od moguceg.");
		disableSubmit(input)
		return false;
	}
	
	var decision = $(input).parents("div").find('select').val();
	
	if (decision == "yes" && value1 >= value0) {
		alert("Upozorenje! Odnos glasova odbornika i odluka se ne podudaraju.");
		disableSubmit(input)
		return false;
	}
	else if (decision == "no" && value1 <= value0) {
		alert("Upozorenje! Odnos glasova odbornika i odluka se ne podudaraju.");
		disableSubmit(input)
		return false;
	}
	enableSubmit(input);
	return true;
}

function disableSubmit(input) {
	var submit = $(input).parents("form").find('input[type="button"]');
		$(submit).attr("disabled", true);
}

function enableSubmit(input) {
	var submit = $(input).parents("form").find('input[type="button"]');
		$(submit).attr("disabled", false);
}