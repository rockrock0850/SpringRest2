<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
<meta http-equiv="Content-Type" content="width=device-width; charset=UTF-8">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/moment.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript">//# sourceURL=image.js
$(function (){
	$('button#chooseFile').click(function () {
		$('input#image').trigger('click');
	});
	
	$('input#image').change(function () {
		var file = this.files[0];
		var fullName = file.name.split('.');
		var name = fullName[0];
		var extension = fullName[1];
		var type = file.type;
		
		$('label#imageName').html(name);
		
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = function (f) {
        	file = f.target.result;
        	var reqData = {};
        	reqData.baseFile = file;
        	reqData.name = name;
        	reqData.extension = extension;
        	reqData.type = type;
        	
        	$.ajax({
       		  method: "post",
       		  url: "${pageContext.request.contextPath}/demo/storeImage",
       		  contentType: "application/json;charset=utf-8", 
       		  data: JSON.stringify(reqData),
       		  success: function (resData) {
       			  console.log(resData);
       		  },
       		  error: function (xhr) {
       			  console.log(xhr);
       		  }
       		});
        	$('input#image').val('');
        };
	});
})
</script>
<title>Image Demo</title>
</head>
<body>
<label>檔案名稱:</label>
<label id='imageName'></label>
<br>
<button id='chooseFile'>選擇檔案</button>
<input id='image' type='file' style='display: none;' />
</body>
</html>