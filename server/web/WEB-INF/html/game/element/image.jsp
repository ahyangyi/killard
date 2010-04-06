<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<div style="text-align:center;">
    <img src="/image/index.png"/>
</div>
<hr/>
<form action="<c:url value="/game/${bundle.name}/element/${element.name}/image"/>"
      method="POST" enctype="multipart/form-data">
    <fieldset>
        <legend>Upload Image</legend>
        <div class="field">
            <label for="element-image">Choose Image</label>
            <input type="file" name="image" id="element-image"/>
        </div>
        <div class="field">
            <input type="submit" value="Submit"/>
        </div>
    </fieldset>
</form>