<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>Widget Ledger - Online Ledger Tool</title>

		<meta name="description" content="Common UI Features &amp; Elements" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="assets/font-awesome/4.5.0/css/font-awesome.min.css" />

		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="assets/css/bootstrap-datepicker3.min.css" />
		<link rel="stylesheet" href="assets/css/jquery-ui.custom.min.css" />
		<link rel="stylesheet" href="assets/css/jquery.gritter.min.css" />

		<!-- text fonts -->
		<link rel="stylesheet" href="assets/css/fonts.googleapis.com.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
		<![endif]-->
		<link rel="stylesheet" href="assets/css/ace-skins.min.css" />
		<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->
		<style>
			/* some elements used in demo only */
			.spinner-preview {
				width: 100px;
				height: 100px;
				text-align: center;
				margin-top: 60px;
			}
			
			.dropdown-preview {
				margin: 0 5px;
				display: inline-block;
			}
			.dropdown-preview  > .dropdown-menu {
				display: block;
				position: static;
				margin-bottom: 5px;
			}
		</style>

		<!-- ace settings handler -->
		<script src="assets/js/ace-extra.min.js"></script>

		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

		<!--[if lte IE 8]>
		<script src="assets/js/html5shiv.min.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body class="no-skin">
		<jsp:include page="../authHeader.jsp" />
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){};
			</script>
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs" style="padding-left: 20px">
						<a href="#" id="sheetNameTxt">${displayTO.sheetName }</a>
						<input type="text"  id="sheetName" class="hidden" style="height: 20px" value="${displayTO.sheetName}"/>
						<!-- <form class="form-search">
							<input type="text"  id="sheetName" autocomplete="off" />
						</form> -->
						<button class="btn btn-minier btn-blue" id="sheetEdit" 
							onclick="switchDisplays('#sheetNameTxt','#sheetName');switchDisplays('#sheetEdit','#sheetSuccess')">
							<i class="ace-icon fa fa-pencil bigger-110"></i> Edit
						</button>
						<button class="btn btn-minier btn-success hidden" id="sheetSuccess" 
							onclick="switchDisplays('#sheetName','#sheetNameTxt');switchDisplays('#sheetSuccess','#sheetEdit')">
							<i class="ace-icon fa fa-check bigger-80"></i> Success
						</button>
						<div class="nav-search" id="nav-search">
							<form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- /.nav-search -->
					</div>

					<div class="page-content">
						<div class="ace-settings-container" id="ace-settings-container">
							<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
								<i class="ace-icon fa fa-cog bigger-130"></i>
							</div>

							<div class="ace-settings-box clearfix" id="ace-settings-box">
								<div class="pull-left width-50">
									<div class="ace-settings-item">
										<div class="pull-left">
											<select id="skin-colorpicker" class="hide">
												<option data-skin="no-skin" value="#438EB9">#438EB9</option>
												<option data-skin="skin-1" value="#222A2D">#222A2D</option>
												<option data-skin="skin-2" value="#C6487E">#C6487E</option>
												<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
											</select>
										</div>
										<span>&nbsp; Choose Skin</span>
									</div>
									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" autocomplete="off" />
										<label class="lbl" for="ace-settings-rtl"> Right To Left (rtl)</label>
									</div>

									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-add-container" autocomplete="off" />
										<label class="lbl" for="ace-settings-add-container">
											Inside <b>.container</b>
										</label>
									</div>
								</div><!-- /.pull-left -->
							</div><!-- /.ace-settings-box -->
						</div><!-- /.ace-settings-container -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="row">
										<div class="tabbable">
											<ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="tabs">
												<li class="active"><a data-toggle="tab" href="#groups">
													<i class="blue ace-icon fa fa-user bigger-110"></i>
													<i class="hidden-xs">Create Group</i></a>
												</li>
												<li><a data-toggle="tab" href="#expenses">
													<i class="green ace-icon fa fa-database bigger-110 orange"></i>
													<i class="hidden-xs">Enter Expense</i></a>
												</li>
												<li><a data-toggle="tab" href="#payments">
													<i class="red ace-icon fa fa-credit-card bigger-130"></i>
													<i class="hidden-xs">Compute Payment</i></a>
												</li>
											</ul>
											<div class="tab-content">
												<div id="groups" class="tab-pane in active">
													<jsp:include page="userTable.jsp"/>
													<!-- <table id="user-group"></table>
													<table id="user-pager"></table> -->
												</div>
												<div id="expenses" class="tab-pane">
													<jsp:include page="expenseTable.jsp"/>
												</div>
												<div id="payments" class="tab-pane">
													<jsp:include page="balanceTable.jsp"/>
												</div>
											</div>
										</div>
										
									</div>
							<div class="row">
								<a href="#" id="addExpense"
									class="btn btn-white btn-info btn-bold"> <i
									class="ace-icon glyphicon-plus bigger-100"></i> Add Person[+]
								</a> <a href="#" id="deleteSelected"
									class="btn btn-white btn-info btn-bold"> <i
									class="ace-icon fa fa-trash-o bigger-100"></i> Delete Selected
								</a> <a href="#" id="enterExpense"
									class="btn btn-white btn-info btn-bold"> <i
									class="ace-icon fa fa-pencil-square-o bigger-100"></i> Enter
									Expenses
								</a>
								<div id="dialog-message" class="hide">
									<p>This is the default dialog which is useful for
										displaying information. The dialog window can be moved,
										resized and closed with the 'x' icon.</p>
									<p>
										Currently using <b>36% of your storage space</b>.
									</p>
								</div>
							</div><!-- /.row -->
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>
			</div><!-- /.main-content -->

			<jsp:include page="../authFooter.jsp" />

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<!-- basic scripts -->
		<!--[if !IE]> -->
		<script src="assets/js/jquery-2.1.4.min.js"></script>
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="assets/js/bootstrap.min.js"></script>
		<!-- page specific plugin scripts -->
		<script src="assets/js/jquery-ui.custom.min.js"></script>
		<script src="assets/js/jquery.ui.touch-punch.min.js"></script>
		<!-- <script src="assets/js/bootbox.js"></script> -->
		<script src="assets/js/spin.js"></script>
		<!-- <script src="assets/js/jquery.jqGrid.min.js"></script> -->
		<script src="assets/js/bootstrap-datepicker.min.js"></script>
		<script src="assets/js/grid.locale-en.js"></script>

		<!-- ace scripts -->
		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
				$('#tabs a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
					//if($(e.target).attr('href') == "#home") doSomethingNow();
				})
				$('#accordion-style').on('click', function(ev){
					var target = $('input', ev.target);
					var which = parseInt(target.val());
					if(which == 2) $('#accordion').addClass('accordion-style2');
					 else $('#accordion').removeClass('accordion-style2');
				});
				
				//$('[href="#collapseTwo"]').trigger('click');
			
				$('[data-rel=tooltip]').tooltip();
				$('[data-rel=popover]').popover({html:true});
				
				$('#spinner-opts small').css({display:'inline-block', width:'60px'})
			
				var slide_styles = ['', 'green','red','purple','orange', 'dark'];
				var ii = 0;
				$("#spinner-opts input[type=text]").each(function() {
					var $this = $(this);
					$this.hide().after('<span />');
					$this.next().addClass('ui-slider-small').
					addClass("inline ui-slider-"+slide_styles[ii++ % slide_styles.length]).
					css('width','125px').slider({
						value:parseInt($this.val()),
						range: "min",
						animate:true,
						min: parseInt($this.attr('data-min')),
						max: parseInt($this.attr('data-max')),
						step: parseFloat($this.attr('data-step')) || 1,
						slide: function( event, ui ) {
							$this.val(ui.value);
							spinner_update();
						}
					});
				});
			
				//CSS3 spinner
				$.fn.spin = function(opts) {
					this.each(function() {
					  var $this = $(this),
						  data = $this.data();
			
					  if (data.spinner) {
						data.spinner.stop();
						delete data.spinner;
					  }
					  if (opts !== false) {
						data.spinner = new Spinner($.extend({color: $this.css('color')}, opts)).spin(this);
					  }
					});
					return this;
				};
			
				function spinner_update() {
					var opts = {};
					$('#spinner-opts input[type=text]').each(function() {
						opts[this.name] = parseFloat(this.value);
					});
					opts['left'] = 'auto';
					$('#spinner-preview').spin(opts);
				}
				$('#id-pills-stacked').removeAttr('checked').on('click', function(){
					$('.nav-pills').toggleClass('nav-stacked');
				});
				///////////
				$(document).one('ajaxloadstart.page', function(e) {
					$.gritter.removeAll();
					$('.modal').modal('hide');
				});
			});
			
			function switchDisplays(element1,element2){
				$(element1).addClass('hidden');
				$(element2).removeClass('hidden');
			}
			
		$(window).load(function() {
		    $(".message-loading-overlay").fadeOut("slow");
		})
		$(document).ready(function(){
			$( "#addExpense" ).on('click', function(e) {
				e.preventDefault();
				/* $( "#dialog-message" ).modal('show'); */
				$( "#dialog-message" ).dialog({
					resizable: false,
					width: '320',
					modal: true,
					title: "<div class='widget-header'><h4 class='smaller'><i class='ace-icon fa fa-exclamation-triangle red'></i> Empty the recycle bin?</h4></div>",
					title_html: true,
					buttons: [
						{
							html: "<i class='ace-icon fa fa-trash-o bigger-110'></i>&nbsp; Delete all items",
							"class" : "btn btn-danger btn-minier",
							click: function() {
								$( this ).dialog( "close" );
							}
						}
						,
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
		})
		</script>
	</body>
</html>
