$(function() {
	$("#id-button-data-update").click(function() {
		$('#id-form-menu').attr('action', '/data/view');
		$('#id-form-menu').submit();
	});
	$('#id-button-data-race').click(function(e) {
		$('#id-form-menu').attr('action', '/data/race');
		$('#id-form-menu').submit();
    });
	$("#id-button-dashboard-list").click(function() {
		$('#id-form-menu').attr('action', '/dashboard/list');
		$('#id-form-menu').submit();
	});
	$("#id-button-maintenance-sysconfig").click(function() {
		$('#id-form-menu').attr('action', '/maintenance/sysconfig');
		$('#id-form-menu').submit();
	});
	$("#id-button-maintenance-ticket").click(function() {
		$('#id-form-menu').attr('action', '/maintenance/ticket');
		$('#id-form-menu').submit();
	});
	$("#id-button-test-index").click(function() {
		$('#id-form-menu').attr('action', '/');
		$('#id-form-menu').submit();
	});
});
