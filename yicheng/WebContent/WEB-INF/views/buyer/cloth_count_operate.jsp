<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="../header.jsp" flush="true" />

<body>
	<jsp:include page="buyer_navi.jsp" flush="true" />

	<c:set value="${model.clothOrder}" var="clothOrder" />
	<c:set value="${model.leatherDetails}" var="leatherDetails" />
	<c:set value="${model.fabricDetails}" var="fabricDetails" />
	<c:set value="${model.supportDetails}" var="supportDetails" />

	<div class="row container-body-with-left">
		<div class="col-sm-2 left-navi-div">
			<jsp:include page="../left_color_navi.jsp" flush="true" />
		</div>	
		<div class="col-sm-9">
			<h3>皮衣详情
			<a href="ClothCountDetail?clothId=${clothOrder.cloth.id}&clothColorId=${model.clothColorId}" class="btn btn-primary create-button">查看详情</a>
			</h3>
			<hr />
			<div>
				<p>基本信息</p>
		         <div class="form-group row">
			        <label class="col-sm-2 control-label">款号</label>
			        <div class="col-sm-6">
			        <label>${clothOrder.cloth.type}</label>
			        </div>
			     </div>

	             <div class="form-group row">
	     	        <label for="name" class="col-sm-2 control-label">款名</label>
	     	        <div class="col-sm-6">
	     	        <label>${clothOrder.cloth.name}</label>
	     	        </div>
	     	     </div>

	             <div class="form-group row">
	     	        <label for="name" class="col-sm-2 control-label">采购数量</label>
	     	        <div class="col-sm-6">
	     	        <input type="text" name="buyCount" id="buyCount" value="${null == clothOrder.count ? 0 : clothOrder.count}" />
	     	        </div>
	     	     </div>     
	             <div class="form-group row">
	      	        <label class="col-sm-2">订货单总价</label>
	     	        <label id="orderPrice" class="col-sm-4">
	     	        	<c:if test="${null != model.clothTotalPrice}">${model.clothTotalPrice}</c:if>
	     	        	<c:if test="${null == model.clothTotalPrice}">暂无</c:if>
	     	        </label>     	     
	     	     </div>     	     	     	

	     	     <div class="form-group row">
	     	     <div class="col-sm-2"></div>
	     	     <div class="col-sm-4">
	     	     	<a href="#" id="save_cloth_count" class="btn btn-sm btn-success">保存</a>
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
						<th>颜色</th>
						<th>部位</th>
						<th>单位</th>
						<th>用料</th>
						<th>单价</th>
						<th>数量</th>
						<th>金额</th>
						<th>订购日期</th>
						<th>备注</th>
						<th>操作</th>
						</tr>
						<c:forEach items="${leatherDetails}" var="leatherDetail">
							<tr clothMaterialId="${leatherDetail.id}">
								<td>${leatherDetail.materialName}</td>
								<td>${leatherDetail.materialColor}</td>
								<td>${leatherDetail.part}</td>
								<td>${leatherDetail.unitName}</td>
								<td>${leatherDetail.consumption}</td>
								<td>${leatherDetail.price}</td>
								<td><input type="text" name="count" class="full-width count" value="${leatherDetail.count}"/></td>
								<td>${null == leatherDetail.materialTotalPrice ? "暂无" : leatherDetail.materialTotalPrice}</td>
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
							<th>颜色</th>
							<th>部位</th>
							<th>单位</th>
							<th>用料</th>
							<th>单价</th>
							<th>数量</th>
							<th>金额</th>
							<th>订购日期</th>
							<th>备注</th>
							<th>操作</th>
				     	</tr>
				     	<c:forEach items="${fabricDetails}" var="fabricDetail" >
							<tr clothMaterialId="${fabricDetail.id}">
								<td>${fabricDetail.materialName}</td>
								<td>${fabricDetail.materialColor}</td>
								<td>${fabricDetail.part}</td>
								<td>${fabricDetail.unitName}</td>
								<td>${fabricDetail.consumption}</td>
								<td>${fabricDetail.price}</td>
								<td><input type="text" name="count" class="full-width count" value="${fabricDetail.count}"/></td>
								<td>${null == fabricDetail.materialTotalPrice ? "暂无" : fabricDetail.materialTotalPrice}</td>
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
							<th>颜色</th>
							<th>部位</th>
							<th>单位</th>
							<th>用料</th>
							<th>单价</th>
							<th>数量</th>
							<th>金额</th>
							<th>订购日期</th>
							<th>备注</th>
							<th>操作</th>
		     	     	</tr>
		     	     	<c:forEach items="${supportDetails}" var="supportDetail" >
							<tr clothMaterialId="${supportDetail.id}">
								<td>${supportDetail.materialName}</td>
								<td>${supportDetail.materialColor}</td>
								<td>${supportDetail.part}</td>
								<td>${supportDetail.unitName}</td>
								<td>${supportDetail.consumption}</td>
								<td>${supportDetail.price}</td>
								<td><input type="text" name="count" class="full-width count" value="${supportDetail.count}"/></td>
								<td>${null == supportDetail.materialTotalPrice ? "暂无" : supportDetail.materialTotalPrice}</td>
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

				<div class="margin-top-little">
					<div class="row">
						<div class="col-sm-3"></div>
						<div class="col-sm-4"><a href="ClothCountToProcess" class="btn btn-info btn-lg" >提交</a></div>
					</div>
						
				</div>
	      	   
			</div>		
		</div>
	</div>

<jsp:include page="../footer.jsp" flush="true" />

<script type="text/javascript">
/* variables */
var clothId;
var clothColorId;

var $saveCountBtn = $(".save_count_btn");
var $saveClothCountBtn = $("#save_cloth_count");
var $buyCountInput = $("#buyCount");

$(function() {
	clothId = "${model.clothOrder.cloth.id}";
	clothColorId = "${model.clothColorId}";

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

	$saveClothCountBtn.click(function(e) {
		var buyCount = $buyCountInput.val();
		console.log("clothId", clothId);
		console.log("buyCount: " + buyCount);
		if(checkInt(buyCount)) {
			$.ajax({
				url: "OrderClothSaveCount",
				method: "post",
				data: {
					clothId: clothId,
					buyCount: buyCount
				},
				success: function(result) {
					if(result.resultCode == 0) {
						location.reload();
					}else {
						console.log("save count error");
					}
				}
			});
		}else {
			alert("采购数量格式错误！");
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
			clothColorId: clothColorId,
			count: count,
			remark: remark
		},
		success: function(result) {
			if(result.resultCode == 0) {
				console.log("save count success");
				location.reload();
			}else {
				cnosole.log("save count fail");
			}
		}
	});
}
</script>	
</body>
</html>
