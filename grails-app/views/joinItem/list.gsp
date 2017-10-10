
<%@ page import="signup.JoinItem" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'joinItem.label', default: group.name + ' JoinItem')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-joinItem" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				%{--<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>--}%
				<li><a class="home"  href="${createLink(controller: 'activityItem', action: 'list', params: [g:group.id])}">${group.name} Activity List</a></li>
				<li><a class="create"  href="${createLink(controller: 'JoinItem', action: 'create', params: [activityId: activityId])}">Join</a></li>
			</ul>
		</div>
		<div id="list-joinItem" class="content scaffold-list" role="main">
			<h1>Members of Date: <g:formatDate format="yyyy-MM-dd" date="${currentDate}"/> (Counts: ${joinItemInstanceTotal})</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
                        <td> Member Name</td>
                        <td> Submit Date</td>
                        <td> Submit From</td>
                        <g:if test="${params.admin}">
                            <td> Update From</td>
                        </g:if>
						%{--<g:sortableColumn property="changeLog.createdDate" params="[activityId: activityId]" title="${message(code: 'joinItem.createDate.label', default: 'Submit Date')}" />--}%

					</tr>
				</thead>
				<tbody>
				<g:each in="${joinItemInstanceList}" status="i" var="joinItemInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}" >
						<td>
                            <g:if test="${joinItemInstance.isDeleted}">
                                ${joinItemInstance.userName}
                            </g:if>
                            <g:else>
                                <a class="edit" href="${createLink(controller: 'joinItem', action: 'edit', params: [id: joinItemInstance.id])}">${joinItemInstance.userName}</a>
                            </g:else>

                        </td>

						<td>${fieldValue(bean: joinItemInstance, field: "changeLog.createdDate")}</td>

						<td>${fieldValue(bean: joinItemInstance, field: "changeLog.createdBy")}</td>

                        <g:if test="${params.admin}">
                            <td>${fieldValue(bean: joinItemInstance, field: "changeLog.lastUpdatedBy")}</td>
                        </g:if>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
                <g:paginate controller="JoinItem" action="list" total="${joinItemInstanceTotal}" params="[g :group.id, activityId: activityId]"/>
			</div>
		</div>
	</body>
</html>
