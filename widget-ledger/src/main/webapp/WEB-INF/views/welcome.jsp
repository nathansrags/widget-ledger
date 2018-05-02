<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>Widget Ledger - Online Ledger Tool</title>
		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="assets/font-awesome/4.5.0/css/font-awesome.min.css" />
		<link rel="shortcut icon" href="assets/images/avatars/avatar.png">

		<!-- page specific plugin styles -->

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

		<!-- ace settings handler -->
		<script src="assets/js/ace-extra.min.js"></script>

		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

		<!--[if lte IE 8]>
		<script src="assets/js/html5shiv.min.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body class="no-skin">
		<div class="message-loading-overlay"><h1 style="padding-top: 17%"><i class="fa-spin ace-icon fa fa-refresh blue bigger-250"></i></h1></div>
		<div id="navbar" class="navbar navbar-default          ace-save-state">
			<div class="navbar-container ace-save-state" id="navbar-container">
				<button type="button" class="navbar-toggle menu-toggler pull-left"
					id="menu-toggler" data-target="#sidebar">
					<span class="sr-only">Toggle sidebar</span> <span class="icon-bar"></span>
		
					<span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
		
				<div class="navbar-header pull-left">
					 <a href="<c:url value='/welcome' />" class="navbar-brand"> <small> <i
							class="fa fa-leaf"></i> Widget Ledger
					</small>
					</a>
				</div>
			</div>
		<!-- /.navbar-container -->
		</div>
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
					<c:if test="${errorTO.errMsg  != null}">
											<div class="alert alert-danger">
												<p>No Sheet found.</p>
											</div>
					</c:if>
						<div class="nav-search" id="nav-search">
							<form class="form-search" name="createExpense" method="POST" action="<c:url value='retriveSheet' />">Track Sheet
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" name="uniqueSheetId" id="uniqueSheetId" autocomplete="off" value="ATK90374O1DJ"/>
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- /.nav-search -->
					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<H4>No more hassle tracking and evening out shared expenses!</H4>
								<div class="well">
									<ul class="list-unstyled spaced">
										<li><i class="ace-icon fa fa-share green bigger-110"></i>Enter a list of persons (or select Facebook friends);</li>
										<li><i class="ace-icon fa fa-share green bigger-110"></i>Enter expenses (amount, who paid, for whom, etc.);</li>
										<li><i class="ace-icon fa fa-share green bigger-110"></i>The shortest list of payments needed to even the debts is calculated;</li>
										<li><i class="ace-icon fa fa-share green bigger-110"></i>Print, share, save and export your work.</li>
										<li><i class="ace-icon fa fa-share green bigger-110"></i>Enter expenses (amount, who paid, for whom, etc.);</li>
									</ul>
								</div>
								<form name="createExpense" method="POST" action="<c:url value='createExpense' />">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<h4>Start a new expense sheet 
								<span class="header smaller lighter orange">
								NO REGISTRATION REQUIRED</span></h4>
								<div class="well">
									Name it <input type="text" name="sheetName" id="sheetName" />
									<input type="submit" class="btn btn-minier btn-yellow" name="newSheetSubmit" value="Start!" />
								</div>
								</form>
								<h4>Features & Benefits</h4>
								<div class="well">
									<ul class="list-unstyled spaced">
										<li><i class="ace-icon fa fa-check bigger-110 green"></i>
											Roaming-friendly: Keep working when offline!</li>
										<li><i class="ace-icon fa fa-check bigger-110 green"></i>
											Unlimited Storage: Sign-in with your Facebook account
										and save all your sheets to one place;</li>
										<li><i class="ace-icon fa fa-check bigger-110 green"></i>
											Open: Select sheet members from your Facebook friends; </li>
										<li><i class="ace-icon fa fa-check bigger-110 green"></i>
											Ultra Fast: The user-friendly interface lets you enter several dozen expenses in minutes; </li>
										<li><i class="ace-icon fa fa-check bigger-110 green"></i>
											International: Content can be entered in any language (Unicode support); </li>
										<li><i class="ace-icon fa fa-check bigger-110 green"></i>
											Advanced: Support for non-even splits (fixed amounts, percentages or weights);  </li>
										<li><i class="ace-icon fa fa-check bigger-110 green"></i>
											Convenient: Export to Excel, auto-complete, formulas, 1-click copy of existing groups, and more;</li>
										<li><i class="ace-icon fa fa-check bigger-110 green"></i>
											Stay Tuned: Follow us on Twitter and on Facebook.  </li>
									</ul>
								</div>
							<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>
			</div><!-- /.main-content -->
			<jsp:include page="authFooter.jsp" />

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->
		<script src="assets/js/jquery-2.1.4.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
<script src="assets/js/jquery-1.11.3.min.js"></script>
<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
			$(window).load(function() {
			    $(".message-loading-overlay").fadeOut("slow");
			})
		</script>
		<script src="assets/js/bootstrap.min.js"></script>
		<script src="assets/js/jquery-ui.custom.min.js"></script>
		<script src="assets/js/jquery.ui.touch-punch.min.js"></script>
		<!-- ace scripts -->
		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->
		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>

		<!-- inline scripts related to this page -->
	</body>
</html>
