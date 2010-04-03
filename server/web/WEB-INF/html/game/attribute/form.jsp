<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<%--@elvariable id="attribute" type="com.killard.board.jdo.board.AttributeDO"--%>
<form action="<c:url value="/game/${package.name}/element/${element.name}/attribute/${attribute.name}"/>" method="POST"
      class="horizontal">
    <fieldset>
        <legend>${attribute.descriptor.name}</legend>
        <div class="field">
            <label for="card-description">Description</label>
            <textarea cols="32" rows="6" id="card-description" name="description"></textarea>
        </div>
    </fieldset>
    <hr/>
    <div class="field">
        <a href="<c:url value="/game/${package.name}/element/${element.name}/attribute/${attribute.name}/delete"/>">Delete</a>
    </div>
</form>