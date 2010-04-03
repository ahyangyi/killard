<%@ include file="../../includes.jsp" %>
<%--@elvariable id="role" type="com.killard.board.jdo.board.RoleDO"--%>
<%--@elvariable id="actions" type="java.util.Set<java.lang.Class>"--%>
<form action="" method="POST" class="horizontal">
<fieldset>
    <fieldset>
        <legend>Validator</legend>
            <hr/>
        <c:forEach var="handler" items="${role.validators}">
            <div class="field">
                <label for="validator_action_${handler}">Action</label>
                <select id="validator_action_${handler}" name="validator_actionClass">
                    <c:forEach var="action" items="${actions}">
                        <c:choose>
                            <c:when test="${action == handler.actionClass}">
                                <option selected="true" value="${action.simpleName}">${action.simpleName}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${action.simpleName}">${action.simpleName}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
            <div class="field">
                <label for="validator_target_${handler}">Target</label>
                <select id="validator_target_${handler}" name="validator_selfTargeted">
                    <c:choose>
                        <c:when test="${handler.selfTargeted}">
                            <option selected="true" value="true">Self</option>
                            <option value="false">All</option>
                        </c:when>
                        <c:otherwise>
                            <option selected="true" value="false">All</option>
                            <option value="false">Self</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
            <div class="field">
                <label for="validator_function_${handler}">Target</label>
                <textarea id="validator_function_${handler}" rows="6" cols="80" name="validator_function">${handler.function}</textarea>
            </div>
            <hr/>
        </c:forEach>
    </fieldset>
    <fieldset>
        <legend>Before</legend>
            <hr/>
        <c:forEach var="handler" items="${role.before}">
            <div class="field">
                <label for="before_action_${handler}">Action</label>
                <select id="before_action_${handler}" name="before_actionClass">
                    <c:forEach var="action" items="${actions}">
                        <c:choose>
                            <c:when test="${action == handler.actionClass}">
                                <option selected="true" value="${action.simpleName}">${action.simpleName}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${action.simpleName}">${action.simpleName}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
            <div class="field">
                <label for="before_target_${handler}">Target</label>
                <select id="before_target_${handler}" name="before_selfTargeted">
                    <c:choose>
                        <c:when test="${handler.selfTargeted}">
                            <option selected="true" value="true">Self</option>
                            <option value="false">All</option>
                        </c:when>
                        <c:otherwise>
                            <option selected="true" value="false">All</option>
                            <option value="false">Self</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
            <div class="field">
                <label for="before_function_${handler}">Target</label>
                <textarea id="before_function_${handler}" rows="6" cols="80" name="before_function">${handler.function}</textarea>
            </div>
            <hr/>
        </c:forEach>
    </fieldset>
    <fieldset>
        <legend>After</legend>
            <hr/>
        <c:forEach var="handler" items="${role.after}">
            <div class="field">
                <label for="after_action_${handler}">Action</label>
                <select id="after_action_${handler}" name="after_actionClass">
                    <c:forEach var="action" items="${actions}">
                        <c:choose>
                            <c:when test="${action == handler.actionClass}">
                                <option selected="true" value="${action.simpleName}">${action.simpleName}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${action.simpleName}">${action.simpleName}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
            <div class="field">
                <label for="after_target_${handler}">Target</label>
                <select id="after_target_${handler}" name="after_selfTargeted">
                    <c:choose>
                        <c:when test="${handler.selfTargeted}">
                            <option selected="true" value="true">Self</option>
                            <option value="false">All</option>
                        </c:when>
                        <c:otherwise>
                            <option selected="true" value="false">All</option>
                            <option value="false">Self</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
            <div class="field">
                <label for="after_function_${handler}">Target</label>
                <textarea id="after_function_${handler}" rows="6" cols="80" name="after_function">${handler.function}</textarea>
            </div>
            <hr/>
        </c:forEach>
    </fieldset>
    <div class="field">
        <input type="submit" value="Save"/>
    </div>
</fieldset>
</form>