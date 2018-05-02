<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en">
	<head>
		<jsp:include page="../common/includes.jsp" />
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
						<input type="text"  id="uniqueSheetId" class="hidden" style="height: 20px" value="${displayTO.uniqueSheetId}"/>
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
									<ul
										class="nav nav-tabs padding-12 tab-color-blue background-blue"
										id="tabs">
										<li class="active"><a data-toggle="tab" href="#groups">
												<i class="blue ace-icon fa fa-user bigger-110"></i> <i
												class="hidden-xs">Create Group</i>
										</a></li>
										<li><a data-toggle="tab" href="#expenses"> <i
												class="green ace-icon fa fa-database bigger-110 orange"></i>
												<i class="hidden-xs">Enter Expense</i></a></li>
										<li><a data-toggle="tab" href="#payments"> <i
												class="red ace-icon fa fa-credit-card bigger-130"></i> <i
												class="hidden-xs">Compute Payment</i></a></li>
									</ul>
									<div class="tab-content">
										<div id="groups" class="tab-pane in active">
											<table id="user-group"></table>
											<table id="user-group-pager"></table>
											<jsp:include page="userTable.jsp" />
										</div>
										<div id="expenses" class="tab-pane">
											<table id="expense-jqTable"></table>
											<table id="expense-jqTable-pager"></table>
											<jsp:include page="expenseTable.jsp" />
										</div>
										<div id="payments" class="tab-pane">
											<table id="payments-jqTable"></table>
											<jsp:include page="balanceTable.jsp" />
										</div>
									</div>
								</div>
							</div>
							<!-- /.row -->
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
		<jsp:include page="../common/scriptImports.jsp" />
		
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
		//override dialog's title function to allow for HTML titles
		$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
			_title: function(title) {
				var $title = this.options.title || '&nbsp;'
				if( ("title_html" in this.options) && this.options.title_html == true )
					title.html($title);
				else title.text($title);
			}
		}));
		
		</script>
	</body>
</html>
