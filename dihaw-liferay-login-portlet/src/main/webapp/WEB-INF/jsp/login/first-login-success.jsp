<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<portlet:defineObjects/>

<portlet:actionURL name="continue" var="continueURL" copyCurrentRenderParameters="false" />
<h3><span><spring:message code="restricted-area-access" /></span></h3>
<div id="main-container">
	<div id="form-container">
		<div id="message">
			<c:choose>
				<c:when test="${portletPreferencesValues['changeUsernameOnFirstLogin'][0] == 'true'}">
					<spring:message code="username-and-password-changed" />
				</c:when>
				<c:otherwise>
					<spring:message code="password-changed" />
				</c:otherwise>
			</c:choose>
		</div>
		<ul class="button-bar">
			<li><a class="button" href="${continueURL}"><spring:message code="continue" /></a></li>
		</ul>
	</div>
</div>