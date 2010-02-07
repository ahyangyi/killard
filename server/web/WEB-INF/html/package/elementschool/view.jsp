<%@ include file="../../header.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="elementSchool" type="com.killard.board.jdo.board.ElementSchoolDO"--%>
<table style="width:100%;border-style:ridge;">
    <tr>
        <td>${elementSchool.descriptor.name}</td>
        <td>
            <form action="/package/${bundle.key.id}/view.html" method="GET">
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
            <td>${card.name}</td>
            <td>${card.level}</td>
            <td>${card.maxHealth}</td>
            <td>${card.attackValue}</td>
            <td>
                <c:if test="${card.renderable}">
                    <img src="/package/${bundle.key.id}/${card.elementSchool.name}/${card.name}/image.png"/>
                </c:if>
            </td>
            <td>
                <form action="/package/${bundle.key.id}/${elementSchool.name}/${card.name}/view.html" method="GET">
                    <input type="submit" value="Details"/>
                </form>
            </td>
            <td>
                <form action="/package/${bundle.key.id}/${elementSchool.name}/${card.name}/delete.html" method="POST">
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
            <td>${attribute.name}</td>
            <td>${attribute.visible}</td>
            <td><input type="submit" value="Details"/></td>
            <td><input type="submit" value="Delete"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@ include file="../../footer.jsp" %>