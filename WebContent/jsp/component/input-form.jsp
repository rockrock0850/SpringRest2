<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test="${errorList != null}">
		<c:set var="breac" value="false" scope="page"/>
		<c:forEach items="${errorList}" var="error">
			<c:if test="${error.field == param.field}">
				<c:set var="breac" value="true" scope="page"/>
				<input class="form-control" type="${param.type}" id="${param.field}" name="${param.field}" />
				<small class="text-danger">${error.defaultMessage}</small>
			</c:if>
		</c:forEach>
		<c:if test="${!breac}">
			<input class="form-control" type="${param.type}" id="${param.field}" name="${param.field}" />
		</c:if>
	</c:when>
	<c:otherwise>
		<input class="form-control" type="${param.type}" id="${param.field}" name="${param.field}" value="${param.val}" />
	</c:otherwise>
</c:choose>
