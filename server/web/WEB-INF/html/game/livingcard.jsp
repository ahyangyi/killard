<%@ include file="../includes.jsp" %>
<%--@elvariable id="record" type="com.killard.jdo.board.player.CardRecordDO"--%>
<c:set var="card" value="${record.card}"/>
<c:choose>
    <c:when test="${card.imageData != null}">
        <img src="/image/card/${card.id}_${card.id}_${card.key.id}.png">
    </c:when>
    <c:otherwise>
        ${card.id}
    </c:otherwise>
</c:choose>
<form action="/card/instantedit.html" method="POST" enctype="multipart/form-data">
    <input type="hidden" name="packageId" value="${card.id}"/>
    <input type="hidden" name="elementSchoolId" value="${card.id}"/>
    <input type="hidden" name="cardId" value="${card.key.id}"/>
    <table style="width:100%;table-layout:fixed;">
        <tr>
            <td><input type="file" name="image" size="1"/></td>
            <td><input type="submit" value="U"/></td>
        </tr>
    </table>
</form>
