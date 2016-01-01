<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<portlet:defineObjects/>

<portlet:actionURL name="goToChangePassword" var="doChangePasswordURL" copyCurrentRenderParameters="false" >
	<portlet:param name="state" value="password-expired" />
</portlet:actionURL>

<spring:message code="new-password" var="newPassword"/>
<spring:message code="new-password-repeat" var="newPasswordRepeat"/>

<c:if test="${status eq 'CHANGE_WRONG_PASSWORD'}">
	<div id="main-message">
		<div id="form-container">
			<div id="warning"><spring:message code="chnage-wrong-password" /></div>
		</div>
	</div>
</c:if>
<c:if test="${status eq 'CHANGE_WRONG_NEW_PASSWORD'}">
	<div id="main-message">
		<div id="form-container">
			<div id="warning"><spring:message code="chnage-wrong-new-password" /></div>
		</div>
	</div>
</c:if>
<c:if test="${status eq 'CHANGE_WRONG_REPEAT_PASSWORD'}">
	<div id="main-message">
		<div id="form-container">
			<div id="warning"><spring:message code="chnage-wrong-repeat-password" /></div>
		</div>
	</div>
</c:if>

<div id="main-container">
	<form:form action="${doChangePasswordURL}" class="form-container" method="POST" name="passwordChangeForm" modelAttribute="passwordChange" id="form">
		<p class="field">
			<form:password path="newPassword" type="password" name="login" maxlength="20" placeholder="${newPassword}"/>
			
		</p>
		<p class="field">
			<form:password path="repeatPassword" type="password" name="password" maxlength="20" placeholder="${newPasswordRepeat}"/>
			
		</p>
		<p class="submit">
			<button type="submit" name="submit"><i class="icon-arrow-right icon-large"></i></button>
		</p>
	</form:form>
</div>
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