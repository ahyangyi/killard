<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<form action="<c:url value="/game/${package.name}/newelement"/>" method="POST" class="horizontal">
<fieldset>
    <legend>${package.descriptor.name}</legend>
    <fieldset>
        <legend>New Element</legend>
        <div class="field">
            <label for="element_id">ID</label>
            <input type="text" id="element_id" name="elementId"/>
        </div>
        <div class="field">
            <input type="submit" value="Create"/>
        </div>
    </fieldset>
</fieldset>
</form>