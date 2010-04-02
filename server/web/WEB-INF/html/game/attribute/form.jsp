<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<%--@elvariable id="card" type="com.killard.board.jdo.board.MetaCardDO"--%>
<form action="" method="POST" class="horizontal">
<fieldset>
    <legend>${card.descriptor.name}</legend>
    <div class="field">
        <label for="card-description">Description</label>
        <textarea cols="32" rows="6" id="card-description" name="description"></textarea>
    </div>
    <div class="field">
        <label for="card-level">Level</label>
        <input type="text" name="level" id="card-level" value="${card.level}"/>
    </div>
    <div class="field">
        <label for="card-health">Health</label>
        <input type="text" name="health" id="card-health" value="${card.maxHealth}"/>
    </div>
    <div class="field">
        <label for="card-attack">Attack</label>
        <input type="text" name="attack" id="card-attack" value="${card.attack.value}"/>
    </div>
    <fieldset>
        <legend>Skills</legend>
        <c:forEach var="skill" items="${card.skills}">
            <div class="field">
                <label>${skill.descriptor.name}</label>
                <textarea cols="32" rows="6" name="skill">${skill.function}</textarea>
            </div>
        </c:forEach>
    </fieldset>
</fieldset>
</form>