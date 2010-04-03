<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<form action="<c:url value="/game/${package.name}/element/${element.name}/newskill"/>" method="POST" class="horizontal">
<fieldset>
    <legend>${element.descriptor.name}</legend>
    <fieldset>
        <legend>New Skill</legend>
        <div class="field">
            <label for="skill_id">ID</label>
            <input type="text" id="skill_id" name="skillId"/>
        </div>
        <div class="field">
            <input type="submit" value="Create"/>
        </div>
    </fieldset>
</fieldset>
</form>