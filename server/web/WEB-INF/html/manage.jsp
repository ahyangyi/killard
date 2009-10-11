<%@ include file="includes.jsp" %>
<% if (request.isUserInRole("admin")) {%>
<hr/>
<div class="menu">
    <ul>
        <li><a href="<c:url value="/manage/reset.html"/>">Reset</a>|</li>
        <li><a href="<c:url value="/manage/clearpackages.html"/>">Clear Packages</a>|</li>
        <li><a href="<c:url value="/manage/clearboards.html"/>">Clear Boards</a>|</li>
        <li><a href="/_ah/admin/datastore">Datastore</a>|</li>
        <li><a href="/_ah/admin/taskqueue">Task Queue</a>|</li>
        <li><a href="/_ah/admin/xmpp">XMPP</a></li>
    </ul>
</div>
<%}%>