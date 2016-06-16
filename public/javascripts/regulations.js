$(".izmeneMenu").show();

$(document).ready(function() {		
	
	$(".cancel").click(function() {
		clearInputs(this);
	});
	
	$(".checkXml").click(function() {
		$(".error1").slideUp();
		$(".error2").slideUp();
		
		var form = $(".checkXml").parents("form");
		var content = $(form).find("textarea").val();
		
		$.ajax({
			type: 'POST',
			url: 'checkDocument',
			async: 'false',
			dataType: 'text',	
			data: 'amendmentContent=' + content,
			success: function(data) {
				if (data == "error1") {
					$(".error1").slideDown();
				} else if (data == "error2") {
					$(".error2").slideDown();
				} else {
					form.submit();
				}
			},
			error: function(data) {
				alert("Unos podataka nije obavljen uspesno. Pokusajte ponovo!");
			}
		});
	});
});