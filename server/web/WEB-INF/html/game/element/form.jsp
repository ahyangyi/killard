<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<%@ include file="../locale_options.jsp" %>
<form action="<c:url value="/game/${package.name}/element/${element.name}"/>" method="POST" class="horizontal">
    <fieldset>
        <c:set var="descriptable" value="${element}"/>
        <%@ include file="../descriptors.jsp" %>
        <hr/>
        <div class="field">
            <a href="<c:url value="/game/${package.name}/element/${element.name}/newcard"/>">New Card</a>
        </div>
        <div class="field">
            <a href="<c:url value="/game/${package.name}/element/${element.name}/newskill"/>">New Skill</a>
        </div>
        <div class="field">
            <a href="<c:url value="/game/${package.name}/element/${element.name}/newattribute"/>">New Attribute</a>
        </div>
        <hr/>
        <div class="field">
            <a href="<c:url value="/game/${package.name}/element/${element.name}/delete"/>">Delete</a>
        </div>
    </fieldset>
</form>