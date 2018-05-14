<jsp:include page="../common/scriptImports.jsp" />
<table id="simple-table-expense" class="table  table-bordered table-hover">
	<thead>
		<tr>
			<th class="center"><label class="pos-rel"> <input
					type="checkbox" class="ace" /> <span class="lbl"></span>
			</label></th>
			<th>Id</th>
			<th class="detail-col">Date</th>
			<th>Description</th>
			<th>Who Paid</th>
			<th>Amount</th>
			<th>For Whom?</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
	<tbody>
		<tr>
			<td colspan="8" class="well">Click «Add Expense» or press the
				«+» key to enter the first expense. Once done, click «Compute
				Payments».</td>
		</tr>
	</tbody>
</table>
<br/>
<div>
	<a href="#" id="addExpense" class="btn btn-sm btn-primary btn-bold"> <i
		class="glyphicon glyphicon-plus-sign bigger-100"></i><i class="hidden-xs">
			Add Expense [+]</i>
	</a> <a href="#" id="deleteSelected" class="btn btn-sm btn-danger"> <i class="ace-icon fa fa-trash-o bigger-100"></i><i
		class="hidden-xs"> Delete Selected</i>
	</a> <a href="#" id="computePayment" class="btn btn-sm btn-primary"> <i
		class="ace-icon fa fa-pencil-square-o bigger-100"></i><i
		class="hidden-xs"> Compute Payments</i>
	</a>
	<div id="expense-dialog-message" class="hide">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" id="token" />
		<table>
			<tr>
				<td class="col-md-4">Amount:</td>
				<td><div class="input-group">
					<span class="input-group-addon">$</span>
						<input type="text" name="expenseAmount"
					id="expenseAmount" class="form-control" placeholder="Expenses"/>
					</div>
				</td>
			</tr>
			<tr><td colspan="2"><div class="space-6"></div></td></tr>
			<tr>
				<td class="col-md-4">Date:</td>
				<td>
				<div class="input-group">
					<span class="input-group-addon">
						<i class="fa fa-calendar bigger-110"></i>
					</span>
					<input type="text" name="expenseDate" id="expenseDate" class="date-picker" data-date-format="mm-dd-yyyy" />
				</div>
				</td>
			</tr>
			<tr><td colspan="2"><div class="space-6"></div></td></tr>
			<tr>
				<td class="col-md-4">Description :</td>
				<td><input type="text" name="expenseDescription" class="col-xs-12" id="expenseDescription" value="Patel Bros"/></td>
			</tr>
			<tr><td colspan="2"><div class="space-6"></div></td></tr>
			<tr>
				<td class="col-md-4">Who Paid?:</td>
				<td>
					<div id="paidByGroup" ></div>
				</td>
			</tr>
			<tr><td colspan="2"><div class="space-6"></div></td></tr>
			<tr>
				<td class="col-md-4">Paid For?: </td>
				<td><div id="paidForGroup" name="paidForGroup">
						
				</div></td>
			</tr>
			<tr><td colspan="2"><div class="space-6"></div></td></tr>
			<tr>
				<td></td>
				<td> <a href="#">Non even Split</a> | <a href="#" id="uncheckExpenses" onclick="uncheckExpenses()">Uncheck all</a></td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
$('.date-picker').datepicker({
	autoclose: true,
	todayHighlight: true
})
//show datepicker when clicking on the icon
.next().on(ace.click_event, function(){
	//$(this).prev().focus();
});
function uncheckExpenses(){
	if($('#uncheckExpenses').html() === 'Check all'){
		$('#paidForGroup input').each(function(i,v){
			$(this).prop('checked',true);
		})
		$('#uncheckExpenses').html('').html('UnCheck all');
	}else{
		$('#paidForGroup input').each(function(i,v){
			$(this).prop('checked',false);
		})
		$('#uncheckExpenses').html('').html('Check all');
	}
	
}
function populateUserSelections(){
	var userData = $('#user-group').getGridParam('data');
	$('#paidByGroup').html('');
	$('#paidForGroup').html('');
	$.each(userData, function(i, v){
		var userDetailId = userData[i].userDetailId;
		var displayName =  userData[i].addDisplayName;
		$('#paidByGroup').append("<input type='radio' id='paidByUser' name='paidByUser' style='margin-left: 3px;margin-right: 3px' value='"+userDetailId+"'>" +displayName) +"</input>&nbsp";
		$('#paidForGroup').append("<input type='checkbox' id='paidForUser_"+[i]+"' name='' style='margin-left: 3px;margin-right: 3px' value='"+userDetailId+"' checked>" +displayName) +"</input>&nbsp";
	});
	$('#expenseDescription').focus();
}

