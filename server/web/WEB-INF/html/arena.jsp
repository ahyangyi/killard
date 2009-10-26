<html xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://apps.facebook.com/ns/1.0" lang="en">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/html/includes.jsp" %>
<head>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name='author' content='DONG Keren'/>
    <%--<meta name="description" content="Killard Board Game"/>--%>
    <%--<meta name="keywords" content="board, card, online, realtime, community, free, play, game"/>--%>
    <%--<meta name="robots" content="all"/>--%>
    <noscript><p>JavaScript must be enabled in order for you to play game.</p></noscript>
    <link type="text/css" href="/css/default.css" rel="stylesheet"/>
    <link type="text/css" href="/css/ui/arena.css" rel="stylesheet"/>
    <script type="text/javascript" src="/js/jquery.js"></script>
    <script type="text/javascript" src="/js/jquery-ui.js"></script>
    <script type="text/javascript" src="/js/ui/arena.js"></script>
    <title>Killard</title>
</head>
<body>
<%--@elvariable id="playerName" type="java.lang.String"--%>
<%--@elvariable id="players" type="java.util.List<com.killard.board.jdo.board.record.PlayerRecordDO>"--%>
<%--<table style="width:100%;">--%>
<%--<c:forEach var="player" items="${players}">--%>
<%--<tr>--%>
<%--<td style="width:100%;">--%>
<%--<%@ include file="board/player.jsp" %>--%>
<%--</td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td style="width:100%;height:10px;color:cornflowerblue;"></td>--%>
<%--</tr>--%>
<%--</c:forEach>--%>
<%--</table>--%>
<%pageContext.setAttribute("chairs", new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});%>
<div id="board">
    <c:forEach var="i" items="${chairs}">
        <div id="chair${i}" class="chair">
            <div>Left Hand</div>
            <div>Player</div>
            <div>Right Hand</div>
        </div>
    </c:forEach>
</div>
</body>
</html>