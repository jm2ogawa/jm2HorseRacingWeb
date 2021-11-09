var raceday = "";
var progressRowNum = "";
var cnt = 0;
var timer = "";

$(function() {

	//メニューリンク
	$("#id-link-menu").click(function() {
		$('#id-form-menu').attr('action', './../menu');
		$('#id-form-menu').submit();
	});

	// デフォルトの設定を変更
	$.extend( $.fn.dataTable.defaults, {
		language: {
			url: "http://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Japanese.json"
		}
	});

    $('#id-table').DataTable({
//        // 件数切替機能 無効
//        lengthChange: true,
//        // 検索機能 無効
//        searching: true,
//        // ソート機能 無効
//        ordering: true,
//        // 情報表示 無効
//        info: true,
//        // ページング機能 無効
//        paging: true,
//		// 2列目を昇順にする ( [ [ 列番号, 昇順降順 ], ... ] の形式)
//		order: [ [ 0, "asc" ] ],
//		// 状態を保存する機能をつける
//		stateSave: true
    });
    //システム設定のタイマーがオンの場合周期起動
    timer = $('#input-hidden-confkey-timer').val();
    if(timer == '1'){
    	setInterval(check,10000);
    }

	//現在時刻を表示
	$('#current_timer').countdowntimer({
		currentTime : true,
		size : "sm"
	});

    $('#id-popup-button').click(function() {
        $('.popup').addClass('js_active');
        setTimeout(function() {
            $('.popup').removeClass('js_active');
        }, 30000);
    });
	$('#id-popup-button').hide();
    $('.popup').hover(function() {
        $(this).removeClass('js_active');
    });

    var popup = $('#input-hidden-popup-message').val();
    if(popup != null && popup != ''){
    	$('#id-popup-button').click();
    }
});

/*--------------------------------
 * リレーションテーブルと比較してリストを再表示する
 --------------------------------*/
var check = function dataUpdateCheck(){
	var requestForm = {
			raceday : $('#input-hidden-raceday').val(),
			rownum : $('#input-hidden-prog-rownum').val()
    };
	var hostUrl= 'http://localhost:8080/dashboard/check';
	$.ajax({
	    url: hostUrl,
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(requestForm),
		dataType: 'text'
	}).then(
		function(data) {
			progressRowNum = $('#input-hidden-prog-rownum').val();
			if(data <= progressRowNum){
				if(data < progressRowNum){
					window.location.href = 'http://localhost:8080/dashboard/list';
				}
				cnt = cnt + 1;
				$('#id-message').text('['+cnt+']タイマー周回');
				return;
			}
			if(data > progressRowNum){
				window.location.href = 'http://localhost:8080/dashboard/list';
			}
		},
		function(data) {
			$("#id-message").text('[購入一覧]ajax通信エラー');
		}
	);
}

