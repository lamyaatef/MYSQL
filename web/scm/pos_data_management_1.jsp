<%-- 
    Document   : posdatamanagement
    Created on : Nov 1, 2010, 3:00:53 PM
    Author     : Salma
--%>

<%@page import="java.sql.Connection"%>
<%@page import="com.mobinil.sds.core.utilities.DBUtil"%>
<%@page import="com.mobinil.sds.core.system.request.dao.RequestDao"%>
<%@page contentType="text/html" pageEncoding="windows-1256"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ page  import ="com.mobinil.sds.core.utilities.Utility"
          import = "java.util.*"
          import = "java.io.*"
          import = "javax.servlet.*"
          import = "javax.servlet.http.*"
          import = "javax.servlet.jsp.*"
          import = "java.io.PrintWriter"
          import = "java.io.IOException"
          import = "com.mobinil.sds.web.interfaces.scm.*"
          import = "com.mobinil.sds.core.system.fn.addfunction.model.*"
          import = "com.mobinil.sds.core.system.fn.importdata.model.*"
          import = "com.mobinil.sds.web.interfaces.*"
          import = "com.mobinil.sds.web.interfaces.fn.*"
          import = "com.mobinil.sds.web.interfaces.gn.querybuilder.*"
          import = "com.mobinil.sds.web.interfaces.dcm.*"
          import="com.mobinil.sds.core.system.dcm.pos.dao.*"
          import="com.mobinil.sds.core.system.dcm.genericModel.*"
          import="com.mobinil.sds.core.system.dcm.genericModel.DAO.*"
          import="com.mobinil.sds.core.system.dcm.pos.model.*"
          import="com.mobinil.sds.core.system.dcm.region.model.*"
          import="com.mobinil.sds.core.system.request.model.*"
          import="com.mobinil.sds.web.interfaces.sa.*"
          %>

<%
    

String appName = request.getContextPath();

String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SCMInterfaceKey.ACTION_REGIONS; //action=
String formAction2 = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SCMInterfaceKey.ACTION_SEARCH_POS_DATA_MANAGEMENT;
System.out.println("form action 2 : "+formAction2);
%>


<%
    HashMap dataHashMap = new HashMap(100);
    //lamya
    boolean disabled = true;
    int returnedRegionID = -1;
    Vector <PlaceDataModel> children = new Vector();
    Connection con = Utility.getConnection();
    //lamya
    dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String strUserID = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    String alert = (String) dataHashMap.get(SCMInterfaceKey.REP_KIT_Alert);
    Vector regions = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_REGIONS);
    Vector userLevels = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_USER_LEVEL_TYPES);
    Vector IDTypeVector = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_ID_TYPE);
    Vector documentVec = (Vector) dataHashMap.get(SCMInterfaceKey.DOC_VECTOR);
    Vector<POSDetailModel> posDataVec = (Vector<POSDetailModel>) dataHashMap.get(SCMInterfaceKey.SIMILAR_POS_LIST);
    System.out.println("POS DATA VECTOR : "+posDataVec);
    String region = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_REGION);
    String governrate = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_GOVER);
    String area = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_AREA);
    String city = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CITY);
    String district = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT);
    String ownerIdType = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE);
    String managerIdType = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE);
    String proposedDoc = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC);
    String distinationPage = (String) dataHashMap.get(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER);
    String totalPageNumbers = (String) dataHashMap.get(SCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER);
    Vector channelVec = (Vector) dataHashMap.get(SCMInterfaceKey.CHANNEL_VECTOR);
    Vector levelVec = (Vector) dataHashMap.get(SCMInterfaceKey.LEVEL_VECTOR);
    Vector PaymentLevelVec = (Vector) dataHashMap.get(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR);
    String posName = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_NAME);
    String level = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL);
    String channel = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL);
    String payment = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL);
    String Slach = System.getProperty("file.separator");

    if (posName == null) {
        posName = "";
    }

    String posCode = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CODE);
    if (posCode == null) {
        posCode = "";
    }
    String entryDate = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENTRY_DATE);
    entryDate = entryDate == null ? "*" : entryDate;
    String englishAddress = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENGLISH_ADDRESS);
    englishAddress = englishAddress == null ? "" : englishAddress;
    String posPhone = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_POS_PHONE);
    posPhone = posPhone == null ? "" : posPhone;
    String docLocation = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_DOC_LOCATION);
    docLocation = docLocation == null ? "" : docLocation;
    String dcmStatusId = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_SELECT_POS_STATUS);
    dcmStatusId = dcmStatusId == null ? "-1" : dcmStatusId;
    String stkStatusId = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_SELECT_STK_STATUS);
    stkStatusId = stkStatusId == null ? "-1" : stkStatusId;
    String psymentStatusId = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_SELECT_PAYMENT_STATUS);
    psymentStatusId = psymentStatusId == null ? "-1" : psymentStatusId;

    HashMap<String, String> dcmStatus = (HashMap<String, String>) dataHashMap.get(SCMInterfaceKey.HASHMAP_GEN_DCM);
    HashMap<String, String> stkStatus = (HashMap<String, String>) dataHashMap.get(SCMInterfaceKey.HASHMAP_STK_STATUS);
    HashMap<String, String> paymentStatus = (HashMap<String, String>) dataHashMap.get(SCMInterfaceKey.HASHMAP_CAM_PAY_STATUS);
