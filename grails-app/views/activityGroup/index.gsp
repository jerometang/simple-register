
<%@ page import="signup.ActivityGroup" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'activityGroup.label', default: 'ActivityGroup')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
        <style type="text/css" media="screen">
        #status {
            background-color: #eee;
            border: .2em solid #fff;
            margin: 2em 2em 1em;
            padding: 1em;
            width: 12em;
            float: left;
            -moz-box-shadow: 0px 0px 1.25em #ccc;
            -webkit-box-shadow: 0px 0px 1.25em #ccc;
            box-shadow: 0px 0px 1.25em #ccc;
            -moz-border-radius: 0.6em;
            -webkit-border-radius: 0.6em;
            border-radius: 0.6em;
        }

        .ie6 #status {
            display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
        }

        #status ul {
            font-size: 0.9em;
            list-style-type: none;
            margin-bottom: 0.6em;
            padding: 0;
        }

        #status li {
            line-height: 1.3;
        }

        #status h1 {
            text-transform: uppercase;
            font-size: 1.1em;
            margin: 0 0 0.3em;
        }

        #page-body {
            margin: 2em 1em 1.25em 18em;
        }

        h2 {
            margin-top: 1em;
            margin-bottom: 0.3em;
            font-size: 1em;
        }

        p {
            line-height: 1.5;
            margin: 0.25em 0;
        }

        #controller-list ul {
            list-style-position: inside;
        }

        #controller-list li {
            line-height: 1.3;
            list-style-position: inside;
            margin: 0.25em 0;
        }

        @media screen and (max-width: 480px) {
            #status {
                display: none;
            }

            #page-body {
                margin: 0 1em 1em;
            }

            #page-body h1 {
                margin-top: 0;
            }
        }
        </style>
	</head>
	<body>
		<a href="#list-activityGroup" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

    <div class="nav" role="navigation">
        <ul>
            <g:if test="${params.admin}">
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </g:if>
        </ul>
    </div>

    <div id="status" role="complementary">
        <h1>Application Status</h1>
        <ul>
            <li>App version: <g:meta name="app.version"/></li>
            <li>Grails version: <g:meta name="app.grails.version"/></li>
            <li>Groovy version: ${org.codehaus.groovy.runtime.InvokerHelper.getVersion()}</li>
            <li>JVM version: ${System.getProperty('java.version')}</li>
            <li>Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</li>
            <li>Controllers: ${grailsApplication.controllerClasses.size()}</li>
            <li>Domains: ${grailsApplication.domainClasses.size()}</li>
            <li>Services: ${grailsApplication.serviceClasses.size()}</li>
            <li>Tag Libraries: ${grailsApplication.tagLibClasses.size()}</li>
        </ul>

    </div>

    <div id="page-body" role="main">
        <h1>Welcome to Grails</h1>

        <div id="controller-list" role="navigation">
            <h2>Available Activities:</h2>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

                <g:each in="${activityGroupInstanceList}" status="i" var="activityGroupInstance">
                     <ul>
                        <a class="create" href="${createLink(controller: 'ActivityItem', action: 'list', params: [g : activityGroupInstance.id])}">
                            <td>${fieldValue(bean: activityGroupInstance, field: "name")}</td>
                        </a>
                     </ul>
                </g:each>

%{--            <div class="pagination">
                <g:paginate total="${activityGroupInstanceTotal}" />
            </div>--}%
        </div>
    </div>

	</body>
</html>