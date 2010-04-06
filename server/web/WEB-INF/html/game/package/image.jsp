<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<div style="text-align:center;">
    <img src="<c:url value="/game/${bundle.name}/image.png"/>"/>
</div>
<hr/>
<form action="<c:url value="/game/${bundle.name}/image"/>"
      method="POST" enctype="multipart/form-data">
    <fieldset>
        <legend>Upload Image</legend>
        <div class="field">
            <label for="package-image">Choose Image</label>
            <input type="file" name="image" id="package-image"/>
        </div>
    </fieldset>
</form>