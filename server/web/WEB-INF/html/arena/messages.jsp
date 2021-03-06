<%@ include file="../includes.jsp" %>
<%--@elvariable id="players" type="java.util.List<com.killard.board.jdo.board.record.PlayerRecordDO>"--%>
<table width="100%" style="vertical-align:top;background:lightcyan">
    <thead>
    <form action="/message.html" method="POST">
        <td>
            <select name="to" title="To">
                <c:forEach var="player" items="${players}">
                    <c:if test="${player.id != playerId}">
                        <option value="${player.id}">${player.id}</option>
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
        <c:if test="${message.from == playerId or message.to == playerId or message.to == null or empty message.to}">
            <tr>
                <td>${message.from}:</td>
                <td>${message.message}</td>
            </tr>
        </c:if>
    </c:forEach>
    </tbody>
</table>