<%@ page session="false"%>
<HEAD>
<TITLE>Release session</TITLE>
</HEAD>
<BODY>
<h2>Release output</h2>
<p><br><%=request.getAttribute("operationInfo")%></p>
<p><%=request.getAttribute("sessionToReleaseInfo")%></p>
<p><br>
<a href=<%=response.encodeURL("index.html")%>>Home</a>
</BODY>
