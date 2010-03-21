<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementSchoolDO"--%>
<%--@elvariable id="card" type="com.killard.board.jdo.board.MetaCardDO"--%>
<div style="text-align:center;">
    <img src="<c:url value="/games/${bundle.name}/${package.key.id}/${element.name}/${card.name}.png"/>"/>
</div>
<hr/>
<div class="field">
    <label for="card-image">Upload Image</label>
    <input type="file" name="image" id="card-image"/>
</div>