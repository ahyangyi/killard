<%@ include file="header.jsp" %>
<%--@elvariable id="packages" type="java.util.List<com.killard.board.jdo.game.GamePackageDO>"--%>
<%--@elvariable id="boards" type="java.util.List<com.killard.board.jdo.board.BoardDO>"--%>
<form action="/board/new.html" method="POST">
    <fieldset title="Create New Game">
        <select name="packageId">
            <c:forEach var="package" items="${packages}">
                <option value="${package.key.id}">${package.descriptor.name}</option>
            </c:forEach>
        </select>
        <input type="hidden" name="maxPlayerNumber" value="2"/>
        <input type="submit" value="New Game"/>
    </fieldset>
</form>
<%@ include file="footer.jsp" %>