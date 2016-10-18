package com.orange.labs.benchmark.session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author goov5550
 */
public class AbstractServlet extends HttpServlet {

	private static final long serialVersionUID = 3530201549192060332L;
    private static final Integer DEFAULT_ATTRIBUTE_NUMBER = 5;
    private static final Integer DEFAULT_ATTRIBUTE_LENGTH = 2000;
    private static final Integer DEFAULT_WRITE_NUMBER = 2;
    // in order to generate session payload
    private static final String LORENZO = "Veux-tu donc que je sois un spectre, " +
    "et qu'en frappant sur ce squelette il n'en sorte aucun son ? " +
    "Si je suis l'ombre de moi-meme, veux-tu donc que je rompe le seul " +
    "fil qui rattache aujourd'hui mon coeur a quelques fibres de mon coeur " +
    "d'autrefois ? Songes-tu que ce meurtre, c'est tout ce qui reste de " +
    "ma vertu ? Songes-tu que je glisse depuis deux ans sur un rocher " +
    "taille a pic, et que ce meurtre est le seul brin d'herbe ou j'aie " +
    "pu cramponner mes ongles ? Crois-tu donc que je n'aie plus d'orgueil, " +
    "parce que je n'ai plus de honte ? et veux-tu que je laisse mourir " +
    "en silence l'enigme de ma vie ?";
	
    private char[] getShuffledChar(String s) {
        char[] shuffledChar = null;
        char[] charArray = s.toCharArray();
        Character[] characterArray = new Character[charArray.length];
        for (int i = 0; i < charArray.length; i++)
            characterArray[i] = new Character(charArray[i]);
        List<Character> l = Arrays.asList(characterArray);
        Collections.shuffle(l);
        Character[] newCharacterArray = (Character[]) l.toArray();
        shuffledChar = new char[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            shuffledChar[i] = newCharacterArray[i].charValue();
        }
        return shuffledChar;
    }

    public String getDummyString(int length) {
        char[] shuffledChar = getShuffledChar(LORENZO);
        if (length == LORENZO.length())
            return new String(shuffledChar);
        else if (length < LORENZO.length()) {
            String s = new String(shuffledChar);
            return s.substring(0, length);
        } else {
            char[] arrayOfChar = new char[length];
            for (int i = 0; i < arrayOfChar.length; i++)
                arrayOfChar[i] = shuffledChar[i % LORENZO.length()];
            return new String(arrayOfChar);
        }
    }
    
    public String shuffledString(String s) {
        char[] shuffledChar = getShuffledChar(s);
        return new String(shuffledChar);
    }
    
    public void setSessionParameter(HttpServletRequest request, HttpSession session) {
		String param = null;
		// parameters in order to define session data
		param = request.getParameter("numberOfAttributes");
		if ((param != null) && (param.length() > 0)) {
			session.setAttribute("attribute.number", new Integer(param));
		} else {
			session.setAttribute("attribute.number", DEFAULT_ATTRIBUTE_NUMBER);
		}
    	param = request.getParameter("lengthOfAttributes");
		if ((param != null) && (param.length() > 0)) {
			session.setAttribute("attribute.length", new Integer(param));
		} else {
			session.setAttribute("attribute.length", DEFAULT_ATTRIBUTE_LENGTH);
		}
        param = request.getParameter("numberOfWrites");
        if (param != null && param.length() > 0)
            session.setAttribute("write.number", new Integer(param));
        else
            session.setAttribute("write.number", DEFAULT_WRITE_NUMBER);
    }
    
    /**
     * set payload of the session
     * @param session
    public int[] initSessionData(HttpSession session) {
     */
    public List<String> initSessionData(HttpSession session) {
    	String payload = null;
    	Integer attributeNb = (Integer)session.getAttribute("attribute.number");
    	Integer attribLength = (Integer)session.getAttribute("attribute.length");
    	//int[] payloadHashCodes = new int[attributeNb];
    	List<String> payloadHashCodes = new ArrayList<String>(attributeNb);
        StringBuilder sb = new StringBuilder().append(attributeNb * attribLength / 1000);
        session.setAttribute("data.size", sb.append("KB").toString());
        for (int i = 0; i < attributeNb; i++) {
        	payload = getDummyString(attribLength);
        	//payloadHashCodes[i] = payload.hashCode();
        	payloadHashCodes.add(new Integer(payload.hashCode()).toString());
            session.setAttribute(new StringBuilder("pl_").append(i).toString(), payload);
        }
        return payloadHashCodes;
    }

    /**
     * read & write operation on payload of the session
     * @param session
     */
    public List<String> updateSessionData(HttpSession session) {
    	String attributeId, payload = null;
    	Integer attributeNb = (Integer)session.getAttribute("attribute.number");
    	Integer writeNumber = (Integer)session.getAttribute("write.number");
    	//int[] payloadHashCodes = new int[attributeNb];
    	List<String> payloadHashCodes = new ArrayList<String>(attributeNb);
        for (int i = 0; i < attributeNb; i++) {
            // read all attributes
            attributeId = new StringBuffer("pl_").append(i).toString();
            payload = (String) session.getAttribute(attributeId);
            // some one are written as
            if (i < writeNumber) {
            	payload = shuffledString(payload);
                session.setAttribute(attributeId, payload);
            }
        	//payloadHashCodes[i] = payload.hashCode();
        	payloadHashCodes.add(new Integer(payload.hashCode()).toString());        	
        }
        return payloadHashCodes;
    }
    
    
}
