<%@ include file="../header.jsp" %>
<%--@elvariable id="packages" type="java.util.List<com.killard.board.jdo.game.GamePackageDO>"--%>
<%--@elvariable id="boards" type="java.util.List<com.killard.board.jdo.board.BoardDO>"--%>
<form action="/board/add.html" method="POST">
    <fieldset title="Create New Game">
        <select name="packageId">
            <c:forEach var="package" items="${packages}">
                <option value="${package.key.id}">${package.descriptor.name}</option>
            </c:forEach>
        </select>
        <input type="hidden" name="maxPlayerNumber" value="2"/>
        <input type="submit" value="New Game"/>
    </fieldset>
</form>
<%--<table style="width:100%;">--%>
    <%--<c:forEach var="record" items="${boards}">--%>
        <%--<tr>--%>
            <%--<td width="100%">--%>
                <%--<table title="Board" width="100%">--%>
                    <%--<tr>--%>
                        <%--<td>Package:</td>--%>
                        <%--<td>${record.package.name}</td>--%>
                    <%--</tr>--%>
                    <%--<c:forEach var="player" items="${record.players}">--%>
                        <%--<tr>--%>
                            <%--<td>Player:</td>--%>
                            <%--<td>${player.name}</td>--%>
                        <%--</tr>--%>
                    <%--</c:forEach>--%>
                    <%--<tr>--%>
                        <%--<td colspan="2">--%>
                            <%--<form action="/record/join.html" method="POST">--%>
                                <%--<input name="boardId" type="hidden" value="${record.key.id}"/>--%>
                                <%--<input type="submit" value="Join"/>--%>
                            <%--</form>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                <%--</table>--%>
            <%--</td>--%>
        <%--</tr>--%>
    <%--</c:forEach>--%>
<%--</table>--%>
<%@ include file="../footer.jsp" %>