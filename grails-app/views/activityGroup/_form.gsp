<%@ page import="signup.ActivityGroup" %>



<div class="fieldcontain ${hasErrors(bean: activityGroupInstance, field: 'activities', 'error')} ">
	<label for="activities">
		<g:message code="activityGroup.activities.label" default="Activities" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${activityGroupInstance?.activities?}" var="a">
    <li><g:link controller="activityItem" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="activityItem" action="create" params="['activityGroup.id': activityGroupInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'activityItem.label', default: 'ActivityItem')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: activityGroupInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="activityGroup.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${activityGroupInstance?.name}"/>
</div>

