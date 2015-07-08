<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="../header.jsp" flush="true" />

<body>
	<jsp:include page="proofing_navi.jsp" flush="true" />

	<c:set value="${model.cloth}" var="cloth" />
	<c:set value="${model.leatherDetails}" var="leatherDetails" />
	<c:set value="${model.fabricDetails}" var="fabricDetails" />
	<c:set value="${model.supportDetails}" var="supportDetails" />

	<div class="container-body">
		<h3>皮衣详情
		<a href="ClothMaterialDetail?clothId=${cloth.id}" class="btn btn-primary create-button">查看详情</a>
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
      	        <label for="name" class="col-sm-2 control-label">备注</label>
      	        <div class="col-sm-6">
 	     	        <label class="remark">${cloth.remark}</label>
      	        </div>
      	     </div>     	     
			
			<div class="margin-top-little">
				<div class="row title-area">
					<p class="col-sm-7">皮料信息</p>
					<div class="col-sm-2">
						<a href="#" id="add_leather_btn" class="btn btn-success btn-sm" >添加</a>	
					</div>
				</div>	

				<c:if test="${null != leatherDetails}">	
				<table id="leather_table" class="table table-striped table-bordered table-hover table-responsive">
					<tr>
						<th>项目</th>
						<th>颜色</th>	
						<th>部位</th>
						
						<th>单位</th>
						<th>供应商</th>
						<th>用料</th>
						<th>估价</th>
						<th>备注</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${leatherDetails}" var="leatherDetail">
						<tr clothMaterialId="${leatherDetail.id}">
							<td>${leatherDetail.materialName}</td>
							<td>${leatherDetail.materialColor}</td>
							<td>${leatherDetail.part}</td>
							<td>${leatherDetail.unitName}</td>
							<td>${supportDetail.supplier}</td>
							<td>${leatherDetail.consumption}</td>
							<td><input type="text" name="estimatedPrice" value="${leatherDetail.estimatedPrice}" placeholder="整数或小数"></td>
							<td>${leatherDetail.remark}</td>
							<td>
								<a href="#" class="delete_leather_btn" >删除</a>
								<a href="#" class="save_leather_btn" >保存</a>
							</td>
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
			     	<div class="col-sm-2">
			     		<a href="#" id="add_fabric_btn" class="btn btn-success btn-sm" >添加</a>	
			     	</div>
			     </div>		
			     <c:if test="${null != fabricDetails}">
			     <table id="fabric_table" class="table table-striped table-bordered table-hover table-responsive">
			     	<tr>
			     		<th>项目</th>
			     		<th>颜色</th>
			     		<th>部位</th>
			     		<th>单位</th>
			     		<th>供应商</th>
			     		<th>用料</th>
			     		<th>估价</th>
			     		<th>备注</th>
			     		<th>操作</th>
			     	</tr>
			     	<c:forEach items="${fabricDetails}" var="fabricDetail" >
						<tr clothMaterialId="${fabricDetail.id}">
							<td>${fabricDetail.materialName}</td>
							<td>${leatherDetail.materialColor}</td>
							<td>${fabricDetail.part}</td>
							<td>${fabricDetail.unitName}</td>
							<td>${supportDetail.supplier}</td>
							<td>${fabricDetail.consumption}</td>
							<td><input type="text" value="${fabricDetail.estimatedPrice}" placeholder="整数或小数"></td>
							<td>${fabricDetail.remark}</td>
							<td>
								<a href="#" class="delete_fabric_btn" >删除</a>
								<a href="#" class="save_fabric_btn" >保存</a>
							</td>
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
					<div class="col-sm-2">
						<a href="#" id="add_support_btn" class="btn btn-success btn-sm" >添加</a>	
					</div>

	     	     </div>		
	     	     <c:if test="${null != supportDetails}">
	     	     <table id="support_table" class="table table-striped table-bordered table-hover table-responsive">
	     	     	<tr>
	     	     		<th>项目</th>
	     	     		<th>颜色</th>
	     	     		<th>部位</th>
	     	     		<th>单位</th>
	     	     		<th>供应商</th>
	     	     		<th>用料</th>
	     	     		<th>估价</th>
	     	     		<th>备注</th>
	     	     		<th>操作</th>
	     	     	</tr>
	     	     	<c:forEach items="${supportDetails}" var="supportDetail" >
						<tr clothMaterialId="${supportDetail.id}">
							<td>${supportDetail.materialName}</td>
							<td>${leatherDetail.materialColor}</td>
							<td>${supportDetail.part}</td>
							<td>${supportDetail.unitName}</td>
							<td>${supportDetail.supplier}</td>
							<td>${supportDetail.consumption}</td>
							<td><input type="text" value="${supportDetail.estimatedPrice}" placeholder="整数或小数"></td>
							<td>${supportDetail.remark}</td>
							<td>
								<a href="#" class="delete_support_btn" >删除</a>
								<a href="#" class="save_support_btn" >保存</a>
							</td>
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
					<div class="col-sm-4"><a href="ClothMaterialManage" class="btn btn-info btn-lg" >提交</a></div>
				</div>
					
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
					        <label class="col-sm-2 control-label">项目</label>
					        <div class="col-sm-6">
					            <!-- <input type="text" class="form-control" name="material_name" placeholder="项目" /> -->
					            <select id="material_select" name="materialId">
					            	
					            </select>
					        </div>
					    </div>		
				        <div class="form-group row">
					        <label for="color" class="col-sm-2 control-label">颜色</label>
					        <div class="col-sm-6">
					            <input type="text" id="color" class="form-control" name="color" placeholder="颜色" />
					        </div>
					    </div>					    		
				        <div class="form-group row">
					        <label for="part" class="col-sm-2 control-label">部位</label>
					        <div class="col-sm-6">
					            <input type="text" id="part" class="form-control" name="part" placeholder="部位" />
					        </div>
					    </div>
				        <div class="form-group row">
					        <label class="col-sm-2 control-label">单位</label>
					        <div class="col-sm-6">
					            <input type="text" id="unitName" class="form-control" name="unitName" placeholder="单位" />
					        </div>
					    </div>
			            <div class="form-group row">
			    	        <label class="col-sm-2 control-label">供应商</label>
			    	        <div class="col-sm-6">
			    	            <input type="text" id="supplier" class="form-control" name="supplier" placeholder="供应商" />
			    	        </div>
			    	    </div>
				        <div class="form-group row">
					        <label for="consumption" class="col-sm-2 control-label">用料</label>
					        <div class="col-sm-6">
					            <input type="text" id="consumption" class="form-control" name="consumption" placeholder="用料（整数或小数）" />
					        </div>
					    </div>
			            <div class="form-group row">
			    	        <label for="consumption" class="col-sm-2 control-label">估价</label>
			    	        <div class="col-sm-6">
			    	            <input type="text" id="estimatedPrice" class="form-control" name="estimatedPrice" placeholder="估价（整数或小数）" />
			    	        </div>
			    	    </div>
				        <div class="form-group row">
					        <label for="remark" class="col-sm-2 control-label">备注</label>
					        <div class="col-sm-6">
					            <input type="text" id="remark" class="form-control" name="remark" placeholder="备注" />
					        </div>
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
/*variables*/
var clothId;
var materials;
var currentMaterialType;

