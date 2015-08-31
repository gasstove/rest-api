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

package com.gasstove.gs.util;

import com.google.gson.Gson;

public class Response {

    public boolean success;
    public String message;
    public String resource;

    public Response(boolean success, String message, String resource) {
        this.success = success;
        this.message = message;
        this.resource = resource;
    }

    public Response(String json) {
        Gson gson = new Gson();
        Response r = gson.fromJson(json, Response.class);
        this.success = r.success;
        this.message = r.message;
        this.resource = r.resource;
    }

//	public String toJSONP(boolean success, String message, String resource) {
//
//		// make sure to escape all quotes within message and resource strings
//		if (message != null) {
//			message = message.replace("\"", "\\\"");
//		}
//
//		// Construct JSON string of success, message and resource
//		String returnJSON = "{\"success\":" + success + ", \"message\":" + "\"" + message + "\""
//				+ ", \"resource\":" + resource + "}";
//
//		// replace all newline characters for JSON since they prevent it from being parsed into an object
//		returnJSON = returnJSON.replace("\n", "");
//
//		return returnJSON;
//	}
//
//    public String toJSON() {
//
////        // make sure to escape all quotes within message and resource strings
////        if (message != null) {
////            message = message.replace("\\", "\\\\");
////            message = message.replace("\"", "\\\"");
////        }
////        if (resource != null) {
////            resource = resource.replace("\\", "\\\\");
////            resource = resource.replace("\"", "\\\"");
////        }
////
////        // Construct JSON string of success, message and resource
////        String returnJSON = "{\"success\":" + success + ", \"message\":" + "\"" + message + "\""
////                + ", \"resource\":" + "\"" + resource + "\"" + "}";
////
////        // replace all newline characters for JSON since they prevent it from being parsed into an object
////        returnJSON = returnJSON.replace("\n", "");
////
////        return returnJSON;
//
//        return (new Gson()).toJson(this,Response.class);
//    }

    public String format(Configuration.FORMAT format) {
        switch (format) {
            case json:
                return (new Gson()).toJson(this, Response.class);
            case jsonp:
                return Util.json2jsonp( (new Gson()).toJson(this, Response.class) );
        }
        return null;
    }

}