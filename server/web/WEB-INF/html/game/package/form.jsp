<%@ include file="../../includes.jsp" %>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%@ include file="../locale_options.jsp" %>
<form action="<c:url value="/game/${package.name}"/>" method="POST" class="horizontal">
    <fieldset>
        <c:set var="descriptable" value="${package}"/>
        <%@ include file="../descriptors.jsp" %>
        <div class="field">
            <input type="submit" value="Save"/>
        </div>
        <hr/>
        <div class="field">
            <a href="<c:url value="/game/${package.name}/rule"/>" id="rule">Rule</a>
        </div>
        <hr/>
        <div class="field">
            <a href="<c:url value="/game/${package.name}/newrole"/>" id="role">New Role</a>
        </div>
        <hr/>
        <div class="field">
            <a href="<c:url value="/game/${package.name}/newelement"/>" id="element">New Element</a>
        </div>
    </fieldset>
</form>
<form action="<c:url value="/game/${package.name}/deletemanager"/>" method="POST" class="horizontal">
    <fieldset>
        <c:forEach var="manager" items="${package}">
            <div class="field">
                <label>${manager}</label>
                <input type="submit" value="Delete"/>
            </div>
        </c:forEach>
    </fieldset>
</form>
<form action="<c:url value="/game/${package.name}/addmanager"/>" method="POST" class="horizontal">
    <fieldset>
        <div class="field">
            <label for="managerId">Add Email</label>
            <input type="text" id="managerId"/>
        </div>
        <div class="field">
            <input type="submit" value="Save"/>
        </div>
    </fieldset>
</form>