<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<%--@elvariable id="card" type="com.killard.board.jdo.board.MetaCardDO"--%>
<%@ include file="../locale_options.jsp" %>
<fieldset style="display:none;" id="attribute_options">
    <div class="field">
        <select name="attributes">
            <c:forEach var="attribute" items="${element.attributes}">
                <option value="${attribute.name}">${attribute.descriptor.name}</option>
            </c:forEach>
        </select>
        <a href="#" class="delete">delete</a>
    </div>
</fieldset>
<fieldset style="display:none;" id="skill_options">
    <div class="field">
        <select name="skills">
            <c:forEach var="skill" items="${element.skills}">
                <option value="${skill.name}">${skill.descriptor.name}</option>
            </c:forEach>
        </select>
        <a href="#" class="delete">delete</a>
    </div>
</fieldset>
<form action="" method="POST" class="horizontal" id="card-form">
    <fieldset>
        <c:set var="descriptable" value="${card}"/>
        <%@ include file="../descriptors.jsp" %>
        <div class="field">
            <label for="level">Level</label>
            <input id="level" type="text" name="level" value="${card.level}"/>
        </div>
        <div class="field">
            <label for="health">Health</label>
            <input id="health" type="text" name="health" value="${card.maxHealth}"/>
        </div>
        <div class="field">
            <label for="attack">Attack</label>
            <input id="attack" type="text" name="attack" value="${card.attackValue}"/>
        </div>
        <div class="field">
            <label for="range">Range</label>
            <input id="range" type="text" name="range" value="${card.range}"/>
        </div>
        <fieldset>
            <legend>Attributes&nbsp;<a href="#" class="new" options="attribute_options">add</a>
            </legend>
            <c:forEach var="attribute" items="${card.attributes}" varStatus="status">
                <fieldset>
                    <div class="field">
                        <select name="attributes">
                            <option value="${attribute.name}">${attribute.descriptor.name}</option>
                        </select>
                        <a href="#" class="delete">delete</a>
                    </div>
                </fieldset>
            </c:forEach>
        </fieldset>
        <fieldset>
            <legend>Skills&nbsp;<a href="#" class="new" options="skill_options">add</a>
            </legend>
            <c:forEach var="skill" items="${card.skills}" varStatus="status">
                <fieldset>
                    <div class="field">
                        <select name="skills">
                            <option value="${skill.name}">${skill.descriptor.name}</option>
                        </select>
                        <a href="#" class="delete">delete</a>
                    </div>
                </fieldset>
            </c:forEach>
        </fieldset>
        <div class="field">
            <input type="submit" value="Save"/>
        </div>
        <hr/>
        <div class="field">
            <a href="<c:url value="/game/${package.name}/element/${element.name}/card/${card.name}/delete"/>">Delete</a>
        </div>
    </fieldset>
</form>