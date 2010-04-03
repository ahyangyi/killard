<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<form action="<c:url value="/game/${package.name}/delete"/>" method="POST" class="horizontal">
<fieldset>
    <legend>${package.descriptor.name}</legend>
    <fieldset>
        <legend>Delete Package?</legend>
        <div class="field">
            <input type="submit" value="Confirm"/>
        </div>
    </fieldset>
</fieldset>
</form>