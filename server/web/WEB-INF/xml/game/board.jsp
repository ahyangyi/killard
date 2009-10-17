<%@ include file="/WEB-INF/xml/includes.jsp" %>
<%@ page import="com.killard.board.card.ElementSchool" %>
<%@ page import="com.killard.board.card.MetaCard" %>
<%@ page import="com.killard.board.jdo.board.record.PlayerRecordDO" %>
<%--@elvariable id="playerName" type="java.lang.String"--%>
<%--@elvariable id="board" type="com.killard.board.jdo.board.BoardDO"--%>
<%--@elvariable id="players" type="java.util.List<com.killard.board.card.Player>"--%>
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
                                    for (MetaCard metaCard : player.getDealtCards(elementSchool)) {
                                        pageContext.setAttribute("board", metaCard);
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
                <c:forEach var="card" items="${player.equippedCards}">
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