<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<%--@elvariable id="card" type="com.killard.board.jdo.board.MetaCardDO"--%>
<%--@elvariable id="skill" type="com.killard.board.jdo.board.SkillDO"--%>
<fieldset style="display:none;" id="target_options">
    <div class="field">
        <label for="target">Target</label>
        <select id="target" name="targets">
            <option value="self">self</option>
            <option value="all">all</option>
            <option value="other">other</option>
            <option value="owncard">owncard</option>
            <option value="otherscard">otherscard</option>
        </select>
        <a href="#" class="delete">delete</a>
    </div>
</fieldset>
<%@ include file="../locale_options.jsp" %>
<%@ include file="../handler_options.jsp" %>
<form action="" method="POST" class="horizontal" id="skill-form">
    <fieldset>
        <c:set var="descriptable" value="${skill}"/>
        <%@ include file="../descriptors.jsp" %>
        <fieldset>
            <legend>Targets&nbsp;<a href="#" class="new" options="target_options">add</a></legend>
            <c:forEach var="target" items="${skill.targets}" varStatus="status">
                <fieldset>
                    <div class="field">
                        <select name="targets">
                            <option value="${target.name}">${target.name}</option>
                        </select>
                        <a href="#" class="delete">delete</a>
                    </div>
                </fieldset>
            </c:forEach>
        </fieldset>
        <div class="field">
            <label for="cost">Cost</label>
            <input id="cost" type="text" name="cost" value="${skill.cost}"/>
        </div>
        <div class="field">
            <label for="function">Function</label>
            <textarea id="function" rows="6" cols="25" name="function">${skill.function}</textarea>
        </div>
        <div class="field">
            <input type="submit" value="Save"/>
        </div>
        <hr/>
        <div class="field">
            <a href="<c:url value="/game/${package.name}/element/${element.name}/skill/${skill.name}/delete"/>">Delete</a>
        </div>
    </fieldset>
</form>