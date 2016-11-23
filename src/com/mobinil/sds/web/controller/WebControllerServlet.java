package com.mobinil.sds.web.controller;

/**
 * Main Servlet object - Application Web Controller
 * For example:
 * <pre>
 *      WebControllerServlet objWebController = new WebControllerServlet();
 *      objWebController.doPost(objRequest,objResponse);
 * </pre>
 *
 * @version	1.01 Feb 2004
 * @author  Ahmed Mohamed Abd Elmonem
 * @see
 *
 * SDS
 * MobiNil
 */
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.sa.*;
import com.mobinil.sds.web.interfaces.gn.ua.*;
import com.mobinil.sds.core.system.gn.user.dao.UserAccountDAO;
import com.mobinil.sds.web.interfaces.cam.MemoInterfaceKey;


public class WebControllerServlet extends HttpServlet {

    private static final String CONTENT_TYPE = "text/html; charset=windows-1256";
    private static final String HTTP_STR = "http://";

    /**
     * Init method of the servlet. Call the super function
     * 
     * @param request
     *            HttpServletRequest,response HttpServletResponse
     * @return
     * @throws ServletException
     *             , IOException if the servlet failed
     * @see
     */
    public void init(ServletConfig config) throws ServletException {
        try {
            super.init(config);
        } catch (Exception e) {
            Utility.logger.debug(e.getMessage());
        }
    }

    /**
     * handle a "GET Action"
     * 
     * @param request
     *            HttpServletRequest,response HttpServletResponse
     * @return
     * @throws ServletException
     *             , IOException if the servlet failed
     * @see
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        performTask(request, response);
    }

    /**
     * handle a "POST Action"
     * 
     * @param request
     *            HttpServletRequest,response HttpServletResponse
     * @return
     * @throws ServletException
     *             , IOException if the servlet failed
     * @see
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        performTask(request, response);
    }
    private static final Vector<String> basicActions = new Vector<String>();

    // initializing the basci action vector
    static {
        basicActions.add(UserAccountInterfaceKey.ACTION_LOGIN);
        basicActions.add(UserAccountInterfaceKey.ACTION_FORGOT_PASSWORD);
        basicActions.add(UserAccountInterfaceKey.ACTION_RESEND_PASSWORD);
        basicActions.add(UserAccountInterfaceKey.ACTION_SHOW_LOGIN_PAGE);
        basicActions.add(UserAccountInterfaceKey.ACTION_GET_DCM_REQUEST);
        basicActions.add(UserAccountInterfaceKey.ACTION_GO_TO_RENEW_PASSWORD);
        basicActions.add(UserAccountInterfaceKey.ACTION_NEW_PASSWORD);
        basicActions.add(UserAccountInterfaceKey.ACTION_GO_TO_ACTIVATION);
        basicActions.add(UserAccountInterfaceKey.ACTION_CONFIRMATION);
    }

    /**
     * Perform the task required from the request. Implement the "GET Action" or
     * the "POST Action"
     * 
     * @param request
     *            HttpServletRequest,response HttpServletResponse
     * @return
     * @throws ServletException
     *             , IOException if the servlet failed
     * @see
     * 
     */
    private static void validateSecurity(String strAction, String userID,
            HttpSession currentSession, Connection con) {

        try {

            String privilegeID = UserAccountDAO.getPrivilegeID(con, strAction);

            if (privilegeID != null) {
                userID = (String) currentSession.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
                String strRoleID = (String) currentSession.getAttribute(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID);
                if (userID == null
                        || strRoleID == null
                        || !UserAccountDAO.hasPrivilegeAccess(con, userID, strRoleID, privilegeID)) {
                    strAction = UserAccountInterfaceKey.ACTION_REDIRECT_TO_LOGIN_PAGE;
                    System.out.println("The Action name is " + strAction);

                    clearSession(currentSession);
                }
            }
        } catch (Exception e) {
        }

    }

    private static void clearSession(HttpSession currentSession) {
        Enumeration sessionAttributes = currentSession.getAttributeNames();
        while (sessionAttributes.hasMoreElements()) {
            currentSession.removeAttribute((String) sessionAttributes.nextElement());
        }

        /*
         * try{ currentSession.invalidate(); } catch(Exception e) {
         * e.printStackTrace(); }
         */
    }

