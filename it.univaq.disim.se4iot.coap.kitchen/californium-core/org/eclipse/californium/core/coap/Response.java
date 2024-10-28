/*******************************************************************************
 * Copyright (c) 2014 Institute for Pervasive Computing, ETH Zurich and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 * Contributors:
 *    Matthias Kovatsch - creator and main architect
 *    Martin Lanter - architect and re-implementation
 *    Dominique Im Obersteg - parsers and initial implementation
 *    Daniel Pauli - parsers and initial implementation
 *    Kai Hudalla - logging
 ******************************************************************************/
package org.eclipse.californium.core.coap;

import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.CoAP.Type;

/**
 * Response represents a CoAP response to a CoAP request. A response is either a
 * piggy-backed response with type ACK or a separate response with type CON or
 * NON. A response has a response code ({@link CoAP.ResponseCode}).
 * @see Request
 */
public class Response extends Message {

	/** The response code. */
	private final CoAP.ResponseCode code;
	
	private long rtt;

	private boolean last = true;
	
	/**
	 * Instantiates a new response with the specified response code.
	 *
	 * @param code the response code
	 */
	public Response(ResponseCode code) {
		this.code = code;
	}

	/**
	 * Gets the response code.
	 *
	 * @return the code
	 */
	public CoAP.ResponseCode getCode() {
		return code;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String payload = getPayloadString();
		if (payload == null) {
			payload = "no payload";
		} else {
			int len = payload.length();
			if (payload.indexOf("\n")!=-1) payload = payload.substring(0, payload.indexOf("\n"));
			if (payload.length() > 24) payload = payload.substring(0,20);
			payload = "\""+payload+"\"";
			if (payload.length() != len+2) payload += ".. " + payload.length() + " bytes";
		}
		return String.format("%s-%-6s MID=%5d, Token=%s, OptionSet=%s, %s", getType(), getCode(), getMID(), getTokenString(), getOptions(), payload);
	}
	
	/**
	 * Creates a piggy-backed response with the specified response code to the
	 * specified request. The destination address of the response is the source
	 * address of the request. The response has the same MID and token as the
	 * request.
	 * 
	 * @param request the request
	 * @param code the code
	 * @return the response
	 */
	public static Response createPiggybackedResponse(Request request, ResponseCode code) {
		Response response = new Response(code);
		response.setMID(request.getMID());
		response.setType(Type.ACK);
		response.setDestination(request.getSource());
		response.setDestinationPort(request.getSourcePort());
		response.setToken(request.getToken());
		return response;
	}
	
	/**
	 * Creates a separate response with the specified response code to the
	 * specified request. The destination address of the response is the source
	 * address of the request. The response has the same token as the request
	 * but needs another MID from the CoAP network stack.
	 *
	 * @param request the request
	 * @param code the code
	 * @return the response
	 */
	public static Response createSeparateResponse(Request request, ResponseCode code) {
		Response response = new Response(code);
		response.setDestination(request.getSource());
		response.setDestinationPort(request.getSourcePort());
		response.setToken(request.getToken());
		return response;
	}
	
	public boolean isLast() {
		return last;
	}

	/**
	 * Defines whether this response is the last response of an exchange. If
	 * this is only a block or a notification, the response might not be the
	 * last one.
	 * 
	 * @param last if this is the last response of an exchange
	 */
	public void setLast(boolean last) {
		this.last = last;
	}

	public long getRTT() {
		return rtt;
	}

	public void setRTT(long rtt) {
		this.rtt = rtt;
	}
}
