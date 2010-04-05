<%@ include file="../includes.jsp" %>
<%--@elvariable id="actions" type="java.util.Set<java.lang.Class>"--%>
<fieldset style="display:none;" id="validator_options">
    <div class="field">
        <label for="validator_action_handler">Action</label>
        <select id="validator_action_handler" name="validator_actionClass">
            <c:forEach var="action" items="${actions}">
                <option value="${action.simpleName}">${action.simpleName}</option>
            </c:forEach>
        </select>
    </div>
    <div class="field">
        <label for="validator_target_handler">Target</label>
        <select id="validator_target_handler" name="validator_selfTargeted">
            <option selected="true" value="true">Self</option>
            <option value="false">All</option>
        </select>
    </div>
    <div class="field">
        <label for="validator_function_handler">Target</label>
        <textarea id="validator_function_handler" rows="6" cols="80" name="validator_function"></textarea>
    </div>
    <div class="field">
        <a href="#" class="delete">delete</a>
    </div>
</fieldset>
<fieldset style="display:none;" id="before_options">
    <div class="field">
        <label for="before_action_handler">Action</label>
        <select id="before_action_handler" name="before_actionClass">
            <c:forEach var="action" items="${actions}">
                <option value="${action.simpleName}">${action.simpleName}</option>
            </c:forEach>
        </select>
    </div>
    <div class="field">
        <label for="before_target_handler">Target</label>
        <select id="before_target_handler" name="before_selfTargeted">
            <option selected="true" value="true">Self</option>
            <option value="false">All</option>
        </select>
    </div>
    <div class="field">
        <label for="before_function_handler">Target</label>
        <textarea id="before_function_handler" rows="6" cols="80" name="before_function"></textarea>
    </div>
    <div class="field">
        <a href="#" class="delete">delete</a>
    </div>
</fieldset>
<fieldset style="display:none;" id="after_options">
    <div class="field">
        <label for="after_action_handler">Action</label>
        <select id="after_action_handler" name="after_actionClass">
            <c:forEach var="action" items="${actions}">
                <option value="${action.simpleName}">${action.simpleName}</option>
            </c:forEach>
        </select>
    </div>
    <div class="field">
        <label for="after_target_handler">Target</label>
        <select id="after_target_handler" name="after_selfTargeted">
            <option selected="true" value="true">Self</option>
            <option value="false">All</option>
        </select>
    </div>
    <div class="field">
        <label for="after_function_handler">Target</label>
        <textarea id="after_function_handler" rows="6" cols="80" name="after_function"></textarea>
    </div>
    <div class="field">
        <a href="#" class="delete">delete</a>
    </div>
</fieldset>