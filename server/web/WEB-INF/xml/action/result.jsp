<%@ include file="/WEB-INF/xml/includes.jsp" %>
<actions>
    <%--@elvariable id="actions" type="java.util.List<com.killard.board.jdo.game.ActionDO>"--%>
    <c:forEach var="action" items="${actions}">
        <c:out value="${action.xml}" escapeXml="false"/>
    </c:forEach>
</actions>