%>

<%!
    private void drawSelectCompo(JspWriter out, HashMap<String, String> hashMap, String selectName, String selectedKey) throws Exception {
        String isSelected = "selected";
        out.println("<select name=\"" + selectName + "\" id=\"" + selectName + "\">");
        out.println("<option " + (selectedKey.compareTo("-1") == 0 ? isSelected : "") + " value=\"-1\">--</option>");
        if (hashMap != null && !hashMap.isEmpty()) {
            for (String key : hashMap.keySet()) {
                out.println("<option " + (selectedKey.compareTo(key) == 0 ? isSelected : "") + " value=\"" + key + "\">" + hashMap.get(key) + "</option>");
            }
        }
        out.println("</select>    ");

    }

    private void drawSelectRegions(JspWriter out, Vector<PlaceDataModel> vec, String selectName, String selectedKey, int typeId/*, boolean isDisabled*/) throws Exception {

        String isSelected = "selected";
       /* if(isDisabled)
            out.println("<select name=\"" + selectName + "\" id=\"" + selectName + "\" disabled>");
        else*/
        out.println("<select name=\"" + selectName + "\" id=\"" + selectName + "\">");
        if(typeId==1)
        {
            out.println("<option " + (selectedKey == null || selectedKey.compareTo("") == 0 ? isSelected : "") + " value=\"\">--</option>"); 
            if (vec != null && !vec.isEmpty()) {
                for (PlaceDataModel placeDataModel : vec) {
                    if (placeDataModel.getTypeId() == typeId) {
                        out.println("<option value=" + placeDataModel.getRegionId() + (selectedKey.compareTo(placeDataModel.getRegionId() + "") == 0 ? " selected" : "") + ">" + placeDataModel.getRegionName() + "</option>");

                    }

                }


            }
        }
        
        out.println("</select>");
     
    }
    private void drawSelectSupervisors(JspWriter out, String selectName) throws Exception {

        //String isSelected = "selected";
       /* if(isDisabled)
            out.println("<select name=\"" + selectName + "\" id=\"" + selectName + "\" disabled>");
        else*/
        out.println("<select name=\"" + selectName + "\" id=\"" + selectName + "\">");
      /*  if(typeId==1)
        {
            out.println("<option " + (selectedKey == null || selectedKey.compareTo("") == 0 ? isSelected : "") + " value=\"\">--</option>"); 
            if (vec != null && !vec.isEmpty()) {
                for (PlaceDataModel placeDataModel : vec) {
                    if (placeDataModel.getTypeId() == typeId) {
                        out.println("<option value=" + placeDataModel.getRegionId() + (selectedKey.compareTo(placeDataModel.getRegionId() + "") == 0 ? " selected" : "") + ">" + placeDataModel.getRegionName() + "</option>");

                    }

                }


            }
        }*/
        
        out.println("</select>");
     
    }
