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
<title>清單</title>
<script type="text/javascript">
</script>
<jsp:include page="../constant.jsp"/>
</head>
<body>
<jsp:include page="${requestScope.menu}">
	<jsp:param value="user" name="module"/>
	<jsp:param value="清單" name="title"/>
	<jsp:param value="login" name="function1"/>
	<jsp:param value="登入" name="title1"/>
	<jsp:param value="create" name="function2"/>
	<jsp:param value="註冊" name="title2"/>
	<jsp:param value="update" name="function3"/>
	<jsp:param value="更新" name="title3"/>
	<jsp:param value="delete" name="function4"/>
	<jsp:param value="刪除" name="title4"/>
	<jsp:param value="logout" name="function5"/>
	<jsp:param value="登出" name="title5"/>
</jsp:include>
<table class="table">
	<thead>
		<tr>
			<th>#</th>
			<th>帳號</th>
			<th>密碼</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${userList}" var="user">
			<tr>
				<td>${user.id}</td>
				<td>${user.account}</td>
				<td>${user.password}</td>
				<td>
					<c:if test="${user.img != ''}">
						<img src="${user.img}" alt="Mountain View" style="width:50px;height:50px;">
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot></tfoot>
</table>
<!-- editModal -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" 
     aria-labelledby="editModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Modal Body -->
            <div class="modal-body"></div>
            <!-- Modal Footer -->
            <div class="modal-footer"></div>
        </div>
    </div>
</div>
</body>
</html>