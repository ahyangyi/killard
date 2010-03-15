<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementSchoolDO"--%>
<%--@elvariable id="card" type="com.killard.board.jdo.board.MetaCardDO"--%>
<fieldset>
    <legend>${element.descriptor.name}</legend>
    <fieldset>
        <legend>Clone</legend>
        <ul class="fields">
            <li>
                <label><input type="radio" class="radio" name="field4"/> Public </label>
            </li>
            <li>
                <label><input type="radio" class="radio" name="field4"/> Private </label>
            </li>
        </ul>
    </fieldset>
</fieldset>