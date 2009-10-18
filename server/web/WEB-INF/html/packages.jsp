<%@ include file="header.jsp" %>
<%--@elvariable id="packages" type="java.util.List<com.killard.board.jdo.board.PackageDO>"--%>
<table>
    <c:forEach var="package" items="${packages}">
        <tr>
            <td>
                <a href="/package/${package.bundleKey.id}/view.html">${package.name}</a>
            </td>
        </tr>
    </c:forEach>
</table>
<%@ include file="footer.jsp" %>