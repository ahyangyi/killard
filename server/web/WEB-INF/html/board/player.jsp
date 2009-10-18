<%@ include file="../includes.jsp" %>
<%--@elvariable id="playerId" type="java.lang.String"--%>
<%--@elvariable id="board" type="com.killard.board.jdo.board.BoardDO"--%>
<%--@elvariable id="player" type="com.killard.board.jdo.board.record.PlayerRecordDO"--%>
<c:choose>
    <c:when test="${player.health == 0}">
        <c:set var="playerColor" value="#808080"/>
    </c:when>
    <c:when test="${board.currentPlayer.id == player.id}">
        <c:set var="playerColor" value="#7cfc00"/>
    </c:when>
    <c:otherwise>
        <c:set var="playerColor" value="#ffe4b5"/>
    </c:otherwise>
</c:choose>
<c:set var="active" scope="page" value="${board.activePlayer.id == player.id and player.id == playerId}"/>
<table style="width:1000px;border: 1px solid ${playerColor};margin:auto;">
    <tr>
        <td style="width:100%;" colspan="5">
            <table style="width:100%;background-color:${playerColor};">
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${player.id == playerId}">
                                <c:set var="facebookUID" value="loggedinuser"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="facebookUID" value="${facebookUID}"/>
                            </c:otherwise>
                        </c:choose>
                        ${player.id}
                        <%--<fb:if-can-see uid="${facebookUID}" what="profile">--%>
                            <%--<fb:profile-pic uid="${facebookUID}" size="square" facebook-logo="false"></fb:profile-pic>--%>
                            <%--<fb:else>${player.name}</fb:else>--%>
                        <%--</fb:if-can-see>--%>
                    </td>
                    <td>HP:${player.health}</td>
                    <td style="width:50%;"></td>
                    <td style="width:150px;">
                        <c:if test="${active}"><a href="/board/endturn.html">End Turn</a></c:if>
                        <c:if test="${player.health <= 0}">Game Over</c:if>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td style="width:100%;" colspan="5">
            <table style="width:100%;border-style:solid;border-width:1px;">
                <tr>
                    <c:forEach var="elementRecord" items="${player.elementRecords}">
                        <td width="16%">
                            <%@ include file="dealtcard.jsp" %>
                        </td>
                    </c:forEach>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <c:forEach var="board" items="${player.equippedCardsView}">
            <c:choose>
                <c:when test="${board != null}">
                    <td style="width:200px;height:300px;padding-left:5px;padding-right:5px;padding-top:0;padding-bottom:0;">
                        <%@ include file="equippedcard.jsp" %>
                    </td>
                </c:when>
                <c:otherwise>
                    <td style="width:200px;height:300px;padding-left:5px;padding-right:5px;padding-top:0;padding-bottom:0;">
                        <table style="width:171px;height:300px;border:dashed;border-width:1px;">
                            <tr>
                                <td></td>
                            </tr>
                        </table>
                    </td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>
