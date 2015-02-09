/**
 * Copyright (c) 2012, Regents of the University of California
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *   Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 **/

package com.gasstove.gs;

/**
 * Holds static methods to return JSON response object
 *
 * @author: mnjuhn
 */
public class Response {

	/**
	 * NOTE: This generates a response intended for a JSON parser. The new ui, Project Manager, uses
     * this method. The old UI, Scenario Editor, uses an xml parser on the response. Everything will
     * eventually use this method.
     *
     * Returns a standard JSON message for REST API requests.  All responses should be
	 * generated here.
	 *
	 * @param success boolean whether message was successful or not
	 * @param message String representation of success or error message of request
	 * @param resource String representation of REST resource
	 * @return JSON representing success, message and resource
	 */
	public static String JSONPMMessage(boolean success, String message, String resource) {

		// make sure to escape all quotes within message and resource strings
		if (message != null) {
			message = message.replace("\"", "\\\"");
		}

		// Construct JSON string of success, message and resource
		String returnJSON = "{\"success\":" + success + ", \"message\":" + "\"" + message + "\""
				+ ", \"resource\":" + resource + "}";

		// replace all newline characters for JSON since they prevent it from being parsed into an object
		returnJSON = returnJSON.replace("\n", "");

		return returnJSON;
	}

    /**
 	 * NOTE: Scenario Editor Method: We expect this to go away as we integrate the Project Manager into the workflow.
     * You will notice the JSON response is in a format intended for an XML parser on the client side.Returns a standard JSON message for REST API requests.  All responses should be
     * generated here. This is used by Scenario Editor
     *
     * @param success boolean whether message was successful or not
     * @param message String representation of success or error message of request
     * @param resource String representation of REST resource
     * @return JSON representing success, message and resource
     */
    public static String JSONMessage(boolean success, String message, String resource) {

        // make sure to escape all quotes within message and resource strings
        if (message != null) {
            message = message.replace("\\", "\\\\");
            message = message.replace("\"", "\\\"");
        }
        if (resource != null) {
            resource = resource.replace("\\", "\\\\");
            resource = resource.replace("\"", "\\\"");
        }

        // Construct JSON string of success, message and resource
        String returnJSON = "{\"success\":" + success + ", \"message\":" + "\"" + message + "\""
                + ", \"resource\":" + "\"" + resource + "\"" + "}";

        // replace all newline characters for JSON since they prevent it from being parsed into an object
        returnJSON = returnJSON.replace("\n", "");

        return returnJSON;
    }


}
