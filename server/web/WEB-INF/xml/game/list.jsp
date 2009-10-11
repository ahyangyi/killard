<%@ include file="/WEB-INF/xml/includes.jsp" %>
<%--@elvariable id="boards" type="java.util.List<com.killard.jdo.board.BoardManagerDO>"--%>
<list>
    <c:forEach var="board" items="${boards}">
        <board>
            <c:forEach var="player" items="${board.players}">
                <player>${player.id}</player>
            </c:forEach>
        </board>
    </c:forEach>
</list>