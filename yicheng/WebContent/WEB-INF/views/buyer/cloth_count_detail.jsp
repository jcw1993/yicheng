<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fmt"%>

<jsp:include page="../header.jsp" flush="true" />

<body>
	<jsp:include page="buyer_navi.jsp" flush="true" />

	<c:set value="${model.cloth}" var="cloth" />
	<c:set value="${model.clothMaterialDetails}" var="clothMaterialDetails" />
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
				<table class="table table-striped table-bordered table-hover table-responsive">
					<tr>
						<th>项目</th>
						<th>部位</th>
						<th>单位</th>
						<th>用料</th>
						<th>单价</th>
						<th>金额</th>
						<th>订购数量</th>
						<th>订购日期</th>
						<th>备注</th>
					</tr>
					<c:forEach items="${clothMaterialDetails}" var="clothMaterialDetail">
					<c:if test="${clothMaterialDetail.materialType == 0}">
						<tr>
							<td>${clothMaterialDetail.materialId}</td>
							<td>${clothMaterialDetail.part}</td>
							<td>${clothMaterialDetail.unitName}</td>
							<td>${clothMaterialDetail.consumption}</td>
							<td>${clothMaterialDetail.price}</td>
							<td>${clothMaterialDetail.consumption * clothMaterialDetail.price}</td>
							<td>${clothMaterialDetail.orderCount}</td>
							<td>
								<fmt:formatDate value="${clothMaterialDetail.orderDate}" pattern="yyyy-MM-dd" />
							</td>
							<td>${clothMaterialDetail.remark}</td>
						</tr>
					</c:if>
					</c:forEach>
				</table>
			</div>

			<div class="margin-top-little">
		 	     <div class="row title-area">
			     	<p class="col-sm-7">面料信息</p>
			     </div>		
			     <table class="table table-striped table-bordered table-hover table-responsive">
			     	<tr>
			     		<th>项目</th>
			     		<th>部位</th>
			     		<th>单位</th>
			     		<th>用料</th>
			     		<th>单价</th>
			     		<th>金额</th>
			     		<th>订购数量</th>
			     		<th>订购金额</th>
			     		<th>备注</th>
			     	</tr>
			     	<c:forEach items="${clothMaterialDetails}" var="clothMaterialDetail" >
					<c:if test="${clothMaterialDetail.materialType == 1}">
						<tr>
							<td>${clothMaterialDetail.materialId}</td>
							<td>${clothMaterialDetail.part}</td>
							<td>${clothMaterialDetail.unitName}</td>
							<td>${clothMaterialDetail.consumption}</td>
							<td>${clothMaterialDetail.price}</td>
							<td>${clothMaterialDetail.consumption * clothMaterialDetail.price}</td>
							<td>${clothMaterialDetail.orderCount}</td>
							<td>
								<fmt:formatDate value="${clothMaterialDetail.orderDate}" pattern="yyyy-MM-dd" />
							</td>
							<td>${clothMaterialDetail.remark}</td>
						</tr>
					</c:if>
			     	</c:forEach>
			     </table>
			</div>


			<div class="margin-top-little">
				 <div class="row title-area">
	     	     	<p class="col-sm-7">辅料信息</p>
	     	     </div>		
	     	     <table class="table table-striped table-bordered table-hover table-responsive">
	     	     	<tr>
	     	     		<th>项目</th>
	     	     		<th>部位</th>
	     	     		<th>单位</th>
	     	     		<th>用料</th>
	     	     		<th>单价</th>
	     	     		<th>金额</th>
	     	     		<th>订购数量</th>
	     	     		<th>订购金额</th>
	     	     		<th>备注</th>
	     	     	</tr>
	     	     	<c:forEach items="${clothMaterialDetails}" var="clothMaterialDetail" >
					<c:if test="${clothMaterialDetail.materialType == 2}">
						<tr>
							<td>${clothMaterialDetail.materialId}</td>
							<td>${clothMaterialDetail.part}</td>
							<td>${clothMaterialDetail.unitName}</td>
							<td>${clothMaterialDetail.consumption}</td>
							<td>${clothMaterialDetail.price}</td>
							<td>${clothMaterialDetail.consumption * clothMaterialDetail.price}</td>
							<td>${clothMaterialDetail.orderCount}</td>
							<td>
								<fmt:formatDate value="${clothMaterialDetail.orderDate}" pattern="yyyy-MM-dd" />
							</td>
							<td>${clothMaterialDetail.remark}</td>
						</tr>
					</c:if>	     	     	
					</c:forEach>
	     	     </table>
			</div>
      	   
		</div>


	</div>


<script type="text/javascript">

</script>	
</body>
</html>
