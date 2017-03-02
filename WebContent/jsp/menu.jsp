<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul class="nav nav-tabs">
  <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/${param.module}">${param.title}</a></li>
  <li role="presentation"><a href="${pageContext.request.contextPath}/${param.module}/${param.function3}">${param.title3}</a></li>
  <li role="presentation"><a href="${pageContext.request.contextPath}/${param.module}/${param.function4}">${param.title4}</a></li>
  <li role="presentation"><a href="${pageContext.request.contextPath}/${param.module}/${param.function2}">${param.title2}</a></li>
  <c:choose>
  	<c:when test="${sessionScope.account != null}">
  		<li role="presentation"><a href="${pageContext.request.contextPath}/${param.module}/${param.function5}">${param.title5}</a></li>
  	</c:when>
  	<c:otherwise>
  		<li role="presentation"><a href="${pageContext.request.contextPath}/${param.module}/${param.function1}">${param.title1}</a></li>
  	</c:otherwise>
  </c:choose>
  <li role="presentation"><h5><span class="label label-default">${sessionScope.account}</span></h5></li>
</ul>