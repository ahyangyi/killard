<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<%--@elvariable id="attribute" type="com.killard.board.jdo.board.AttributeDO"--%>
<%@ include file="../locale_options.jsp" %>
<form action="<c:url value="/game/${package.name}/element/${element.name}/attribute/${attribute.name}"/>" method="POST"
      class="horizontal">
    <c:set var="descriptable" value="${attribute}"/>
    <%@ include file="../descriptors.jsp" %>
    <hr/>
    <div class="field">
        <a href="<c:url value="/game/${package.name}/element/${element.name}/attribute/${attribute.name}/handlers"/>"
           id="handlers">Handlers</a>
    </div>
    <hr/>
    <div class="field">
        <a href="<c:url value="/game/${package.name}/element/${element.name}/attribute/${attribute.name}/delete"/>">Delete</a>
    </div>
</form>