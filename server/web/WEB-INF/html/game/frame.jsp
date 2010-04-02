<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="../includes.jsp" %>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<head>
    <link type="text/css" href="/css/layout.css" rel="stylesheet"/>
    <link type="text/css" href="/css/menu.css" rel="stylesheet"/>
    <link type="text/css" href="/css/form.css" rel="stylesheet"/>
    <link type="text/css" href="/css/ui/card.css" rel="stylesheet"/>
    <script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.7.2.min.js"></script>
    <script type="text/javascript" src="/js/jquery.layout-latest.js"></script>
    <script type="text/javascript" src="/js/jquery.validate.1.7.js"></script>
    <script type="text/javascript" src="/js/ui/browser.js"></script>
    <script type="text/javascript" src="/js/ui/form.js"></script>
    <title>Killard Games - Game Settings</title>
</head>
<body>
<%@ include file="../topbar.jsp" %>
<div class="container">
    <c:if test="${menu != null}">
        <div class="ui-layout-west">
            <div class="ui-layout-content">
                    <%--@elvariable id="menu" type="java.lang.String"--%>
                <jsp:include page="${menu}"/>
            </div>
        </div>
    </c:if>
    <div class="ui-layout-center">
        <div class="ui-layout-content">
            <%--@elvariable id="form" type="java.lang.String"--%>
            <jsp:include page="${form}"/>
        </div>
    </div>
    <c:if test="${image != null}">
        <div class="ui-layout-east">
            <div class="ui-layout-content">
                    <%--@elvariable id="image" type="java.lang.String"--%>
                <jsp:include page="${image}"/>
            </div>
        </div>
    </c:if>
    <c:if test="${toolbar != null}">
        <div class="ui-layout-south">
            <div class="ui-layout-content">
                    <%--@elvariable id="toolbar" type="java.lang.String"--%>
                <jsp:include page="${toolbar}"/>
            </div>
        </div>
    </c:if>
</div>
<%@ include file="../bottombar.jsp" %>
</body>
</html>