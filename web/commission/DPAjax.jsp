<%-- 
    Document   : DPAjax
    Created on : Oct 18, 2011, 12:47:15 PM
    Author     : mabdelaal
--%>

<%@page import="com.mobinil.sds.core.system.commission.dao.CommissionDAO"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.mobinil.sds.core.utilities.DBUtil"%>
<%@page import="com.mobinil.sds.core.utilities.Utility"%>
<%@ page import="java.sql.*" %> 
<%
    String cat_id = request.getParameter("cat_id").toString();
    String data = "";

    Connection conn = Utility.getConnection();    
    HashMap<String, String> dpHM = CommissionDAO.getDrivingPlanMap(conn, cat_id);
    for (String dpId : dpHM.keySet()) {
        data += "<option value='" + dpId + "'>" + dpHM.get(dpId) + "</option>";
    }
    System.out.println("data is "+data);
    out.println(data);
    DBUtil.close(conn);

%>
