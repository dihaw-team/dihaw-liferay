<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<portlet:defineObjects/>

<portlet:actionURL name="performLogin" var="performLoginURL" copyCurrentRenderParameters="false"/>

<spring:message code="email-address" var="userEmailAddress"/>
<spring:message code="password" var="userPassword"/>

<c:if test="${status eq 'WRONG_PASSWORD' or status eq 'NOT_FOUND'}">
	<div id="main-message">
		<div id="form-container">
			<div id="error"><spring:message code="bad-email-password" /></div>
		</div>
	</div>
</c:if>
<c:if test="${status eq 'ACCESS_DENIED'}">
	<div id="main-message">
		<div id="form-container">
			<div id="warning"><spring:message code="user-blocked" /></div>
		</div>
	</div>
</c:if>

<div id="main-login">
<div class="login-text"><h1><spring:message code="login" /></h1></div>
	<form:form action="${performLoginURL}" class="form-login" method="POST" name="loginForm" modelAttribute="login"  id="form">
		<p class="field">
			<form:input path="emailAddress" type="text" name="emailAddress" maxlength="50" placeholder="${userEmailAddress}"/>
			<i class="icon-user icon-large"></i>
		</p>
		<p class="field">
			<form:input path="password" type="password" name="password" maxlength="20" placeholder="${userPassword}"/>
			<i class="icon-lock icon-large"></i>
		</p>
		<p class="submit">
			<button type="submit" name="submit"><i class="icon-arrow-right icon-large"></i></button>
		</p>
	</form:form>
</div>

<div id="login-img"/>



