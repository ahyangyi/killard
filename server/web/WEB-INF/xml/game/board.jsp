<%@ include file="/WEB-INF/xml/includes.jsp" %>
<%@ page import="com.killard.card.Card" %>
<%@ page import="com.killard.card.ElementSchool" %>
<%@ page import="com.killard.jdo.board.player.PlayerRecordDO" %>
<%--@elvariable id="playerName" type="java.lang.String"--%>
<%--@elvariable id="board" type="com.killard.jdo.board.BoardManagerDO"--%>
<%--@elvariable id="players" type="java.util.List<com.killard.card.Player>"--%>
<board>
    <c:forEach var="player" items="${board.players}">
        <c:set var="player" scope="page" value="${player}"/>
        <%PlayerRecordDO player = (PlayerRecordDO) pageContext.getAttribute("player");%>
        <player>
            <name>${player.id}</name>
            <health>${player.health}</health>
            <elements>
                <c:forEach var="elementSchool" items="${player.allElementSchool}">
                    <c:set var="elementSchool" scope="page" value="${elementSchool}"/>
                    <%ElementSchool elementSchool = (ElementSchool) pageContext.getAttribute("elementSchool");%>
                    <element>
                        <type>${elementSchool}</type>
                        <amount>
                            <%=player.getElementAmount(elementSchool)%>
                        </amount>
                        <c:if test="${player.id == playerName}">
                            <holdedcards>
                                <%
                                    for (Card card : player.getHoldedCards(elementSchool)) {
                                        pageContext.setAttribute("card", card);
                                %>
                                <card>
                                    <name>${card.name}</name>
                                    <elementSchool>${card.elementSchool}</elementSchool>
                                    <level>${card.level}</level>
                                    <maxHealth>${card.maxHealth}</maxHealth>
                                    <health>${card.health}</health>
                                    <attack>${card.attack}</attack>
                                    <skill>${card.skill}</skill>
                                    <attributes>
                                        <c:forEach var="attribute" items="${card.visibleAttributes}">
                                            <attribute>
                                                <name>${attribute.name}</name>
                                                <hidden>${attribute.visible}</hidden>
                                            </attribute>
                                        </c:forEach>
                                    </attributes>
                                </card>
                                <%
                                    }
                                %>
                            </holdedcards>
                        </c:if>
                    </element>
                </c:forEach>
            </elements>
            <livingcards>
                <c:forEach var="card" items="${player.livingCards}">
                    <cardinstance>
                        <name>${card.name}</name>
                        <position>${card.position}</position>
                        <target>${card.target.name}</target>
                        <elementSchool>${card.elementSchool}</elementSchool>
                        <level>${card.level}</level>
                        <maxHealth>${card.maxHealth}</maxHealth>
                        <health>${card.health}</health>
                        <attack>${card.attack}</attack>
                        <skill>${card.skill}</skill>
                        <attributes>
                            <c:forEach var="attribute" items="${card.visibleAttributes}">
                                <attribute>
                                    <name>${attribute.name}</name>
                                    <hidden>${attribute.visible}</hidden>
                                </attribute>
                            </c:forEach>
                        </attributes>
                    </cardinstance>
                </c:forEach>
            </livingcards>
        </player>
    </c:forEach>
</board>