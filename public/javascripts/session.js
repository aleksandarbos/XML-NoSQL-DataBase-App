$(".sednicaMenu").show();

$(document).ready(function(){		
	$(".cancel").click(function(){
		clearInputs(this);
	}); 	
	
	$(".firstVoting").change(function(){
		checkVotes(this);
	});	
	
	$(".votesNumber").blur(function(){
		checkVotes(this);
	});
	
	$(".firstVoting").change(function(){
		var value = $(this).val();
		if (value == "yes") {
			$(this).parents(".votingCard").find('.votingAmandments').slideDown();
			$(this).parents(".votingCard").find('.votingFinal').slideDown();
		} else {
			$(this).parents(".votingCard").find('.votingAmandments').slideUp();
			$(this).parents(".votingCard").find('.votingFinal').slideUp();
		}
	});
	
	$(".acceptRegulation").click(function() {	
		var form = $(this).parents("form");
	
		var id = $(form).find("input[name='regulationId']").val();
		var result = $(form).find("select[name='votingFirstResult']").val();
		var votesYes = $(form).find("input[name='votesFirstNumberYes']").val();
		var votesNo = $(form).find("input[name='votesFirstNumberNo']").val();
		var votesOff =  $(form).find("input[name='votesFirstNumberOff']").val();	
		
		$.ajax({
			type: 'POST',
			url: 'regulation',
			async: 'false',
			dataType: 'text',	
			data: 'regulationId=' + id + "&votingFirstResult=" + result + "&votesFirstNumberYes=" + votesYes + "&votesFirstNumberNo=" + votesNo + '&votesFirstNumberOff=' + votesOff,
			/*success: function(data) {
				
			},
			error: function(data) {
				
			}*/
		})	
	});
	
	$(".acceptAmandment").click(function() {		
		var form = $(this).parents("form");
	
		var id = $(form).find("input[name='amandmentId']").val();
		var result = $(form).find("select[name='votingAmandmentResult']").val();
		var votesYes = $(form).find("input[name='votesAmandmentNumberYes']").val();
		var votesNo = $(form).find("input[name='votesAmandmentNumberNo']").val();
		var votesOff =  $(form).find("input[name='votesAmandmentNumberOff']").val();	
		
		$.ajax({
			type: 'POST',
			url: 'amandment',
			async: 'false',
			dataType: 'text',	
			data: 'amandmentId=' + id + "&votingAmandmentResult=" + result + "&votesAmandmentNumberYes=" + votesYes + "&votesAmandmentNumberNo=" + votesNo + '&votesAmandmentNumberOff=' + votesOff,
			/*success: function(data) {
				
			},
			error: function(data) {
				
			}*/
		})	
	});
	
	$(".acceptRegulationTotal").click(function() {	
		var form = $(this).parents("form");
	
		var id = $(form).find("input[name='regulationFinalId']").val();
		var result = $(form).find("select[name='votingFinalResult']").val();
		var votesYes = $(form).find("input[name='votesFinalNumberYes']").val();
		var votesNo = $(form).find("input[name='votesFinalNumberNo']").val();
		var votesOff =  $(form).find("input[name='votesFinalNumberOff']").val();	
		
		$.ajax({
			type: 'POST',
			url: 'finals',
			async: 'false',
			dataType: 'text',	
			data: 'regulationFinalId=' + id + "&votingFinalResult=" + result + "&votesFinalNumberYes=" + votesYes + "&votesFinalNumberNo=" + votesNo + '&votesFinalNumberOff=' + votesOff,
			/*success: function(data) {
				
			},
			error: function(data) {
				
			}*/
		})	
	});	
});