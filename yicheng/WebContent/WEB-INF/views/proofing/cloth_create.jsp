<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="../header.jsp" flush="true" />

<body>
	<jsp:include page="proofing_navi.jsp" flush="true" />

	<div class="container-body">
		<h3>创建皮衣</h3>
		<hr />
		<div>
			<form id="create-form" class="form-horizontal" method="post" action="#">
				<p>基本信息</p>
		         <div class="form-group row">
			        <label for="type" class="col-sm-2 control-label">款号</label>
			        <div class="col-sm-6">
			            <input type="text" class="form-control" name="type" placeholder="款号" />
			        </div>
			     </div>

	             <div class="form-group row">
	     	        <label for="name" class="col-sm-2 control-label">款名</label>
	     	        <div class="col-sm-6">
	     	            <input type="text" class="form-control" name="name" placeholder="款名" />
	     	        </div>
	     	     </div>

				<div class="row title-area">
					<p class="col-sm-7">皮料信息</p>
					<div class="col-sm-2">
						<a href="#" id="add_leather_btn" class="btn btn-success btn-sm" >添加</a>	
					</div>
				</div>		    

				<div class="row title-area">
					<p class="col-sm-7">面料信息</p>
					<div class="col-sm-2">
						<a href="#" id="add_fabric_btn" class="btn btn-success btn-sm" >添加</a>	
					</div>
				</div>			

				<div class="row title-area">
					<p class="col-sm-7">辅料信息</p>
					<div class="col-sm-2">
						<a href="#" id="add_support_btn" class="btn btn-success btn-sm" >添加</a>	
					</div>
				</div>
	        </form>
		</div>

	</div>


	<div id="material_add_modal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">材料信息</h4>
				</div>
				<div id="material_add_content" class="modal-body">
			        <div class="form-group row">
				        <label for="material_name" class="col-sm-2 control-label">项目</label>
				        <div class="col-sm-6">
				            <input type="text" class="form-control" name="material_name" placeholder="项目" />
				        </div>
				    </div>				
			        <div class="form-group row">
				        <label for="part" class="col-sm-2 control-label">部位</label>
				        <div class="col-sm-6">
				            <input type="text" class="form-control" name="part" placeholder="部位" />
				        </div>
				    </div>
			        <div class="form-group row">
				        <label for="unit_name" class="col-sm-2 control-label">单位</label>
				        <div class="col-sm-6">
				            <input type="text" class="form-control" name="unit_name" placeholder="单位" />
				        </div>
				    </div>
			        <div class="form-group row">
				        <label for="consumption" class="col-sm-2 control-label">用料</label>
				        <div class="col-sm-6">
				            <input type="text" class="form-control" name="consumption" placeholder="用料" />
				        </div>
				    </div>
			        <div class="form-group row">
				        <label for="remark" class="col-sm-2 control-label">备注</label>
				        <div class="col-sm-6">
				            <input type="text" class="form-control" name="remark" placeholder="备注" />
				        </div>
				    </div>	
				</div>
				<div class="modal-footer">
					<button id="memberCreateSubmit" type="button"
						class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>

<script type="text/javascript">
var $add_leather_btn = $("#add_leather_btn");
var $add_fabric_btn = $("#add_fabric_btn");
var $add_support_btn = $("#add_support_btn");

var $material_add_modal = $("#material_add_modal");

$add_leather_btn.click(function(e) {
	console.log("add leather");
	$material_add_modal.modal();
});

$add_fabric_btn.click(function(e) {
	console.log("add leather");
	$material_add_modal.modal();
});

$add_support_btn.click(function(e) {
	console.log("add leather");
	$material_add_modal.modal();
});
</script>	
</body>
</html>
