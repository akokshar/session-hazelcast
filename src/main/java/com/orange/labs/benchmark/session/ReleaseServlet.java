package com.orange.labs.benchmark.session;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author goov5550
 */
public class ReleaseServlet extends AbstractServlet {

	private static final long serialVersionUID = 5521977535191106961L;
	private static Logger logger = Logger.getLogger("session.benchmark.ReleaseServlet");
	// verbose mode for verification
	private static final boolean VERBOSE = false;

	/**
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuilder info = new StringBuilder("No session to invalidate");
		StringBuilder opDetail = new StringBuilder("No Operation");
		HttpSession session = request.getSession(false);
		if (session != null) {
			long time = System.currentTimeMillis();
	        // performs the last update of session data
			List<String> payloadHashCodes = updateSessionData(session);
			time = System.currentTimeMillis() - time;
			StringBuilder sb = new StringBuilder("\nWebSessionBenchmark ");		
			sb.append(session.getId()).append(" \tCreatedAt ");
			sb.append(session.getCreationTime()).append(" \tlastAccessAt ");
			sb.append(session.getLastAccessedTime()).append(" \t");
			sb.append(session.getAttribute("data.size"));
			sb.append("_Duration ").append(time).append(" \tisReleasing");
			if (VERBOSE) {
				sb.append("\nHashcode_of_payload:");
			    for (Iterator<String> it = payloadHashCodes.iterator(); it.hasNext(); ) {
		        	sb.append(" \t").append(it.next());
			      }
			}
			logger.log(Level.INFO, sb.toString());
			// prepare the response
			opDetail = new StringBuilder("Operations on ");
	  		opDetail.append(session.getAttribute("data.size")).append(" data: ");
			opDetail.append(session.getAttribute("attribute.number"));
			opDetail.append(" reads and ").append(session.getAttribute("write.number")); 
			opDetail.append(" writes thru that release request.");
    		// invalidate the session object
			info = new StringBuilder("Session ").append(session.getId());
			session.invalidate();
			info.append(" is invalidated");
		}
		// manage cookies
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("JSESSIONID")) {
					Cookie ck = (Cookie) cookies[i].clone();
					ck.setMaxAge(0);
					response.addCookie(ck);
				}
			}
		}
		// Write response
		request.setAttribute("sessionToReleaseInfo", info);
		request.setAttribute("operationInfo", opDetail);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("releaseResponse.jsp");
		dispatcher.forward(request, response);
		return;
	}

}
