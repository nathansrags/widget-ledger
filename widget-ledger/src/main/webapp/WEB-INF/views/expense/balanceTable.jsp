<jsp:include page="../common/scriptImports.jsp" />
<div class="row">
	<table id="payments-jqTable" style="width: 50%"></table>
	<table id="balance-jqTable"></table>
</div>
<script type="text/javascript">
$( "#computePayment" ).on('click', function(e) {
	e.preventDefault();
	$('.nav-tabs li:eq(2) a').tab('show');
	var context = "${pageContext.request.contextPath}";
	var service = '/computeExpenses';
	var url = context + service;
	var uniqueSheetId = $('#uniqueSheetId').val();
	$.postJSONData(url, uniqueSheetId, refreshBalanceTable, '', 'preLoadingIcon');

});

function refreshBalanceTable(data){
	var balanceData = data.paymentDetailsTO;
	refreshPaymentTable(data);
	if(balanceData == '' || balanceData == null || balanceData.length == 0){
		hideLoading();
		return false;
	}
	var grid_selector = "#payments-jqTable";
	$.jgrid.gridUnload(grid_selector);
	jQuery(function($) {
		var parent_column = $(grid_selector).closest('[class*="col-"]');
		//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$(grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
	    })
		
		//resize on sidebar collapse/expand
		$(document).on('settings.ace.jqGrid' , function(ev, event_name, collapsed) {
			if( event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed' ) {
				//setTimeout is for webkit only to give time for DOM changes and then redraw!!!
				setTimeout(function() {
					$(grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
				}, 20);
			}
	   });
	});
	jQuery(grid_selector).jqGrid({
		data: balanceData,
		datatype: "local",
		height: 250,
		rownumbers: true,
		colNames:['Name','Expenses','Total Spent','Owes', 'Owned','Balance'],
		colModel:[
			{name:'name',index:'name', width:80, sortable:true, editable: false},
			{name:'expense',index:'expense', width:80, sortable:true, editable: false},
			{name:'totalSpent',index:'totalSpent', width:120, sortable:true, editable:false},
			{name:'ownedTo',index:'paidAmt', width:80, sortable:true, editable: false},
			{name:'ownedFrom',index:'paidFor', width:80, sortable:true, editable: false},
			{name:'balaneAmt',index:'balaneAmt', width:80, sortable:true, editable: false}
		], 

		viewrecords : true,
		rowNum:10,
		rowList:[10,20,30],
		//pager : pager_selector,
		altRows: true,
		//toppager: true,
		multiselect: true,
		//multikey: "ctrlKey",
	    multiboxonly: true,

		loadComplete : function() {
			/* var table = this;
			setTimeout(function(){
				styleCheckbox(table);
				
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
			hideLoading(); */
			hideLoading();
		}
		,autowidth: true,
	});
}

function refreshPaymentTable(data){
	var paymentTO = data.paymentTO;
	if(paymentTO == '' || paymentTO == null || paymentTO.length == 0){
		hideLoading();
		return false;
	}
	var grid_selector = "#payments-jqTable";
	$.jgrid.gridUnload(grid_selector);
	jQuery(function($) {
		jQuery(grid_selector).jqGrid({
			data: paymentTO,
			datatype: "local",
			height: 200,
			colNames:['From','To','Amt'],
			colModel:[
				{name:'payFrom',index:'payFrom', sortable:true},
				{name:'payTO',index:'payTO', sortable:true},
				{name:'payAmt',index:'payAmt', sortable:true}
			], 
			viewrecords : true,
			rowNum:10,
			width : $('#payments-jqTable').width(),
			rowList:[10,20,30],
			//pager : pager_selector,
			altRows: true,
			//toppager: true,
			multiselect: true,
			//multikey: "ctrlKey",
		    multiboxonly: true,
			loadComplete : function() {
				hideLoading();
			}
		});
		//$(grid_selector).trigger('reloadGrid');
		$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	});
	
}
</script>