function getAllUser(){
	var displayNames = '';
	var userTableData = $('#user-group').getGridParam('data');
	$.each(userTableData, function(i, v){
		displayNames += (userTableData[i].userDetailId + ':' +userTableData[i].addDisplayName+';');
	});
	displayNames = displayNames.substring(0,displayNames.length-1);
	return displayNames;
}

$( "#addExpense,#addExpenses" ).on('click', function(e) {
	e.preventDefault();
	$('.nav-tabs li:eq(1) a').tab('show')
	$( "#expense-dialog-message" ).removeClass('hide').dialog({
		resizable: false,
		width: '500',
		modal: true,
		title: "<div class='widget-header'><h4 class='smaller'><i class='ace-icon fa fa-user blue'></i> New Expense</h4></div>",
		title_html: true,
		open: populateUserSelections(),
		buttons: [
			{
				html: "<i class='ace-icon fa fa-floppy-o bigger-110'></i>&nbsp; Save",
				"class" : "btn btn-primary btn-minier",
				click: function() {
					$( this ).dialog( "close" );
					addExpense();
					
				}
			},
			{
				html: "<i class='ace-icon fa fa-times bigger-110'></i>&nbsp; Cancel",
				"class" : "btn btn-minier",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});
});


function addExpense() {
	var context = "${pageContext.request.contextPath}";
	var service = '/addExpense';
	var url = context + service;
	var paidFor = [];
	var expenseAmount =  $('#expenseAmount').val();
	var expenseDate = $('#expenseDate').val();
	var expenseDescription = $('#expenseDescription').val();
	var uniqueSheetId = $('#uniqueSheetId').val();
	var paidBy = $('#paidByGroup :checked').val();
	$('input:checkbox[id^="paidForUser"]:checked').each(function () {
	       // alert("Id: " + $(this).attr("id") + " Value: " + $(this).val());
	       var userDetailsTOPaidFor ={
				"addPersonName": '',
				"addDisplayName": '',
				"addComments": '',
				"uniqueSheetId" : uniqueSheetId,
				"userDetailId": $(this).val()
			}
	       paidFor.push(userDetailsTOPaidFor);
	   });
	var userDetailsTOPaidBy ={
			"addPersonName": '',
			"addDisplayName": '',
			"addComments": '',
			"uniqueSheetId" : uniqueSheetId,
			"userDetailId": paidBy
	};
	var expenseDetailsTO ={
			"paidAmt": expenseAmount,
			"expenseDate": expenseDate,
			"expenseDesc": expenseDescription,
			"paidBy" : userDetailsTOPaidBy,
			"paidFor": paidFor
	};
	//"${_csrf.parameterName}" //: "${_csrf.token}",
	
	$.postJSONData(url, expenseDetailsTO, refreshExpenseTable, '', 'preLoadingIcon');
}

function hideLoading(){
	$('#preLoadingIcon').hide();
}
var context = "${pageContext.request.contextPath}";

function fetchExpenseTable(){
	var service = '/getExpenseDetails';
	var url = context + service;
	var uniqueSheetId = $('#uniqueSheetId').val().replace(/\"/g, "");
	$.postJSONData(url, uniqueSheetId , refreshExpenseTable, '', 'preLoadingIcon');
}

function refreshExpenseTable(data){
	var expenseData = data.expenseDetailsTO;
	if(expenseData == '' || expenseData == null || expenseData.length == 0){
		$('#simple-table-expense').removeClass('hide');
		hideLoading();
		return false;
	}else{
		$('#simple-table-expense').addClass('hide');
	}
	var grid_selector = "#expense-jqTable";
	var pager_selector = "#expense-jqTable-pager";
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
	    })

jQuery(grid_selector).jqGrid({
	data: expenseData,
	datatype: "local",
	height: 250,
	rownumbers: true,
	colNames:['Date','Description','Who Paid?','Amount', 'For Whom','Action'],
	colModel:[
		/* {name:'id',index:'id', width:40, sorttype:"int", editable: false}, */
		/* {name:'sdate',index:'sdate',width:90, editable:true, sorttype:"date",unformat: pickDate}, */
		{name:'expenseDate',index:'expenseDate', width:80, sortable:true, editable: true,sorttype:"date",unformat: pickDate},
		{name:'expenseDesc',index:'expenseDesc', width:80, sortable:true, editable: true,editoptions:{size:"20",maxlength:"30"}},
		{name:'paidBy.addDisplayName',index:'paidBy.addDisplayName', width:120, sortable:true, editable: true,edittype:"select",editoptions:{value: getAllUser()}},
		{name:'paidAmt',index:'paidAmt', width:80, sortable:true, editable: false},
		{name:'paidFor',index:'paidFor', width:80, sortable:true, editable: false, formatter: function (cellvalue) {
			var retVal ='';
			$.each(cellvalue, function(i,v){
				retVal += (cellvalue[i].addDisplayName + ', ');
			})
            return retVal.substring(0,retVal.length-2);
        }},
		{name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false,
			formatter:'actions', 
			formatoptions:{ 
				keys:true,
				//delbutton: false,//disable delete button
				delOptions:{recreateForm: true, beforeShowForm: beforeDeleteCallback},
				//editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
			}
		}
	], 

	viewrecords : true,
	rowNum:10,
	rowList:[10,20,30],
	pager : pager_selector,
	altRows: true,
	//toppager: true,
	multiselect: true,
	//multikey: "ctrlKey",
    multiboxonly: true,

	loadComplete : function() {
		var table = this;
		setTimeout(function(){
			styleCheckbox(table);
			
			updateActionIcons(table);
			updatePagerIcons(table);
			enableTooltips(table);
		}, 0);
		hideLoading();
	}
	//,autowidth: true,
});
//$(grid_selector).trigger('reloadGrid');
$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size

//enable search/filter toolbar
//jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
//jQuery(grid_selector).filterToolbar({});


//switch element when editing inline
/* function aceSwitch( cellvalue, options, cell ) {
	setTimeout(function(){
		$(cell) .find('input[type=checkbox]')
			.addClass('ace ace-switch ace-switch-5')
			.after('<span class="lbl"></span>');
	}, 0);
} */
//enable datepicker
function pickDate( cellvalue, options, cell ) {
	setTimeout(function(){
		$(cell) .find('input[type=text]')
			.datepicker({format:'yyyy-mm-dd' , autoclose:true}); 
	}, 0);
}


//navButtons
jQuery(grid_selector).jqGrid('navGrid',pager_selector,
	{ 	//navbar options
		edit: true,
		editicon : 'ace-icon fa fa-pencil blue',
		add: true,
		addicon : 'ace-icon fa fa-plus-circle purple',
		del: true,
		delicon : 'ace-icon fa fa-trash-o red',
		search: true,
		searchicon : 'ace-icon fa fa-search orange',
		refresh: true,
		refreshicon : 'ace-icon fa fa-refresh green',
		view: true,
		viewicon : 'ace-icon fa fa-search-plus grey',
	},
	{
		//edit record form
		//closeAfterEdit: true,
		//width: 700,
		recreateForm: true,
		beforeShowForm : function(e) {
			var form = $(e[0]);
			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
			style_edit_form(form);
		}
	},
	{
		//new record form
		//width: 700,
		closeAfterAdd: true,
		recreateForm: true,
		viewPagerButtons: false,
		beforeShowForm : function(e) {
			var form = $(e[0]);
			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
			.wrapInner('<div class="widget-header" />')
			style_edit_form(form);
		}
	},
	{
		//delete record form
		recreateForm: true,
		beforeShowForm : function(e) {
			var form = $(e[0]);
			if(form.data('styled')) return false;
			
			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
			style_delete_form(form);
			
			form.data('styled', true);
		},
		onClick : function(e) {
			//alert(1);
		}
	},
	{
		//search form
		recreateForm: true,
		afterShowSearch: function(e){
			var form = $(e[0]);
			form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
			style_search_form(form);
		},
		afterRedraw: function(){
			style_search_filters($(this));
		}
		,
		multipleSearch: true,
		/**
		multipleGroup:true,
		showQuery: true
		*/
	},
	{
		//view record form
		recreateForm: true,
		beforeShowForm: function(e){
			var form = $(e[0]);
			form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
		}
	}
)



function style_edit_form(form) {
	//enable datepicker on "sdate" field and switches for "stock" field
	form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})
	
	form.find('input[name=stock]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
			   //don't wrap inside a label element, the checkbox value won't be submitted (POST'ed)
			  //.addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');
			
	//update buttons classes
	var buttons = form.next().find('.EditButton .fm-button');
	buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();//ui-icon, s-icon
	buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
	buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')
	
	buttons = form.next().find('.navButton a');
	buttons.find('.ui-icon').hide();
	buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
	buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');		
}

function style_delete_form(form) {
	var buttons = form.next().find('.EditButton .fm-button');
	buttons.addClass('btn btn-sm btn-white btn-round').find('[class*="-icon"]').hide();//ui-icon, s-icon
	buttons.eq(0).addClass('btn-danger').prepend('<i class="ace-icon fa fa-trash-o"></i>');
	buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>')
}

function style_search_filters(form) {
	form.find('.delete-rule').val('X');
	form.find('.add-rule').addClass('btn btn-xs btn-primary');
	form.find('.add-group').addClass('btn btn-xs btn-success');
	form.find('.delete-group').addClass('btn btn-xs btn-danger');
}
function style_search_form(form) {
	var dialog = form.closest('.ui-jqdialog');
	var buttons = dialog.find('.EditTable')
	buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'ace-icon fa fa-retweet');
	buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'ace-icon fa fa-comment-o');
	buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'ace-icon fa fa-search');
}

