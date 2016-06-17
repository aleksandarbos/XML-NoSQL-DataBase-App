$(".sednicaMenu").show();

$(document).ready(function() {	
		
	$(".cancel").click(function() {
		clearInputs(this);
	}); 	
	
	$(".votingResult").change(function() {
		checkVotes(this);
	});	
	
	$(".votesNumber").blur(function() {
		checkVotes(this);
	});
	
	$(".sessionDocumentPreview").click(function() {		
		var id = $(this).parents("form").find(".documentId").val();
		var url = '/overview/preview?id='+id;
		window.open( url, "_blank");
	});
	
	$(".acceptRegulation").click(function() {	
		var form = $(this).parents("form");
	
		var idInput = $(form).find("input[name='regulationId']");
		var resultInput = $(form).find("select[name='votingFirstResult']");
		var votesYesInput = $(form).find("input[name='votesFirstNumberYes']");
		var votesNoInput = $(form).find("input[name='votesFirstNumberNo']");
		var votesOffInput = $(form).find("input[name='votesFirstNumberOff']");
		var votesButton = $(form).find("input[type='button']");
		var votesLabel = $(form).find(".votesLabel");
	
		var id = idInput.val();
		var result = resultInput.val();
		var votesYes = votesYesInput.val();
		var votesNo = votesNoInput.val();
		var votesOff = votesOffInput.val();	
		
		$.ajax({
			type: 'POST',
			url: 'regulation',
			async: 'false',
			dataType: 'text',	
			data: 'regulationId=' + id + "&votingFirstResult=" + result + "&votesFirstNumberYes=" + votesYes + "&votesFirstNumberNo=" + votesNo + '&votesFirstNumberOff=' + votesOff,
			success: function(data) {
				if (result == "yes") {
					$(form).parents(".votingCard").find('.votingAmandments').slideDown();
				}
				resultInput.prop('disabled', true);
				votesLabel.slideUp();
				votesButton.slideUp();
			},
			error: function(data) {
				alert("Unos podataka nije obavljen uspesno. Pokusajte ponovo!");
			}
		})	
	});
	
	$(".acceptAmandment").click(function() {		
		var form = $(this).parents("form");
		var amandmentsLeft = $(form).parents(".votingAmandments").find('.amandmentsLeft').text();
	
		var idInput = $(form).find("input[name='amendmentId']");
		var resultInput = $(form).find("select[name='votingAmandmentResult']");
		var votesYesInput = $(form).find("input[name='votesAmandmentNumberYes']");
		var votesNoInput = $(form).find("input[name='votesAmandmentNumberNo']");
		var votesOffInput = $(form).find("input[name='votesAmandmentNumberOff']");
		var votesButton = $(form).find("input[type='button']");
		var votesLabel = $(form).find(".votesLabel");
	
		var id = idInput.val();
		var result = resultInput.val();
		var votesYes = votesYesInput.val();
		var votesNo = votesNoInput.val();
		var votesOff = votesButton.val();	
		
		$.ajax({
			type: 'POST',
			url: 'amandment',
			async: 'false',
			dataType: 'text',	
			data: 'amendmentId=' + id + "&votingAmandmentResult=" + result + "&votesAmandmentNumberYes=" + votesYes + "&votesAmandmentNumberNo=" + votesNo + '&votesAmandmentNumberOff=' + votesOff,
			success: function(data) {
				if (amandmentsLeft == 1) {
					$(form).parents(".votingCard").find('.votingFinal').slideDown();
				}
				resultInput.prop('disabled', true);
				votesLabel.slideUp();
				votesButton.slideUp();
				$(form).parents(".votingAmandments").find('.amandmentsLeft').text(--amandmentsLeft);			
			},
			error: function(data) {
				alert("Unos podataka nije obavljen uspesno. Pokusajte ponovo!");
			}
		})	
	});
	
	$(".acceptRegulationTotal").click(function() {	
		var form = $(this).parents("form");
	
		var idInput = $(form).find("input[name='regulationFinalId']");
		var resultInput = $(form).find("select[name='votingFinalResult']");
		var votesYesInput = $(form).find("input[name='votesFinalNumberYes']");
		var votesNoInput = $(form).find("input[name='votesFinalNumberNo']");
		var votesOffInput = $(form).find("input[name='votesFinalNumberOff']");
		var votesButton = $(form).find("input[type='button']");
		var votesLabel = $(form).find(".votesLabel");
	
		var id = idInput.val();
		var result = resultInput.val();
		var votesYes = votesYesInput.val();
		var votesNo = votesNoInput.val();
		var votesOff = votesOffInput.val();	
		
		$.ajax({
			type: 'POST',
			url: 'finals',
			async: 'false',
			dataType: 'text',	
			data: 'regulationFinalId=' + id + "&votingFinalResult=" + result + "&votesFinalNumberYes=" + votesYes + "&votesFinalNumberNo=" + votesNo + '&votesFinalNumberOff=' + votesOff,
			success: function(data) {
				resultInput.prop('disabled', true);
				votesLabel.slideUp();
				votesButton.slideUp();
				$(form).parents(".votingCard").find('.votingAmandments').slideUp();	
				$(form).parents(".votingCard").find('.votingRegulationResult').slideUp();	
			},
			error: function(data) {
				alert("Unos podataka nije obavljen uspesno. Pokusajte ponovo!");
			}
		})	
	});	
});

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
		$(inputs[1]).css("border", "1px solid #AAA");
	}
	if (isNaN(value1) || value1 < 0 || (value1 % 1 != 0)) {
		$(inputs[2]).css("border", "1px solid #F00");
		errCount++;
	} else {
		$(inputs[2]).css("border", "1px solid #AAA");
	}
	if (isNaN(value2) || value2 < 0 || (value2 % 1 != 0)) {
		$(inputs[3]).css("border", "1px solid #F00");
		errCount++;
	} else {
		$(inputs[3]).css("border", "1px solid #AAA");
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