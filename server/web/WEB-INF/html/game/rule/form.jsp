<%@ include file="../../includes.jsp" %>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%@ include file="../handler_options.jsp" %>
<form action="" method="POST" class="horizontal">
    <fieldset>
        <c:set var="validators" value="${package.rule.validators}"/>
        <c:set var="before" value="${package.rule.before}"/>
        <c:set var="after" value="${package.rule.after}"/>
        <%@ include file="../handlers.jsp"%> 
    </fieldset>
</form>