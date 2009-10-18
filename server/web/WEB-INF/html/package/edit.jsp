<%@ include file="../header.jsp" %>
<%@ page import="com.killard.board.jdo.board.MetaCardDO" %>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<h1>${package.descriptor.name}</h1>
<table style="border-style:solid">
    <c:forEach var="user" items="${package.managers}">
        <tr>
            <td>${user.email}</td>
            <td>
                <form action="/package/manager/delete.html" method="POST">
                    <input type="hidden" name="packageId" value="${package.key.id}"/>
                    <input type="hidden" name="email" value="${user.email}"/>
                    <input type="submit" value="Delete"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    <form action="/package/manager/add.html" method="POST">
        <input type="hidden" name="packageId" value="${package.key.id}"/>
        <tr>
            <td><input type="text" name="manager"/></td>
            <td><input type="submit" value="Add Manager"/></td>
        </tr>
    </form>
</table>
<p></p>
<form action="/package/rule.html" method="POST">
    <input type="hidden" name="packageId" value="${package.key.id}"/>
    <table style="border-style:solid">
        <tr>
            <td>Rule:</td>
            <td><textarea rows="20" cols="80" name="definition">${package.rule.definition}</textarea></td>
        </tr>
        <tr>
            <td>Clonable:</td>
            <td>
                Yes<input type="radio" name="clonable" value="true" <c:if test="${package.clonable}">checked="checked"</c:if>/>
                No<input type="radio" name="clonable" value="false" <c:if test="${not package.clonable}">checked="checked"</c:if>/>
            </td>
        </tr>
        <tr>
            <td>Published</td>
            <td>
                Yes<input type="radio" name="published" value="true" <c:if test="${package.published}">checked="checked"</c:if>/>
                No<input type="radio" name="published" value="false" <c:if test="${not package.published}">checked="checked"</c:if>/>
            </td>
        </tr>
        <tr>
            <td>Open:</td>
            <td>
                Yes<input type="radio" name="open" value="true"  <c:if test="${package.open}">checked="checked"</c:if>/>
                No<input type="radio" name="open" value="false"  <c:if test="${not package.open}">checked="checked"</c:if>/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="Update"/></td>
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
                <form action="/package/elementschool.html" method="GET">
                    <input type="hidden" name="packageId" value="${package.key.id}"/>
                    <input type="hidden" name="elementSchoolId" value="${elementSchool.key.id}"/>
                    <input type="submit" value="Details"/>
                </form>
            </td>
            <td>
                <form action="/package/elementschool/delete.html" method="POST">
                    <input type="hidden" name="packageId" value="${package.key.id}"/>
                    <input type="hidden" name="elementSchoolId" value="${elementSchool.key.id}"/>
                    <input type="submit" value="Delete"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<p></p>
<form action="/package/elementschool/add.html" method="POST">
    <input type="hidden" name="packageId" value="${package.key.id}"/>
    <table style="border-style:ridge">
        <tr>
            <td>New element school:</td>
            <td><input type="text" name="elementSchoolName"/></td>
            <td><input type="submit" value="New"/></td>
        </tr>
    </table>
</form>
<%@ include file="../footer.jsp" %>