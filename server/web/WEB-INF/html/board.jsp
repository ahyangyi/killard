<%@ include file="header.jsp" %>
<%--@elvariable id="playerName" type="java.lang.String"--%>
<%--@elvariable id="players" type="java.util.List<com.killard.board.jdo.board.record.PlayerRecordDO>"--%>
<table style="width:100%;">
    <c:forEach var="player" items="${players}">
        <tr>
            <td style="width:100%;">
                <%@ include file="board/player.jsp" %>
            </td>
        </tr>
        <tr>
            <td style="width:100%;height:10px;color:cornflowerblue;"></td>
        </tr>
    </c:forEach>
</table>
<%@ include file="footer.jsp" %>