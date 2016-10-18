<%@ page language="java" contentType="text/html" %>
<%@ page isErrorPage="true" %>
<html>
  <body>
    <h1>Error raised during running</h1>
    <p><%= exception.getMessage() %></p>
  </body>
</html>