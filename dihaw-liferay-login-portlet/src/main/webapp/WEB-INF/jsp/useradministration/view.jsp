<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>

<spring:message code="date.format" var="dateFormat" />
<jsp:useBean id="lastConnectionDateValue" class="java.util.Date"/>
<jsp:useBean id="creationDateValue" class="java.util.Date"/>

<portlet:renderURL var="registerUserURL">
	<portlet:param name="action" value="registerUser" />
</portlet:renderURL>
    
<div class="text"><h1><spring:message code="user.administration" /></h1></div>

<div id="main-container">
	<div id="form-container">
			<c:choose>
				<c:when test="${fn:length(users) > 0 }">
					<portlet:renderURL var="maxSize10URL"><portlet:param name="size" value="10" /></portlet:renderURL>
					<portlet:renderURL var="maxSize20URL"><portlet:param name="size" value="20" /></portlet:renderURL>
					<portlet:renderURL var="maxSize50URL"><portlet:param name="size" value="50" /></portlet:renderURL>
					<portlet:renderURL var="maxSize100URL"><portlet:param name="size" value="100" /></portlet:renderURL>
			
					<div class="page-size-links ">
						<span class="prefix"><spring:message code="ligne-page" /></span>
						<ul class="links">
						
							<c:if test="${size eq 10}">
								<li><a href="${maxSize10URL}" ><strong>10</strong></a></li>
								<li><a href="${maxSize20URL}" >20</a></li>
								<li><a href="${maxSize50URL}" >50</a></li>
								<li><a href="${maxSize100URL}" >100</a></li>
							</c:if>
							<c:if test="${size eq 20}">
								<li><a href="${maxSize10URL}" >10</a></li>
								<li><a href="${maxSize20URL}" ><strong>20</strong></a></li>
								<li><a href="${maxSize50URL}" >50</a></li>
								<li><a href="${maxSize100URL}" >100</a></li>
							</c:if>
							<c:if test="${size eq 50}">
								<li><a href="${maxSize10URL}" >10</a></li>
								<li><a href="${maxSize20URL}" >20</a></li>
								<li><a href="${maxSize50URL}" ><strong>50</strong></a></li>
								<li><a href="${maxSize100URL}" >100</a></li>
							</c:if>
							<c:if test="${size eq 100}">
								<li><a href="${maxSize10URL}" >10</a></li>
								<li><a href="${maxSize20URL}" >20</a></li>
								<li><a href="${maxSize50URL}" >50</a></li>
								<li><a href="${maxSize100URL}" ><strong>100</strong></a></li>
							</c:if>
							
						</ul>
					</div>
				
					<portlet:renderURL var="userOrderByDescURL"><portlet:param name="order" value="desc" /></portlet:renderURL>
					<portlet:renderURL var="userOrderByAscURL"><portlet:param name="order" value="asc" /></portlet:renderURL>

					<div class="section">
						<c:choose>
							<c:when test="${order eq 'desc'}">
								<a class="sort-desc" href="${userOrderByAscURL}">
									<li class="section-head-40-left"><spring:message code="users" /></li>
								</a>
							</c:when>
							<c:otherwise>
								<a class="sort-asc" href="${userOrderByDescURL}">
									<li class="section-head-40-left"><spring:message code="users" /></li>
								</a>
							</c:otherwise>
						</c:choose>
						<li class="section-head-20"><spring:message code="last.connection" /></li>
						<li class="section-head-20"><spring:message code="creation.date" /></li>
						<li class="section-head-10"><spring:message code="status" /></li>
						<li class="section-head-10-right"><spring:message code="action" /></li>
						<c:forEach var="user" items="${users}">
							<portlet:actionURL name="userChangeStatus" var="userChangeStatusURL" >
								<portlet:param name="userId" value="${user.id}"/>
							</portlet:actionURL>
							<ul class="section-body">
								<li class="section-body-40">${user.emailAddress}</td>
								
								<jsp:setProperty name="lastConnectionDateValue" property="time" value="${user.lastConnection}"/>
								<jsp:setProperty name="creationDateValue" property="time" value="${user.creationDate}"/>
								
								<fmt:formatDate value="${lastConnectionDateValue}" pattern="${dateFormat}" var="lastConnectionVar"/>
								<fmt:formatDate value="${creationDateValue}" pattern="${dateFormat}" var="creationDateVar"/>
								
								<li class="section-body-20">
									<c:choose>
										<c:when test="${lastConnectionVar eq '31-12-1999 23:00'}">
											<spring:message code="user.not.connected" />
										</c:when>
										<c:otherwise>${lastConnectionVar}</c:otherwise>
									</c:choose>
								</li>
								<li class="section-body-20">${creationDateVar}</li>
								<c:choose>
									<c:when test="${user.status eq '0'}">
										<li class="section-body-6"><div id="user-first"></div></li>
										<li class="section-body-6"><a href="${userChangeStatusURL}" ><img class="action-red" src="${THEME_DISPLAY.pathThemeImages}/administration/action.png"></a></li>
									</c:when>
									<c:when test="${user.status eq '1'}">
										<li class="section-body-6"><div id="user-block"></div></li>
										<li class="section-body-6"><a href="${userChangeStatusURL}" ><img class="action-green" src="${THEME_DISPLAY.pathThemeImages}/administration/action.png" ></a></li>
									</c:when>
									<c:when test="${user.status eq '2'}">
										<li class="section-body-6"><div id="user-connect"></div></li>
										<li class="section-body-6"><a href="${userChangeStatusURL}" ><img class="action-red" src="${THEME_DISPLAY.pathThemeImages}/administration/action.png" ></a></td>
									</c:when>
									<c:when test="${user.status eq '3'}">
										<li class="section-body-6"><div id="user-block"></div></li>
										<li class="section-body-6"><a href="${userChangeStatusURL}" ><img class="action-green" src="${THEME_DISPLAY.pathThemeImages}/administration/action.png" ></a></li>
									</c:when>
								</c:choose>
							</ul>
						</c:forEach>
					</div>			
					<ul class="paging">
						<li><c:if test="${page gt 0}">
							<portlet:renderURL var="goPrevURL">
								<portlet:param name="page" value="${page -1}" />
								<portlet:param name="size" value="${size}" />
							</portlet:renderURL>
							<a href="${goPrevURL}" class="prev"><spring:message code="prev" /></a>	
						</c:if>
						<li>
							<portlet:renderURL var="currentURL">
								<portlet:param name="page" value="${page}" />
								<portlet:param name="size" value="${size}" />
							</portlet:renderURL>
							<a href="${currentURL}">${page + 1}</a>
						</li>
						<li><spring:message code="of" /> ${totalPages}</li>
						<li><c:if test="${page lt totalPages - 1}">
							<portlet:renderURL var="goNextURL">
								<portlet:param name="page" value="${page + 1}" />
								<portlet:param name="size" value="${size}" />
							</portlet:renderURL>
							<a href="${goNextURL}" class="next"><spring:message code="next" /></a>
						</c:if></li>
					</ul>
					
				</c:when>
				<c:otherwise>
					<div id="message"><spring:message code="user.delete.uniq" /></div>
				</c:otherwise>
			</c:choose>
	</div>
</div>
<center><table width="50%">
		<tr><td><a href="${registerUserURL}" class="button"><spring:message code="add.new.user" /></a></td></tr>
</table></center>
