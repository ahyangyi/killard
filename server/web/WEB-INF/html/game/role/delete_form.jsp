<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="role" type="com.killard.board.jdo.board.RoleDO"--%>
<form action="<c:url value="/game/${package.name}/role/${role.name}/delete"/>" method="POST" class="horizontal">
<fieldset>
    <legend>${role.descriptor.name}</legend>
    <fieldset>
        <legend>Delete Role?</legend>
        <div class="field">
            <input type="submit" value="Confirm"/>
        </div>
    </fieldset>
</fieldset>
</form>