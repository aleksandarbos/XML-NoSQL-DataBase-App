$(".izmeneMenu").show();	

$(document).ready(function() {
	
	$(".deleteAlteration").click(function() {
		if (confirm('Opozovi ovaj predlog?')) {
			var id = $(this).parents("tr").find(".documentId").text();
			$(location).attr('href', '/Alterations/delete?id=' + id);
		}
	});
});