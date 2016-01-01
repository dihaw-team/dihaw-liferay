<%@ include file="menu.jsp" %>
<portlet:actionURL name="syncRoles" var="syncRolesURL" copyCurrentRenderParameters="false">
	<portlet:param name="tabs1" value="roles" />
</portlet:actionURL>
<liferay-ui:success key="roles-synchronized" message="roles-synchronization-completed" />
<div>
	<input type="button" id="sync-roles" value="<spring:message code="syncronize-roles" />" />
</div>
<script>
	AUI().ready('node', 'event', 'aui-loading-mask', function(A) {
		A.one('#sync-roles').on('click', function(e) {
			var body = A.one('body');
			body.plug(A.LoadingMask, {
				background: '#000',
				strings: {
					loading: '<spring:message code="progress" />'
				}
			});
			body.loadingmask.show();
			document.location.href = '${syncRolesURL}';
		});
	});
</script>