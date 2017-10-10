
<%@ page import="signup.ActivityItem" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'activityItem.label', default: 'ActivityItem')}" />
		<title>${group.name} Activity List</title>
	</head>
	<body>

		<a href="#list-activityItem" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				%{--<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>--}%
				<g:if test="${params.add || params.admin}">
                    <li><g:link class="create" action="create" params="[g: group.id]"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				</g:if>
			</ul>
		</div>
		<div id="list-activityItem" class="content scaffold-list" role="main">
			<h1>${group.name} Activity List</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="activityDate" params="[g: group.id]" title="${message(code: 'activityItem.name.label', default: 'Activity Date')}" />
                        <td>Comments</td>

					</tr>
				</thead>
				<tbody>
				<g:each in="${activityItemInstanceList}" status="i" var="activityItemInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td>
                            <a class="create" href="${createLink(controller: 'JoinItem', action: 'list', params: [g:group.id, activityId: activityItemInstance.id])}">
                                <g:formatDate format="yyyy-MM-dd" date="${activityItemInstance.activityDate}"/>
                            </a>
                            (Counts: ${activityItemInstance.activeJoinItems().size()})
                        </td>
                        <td>
                            ${activityItemInstance.memo}
                        </td>
                        <g:if test="${params.admin}">
                            <td>
                                <a class="create" href="${createLink(controller: 'ActivityItem', action: 'edit', params: [id:activityItemInstance.id])}">
                                    Edit
                                </a>
                            </td>
                        </g:if>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${activityItemInstanceTotal}" params="[g :group.id]"/>
			</div>
		</div>
	</body>
</html>
