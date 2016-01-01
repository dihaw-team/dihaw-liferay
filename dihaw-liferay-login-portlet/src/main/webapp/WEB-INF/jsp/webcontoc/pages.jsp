<%@ include file="menu.jsp" %>

<portlet:actionURL name="updatePages" var="updatePagesURL" copyCurrentRenderParameters="true" />
<liferay-ui:success key="pages-update-completed" message="pages-update-completed" />
<c:set var="locale" value="${requestScope['THEME_DISPLAY'].locale}" />
<form:form id="pagesForm" modelAttribute="model" action="${updatePagesURL}" method="post">
	<div class="lfr-search-container">
		<div class="results-grid">
			<table class="taglib-search-iterator">
				<thead>
					<tr class="portlet-section-header results-header">
						<th id="suds_col-1" class="col-1"><spring:message code="page-name" /></th>
						<th id="suds_col-2" class="col-2"><spring:message code="access-role" /></th>
					</tr>
				</thead>
				<tfoot>
				</tfoot>
				<tbody>
					<c:forEach items="${model.rows}" var="page" varStatus="status">
						<tr class="${status.index mod 2 eq 0 ? 'portlet-section-body' : 'portlet-section-alternate'} results-row ${status.index mod 2 ne 0 ? 'alt' : ''}">
							<td class="align-left col-1 first valign-middle" headers="suds_col-1">
								<span class="level-${page.level}">${page.title}</span>
							</td>
							<td class="align-left col-2 last valign-middle" headers="suds_col-2">
								<c:if test="${page.leaf and page.title ne '--'}">
									<form:hidden path="rows[${status.index}].pageId" />
									<form:select path="rows[${status.index}].roleName">
										<form:option value=""><spring:message code="please-select" /></form:option>
										<c:forEach items="${roles}" var="role">
											<form:option value="${role.name}">${role.titleMap[locale]}</form:option>
										</c:forEach>
									</form:select>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="button-bar">
		<input type="submit" id="update-pages" value="<spring:message code="update-pages" />" />
	</div>
</form:form>
<script>
	AUI().ready('node', 'event', 'aui-loading-mask', function(A) {
		A.one('#pagesForm').on('submit', function(e) {
			var body = A.one('body');
			body.plug(A.LoadingMask, {
				background: '#000',
				strings: {
					loading: '<spring:message code="progress" />'
				}
			});
			body.loadingmask.show();
			
			return true;
		});
	});
</script>