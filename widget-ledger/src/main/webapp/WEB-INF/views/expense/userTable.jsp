<jsp:include page="../common/scriptImports.jsp" />
<table id="simple-table" class="table  table-bordered table-hover hide">
	<thead>
		<tr>
			<th class="center"><label class="pos-rel"> <input
					type="checkbox" class="ace" /> <span class="lbl"></span>
			</label></th>
			<th class="detail-col">Name</th>
			<th>Display name</th>
			<th>Description or Comments</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
	<tbody>
		<tr>
			<td colspan="5" class="well">Please enter the list of persons
				who are sharing expenses: <br /> Click «Add Person» or press the
				«+» key to get started. Once you have entered at least 2 persons,
				click «Enter Expenses» to start entering shared expenses.
			</td>
		</tr>
	</tbody>
</table>
<br/>
<div>
	<a href="#" id="addPerson" class="btn btn-sm btn-primary btn-bold"> <i
		class="glyphicon glyphicon-plus-sign bigger-100"></i><i class="hidden-xs">
			Add Person[+]</i>
	</a> <a href="#" id="deleteSelected" class="btn btn-sm btn-danger hide">
		 <i class="ace-icon fa fa-trash-o bigger-100"></i><i
		class="hidden-xs"> Delete Selected</i>
	</a> <a href="#" id="addExpenses" class="btn btn-sm btn-primary hide"> <i
		class="ace-icon fa fa-pencil-square-o bigger-100"></i><i
		class="hidden-xs"> Enter Expenses</i>
	</a>
	<div id="person-dialog-message" class="hide">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" id="token" /> Person Name: <input type="text"
			name="addPersonName" id="addPersonName" value="Gopinathan" />
		<div class="space-6"></div>
		Display Name: <input type="text" name="addDisplayName"
			id="addDisplayName" value="Gopi" />
		<p class="smaller-80 bolder center grey">
			<i class="ace-icon fa fa-hand-o-right blue bigger-120"></i> Keep it
			short for better readability in expense table. Ex: Initials,
			nickname, first name, etc
		</p>
		<div class="space-6"></div>
		Comments: <input type="text" name="addComments" id="addComments"
			value="Master" />
		<p class="smaller-80 bolder center grey">
			<i class="ace-icon fa fa-hand-o-right blue bigger-120"></i> When
			signed in with your Facebook account, enter the first letters of the
			person name and it will search among your saved sheets and Facebook
			friends.
		</p>
	</div>
</div>
<script type="text/javascript">
$( "#addPerson" ).on('click', function(e) {
	e.preventDefault();
	$( "#person-dialog-message" ).removeClass('hide').dialog({
		resizable: false,
		width: '500',
		modal: true,
		title: "<div class='widget-header'><h4 class='smaller'><i class='ace-icon fa fa-user blue'></i> New Person</h4></div>",
		title_html: true,
		buttons: [
			{
				html: "<i class='ace-icon fa fa-floppy-o bigger-110'></i>&nbsp; Save",
				"class" : "btn btn-primary btn-minier",
				click: function() {
					$( this ).dialog( "close" );
					//$('#addUserToGroup').submit();
					addUser();
					
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


function addUser() {
	var context = "${pageContext.request.contextPath}";
	var service = '/addUser';
	var url = context + service;
	var addPersonName =  $('#addPersonName').val();
	var addDisplayName = $('#addDisplayName').val();
	var addComments = $('#addComments').val();
	var uniqueSheetId = $('#uniqueSheetId').val();
	var userDetailsTO ={
			"addPersonName": addPersonName,
			"addDisplayName": addDisplayName,
			"addComments": addComments,
			"uniqueSheetId" : uniqueSheetId
	};
	//"${_csrf.parameterName}" //: "${_csrf.token}",
	
	$.postJSONData(url, userDetailsTO, refreshUserTable, addUserFailure, 'preLoadingIcon');
}

function hideLoading(){
	$('#preLoadingIcon').hide();
}
var context = "${pageContext.request.contextPath}";
$(document).ready(function(){
	fetchUserTable();
});

function fetchUserTable(){
	var service = '/getUserDetails';
	var url = context + service;
	var uniqueSheetId = $('#uniqueSheetId').val();
	var userDetailsTO ={
			"addPersonName": '',
			"addDisplayName": '',
			"addComments": '',
			"uniqueSheetId" : uniqueSheetId
	};
	$.postJSONData(url, userDetailsTO , refreshUserTable, addUserFailure, 'preLoadingIcon');
}

function addUserFailure(jqXHR){
	$('#errorDiv').removeClass('hide').append(jqXHR.responseText);
	$('#errorDiv').fadeOut();
	hideLoading();
}
function fillBreadCrumbs(data){
	$('#breadCrumbsPerson').html('').html(data.totalUsers +' Persons');
	$('#breadCrumbsExpense').html('').html(data.countExpenses +' Expenses');
	$('#breadCrumbsTotal').html('').html('Total Spent: ' + data.totalExpenses);
	$('#breadCrumbsCreatedOn').html('').html('Created On : ' +data.createdOn);
	$('#breadCrumbsUpdatedOn').html('').html('Last Updated on : ' +data.lastUpdated);
}
function refreshUserTable(data){
	fillBreadCrumbs(data);
	var userData = data.userDetailsTO;
	if(userData == '' || userData == null || userData.length == 0){
		$('#simple-table').removeClass('hide');
		return false;
	}else {
		$('#simple-table').addClass('hide');
		if(userData.length >= 2)
		$('#deleteSelected, #addExpenses').removeClass('hide');
		$('#paidByGroup').html('');
		$('#paidForGroup').html('');
	}
	
	var grid_selector = "#user-group";
	var pager_selector = "#user-group-pager";
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
	data: userData,
	datatype: "local",
	height: 250,
	rownumbers: true,
	colNames:['Person Name','Display Name','Description Comments','<i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i> Last Updated', 'Actions'],
	colModel:[
		/* {name:'id',index:'id', width:40, sorttype:"int", editable: false}, */
		/* {name:'sdate',index:'sdate',width:90, editable:true, sorttype:"date",unformat: pickDate}, */
		{name:'addPersonName',index:'addPersonName', width:80, sortable:true, editable: true,editoptions:{size:"20",maxlength:"30"}},
		{name:'addDisplayName',index:'addDisplayName', width:80, sortable:true, editable: true,editoptions:{size:"20",maxlength:"30"}},
		{name:'addComments',index:'addComments', width:120,sortable:true, editable: true, editoptions:{size:"20",maxlength:"30"}},
		{ name:'lastUpdateTime',index:'lastUpdateTime', width:80, sortable:true,editable: false},
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
$(grid_selector).trigger('reloadGrid');
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
	fetchExpenseTable();
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