%>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
        <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>


        <script>
            function drawCalender(argOrder,argValue)
            {
                document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(formPosMangement."+argOrder+",'mm-dd-yyyy','Choose date')\">");
                document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
            }
        </script>
        <%--
        <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
        --%>
        <script src="../resources/js/jquery-1.11.3.js"></script>
    </head>
    
    <body>

        <br><br>
        <center><h2>Pos Data Management</h2></center>
        <br><br>


        <center><h2>POS Search</h2></center>
        <br>
        <br>

        <form id="formPosMangement" name="formPosMangement" action="" method=post  >

            <input type="hidden"  name=<%=InterfaceKey.HASHMAP_KEY_ACTION%> id=<%=InterfaceKey.HASHMAP_KEY_ACTION%> >
            <input type=hidden name=<%=InterfaceKey.HASHMAP_KEY_USER_ID%> value="<%=strUserID%>" >
           
            <center>
                <table cellpadding="2" cellspacing="0" border="1" width="100%">
                    <tr class=TableTextNote>
                        <td >
                            Channel
                        </td>
                        <td align="left">
                            <select name="<%= SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL%>">
                                <option value="" >--</option>
                                <%
                                    for (int i = 0; i < channelVec.size(); i++) {
                                        ChannelModel channelModel = (ChannelModel) channelVec.get(i);
                                %>
                                <option value="<%=channelModel.getChannelId()%>" <%=(channel.compareTo(channelModel.getChannelId() + "") == 0 ? "selected" : "")%>><%=channelModel.getChannelName()%></option>
                                <%}%>
                            </select>
                        </td>

                        <td align="center" class=TableTextNote>
                            DCM Level
                        </td>
                        <td align="left">
                            <select name="<%= SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL%>">
                                <option value="" >--</option>
                                <%
                                    for (int i = 0; i < levelVec.size(); i++) {
                                        LevelModel levelModel = (LevelModel) levelVec.get(i);
                                %>
                                <option value="<%=levelModel.getLevelId()%>" <%=(level.compareTo(levelModel.getLevelId() + "") == 0) ? "selected" : ""%>><%=levelModel.getLevelName()%></option>
                                <%}%>
                            </select>
                        </td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>Payment Level</td>
                        <td align="left">
                            <select name="<%= SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL%>">
                                <option value="" >--</option>
                                <%
                                    for (int i = 0; i < PaymentLevelVec.size(); i++) {
                                        PaymentModel levelModel = (PaymentModel) PaymentLevelVec.get(i);
                                %>
                                <option value="<%=levelModel.getPaymentId()%>" <%=(payment.compareTo(levelModel.getPaymentId() + "") == 0 ? "selected" : "")%>><%=levelModel.getPaymentName()%></option>                       
                                <%}%>

                            </select>
                        </td>
                        <td>Payment Status</td>
                        <td align="left">
                            <%drawSelectCompo(out, paymentStatus, SCMInterfaceKey.CONTROL_SELECT_PAYMENT_STATUS, psymentStatusId);%>
                        </td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>POS Name</td>
                        <td align="left">
                            <input type="text" value="<%=posName%>" name="<%= SCMInterfaceKey.CONTROL_TEXT_POS_NAME%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_POS_NAME%>">
                        </td>
                        <td>Pos Code</td>
                        <td align="left">
                            <input type="text" value="<%=posCode%>" name="<%= SCMInterfaceKey.CONTROL_TEXT_POS_CODE%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_POS_CODE%>">
                        </td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>Region</td>
                        <td align="left">
                           
                            <% drawSelectRegions(out, (Vector<PlaceDataModel>) regions, SCMInterfaceKey.CONTROL_TEXT_POS_REGION, region, 1/*,!disabled*/);%>
                            
                           
                        </td>
                        
                        <td>Governorate</td>
                        <td align="left">
                            <%--
                            <select id='govern'>
                                
                            </select>
                            --%>
                           
                            <% drawSelectRegions(out, (Vector<PlaceDataModel>) regions, SCMInterfaceKey.CONTROL_TEXT_POS_GOVER, governrate, 2/*,disabled*/);%>
                           
                    </tr>
                    <tr class=TableTextNote>
                        <td>City</td>
                        <td align="left">
                            <% drawSelectRegions(out, (Vector<PlaceDataModel>) regions, SCMInterfaceKey.CONTROL_TEXT_POS_CITY, city, 3/*,disabled*/);%>
                            
                        </td>
                        <td>District</td>
                        <td align="left">
                            <% drawSelectRegions(out, (Vector<PlaceDataModel>) regions, SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT, district, 4/*,disabled*/);%>
                            
                        </td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>Area</td>
                        <td colspan="4" align="left">
                            <% drawSelectRegions(out, (Vector<PlaceDataModel>) regions, SCMInterfaceKey.CONTROL_TEXT_POS_AREA, area, 5/*,disabled*/);%>
                            
                        </td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>English Address</td>
                        <td align="left">
                            <input type="text" value="<%=englishAddress%>" name="<%= SCMInterfaceKey.CONTROL_TEXT_INPUT_ENGLISH_ADDRESS%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_INPUT_ENGLISH_ADDRESS%>">
                        </td>
                        <td>POS Phone</td>
                        <td align="left">
                            <input type="text" value="<%=posPhone%>" name="<%= SCMInterfaceKey.CONTROL_TEXT_INPUT_POS_PHONE%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_INPUT_POS_PHONE%>">
                        </td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>Document Location</td>
                        <td align="left">
                            <input type="text" value="<%=docLocation%>" name="<%= SCMInterfaceKey.CONTROL_TEXT_INPUT_DOC_LOCATION%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_INPUT_DOC_LOCATION%>">
                        </td>
                        <td>POS Status</td>
                        <td align="left">
                            <%drawSelectCompo(out, dcmStatus, SCMInterfaceKey.CONTROL_SELECT_POS_STATUS, dcmStatusId);%>
                        </td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>Owner Name</td>
                        <td align="left">
                            <input type="text" value="<%=(String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME)%>" name="<%= SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME%>">
                        </td>
                        <td>Supervisor Name</td>
                        <td align="left">
                            <% drawSelectSupervisors(out, SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR);%>
                            <input type=hidden id="<%=SCMInterfaceKey.INPUT_HIDDEN_USER_ID%>" name="<%=SCMInterfaceKey.INPUT_HIDDEN_USER_ID%>" value="" >
                        </td>
                        
                    </tr>

                    
                    
                    <tr class=TableTextNote>
                        <td>Team Leader Name</td>
                        <td align="left">
                            <% drawSelectSupervisors(out, SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER);%>
                            <input type=hidden id="<%=SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_ID%>" name="<%=SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_ID%>" value="" >
                        </td>
                        <td>Sales Rep Name</td>
                        <td align="left">
                            <% drawSelectSupervisors(out, SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP);%>
                            <input type=hidden id="<%=SCMInterfaceKey.INPUT_HIDDEN_SALESREP_ID%>" name="<%=SCMInterfaceKey.INPUT_HIDDEN_SALESREP_ID%>" value="" >
                        </td>
                        
                    </tr>
                    
                    
                    
                    
                    <tr class="TableTextNote">
                        <td >Owner I.D Type</td>
                        <td align="left">
                            <select name="<%= SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE%>">
                                <option value="" >--</option>
                                <%
                                    for (int i = 0; i < IDTypeVector.size(); i++) {
                                        GenericModel IDTypeModel = (GenericModel) IDTypeVector.get(i);
                                %>
                                <option value="<%=IDTypeModel.get_primary_key_value()%>" <%=(ownerIdType.compareTo(IDTypeModel.get_primary_key_value()) == 0 ? "selected" : "")%>><%= IDTypeModel.get_field_2_value()%></option>
                                <%}%>
                            </select>
                        </td>
                        <td>Owner I.D Number</td>
                        <td align="left">
                            <input type="text" value="<%=(String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER)%>" name="<%= SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER%>">
                        </td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>Manager Name</td>
                        <td colspan="3" align="left">
                            <input type="text" value="<%=(String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME)%>" name="<%= SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME%>">
                        </td>
                    </tr>
                    <tr class="TableTextNote">
                        <td>Manager I.D Type</td>
                        <td align="left">
                            <select name="<%= SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE%>">
                                <option value="" >--</option>
                                <%
                                    for (int i = 0; i < IDTypeVector.size(); i++) {
                                        GenericModel IDTypeModel = (GenericModel) IDTypeVector.get(i);
                                %>
                                <option value="<%=IDTypeModel.get_primary_key_value()%>" <%=(managerIdType.compareTo(IDTypeModel.get_primary_key_value()) == 0 ? "selected" : "")%>><%= IDTypeModel.get_field_2_value()%></option>
                                <%}%>
                            </select>
                        </td>
                        <td>Manager I.D Number</td>
                        <td align="left">
                            <input type="text" value="<%=(String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER)%>" name="<%= SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER%>">
                        </td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>Proposed Document</td>
                        <td align="left">
                            <select name="<%= SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC%>">
                                <option value="" >--</option>
                                <%
                                    for (int i = 0; i < documentVec.size(); i++) {
                                        ProposedDocument docModel = (ProposedDocument) documentVec.get(i);
                                %>
                                <option value="<%=docModel.getDocId()%>" <%=(proposedDoc.compareTo(docModel.getDocId() + "") == 0 ? "selected" : "")%> ><%=docModel.getDocName()%></option>
                                <%}%>
                            </select>
                        </td>
                        <td>
                            Document Number
                        </td>
                        <td align="left">
                            <input type="text" value="<%=(String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM)%>" name="<%= SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM%>">
                        </td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>STK Status</td>
                        <td align="left">
                            <%drawSelectCompo(out, stkStatus, SCMInterfaceKey.CONTROL_SELECT_STK_STATUS, stkStatusId);%>
                        </td>
                        <td>STK Dial Number</td>
                        <td align="left">
                            <input type="text" value="<%=(String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_DIAL)%>" name="<%= SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>" id="<%= SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>">
                        </td>
                    </tr>

                    <tr class=TableTextNote>
                        <td>Entry Date</td>
                        <td colspan="3" align="left">
                            <script>drawCalender('<%=SCMInterfaceKey.CONTROL_TEXT_INPUT_ENTRY_DATE%>',"<%=entryDate%>");</script>                    
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="<%=SCMInterfaceKey.BASE_DIRECTION%>" value="<%=request.getRealPath(Slach + "scm" + Slach + "upload" + Slach)%>">

                <input type="hidden" name="<%= SCMInterfaceKey.INPUT_HIDDEN_POS_ID%>" id="<%= SCMInterfaceKey.INPUT_HIDDEN_POS_ID%>">
                <br>
                <br>
         
                <input class=button  type="button"  value="Search" onclick="searchRequest()">
          
   <%-- 
            <input class=button  type="submit"  value="Search" id="mySearch">
