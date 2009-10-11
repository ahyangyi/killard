<%@ include file="../header.jsp" %>
<%--@elvariable id="elementSchool" type="com.killard.jdo.card.ElementSchoolDO"--%>
<table style="width:100%;border-style:ridge;">
    <tr>
        <td>${elementSchool.descriptor.name}</td>
        <td>
            <form action="/package.html" method="GET">
                <input type="hidden" name="packageId" value="${elementSchool.packageKey.id}"/>
                <input type="submit" value="Package"/>
            </form>
        </td>
    </tr>
</table>
<table style="width:100%;border-style:ridge;">
    <thead>
    <tr>
        <th>Card</th>
        <th>Level</th>
        <th>Health</th>
        <th>Attack</th>
        <th>Image</th>
        <th>Details</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tfoot>
    <tr>
        <form action="/card/add.html" method="POST">
            <input type="hidden" name="packageId" value="${elementSchool.packageKey.id}"/>
            <input type="hidden" name="elementSchoolId" value="${elementSchool.key.id}"/>
            <td>New Card:</td>
            <td colspan="5"><input type="text" name="cardName"/></td>
            <td><input type="submit" value="New"/></td>
        </form>
    </tr>
    </tfoot>
    <tbody>
    <c:forEach var="card" items="${elementSchool.cards}">
        <tr>
            <td>${card.id}</td>
            <td>${card.level}</td>
            <td>${card.health}</td>
            <td>${card.attackValue}</td>
            <td>
                <c:if test="${card.descriptor.imageData != null}">
                    <img src="/image/card/${card.packageKey.id}_${card.elementSchool.key.id}_${card.key.id}.png"
                         alt="Current Image"/>
                </c:if>
            </td>
            <td>
                <form action="/card.html" method="GET">
                    <input type="hidden" name="packageId" value="${card.packageKey.id}"/>
                    <input type="hidden" name="elementSchoolId" value="${card.elementSchool.key.id}"/>
                    <input type="hidden" name="cardId" value="${card.key.id}"/>
                    <input type="submit" value="Details"/>
                </form>
            </td>
            <td>
                <form action="/card/delete.html" method="POST">
                    <input type="hidden" name="packageId" value="${card.packageKey.id}"/>
                    <input type="hidden" name="elementSchoolId" value="${card.elementSchool.key.id}"/>
                    <input type="hidden" name="cardId" value="${card.key.id}"/>
                    <input type="submit" value="Delete"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<table style="width:100%;border-style:ridge;">
    <thead>
    <tr>
        <th>Attribute</th>
        <th>Visible</th>
        <th>Useful</th>
        <th>Harmful</th>
        <th>Details</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tfoot>
    <tr>
        <td>New Attribute:</td>
        <td colspan="4"><input type="text" name="attribute"/></td>
        <td><input type="submit" value="New"/></td>
    </tr>
    </tfoot>
    <tbody>
    <c:forEach var="attribute" items="${elementSchool.attributes}">
        <tr>
            <td>${attribute.id}:${attribute.descriptor.imageData == null}</td>
            <td>${attribute.visible}</td>
            <td>${attribute.useful}</td>
            <td>${attribute.harmful}</td>
            <td><input type="submit" value="Details"/></td>
            <td><input type="submit" value="Delete"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@ include file="../footer.jsp" %>