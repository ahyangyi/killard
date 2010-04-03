<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<form action="<c:url value="/game/${package.name}/element/${element.name}/newattribute"/>" method="POST" class="horizontal">
<fieldset>
    <legend>${element.descriptor.name}</legend>
    <fieldset>
        <legend>New Attribute</legend>
        <div class="field">
            <label for="attribute_id">ID</label>
            <input type="text" id="attribute_id" name="attributeId"/>
        </div>
        <div class="field">
            <input type="submit" value="Create"/>
        </div>
    </fieldset>
</fieldset>
</form>