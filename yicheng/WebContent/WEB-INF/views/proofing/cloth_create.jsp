<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="../header.jsp" flush="true" />

<body>
	<jsp:include page="proofing_navi.jsp" flush="true" />

	<div class="container-body">
		<h3>创建皮衣</h3>
		<hr />
		<div>
			<form id="create-form" class="form-horizontal" method="post" action="#">
				<p>基本信息</p>
		         <div class="form-group row">
			        <label for="type" class="col-sm-2 control-label">款号</label>
			        <div class="col-sm-6">
			            <input id="cloth_type_input" type="text" class="form-control" name="type" placeholder="款号" />
			        </div>
			     </div>

	             <div class="form-group row">
	     	        <label for="name" class="col-sm-2 control-label">款名</label>
	     	        <div class="col-sm-6">
	     	            <input id="cloth_name_input" type="text" class="form-control" name="name" placeholder="款名" />
	     	        </div>
	     	     </div>

				<div class="row title-area">
					<p class="col-sm-7">皮料信息</p>
					<div class="col-sm-2">
						<a href="#" id="add_leather_btn" class="btn btn-success btn-sm" >添加</a>	
					</div>
				</div>		    
				<div class="cloth_material_area row">
					<p class="col-sm-2">项目</p>
					<p class="col-sm-2">部位</p>
					<p class="col-sm-2">单位</p>
					<p class="col-sm-2">用料</p>
					<p class="col-sm-2">备注</p>
				</div>

				<div class="row title-area">
					<p class="col-sm-7">面料信息</p>
					<div class="col-sm-2">
						<a href="#" id="add_fabric_btn" class="btn btn-success btn-sm" >添加</a>	
					</div>
				</div>			

				<div class="row title-area">
					<p class="col-sm-7">辅料信息</p>
					<div class="col-sm-2">
						<a href="#" id="add_support_btn" class="btn btn-success btn-sm" >添加</a>	
					</div>
				</div>
	        </form>
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
					        <label for="part" class="col-sm-2 control-label">部位</label>
					        <div class="col-sm-6">
					            <input type="text" class="form-control" name="part" placeholder="部位" />
					        </div>
					    </div>
				        <div class="form-group row">
					        <label class="col-sm-2 control-label">单位</label>
					        <div class="col-sm-6">
					            <input type="text" class="form-control" name="unitName" placeholder="单位" />
					        </div>
					    </div>
				        <div class="form-group row">
					        <label for="consumption" class="col-sm-2 control-label">用料</label>
					        <div class="col-sm-6">
					            <input type="text" class="form-control" name="consumption" placeholder="用料" />
					        </div>
					    </div>
				        <div class="form-group row">
					        <label for="remark" class="col-sm-2 control-label">备注</label>
					        <div class="col-sm-6">
					            <input type="text" class="form-control" name="remark" placeholder="备注" />
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

<script type="text/javascript">
/* ui components */
var $add_leather_btn = $("#add_leather_btn");
var $add_fabric_btn = $("#add_fabric_btn");
var $add_support_btn = $("#add_support_btn");

var $material_create_modal = $("#material_create_modal");
var $material_create_submit = $("#material_create_submit");
var $material_create_form = $("#material_create_form");

var $material_select = $("#material_select");

var $cloth_name_input = $("#cloth_name_input");
var $cloth_type_input = $("#cloth_type_input");

/*variables*/
var materials;
var clothId;


$add_leather_btn.click(function(e) {
	console.log("add leather");
	if(clothId) {
		$material_create_modal.modal();
		updateMaterialSelect(0);
	}else {
		checkClothCreate();
	}

});

$add_fabric_btn.click(function(e) {
	console.log("add fabric");
	if(clothId) {
		$material_create_modal.modal();
		updateMaterialSelect(1);
	}else {
		checkClothCreate();
	}

});

$add_support_btn.click(function(e) {
	console.log("add support");
	if(clothId) {
		$material_create_modal.modal();
		updateMaterialSelect(2);
	}else {
		checkClothCreate();
	}

});


$material_create_submit.click(function(e) {
	console.log("create material submit");
	saveClothMaterial(SAVE_TYPE_CREATE);
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
				}else {
					console.log("create clothMaterial fail");
				}
			}
		});
	}else {
		// TODO
	}
}

function checkClothCreate() {
	if(!clothId) {
		var clothType = $cloth_type_input.val();
		var clothName = $cloth_name_input.val();

		if(!clothType || clothType.trim() == "" || !clothName || clothName.trim() == "") {
			alert("请先填写衣服参数！");
			return;
		}

		clothType = clothType.trim();
		clothName = clothName.trim();

		console.log("clothType:" + clothType);
		console.log("clothName:" + clothName);
		
		$.ajax({
			url: "CreateCloth",
			data: {
				name: clothName,
				type: clothType
			},
			method: "post",
			success: function(result) {
				if(result.resultCode == 0) {
					console.log("create cloth success");
					clothId = result.data;
				}else {
					console.log("create cloth fail, " + result.message);
				}
			}
		});

	}
}

function appendClothMaterialItem($clothMaterialArea) {

}
</script>	
</body>
</html>
