<%@ page import="signup.JoinItem" %>



<div class="fieldcontain ${hasErrors(bean: joinItemInstance, field: 'userName', 'error')} ">
	<label for="userName">
		<g:message code="joinItem.userName.label" default="Member Name" />
		
	</label>
	<g:textField name="userName" value="${joinItemInstance?.userName}"/>
    <g:hiddenField name="activityId" value="${activityId}"/>
</div>

