<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<portlet:defineObjects/>

<portlet:renderURL portletMode="HELP" var="helpURL" />
<portlet:renderURL var="continueURL" copyCurrentRenderParameters="false" />
<div class="form-container">
	<h3><span><spring:message code="javax.portlet.title" /></span></h3>
	<div class="dialog">
		<spring:message code="your-password-has-been-changed" />
		<div class="button-bar">
			<a href="${continueURL}"><spring:message code="continue" /></a>
		</div>
	</div>
	<a href="${helpURL}" class="help"><spring:message code="help" /></a>
</div>