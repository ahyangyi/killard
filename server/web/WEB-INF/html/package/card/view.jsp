<%@ include file="../../header.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="elementSchool" type="com.killard.board.jdo.board.ElementSchoolDO"--%>
<%--@elvariable id="card" type="com.killard.board.jdo.board.MetaCardDO"--%>
<table>
    <tr>
        <td>${card.descriptor.name}</td>
        <td>
            <form action="/package/elementschool.html" method="GET">
                <input type="submit" value="ElementSchool"/>
            </form>
        </td>
        <td>
            <form action="/package.html" method="GET">
                <input type="submit" value="Package"/>
            </form>
        </td>
    </tr>
</table>
<p></p>
<form action="/${bundle.key.id}/${elementSchool.name}/${card.name}/edit.html" method="POST" enctype="multipart/form-data">
    <input type="hidden" name="cardId" value="${card.key.id}"/>
    <table style="border-style:ridge">
        <c:if test="${card.renderable}">
            <tr>
                <td>Current Image:</td>
                <td><img
                        src="/package/${bundle.key.id}/${card.elementSchool.name}/${card.name}/image.png"
                        alt="Current Image"/></td>
            </tr>
        </c:if>
        <tr>
            <td>Definition:</td>
            <td><textarea rows="20" cols="80" name="definition">${card.definition.value}</textarea></td>
        </tr>
        <tr>
            <td>Image:</td>
            <td><input type="file" name="image"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form>
<%@ include file="../../footer.jsp" %>