function beforeDeleteCallback(e) {
	var form = $(e[0]);
	if(form.data('styled')) return false;
	form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
	style_delete_form(form);
	form.data('styled', true);
}

function beforeEditCallback(e) {
	var form = $(e[0]);
	form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
	style_edit_form(form);
}

//it causes some flicker when reloading or navigating grid
//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
//or go back to default browser checkbox styles for the grid
function styleCheckbox(table) {
/**
	$(table).find('input:checkbox').addClass('ace')
	.wrap('<label />')
	.after('<span class="lbl align-top" />')


	$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
	.find('input.cbox[type=checkbox]').addClass('ace')
	.wrap('<label />').after('<span class="lbl align-top" />');
*/
}


//unlike navButtons icons, action icons in rows seem to be hard-coded
//you can change them like this in here if you want
function updateActionIcons(table) {
	/**
	var replacement = 
	{
		'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue',
		'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red',
		'ui-icon-disk' : 'ace-icon fa fa-check green',
		'ui-icon-cancel' : 'ace-icon fa fa-times red'
	};
	$(table).find('.ui-pg-div span.ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	})
	*/
}

//replace icons with FontAwesome icons like above
function updatePagerIcons(table) {
	var replacement = 
	{
		'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
		'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
		'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
		'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	})
}

function enableTooltips(table) {
	$('.navtable .ui-pg-button').tooltip({container:'body'});
	$(table).find('.ui-pg-div').tooltip({container:'body'});
}

//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');

$(document).one('ajaxloadstart.page', function(e) {
	$.jgrid.gridDestroy(grid_selector);
	$('.ui-jqdialog').remove();
});
	});
	
}
</script>