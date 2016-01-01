<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet"%>

<portlet:actionURL name="doRegisterUser" var="doRegisterUserURL"/>

<div class="text"><h1><spring:message code="user.register" /></h1></div>

<c:choose>
	<c:when test="${status eq 'SUCCESS'}">
		<div id="main-message">
			<div id="form-container">
				<div id="success">
					<spring:message code="success" arguments="${registerUserRequest.firstName};${registerUserRequest.lastName}" htmlEscape="false" argumentSeparator=";"/>
				</div>
			</div>
		</div>
	</c:when>
	<c:when test="${status eq 'WRONG_DATA'}">
		<div id="main-message">
			<div id="form-container">
				<div id="error"><spring:message code="wrong-data" /></div>
			</div>
		</div>
	</c:when>
	<c:when test="${status eq 'USER_EXIST'}">
		<div id="main-message">
			<div id="form-container">
				<div id="warning"><spring:message code="wrong-user-exist" /></div>
			</div>
		</div>
	</c:when>
</c:choose>

<div id="main-container">
	<div class="section">
		<form:form class="form-container" modelAttribute="registerUser" action="${doRegisterUserURL}" autocomplete="off">
			<ol>
				<li class="huge">
					<form:label path="firstName"><strong><spring:message code="firstName" /></strong></form:label>
					<form:input path="firstName" type="text" name="firstName" maxlength="20" />
				</li>
				<li class="huge">
					<form:label path="lastName"><strong><spring:message code="lastName" /></strong></form:label>
					<form:input path="lastName" type="text" name="lastName" maxlength="20"/>
				</li>
				<p class="submit">
					<button type="submit" name="submit"><spring:message code="register-user-btn" /></button>
				</p>
			</ol>
		</form:form>
	</div>
</div>