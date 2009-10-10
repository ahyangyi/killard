<%@ page import="com.killard.web.jdo.CardDO" %>
<%@ include file="/WEB-INF/html/header.jsp" %>
<%--@elvariable id="packages" type="java.util.List<com.killard.web.jdo.PackageDO>"--%>
<table style="width:100%;">
    <c:forEach var="package" items="${packages}">
        <tr>
            <td style="width:100%;">
                <table style="width:100%;border-style:ridge;">
                    <tr>
                        <td colspan="2">
                            <form action="/game/package.html" method="GET">
                                <input type="hidden" name="packageId" value="${package.key.id}">
                                <table>
                                    <tr>
                                        <td>${package.descriptor.name}</td>
                                        <td><input type="submit" value="Details"></td>
                                    </tr>
                                </table>
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td>ElementSchool</td>
                        <td>Card Amount</td>
                        <td>Details</td>
                    </tr>
                    <c:forEach var="elementSchool" items="${package.elementSchools}">
                        <form action="/game/package/elementschool.html" method="GET">
                            <input type="hidden" name="packageId" value="${package.key.id}">
                            <input type="hidden" name="elementSchoolId" value="${elementSchool.key.id}">
                            <tr>
                                <td>${elementSchool.descriptor.name}</td>
                                <td>
                                    <c:set var="cards" value="${elementSchool.cards}"/>
                                    <%=((CardDO[]) pageContext.getAttribute("cards")).length%>
                                </td>
                                <td><input type="submit" value="Details"></td>
                            </tr>
                        </form>
                    </c:forEach>
                </table>
            </td>
            <td>
                <form action="/game/package/delete.html" method="POST">
                    <input type="hidden" name="packageId" value="${package.key.id}">
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<p></p>
<table style="border: 1px ridge;">
    <tr>
        <form action="/game/package/add.html" method="POST">
            <td>New Package:</td>
            <td><input type="text" name="packageName"></td>
            <td><input type="submit" value="Submit"></td>
        </form>
    </tr>
</table>
<%@ include file="/WEB-INF/html/footer.jsp" %>