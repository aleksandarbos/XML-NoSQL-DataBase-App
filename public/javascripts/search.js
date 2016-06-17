$(".pretragaMenu").show();

function stateChangedSearch() {
	var value = $(".selectStatus").val();
	$(".statusNominated").hide(); 
	$(".statusVoted").hide(); 
	$(".statusPublished").hide(); 
	$(".statusApplied").hide(); 
	$(".statusWithdrawn").hide(); 
	if (value == "PREDLOZEN") {
		$(".statusNominated").show(); 				
	} else if (value == "PRIHVACEN" || value == "ODBIJEN") {
		$(".statusNominated").show(); 
		$(".statusVoted").show(); 
	} else if (value == "OBJAVLJEN") {
		$(".statusNominated").show(); 
		$(".statusVoted").show(); 
		$(".statusPublished").show(); 
	} else if (value == "U_PRIMENI") {
		$(".statusNominated").show(); 
		$(".statusVoted").show(); 
		$(".statusPublished").show(); 
		$(".statusApplied").show(); 
	} else if (value == "VAN_SNAGE") {
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
		if (stat == "OBJAVLJEN" || stat == "U_PRIMENI" || stat == "VAN_SNAGE")
			$(".selectStatus").val("voted");
	} else {
		$(".hideDate").show();
	}
	stateChangedSearch();
}
function searchChanged() {
	var val = $(".selectSearch").val();
	if (val == "text") {
		$(".textSearchForm").slideDown();
		$(".detailsSearchForm").slideUp();
	} else {
		$(".textSearchForm").slideUp();
		$(".detailsSearchForm").slideDown();
	}
}

function fillDropDowns() {
	
	$("select[name='searchType']").val($(".searchType").text());
	$("select[name='documentType']").val($(".documentType").text());
	$("select[name='documentStatus']").val($(".documentStatus").text());
	$("select[name='documentDomain']").val($(".documentDomain").text());
}

$(document).ready(function() {
	
	fillDropDowns();
	
	searchChanged();	
	stateChangedSearch();
	
	$(".showHideSearch").click(function() {
		var val = $(".selectSearch").val();
		if (val == "text") {
			$(".textSearchForm").slideToggle();
		} else {
			$(".detailsSearchForm").slideToggle();
		}
	});
	
	$(".cancel").click(function() {
		clearInputs(this);
		stateChangedSearch();
		$(".searchResult").remove();
	});
	
	$(".selectSearch").change(function() {
		searchChanged();		
	});
	
	$(".selectStatus").change(function() {
		stateChangedSearch();		
	});
	
	$(".selectType").change(function() {
		typeChangedSearch();		
	});
	
});