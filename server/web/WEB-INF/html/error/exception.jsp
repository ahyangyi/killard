<%@ page session="false" isErrorPage="true" %>
<%@ include file="../header.jsp" %>
<div class="error">
    <strong>Oops, we got an internal error, it will be fixed soon.</strong>
    <table style="width:100%;background-color:lightcoral;">
        <thead>
        <tr>
            <th>Error Code</th>
            <th>Exception</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${requestScope["javax.servlet.error.status_code"]}</td>
            <td><code>${requestScope["javax.servlet.error.exception"]}</code></td>
        </tr>
        </tbody>
    </table>
</div>
<%@ include file="../footer.jsp" %>