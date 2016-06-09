$(".pretragaMenu").show();

function showDateSearch() {
	var value = $(".selectStatus").val();
	$(".statusNominated").hide(); 
	$(".statusAdopted").hide(); 
	$(".statusPublished").hide(); 
	$(".statusApplied").hide(); 
	$(".statusWithdrawn").hide(); 
	if (value == "nominated") {
		$(".statusNominated").show(); 				
	} else if (value == "adopted") {
		$(".statusNominated").show(); 
		$(".statusAdopted").show(); 
	} else if (value == "published") {
		$(".statusNominated").show(); 
		$(".statusAdopted").show(); 
		$(".statusPublished").show(); 
	} else if (value == "applied") {
		$(".statusNominated").show(); 
		$(".statusAdopted").show(); 
		$(".statusPublished").show(); 
		$(".statusApplied").show(); 
	} else if (value == "withdrawn") {
		$(".statusNominated").show(); 
		$(".statusAdopted").show(); 
		$(".statusPublished").show(); 
		$(".statusApplied").show(); 
		$(".statusWithdrawn").show();
	}
}

$(document).ready(function(){

	showDateSearch();

	$(".alterationToPdf").click(function(){
		var id = $(this).parents("tr").find(".documentId").text();
		$(location).attr('href', '/search/topdf?id=' + id);
	});
	$(".alterationPreview").click(function(){
		var id = $(this).parents("tr").find(".documentId").text();
		$(location).attr('href', '/search/preview?id=' + id);
	});
	
	$(".showHideSearch").click(function(){
		$(".searchParameters").slideToggle();
	});
	
	$(".cancel").click(function(){
		clearInputs(this);
		showDateSearch();
	});
	
	$(".selectStatus").change(function(){
		showDateSearch();		
	});
});