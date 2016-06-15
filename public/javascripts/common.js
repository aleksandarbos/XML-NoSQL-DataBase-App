function clearInputs(button) {
	var inputs = $(button).parents("form").find('input');
	$(inputs).each(function(index) {
		if ($(this).attr("type") != "button" && $(this).attr("type") != "submit" && $(this).attr("name") != "user")
			$(this).val("");
	});
	var inputs = $(button).parents("form").find('select');
	$(inputs).each(function(index) {
		$(this).val("");
	});
	var inputs = $(button).parents("form").find('textarea');
	$(inputs).each(function(index) {
		$(this).val("");
	});
}

$(document).ready(function() {
	$(".alterationToPdf").click(function() {	
		var id = $(this).parents("tr").find(".documentId").text();
		var url = '/overview/topdf?id='+id;
		window.open( url, "_blank");
	});
	$(".alterationPreview").click(function() {		
		var id = $(this).parents("tr").find(".documentId").text();
		var url = '/overview/preview?id='+id;
		window.open( url, "_blank");
	});
})