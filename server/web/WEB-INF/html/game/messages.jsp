<%@ include file="../includes.jsp" %>
<table width="100%" style="vertical-align:top;background:lightcyan">
    <thead>
    <form action="/message.html" method="POST">
        <td>
            <select name="to" title="To">
                <c:forEach var="player" items="${board.players}">
                    <c:if test="${player.name != playerName}">
                        <option value="${player.name}">${player.name}</option>
                    </c:if>
                </c:forEach>
                <option value="">All</option>
            </select>
        </td>
        <td>
            <input type="text" name="message"/>
            <input type="submit" value="Send"/>
        </td>
    </form>
    </thead>
    <tbody>
    <c:forEach var="message" items="${board.messages}">
        <c:if test="${message.from == playerName or message.to == playerName or message.to == null or empty message.to}">
            <tr>
                <td>${message.from}:</td>
                <td>${message.message}</td>
            </tr>
        </c:if>
    </c:forEach>
    </tbody>
</table>