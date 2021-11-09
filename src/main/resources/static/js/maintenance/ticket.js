$(function () {
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
});

