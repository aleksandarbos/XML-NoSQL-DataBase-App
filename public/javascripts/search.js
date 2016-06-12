$(".pretragaMenu").show();

function stateChangedSearch() {
	var value = $(".selectStatus").val();
	$(".statusNominated").hide(); 
	$(".statusVoted").hide(); 
	$(".statusPublished").hide(); 
	$(".statusApplied").hide(); 
	$(".statusWithdrawn").hide(); 
	if (value == "nominated") {
		$(".statusNominated").show(); 				
	} else if (value == "voted") {
		$(".statusNominated").show(); 
		$(".statusVoted").show(); 
	} else if (value == "published") {
		$(".statusNominated").show(); 
		$(".statusVoted").show(); 
		$(".statusPublished").show(); 
	} else if (value == "applied") {
		$(".statusNominated").show(); 
		$(".statusVoted").show(); 
		$(".statusPublished").show(); 
		$(".statusApplied").show(); 
	} else if (value == "withdrawn") {
		$(".statusNominated").show(); 
		$(".statusVoted").show(); 
		$(".statusPublished").show(); 
		$(".statusApplied").show(); 
		$(".statusWithdrawn").show();
	}
}
function typeChangedSearch() {
	var type = $(".selectType").val();
	var stat = $(".selectStatus").val();
	if (type == "regulation") {
		$(".hideDate").show();
	} else if (type == "amendment") {
		$(".hideDate").hide();
		if (stat == "published" || stat == "applied" || stat == "withdrawn")
			$(".selectStatus").val("voted");
	} else {
		$(".hideDate").show();
	}
	stateChangedSearch();
}

$(document).ready(function() {
	
	stateChangedSearch();

	$(".alterationToPdf").click(function() {
		var id = $(this).parents("tr").find(".documentId").text();
		$(location).attr('href', '/search/topdf?id=' + id);
	});
	$(".alterationPreview").click(function() {
		var id = $(this).parents("tr").find(".documentId").text();
		$(location).attr('href', '/search/preview?id=' + id);
	});
	
	$(".showHideSearch").click(function() {
		$(".searchParameters").slideToggle();
	});
	
	$(".cancel").click(function() {
		clearInputs(this);
		stateChangedSearch();
	});
	
	$(".selectStatus").change(function() {
		stateChangedSearch();		
	});
	
	$(".selectType").change(function() {
		typeChangedSearch();		
	});
});