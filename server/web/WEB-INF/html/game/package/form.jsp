<%@ include file="../../includes.jsp" %>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<fieldset>
    <legend>${package.descriptor.name}</legend>
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
    <div class="field">
        <label for="rule">Game Rule</label>
        <textarea cols="32" rows="6" id="rule" name="rule">${package.rule}</textarea>
    </div>
</fieldset>