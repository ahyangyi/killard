<%@ include file="../includes.jsp" %>
<%--@elvariable id="record" type="com.killard.board.jdo.board.record.CardRecordDO"--%>
<c:set var="card" value="${record.card}"/>
<c:choose>
    <c:when test="${card.descriptor.imageData != null}">
        <img src="/image/card/${card.name}_${card.name}_${card.key.id}.png">
    </c:when>
    <c:otherwise>
        ${card.name}
    </c:otherwise>
</c:choose>
<c:forEach var="skill" items="${record.skills}">
<%@ include file="skill.jsp"%>
</c:forEach>
<form action="/card/instantedit.html" method="POST" enctype="multipart/form-data">
    <input type="hidden" name="packageId" value="${card.name}"/>
    <input type="hidden" name="elementSchoolId" value="${card.name}"/>
    <input type="hidden" name="cardId" value="${card.key.id}"/>
    <table style="width:100%;table-layout:fixed;">
        <tr>
            <td><input type="file" name="image" size="1"/></td>
            <td><input type="submit" value="U"/></td>
        </tr>
    </table>
</form>
