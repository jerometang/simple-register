
<%@ page import="signup.JoinItem" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'joinItem.label', default: 'JoinItem')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-joinItem" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				%{--<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>--}%
                <li><a class="list"  href="${createLink(controller: 'activityItem', action: 'list', params: [g : joinItemInstance?.activity?.group?.id])}">Activity List</a></li>
				<li><a class="list" href="${createLink(controller: 'JoinItem', action: 'list', params: [joinId: joinItemInstance?.id])}">Member List</a></li>
				<li><a class="create" href="${createLink(controller: 'JoinItem', action: 'create', params: [joinId: joinItemInstance?.id])}">Add Member</a></li>
			</ul>
		</div>
		<div id="show-joinItem" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list joinItem">
			
				<g:if test="${joinItemInstance?.activity}">
				<li class="fieldcontain">
					<span id="activity-label" class="property-label"><g:message code="joinItem.activity.label" default="Activity Date" /></span>
					
						<span class="property-value" aria-labelledby="activity-label">
                            <g:formatDate format="yyyy-MM-dd" date="${joinItemInstance?.activity?.activityDate}"/>
						</span>
					
				</li>
				</g:if>
			
				<g:if test="${joinItemInstance?.userName}">
				<li class="fieldcontain">
					<span id="userName-label" class="property-label"><g:message code="joinItem.userName.label" default="Member Name" /></span>
					
						<span class="property-value" aria-labelledby="userName-label"><g:fieldValue bean="${joinItemInstance}" field="userName"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${joinItemInstance?.id}" />
					<g:link class="edit" action="edit" id="${joinItemInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
