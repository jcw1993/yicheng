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
	<c:set value="${model.supportDetails}" var="supportDetails" />

	<div class="row container-body-with-left">
		<div class="col-sm-2 left-navi-div">
			<jsp:include page="../left_color_navi.jsp" flush="true" />
		</div>	
		<div class="col-sm-9">
				<h3>皮衣详情
				<a href="ClothPriceDetail?clothId=${cloth.id}&clothColorId=${model.clothColorId}" class="btn btn-primary create-button">查看详情</a>
				</h3>
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
								<th>颜色</th>
								<th>部位</th>
								<th>单位</th>
								<th>用料</th>
								<th>估价</th>
								<th>单价</th>
								<th>金额</th>
								<th>备注</th>
								<th>保存</th>	
							</tr>
							<c:forEach items="${leatherDetails}" var="leatherDetail">
								<tr clothMaterialId="${leatherDetail.id}">
									<td>${leatherDetail.materialName}</td>
									<td>${leatherDetail.materialColor}</td>
									<td>${leatherDetail.part}</td>
									<td>${leatherDetail.unitName}</td>
									<td>${leatherDetail.consumption}</td>
									<td>${null == leatherDetail.estimatedPrice ? "暂无" : leatherDetail.estimatedPrice}</td>
									<td><input type="text" name="price" class="full-width price" value="${leatherDetail.price}" /></td>
									<td><label class="priceTotal blue">${leatherDetail.consumption * (null == leatherDetail.price ? 0.0 : leatherDetail.price)}</label></td>
									<td><input type="text" name="remark" class="full-width remark" value="${leatherDetail.remark}" /></td>
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
								<th>颜色</th>
								<th>部位</th>
								<th>单位</th>
								<th>用料</th>
								<th>估价</th>
								<th>单价</th>
								<th>金额</th>
								<th>备注</th>
								<th>保存</th>
					     	</tr>
					     	<c:forEach items="${fabricDetails}" var="fabricDetail" >
								<tr clothMaterialId="${fabricDetail.id}">
									<td>${fabricDetail.materialName}</td>
									<td>${fabricDetail.materialColor}</td>
									<td>${fabricDetail.part}</td>
									<td>${fabricDetail.unitName}</td>
									<td>${fabricDetail.consumption}</td>
									<td>${null == fabricDetail.estimatedPrice ? "暂无" : fabricDetail.estimatedPrice}</td>
									<td><input type="text" name="price" class="full-width price" value="${fabricDetail.price}" /></td>
									<td><label class="priceTotal blue">${fabricDetail.consumption * (null == fabricDetail.price ? 0.0 : fabricDetail.price)}</label></td>
									<td><input type="text" name="remark" class="full-width remark" value="${fabricDetail.remark}" /></td>
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
								<th>颜色</th>
								<th>部位</th>
								<th>单位</th>
								<th>用料</th>
								<th>估价</th>
								<th>单价</th>
								<th>金额</th>
								<th>备注</th>
								<th>保存</th>
			     	     	</tr>
			     	     	<c:forEach items="${supportDetails}" var="supportDetail" >
								<tr clothMaterialId="${supportDetail.id}">
									<td>${supportDetail.materialName}</td>
									<td>${supportDetail.materialColor}</td>
									<td>${supportDetail.part}</td>
									<td>${supportDetail.unitName}</td>
									<td>${supportDetail.consumption}</td>
									<td>${null == supportDetail.estimatedPrice ? "暂无" : supportDetail.estimatedPrice}</td>
									<td><input type="text" name="price" class="full-width price" value="${supportDetail.price}" /></td>
									<td><label class="priceTotal blue">${supportDetail.consumption * (null == supportDetail.price ? 0.0 : supportDetail.price)}</label></td>
									<td><input type="text" name="remark" class="full-width remark" value="${supportDetail.remark}" /></td>
									<td><a href="#" class="save_price_btn">保存</a></td>
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
			      	   		<div class="col-sm-4"><a href="ClothPriceToProcess" class="btn btn-info btn-lg" >提交</a></div>
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

var $savePriceBtn = $(".save_price_btn");

$(function() {
	clothId = "${model.cloth.id}";
	clothColorId = "${model.clothColorId}";

	$savePriceBtn.click(function(e) {
		var clothMaterialId = $(this).parent().parent().attr("clothMaterialId");
		var price = $(this).parent().parent().find("input.price").val();
		var remark = $(this).parent().parent().find("input.remark").val();
		if(checkDouble(price)) {
			saveClothMaterialPrice(clothMaterialId, price, remark);
		}else {
			alert("单价格式错误！");
		}
	});

});

function saveClothMaterialPrice(clothMaterialId, price, remark) {
	$.ajax({
		url: "ClothMaterialSavePrice",
		method: "post",
		data: {
			clothId: clothId,
			clothMaterialId: clothMaterialId,
			clothColorId: clothColorId,
			price: price, 
			remark: remark
		},
		success: function(result) {
			if(result.resultCode == 0) {
				console.log("save price success");
				location.reload();
			}else {
				cnosole.log("save price fail");
			}
		}
	});
}

</script>	
</body>
</html>
