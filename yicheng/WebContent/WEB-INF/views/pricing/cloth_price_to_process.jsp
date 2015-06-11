<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:include page="../header.jsp" flush="true" />

<body>
	<jsp:include page="pricing_navi.jsp" flush="true" />

	<div class="container-body">

	<div class="tab-navi">
		<div class="tab tab-selected">
			<a href="#">待处理</a>
		</div>
		<div class="tab">
			<a href="ClothPriceProcessed">已处理</a>
		</div>
	</div>

		<c:if test="${null != model.clothToPrice}">
			<table class="table table-striped table-bordered table-hover table-responsive">
				<tr>
					<th>款号</th>
					<th>款名</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${model.clothToPrice}" var="cloth">
					<tr>
						<td>${cloth.type}</td>
						<td>${cloth.name}</td>
						<td><fmt:formatDate value="${cloth.createdTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>
							<a href="ClothPriceDetail?clothId=${cloth.id}">详情</a>
							<a href="ClothPriceOperate?clothId=${cloth.id}">处理</a>
						</td>
					</tr>
				</c:forEach>			
			</table>
			<jsp:include page="../pagination.jsp" flush="true" />		
		</c:if>
		<c:if test="${null == model.clothToPrice}">
		<p>暂无数据</p>
		</c:if>
	</div>

<jsp:include page="../footer.jsp" flush="true" />

<script type="text/javascript">

</script>	
</body>
</html>
