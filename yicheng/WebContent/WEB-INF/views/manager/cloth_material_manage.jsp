<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="../header.jsp" flush="true" />

<body>
	<jsp:include page="manager_navi.jsp" flush="true" />

	<div class="container-body">
		<h3>面辅料清单</h3>
		<hr />
		<c:forEach items="${model.clothes}" var="cloth">
			<div class="list-item row">
				<p class="col-sm-5">${cloth.name}</p>
				<p class="col-sm-5">${cloth.type}</p>
				<a href="ClothMaterialDetail?clothId=${cloth.id}" class="col-sm-1">详情</a>
			</div>
		</c:forEach>									
	</div>


<script type="text/javascript">

</script>	
</body>
</html>
