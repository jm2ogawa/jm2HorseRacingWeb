$(function() {

	$("#id-link-menu").click(function() {
		$('#id-form-menu').attr('action', '/menu');
		$('#id-form-menu').submit();
	});

	$('.read-btn').click(function() {
		var hostUrl= 'http://localhost:8080/data/csvread';
		var rownum = $(this).parent().parent().children('#id-rownum').text();
		var filename = $(this).parent().parent().children('#id-file').text();
		if(filename != undefined){
			$.ajax({
			    url: hostUrl,
				type: 'POST',
				contentType: 'application/json',
				data: filename,
				dataType: "text",
			    timeout : 3000
			}).then(
				function(data) {
					$("#id-message").text(data);
					$('#id-button-row'+rownum+'-read').prop("disabled", true);
				},
				function(data) {
					$("#id-message").text("ajax通信エラー");
			});
		}
    });


});
