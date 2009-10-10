<%@ include file="bottombar.jsp"%> 
<div class="copyright">Killard &copy; 2009</div>
<% if (request.isUserInRole("admin")) {%>
<br>
<div class="menu">
    <a href="<c:url value="/manage/reset.html"/>">Reset</a>|
    <a href="<c:url value="/manage/clearpackages.html"/>">Clear Packages</a>|
    <a href="<c:url value="/manage/clearboards.html"/>">Clear Boards</a>
</div>
<%}%>
</body>
</html>
