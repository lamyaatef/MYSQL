    <%-- 
    Document   : pos_detail_update
    Created on : Nov 9, 2010, 11:44:53 AM
    Author     : Salma
--%>



<%@ page  import ="com.mobinil.sds.core.utilities.Utility"
          
          import ="com.mobinil.sds.core.system.request.dao.*"
          import ="java.sql.Connection"
          
          
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
          %>

<script>
    function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(formDataView."+argOrder+",'dd-mm-yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }
</script>

<%
    String appName = request.getContextPath();
%>
<SCRIPT language=JavaScript>
    function checkbeforSubmit()
    {

        document.formDataView.submit();
    }
</SCRIPT>
<html>

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">

        <SCRIPT  src="../resources/js/calendar.js" type="text/javascript"></SCRIPT>
        <SCRIPT  src="../resources/js/deepgrid.js" type="text/javascript"></SCRIPT>
        <SCRIPT  src="../resources/js/yav/yav.js" type="text/javascript"></SCRIPT>
        <SCRIPT  src="../resources/js/yav/yav-config.js" type="text/javascript"></SCRIPT>
        <script src="../resources/js/jquery-1.11.3.js"></script>
    </head>

    <body >
        <%String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SCMInterfaceKey.ACTION_POS_DATA_EDIT_STORE;%>
        <%
            HashMap dataHashMap = new HashMap(100);
            dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            
            
            POSDetailModel posDetailModel = (POSDetailModel) dataHashMap.get(SCMInterfaceKey.POS_DETAIL_MODEL);
            PosModel posData = (PosModel) dataHashMap.get(SCMInterfaceKey.SIMILAR_POS_LIST);
            UserDataModel userData = (UserDataModel) dataHashMap.get(SCMInterfaceKey.SIMILAR_USER_LIST);
            UserDataModel teamleaderData = (UserDataModel) dataHashMap.get(SCMInterfaceKey.SIMILAR_TEAMLEADER_LIST);
            UserDataModel salesrepData = (UserDataModel) dataHashMap.get(SCMInterfaceKey.SIMILAR_SALESREP_LIST);
            System.out.println("USER DATA , teamleader data, salesrep data "+userData+"  "+teamleaderData+"  "+salesrepData);
            String userID = request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
            String posDetailId = request.getParameter(SCMInterfaceKey.INPUT_HIDDEN_POS_ID);
            String supervisorDetail = request.getParameter(SCMInterfaceKey.INPUT_HIDDEN_USER_ID);
            String teamleaderDetail = request.getParameter(SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_ID);
            String salesrepDetail = request.getParameter(SCMInterfaceKey.INPUT_HIDDEN_SALESREP_ID);
            System.out.println("possssssssssss detaaaaaaaaaaaaail : "+posDetailId);
            System.out.println("supervisor id JSP : "+supervisorDetail);
            System.out.println("teamleader id JSP : "+teamleaderDetail);
            System.out.println("salesrep id JSP : "+salesrepDetail);
            boolean exclusive = posData.isIsEX();
            boolean levelOne = posData.isIsL1();
            boolean mobicach = posData.isIsMobicash();
            boolean nomad = posData.isIsNomad();
            boolean qualityControl = posData.isIsQC();
            boolean sign = posData.isIsSignSet();
            boolean calidus = posData.isReportToCalidus();
            System.out.println("nooooooooomaaaaaaaaadddd : "+nomad);
           /* 
            
            
            System.out.println("pos data mobicash for this code for example mobicash detailed : "+posDetailModel.isIsMobicash());
            System.out.println("pos data mobicash for this code for example mobicash : "+posData.isIsMobicash());*/

            HashMap<String, RegionModel> regionsChilds = (HashMap<String, RegionModel>) dataHashMap.get(SCMInterfaceKey.CHILD_REGIONS_HM);

            String channel = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL);
            Vector channelVec = (Vector) dataHashMap.get(SCMInterfaceKey.CHANNEL_VECTOR);
            Vector levelVec = (Vector) dataHashMap.get(SCMInterfaceKey.LEVEL_VECTOR);
            
            
            Vector statusVec = (Vector) dataHashMap.get(SCMInterfaceKey.STATUS_VECTOR);
            
            String alert = (String) dataHashMap.get(SCMInterfaceKey.REP_KIT_Alert);
            Vector regions = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_REGIONS);
            Vector IDTypeVector = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_ID_TYPE);

            String level = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL);
            
            String status = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_STATUS);
            
            String Payment = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL);
            System.out.println("inside jsp before getting payment method");
            String PaymentMethod = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD);
            System.out.println("payment method : "+PaymentMethod);
            Vector documentVec = (Vector) dataHashMap.get(SCMInterfaceKey.DOC_VECTOR);
            Vector rateVec = (Vector) dataHashMap.get(SCMInterfaceKey.RATE_VECTOR);
            Vector PaymentLevelVec = (Vector) dataHashMap.get(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR);
            Vector PaymentMethodVec = (Vector) dataHashMap.get(SCMInterfaceKey.PAYMENT_METHOD_VECTOR);        
            String disabledStrCity = "disabled=disabled";
            String cityIdVal = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CITY);
            disabledStrCity = cityIdVal != null && cityIdVal.compareTo("0") != 0 ? "" : disabledStrCity;
            String disabledStrArea = "disabled=disabled";
            String areaIdVal = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_AREA);
            
            ////////////////////////////////////////
            String entryDate = (String) dataHashMap.get(SCMInterfaceKey.SURVEY_DATE);
            System.out.println("pos_detail_update.jsp entry date "+entryDate);
            ////////////////////////////////////////////////
            
            
            disabledStrArea = areaIdVal != null && areaIdVal.compareTo("0") != 0 ? "" : disabledStrArea;
            Boolean isDist = false, isPos = false;
            Object parentIdObj = dataHashMap.get(SCMInterfaceKey.CONTROL_REGION_PARENT_ID);
            String parentIdstr = parentIdObj == null ? "" : (String) parentIdObj;

            String formstkAction = appName + "/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    + InterfaceKey.HASHMAP_KEY_ACTION + "="
                    + SCMInterfaceKey.ACTION_CHANGE_STK_NUMBER_PAGE + "&" + InterfaceKey.HASHMAP_KEY_USER_ID + "="
                    + userID;




            out.println("<CENTER>");
            out.println("  <br><br><h2>POS Data Update</h2><br><br>");
            out.println("</CENTER>");


            //out.println("<input type=hidden name='"+ InterfaceKey.HASHMAP_KEY_ACTION + "  id='"+ InterfaceKey.HASHMAP_KEY_ACTION +"' value="+ SCMInterfaceKey.ACTION_POS_DATA_ENTRY_STORE+"/>");


            out.println("<form id='formDataView' onsubmit=\"mySubmit.disabled = true;\" name='formDataView' action='' method='post'>");
        %>
        <input type="hidden" name="<%= SCMInterfaceKey.CONTROL_REGION_PARENT_ID%>" id="<%= SCMInterfaceKey.CONTROL_REGION_PARENT_ID%>" value="<%=parentIdstr%>" />
        <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" >
        <input type="hidden" id="<%=SCMInterfaceKey.INPUT_HIDDEN_POS_ID%>" name="<%=SCMInterfaceKey.INPUT_HIDDEN_POS_ID%>" value="<%=posDetailId%>" >
        <input type="hidden" id="<%=SCMInterfaceKey.INPUT_HIDDEN_USER_ID%>" name="<%=SCMInterfaceKey.INPUT_HIDDEN_USER_ID%>" value="<%=supervisorDetail%>" >
        <input type="hidden" id="<%=SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_ID%>" name="<%=SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_ID%>" value="<%=teamleaderDetail%>" >
        <input type="hidden" id="<%=SCMInterfaceKey.INPUT_HIDDEN_SALESREP_ID%>" name="<%=SCMInterfaceKey.INPUT_HIDDEN_SALESREP_ID%>" value="<%=salesrepDetail%>" >
        
        

        <%if (posData.getStkDialNumber() != null) {%>
        <input type="hidden" id="<%=SCMInterfaceKey.INPUT_HIDDEN_STK_NUMBER%>" name ="<%=SCMInterfaceKey.INPUT_HIDDEN_STK_NUMBER%>" value="<%= posData.getStkDialNumber() == null ? "" : posData.getStkDialNumber()%>" >
        <%}%>
        <input type="hidden" id="doc_number" name="doc_number" value="<%= posData.getDocNumber()%>" >
        <input type="hidden" id="doc_id" name="doc_id" value="<%= posData.getProposedDocId()%>" >
        <input type="hidden" id="verify_stk" name="verify_stk" value="<%= posData.getStkVerify()%>" >
        <input type="hidden" id="manager_id" name="manager_id" value="<%=  posData.getPosDetailModel().getPosManagerIDNumber()%>" >
        <input type="hidden" id="owner_id" name="owner_id" value="<%=  posData.getPosDetailModel().getPosOwnerIDNumber()%>" >



        <%
            int v = 0;
            GenericModel IDTypeModel;
            RegionModel regionModel;




            out.println("<input type='hidden' name='" + InterfaceKey.HASHMAP_KEY_USER_ID + "' value='" + userID + "'>");

            out.println("<TABLE align=center style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"80%\" border=1>");
            out.println("<TR>");
            out.println("<TD colspan=2 class=TableTextNote>POS Data ");

            out.println("</TD>");
            out.println("</tr>");
            out.println("<TR class=TableTextNote>");
            out.println("      <TD colspan=2>");
            out.println("      <table border=1 align='center' width='100%'>");
            out.println("<TR class=TableTextNote>");


            out.println("<TD class=TableTextNote width='40%'>POS Name</TD>");
            if (posData.getPosDetailModel().getPosName() != null) {
                out.println("<TD><input type='text' value='" + posData.getPosDetailModel().getPosName() + "' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_NAME + "'>");
            } else {
                out.println("<TD><input type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_NAME + "'>");
            }
            out.println("&nbsp;&nbsp;");
            out.println("<input class='button' type='button' value=Similars  onclick=similarName(); />");
            out.println("</TD>");
            out.println("</tr>");
            
            
            
            
            
             out.println("<TR>");
            out.println("      <TD class=TableTextNote width='40%'>POS Code</TD>");
            out.println("      <TD><input type='text' disabled value='" + posData.getPosDetailModel().getPOSCode() + "' name='" + SCMInterfaceKey.TEXT_POSCODE + "'></TD>");
            out.println("      <TD><input type='hidden' value='" + posData.getPosDetailModel().getPOSCode() + "' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_CODE + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_CODE + "'></TD>");
            out.println("</tr>");

            
            
            

            out.println("<TD class=TableTextNote width='40%'>POS Arabic Name</TD>");
            if (posData.getPosDetailModel().getPosArabicName() != null) {
                out.println("<TD><input type='text' value='" + posData.getPosDetailModel().getPosArabicName() + "' name='" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_NAME + "'>");
            } else {
                out.println("<TD><input type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_NAME + "'>");
            }

            out.println("</TD>");
            out.println("</tr>");
            out.println("</tr>");

            
            
            
            
            out.println("<TR>");
            out.println("      <TD class=TableTextNote width='40%'>POS Status</TD>");%>
    <td>
        <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_TEXT_POS_STATUS%>" value="<%=status%>" >
        <select name="<%= SCMInterfaceKey.STATUS_FOR_POS%>" id="<%= SCMInterfaceKey.STATUS_FOR_POS%>"  >
            <option value="" >--</option>
            <%
                for (int i = 0; i < statusVec.size(); i++) {
                    StatusModel statusModel = (StatusModel) statusVec.get(i);
                    if (status != null && status.compareTo(statusModel.getStatusId()+ "") == 0) {
                       /* if (statusModel.getStatusName().equalsIgnoreCase("pos")) {
                            isPos = true;
                        }*/

            %>
            <option value="<%=statusModel.getStatusId()%>" selected><%=statusModel.getStatusName()%></option>
            <%
            } else {
            %>
            <option value="<%=statusModel.getStatusId()%>" ><%=statusModel.getStatusName()%></option>
            <%
                    }
                }
            %>
        </select>
    </td>
    <%
        //  out.println("<TD><input type='text' name="+SCMInterfaceKey.TEXT_POSLEVEL+"  value=\""+level+"\" disabled>");
        //  out.println("<TD><input type='hidden' name="+SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL+" id="+SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL+" value=\""+level+"\">");
        //  out.println("</TD>");
        out.println("</TR>");
            
            
            


            out.println("<TR class=TableTextNote>");
            out.println("      <TD colspan=2>");
            out.println("      <table border=0 align='center' width='100%'>");
            out.println("<TR class=TableTextNote>");


            out.println("      <TD class=TableTextNote width='40%'>Channel</TD>");%>
    <td>
        <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL%>" value="<%=channel%>" >
        <select  name="<%= SCMInterfaceKey.TEXT_POSCHANNEL%>" id="<%= SCMInterfaceKey.TEXT_POSCHANNEL%>">
            <option value="" >--</option>
            <%
                String channelNameTemp = "";
                for (int i = 0; i < channelVec.size(); i++) {
                    ChannelModel channelModel = (ChannelModel) channelVec.get(i);
                    channelNameTemp = channelModel.getChannelName();
                    channelNameTemp = channelNameTemp.contains(" ") ? channelNameTemp.replaceAll(" ", "") : channelNameTemp;
                    if (channel !=null && channel.compareTo(channelModel.getChannelId() + "") == 0) {
                        if (channelNameTemp.equalsIgnoreCase("Distribution")) {
                            isDist = true;
                        }
            %>
            <option value="<%=channelModel.getChannelId()%>" selected><%=channelNameTemp%></option>
            <%
            } else {
            %>
            <option value="<%=channelModel.getChannelId()%>" ><%=channelNameTemp%></option>
            <%
                    }
                }
            %>
        </select>

        <%
            out.println("</td>");
            out.println("</tr>");


            out.println("<TR>");
            out.println("      <TD class=TableTextNote width='40%'>Level</TD>");%>
    <td>
        <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL%>" value="<%=level%>" >
        <select name="<%= SCMInterfaceKey.LEVEL_FOR_POS%>" id="<%= SCMInterfaceKey.LEVEL_FOR_POS%>"  >
            <option value="" >--</option>
            <%
                for (int i = 0; i < levelVec.size(); i++) {
                    LevelModel levelModel = (LevelModel) levelVec.get(i);
                    if (level != null && level.compareTo(levelModel.getLevelId() + "") == 0) {
                        if (levelModel.getLevelName().equalsIgnoreCase("pos")) {
                            isPos = true;
                        }

            %>
            <option value="<%=levelModel.getLevelId()%>" selected><%=levelModel.getLevelName()%></option>
            <%
            } else {
            %>
            <option value="<%=levelModel.getLevelId()%>" ><%=levelModel.getLevelName()%></option>
            <%
                    }
                }
            %>
        </select>
    </td>
    <%
        //  out.println("<TD><input type='text' name="+SCMInterfaceKey.TEXT_POSLEVEL+"  value=\""+level+"\" disabled>");
        //  out.println("<TD><input type='hidden' name="+SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL+" id="+SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL+" value=\""+level+"\">");
        //  out.println("</TD>");
        out.println("</TR>");
        out.println("<TR>");
        out.println("      <TD class=TableTextNote width='40%'>Payment Level</TD>");
        out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL + "' id='" + SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL + "'>");
        for (int i = 0; i < PaymentLevelVec.size(); i++) {
            PaymentModel levelModel = (PaymentModel) PaymentLevelVec.get(i);
            if (Payment != null && Payment.compareTo(levelModel.getPaymentId() + "") == 0) {
    %>
    <option value="<%=levelModel.getPaymentId()%>" selected><%=levelModel.getPaymentName()%></option>
    <%
    } else {
    %>
    <option value="<%=levelModel.getPaymentId()%>" ><%=levelModel.getPaymentName()%></option>
    <%
            }
        }
        out.println("</select></td>");
        out.println("</tr>");

        /*
         * out.println("<TR>"); out.println("<TD class=TableTextNote
         * width='40%'>Branch Of</TD>"); out.println("<TD><input type='text'
         * name='" + SCMInterfaceKey.CONTROL_TEXT_POS_BRANCH + "' id='" +
         * SCMInterfaceKey.CONTROL_TEXT_POS_BRANCH + "'> <font class=txt> Code
         * of main branch without .000</font></TD>"); out.println("</tr>");
         */


        out.println("<TR class=TableTextNote>");
        out.println("     <TD class=TableTextNote width='40%'>Address</TD>");
        if (posData.getPosDetailModel().getPosAddress() != null) {
            out.println("      <TD><TEXTAREA  name=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS + "\" cols=50 rows=5>" + posData.getPosDetailModel().getPosAddress() + "</TEXTAREA></TD>");
        } else {
            out.println("      <TD><TEXTAREA name=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS + "\" cols=50 rows=5></TEXTAREA></TD>");
        }
        out.println("</tr>");

        out.println("<TR class=TableTextNote>");
        out.println("     <TD class=TableTextNote width='40%'>Arabic Address</TD>");
        if (posData.getPosDetailModel().getPosArabicAddress() != null) {
            out.println("      <TD><TEXTAREA  name=\"" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_ADDRESS + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_ADDRESS + "\" cols=50 rows=5>" + posData.getPosDetailModel().getPosArabicAddress() + "</TEXTAREA></TD>");
        } else {
            out.println("      <TD><TEXTAREA name=\"" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_ADDRESS + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_ADDRESS + "\" cols=50 rows=5></TEXTAREA></TD>");
        }
        out.println("</tr>");

        out.println("<TR>");
        out.println("      <TD class=TableTextNote  width='40%'>Region</TD>");
        
        out.println("      <TD><select onChange=AddGovernrate(); name='" + SCMInterfaceKey.CONTROL_TEXT_POS_REGION + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_REGION + "'>");

        out.println("<option value=0>--</option>");
        String selectedId = posData.getPosDetailModel().getPosRegionID() + "";
        for (int i = 0; i < regions.size(); i++) {
            PlaceDataModel placeDataModel = (PlaceDataModel) regions.get(i);
            if (placeDataModel.getTypeId() == 1) {
                out.println("<option " + ((selectedId != null && selectedId.compareTo(placeDataModel.getRegionId() + "") == 0) ? "selected" : "") + " value=" + placeDataModel.getRegionId() + ">" + placeDataModel.getRegionName() + "</option>");
            }
        }
        out.println("</select></TD>");
        out.println("</tr>");

        //lamya
        //calidus
        out.println("<TR>");
        if(posData.isReportToCalidus()==true)
        {
            out.println(" <td><input type=checkbox name=report_to_calidus value=yes checked><font size='1'>Report to Calidus</font></td>");
        }
        else
        {
            out.println(" <td><input type=checkbox name=report_to_calidus value=yes><font size='1'>Report to Calidus</font></td>");
        }
        out.println("</tr>");
        
        //sign
        out.println("<TR>");
        if(posData.isIsSignSet()==true)
        {
            out.println(" <td><input type=checkbox name=set_sign value=yes checked><font size='1'>Has Sign</font></td>");
        }
        else
        {
            out.println(" <td><input type=checkbox name=set_sign value=yes><font size='1'>Has Sign</font></td>");
        }
        out.println("</tr>");
        
        //mobicash
        out.println("<TR>");
        if(posData.isIsMobicash()==true)
        {
            System.out.println("posData.getMobicashNum() INSIDE JSP : "+posData.getMobicashNum());
            out.println(" <td><input type=checkbox id=mobicash onclick=\"ShowHideDiv(this)\" name=mobicash value=yes checked><font size='1'>Orange Money</font></td>");
            out.println("<td><div name=orange id=orange style=display:block><font size='1'>Orange Money Number: </font><input type=text name=orangeText id=orangeText value="+posData.getMobicashNum()+" /><font size='1'> ex. 1227777777</font></div></td>");
        }
        else
        {
            out.println(" <td><input type=checkbox id=mobicash onclick=\"ShowHideDiv(this)\" name=mobicash value=yes><font size='1'>Orange Money</font></td>");
            out.println("<td><div name=orange id=orange style=display:none><font size='1'>Orange Money Number: </font><input type=text name=orangeText id=orangeText /><font size='1'> ex. 1227777777</font></div></td>");
        }
        out.println("</tr>");
        
        //nomad
        out.println("<TR>");
        if(posData.isIsNomad()==true)
        {
            out.println(" <td><input type=checkbox name=nomad value=yes checked><font size='1'>Nomad</font></td>");
        }
        else
        {
            out.println(" <td><input type=checkbox name=nomad value=yes><font size='1'>Nomad</font></td>");
        }
        out.println("</tr>");

        //L1
        out.println("<TR>");
        if(posData.isIsL1()==true)
        {
            out.println(" <td><input type=checkbox name=pos_level_one value=yes checked><font size='1'>POS Level One</font></td>");
        }
        else
        {
            out.println(" <td><input type=checkbox name=pos_level_one value=yes><font size='1'>POS Level One</font></td>");
        }
        out.println("</tr>");


        //QC
        out.println("<TR>");
        if(posData.isIsQC()==true)
        {
            out.println(" <td><input type=checkbox name=quality_club value=yes checked><font size='1'>Quality Club</font></td>");
        }
        else
        {
            out.println(" <td><input type=checkbox name=quality_club value=yes><font size='1'>Quality Club</font></td>");
        }
        out.println("</tr>");


        //EX
        out.println("<TR>");
        if(posData.isIsEX()==true)
        {
            out.println(" <td><input type=checkbox name=exclusive value=yes checked><font size='1'>Exclusive</font></td>");
        }
        else
        {
            out.println(" <td><input type=checkbox name=exclusive value=yes><font size='1'>Exclusive</font></td>");
        }
        out.println("</tr>");
        
        // end of checkboxes
        
        
        out.println("<tr>");
        out.println("      <TD class=TableTextNote width='40%'>Entry Date</TD>");
        out.println("<td><input type=text name='entry_date' value = '"+entryDate+"' readonly></td>");
        out.println("</tr>");
       /* out.println("<TR>");
        out.println("<td><font size='1'>Payment Level : </font><select name=payment_level value=selected >");*/
        
       
        /*Connection con= Utility.getConnection();
        Vector paymentLevels = RequestDao.getGenDcmPaymentLevelNames(con);
        for(int i=0; i< paymentLevels.size(); i++)
        {
            String paymentMethodName = (String) paymentLevels.elementAt(i);
            
            out.println("<option value="+paymentMethodName+" >"+"<font size='1'>"+paymentMethodName+"</font>"+"</option>");
        
        }
        out.println("</select>");
        out.println("</td></TR>");*/
        
        
        /////////////////////////////
        out.println("</TR>");
        out.println("<TR>");
        out.println("      <TD class=TableTextNote width='40%'>Payment Method</TD>");
        out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD  + "' id='" + SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD  + "'>");
        for (int i = 0; i < PaymentMethodVec.size(); i++) {
            PaymentMethodModel payModel = (PaymentMethodModel) PaymentMethodVec.get(i);
            if (PaymentMethod != null && PaymentMethod.compareTo(payModel.getPaymentMethodId()+ "") == 0) {
                %>
                <option value="<%=payModel.getPaymentMethodId()%>" selected><%=payModel.getPaymentMethodName()%></option>
                <%
                } else {
                %>
                <option value="<%=payModel.getPaymentMethodId()%>" ><%=payModel.getPaymentMethodName()%></option>
                <%
                        }
        }
        out.println("</select></td>");
        out.println("</tr>");

        ////////////////////////////
        
        
        
        
        
        //lamya
        
        
        out.println("<TR>");
        out.println("      <TD class=TableTextNote width='40%'>Governrate</TD>");
        out.println("      <TD>");
        drowRegionChild(out, regionsChilds, "2", SCMInterfaceKey.CONTROL_TEXT_POS_GOVER, posData.getGovernateId() + "");
        out.println("</TD>");
        out.println("</tr>");

        out.println("<TR>");
        out.println("      <TD class=TableTextNote width='40%'>City</TD>");
        out.println("      <TD>");
        drowRegionChild(out, regionsChilds, "3", SCMInterfaceKey.CONTROL_TEXT_POS_CITY, posData.getCityId() + "");
        out.println("&nbsp;&nbsp;");
        out.println("<input class='button' " + disabledStrCity + " type='button' name='similar_city' id='similar_city' value='All POS in City'  onclick=similarCity(); />");
        out.println("</TD>");
        out.println("</tr>");


        out.println("<TR>");
        out.println("      <TD class=TableTextNote width='40%'>District</TD>");
        out.println("      <TD>");
        drowRegionChild(out, regionsChilds, "4", SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT, posData.getDistrictId() + "");
        out.println("</TD>");
        out.println("</tr>");

        out.println("<TR>");
        out.println("      <TD class=TableTextNote width='40%'>Area</TD>");
        out.println("<TD>");
        drowRegionChild(out, regionsChilds, "5", SCMInterfaceKey.CONTROL_TEXT_POS_AREA, posData.getAreaId() + "");
        out.println("&nbsp;&nbsp;");
        out.println("<input class='button' " + disabledStrArea + " name='similar_area' id='similar_area' type='button' value='All POS in Area'  onclick=similarArea(); />");
        out.println("</TD>");
        out.println("</tr>");





        out.println("<TR class=TableTextNote>");
        out.println("      <TD class=TableTextNote width='40%'> Phone </TD>");

        out.println("<td><input name='phones__R0__C1' id='phones__R0__C1' value='");
        if (posData.getPosDetailModel().getPosPhones() != null) {
            out.println(posData.getPosDetailModel().getPosPhones().get(0));
        }

        out.println("'></td>");
        out.println("</TR>");


        out.println("</table>");


        out.println("</tr>");



        String ownerIDTypeName = posData.getPosDetailModel().getPosOwnerIDTypeName();
        
        
        /////////////////////////////////////////////////////////
        out.println("<TR>");
        out.println("<TD colspan=2 class=TableTextNote>Supervisor Data ");
        out.println("</TD>");
        out.println("</tr>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD colspan=2>");
        out.println("      <table border=1 align='center' width='100%'>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD width='40%'>Supervisor Name</TD>");
        /*if(userData.getUserFullName()==null || userData.getUserFullName().compareTo("")==0)
            out.println("      <TD><input type='text' value='" + posDetailModel.getSupervisorName()+ "' name='" + SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_NAME + "'></TD>");
        else*/
            out.println("      <TD><input type='text' value='" + userData.getUserFullName()+ "' name='" + SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_NAME + "'></TD>");
        
        out.println("</tr>");



        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Supervisor Mobile Phone</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_MOBILE+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_MOBILE+"' value=" +userData.getUserMobile()+ ">");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Supervisor Email</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_EMAIL+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_EMAIL+"' value=" +userData.getUserEmail()+ ">");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Supervisor Address</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_ADDRESS+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_ADDRESS+"' value=" +userData.getUserAddress()+ ">");
        out.println("</td>");
        out.println("</tr>");
        
        ////added
         out.println("</tr>");

        out.println("      </table>");
        out.println("      </TD>");
        out.println("</tr>");
        ///added - end
        
        
        ///////////////////////////////////////////////////////
        
        //////////////////////////////////////////////////////////
        out.println("<TR>");
        out.println("<TD colspan=2 class=TableTextNote>TeamLeader Data ");
        out.println("</TD>");
        out.println("</tr>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD colspan=2>");
        out.println("      <table border=1 align='center' width='100%'>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD width='40%'>TeamLeader Name</TD>");
        out.println("      <TD><input type='text' value='" + teamleaderData.getUserFullName()+ "' name='" + SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_NAME + "'></TD>");
        out.println("</tr>");



        out.println("<TR class=TableTextNote>");
        out.println("      <TD>TeamLeader Mobile Phone</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_MOBILE+"' id='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_MOBILE+"' value=" +teamleaderData.getUserMobile()+ ">");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>TeamLeader Email</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_EMAIL+"' id='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_EMAIL+"' value=" +teamleaderData.getUserEmail()+ ">");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>TeamLeader Address</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_ADDRESS+"' id='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_ADDRESS+"' value=" +teamleaderData.getUserAddress()+ ">");
        out.println("</td>");
        out.println("</tr>");
        
        ////added
         out.println("</tr>");

        out.println("      </table>");
        out.println("      </TD>");
        out.println("</tr>");
        ///added - end
        ///////////////////////////////////////////////////////////
        
        
        //////////////////////////////////////////////////////////////////////
        out.println("<TR>");
        out.println("<TD colspan=2 class=TableTextNote>Salesrep Data ");
        out.println("</TD>");
        out.println("</tr>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD colspan=2>");
        out.println("      <table border=1 align='center' width='100%'>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD width='40%'>Salesrep Name</TD>");
        out.println("      <TD><input type='text' value='" + salesrepData.getUserFullName()+ "' name='" + SCMInterfaceKey.CONTROL_TEXT_SALESREP_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_SALESREP_NAME + "'></TD>");
        out.println("</tr>");



        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Salesrep Mobile Phone</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_MOBILE+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_MOBILE+"' value=" +salesrepData.getUserMobile()+ ">");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Salesrep Email</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_EMAIL+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_EMAIL+"' value=" +salesrepData.getUserEmail()+ ">");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Salesrep Address</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_ADDRESS+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_ADDRESS+"' value=" +salesrepData.getUserAddress()+ ">");
        out.println("</td>");
        out.println("</tr>");
        
        ////added
         out.println("</tr>");

        out.println("      </table>");
        out.println("      </TD>");
        out.println("</tr>");
        ///added - end
        
        /////////////////////////////////////////////////////////////////////////

        out.println("<TR>");
        out.println("<TD colspan=2 class=TableTextNote>Owner Data ");
        out.println("</TD>");
        out.println("</tr>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD colspan=2>");
        out.println("      <table border=1 align='center' width='100%'>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD width='40%'>Owner Name</TD>");
        if (posData.getPosDetailModel().getPosOwnerName() != null) {
            out.println("      <TD><input type='text' value='" + posData.getPosDetailModel().getPosOwnerName() + "' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME + "'></TD>");
        } else {
            out.println("      <TD><input type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME + "'></TD>");
        }
        out.println("</tr>");



        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Owner Phone </TD>");
        out.println("<td>");

        //out.println("<INPUT type=hidden size=15 value="+posData.getPosDetailModel().getPosOwnerPhones().size()+" name=owner_phones_count>");


        String ownerPhone = (String) (posData.getPosDetailModel().getPosOwnerPhones() == null ? "" : (posData.getPosDetailModel().getPosOwnerPhones().get(0) == null ? "" : posData.getPosDetailModel().getPosOwnerPhones().get(0)));
        out.println("<input name=owner_phones__R0__C1 id= owner_phones__R0__C1 value=" + ownerPhone + ">");


        out.println("</td>");
        out.println("</tr>");


        // if(posData.getPosDetailModel().getPOSOwnerBirthDate() == null)
        //  {
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Owner Birth Date</TD>");
        out.println("      <TD><Script>drawCalender('" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE + "',\"*\");</script></TD>");
        out.println("</tr>");
        //   }
        //  else
        // {
        //   out.println("<TR class=TableTextNote>");
        //   out.println("      <TD>Owner Birth Date</TD>");
        //  out.println("      <TD><Script>drawCalender('" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE + "',"+ posData.getPosDetailModel().getPOSOwnerBirthDate()+");</script></TD>");
        //  out.println("</tr>");
        // }
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Owner I.D Type</TD>");
        out.println("      <TD><select id '" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE + "' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE + "'>");

        for (int i = 0; i < IDTypeVector.size(); i++) {
            IDTypeModel = (GenericModel) IDTypeVector.get(i);
            String selection = "";
            if (ownerIDTypeName != null && ownerIDTypeName.equals(IDTypeModel.get_field_2_value())) {
                selection = "selected";
            }
            out.println("<option value='" + IDTypeModel.get_primary_key_value() + "' " + selection + ">" + IDTypeModel.get_field_2_value() + "</option>");
        }
        out.println("</select></TD>");
        out.println("</tr>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Owner I.D Number</TD>");

        /**
         * Change Request for enabling the owner to be editable. By Ahmed Safwat
         * 31 May 2011
         *
         */
//   out.println("      <TD><input value='" + posData.getPosDetailModel().getPosOwnerIDNumber() + "' type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER + "'></TD>");

        /*
         * if (posData.getPosDetailModel().getPosOwnerIDNumber() != null) {
         * out.println(" <TD><input disabled value='" +
         * posData.getPosDetailModel().getPosOwnerIDNumber() + "' type='text'
         * name='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER + "' id='"
         * + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER + "'></TD>");
         * out.println(" <TD><input value='" +
         * posData.getPosDetailModel().getPosOwnerIDNumber() + "' type='hidden'
         * name='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER_HIDDEN +
         * "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER_HIDDEN +
         * "'></TD>"); } else { out.println(" <TD><input disabled type='text'
         * name='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER + "' id='"
         * + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER + "'></TD>"); }
         *
         */
        /**
         * End of Change
         *
         */
        out.println("      <TD><input  value='" + posData.getPosDetailModel().getPosOwnerIDNumber() + "' type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER_HIDDEN + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER_HIDDEN + "'></TD>");




        out.println("</tr>");

        out.println("      </table>");
        out.println("      </TD>");
        out.println("</tr>");
        
        
        
        out.println("<TR>");
        out.println("<TD colspan=2 class=TableTextNote>Manager Data ");

        out.println("</TD>");
        out.println("</tr>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD colspan=2>");
        out.println("      <table border=1 align='center' width='100%'>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD width='40%'>Manager Name</TD>");

        if (posData.getPosDetailModel().getPosManagerName() != null) {
            out.println("      <TD><input type='text' value='" + posData.getPosDetailModel().getPosManagerName() + "' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME + "'></TD>");
        } else {
            out.println("      <TD><input type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME + "'></TD>");
        }
        out.println("</tr>");








        out.println("<TR class=TableTextNote>");
        out.println("      <TD> Manager Phone </TD>");
        out.println("<td>");
        // out.println("<TR>");
        //out.println("<INPUT type=hidden size=15 value="+posData.getPosDetailModel().getPosManagerPhones().size()+" name=manager_phones_count>");
        String managerPhone = (String) (posData.getPosDetailModel().getPosManagerPhones() == null ? "" : (posData.getPosDetailModel().getPosManagerPhones().get(0) == null ? "" : posData.getPosDetailModel().getPosManagerPhones().get(0)));
        out.println("<input type='text' name='manager_phones__R0__C1' id='manager_phones__R0__C1'  value='" + managerPhone + "'>");
        out.println("</td>");
        out.println("</tr>");


        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Manager Birth Date</TD>");
        out.println("      <TD><Script>drawCalender('" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_BIRTH_DATE + "',\"*\");</script></TD>");
        out.println("</tr>");


        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Manager I.D Type</TD>");
        out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE + "'>");

        for (int i = 0; i < IDTypeVector.size(); i++) {

            IDTypeModel = (GenericModel) IDTypeVector.get(i);

            String selection = "";
            if (posData.getPosDetailModel().getPosManagerIDTypeName() != null && posData.getPosDetailModel().getPosManagerIDTypeName().equalsIgnoreCase(IDTypeModel.get_field_2_value())) {
                selection = "selected";
            }
            out.println("<option value='" + IDTypeModel.get_field_1_value() + "' " + selection + ">" + IDTypeModel.get_field_2_value() + "</option>");
            //    Utility.logger.debug("XxxxXxxxXxxxX:  MANAGER ID TYPE  ID:  "+ posDetailModel.getPOSManagerIDTypeName());
        }

        out.println("</select></TD>");
        out.println("</TR>");

        out.println("<TR class=TableTextNote>");

        out.println("      <TD>Manager I.D Number</TD>");
        if (posData.getPosDetailModel().getPosManagerIDNumber() != null) {
            out.println("      <TD><input  value='" + posData.getPosDetailModel().getPosManagerIDNumber() + "' type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER + "'></TD>");
        } else {
            out.println("      <TD><input  type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER + "'></TD>");
        }
        out.println("</tr>");


        out.println("<TR>");
        out.println("      <TD class=TableTextNote>Demo Line Num.</TD>");
        if (posData.getDemoLineNum() != null) {
            out.println("      <TD><input type='text' value='" + posData.getDemoLineNum() + "' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_DEMO + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_DEMO + "'></TD>");
        } else {
            out.println("      <TD><input type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_DEMO + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_DEMO + "'></TD>");
        }
        out.println("</tr>");

        out.println("<TR>");
        out.println("      <TD class=TableTextNote>E-mail</TD>");
        if (posData.getPosDetailModel().getPosEmail() != null) {
            out.println("      <TD><input value='" + posData.getPosDetailModel().getPosEmail() + "' type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL + "'></TD>");
        } else {
            out.println("      <TD><input type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL + "'></TD>");
        }
        out.println("</tr>");

        out.println("      </table>");
        out.println("      </TD>");
        out.println("</tr>");


        /**
         * Change Request for enabling the documents to be editable. By Ahmed
         * Safwat 31 May 2011
         *
         */

        /*
         * out.println("<TR class=TableTextNote>"); out.println("<TR>");
         * out.println("<TD colspan=2 class=TableTextNote>Documents ");
         * out.println("</TD>"); out.println("</tr>"); out.println("<TR
         * class=TableTextNote>"); out.println(" <TD colspan=2>"); out.println("
         * <table border=1 align='center' width='100%'>"); out.println(" <TD
         * class=TableTextNote width='40%'>Proposed Documents</TD>");
         *
         * out.println(" <TD><select name='" +
         * SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC + "' id='" +
         * SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC + "'
         * onchange='doc(this);'>"); out.print("<option>--</option>"); for (int
         * i = 0; i < documentVec.size(); i++) {
         *
         * ProposedDocument docModel = (ProposedDocument) documentVec.get(i); if
         * (docModel.getDocId() == posData.getProposedDocId()) {
         * out.println("<option selected value=" + docModel.getDocId() + ">" +
         * docModel.getDocName() + "</option>"); } else { out.println("<option
         * value=" + docModel.getDocId() + ">" + docModel.getDocName() +
         * "</option>"); } } out.println("</select></td>");
         * out.println("</tr>");
         *
         *
         * out.println("<TR>"); String
         * posDocNumber=posData.getDocNumber()==null?"":posData.getDocNumber();
         * out.println(" <TD class=TableTextNote>Document Num.</TD>");
         * out.println(" <TD><input type='text' name=\"" +
         * SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM + "\" id=\"" +
         * SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM + "\" value='" +
         * posDocNumber + "' ></TD>"); out.println("</TR>");
         */
        //if (posData.getDocNumber() == null) {
        out.println("<input type='hidden' name='" + SCMInterfaceKey.INPUT_HIDDEN_POS_DOC_CHANGE + "' value='0'>");
        out.println("<TR class=TableTextNote>");
        out.println("<TR>");
        out.println("<TD colspan=2 class=TableTextNote>Documents ");
        out.println("</TD>");
        out.println("</tr>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD colspan=2>");
        out.println("      <table border=1 align='center' width='100%'>");
        out.println("      <TD class=TableTextNote width='40%'>Proposed Documents</TD>");

        out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC + "' onchange='doc(this);'>");
        out.print("<option>--</option>");
        for (int i = 0; i < documentVec.size(); i++) {

            ProposedDocument docModel = (ProposedDocument) documentVec.get(i);


            if (posData.getDocument() != null && docModel.getDocId() == Integer.parseInt(posData.getDocument())) {
                out.println("<option selected value=" + docModel.getDocId() + ">" + docModel.getDocName() + "</option>");
            } else {
                out.println("<option value=" + docModel.getDocId() + ">" + docModel.getDocName() + "</option>");
            }
        }
        out.println("</select></td>");
        out.println("</tr>");


        out.println("<TR>");
        out.println("      <TD class=TableTextNote>Document Num.</TD>");
        if (posData.getDocNumber() != null) {
            out.println("      <TD><input  type='text' value='" + posData.getDocNumber() + "' name=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM + "\" cols=50 rows=5></TEXTAREA></TD>");

        } else {
            out.println("      <TD><input  type='text' name=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM + "\" cols=50 rows=5></TEXTAREA></TD>");
        }

        /*
         * } else { out.println("<input type='hidden' name='" +
         * SCMInterfaceKey.INPUT_HIDDEN_POS_DOC_CHANGE + "' value='1'>");
         * out.println("<TR class=TableTextNote>"); out.println(" <TD
         * colspan=2>"); out.println(" <table border=1 align='center'
         * width='100%'>"); out.println("<TR class=TableTextNote>");
         *
         *
         * out.println(" <TD class=TableTextNote width='40%'>Proposed
         * Documents</TD>"); if (posData.getDocument() != null) { out.println("
         * <TD><input disabled type='text' value='" + posData.getDocument() + "'
         * cols=50 rows=5></TEXTAREA></TD>");
         *
         * } else { out.println(" <TD><input disabled type='text' cols=50
         * rows=5></TEXTAREA></TD>"); }
         *
         *
         * out.println(" <TD><input type='hidden' value='" +
         * posData.getProposedDocId() + "' name=\"" +
         * SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC + "\" id=\"" +
         * SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC + "\" cols=50
         * rows=5></TEXTAREA></TD>"); out.println("</tr>");
         *
         *
         *
         * out.println("<TR>"); out.println(" <TD class=TableTextNote>Document
         * Num.</TD>"); if (posData.getDocNumber() != null) { out.println("
         * <TD><input disabled type='text' value='" + posData.getDocNumber() +
         * "' cols=50 rows=5></TEXTAREA></TD>"); } else { out.println("
         * <TD><input disabled type='text' cols=50 rows=5></TEXTAREA></TD>"); }
         *
         * out.println(" <TD><input type='hidden' value='" +
         * posData.getDocNumber() + "' name=\"" +
         * SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM + "\" id=\"" +
         * SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM + "\" cols=50
         * rows=5></TEXTAREA></TD>"); out.println("</tr>"); }
         *
         *
         *
         */
        /**
         * End Of Change
         *
         */
        out.println("<TR>");
        out.println("      <TD class=TableTextNote>Document Location</TD>");
        String docLoc = posData.getDocLocation() != null ? posData.getDocLocation() : "";
        out.println("      <TD><input type='text' value='" + docLoc + "' name=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_DOC_LOC + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_DOC_LOC + "\" cols=50 rows=5></TD>");
        out.println("</tr>");

        out.println("</table>");
        out.println("</TD>");
        out.println("</tr>");



        out.println("<TR>");
        out.println("<TD colspan=2 class=TableTextNote>STK Data ");

        out.println("</TD>");
        out.println("</tr>");







        out.println("<TR class=TableTextNote>");
        out.println("      <TD colspan=2>");
        out.println("      <table border=1 align='center' width='100%'>");

        if (posData.getStkDialNumber() == null) {
            out.println("<input type='hidden' name='" + SCMInterfaceKey.INPUT_HIDDEN_STK_POS_CHANGE + "' value='0'>");
            out.println("<TR class=TableTextNote>");
            out.println("<TD class=TableTextNote width='40%'>STK Dial Num.</TD>");
            if (posData.getStkDialNumber() != null) {
                out.println("<TD><input type='text' " + (isDist && isPos ? "" : "disabled") + " value='" + posData.getStkDialNumber() + "' name='" + SCMInterfaceKey.CONTROL_TEXT_STK_DIAL + "' id='" + SCMInterfaceKey.CONTROL_TEXT_STK_DIAL + "'></TD>");
            } else {
                out.println("<TD><input type='text' " + (isDist && isPos ? "" : "disabled") + " value='' name='" + SCMInterfaceKey.CONTROL_TEXT_STK_DIAL + "' id='" + SCMInterfaceKey.CONTROL_TEXT_STK_DIAL + "'></TD>");
            }
            out.println("</tr>");



        } else {
            out.println("<input type='hidden' name='" + SCMInterfaceKey.INPUT_HIDDEN_STK_POS_CHANGE + "' value='1'>");
            out.println("<TR class=TableTextNote>");
            out.println("<TD class=TableTextNote width='40%'>STK Dial Num.</TD>");
            if (posData.getStkDialNumber() != null && posData.getStkDialNumber() != "") {
                out.println("<TD><input disabled type='text' value='" + posData.getStkDialNumber() + "' name='" + SCMInterfaceKey.CONTROL_TEXT_STK_DIAL + "' id='" + SCMInterfaceKey.CONTROL_TEXT_STK_DIAL + "'></TD>");
                out.println("<TD><input  type='hidden' value='" + posData.getStkDialNumber() + "' name='" + SCMInterfaceKey.CONTROL_TEXT_STK_DIAL_HIDE + "' id='" + SCMInterfaceKey.CONTROL_TEXT_STK_DIAL_HIDE + "'></TD>");
    %>
    <td>
        <input type="button" value="change STK Dial Number" onclick="changestk();">
    </td>
    <%
            } else
                out.println("<TD><input disabled type='text' value='' name='" + SCMInterfaceKey.CONTROL_TEXT_STK_DIAL + "' id='" + SCMInterfaceKey.CONTROL_TEXT_STK_DIAL + "'></TD>");
            out.println("</tr>");



        }

        out.println("</table>");
        out.println("</TD>");
        out.println("</tr>");

        out.println("</table>");

















        out.print("<script language=\"javascript\">");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + posData.getPosDetailModel().getPosOwnerBirthDate());
        out.print("document.formDataView." + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE + ".value='" + posData.getPosDetailModel().getPosOwnerBirthDate() + "';\n");
        out.print("document.formDataView." + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_BIRTH_DATE + ".value='" + posData.getPosDetailModel().getPosManagerBirthDate() + "';\n");
//      out.print("document.formDataView."+ SCMInterfaceKey.CONTROL_TEXT_POS_RATE_DATE+".value='"+posData.getRateDate()+"';\n");

        out.print("</script>");

        /*
         * /// ownerIDTypeName = posDetailModel.getPOSOwnerIDTypeName();
         * POSPhones = posData.getPosDetailModel().getPOSPhones();
         * POSManagerPhones = posData.getPosDetailModel().getPOSManagerPhones();
         * POSOwnerPhones = posData.getPosDetailModel().getPOSOwnerPhones();
         *
         *
         * out.println("<script>"); if(POSPhones != null) { for(int h= 0; h <
         * POSPhones.size() ; h++) { v = h+1; String phone =
         * (String)POSPhones.get(h);
         * out.println("eval(\"phones.RowSet.add("+v+");\"); ");
         * out.println("eval(\"document.formDataView.phones__R"+v+"__C1.value =
         * '"+ phone +"';\"); ");
         *
         * }
         * }
         *
         * out.println("eval(\"document.formDataView.phones_count.value = "+
         * POSPhones.size() +" ;\"); ");
         *
         * if(POSManagerPhones != null) { for(int h= 0; h <
         * POSManagerPhones.size() ; h++) { v = h+1; String phone =
         * (String)POSManagerPhones.get(h);
         * out.println("eval(\"manager_phones.RowSet.add("+v+");\"); ");
         * out.println("eval(\"document.formDataView.manager_phones__R"+v+"__C1.value
         * = '"+ phone +"';\"); ");
         *
         * }
         * }
         * out.println("eval(\"document.formDataView.manager_phones_count.value
         * = "+ POSManagerPhones.size() +" ;\"); ");
         *
         * if(POSOwnerPhones != null) { for(int h= 0; h < POSOwnerPhones.size()
         * ; h++) { v = h+1; String phone = (String)POSOwnerPhones.get(h);
         * out.println("eval(\"owner_phones.RowSet.add("+v+");\"); ");
         * out.println("eval(\"document.formDataView.owner_phones__R"+v+"__C1.value
         * = '"+ phone +"';\"); ");
         *
         * }
         * }
         * out.println("eval(\"document.formDataView.owner_phones_count.value =
         * "+ POSOwnerPhones.size() +" ;\"); ");
         */
        out.println("</script>");





        out.println("<center>");
        out.println("<input class='button'   type='submit' name = 'mySubmit' value='Save' onclick=\"saveForm();\"     />");
    %>
    <input class='button' type='button' value='Back' onclick="history.go(-1);"/>
    <%
        out.println("</center>");


        out.println("</form>");

    %>
    <br><br>

</body>


<SCRIPT language="javascript">
    
    // Declaring required variables
    var digits = "0123456789";
    // non-digit characters which are allowed in phone numbers
    var phoneNumberDelimiters = "()- ";
    // characters which are allowed in international phone numbers
    // (a leading + is OK)
    var validWorldPhoneChars = phoneNumberDelimiters + "+";
    // Minimum no of digits in an international phone no.
    var minDigitsInIPhoneNumber = 10;
    function IsPhoneInteger(s)
    {   var i;
        for (i = 0; i < s.length; i++)
        {   
            // Check that current character is number.
            var c = s.charAt(i);
            if (((c < "0") || (c > "9"))) return false;
        }
        // All characters are numbers.
        return true;
    }
    function trimPhone(s)
    {   var i;
        var returnString = "";
        // Search through string's characters one by one.
        // If character is not a whitespace, append to returnString.
        for (i = 0; i < s.length; i++)
        {   
            // Check that current character isn't whitespace.
            var c = s.charAt(i);
            if (c != " ") returnString += c;
        }
        return returnString;
    }
    function stripCharsInBag(s, bag)
    {   var i;
        var returnString = "";
        // Search through string's characters one by one.
        // If character is not in bag, append to returnString.
        for (i = 0; i < s.length; i++)
        {   
            // Check that current character isn't whitespace.
            var c = s.charAt(i);
            if (bag.indexOf(c) == -1) returnString += c;
        }
        return returnString;
    }

    function checkInternationalPhone(strPhone){
        var bracket=3
        strPhone=trimPhone(strPhone)
        if(strPhone.indexOf("+")!=-1) return false
        if(strPhone.indexOf("-")!=-1)return false
        if(strPhone.indexOf("(")!=-1)return false
        if(strPhone.indexOf(")")!=-1)return false
        s=stripCharsInBag(strPhone,validWorldPhoneChars);
        return (IsPhoneInteger(s));
    }


    function onSelectChannel_Level(){
        var selectedChannelIndexVal = document.formDataView.<%=SCMInterfaceKey.TEXT_POSCHANNEL%>.selectedIndex;
        var selectedLevelIndexVal = document.formDataView.<%=SCMInterfaceKey.LEVEL_FOR_POS%>.selectedIndex;
        var channelOptVal = document.formDataView.<%=SCMInterfaceKey.TEXT_POSCHANNEL%>.options[selectedChannelIndexVal].text;
        var levelOptVal = document.formDataView.<%=SCMInterfaceKey.LEVEL_FOR_POS%>.options[selectedLevelIndexVal].text;

        if (channelOptVal.toLowerCase() == 'distribution' && levelOptVal.toLowerCase()=='pos'){
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.disabled = false;
        }
        else{
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.value='';
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.disabled = true;
        }

    }



    function similarStatus()
    {
        if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_STATUS%>.value") == "")
        {
            alert("Please Enter POS Status ..");
        }
        else
        {
            document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_POS_DATA_SIMILAR_STATUS%>';
            formDataView.submit();
        }
    }


    function similarName()
    {
        if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_NAME%>.value") == "")
        {
            alert("Please Enter POS Name ..");
        }
        else
        {
            document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_POS_DATA_SIMILAR_NAME%>';
            formDataView.submit();
        }
    }

    function similarCity()
    {
        if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.value") == "0")
        {
            alert("Please Enter City ..");
        }
        else
        {
            document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_POS_DATA_SIMILAR_CITY%>';
            formDataView.submit();
        }
    }


    function similarArea()
    {
        if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.value") == "0")
        {
            alert("Please Enter Area ..");
        }
        else
        {
            document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_POS_DATA_SIMILAR_AREA%>';
            formDataView.submit();
        }

    }

    function saveForm()
    {
       
   
        var flag = 0;
        // var posCode = document.getElementById('<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE%>').value;
        // alert(posCode);
        // var indicator = posCode.indexof('.001');
 
        var docLoc = trimAll(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_LOC%>.value);
 
    document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_LOC%>.value = docLoc;
 
    if( docLoc == "")
        {
            flag = 1;
            alert("Please Enter Document Location ..");
        }
 
         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_NAME%>.value") == "")
        {
            flag = 1;
            alert("Please Enter POS Name ..");
        }
         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE%>.value") == "")
        {
            flag = 1;
            alert("Please Enter POS code ..");
        }
        //else if(isNaN(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE%>.value")))
        //  {
        //      flag = 1;
        //    alert("POS Code Accepts Numbers Only ...");
        //  }
        // else if(indicator== -1)
        //{
        // flag = 1;
        // alert("POS Code Must Contais .001  ...");
        // }
        
         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS%>.value") == "")
        {
            flag = 1;
            alert("Please Enter Address ..");
        }
        
         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>.value") == "0")
        {
            flag = 1;
            alert("Please Enter Region ..");
        }
        
         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>.value") == "0" || eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>.value") == "")
        {
            flag = 1;
            alert("Please Enter Governrate ..");
        }
        
         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.value") == "0" || eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.value") == "")
        {
            flag = 1;
            alert("Please Enter City ..");
        }
        
         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL%>.value") == "3")
        {
            if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.value") == "0" || eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.value") == "")
            {
                flag = 1;
                alert("Please Enter District ..");
            }
            else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.value") == "0" || eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.value") == "")
            {
                flag = 1;
                alert("Please Enter Area ..");
            }
        }
        
        if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME%>.value") == "")
        {
            flag = 1;
            alert("Please Enter Owner Name ..");
        }
         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER_HIDDEN%>.value") == "")
        {
            flag = 1;
            alert("Please Enter Owner ID Number ..");
        }
         if(isNaN(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER_HIDDEN%>.value")))
        {
            flag = 1;
            alert("Owner ID Number Accepts Numbers Only ...");
        }
         if(checkInternationalPhone(eval("document.formDataView.owner_phones__R0__C1.value"))==false)
        {
            flag = 1;
            alert("Owner Phone Number Accepts Numbers Only ...");
        }
  
         if(isNaN(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER%>.value")))
        {
            flag = 1;
            alert("Manager ID Number Accepts Numbers Only ...");
        }
        if(checkInternationalPhone(eval("document.formDataView.manager_phones__R0__C1.value"))==false)
        {
            flag = 1;
            alert("Manager Phone Number Accepts Numbers Only ...");
        }
         if(checkInternationalPhone(document.formDataView.phones__R0__C1.value)==false){
            alert("Please POS Phone Must Be Numric.");
            return;
        }
    
         if(checkInternationalPhone(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DEMO%>.value"))==false)
        {
            flag = 1;
            alert("Demo Line Accepts Numbers Only ...");
        }
  
         if(eval("document.formDataView.phones__R0__C1.value") == "")
        {
            flag = 1;
            alert("Please Enter POS Phone..");
        }
         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL%>.value") != "" /*|| eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_EMAIL%>.value") != ""*/)
        {
            var str = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL%>.value;
            var at="@"
            var dot="."
            var lat=str.indexOf(at)
            var lstr=str.length
            var ldot=str.indexOf(dot)
            if (str.indexOf(at)==-1)
            {
                flag = 1;
                alert("Invalid E-mail Formate ..")

            }

            else if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr)
            {
                flag = 1;
                alert("Invalid E-mail Formate ..")

            }

            else if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr)
            {
                flag = 1;
                alert("Invalid E-mail Formate ..")

            }

            else if (str.indexOf(at,(lat+1))!=-1)
            {
                flag = 1;
                alert("Invalid E-mail Formate ..")

            }

            else if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot)
            {
                flag = 1;

                alert("Invalid E-mail Formate ..")

            }

            else if (str.indexOf(dot,(lat+2))==-1)
            {
                flag = 1;
                alert("Invalid E-mail Formate ..")

            }

            else if (str.indexOf(" ")!=-1)
            {
                flag = 1;
                alert("Invalid E-mail Formate ..")

            }
        }

  
         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.value") != "")
        {
            if(checkInternationalPhone(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.value"))==false)
            {
                flag = 1;
                alert("STK Dial Number Accepts Numbers Only ...");
            }

        }
         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.value") != "")
        {
            if(checkInternationalPhone(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.value"))==false)
            {
                flag = 1;
                alert("STK Dial Number Accepts Numbers Only ...");
            }
         
        }

        
        if(flag != 1)
        {
          
            
            document.formDataView.phones__R0__C1.value=trimPhone(document.formDataView.phones__R0__C1.value);
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.value=trimPhone(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.value);
            document.formDataView.manager_phones__R0__C1.value=trimPhone(document.formDataView.manager_phones__R0__C1.value);
            document.formDataView.owner_phones__R0__C1.value=trimPhone(document.formDataView.owner_phones__R0__C1.value);
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_MOBILE%>.value=trimPhone(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_MOBILE%>.value);
            document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_POS_DATA_EDIT_STORE%>';
        
            $("#formDataView").attr("<%=InterfaceKey.HASHMAP_KEY_ACTION%>","<%out.print(formAction);%>");
            document.formDataView.submit();
        }


    }


    function checkQuotes()
    {
        var nKeyCode = event.keyCode;
        if( Number(nKeyCode)== 34 )
        {
            alert("You are not allowed to use the (\") character");
            event.keyCode =0;
            return false;
        }
        if( Number(nKeyCode)== 39 )
        {
            alert("You are not allowed to use the (\') character");
            event.keyCode = 0;
            return false;
        }
        return true;
    }

    function data_view_RowSet_add(argCurrentValue,argCurrentName,argCounterName,argControlName,argArrayDataView)
    {
        //Number("+ argControlName + ".RowSet.getRowCount())
        ix = eval("document.formDataView."+argCounterName+".value = Number(document.formDataView."+argCounterName+".value) + 1;");
        ix = ix-1;
        eval(argControlName+".RowSet.add()");
        argCurrentName = argCurrentName+","+argCurrentName;
        //for (var i = 0; i < eval(argArrayDataView+".length"); i++)
        //{
        //   if (eval(argArrayDataView+"["+i+"]") == argCurrentValue)
        //   {
        //      eval(argControlName+".RowSet.getCell("+Number(ix+1)+",1).cellElement.selectedIndex ="+i+";");
        //   }
        //}
    }

    function app_need_removeRows(argObject)
    {
        i = confirm("This will remove this data")
        if (i==true){
            for(var i=eval(argObject+".getRowCount()");i>=1;i--){
                if(eval(argObject+".getCell("+i+",2).getValue()")==true){
                    eval(argObject+".RowSet.deleteRow("+i+");");
                }//end if
            }//end for
        }//end if
        else
        {
            for(var i=eval(argObject+".getRowCount()");i>=1;i--){
                if(eval(argObject+".getCell("+i+",2).getValue()")==true){
                    eval(argObject+".getCell("+i+",2).setValue == false; ")
                }//end if
            }//end for
        }//end else
    }

    var UserDefinedDataViewArray =new Array();
    function popUp(argObj,argVersionArrayName,argDescriptionArrayName)
    {
        var nRowIndex;
        var nSelectedIndex = 0;
        var strPopUpColumnIDVersion = new String();
        var strPopUpColumnIDDescription = new String();
        var strID= new String(argObj.id);
        nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
        strPopUpColumnIDVersion = strID.substring(0,strID.indexOf("__C")+3);
        strPopUpColumnIDVersion +=2
        strPopUpColumnIDDescription = strID.substring(0,strID.indexOf("__C")+3);
        strPopUpColumnIDDescription +=3;

        eval("nSelectedIndex = document.formDataView."+strID+".selectedIndex");
        if(nSelectedIndex > 0)
        {
            var strDescrioption;
            var arrPairs=new Array();
            var strPairs=new String(eval(argVersionArrayName+"["+nSelectedIndex+"];"));
            strDescrioption = eval(argDescriptionArrayName+"["+nSelectedIndex+"];");
            arrPairs = strPairs.split(",");

            eval("document.formDataView."+strPopUpColumnID+".add(objOption);");
            eval("document.formDataView."+strPopUpColumnIDVersion+".value ="+arrPairs[0]+";");
            if(strDescrioption == "" || strDescrioption == null || strDescrioption == "null")
                strDescrioption = "N/A";
            eval("document.formDataView."+strPopUpColumnIDDescription+".value ='"+strDescrioption+"';");

        }
        else
        {
            eval("document.formDataView."+strPopUpColumnIDVersion+".value ='';");
            eval("document.formDataView."+strPopUpColumnIDDescription+".value ='';");
        }
    }




    function AddGovernrate()
    {
                   
        var regionId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>.value;
        buildPatternOfRegions();
                  
        if (regionId!=0){
            getRegionChild();
        }
    }

    function AddCity()
    {
        var regionId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>.value;
        buildPatternOfRegions();
        if (regionId!=0){
            getRegionChild();
        }
    }
    function AddDistrict()
    {
        var regionId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.value;
        buildPatternOfRegions();
        if (regionId!=0){
            getRegionChild();
        }
    }
    function AddArea()
    {
        var regionId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.value;
        buildPatternOfRegions();
        if (regionId!=0){
            getRegionChild();
        }
    }
    function onChangeArea()
    {
        var regionId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.value;
        buildPatternOfRegions();
        if (regionId!=0){
            document.formDataView.similar_area.disabled=false;
        }
        else
        {

            document.formDataView.similar_area.disabled=true;
        }
    }
    function getRegionChild()
    {
        document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_GET_REGION_CHILD_EDIT%>';
                  
        formDataView.submit();
    }
    function buildPatternOfRegions(){
        var disCt = "0";
        var disArea = "0";
        document.formDataView.<%=SCMInterfaceKey.CONTROL_REGION_PARENT_ID%>.value='';
        var regId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>.value;
        var govId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>.value;
        var cityId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.value;
        var distId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.value;
        var areaId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.value;

        if (regId==0){
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>.selectedIndex=0;
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.selectedIndex=0;
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.selectedIndex=0;
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.selectedIndex=0;
            removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>');
            removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>');
            removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>');
            removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>');
            disCt=1;
            disArea=1;
        }
        if (govId==0){
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.selectedIndex=0;
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.selectedIndex=0;
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.selectedIndex=0;

            removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>');
            removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>');
            removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>');
            disCt=1;
            disArea=1;
        }
        if (cityId==0){
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.selectedIndex=0;
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.selectedIndex=0;

            removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>');
            removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>');
            disCt=1;
            disArea=1;
        }
        if (distId==0){
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.selectedIndex=0;
            removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>');

            disArea=1;
        }

        if (disArea==1)
        {
            document.formDataView.similar_area.disabled=true;
        }
        if (disCt==1)
        {
            document.formDataView.similar_city.disabled=true;
        }

        document.formDataView.<%=SCMInterfaceKey.CONTROL_REGION_PARENT_ID%>.value = regId+'-'+govId+'-'+cityId+'-'+distId+'-'+areaId;
        //alert(regId+'-'+govId+'-'+cityId+'-'+distId+'-'+areaId)

    }


    function fillData()
    {
        if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>.value") != "0")
        {
            AddGovernrate();
            AddCity();
            AddDistrict();
            AddArea();


        }
    }
    function changestk()
    {
        document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_CHANGE_STK_NUMBER_PAGE%>';
        formDataView.submit();

    }
    function doc(selectBox)
    {
        var selectedIndex=selectBox.selectedIndex;
        var selectedValue=selectBox.options[selectedIndex].value;
        if(selectedValue=="")
        {
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM%>.disabled=true;
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM%>.value="";
        }
        else{
            if(selectedValue=="4")
            {
                document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM%>.disabled=false;
                document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM%>.value="memo";

            }else{
                document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM%>.disabled=false;
                document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM%>.value="";

            }
        }
    }
    function trimAll(sString)
    {
        while (sString.substring(0,1) == ' ')
        {
            sString = sString.substring(1, sString.length);
        }
        while (sString.substring(sString.length-1, sString.length) == ' ')
        {
            sString = sString.substring(0,sString.length-1);
        }
        return sString;
    }
    function removeAllOptions(selectName){
        document.getElementById(selectName).length = 0;
        document.getElementById(selectName).options[0] = new Option(' -- ', '0');

    }

</SCRIPT>
                
<script type="text/javascript">
    function ShowHideDiv(mobicash) {
        var orange = document.getElementById("orange");
        orange.style.display = mobicash.checked ? "block" : "none";
       
    }
</script>
<%!
    private void drowRegionChild(JspWriter out, HashMap<String, RegionModel> regionsChilds, String regionType, String ControlName, String selectedId) throws IOException {
        //System.out.println("regionType iss "+regionType);

        String functionName = regionType.compareTo("2") == 0 ? "onChange=AddCity();" : regionType.compareTo("3") == 0 ? "onChange=AddDistrict();"
                : regionType.compareTo("4") == 0 ? "onChange=AddArea();"
                : regionType.compareTo("5") == 0 ? "onChange=onChangeArea();" : "";



        out.println("<select " + functionName + "  name='" + ControlName + "' id='" + ControlName + "'>");
        out.println("<option value=0>--</option>");
        if (regionsChilds != null && !regionsChilds.isEmpty()) {
            for (String regId : regionsChilds.keySet()) {

                String regName = regionsChilds.get(regId).getRegionName();
                String levl = regionsChilds.get(regId).getRegionLevelTypeId();
                //String selected = "selected";
                if (regionType.compareTo(levl) == 0) {
                    out.println("<option ");
                    if (selectedId != null && regId.compareTo(selectedId) == 0) {
                        out.print("selected");
                    }
                    out.print(" value=" + regId + ">" + regName + "</option>");
                }
            }
        }
        out.println("</select>");



    }%>
</html>
