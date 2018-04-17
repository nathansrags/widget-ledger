
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="userDetails" value="${displayTO.userDetailsTO }" />
<c:choose>
	<c:when test="${empty userDetails}">
		<table id="simple-table" class="table  table-bordered table-hover">
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
					<td colspan="5" class="well">Please enter the list of persons who are
						sharing expenses: <br /> Click «Add Person» or press the «+» key
						to get started. Once you have entered at least 2 persons, click
						«Enter Expenses» to start entering shared expenses.
					</td>
				</tr>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<table id="simple-table" class="table  table-bordered table-hover">
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
				<%
					for (int i = 0; i < 5; i++) {
				%>
				<tr>
					<td class="center"><label class="pos-rel"> <input
							type="checkbox" class="ace" /> <span class="lbl"></span>
					</label></td>
					<td class="center">Gopinathan</td>
					<td>Gopi</td>
					<td>Admin User</td>
					<td>
						<div class="hidden-sm hidden-xs btn-group">
							<button class="btn btn-xs btn-success">
								<i class="ace-icon fa fa-check bigger-120"></i>
							</button>
							<button class="btn btn-xs btn-info">
								<i class="ace-icon fa fa-pencil bigger-120"></i>
							</button>
							<button class="btn btn-xs btn-danger">
								<i class="ace-icon fa fa-trash-o bigger-120"></i>
							</button>
						</div>
					</td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</c:otherwise>
</c:choose>