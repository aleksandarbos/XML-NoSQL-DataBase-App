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

$(document).ready(function() {
})