<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<%--@elvariable id="attribute" type="com.killard.board.jdo.board.AttributeDO"--%>
<div style="text-align:center;">
    <img src="<c:url value="/game/${bundle.name}/element/${element.name}/attribute/${attribute.name}/image.png"/>"/>
</div>
<hr/>
<form action="<c:url value="/game/${bundle.name}/element/${element.name}/attribute/${attribute.name}/image"/>"
      method="POST" enctype="multipart/form-data">
    <fieldset>
        <legend>Upload Image</legend>
        <div class="field">
            <label for="attribute-image">Choose Image</label>
            <input type="file" name="image" id="attribute-image"/>
        </div>
        <div class="field">
            <input type="submit" value="Submit"/>
        </div>
    </fieldset>
</form>