<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<%--@elvariable id="attribute" type="com.killard.board.jdo.board.AttributeDO"--%>
<form action="<c:url value="/game/${package.name}/element/${element.name}/attribute/${attribute.name}/delete"/>"
      method="POST" class="horizontal">
    <fieldset>
        <legend>${attribute.descriptor.name}</legend>
        <fieldset>
            <legend>Delete Attribute?</legend>
            <div class="field">
                <input type="submit" value="Confirm"/>
            </div>
        </fieldset>
    </fieldset>
</form>