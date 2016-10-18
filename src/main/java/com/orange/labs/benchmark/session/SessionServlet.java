package com.orange.labs.benchmark.session;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author goov5550
 */
public class SessionServlet extends AbstractServlet {

	private static final long serialVersionUID = -1801879308614516361L;
	private static Logger logger = Logger.getLogger("session.benchmark.SessionServlet");
	// verbose mode for verification
	private static final boolean VERBOSE = false;
	
	/**
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		StringBuilder sb = new StringBuilder("\nWebSessionBenchmark ");		
		HttpSession session = request.getSession(true);
		//List<String> payloadHashCodes = new ArrayList<String>();
		List<String> payloadHashCodes = null;
		long time = System.currentTimeMillis();
		if (session.isNew()) {
			setSessionParameter(request, session);
			// initialize data of the session
			payloadHashCodes = initSessionData(session);
		} else {
			// update data of the session
			payloadHashCodes = updateSessionData(session);
		}
		// store HashCodes of the session data in order to display them thru JSP
		request.setAttribute("payloadHashCodes", payloadHashCodes);
		time = System.currentTimeMillis() - time;
		sb.append(session.getId()).append(" \tCreatedAt ");
		sb.append(session.getCreationTime()).append(" \tlastAccessAt ");
		sb.append(session.getLastAccessedTime()).append(" \t");
		sb.append(session.getAttribute("data.size"));
		sb.append("_Duration ").append(time);
		if (session.isNew()) {
			sb.append(" \tisNewSession");
		}
		if (VERBOSE) {
			sb.append("\nHashcode_of_payload:");
		    for (Iterator<String> it = payloadHashCodes.iterator(); it.hasNext(); ) {
	        	sb.append(" \t").append(it.next());
		      }
		}
		logger.log(Level.INFO, sb.toString());
		// Write response
		sb = new StringBuilder("Operations on ");
		sb.append(session.getAttribute("data.size")).append(" data (read ");
		sb.append(session.getAttribute("attribute.number")).append(" and write "); 
		sb.append(session.getAttribute("write.number")).append(" attributes).");
		request.setAttribute("operationInfo", sb);
		// Write response
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("sessionResponse.jsp");
		dispatcher.forward(request, response);		
		return;
	}

}
