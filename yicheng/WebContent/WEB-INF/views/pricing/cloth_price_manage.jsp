<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="../header.jsp" flush="true" />

<body>
	<jsp:include page="pricing_navi.jsp" flush="true" />

	<div class="container-body">
		<h3>待处理</h3>
		<hr />
		<c:if test="${null != model.clothToPrice}">
		<c:forEach items="${model.clothToPrice}" var="cloth">
			<div class="list-item row">
				<p class="col-sm-5">${cloth.name}</p>
				<p class="col-sm-5">${cloth.type}</p>
				<a href="ClothPriceDetail?clothId=${cloth.id}" class="col-sm-1">详情</a>
				<a href="ClothPriceOperate?clothId=${cloth.id}" class="col-sm-1" clothId="${cloth.id}">处理</a>
			</div>
		</c:forEach>	
		</c:if>
		<c:if test="${null == model.clothToPrice}">
		<p>暂无数据</p>
		</c:if>

		<h3>已处理</h3>		
		<hr />
		<c:if test="${null != model.clothPriced}">
		<c:forEach items="${model.clothPriced}" var="cloth">
			<div class="list-item row">
				<p class="col-sm-5">${cloth.name}</p>
				<p class="col-sm-5">${cloth.type}</p>
				<a href="ClothPriceDetail?clothId=${cloth.id}" class="col-sm-1">详情</a>
				<a href="ClothPriceOperate?clothId=${cloth.id}" class="col-sm-1" clothId="${cloth.id}">修改</a>
			</div>
		</c:forEach>	
		</c:if>
		<c:if test="${null == model.clothPriced}">
			<p>暂无数据</p>
		</c:if>


	</div>


<script type="text/javascript">

</script>	
</body>
</html>
