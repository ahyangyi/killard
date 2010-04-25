<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="../includes.jsp" %>
<%--@elvariable id="templates" type="java.util.List<com.killard.board.jdo.board.PackageBundleDO>"--%>
<head>
    <link type="text/css" href="/css/menu.css" rel="stylesheet"/>
    <link type="text/css" href="/css/form.css" rel="stylesheet"/>
    <script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/js/jquery.validate.1.7.js"></script>
    <script type="text/javascript" src="/js/ui/form.js"></script>
    <title>Killard Games - Create Game</title>
</head>
<body>
<%@ include file="../topbar.jsp" %>
<div style="width:80%;text-align:center;">
    <form action="<c:url value="/game"/>" method="POST">
        <input type="hidden" name="filter" value="new"/>
        <fieldset>
            <legend>Create Game</legend>
            <div class="field">
                <label for="package_name">Name</label>
                <input type="text" id="package_name" name="packageId"/>
            </div>
            <div class="field">
                <label for="template_name">Template</label>
                <select id="template_name" name="templateId">
                    <option value="">None</option>
                    <c:forEach var="template" items="${templates}">
                        <option value="${template.name}">${template.release.descriptor.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="field">
                <input type="submit" value="Create"/>
            </div>
        </fieldset>
    </form>
</div>
<%@ include file="../bottombar.jsp" %>
</body>
</html>