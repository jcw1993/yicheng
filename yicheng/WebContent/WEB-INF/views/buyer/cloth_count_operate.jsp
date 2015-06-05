<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="../header.jsp" flush="true" />

<body>
	<jsp:include page="buyer_navi.jsp" flush="true" />

	<c:set value="${model.cloth}" var="cloth" />
	<c:set value="${model.leatherDetails}" var="leatherDetails" />
	<c:set value="${model.fabricDetails}" var="fabricDetails" />
	<c:set value="${model.supportDetails}" var="supportetails" />

	<div class="container-body">
		<h3>皮衣详情</h3>
		<hr />
		<div>
			<p>基本信息</p>
	         <div class="form-group row">
		        <label class="col-sm-2 control-label">款号</label>
		        <div class="col-sm-6">
		        <input id="cloth_type_input" type="text" class="form-control" name="type" placeholder="款号" value="${cloth.type}" />
		        </div>
		     </div>

             <div class="form-group row">
     	        <label for="name" class="col-sm-2 control-label">款名</label>
     	        <div class="col-sm-6">
     	        <input id="cloth_name_input" type="text" class="form-control" name="name" placeholder="款名" value="${cloth.name}" />
     	        </div>
     	     </div>
			
			<div class="margin-top-little">
				<div class="row title-area">
					<p class="col-sm-7">皮料信息</p>
				</div>		
				<c:if test="${null != leatherDetails}">
				<table id="leather_table" class="table table-striped table-bordered table-hover table-responsive">
					<tr>
					<th>项目</th>
					<th>部位</th>
					<th>单位</th>
					<th>用料</th>
					<th>单价</th>
					<th>金额</th>
					<th>数量</th>
					<th>订购日期</th>
					<th>备注</th>
					<th>操作</th>
					</tr>
					<c:forEach items="${leatherDetails}" var="leatherDetail">
						<tr clothMaterialId="${leatherDetail.id}">
							<td>${leatherDetail.materialName}</td>
							<td>${leatherDetail.part}</td>
							<td>${leatherDetail.unitName}</td>
							<td>${leatherDetail.consumption}</td>
							<td>${leatherDetail.price}</td>
							<td>${leatherDetail.consumption * leatherDetail.price}</td>
							<td><input type="text" name="count" class="full-width count" value="${leatherDetail.count}"/></td>
							<td></td>
							<td><input type="text" name="remark" class="full-width remark" value="${leatherDetail.remark}" /></td>
							<td><a href="#" class="save_count_btn">保存</a></td>
						</tr>
					</c:forEach>
				</table>
				</c:if>
				<c:if test="${null == leatherDetails}">
					<p>暂无数据</p>
				</c:if>
			</div>

			<div class="margin-top-little">
		 	     <div class="row title-area">
			     	<p class="col-sm-7">面料信息</p>
			     </div>		
			     <c:if test="${null != fabricDetails}">
			     <table id="fabric_table" class="table table-striped table-bordered table-hover table-responsive">
			     	<tr>
						<th>项目</th>
						<th>部位</th>
						<th>单位</th>
						<th>用料</th>
						<th>单价</th>
						<th>金额</th>
						<th>数量</th>
						<th>订购日期</th>
						<th>备注</th>
						<th>操作</th>
			     	</tr>
			     	<c:forEach items="${fabricDetails}" var="fabricDetail" >
						<tr clothMaterialId="${fabricDetail.id}">
							<td>${fabricDetail.materialName}</td>
							<td>${fabricDetail.part}</td>
							<td>${fabricDetail.unitName}</td>
							<td>${fabricDetail.consumption}</td>
							<td>${fabricDetail.price}</td>
							<td>${fabricDetail.consumption * v.price}</td>
							<td><input type="text" name="count" class="full-width count" value="${fabricDetail.count}"/></td>
							<td></td>
							<td><input type="text" name="remark" class="full-width remark" value="${fabricDetail.remark}" /></td>
							<td><a href="#" class="save_count_btn">保存</a></td>
						</tr>
			     	</c:forEach>
			     </table>
			     </c:if>
			     <c:if test="${null == fabricDetails}">
			     	<p>暂无数据</p>
			     </c:if>
			</div>


			<div class="margin-top-little">
				 <div class="row title-area">
			     	<p class="col-sm-7">辅料信息</p>
	     	     </div>		
	     	     <c:if test="${null != supportDetails}">
	     	     <table id="support_table" class="table table-striped table-bordered table-hover table-responsive">
	     	     	<tr>
						<th>项目</th>
						<th>部位</th>
						<th>单位</th>
						<th>用料</th>
						<th>单价</th>
						<th>金额</th>
						<th>数量</th>
						<th>订购日期</th>
						<th>备注</th>
						<th>操作</th>
	     	     	</tr>
	     	     	<c:forEach items="${supportDetails}" var="supportDetail" >
						<tr clothMaterialId="${supportDetail.id}">
							<td>${supportDetail.materialName}</td>
							<td>${supportDetail.part}</td>
							<td>${supportDetail.unitName}</td>
							<td>${supportDetail.consumption}</td>
							<td>${supportDetail.price}</td>
							<td>${supportDetail.consumption * supportDetail.price}</td>
							<td><input type="text" name="count" class="full-width count" value="${supportDetail.count}"/></td>
							<td></td>
							<td><input type="text" name="remark" class="full-width remark" value="${supportDetail.remark}" /></td>
							<td><a href="#" class="save_count_btn">保存</a></td>
						</tr>
					</c:forEach>
	     	     </table>
	     	     </c:if>
	     	     <c:if test="${null == supportDetails}">
	     	     	<p>暂无数据</p>
	     	     </c:if>
			</div>
      	   
		</div>
	</div>

	<div id="material_create_modal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">材料信息</h4>
				</div>
				<form id="material_create_form">
					<div id="material_create_content" class="modal-body">
						<input type="hidden" name="clothId" value="" />
				        <div class="form-group row">
					        <label class="col-sm-2 control-label">项目</label>
					        <div class="col-sm-6">
					            <!-- <input type="text" class="form-control" name="material_name" placeholder="项目" /> -->
					            <select id="material_select" name="materialId">
					            	
					            </select>
					        </div>
					    </div>				
				        <div class="form-group row">
					        <label for="part" class="col-sm-2 control-label">部位</label>
					        <div class="col-sm-6">
					            <input type="text" id="part" class="form-control" name="part" placeholder="部位" />
					        </div>
					    </div>
				        <div class="form-group row">
					        <label class="col-sm-2 control-label">单位</label>
					        <div class="col-sm-6">
					            <input type="text" id="unitName" class="form-control" name="unitName" placeholder="单位" />
					        </div>
					    </div>
				        <div class="form-group row">
					        <label for="consumption" class="col-sm-2 control-label">用料</label>
					        <div class="col-sm-6">
					            <input type="text" id="consumption" class="form-control" name="consumption" placeholder="用料" />
					        </div>
					    </div>
				        <div class="form-group row">
					        <label for="remark" class="col-sm-2 control-label">备注</label>
					        <div class="col-sm-6">
					            <input type="text" id="remark" class="form-control" name="remark" placeholder="备注" />
					        </div>
					    </div>	
					</div>
					<div class="modal-footer">
						<button id="material_create_submit" type="button"
							class="btn btn-primary" data-dismiss="modal">保存</button>
					</div>
				</form>
			
			</div>
		</div>
	</div>


<script type="text/javascript">
/* variables */
var clothId;

var $saveCountBtn = $(".save_count_btn");

$(function() {
	clothId = "${model.cloth.id}";

	$saveCountBtn.click(function(e) {
		var clothMaterialId = $(this).parent().parent().attr("clothMaterialId");
		var count = $(this).parent().parent().find("input.count").val();
		var remark = $(this).parent().parent().find("input.remark").val();
		if(checkInt(count)) {
			saveClothMaterialCount(clothMaterialId, count, remark);
		}else {
			alert("数量为整数！");
		}
		
	});
});

function saveClothMaterialCount(clothMaterialId, count, remark) {
	$.ajax({
		url: "ClothMaterialSaveCount",
		method: "post",
		data: {
			clothId: clothId,
			clothMaterialId: clothMaterialId,
			count: count,
			remark: remark
		},
		success: function(result) {
			if(result.resultCode == 0) {
				console.log("save count success");
			}else {
				cnosole.log("save count fail");
			}
		}
	});
}
</script>	
</body>
</html>
