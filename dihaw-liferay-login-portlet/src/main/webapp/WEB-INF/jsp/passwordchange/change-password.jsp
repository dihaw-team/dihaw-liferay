<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<portlet:defineObjects/>

<portlet:renderURL portletMode="HELP" var="helpURL" />
<portlet:actionURL name="doChangePassword" var="doChangePasswordURL" />


<div class="text"><h1><spring:message code="change-password" /></h1></div>
<div id="main-container">
	<div id="form-container">
		<form:form action="${doChangePasswordURL}" method="POST" name="passwordChangeForm" modelAttribute="changePassword" id="form">
			<table class="administration">
				<tr>
					<td><form:label path="oldPassword"><spring:message code="old-password" /><span class="required"><spring:message code="required" /></span></form:label></td>
					<td><form:password path="oldPassword" maxlength="20"  cssErrorClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="newPassword"><spring:message code="new-password" /><span class="required"><spring:message code="required" /></span></form:label></td>
					<td><form:password path="newPassword" maxlength="20"  cssErrorClass="error" /></td>
				</tr>
				<tr>
					<td><form:label path="repeatPassword"><spring:message code="repeat-password" /><span class="required"><spring:message code="required" /></span></form:label></td>
					<td><form:password path="repeatPassword" maxlength="20"  cssErrorClass="error" /></td>
				</tr>
			</table>
			<center><table width="50%">
				<tr>
					<td><a href="javascript:;" onclick="document.getElementById('form').submit();" class="button"><spring:message code="update" /></a></td>
				</tr>
			</table></center>
		</form:form>
	</div>
</div>	




















<!--  
<div class="form-container">
	<h3><span><spring:message code="javax.portlet.title" /></span></h3>
	<form:form action="${doChangePasswordURL}" method="POST" name="passwordChangeForm" modelAttribute="changePassword">
		<fieldset>
			<ol>
				<li class="old-password">
					<spring:message code="old-password-placeholder" var="placeholder" />
					<form:label path="oldPassword"><spring:message code="old-password" /><span class="required"><spring:message code="required" /></span></form:label>
					<form:password path="oldPassword" maxlength="20" placeholder="${placeholder}" cssErrorClass="error" />
					<form:errors path="oldPassword" cssClass="error" />
				</li>
				<li class="new-password">
					<spring:message code="new-password-placeholder" var="placeholder" />
					<form:label path="newPassword"><spring:message code="new-password" /><span class="required"><spring:message code="required" /></span></form:label>
					<form:password path="newPassword" maxlength="20" placeholder="${placeholder}" cssErrorClass="error" />
					<form:errors path="newPassword" cssClass="error" />
				</li>
				<li class="repeat-password">
					<spring:message code="repeat-password-placeholder" var="placeholder" />
					<form:label path="repeatPassword"><spring:message code="repeat-password" /><span class="required"><spring:message code="required" /></span></form:label>
					<form:password path="repeatPassword" maxlength="20" placeholder="${placeholder}" cssErrorClass="error" />
					<form:errors path="repeatPassword" cssClass="error" />
				</li>
			</ol>
		</fieldset>
		<fieldset class="button-bar">
			<button type="submit"><spring:message code="confirm" /></button>
		</fieldset>
	</form:form>
	<div class="required-fields"><spring:message code="required-fields" /></div>
	<div class="message"><spring:message code="inline-help" /></div>
	<a href="${helpURL}" class="help"><spring:message code="help" /></a>
</div>
-->
<script>
	AUI().ready('node', 'aui-tooltip', function(A) {
		var passwordChangeForm = A.one('form[name=passwordChangeForm]');
		
		// Show error tooltip
		passwordChangeForm.all('input.error').each(function(inputNode) {
			// Create the tooltip for the field
			var tooltip = new A.Tooltip({
				trigger: inputNode,
				align: { points: ['tc', 'bc'] },
				bodyContent: inputNode.next().text(),
				hideDelay: 0,
				hideOn: 'blur',
				showOn: 'focus'
			}).render();
			inputNode.setData('error-tooltip', tooltip);
		});
	});
</script>