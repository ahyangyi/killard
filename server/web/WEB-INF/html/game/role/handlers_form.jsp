<%@ include file="../../includes.jsp" %>
<%--@elvariable id="role" type="com.killard.board.jdo.board.RoleDO"--%>
<%--@elvariable id="actions" type="java.util.Set<java.lang.Class>"--%>
<form action="" method="POST" class="horizontal">
    <fieldset>
        <c:set var="validators" value="${role.validators}"/>
        <c:set var="before" value="${role.before}"/>
        <c:set var="after" value="${role.after}"/>
        <%@ include file="../handlers.jsp" %>
    </fieldset>
</form>