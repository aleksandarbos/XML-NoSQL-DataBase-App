$(".pregledMenu").show();

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
});