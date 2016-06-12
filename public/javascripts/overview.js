$(".pregledMenu").show();

$(document).ready(function() {
	
	$(".alterationToPdf").click(function() {
		var id = $(this).parents("tr").find(".documentId").text();
		$(location).attr('href', '/overview/topdf?id=' + id);
	});
	$(".alterationPreview").click(function() {
		var id = $(this).parents("tr").find(".documentId").text();
		$(location).attr('href', '/overview/preview?id=' + id);
	});
});