<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${null != model.clothColors}">
<ul class="nav nav-pills nav-stacked">
	<c:forEach items="${model.clothColors}" var="clothColor">
		<c:if test="${clothColor.id == model.clothColorId}">
		<li role="presentation" class="active"><a href="${model.baseUrl}&clothColorId=${clothColor.id}">${clothColor.color}</a></li>
		</c:if>
		<c:if test="${clothColor.id != model.clothColorId}">
		<li role="presentation"><a href="${model.baseUrl}&clothColorId=${clothColor.id}">${clothColor.color}</a></li>
		</c:if>
	</c:forEach>
</ul>
</c:if>
