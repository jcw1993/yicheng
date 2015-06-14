<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="../header.jsp" flush="true" />

<body>
	<jsp:include page="manager_navi.jsp" flush="true" />

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
			<a href="ExportPriceExcel?clothId=${cloth.id}&clothColorId=${model.clothColorId}" class="btn btn-primary create-button">导出报价单</a>
			</h3>
			<hr />
			<div>
				<p class="info-title">基本信息</p>
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
                  <div class="form-group row">
          	        <label for="name" class="col-sm-2 control-label">颜色</label>
          	        <div class="col-sm-6">
     	     	        <label>${cloth.color}</label>
          	        </div>
          	     </div>

                  <div class="form-group row">
          	        <label for="name" class="col-sm-2 control-label">买手</label>
          	        <div class="col-sm-6">
     	     	        <label>${cloth.client}</label>
          	        </div>
          	     </div>

                  <div class="form-group row">
          	        <label for="name" class="col-sm-2 control-label">供应商</label>
          	        <div class="col-sm-6">
     	     	        <label>${cloth.supplier}</label>
          	        </div>
          	     </div>

                  <div class="form-group row">
          	        <label for="name" class="col-sm-2 control-label">备注</label>
          	        <div class="col-sm-6">
     	     	        <label class="remark">${cloth.remark}</label>
          	        </div>
          	     </div>
     			<c:if test="${null != cloth.imageContent}">
                  <div class="form-group row">
          	        <label for="name" class="col-sm-2 control-label">效果图</label>
          	        <div class="col-sm-6">
     	     	        <img src="data:image;base64,${cloth.imageContent}" class="image-preview" />
          	        </div>
          	     </div>		
          	     </c:if>	
				
				<div class="margin-top-little">
					<div class="row title-area">
						<p class="col-sm-7 info-title">皮料信息</p>
					</div>		
					<c:if test="${null != leatherDetails}">
					<table class="table table-striped table-bordered table-hover table-responsive">
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
						</tr>
						<c:forEach items="${leatherDetails}" var="leatherDetail">
							<tr>
								<td>${leatherDetail.materialName}</td>
								<td>${leatherDetail.materialColor}</td>
								<td>${leatherDetail.part}</td>
								<td>${leatherDetail.unitName}</td>
								<td>${leatherDetail.consumption}</td>
								<td>${null == leatherDetail.estimatedPrice ? "暂无" : leatherDetail.estimatedPrice}</td>
								<td>${null == leatherDetail.price ? "暂无" : leatherDetail.price}</td>
								<td class="blue">${null == leatherDetail.price ? "暂无" : leatherDetail.consumption *  leatherDetail.price}</td>
								<td class="remark">${leatherDetail.remark}</td>
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
				     	<p class="col-sm-7 info-title">面料信息</p>
				     </div>		
				     <c:if test="${null != fabricDetails}">
				     <table class="table table-striped table-bordered table-hover table-responsive">
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
				     	</tr>
				     	<c:forEach items="${fabricDetails}" var="fabricDetail" >
							<tr>
								<td>${fabricDetail.materialName}</td>
								<td>${fabricDetail.materialColor}</td>
								<td>${fabricDetail.part}</td>
								<td>${fabricDetail.unitName}</td>
								<td>${fabricDetail.consumption}</td>
								<td>${null == fabricDetail.estimatedPrice ? "暂无" : fabricDetail.estimatedPrice}</td>
								<td>${null == fabricDetail.price ? "暂无" : fabricDetail.price}</td>
								<td class="blue">${null == fabricDetail.price ? "暂无" : fabricDetail.consumption *  fabricDetail.price}</td>
								<td class="remark">${fabricDetail.remark}</td>
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
		     	     	<p class="col-sm-7 info-title">辅料信息</p>
		     	     </div>		

		     	     	<c:if test="${null != supportDetails}">
		     	     	<table class="table table-striped table-bordered table-hover table-responsive">
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
		     	     		</tr>
		     	     	<c:forEach items="${supportDetails}" var="supportDetail" >
							<tr>
								<td>${supportDetail.materialName}</td>
								<td>${supportDetail.materialColor}</td>
								<td>${supportDetail.part}</td>
								<td>${supportDetail.unitName}</td>
								<td>${supportDetail.consumption}</td>
								<td>${null == supportDetail.estimatedPrice ? "暂无" : supportDetail.estimatedPrice}</td>
								<td>${null == supportDetail.price ? "暂无" : supportDetail.price}</td>
								<td class="blue">${null == supportDetail.price ? "暂无" : supportDetail.consumption *  supportDetail.price}</td>
								<td class="remark">${supportDetail.remark}</td>
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
	</div>

<jsp:include page="../footer.jsp" flush="true" />

<script type="text/javascript">

</script>	
</body>
</html>
