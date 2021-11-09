$(function() {

	//メニューリンク
	$('#id-link-menu').click(function() {
		$('#id-form-menu').attr('action', './../menu');
		$('#id-form-menu').submit();
	});
	//プルダウン操作時
	$('#id-pulldown-acd').change(function() {
		// 選択されているvalue属性値を取り出す
		var val = $('#id-pulldown-acd').val();
		var row = $('#id-hidden-prog-row').val();
		if(val=='CODE_SCRATCH'){
			$('#id-message').text('競争除外の人気番号を入力してください');

		}
		if(val=='CODE_SAME'){
			$('#id-message').text('かぶった人気のうち対象外になるオッズが高い番号を入力してください');
		}
	});
	//各行の登録ボタン
	$('.register-btn').click(function() {

		var hostUrl= 'http://localhost:8080/data/register';
		var row = $(this).parent().parent().children('#id-rownum').text();
		var horse = $(this).parent().parent().children('#id-horse').text();
		var place = $(this).parent().parent().children('#id-place').text();
		var no = $(this).parent().parent().children('#id-no').text();
		var raceday = $(this).parent().parent().children('#id-raceday').text();
		var param1 = $(this).parent().parent().children().find('#id-input-row'+row+'-val1').val();
		var param2 = $(this).parent().parent().children().find('#id-input-row'+row+'-val2').val();
		var param3 = $(this).parent().parent().children().find('#id-input-row'+row+'-val3').val();

		var acdCd = $('#id-pulldown-acd').val();
		$(this).parent().parent().children().find('#id-hidden-acd'+row+'-cd').val(acdCd)
		var acdVal = $(this).parent().parent().children().find('#id-input-row'+row+'-acd').val();
		$(this).parent().parent().children().find('#id-hidden-acd'+row+'-val').val(acdVal)

		var nextrow = parseInt(row)+1;

		var requestForm = {
				official : {
					winner : param1,
					runnerUp : param2,
					thirdPlace : param3
				},
				rownum : row,
				horse : horse,
				place : place,
				no : no,
				raceday : raceday,
				acdPullDownCd : acdCd,
				acdPullDownVal : acdVal
	    };
		console.log(requestForm);
		if(param1 != undefined
			 && param2 != undefined
			 && param3 != undefined){

			$.ajax({
			    url: hostUrl,
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(requestForm),
				dataType: "text"
			}).then(
				function(data) {
					//サーバから返却されたメッセージを表示する
					$("#id-message").text(data);
					//登録した行の活性/非活性のコントロール
					$('#id-input-row'+row+'-val1').prop("readonly", true);
					$('#id-input-row'+row+'-val2').prop("readonly", true);
					$('#id-input-row'+row+'-val3').prop("readonly", true);
					$('#id-button-row'+row+'-register').prop('disabled', true);
					$('#id-button-row'+row+'-fix').prop('disabled', false);
					$('#id-button-row'+row+'-react').prop('disabled', false);
					$('#id-input-row'+row+'-acd').prop('readonly', true);
					//次の行を活性化するコントロール
					$('#id-input-row'+nextrow+'-val1').prop("readonly", false);
					$('#id-input-row'+nextrow+'-val2').prop("readonly", false);
					$('#id-input-row'+nextrow+'-val3').prop("readonly", false);
					$('#id-button-row'+nextrow+'-register').prop('disabled', false);
					$('#id-button-row'+nextrow+'-fix').prop('disabled', true);
					$('#id-button-row'+nextrow+'-react').prop('disabled', true);
					$('#id-input-row'+nextrow+'-acd').prop('readonly', false);
					//アクシデントプルダウンを入線に戻す
					$('#id-pulldown-acd').val('CODE_RESULT');
				},
				function(data) {
					$("#id-message").text("[結果登録]ajax通信エラー");
			});
		}else{
			$("#id-message").text("着順フォームに数値が入力されていません");
		}
	});
	//各行の修正ボタン
	$('.fix-btn').click(function() {
		var hostUrl= 'http://localhost:8080/data/fix';
		var row = $(this).parent().parent().children('#id-rownum').text();
		var horse = $(this).parent().parent().children('#id-horse').text();
		var place = $(this).parent().parent().children('#id-place').text();
		var no = $(this).parent().parent().children('#id-no').text();
		var raceday = $(this).parent().parent().children('#id-raceday').text();
		var param1 = $(this).parent().parent().children().find('#id-input-row'+row+'-val1').val();
		var param2 = $(this).parent().parent().children().find('#id-input-row'+row+'-val2').val();
		var param3 = $(this).parent().parent().children().find('#id-input-row'+row+'-val3').val();

		var acdCd = $('#id-pulldown-acd').val();
		$(this).parent().parent().children().find('#id-hidden-acd'+row+'-cd').val(acdCd)
		var acdVal = $(this).parent().parent().children().find('#id-input-row'+row+'-acd').val();
		$(this).parent().parent().children().find('#id-hidden-acd'+row+'-val').val(acdVal)

		var nextrow = parseInt(row)+1;

		var requestForm = {
				official : {
					winner : param1,
					runnerUp : param2,
					thirdPlace : param3
				},
				rownum : row,
				horse : horse,
				place : place,
				no : no,
				raceday : raceday,
				acdPullDownCd : acdCd,
				acdPullDownVal : acdVal
	    };
		console.log(requestForm);
		if(param1 != undefined
			 && param2 != undefined
			 && param3 != undefined){

			$.ajax({
			    url: hostUrl,
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(requestForm),
				dataType: "text",
			    timeout:3000,
			}).done(function(data) {

				$("#id-message").text(data);
				$('#id-input-row'+row+'-val1').prop("readonly", true);
				$('#id-input-row'+row+'-val2').prop("readonly", true);
				$('#id-input-row'+row+'-val3').prop("readonly", true);
				$('#id-button-row'+row+'-register').prop('disabled', true);
				$('#id-button-row'+row+'-fix').prop('disabled', false);
				$('#id-button-row'+row+'-react').prop('disabled', false);

				$('#id-input-row'+nextrow+'-val1').prop("readonly", false);
				$('#id-input-row'+nextrow+'-val2').prop("readonly", false);
				$('#id-input-row'+nextrow+'-val3').prop("readonly", false);
				$('#id-button-row'+nextrow+'-register').prop('disabled', false);
				$('#id-button-row'+nextrow+'-fix').prop('disabled', true);
				$('#id-button-row'+nextrow+'-react').prop('disabled', true);

			}).fail(function(data) {
				$("#id-message").text("[結果修正]ajax通信エラー");
			})
		}else{
			$("#id-message").text("着順フォームに数値が入力されていません");
		}
    });
	//各行の復帰チェックボックス
	$('.react-chk').click(function() {
		var row = $(this).parent().parent().children('#id-rownum').text();
		var flg = $('#id-checkbox-row'+row).prop("checked");
		if(flg){
			//チェックをつけた場合
			$('#id-input-row'+row+'-val1').prop("readonly", false);
			$('#id-input-row'+row+'-val2').prop("readonly", false);
			$('#id-input-row'+row+'-val3').prop("readonly", false);
			$('#id-button-row'+row+'-register').prop('disabled', true);
			$('#id-button-row'+row+'-fix').prop('disabled', false);
			$('#id-checkbox-row'+row).prop('disabled', false);
			$('#id-input-row'+row+'-acd').prop('readonly', false);
			$("#id-message").text("走順番No[" + row + "]のフォームが入力可能になりました");
		} else {
			//チェックを消した場合
			$('#id-input-row'+row+'-val1').prop("readonly", true);
			$('#id-input-row'+row+'-val2').prop("readonly", true);
			$('#id-input-row'+row+'-val3').prop("readonly", true);
			$('#id-button-row'+row+'-register').prop('disabled', true);
			$('#id-button-row'+row+'-fix').prop('disabled', true);
			$('#id-checkbox-row'+row).prop('disabled', false);
			$('#id-input-row'+row+'-acd').prop('readonly', true);
			$("#id-message").text("");
		}

    });

	$('.henkan').change(function(){
		var text  = $(this).val();
		var hen = text.replace(/[Ａ-Ｚａ-ｚ０-９]/g,function(s){
		          	return String.fromCharCode(s.charCodeAt(0)-0xFEE0);
		          });
		$(this).val(hen);
	});
});

