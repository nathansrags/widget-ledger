

<table id="simple-table" class="table  table-bordered table-hover">
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
	<%for(int i=0;i<5;i++){ %>
		<tr>
			<td class="center">
				<label class="pos-rel">
					<input type="checkbox" class="ace" />
					<span class="lbl"></span>
				</label>
			</td>
			<td><%=i+1 %></td>
			<td class="center">22/12/2018
			</td>
			<td>Grocery</td>
			<td> Gopi</td>
			<td>100.00</td>
			<td>Nathan</td>
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
		<%} %>
	</tbody>
</table>