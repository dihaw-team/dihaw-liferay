<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<portlet:defineObjects/>

<portlet:actionURL name="continue" var="continueURL" copyCurrentRenderParameters="false" />

<div id="main-message">
	<div id="form-container">
		<div id="success"><spring:message code="password-changed" /></div>
	</div>
</div>

<center><table width="50%">
	<tr>
		<td><a class="button" href="${continueURL}"><spring:message code="continue" /></a></td>
	</tr>
</table></center>