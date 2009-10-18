<%@ include file="../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="elementSchool" type="com.killard.board.jdo.board.ElementSchoolDO"--%>
<%--@elvariable id="record" type="com.killard.board.jdo.board.record.CardRecordDO"--%>
<c:set var="card" value="${record.card}"/>
<c:choose>
    <c:when test="${card.renderable}">
        <img src="<c:url value="/package/${bundle.key.id}/${card.name}/${elementSchool.name}/${card.name}/image.png"/>">
    </c:when>
    <c:otherwise>
        ${card.name}
    </c:otherwise>
</c:choose>
<c:forEach var="skill" items="${record.skills}">
<%@ include file="skill.jsp"%>
</c:forEach>
<form action="<c:url value="/package/${bundle.key.id}/${elementSchool.name}/${card.name}/edit.html"/>" method="POST"
      enctype="multipart/form-data">
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
