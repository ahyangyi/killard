<%@ include file="../header.jsp" %>
<%@ page import="com.killard.board.jdo.board.MetaCardDO" %>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<h1>${package.descriptor.name}</h1>
<form action="/board/new.html" method="POST">
    <input type="hidden" name="packageBundleId" value="${package.bundleKey.id}"/>
    <input type="submit" value="New Game"/>
</form>
<hr/>
<table style="width:100%;border:blueviolet;">
    <%--@elvariable id="boards" type="com.killard.board.jdo.board.BoardDO"--%>
    <c:forEach var="board" items="${boards}">
        <tr>
            <td width="100%">
                <table title="Board" width="100%">
                    <tr>
                        <td>Package:</td>
                        <td>${board.package.name}</td>
                    </tr>
                    <c:forEach var="player" items="${board.players}">
                        <tr>
                            <td>Player:</td>
                            <td>${player.id}</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2">
                            <form action="/board/join.html" method="GET">
                                <input name="packageBundleId" type="hidden" value="${package.bundleKey.id}"/>
                                <input name="boardId" type="hidden" value="${board.key.id}"/>
                                <input type="submit" value="Join"/>
                            </form>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </c:forEach>
</table>
<hr/>
<form action="/package/${package.bundleKey.id}/rule.html" method="POST">
    <input type="hidden" name="packageId" value="${package.key.id}"/>
    <table style="border-style:solid">
        <tr>
            <td>Rule:</td>
            <td><textarea rows="20" cols="80" name="definition">${package.rule.definition}</textarea></td>
        </tr>
    </table>
</form>
<p></p>
<table width="100%" style="border-style:solid">
    <tr>
        <td>ElementSchool</td>
        <td>Card Amount</td>
        <td>Details</td>
        <td>Delete</td>
    </tr>
    <c:forEach var="elementSchool" items="${package.elementSchools}">
        <tr>
            <td>${elementSchool.descriptor.name}</td>
            <td>
                <c:set var="cards" value="${elementSchool.cards}"/>
                <%=((MetaCardDO[]) pageContext.getAttribute("cards")).length%>
            </td>
            <td>
                <form action="<c:url value="/package/${package.bundleKey.id}/${elementSchool.name}/view.html"/>" method="GET">
                    <input type="submit" value="Details"/>
                </form>
            </td>
            <td>
                <form action="<c:url value="/package/${package.bundleKey.id}/${elementSchool.name}/delete.html"/>" method="POST">
                    <input type="submit" value="Delete"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<%@ include file="../footer.jsp" %>