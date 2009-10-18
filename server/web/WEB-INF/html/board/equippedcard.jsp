<%@ include file="../includes.jsp" %>
<%--@elvariable id="board" type="com.killard.board.jdo.board.BoardDO"--%>
<%--@elvariable id="record" type="com.killard.board.jdo.board.record.CardRecordDO"--%>
<c:set var="card" value="${record.card}"/>
<c:choose>
    <c:when test="${card.renderable}">
        <img src="<c:url value="/package/${board.boardPackage.bundleKey.id}/${record.elementSchool.name}/${record.name}/image.png"/>">
    </c:when>
    <c:otherwise>
        ${card.name}
    </c:otherwise>
</c:choose>
<c:forEach var="skill" items="${record.skills}">
<%@ include file="skill.jsp"%>
</c:forEach>
<form action="<c:url value="/package/${board.boardPackage.bundleKey.id}/${record.elementSchool.name}/${record.name}/updateimage.html"/>" method="POST"
      enctype="multipart/form-data">
    <table style="width:100%;table-layout:fixed;">
        <tr>
            <td><input type="file" name="image" size="1"/></td>
            <td><input type="submit" value="U"/></td>
        </tr>
    </table>
</form>
