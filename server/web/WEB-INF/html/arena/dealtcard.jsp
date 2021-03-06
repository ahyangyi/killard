<%@ include file="../includes.jsp" %>
<%--@elvariable id="playerId" type="java.lang.String"--%>
<%--@elvariable id="elementRecord" type="com.killard.board.jdo.board.record.ElementRecordDO"--%>
<%--@elvariable id="player" type="com.killard.board.jdo.board.record.PlayerRecordDO"--%>
<div class="elementschool">
    ${elementRecord.element.name}:&nbsp;${elementRecord.resource}
    <c:if test="${player.id == playerId}">
        <form action="<c:url value="/board/playcard.html"/>" method="POST">
            <div id="newcard">
                <select name="cardName">
                    <c:forEach var="card"
                               items="${elementRecord.dealtCards}">
                        <option value="${card.name}">
                            <span style="font-size:10px;">${card.name},${card.level}</span>
                        </option>
                    </c:forEach>
                </select>
                <select name="cardPosition">
                    <c:forEach var="position"
                               items="${player.availablePositions}">
                        <option value="${position}">${position}</option>
                    </c:forEach>
                </select>
            </div>
            <c:if test="${not empty player.availablePositions and active}">
                <input type="hidden" name="elementName" value="${elementRecord.element.name}"/>
                <input type="hidden" name="targetPosition" value="1"/>
                <input type="submit" value="Play"/>
            </c:if>
        </form>
    </c:if>
</div>