<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="role" type="com.killard.board.jdo.board.ElementDO"--%>
<div style="text-align:center;">
    <img src="<c:url value="/game/${bundle.name}/role/${role.name}/image.png"/>"/>
</div>
<hr/>
<div class="field">
    <label for="card-image">Upload Image</label>
    <input type="file" name="image" id="card-image"/>
</div>