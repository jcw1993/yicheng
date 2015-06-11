<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:include page="../header.jsp" flush="true" />

<body>
	<jsp:include page="proofing_navi.jsp" flush="true" />

	<div class="container-body">
		<h3>历史记录<a id="cloth-create" href="CreateCloth" class="btn btn-primary create-button">创建皮衣</a></h3>
		<hr />

		<table class="table table-striped table-bordered table-hover table-responsive">
			<tr>
				<th>款号</th>
				<th>款名</th>
				<th>创建日期</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${model.clothes}" var="cloth">
				<tr>
					<td>${cloth.type}</td>
					<td>${cloth.name}</td>
					<td><fmt:formatDate value="${cloth.createdTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<a href="ClothMaterialDetail?clothId=${cloth.id}">详情</a>
						<a href="ClothMaterialOperate?clothId=${cloth.id}">修改</a>
					</td>
				</tr>
			</c:forEach>			
		</table>
		<jsp:include page="../pagination.jsp" flush="true" />			
	</div>

<jsp:include page="../footer.jsp" flush="true" />

<script type="text/javascript">

</script>	
</body>
</html>