--%>
                <input class=button  type="button"  value="Export to excel" onclick="buildActionStr()">
                <br>
                <br>
                <%
                    if (posDataVec.size() > 0) {
                %>
                <table class="sortable" style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="90%" border="1">
                    <tr >
                        <td class=TableHeader nowrap align=center>POS Name</td>
                        <td class=TableHeader nowrap align=center>POS Code</td>
                        <td class=TableHeader nowrap align=center>POS Address</td>
                        <td class=TableHeader nowrap align=center>Owner Name</td>
                        <td class=TableHeader nowrap align=center>Manager Name</td>
                        <td class=TableHeader nowrap align=center>Details</td>
                        <td class=TableHeader nowrap align=center>Edit</td>
                        <td class=TableHeader nowrap align=center>View History</td>
                        <td class=TableHeader nowrap align=center>Status</td>
                    </tr>
                    <%
                        for (int i = 0; i < posDataVec.size(); i++) {
                    %>
                    <tr>
                        <% if (posDataVec.get(i).getPosName() == null) {
                                posDataVec.get(i).setPosName("");
                            }%>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posDataVec.get(i).getPosName()%></td>
                        <% if (posDataVec.get(i).getPOSCode() == null) {
                                posDataVec.get(i).setPOSCode("");
                            }%>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posDataVec.get(i).getPOSCode()%></td>
                        <% if (posDataVec.get(i).getPosAddress() == null) {
                                posDataVec.get(i).setPosAddress("");
                            }%>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posDataVec.get(i).getPosAddress()%></td>
                        <% if (posDataVec.get(i).getPosOwnerName() == null) {
                                posDataVec.get(i).setPosOwnerName("");
                            }%>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posDataVec.get(i).getPosOwnerName()%></td>
                        <% if (posDataVec.get(i).getPosManagerName() == null) {
                                posDataVec.get(i).setPosManagerName("");
                            }%>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posDataVec.get(i).getPosManagerName()%></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><input class=button  type="button"  value="Details" onclick="detailRequest(<%=posDataVec.get(i).getPosID()%>)"></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"> 
                            <% if (posDataVec.get(i).getPosStatusName().contains("Stop")) {%>

                            <%--    <input class=button  type="button"  value="Edit" disabled readonly> --%>
                            <%} else {%>
                            <input class=button  type="button"  value="Edit" onclick="editRequest((<%=posDataVec.get(i).getPosID()%>))">
                            <%}%>
                        </td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"> <input class=button  type="button"  value="View History" onclick="viewHistory((<%=posDataVec.get(i).getPosID()%>))"></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posDataVec.get(i).getPosStatusName()%></td>

                    </tr>
                    <%
                        }
                    %>
                </table>
                <div align="center">
                    <jsp:include page="pagingTable.jsp"  flush="true" >
                        <jsp:param   name="action_name_when_click_enter" value="search_pos_data_management"/>
                        <jsp:param   name="first_page_number" value="0"/>
                        <jsp:param   name="string_of_total_page_number" value="<%=totalPageNumbers%>"/>
                        <jsp:param   name="control_text_page_number" value="<%=distinationPage%>"/>


                    </jsp:include>
                </div>
                <%
                    }
                %>

                <br>
                <center><font color=red style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=alert%></font></center>
                <br>
                <br>

                <table>
                    <tr>
                        <td>
                            <input class=button  type="button"  value="Create Data Entry " onclick="preRequestDataEntryWithoutStk();">
                        </td>
                        <td>
                            <input class=button  type="button"  value="Create Data Entry and STK " onclick="preRequestDataEntryWithStk()">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input class=button id="tempDown" id="tempDown" type="button"  value="Generate Excel Template " onclick="exportExcel()">
                        </td>

                    </tr>
                </table>



            </center>
         <script>
    
        function pulldownChanged(){
            
      
    }
        function DevChangePageActionWithSubmit(action)
        {

            document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value =  document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value =action
            document.formPosMangement.submit();
        }


        function Download()
        {

            document.getElementById("download").disabled=true;
            document.GenerateSheet.submit();

        }
    
    function Sheet()
    {
        document.GenerateSheet.Submit.disabled=true;
        document.GenerateSheet.submit();


    }
    function exportExcel()
    {
        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.IMPORT_TEMPLATE_DATES_FOR_POS%>';
        document.getElementById("tempDown").disabled=true;
        document.formPosMangement.submit();
    }
    function preRequestDataEntryWithoutStk()
    {
        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.ACTION_POS_DATA_ENTRY%>';
        document.formPosMangement.submit();
    }
    function preRequestDataEntryWithStk()
    {

        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.ACTION_POS_DATA_ENTRY_WITH_STK%>';
        document.formPosMangement.submit();
    }

    function detailRequest(id)
    {
        document.formPosMangement.<%=SCMInterfaceKey.INPUT_HIDDEN_POS_ID%>.value  = id;
        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.ACTION_SHOW_DETAIL_POS_DATA_MANAGEMENT%>';
        document.formPosMangement.submit();
    }


    function editRequest(id)
    {
        document.formPosMangement.<%=SCMInterfaceKey.INPUT_HIDDEN_POS_ID%>.value  = id;
        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=SCMInterfaceKey.ACTION_POS_DATA_EDIT%>';
        document.formPosMangement.<%=SCMInterfaceKey.INPUT_HIDDEN_USER_ID%>.value;
        document.formPosMangement.submit();
    }
    function searchRequest()
    {

        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.ACTION_SEARCH_POS_DATA_MANAGEMENT%>';
        document.formPosMangement.submit();
    }
    function buildActionStr (){
        
        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.SEARCH_POS_EXCEL%>';
        document.formPosMangement.submit();
        /*var path = window.location+'?';
        //var path = 'com.mobinil.sds.web.controller.WebControllerServlet?';


                path += '<%=InterfaceKey.HASHMAP_KEY_ACTION%>=<%=SCMInterfaceKey.SEARCH_POS_EXCEL%>';                
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_NAME%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_NAME%>.value;
                path += '&<%=SCMInterfaceKey.BASE_DIRECTION%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.BASE_DIRECTION%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL%>.value;

                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_INPUT_ENTRY_DATE%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_INPUT_ENTRY_DATE%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_INPUT_ENGLISH_ADDRESS%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_INPUT_ENGLISH_ADDRESS%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_INPUT_POS_PHONE%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_INPUT_POS_PHONE%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_TEXT_INPUT_DOC_LOCATION%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_INPUT_DOC_LOCATION%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_SELECT_POS_STATUS%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_SELECT_POS_STATUS%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_SELECT_STK_STATUS%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_SELECT_STK_STATUS%>.value;
                path += '&<%=SCMInterfaceKey.CONTROL_SELECT_PAYMENT_STATUS%>=';
                path += document.formPosMangement.<%=SCMInterfaceKey.CONTROL_SELECT_PAYMENT_STATUS%>.value;
               //alert (path) ;

               //document.formPosMangement.action="com.mobinil.sds.web.controller.WebControllerServlet?";
               //document.formPosMangement.action=document.formPosMangement.action+path;
               //document.formPosMangement.submit();



                 window.open(path,"AdminLogin","status=yes,toolbar=yes,menubar=yes,location=yes,directories=yes,resizable=yes,scrollbars=yes");

         */
    }

    function searchExcel()
    {
        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.SEARCH_POS_EXCEL%>';
        document.formPosMangement.submit();
    }
    function viewHistory(id)
    {
        document.formPosMangement.<%=SCMInterfaceKey.INPUT_HIDDEN_POS_ID%>.value  = id;
        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=SCMInterfaceKey.ACTION_POS_DATA_VIEW_HISTORY%>';
        document.formPosMangement.submit();
    }
    
    
    
    
    
    
   //region     
  $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>").change(function(){
  //console.log("aaaa ",$(this).val());
  var regionid= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>").val(); //value id of Option selected in the Select object
  //console.log("value id of option selected in Select object is : ",regionid);
    
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    datatype: "JSON",
    data : {regionid:regionid,type:"<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>"},
    success: function(data, textStatus, jqXHR)
    {
        
      
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>").empty();
      
        
      
    $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>").append($("<option/>").text("--"));

        $.each(data.map.districts, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
         //   console.log("data governorates ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>").append(option);
});

},
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

}); 


