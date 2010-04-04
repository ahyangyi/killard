<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<%--@elvariable id="card" type="com.killard.board.jdo.board.MetaCardDO"--%>
<%--@elvariable id="skill" type="com.killard.board.jdo.board.SkillDO"--%>
<form action="" method="POST" class="horizontal" id="card-form">
    <fieldset>
        <c:set var="descriptable" value="${skill}"/>
        <%@ include file="../descriptors.jsp" %>
        <hr/>
        <div class="field">
            <a href="<c:url value="/game/${package.name}/element/${element.name}/skill/${skill.name}/delete"/>">Delete</a>
        </div>
    </fieldset>
</form>