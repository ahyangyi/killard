<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="../includes.jsp" %>
<%--@elvariable id="bundles" type="java.util.List<com.killard.board.jdo.board.PackageBundleDO>"--%>
<head>
    <link type="text/css" href="/css/menu.css" rel="stylesheet"/>
    <link type="text/css" href="/css/form.css" rel="stylesheet"/>
    <script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/js/jquery.validate.1.7.js"></script>
    <script type="text/javascript" src="/js/ui/form.js"></script>
    <title>Killard Games - My Games</title>
</head>
<body>
<%@ include file="../topbar.jsp" %>
<div class="container">
    <h3>My Games</h3>
    <table>
        <thead>
        <tr>
            <th>Game</th>
            <th>Title</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="bundle" items="${bundles}">
            <tr>
                <td>${bundle.name}</td>
                <td>${bundle.release.descriptor.name}</td>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="2">
                <a href="<c:url value="/game"/>">Create a Game</a>
            </td>
        </tr>
        </tfoot>
    </table>
</div>
<%@ include file="../bottombar.jsp" %>
</body>
</html>