//governorate
$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>").change(function(){
  
  var governid= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>").val(); //value id of Option selected in the Select object
  //console.log("value id of option selected in Select object is : ",id);
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    data : {regionid:governid ,type:"<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>"/*,userLevel:4*/},
    success: function(data, textStatus, jqXHR)
    {
         $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>").empty();
        
              
    $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>").append($("<option/>").text("--"));
    
        $.each(data.map.districts, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
          // console.log("data governorates ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>").append(option);
});
        
        
       
        
        
        
    },
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

}); 







//city
$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>").change(function(){
  
  var cityid= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>").val(); //value id of Option selected in the Select object
  //console.log("value id of option selected in Select object is : ",id);
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    data : {regionid:cityid ,type:"<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>"/*,userLevel:4*/},
    success: function(data, textStatus, jqXHR)
    {
        
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>").empty();
        
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").append($("<option/>").text("--"));
    
        $.each(data.map.districts, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
           // console.log("data governorates ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").append(option);
});
        
      
      
        
    },
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

}); 
         
   //district
$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").change(function(){
  
  var districtid= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").val(); //value id of Option selected in the Select object
  var array=[3];
    array[0]= 4;
    array[1] = 5;
    array[2] = 6;
    var str = JSON.stringify(array);
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    datatype: "JSON",
    data : {regionid:districtid ,type:"<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>",arraySent:str},
    success: function(data, textStatus, jqXHR)
    {
        
      
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>").append($("<option/>").text("--"));
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%>").append($("<option/>").text("--"));
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").append($("<option/>").text("--"));
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").append($("<option/>").text("--"));
    
        $.each(data.map.districts, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
         //   console.log("data governorates ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>").append(option);
});
 
 $.each(data.map.users, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
         //   console.log("data supervisors ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%>").append(option);
});
 
 $.each(data.map.teams, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
        //    console.log("data teamleaders ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").append(option);
});

 $.each(data.map.sales, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
     //       console.log("data salesrep ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").append(option);
});



},
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

}); 




