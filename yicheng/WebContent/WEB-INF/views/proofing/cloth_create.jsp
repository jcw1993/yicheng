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
			<form id="create-form" class="form-horizontal" method="post" action="#" enctype="multipart/form-data">
				<p>基本信息</p>
		         <div class="form-group row">
			        <label for="type" class="col-sm-2 control-label">款号</label>
			        <div class="col-sm-6">
			            <input id="cloth_type_input" type="text" class="form-control" name="type" placeholder="款号(必填)" />
			        </div>
			     </div>

	             <div class="form-group row">
	     	        <label for="name" class="col-sm-2 control-label">款名</label>
	     	        <div class="col-sm-6">
	     	            <input id="cloth_name_input" type="text" class="form-control" name="name" placeholder="款名(必填)" />
	     	        </div>
	     	     </div>

     	         <div class="form-group row">
     		        <label for="type" class="col-sm-2 control-label">买手</label>
     		        <div class="col-sm-6">
     		            <input id="client_input" type="text" class="form-control" name="client" placeholder="买手(选填)" />
     		        </div>
     		     </div>

                  <div class="form-group row">
          	        <label for="name" class="col-sm-2 control-label">供应商</label>
          	        <div class="col-sm-6">
          	            <input id="supplier_input" type="text" class="form-control" name="supplier" placeholder="供应商(选填)" />
          	        </div>
          	     </div>

	             <div class="form-group row">
	     	        <label for="name" class="col-sm-2 control-label">颜色</label>
	     	        <div class="col-sm-6">
	     	            <input id="color_input" type="text" class="form-control" name="color" placeholder="颜色(必填)" />
	     	        </div>
	     	     </div>

                  <div class="form-group row">
          	        <label for="name" class="col-sm-2 control-label">备注</label>
          	        <div class="col-sm-6">
          	            <input id="remark_input" type="text" class="form-control" name="remark" placeholder="备注(选填)" />
          	        </div>
          	     </div>

  	             <div class="form-group row">
  	     	        <label for="name" class="col-sm-2 control-label">交货期</label>
  	     	        <div class="col-sm-6">
  	     	            <input id="delivery_date_input" type="text" class="form-control" name="deliveryDate" placeholder="yyyy-MM-dd(选填)" />
  	     	        </div>
  	     	     </div>

 	              <div class="form-group row">
 	     	        <label for="type" class="col-sm-2 control-label">上传图片</label>
 	     	        <div class="col-sm-6">
 	     	            <input id="image_input" type="file" name="image" />
 	     	            <img id="image_preview" class="image-preview" src="#" style="display: none"/>
 	     	        </div>
 	     	     </div>

	     	     <div class="form-group row">
		     	     <div class="col-sm-2"></div>
		     	     <div class="col-sm-4">
		     	     	<a href="#" id="save_cloth_btn" class="btn btn-sm btn-success">保存</a>
		     	     </div>     	     	
	     	     </div>   
	        </form>
		</div>
	</div>
	</div>
<jsp:include page="../footer.jsp" flush="true" />

<script type="text/javascript">

// /* ui components */
var $saveClothBtn = $("#save_cloth_btn");
var $cloth_name_input = $("#cloth_name_input");
var $cloth_type_input = $("#cloth_type_input");
var $client_input = $("#client_input");
var $supplier_input = $("#supplier_input");
var $color_input = $("#color_input");
var $remark_input = $("#remark_input");
var $delivery_date_input = $("#delivery_date_input");

var $image_input = $("#image_input");
var $image_preview = $("#image_preview");

var $createForm = $("#create-form");


$image_input.change(function(){
		var imageUrl = $image_input.val();

	if(!imageUrl || imageUrl.trim() != "") {
		var pattern = /\.jpg$|\.jpeg$|\.gif$|\.png$/i;
		if(pattern.test(imageUrl)) {
			previewImage(this);
		}else {
			alert("您选择的似乎不是图像文件。");	
		}
		
	}
    
});


$saveClothBtn.click(function(e) {
	var clothType = $cloth_type_input.val();
	var clothName = $cloth_name_input.val();
	var color = $color_input.val();



	if(!clothType || clothType.trim() == "" || !clothName || clothName.trim() == "" || !color || color.trim() == "") {
		alert("请先填写衣服必填参数！");
		return;
	}

	clothType = clothType.trim();
	clothName = clothName.trim();
	color = color.trim();

	$createForm.submit();

	// $.ajax({
	// 	url: "CreateCloth",
	// 	method: "post",
	// 	data: formData,
	// 	success: function(result) {
	// 		if(result.resultCode == 0) {
	// 			console.log("create cloth success");
	// 			clothId = result.data;
	// 			window.location = "ClothMaterialCreate?clothId=" + clothId;
	// 		}else {
	// 			alert("保存失败，请刷新页面重试");
	// 			console.log("create cloth fail, " + result.message);
	// 		}
	// 	}
	// });
});

function previewImage(input) {

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $image_preview.attr("src", e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
        $image_preview.show();
    }
}

</script>	
</body>
</html>
