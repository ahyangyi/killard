<%@ include file="../includes.jsp" %>
<%--@elvariable id="board" type="com.killard.board.jdo.game.BoardDO"--%>
<%--@elvariable id="player" type="com.killard.board.jdo.game.player.PlayerRecordDO"--%>
<%--@elvariable id="record" type="com.killard.board.jdo.game.player.CardRecordDO"--%>
<%--@elvariable id="skill" type="com.killard.board.jdo.game.GameSkillDO"--%>
<%--@elvariable id="playerId" type="java.lang.String"--%>
<form action="/board/cast.html" method="POST">
    <input type="hidden" name="cardPosition" value="${record.position}"/>
    <input type="hidden" name="skillName" value="${skill.name}"/>
    <div class="menu">
        <ul>
            <c:forEach var="target" items="${skill.targets}">
                <c:choose>
                    <c:when test="${target.name == 'selfcard'}">
                        <li>
                            <select name="target">
                                <c:forEach var="card" items="${player.equippedCards}">
                                    <option value="${card.position}">${card.name}</option>
                                </c:forEach>
                            </select>
                        </li>
                    </c:when>
                    <c:when test="${target.name == 'other'}">
                        <li>
                            <select name="target">
                                <c:forEach var="other" items="${board.players}">
                                    <c:if test="${other.id != playerId}">
                                        <option value="${other.id}">${other.id}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </li>
                    </c:when>
                    <c:when test="${target.name == 'othercard'}">
                        <li>
                            <select name="target">
                                <c:forEach var="other" items="${board.players}">
                                    <c:if test="${other.id != playerId}">
                                        <c:forEach var="card" items="${other.equippedCards}">
                                            <option value="${card.position}">${other.id}:${card.name}</option>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </li>
                    </c:when>
                </c:choose>
            </c:forEach>
            <li><input type="submit" value="${skill.name}"/></li>
        </ul>
    </div>
</form>