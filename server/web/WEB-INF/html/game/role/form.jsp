<%@ include file="../../includes.jsp" %>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="role" type="com.killard.board.jdo.board.RoleDO"--%>
<%--@elvariable id="actions" type="java.util.Set<java.lang.Class>"--%>
<%@ include file="../locale_options.jsp" %>
<%@ include file="../handler_options.jsp" %>
<form action="<c:url value="/game/${package.name}/role/${role.name}"/>" method="POST" class="horizontal">
    <fieldset>
        <c:set var="descriptable" value="${role}"/>
        <%@ include file="../descriptors.jsp" %>
        <div class="field">
            <input type="submit" value="Save"/>
        </div>
        <hr/>
        <div class="field">
            <a href="<c:url value="/game/${package.name}/role/${role.name}/handlers"/>" id="handlers">Handlers</a>
        </div>
        <hr/>
        <div class="field">
            <a href="<c:url value="/game/${package.name}/role/${role.name}/delete"/>">Delete</a>
        </div>
    </fieldset>
</form>