    private void performTask(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // ///////////////////////////////////////////////////////////////////////////////////////
        /*
         * DECLARATIONS
         */// //////////////////////////////////////////////////////////////////////////////////////
        response.setContentType("text/html; charset=Windows-1256");
        request.setCharacterEncoding("Windows-1256");

        // HttpSession objSession = null;

        String strErrorPage = "";
        String actionFlag = "";
        String strAction = "";
        // String strMessage = "";
        String strParameterName = "";
        String strHandlerName = "";
        String strURL = "";
        // String strButtonText = "";

        RequestDispatcher objRequestDispatcher = null;
        HashMap objParameterHashMap = new HashMap(100);
        ActionMapperModel objActionMapperModel;
//        ParsingXML objParser;
        PrintWriter objOut = null;
        Enumeration objParameterNamesEnum;

        HttpSession currentSession = request.getSession();
        currentSession.setAttribute("aa", "11");
        currentSession.putValue("bb", "22");
        /*
         * A vector that contains all the basic actions that any one can do
         * without the need to check if he has a user id in the current session
         * or not.
         */

        /*
         * Use only one
         */
        HashMap objDataHashMap;
        // MainDTO objMainDTO;
        Connection newConnection = null;
        try {
            long starttime = System.currentTimeMillis();

            objOut = response.getWriter();
            objOut.println("<html>");
            objOut.println("<head><title>Sales Distrubtion System Servlet</title></head>");
            objOut.println("<body>");
            objOut.println("<p>The servlet has received a GET. This is the reply.</p>");
            System.out.println("lamya inside webcontrollerservlet before request get parameter names");
            objParameterNamesEnum = request.getParameterNames();
            newConnection = Utility.getConnection();

            String userID = null;
            while (objParameterNamesEnum.hasMoreElements()) {
                strParameterName = "";
                strParameterName = (String) objParameterNamesEnum.nextElement();
                // strMessage = request.getParameter(strParameterName);

                if ((strParameterName.equalsIgnoreCase(InterfaceKey.HASHMAP_KEY_ACTION))
                        && (!strParameterName.equalsIgnoreCase(""))) {
                    System.out.println("strParameterName : "+strParameterName);
                    strAction = request.getParameter(strParameterName);
                    System.out.println("The Action name is " + strAction);

                    /*
                     * Check if the action is logout remove session attributes.
                     */
                    // commented until deployment
                    if (strAction.equalsIgnoreCase(UserAccountInterfaceKey.ACTION_LOGOUT)) {
                        clearSession(currentSession);
                    }
                    /*
                     * Check if the action is a basic action first. If the
                     * action is not one of the basic actions that means that
                     * the user sending this action must have a user id in the
                     * current session. If the user id in the current session is
                     * null the user is redirected to the login page. If user id
                     * is not null then we check if this action is a privlege.
                     * If action is not a privlege the web controller conineue
                     * normally. If action is a privlege the web controller gets
                     * the privilege id, the user id and the current role id
                     * from the session. Second check for the user access rights
                     * of this privilege. If has access the web controller
                     * continue normally. If user has no access the web
                     * controller overide this action by another action.
                     */
                    // commented until deployment

                    if (!basicActions.contains(strAction)) {
                        userID = (String) currentSession.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
                        System.out.println("WEB CONTROLLER USER ID FROM PARAM "+objParameterHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID)+" AND USER ID FROM SESSION "+userID);
                        if (userID == null /*&& objParameterHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID)==null*/) {
                            userID = (String)objParameterHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                            System.out.println("inside if userID==null .. userID is "+userID);
                            strAction = UserAccountInterfaceKey.ACTION_REDIRECT_TO_LOGIN_PAGE;
                            System.out.println("The Action name is "
                                    + strAction);

                            clearSession(currentSession);
                        } else {
                            validateSecurity(strAction, userID, currentSession, newConnection);
                        }
                    }
                } else {

                    /*
                     * Check if the parameter contains array of values or only
                     * one value
                     */

                    if ((request.getParameterValues(strParameterName) != null)
                            && (request.getParameterValues(strParameterName).length > 1)) {
                        System.out.println("url paramter name is " + strParameterName);
                        String[] values = request.getParameterValues(strParameterName);
                        for (int i = 0; i < values.length; i++) {
                            System.out.println("value :" +values[i]);
                            values[i] = values[i].toString().replaceAll("\\s+", " ").trim();
                        }

                        objParameterHashMap.put(strParameterName, values);
                    } else {
                        String value = request.getParameter(strParameterName);


                        if (MemoInterfaceKey.MEMO_COMMENT.compareTo(strParameterName) != 0) {
                            value = value.replaceAll("\\s+", " ").trim();
                        } else {
                            System.out.println(value);
                        }
                        System.out.println("value is :"+value);
                        objParameterHashMap.put(strParameterName, value);
                        // Utility.logger.debug(" Servlet22 "
                        // +request.getParameter(strParameterName));
                    }
                }
            }
//objActionMapperModel = ActionMapper.getActionModel(strAction.toLowerCase()); Mahmoud
            objActionMapperModel = ActionMapper.getActionModel(strAction);

            /*
             * Get the handler name
             */

            if (objActionMapperModel == null) {
                System.out.println("$$$$$$$$$$$$$$$");
                System.out.println("Action is null action name is " + strAction);

            }

            actionFlag = objActionMapperModel.getAction_Flag();
            if (actionFlag != null && actionFlag.compareTo("0") == 0) {
            strHandlerName = objActionMapperModel.getHandler();

            objParameterHashMap.put("USER_ID", userID);

            /*
             * Get the redirect url
             */
            strURL = objActionMapperModel.getURL();
            System.out.println("lamya inside webcontrollerservlet ");
            System.out.println("The Handler name is " + strHandlerName
                    + "  The URL name is " + strURL + "   User id =" + userID);

            /*
             * Get the error page
             */
            strErrorPage = objActionMapperModel.getError_URL();
            strErrorPage = "error_page.jsp";

            /*
             * Get the Button text
             */
            // hagry: i commented the this line cause we never use the button
            // text value in the action mapper

            // strButtonText = objActionMapperModel.getButtonText();

            /* Mahmoud
             * Adding request to objParameterHashMap
             */
            
                objParameterHashMap.put(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET, request);
                com.mobinil.sds.core.controller.ApplicationController appController = new com.mobinil.sds.core.controller.ApplicationController();
                // passing the event to the appropriate handler
                objDataHashMap = appController.handleEvent(strAction, strHandlerName, objParameterHashMap, newConnection);
                long endtime1 = System.currentTimeMillis();
                long duration_ms1 = endtime1 - starttime;
                float fDuration1 = Float.parseFloat("" + duration_ms1 + "");

                fDuration1 = fDuration1 / 1000;
                System.out.println("Action " + strAction + " from handler took= "
                        + fDuration1);

                /*
                 * Put data in the request object
                 */
                request.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, objDataHashMap);
            
            request.setAttribute(InterfaceKey.HASHMAP_KEY_URL, strURL);
            request.setAttribute(InterfaceKey.HASHMAP_KEY_ACTION, strAction);


            boolean actionType = !basicActions.contains(strAction);
            // System.out.println("action type value "+actionType);
                System.out.println("strurl :"+strURL);
                objRequestDispatcher = getServletContext().getRequestDispatcher("/"
                        + strURL);
            
            if (objRequestDispatcher != null) {
                try {
                    objRequestDispatcher.forward(request, response);
                } catch (Exception ee) {
                    ee.printStackTrace();
                } catch (Error e) {
                    e.printStackTrace();
                }
            }// end of if(objRequestDispatcher != null)

            long endtime = System.currentTimeMillis();
            long duration_ms = endtime - starttime;
            float fDuration = Float.parseFloat("" + duration_ms + "");

            fDuration = fDuration / 1000;
            System.out.println("Total " + strAction + " action took= "
                    + fDuration);

            if (actionType) {
                System.out.println("tracing 1");
                UserAccountDAO.userLog(newConnection, strAction, userID, request.getRemoteAddr(), strURL, fDuration
                        + "");
                System.out.println("Tracing 2");
            }
            }
            else
            {
            
            String ret= buildCompleteURLForSpring(request, objActionMapperModel.getURL());
            response.sendRedirect(ret);
            }

        } catch (Exception objExp) {
            objExp.printStackTrace();
            String userID = (String) currentSession.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
            request.setAttribute(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, objExp);
            request.setAttribute(InterfaceKey.HASHMAP_KEY_USER_ID, userID);
            objRequestDispatcher = getServletContext().getRequestDispatcher("/"
                    + strErrorPage);
            if (objRequestDispatcher != null) {
                objRequestDispatcher.forward(request, response);
            }// end of if(objRequestDispatcher != null)

            log("Error: " + objExp.toString());
            Utility.logger.debug("Error: " + objExp.toString());

        } finally {
            System.out.println("Tracing 3");
            if (newConnection != null) {
                // System.out.println("connection closed from the webcontroller");
                try {
                    Utility.closeConnection(newConnection);
                } catch (Exception e) {
                }
            }

        }
        objOut.println("</body></html>");
        objOut.close();
    }// end of performTask()
    private String buildCompleteURLForSpring(HttpServletRequest request,String strActionURL){
    StringBuilder completeURL  = new StringBuilder(HTTP_STR);    
    completeURL.append(request.getLocalAddr());
    completeURL.append(":");
    completeURL.append(request.getLocalPort());    
    completeURL.append(request.getContextPath());
    completeURL.append(strActionURL);
    if (request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID)!=null){
    completeURL.append("?");
    completeURL.append(InterfaceKey.HASHMAP_KEY_USER_ID);
    completeURL.append("=");
    completeURL.append(request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID));
    }
        System.out.println(completeURL.toString());    
    return completeURL.toString();
    }
}
