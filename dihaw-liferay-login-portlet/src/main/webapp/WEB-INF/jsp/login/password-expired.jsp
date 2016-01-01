<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<portlet:defineObjects/>

<portlet:renderURL var="goToChangePasswordURL" copyCurrentRenderParameters="false">
	<portlet:param name="state" value="password-expired" />
</portlet:renderURL>
<h3><span><spring:message code="restricted-area-access" /></span></h3>

<div id="main-container">
	<div id="form-container">
		<div id="message"><spring:message code="your-password-is-expired" /></div>
		<ul class="button-bar">
			<li><a  class="button" href="${goToChangePasswordURL}"><spring:message code="go-to-change-password" /></a></li>
		</ul>
	</div>
</div>