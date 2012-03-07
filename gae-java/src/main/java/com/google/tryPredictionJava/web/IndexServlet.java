/*
 * Copyright 2012 Google Inc.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Author: Marc Cohen
 *
 */

package com.google.tryPredictionJava.web;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.api.client.json.JsonParser;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAuthorizationRequestUrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexServlet extends HttpServlet {

  /* Fill in your client ID and redirect URI here. */
  //private static String clientId = "380979171725.apps.googleusercontent.com";
  private static String redirectUri = 
    "http://try-prediction-java.appspot.com/auth_return";
  private static String scope =
    "https://www.googleapis.com/auth/devstorage.read_write " +
    "https://www.googleapis.com/auth/prediction";

  @SuppressWarnings("unused")
  private static final Logger log = 
    LoggerFactory.getLogger(IndexServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, 
                       HttpServletResponse response) throws 
                         ServletException, IOException {
    log.info("doGet for IndexServlet");

    Entity credentials = null;
    try {
      // Get user's email address.
      UserService userService = UserServiceFactory.getUserService();
      User user = userService.getCurrentUser();
      String user_email = "";
      if (user != null) {
        user_email = user.getEmail();
      }
      request.setAttribute("user_email", user_email);

      /* Get saved server credentials from app engine datastore. */
      DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
      Key creds_key = KeyFactory.createKey("Credentials", "Credentials");
      credentials = ds.get(creds_key);
    } catch (EntityNotFoundException ex) {
      // If server credentials don't exist, setup OAuth 2.0 request and
      // redirect session to Google authorization page.

      // Parse client secrets file json file.
      FileInputStream in = new FileInputStream("rc/client_secrets.json");
      JacksonFactory factory = new JacksonFactory();
      JsonParser parser = factory.createJsonParser(in);
      Map<String, Object> container = new HashMap<String, Object>();
      parser.parse(container, null);
      Map<String, String> secrets = 
        (Map<String, String>) container.get("installed");
      String clientId = secrets.get("client_id");

      GoogleAuthorizationRequestUrl requestUrl = 
        new GoogleAuthorizationRequestUrl(clientId, redirectUri, scope);
      requestUrl.setAccessType("offline");
      requestUrl.setApprovalPrompt("force");
      response.sendRedirect(requestUrl.build());
    }

    // Parse model descriptions from models.json file.
    FileInputStream in = new FileInputStream("rc/models.json");
    JacksonFactory factory = new JacksonFactory();
    JsonParser parser = factory.createJsonParser(in);
    Map<String, Object> models = new HashMap<String, Object>();
    parser.parse(models, null);
    request.setAttribute("models", models);

    // Set selected model name, if specified by user.
    String selected_model_name = request.getParameter("model");
    request.setAttribute("selected_model_name", selected_model_name);

    // Render jsp page with template input (models and selected model).
    forward(request, response, "index.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest request, 
                       HttpServletResponse response) throws 
                         ServletException, IOException {
    // Post logic is same as get (posts are used to switch selected model.
    log.info("doPost for IndexServlet");
    doGet(request, response);
  }

  /*
   * Forwards request and response to given path. Handles any exceptions
   * caused by forward target by printing them to logger.
   * 
   * @param request 
   * @param response
   * @param path 
   */
  protected void forward(HttpServletRequest request,
			 HttpServletResponse response, String path) {
    try {
      RequestDispatcher rd = request.getRequestDispatcher(path);
      rd.forward(request, response);
    } catch (Throwable tr) {
      if (log.isErrorEnabled()) {
        log.error("Caught Exception: " + tr.getMessage());
        log.debug("StackTrace:", tr);
      }
    }
  }
}
