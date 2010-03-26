<%@ include file="../../includes.jsp" %>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<fieldset>
    <legend>${package.descriptor.name}</legend>
    <fieldset>
        <legend>Clone</legend>
        <ul class="fields">
            <li>
                <label><input type="radio" class="radio" name="field4"/>Public</label>
            </li>
            <li>
                <label><input type="radio" class="radio" name="field4"/>Private</label>
            </li>
        </ul>
    </fieldset>
    <div class="field">
        <a href="<c:url value="/game/${package.name}/rule"/>">Rule</a>
    </div>
</fieldset>