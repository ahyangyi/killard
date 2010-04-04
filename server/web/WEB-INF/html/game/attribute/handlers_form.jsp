<%@ include file="../../includes.jsp" %>
<%--@elvariable id="attribute" type="com.killard.board.jdo.board.AttributeDO"--%>
<%--@elvariable id="actions" type="java.util.Set<java.lang.Class>"--%>
<form action="" method="POST" class="horizontal">
    <fieldset>
        <c:set var="validators" value="${attribute.validators}"/>
        <c:set var="before" value="${attribute.before}"/>
        <c:set var="after" value="${attribute.after}"/>
        <%@ include file="../handlers.jsp" %>
    </fieldset>
</form>