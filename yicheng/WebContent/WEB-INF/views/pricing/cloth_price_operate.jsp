<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="../header.jsp" flush="true" />

<body>
	<jsp:include page="pricing_navi.jsp" flush="true" />

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
			        <label>${cloth.type}</label>
		        </div>
		     </div>

             <div class="form-group row">
     	        <label for="name" class="col-sm-2 control-label">款名</label>
     	        <div class="col-sm-6">
	     	        <label>${cloth.name}</label>
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
						<th>备注</th>
						<th>保存</th>	
					</tr>
					<c:forEach items="${leatherDetails}" var="leatherDetail">
						<tr clothMaterialId="${leatherDetail.id}">
							<td>${leatherDetail.materialName}</td>
							<td>${leatherDetail.part}</td>
							<td>${leatherDetail.unitName}</td>
							<td>${leatherDetail.consumption}</td>
							<td><input type="text" name="price" class="full-width" value="${leatherDetail.price}" /></td>
							<td><label class="priceTotal">${leatherDetail.consumption * (null == leatherDetail.price ? 0.0 : leatherDetail.price)}</label></td>
							<td>${leatherDetail.remark}</td>
							<td><a href="#" class="save_price_btn">保存</a></td>
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
						<th>备注</th>
						<th>保存</th>
			     	</tr>
			     	<c:forEach items="${fabricDetails}" var="fabricDetail" >
						<tr clothMaterialId="${fabricDetail.id}">
							<td>${fabricDetail.materialName}</td>
							<td>${fabricDetail.part}</td>
							<td>${fabricDetail.unitName}</td>
							<td>${fabricDetail.consumption}</td>
							<td><input type="text" name="price" value="${fabricDetail.price}" /></td>
							<td><label class="priceTotal">${fabricDetail.consumption * (null == fabricDetail.price ? 0.0 : fabricDetail.price)}</label></td>
							<td>${fabricDetail.remark}</td>
							<td><a href="#" class="save_price_btn">保存</a></td>
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
						<th>备注</th>
						<th>保存</th>
	     	     	</tr>
	     	     	<c:forEach items="${supportDetails}" var="supportDetail" >
						<tr clothMaterialId="${supportDetail.id}">
							<td>${supportDetail.materialName}</td>
							<td>${supportDetail.part}</td>
							<td>${supportDetail.unitName}</td>
							<td>${supportDetail.consumption}</td>
							<td><input type="text" name="price" value="${supportDetail.price}" /></td>
							<td><label class="priceTotal">${supportDetail.consumption * (null == supportDetail.price ? 0.0 : supportDetail.price)}</label></td>
							<td>${supportDetail.remark}</td>
							<td><a href="#" class="save_price_btn">保存</a></td>
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

var $savePriceBtn = $(".save_price_btn");

$(function() {
	clothId = "${model.cloth.id}";

	$savePriceBtn.click(function(e) {
		var clothMaterialId = $(this).parent().parent().attr("clothMaterialId");
		console.log("clothMaterialId:" + clothMaterialId);
		var price = $(this).parent().parent().find("input").val();
		console.log("price: " + price);
		saveClothMaterialPrice(clothMaterialId, price);
	});

});

function saveClothMaterialPrice(clothMaterialId, price) {
	$.ajax({
		url: "ClothMaterialSavePrice",
		method: "post",
		data: {
			clothId: clothId,
			clothMaterialId: clothMaterialId,
			price: price
		},
		success: function(result) {
			if(result.resultCode == 0) {
				console.log("save price success");
			}else {
				cnosole.log("save price fail");
			}
		}
	});
}

</script>	
</body>
</html>
