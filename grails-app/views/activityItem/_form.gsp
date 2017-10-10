<%@ page import="signup.ActivityItem" %>

<div class="fieldcontain ${hasErrors(bean: activityItemInstance, field: 'activityDate', 'error')} required">
	<label for="activityDate">
		<g:message code="activityItem.activityDate.label" default="Activity Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="activityDate" precision="day"  value="${activityItemInstance?.activityDate}"  />
</div>

%{--<div class="fieldcontain ${hasErrors(bean: activityItemInstance, field: 'group', 'error')} required">
	<label for="group">
		<g:message code="activityItem.group.label" default="Group" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="group" name="group.id" from="${signup.ActivityGroup.list()}" optionKey="id" optionValue="name" value="${groupId}" required="" class="many-to-one"/>
</div>--}%

<div class="fieldcontain ${hasErrors(bean: activityItemInstance, field: 'memo', 'error')} ">
	<label for="memo">
		<g:message code="activityItem.memo.label" default="Description" />
		
	</label>
	<g:textField name="memo" value="${activityItemInstance?.memo}"/>
</div>


