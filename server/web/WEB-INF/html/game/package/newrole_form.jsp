<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<form action="<c:url value="/game/${package.name}/newrole"/>" method="POST" class="horizontal">
<fieldset>
    <legend>${element.descriptor.name}</legend>
    <fieldset>
        <legend>New Role</legend>
        <div class="field">
            <label for="role_id">ID</label>
            <input type="text" id="role_id" name="roleId"/>
        </div>
        <div class="field">
            <input type="submit" value="Create"/>
        </div>
    </fieldset>
</fieldset>
</form>