var $deleteLeatherBtn = $(".delete_leather_btn");
var $deleteFabricBtn = $(".delete_fabric_btn");
var $deleteSupportBtn = $(".delete_support_btn");

var $saveLeatherBtn = $(".save_leather_btn");
var $saveFabricBtn = $(".save_fabric_btn");
var $saveSupportBtn = $(".save_support_btn");

var $leatherTable = $("#leather_table");
var $fabricTable = $("#fabric_table");
var $supportTable  =$("#support_table");

var $addLeatherBtn = $("#add_leather_btn");
var $addFabricBtn = $("#add_fabric_btn");
var $addSupportBtn = $("#add_support_btn");

var $material_create_modal = $("#material_create_modal");
var $material_create_submit = $("#material_create_submit");
var $material_create_form = $("#material_create_form");

var $material_select = $("#material_select");
var $part = $("#part");
var $unitName = $("#unitName");
var $consumption = $("#consumption");
var $estimatedPrice = $("#estimatedPrice");
var $remark = $("#remark");

$(function() {
	clothId = "${model.cloth.id}";

	$deleteLeatherBtn.click(function(e) {
		var clothMaterialId = $(this).parent().parent().attr("clothMaterialId");
		deleteClothMaterial(clothMaterialId);
	});

	$deleteFabricBtn.click(function(e) {
		var clothMaterialId = $(this).parent().parent().attr("clothMaterialId");
		deleteClothMaterial(clothMaterialId);
	});

	$deleteSupportBtn.click(function(e) {
		var clothMaterialId = $(this).parent().parent().attr("clothMaterialId");
		deleteClothMaterial(clothMaterialId);
	});

	$saveLeatherBtn.click(function(e) {
		var clothMaterialId = $(this).parent().parent().attr("clothMaterialId");
		var estimatedPrice = $(this).parent().parent().find("input[name='estimatedPrice']").val();
		saveClothMaterialEstimatedPrice(clothMaterialId, estimatedPrice);
	});

	$saveFabricBtn.click(function(e) {
		var clothMaterialId = $(this).parent().parent().attr("clothMaterialId");
		var estimatedPrice = $(this).parent().parent().find("input[name='estimatedPrice']").val();
		saveClothMaterialEstimatedPrice(clothMaterialId, estimatedPrice);
	});

	$saveSupportBtn.click(function(e) {
		var clothMaterialId = $(this).parent().parent().attr("clothMaterialId");
		var estimatedPrice = $(this).parent().parent().find("input[name='estimatedPrice']").val();
		saveClothMaterialEstimatedPrice(clothMaterialId, estimatedPrice);
	});

	$addLeatherBtn.click(function(e) {
		console.log("add leather");
		currentMaterialType = MATERIAL_TYPE_LEATHER;
		$material_create_modal.modal();
		updateMaterialSelect(0);

	});

	$addFabricBtn.click(function(e) {
		console.log("add fabric");
		currentMaterialType = MATERIAL_TYPE_FABRIC;
		$material_create_modal.modal();
		updateMaterialSelect(1);
	});

	$addSupportBtn.click(function(e) {
		console.log("add support");
		currentMaterialType = MATERIAL_TYPE_SUPPORT;
		$material_create_modal.modal();
		updateMaterialSelect(2);

	});

	$material_create_submit.click(function(e) {
		console.log("create material submit");
		saveClothMaterial(SAVE_TYPE_CREATE);
	});
});

