{
<%@ include file="/WEB-INF/json/includes.jsp" %>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<c:forEach var="elementSchool" items="${package.elementSchools}" varStatus="status">
    <c:if test="${not status.first}">,</c:if>
    "${elementSchool.name}": {
    <c:forEach var="card" items="${elementSchool.cards}" varStatus="cardStatus">
        <c:if test="${not cardStatus.first}">,</c:if>
        "${card.name}": {
            "level": ${card.level},
            "maxHealth": ${card.maxHealth},
            "attack": ${card.attack.value},
            "skills": {
                <c:forEach var="skill" items="${card.skills}" varStatus="skillStatus">
                    <c:if test="${not skillStatus.first}">,</c:if>
                    "${skill.name}": {}
                </c:forEach>
            }
        }
    </c:forEach>
    }
</c:forEach>
}
