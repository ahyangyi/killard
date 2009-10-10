<%@ include file="../includes.jsp" %>
<table width="100%" bgcolor="beige">
    <c:forEach var="action" items="${board.actions}">
        <tr>
            <td>
                <c:choose>
                    <c:when test="${action.playerName == playerName}">
                        <span style="color:black">${action}</span>
                    </c:when>
                    <c:otherwise>
                        <span style="color:red">${action}</span>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>