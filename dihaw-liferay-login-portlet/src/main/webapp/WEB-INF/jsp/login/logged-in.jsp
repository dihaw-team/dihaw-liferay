<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<portlet:defineObjects/>

<h3><span><spring:message code="restricted-area-access" /></span></h3>
<div id="main-container">
	<div id="form-container">
		<ul class="logged-in">
		<!--  
			<li class="terminal-code">
				<strong><spring:message code="terminal-code" /></strong>
				<span><c:choose>
					<c:when test="${not empty(terminalCode)}">${terminalCode}</c:when>
					<c:otherwise><spring:message code="not-available" /></c:otherwise>
				</c:choose></span>
			</li>
			-->
			<li class="emailAddress">
				<strong><spring:message code="emailAddress" /></strong>
				<span>${emailAddress}</span>
			</li>
		</ul>
		<ul class="button-bar">
			<c:if test="${not empty(loginURL)}">
				<li class="sign-in"><a href="${loginURL}"><spring:message code="sign-in" /></a></li>
			</c:if>
			<c:if test="${not empty(logoutURL)}">
				<li class="sign-out"><a href="${logoutURL}"><spring:message code="sign-out" /></a></li>
			</c:if>
		</ul>
	</div>
</div>