$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%>").change(function(){
   $("#<%= SCMInterfaceKey.INPUT_HIDDEN_USER_ID%>").val($("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%> option:selected").val());
  var managerid2= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%>").val(); //value id of Option selected in the Select object
  var regionid= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").val(); //value id of Option selected in the Select object
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    data : {managerid2:managerid2 ,regionid:regionid,type:"<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%>",userLevel:5},
    success: function(data, textStatus, jqXHR)
    {
        
      
        
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").empty();
     /*   $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").empty();*/
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").append($("<option/>").text("--"));
      /*  $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").append($("<option/>").text("--"));*/
    

$.each(data.map.superChildren, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
          //  console.log("data supervisors ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").append(option);
          
});




     
        
    },
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

});  






$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").change(function(){
   $("#<%= SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_ID%>").val($("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%> option:selected").val());
  var managerid2= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").val(); //value id of Option selected in the Select object
  var regionid= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").val(); //value id of Option selected in the Select object
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    data : {managerid2:managerid2 ,regionid:regionid,type:"<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>",userLevel:6},
    success: function(data, textStatus, jqXHR)
    {
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").append($("<option/>").text("--"));
    

$.each(data.map.superChildren, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
      //      console.log("data supervisors ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").append(option);
          
});
     
        
    },
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

});  

/*$(function () {
    $('input').on('click', function () {
        var Status = $(this).val();
        $.ajax({
            url: 'Ajax/StatusUpdate.php',
            data: {
                text: $("textarea[name=Status]").val(),
                Status: Status
            },
            dataType : 'json'
        });
    });
});*/
/*

$("#formPosMangement").submit(function(){
            var Status = $(this).val();
            data=$(this).serialize();
              $.ajax({
                  url : "<%out.print(formAction2);%>",
                  type: "POST",
                  data: data,
                  dataType : 'json',
                  success: function(data, textStatus, jqXHR)
          {
              console.log("success");
          },
          error: function (jqXHR, textStatus, errorThrown)
          {
               console.log("error");
          }
    
        });
            return false;

        });*/
        



</script>
        </form>
            
        <script>  </script> 
    
            
    </body>
</html>

