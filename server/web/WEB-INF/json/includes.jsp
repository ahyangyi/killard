<%@ page contentType="application/json;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/html/includes.jsp" %>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>