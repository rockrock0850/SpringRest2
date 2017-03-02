<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#blah')
                .attr('src', e.target.result)
                .width(100)
                .height(100);
        };

        reader.readAsDataURL(input.files[0]);

        $('#blah').removeAttr('style');
    }
};
</script>

<c:choose>
	<c:when test="${errorList != null}">
		<c:set var="breac" value="false" scope="page"/>
		<c:forEach items="${errorList}" var="error">
			<c:if test="${error.field == param.field}">
				<c:set var="breac" value="true" scope="page"/>
				<input class="form-control-file" type="file" id="${param.field}" name="${param.field}" />
				<small class="text-danger">${error.defaultMessage}</small>
			</c:if>
		</c:forEach>
		<c:if test="${!breac}">
			<img style='display: none' id="blah" src="#" alt="your image" />
			<input class="form-control-file" type="file" id="${param.field}" name="${param.field}" onchange="readURL(this)" />
		</c:if>
	</c:when>
	<c:otherwise>
		<img style='display: none' id="blah" src="#" alt="your image" />
		<input class="form-control-file" type="file" id="${param.field}" name="${param.field}" onchange="readURL(this);" />
	</c:otherwise>
</c:choose>
