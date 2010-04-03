<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<%--@elvariable id="skill" type="com.killard.board.jdo.board.SkillDO"--%>
<form action="<c:url value="/game/${package.name}/element/${element.name}/skill/${skill.name}/delete"/>" method="POST"
      class="horizontal">
    <fieldset>
        <legend>${skill.descriptor.name}</legend>
        <fieldset>
            <legend>Delete Skill?</legend>
            <div class="field">
                <input type="submit" value="Confirm"/>
            </div>
        </fieldset>
    </fieldset>
</form>