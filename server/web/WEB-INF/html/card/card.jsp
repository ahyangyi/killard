<%@ include file="../header.jsp" %>
<%--@elvariable id="card" type="com.killard.web.jdo.CardDO"--%>
<table>
    <tr>
        <td>${card.descriptor.name}</td>
        <td>
            <form action="/package/elementschool.html" method="GET">
                <input type="hidden" name="packageId" value="${card.packageKey.id}">
                <input type="hidden" name="elementSchoolId" value="${card.elementSchoolKey.id}">
                <input type="submit" value="ElementSchool">
            </form>
        </td>
        <td>
            <form action="/package.html" method="GET">
                <input type="hidden" name="packageId" value="${card.packageKey.id}">
                <input type="submit" value="Package">
            </form>
        </td>
    </tr>
</table>
<p></p>
<form action="/card.html" method="POST" enctype="multipart/form-data">
    <input type="hidden" name="packageId" value="${card.packageKey.id}">
    <input type="hidden" name="elementSchoolId" value="${card.elementSchoolKey.id}">
    <input type="hidden" name="cardId" value="${card.key.id}">
    <table style="border-style:ridge">
        <c:if test="${card.descriptor.imageData != null}">
            <tr>
                <td>Current Image:</td>
                <td><img
                        src="/image/card/${card.packageKey.id}_${card.elementSchoolKey.id}_${card.key.id}.png"
                        alt="Current Image"/></td>
            </tr>
        </c:if>
        <tr>
            <td>Definition:</td>
            <td><textarea rows="20" cols="80" name="definition">${card.definition.value}</textarea></td>
        </tr>
        <tr>
            <td>Image:</td>
            <td><input type="file" name="image"></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="Submit"></td>
        </tr>
    </table>
</form>
<%@ include file="../footer.jsp" %>