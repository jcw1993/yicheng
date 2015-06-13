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
		<h3>
		历史记录
		<a id="cloth-create" href="CreateCloth" class="btn btn-primary create-button">创建皮衣</a></h3>
		<hr />

		<div class="navbar-form search-div">
		        <div class="form-group">
		          <input id="search_input" type="text" class="form-control" placeholder="请输入款号或款名">
		        </div>
		        <button id="search_submit" type="submit" class="btn btn-default">搜索</button>
		</div>

		<table class="table table-striped table-bordered table-hover table-responsive">
			<tr>
				<th>款号</th>
				<th>款名</th>
				<th>颜色</th>
				<th>买手</th>
				<th>供应商</th>
				<!-- <th>交货期</th> -->
				<th>创建时间</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${model.clothes}" var="cloth">
				<tr clothId="${cloth.id}">
					<td>${cloth.type}</td>
					<td>${cloth.name}</td>
					<td>${cloth.color}</td>
					<td>${cloth.client}</td>
					<td>${cloth.supplier}</td>
					<td><fmt:formatDate value="${cloth.createdTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<a href="ClothMaterialDetail?clothId=${cloth.id}">详情</a>
						<a href="ClothMaterialOperate?clothId=${cloth.id}">修改</a>
						<a href"#">新建版本</a>
						<a href"#" class="create-color">添加颜色</a>
					</td>
				</tr>
			</c:forEach>			
		</table>
		<jsp:include page="../pagination.jsp" flush="true" />			
	</div>

	<div id="color_create_modal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">添加颜色</h4>
				</div>
				<div id="material_create_content" class="modal-body">
			        <div class="form-group row">
				        <label class="col-sm-2 control-label">颜色</label>
				        <div class="col-sm-6">
				            <input id="color-input" type="text" class="form-control" name="color" placeholder="颜色" />
				        </div>
				    </div>			
				</div>
				<div class="modal-footer">
					<button id="color_create_submit" type="button"
						class="btn btn-primary" data-dismiss="modal">保存</button>
				</div>
			</div>
		</div>
	</div>

<jsp:include page="../footer.jsp" flush="true" />

<script type="text/javascript">
var $createColorModal = $("#color_create_modal");
var $colorInput = $("#color-input");
var $submitBtn = $("#color_create_submit");
var $createColorBtn = $(".create-color");

var $searchInput = $("#search_input");
var $searchSubmit = $("#search_submit");

var currentClothId;
var searchUrl = "SearchInAll";

$createColorBtn.click(function(e) {
	$createColorModal.modal();
	var clothId = $(this).parent().parent().attr("clothId");
	currentClothId = clothId;

});

$submitBtn.click(function(e) {
	var color = $colorInput.val();
	if(!color || color.trim() == "") {
		alert("颜色不能为空");
	}
	color = color.trim();

	$.ajax({
		url: "CreateNewColor",
		method: "post",
		data: {
			clothId: currentClothId,
			color: color
		},
		success: function(result) {
			if(result.resultCode == 0) {
				location.reload();
			}else {
				console.log("create new color failed, " + result.message);
			}
		} 
	});
});

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