/*functions*/
function updateMaterialSelect(type) {
	materials = null;
	$.ajax({
		url: "GetAllMaterialByType?type=" + type,
		success: function(result) {
			console.log(result.data);
			if(result.resultCode == 0) {
				materials = result.data;
				fillMaterialSelect();
			}else {
				console.log("getAllMaterial exception: " + result.message);
			}
		}
	});
}

function fillMaterialSelect() {
	if(materials) {
		$material_select.empty();
		materials.forEach(function(material){
			$material_select.append($("<option value='" + material.id+ "'>" + material.name + "</option>"));
		}); 
	}
}

function saveClothMaterial(saveType) {
	if(saveType == SAVE_TYPE_CREATE) {
		$.ajax({
			url: "CreateClothMaterial?clothId=" + clothId,
			data: $material_create_form.serialize(),
			method: "post",
			success: function(result) {
				if(result.resultCode == 0) {
					console.log("create clothMaterial succcss");
					var clothMaterialId = result.data;
					console.log("clothMaterialId: " + clothMaterialId);
					// appendClothMaterialItem(currentMaterialType, clothMaterialId, $("#material_select option:selected").text(),
					// 	$part.val(), $unitName.val(), $consumption.val(), $estimatedPrice.val(), $remark.val());
					// resetInput();
					location.reload();
					
				}else {
					console.log("create clothMaterial fail");
				}
			}
		});
	}else {
		// TODO
	}
}

function appendClothMaterialItem(materialType, clothMaterialId, materialName, part, unitName, consumption, estimatedPrice, remark) {
	var $clothMaterialTable;
	switch(materialType) {
		case MATERIAL_TYPE_LEATHER:
			$clothMaterialTable = $leatherTable;
			break;
		case MATERIAL_TYPE_FABRIC:
			$clothMaterialTable = $fabricTable;
			break;
		case MATERIAL_TYPE_SUPPORT:
			$clothMaterialTable = $supportTable;
			break;
		default:
			return;		
	}

	var $row = $("<tr clothMaterialId='" + clothMaterialId + "'>");

	var $clothMaterialTd1 = $("<td>" + materialName + "</td>");
	var $clothMaterialTd2 = $("<td>" + part + "</td>");
	var $clothMaterialTd3 = $("<td>" + unitName + "</td>");
	var $clothMaterialTd4 = $("<td>" + consumption + "</td>");
	var $clothMaterialTd5 = $("<td><input type='text' value='" + estimatedPrice + "'' placeholder='整数或小数'></td>");
	var $clothMaterialTd6 = $("<td>" + remark + "</td>");
	var $clothMaterialTd7 = $("<td><a href='#'' class='delete_support_btn' >删除</a></td>");

	$row.append($clothMaterialTd1);
	$row.append($clothMaterialTd2);
	$row.append($clothMaterialTd3);
	$row.append($clothMaterialTd4);
	$row.append($clothMaterialTd5);
	$row.append($clothMaterialTd6);
	$row.append($clothMaterialTd7);

	$clothMaterialTable.append($row);
}


function deleteClothMaterial(clothMaterialId) {
	$.ajax({
		url: "DeleteClothMaterial?clothMaterialId=" + clothMaterialId + "&clothId=" + clothId,
		success: function(result) {
			if(result.resultCode == 0) {
				location.reload();
			}else {
				console.log("delete clothMaterial error");
			}
		}
	});
}

function saveClothMaterialEstimatedPrice(clothMaterialId, estimatedPrice) {
	$.ajax({
		url: "SaveClothMaterialEstimatedPrice",
		method: "post",
		data: {
			clothId: clothId,
			clothMaterialId: clothMaterialId,
			estimatedPrice: estimatedPrice
		},
		success: function(result) {
			if(result.resultCode == 0) {
				location.reload();
			}else {
				console.log("error: " + result.message);
			}
		} 
	}); 
}

/*reset input*/
function resetInput() {
	$part.val("");
	$unitName.val("");
	$consumption.val("");
	$estimatedPrice.val("");
	$remark.val("");
}
</script>	
</body>
</html>
