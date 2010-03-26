<%@ include file="../../includes.jsp" %>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="role" type="com.killard.board.jdo.board.RoleDO"--%>
<%--@elvariable id="actions" type="java.util.Set<java.lang.Class>"--%>
<form action="" method="POST" class="horizontal" id="card-form">
<fieldset>
    <fieldset>
        <legend>Validator</legend>
            <hr/>
        <c:forEach var="handler" items="${role.validators}">
            <div class="field">
                <label for="validator_action_${handler}">Action</label>
                <select id="validator_action_${handler}">
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
                <select id="validator_target_${handler}">
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
                <textarea id="validator_function_${handler}" rows="6" cols="80">${handler.function}</textarea>
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
                <select id="before_action_${handler}">
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
                <select id="before_target_${handler}">
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
                <textarea id="before_function_${handler}" rows="6" cols="80">${handler.function}</textarea>
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
                <select id="after_action_${handler}">
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
                <select id="after_target_${handler}">
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
                <textarea id="after_function_${handler}" rows="6" cols="80">${handler.function}</textarea>
            </div>
            <hr/>
        </c:forEach>
    </fieldset>
</fieldset>
</form>