<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="editor" type="boolean"--%>
<h1>${package.descriptor.name}</h1>
<p>${package.descriptor.description}</p>
<c:if test="${editor}">
    <a href="<c:url value="/game/${bundle.name}"><c:param name="mode" value="edit"/></c:url>">Edit</a>
</c:if>