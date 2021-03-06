<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:include page="../header.jsp" flush="true" />

<body>
	<jsp:include page="manager_navi.jsp" flush="true" />

	<div class="container-body">
		<h3>采购单</h3>
		<hr />

		<c:if test="${null != model.clothCounted}">
			<div class="navbar-form search-div">
			        <div class="form-group">
			          <input id="search_input" type="text" class="form-control" placeholder="请输入款号或款名">
			        </div>
			        <button id="search_submit" type="submit" class="btn btn-default">搜索</button>
			</div>
			<table class="table table-striped table-bordered table-hover table-responsive">
				<tr>
					<th>创建时间</th>
					<th>款号</th>
					<th>款名</th>
					<th>颜色</th>
					<th>买手</th>
					<th>皮料</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${model.clothToCount}" var="cloth">
					<tr>
						<td><fmt:formatDate value="${cloth.createdTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${cloth.type}</td>
						<td>${cloth.name}</td>
						<td>${cloth.color}</td>
						<td>${cloth.client}</td>
						<td>
							<c:forEach items="${cloth.leathers}" var="leather">
							${leather.materialName} <br/>
							</c:forEach>
						</td>
						
						<td>
							<a href="ClothCountDetail?clothId=${cloth.id}">详情</a>
							<a href="ClothCountOperate?clothId=${cloth.id}">处理</a>
						</td>
					</tr>
				</c:forEach>			
			</table>
			<jsp:include page="../pagination.jsp" flush="true" />		
		</c:if>
		<c:if test="${null == model.clothCounted}">
			<p>暂无数据</p>
		</c:if>
	</div>

<jsp:include page="../footer.jsp" flush="true" />

<script type="text/javascript">

var searchUrl = "SearchInCountManage";

var $searchInput = $("#search_input");
var $searchSubmit = $("#search_submit");



$searchInput.keyup(function(e){
	if(e.keyCode == 13) {
		$searchSubmit.trigger("click");
	}
});

$searchSubmit.click(function(e) {
	var keyword = $searchInput.val();
	if(keyword || keyword.trim() != "") {
		keyword = keyword.trim();
		window.location = searchUrl + "?keyword=" + keyword;
	}
});

</script>	
</body>
</html>
