<%@ include file="../header.jsp" %>
<%--@elvariable id="elementSchool" type="com.killard.web.jdo.card.ElementSchoolDO"--%>
<table>
    <tr>
        <td>${elementSchool.descriptor.name}</td>
        <td>
            <form action="/package.html" method="GET">
                <input type="hidden" name="packageId" value="${elementSchool.packageKey.id}">
                <input type="submit" value="Package">
            </form>
        </td>
    </tr>
</table>
<p></p>
<table style="width:100%;border-style:ridge">
    <tr>
        <td>Name</td>
        <td>Level</td>
        <td>Health</td>
        <td>Attack</td>
        <td>Image</td>
        <td>Details</td>
        <td>Delete</td>
    </tr>
    <c:forEach var="card" items="${elementSchool.cards}">
        <tr>
            <td>${card.descriptor.name}</td>
            <td>${card.level}</td>
            <td>${card.health}</td>
            <td>${card.attack.value}</td>
            <td>
                <c:if test="${card.descriptor.imageData != null}">
                    <img src="/image/card/${card.packageKey.id}_${card.elementSchoolKey.id}_${card.key.id}.png"
                         alt="Current Image"/>
                </c:if>
            </td>
            <td>
                <form action="/card.html" method="GET">
                    <input type="hidden" name="packageId" value="${card.packageKey.id}">
                    <input type="hidden" name="elementSchoolId" value="${elementSchool.key.id}">
                    <input type="hidden" name="cardId" value="${card.key.id}">
                    <input type="submit" value="Details">
                </form>
            </td>
            <td>
                <form action="/card/delete.html" method="POST">
                    <input type="hidden" name="packageId" value="${card.packageKey.id}">
                    <input type="hidden" name="elementSchoolId" value="${elementSchool.key.id}">
                    <input type="hidden" name="cardId" value="${card.key.id}">
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<p></p>
<form action="/card/add.html" method="POST">
    <input type="hidden" name="packageId" value="${elementSchool.packageKey.id}">
    <input type="hidden" name="elementSchoolId" value="${elementSchool.key.id}">
    <table style="border-style:ridge">
        <tr>
            <td>New Card:</td>
            <td><input type="text" name="cardName"></td>
            <td><input type="submit" value="New"></td>
        </tr>
    </table>
</form>
<%@ include file="../footer.jsp" %>