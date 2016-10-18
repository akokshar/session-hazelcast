<%@ page import="java.util.*" %>
<%@ page errorPage="/myErrorPage.jsp" %>
<HEAD>
<TITLE>Session Servlet Output</TITLE>
</HEAD>
<BODY>
<h2>Session Output</h2>
<p><br>
Session ID: <%=session.getId()%> <br>
Is new Session? <%=session.isNew()%></p>
<p>Operation detail on <%=session.getAttribute("data.size")%>
 data (thru <%=session.getAttribute("attribute.number")%> attributes):  
<%=session.getAttribute("attribute.number")%> reads and 
<%=session.getAttribute("write.number")%> writes per request.<br>
HashCode of session data:<br>
<% List<String> hashCodes = (ArrayList<String>) request.getAttribute("payloadHashCodes");
	for (Iterator<String> it = hashCodes.iterator(); it.hasNext(); ) {    
        out.println(it.next() + "<br>");
    }
%>
</p>
<p><br>
<a href=<%=response.encodeURL("ask")%>>Ask again</a></p>
<p><a href=<%=response.encodeURL("release")%>>Release session</a></p>
</BODY>
