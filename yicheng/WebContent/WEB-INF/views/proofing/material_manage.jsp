<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="../header.jsp" flush="true" />

<body>
	<jsp:include page="proofing_navi.jsp" flush="true" />

	<c:set value="${model.leathers}" var="leathers" />
	<c:set value="${model.fabrics}" var="fabrics" />
	<c:set value="${model.supports}" var="supports" />

	<div class="container-body">
		<h3>材料管理</h3>
		<hr />
		<div>
			
			<div class="margin-top-little">
				<div class="row title-area">
					<p class="col-sm-7">皮料</p>
					<div class="col-sm-2">
						<a href="#" id="add_leather_btn" class="btn btn-success btn-sm" >添加</a>	
					</div>
				</div>	

				<c:if test="${null != leathers}">	
				<table id="leather_table" class="table table-striped table-bordered table-hover table-responsive">
					<tr>
						<th>名称</th>
						<th>操作</th>		
					</tr>
					<c:forEach items="${leathers}" var="leather">
						<tr material="${leather.id}">
							<td>${leather.name}</td>
							<td><a href="#" class="delete_leather_btn" >删除</a></td>
						</tr>
					</c:forEach>
				</table>
				</c:if>
				<c:if test="${null == leathers}">
					<p>暂无数据</p>
				</c:if>
			</div>

			<div class="margin-top-little">
		 	     <div class="row title-area">
			     	<p class="col-sm-7">面料</p>
			     	<div class="col-sm-2">
			     		<a href="#" id="add_fabric_btn" class="btn btn-success btn-sm" >添加</a>	
			     	</div>
			     </div>		
			     <c:if test="${null != fabrics}">
			     <table id="fabric_table" class="table table-striped table-bordered table-hover table-responsive">
			     	<tr>
			     		<th>名称</th>
			     		<th>操作</th>
			     	</tr>
			     	<c:forEach items="${fabrics}" var="fabric" >
						<tr materialId="${fabric.id}">
							<td>${fabric.name}</td>
							<td><a href="#" class="delete_fabric_btn" >删除</a></td>
						</tr>
			     	</c:forEach>
			     </table>
			     </c:if>
			     <c:if test="${null == fabrics}">
			     	<p>暂无数据</p>
			     </c:if>
			</div>


			<div class="margin-top-little">
				 <div class="row title-area">
			     	<p class="col-sm-7">辅料</p>
					<div class="col-sm-2">
						<a href="#" id="add_support_btn" class="btn btn-success btn-sm" >添加</a>	
					</div>

	     	     </div>		
	     	     <c:if test="${null != supports}">
	     	     <table id="support_table" class="table table-striped table-bordered table-hover table-responsive">
	     	     	<tr>
	     	     		<th>名称</th>
	     	     		<th>操作</th>
	     	     	</tr>
	     	     	<c:forEach items="${supports}" var="support" >
						<tr materialId="${support.id}">
							<td>${support.name}</td>
							<td><a href="#" class="delete_support_btn" >删除</a></td>
						</tr>
					</c:forEach>
	     	     </table>
	     	     </c:if>
	     	     <c:if test="${null == supports}">
	     	     	<p>暂无数据</p>
	     	     </c:if>
			</div>
      	   
		</div>
	</div>

	<div id="material_create_modal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">材料信息</h4>
				</div>
				<form id="material_create_form">
					<div id="material_create_content" class="modal-body">
						<input type="hidden" name="clothId" value="" />
				        <div class="form-group row">
					        <label class="col-sm-2 control-label">名称</label>
					        <div class="col-sm-6">
					            <input type="text" class="form-control" name="material_name" placeholder="项目" />
					        </div>
					    </div>				
					<div class="modal-footer">
						<button id="material_create_submit" type="button"
							class="btn btn-primary" data-dismiss="modal">保存</button>
					</div>
				</form>
			
			</div>
		</div>
	</div>

<jsp:include page="../footer.jsp" flush="true" />

<script type="text/javascript">

</script>	
</body>
</html>
