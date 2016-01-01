<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<portlet:defineObjects/>

<portlet:actionURL name="performLogin" var="performLoginURL" />
<div id="main-container">
	<div id="form-container">

		<form:form action="${performLoginURL}" method="POST" name="loginForm" modelAttribute="login">
			<fieldset>
				<legend><span><spring:message code="restricted-area-access" /></span></legend>
				<ol>
		<!-- 		
					<li style="display:none">
						<spring:message code="terminal-code-placeholder" var="placeholder" />
						<form:label path="terminalCode"><spring:message code="terminal-code" /></form:label>
						<form:input path="terminalCode" maxlength="10" placeholder="${placeholder}" cssErrorClass="error" />
						<form:errors path="terminalCode" cssClass="error" />
					</li>
		-->
					<li>
						<spring:message code="emailAddress" var="placeholder" />
						<form:label path="emailAddress"><spring:message code="emailAddress" /></form:label>
						<form:input path="emailAddress" maxlength="10" placeholder="${placeholder}" cssErrorClass="error" />
		<%-- 				<form:errors path="emailAddress" cssClass="error" /> --%>
					</li>
					<li>
						<spring:message code="password-placeholder" var="placeholder" />
						<form:label path="password"><spring:message code="password" /></form:label>
						<form:password path="password" maxlength="20" placeholder="${placeholder}" cssErrorClass="error" autocomplete="off"/>
						<form:errors path="password" cssClass="error" />
					</li>
				</ol>
			</fieldset>
			<fieldset class="button-bar">
				<button type="submit"><spring:message code="sign-in" /></button>
			</fieldset>
		</form:form>
	</div>
</div>
<script>
	AUI().ready('node', 'event', 'cookie', 'aui-tooltip', function(A) {
		var cookieName = Liferay.ThemeDisplay.getScopeGroupId() + '-TC';
		var loginForm = A.one('form[name=loginForm]');
		var terminalCodeNode = loginForm.one('input[name=terminalCode]');
		
		// Bind loginForm submit in order to store the cookie holding the terminal code
		loginForm.on('submit', function(e) {
			var terminalCode = terminalCodeNode.get('value');
			var options = { secure: (document.location.protocol == 'https:') };
			
			A.Cookie.set(cookieName, terminalCode, options);
			
			return true;
		});
		
		if (A.Cookie.exists(cookieName)) {
			// Cookie named '<scopeGroupId>-domain' exists, populate the form terminal code with his value
			
			var terminalCode = A.Cookie.get(cookieName);
			if (terminalCode) {
				// Cookie value is set
				terminalCodeNode.set('value', terminalCode);
			}
		}

		// Show error tooltip.
		loginForm.all('input.error').each(function(inputNode) {
			// Create the tooltip for the field
			var tooltip = new A.Tooltip({
				trigger: inputNode,
				align: { points: [ 'bc', 'tc' ] },
				bodyContent: inputNode.next().text(),
				hideDelay: 0,
				hideOn: 'blur',
				showOn: 'focus'
			}).render();
			inputNode.setData('error-tooltip', tooltip);
		});
	});
</script>