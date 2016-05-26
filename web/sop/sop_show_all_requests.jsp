<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"

         import="com.mobinil.sds.core.system.sop.requests.model.*"

         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
         %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
        <SCRIPT language=JavaScript src="../resources/js/calendar.js" type="text/javascript"></SCRIPT>      
    </head>
    <body>

        <script>
                function checkBeforeSubmit()
                {
                    var warehouseValue = document.SOPform.<%=SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID%>.value;
                    if(warehouseValue == "")
                    {
                        alert("You must choose Warehouse.");
                    }
                    else
                    {
                        SOPform.submit();
                    }
                }
        </script>
        <script>
            function drawCalender(argOrder,argValue)
            {
                document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SOPform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
                document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
            }

            function setSearchValues(requestId,dcmName,requestCreateDateFrom,requestCreateDateTo,requestStatus,warehouseId)
            {
                document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID%>").value = requestId;
                document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME%>").value =     dcmName;
                document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM%>").value = requestCreateDteFrom;
                document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO%>").value = requestCreateDateTo;
                document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS%>").value = requestStatus;
                document.getElementById("<%=SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID%>").value = warehouseId;
            }
            function Toggle(item) 
            {
                obj=document.getElementById(item);
                if (obj!=null) 
                {
                    visible=(obj.style.display!="none")
                    key=document.getElementById("x"+item);
                    if (visible) 
                    {
                        obj.style.display="none";
                        key.innerHTML="<img src='../resources/img/plus.gif' border='0'>";
                    } 
                    else 
                    {
                        obj.style.display="block";
                        key.innerHTML="<img src='../resources/img/minus.gif' border='0'>";
                    }
                }
            }
        </script>  
        <%
            HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

            String strUserID = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String channelName = (String) objDataHashMap.get(SOPInterfaceKey.PAGE_HEADER);
            String strChannelId = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
            Utility.logger.debug("Channel id issssssssssssssssss" + strChannelId);
            String createAction = "";
            //if(strChannelId.equals("1"))
            createAction = SOPInterfaceKey.ACTION_CREATE_NEW_REQUEST;
            //else
            //createAction = SOPInterfaceKey.ACTION_CREATE_NEW_REQUEST_FOR_FRANCHISE;

            Vector vecRequestList = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
            Vector vecRequestStatusList = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
            DCMDto dcmDto = (DCMDto) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
            Vector warehouseVec = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3);

            String searchRequestId = "";
            String searchDcmName = "";
            String searchRequestCreateDateFrom = "*";
            String searchRequestCreateDateTo = "*";
            String searchRequestStatus = "";
            String searchWarehouseId = "";

            //if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CODE)){searchRequestCode = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CODE);}
            if (objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID)) {
                searchRequestId = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID);
            }
            if (objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME)) {
                searchDcmName = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME);
            }
            if (objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM)) {
                searchRequestCreateDateFrom = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM);
            }
            if (objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO)) {
                searchRequestCreateDateTo = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO);
            }
            if (objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS)) {
                searchRequestStatus = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS);
            }
            if (objDataHashMap.containsKey(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID)) {
                searchWarehouseId = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
            }

        %>
    <CENTER>
        <H2> Requests List For <%=channelName%></H2>
    </CENTER>
    <form name='SOPform' id='SOPform' action='' method='post'>
        <%
            out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_ACTION + "\""
                    + " value=\"" + "\">");

            out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_USER_ID + "\""
                    + " value=\"" + strUserID + "\">");

            out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID + "\""
                    + " value=\"" + strChannelId + "\">");

            out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_REQUEST_ID + "\""
                    + " value=\"" + "\">");

            out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CODE + "\""
                    + " value=\"" + "\">");

            out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME + "\""
                    + " value=\"" + "\">");

            out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CREATION_DATE + "\""
                    + " value=\"" + "\">");

            out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_REQUEST_STATUS + "\""
                    + " value=\"" + "\">");
        %>  
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            <tr class=TableHeader>
                <td align='left' colspan=6>Search</td>
            </tr>
            <TR class=TableTextNote>
                <td align=middle>Request ID</td>
                <td align=middle><input type='text' name='<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID%>' id='<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID%>'></td>
                <td align=middle>DCM Name</td>
                <td align=middle>
                    <%
                        out.println("<select name='" + SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME + "' id='" + SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME + "' onchange=\"document.SOPform." + SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME + ".value = document.SOPform." + SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME + ".options[document.SOPform." + SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME + ".selectedIndex].text;\"> ");
                        out.println("<option value=''></option>");
                        for (int index = 0; index < dcmDto.getDcmModelsSize(); index++) {
                            DCMModel model = dcmDto.getDcm(index);
                            int dcmID = model.getDcmId();
                            String dcmName = model.getDcmName();
                            out.println("<option value='" + dcmID + "'>" + dcmName + "</option>");
                        }
                        out.println("</select>");
                    %>
                </td>
            </tr>
            <TR class=TableTextNote>
                <TD align=middle>Warehouse</TD>
                <td align=middle>
                    <select name='<%=SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID%>'>
                        <option value=''></option>
                        <%
                            for (int m = 0; m < warehouseVec.size(); m++) {
                                WarehouseModel warehouseModel = (WarehouseModel) warehouseVec.get(m);
                                String warehouseIdX = warehouseModel.getWarehouseId();
                                String warehouseNameX = warehouseModel.getWarehouseName();
                        %>
                        <option value='<%=warehouseIdX%>'><%=warehouseNameX%></option>    
                        <%
                            }
                        %>
                    </select>
                </td>
                <TD align=middle>Status</TD>
                <td align=middle>
                    <select name='<%=SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS%>' id='<%=SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS%>'>
                        <option value=''></option>
                        <%
                            for (int k = 0; k < vecRequestStatusList.size(); k++) {
                                RequestStatusModel requestStatusModelX = (RequestStatusModel) vecRequestStatusList.get(k);
                                String requestStatusIdX = requestStatusModelX.getRequestStatusId();
                                String requestStatusNameX = requestStatusModelX.getRequestStatusName();
                        %>
                        <option value='<%=requestStatusIdX%>'><%=requestStatusNameX%></option>    
                        <%
                            }
                        %>
                    </select>
                </td>
            </TR>
            <tr class=TableTextNote>  
                <td align=middle>Creation Date</td>
                <td align=middle>From
                    <Script>drawCalender('<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM%>',"*");</script></td>
                <td align=middle colspan=2>To
                    <Script>drawCalender('<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO%>',"*");</script></td>
            </tr>
            <tr>
                <td align='center' colspan=6>
                    <%
                        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.SOPform." + InterfaceKey.HASHMAP_KEY_ACTION + ".value = '" + SOPInterfaceKey.ACTION_SEARCH_REQUEST + "';"
                                + "SOPform.submit();\">");

                        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','','*','*','','');\">");
                    %>
                </td>
            </tr>
            <table> 

                <br><br>
                <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
                    <%
                        if (vecRequestList.size() != 0) {
                    %>
                    <tr class=TableHeader>
                        <td>Request ID</td>
                        <td>DCM Code</td>
                        <td>DCM Name</td>
                        <td>Creation Date</td>
                        <td>Request Status</td>
                        <td>Request Details</td>
                        <td>Sales Request</td>
                    </tr> 
                    <%        }
                        for (int i = 0; i < vecRequestList.size(); i++) {
                            RequestModel requestModel = (RequestModel) vecRequestList.get(i);
                            String requestCode = requestModel.getRequestCode();
                            String requestId = requestModel.getRequestId();
                            String dcmId = requestModel.getDcmId();
                            String dcmName = requestModel.getDcmName();
                            String dcmCode = requestModel.getDcmCode();
                            String schemaId = requestModel.getSchemaId();
                            String creationDate = requestModel.getCreationDate();
                            creationDate = creationDate.substring(0, 10);
                            String lastRequestStatusLogId = requestModel.getLastRequestStatusLogId();
                            String requestStatusId = requestModel.getRequestStatusId();
                            String requestStatusName = requestModel.getRequestStatusName();
                    %>
                    <tr class=<%=InterfaceKey.STYLE[i % 2]%>>
                        <td><%=requestId%></td>
                        <td><%=dcmCode%></td>
                        <td><%=dcmName%></td>
                        <td><%=creationDate%></td>
                        <td>
                            <%
                                if (requestStatusId.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE) == 0 || requestStatusId.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_REJECTED) == 0) {
                            %>
                            <input type='hidden' name='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_ID%>_<%=requestId%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_ID%>_<%=requestId%>' value='<%=dcmId%>'>
                            <input type='hidden' name='<%=SOPInterfaceKey.INPUT_HIDDEN_OLD_REQUEST_STATUS%>_<%=requestId%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_OLD_REQUEST_STATUS%>_<%=requestId%>' value='<%=requestStatusId%>'>
                            <select name='<%=SOPInterfaceKey.INPUT_SELECT_REQUEST_STATUS%>_<%=requestId%>' id='<%=SOPInterfaceKey.INPUT_SELECT_REQUEST_STATUS%>_<%=requestId%>'>
                                <%
                                    for (int j = 0; j < vecRequestStatusList.size(); j++) {
                                        RequestStatusModel requestStatusModel = (RequestStatusModel) vecRequestStatusList.get(j);
                                        String requestStatusIdX = requestStatusModel.getRequestStatusId();
                                        String requestStatusNameX = requestStatusModel.getRequestStatusName();
                                        String requestStatusSelected = "";
                                        if (requestStatusIdX.compareTo(requestStatusId) == 0) {
                                            requestStatusSelected = "selected";
                                        }
                                        //if((requestStatusId.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE)==0 && (requestStatusIdX.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE)==0 || requestStatusIdX.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_REJECTED)==0 || requestStatusIdX.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_DELETED)==0 ))||
                                        //    (requestStatusId.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_REJECTED)==0 && (requestStatusIdX.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE)==0 || requestStatusIdX.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_REJECTED)==0 || requestStatusIdX.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_DELETED)==0)))
                                        if (requestStatusIdX.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE) == 0 || requestStatusIdX.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_REJECTED) == 0 || requestStatusIdX.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_DELETED) == 0) {
                                %>
                                <option value='<%=requestStatusIdX%>' <%=requestStatusSelected%>><%=requestStatusNameX%></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                            <%
                                } else {
                                    out.println(requestStatusName);
                                }
                            %>
                        </td>
                        <td>
                            <%
                                out.print("<INPUT class=button type='button' value=\" Request Details \" name=\"viewrequestdetails\" id=\"viewrequestdetails\" onclick=\"document.SOPform." + InterfaceKey.HASHMAP_KEY_ACTION + ".value = '" + SOPInterfaceKey.ACTION_VIEW_REQUEST_DETAILS + "';"
                                        + "document.SOPform." + SOPInterfaceKey.INPUT_HIDDEN_REQUEST_ID + ".value = '" + requestId + "';"
                                        + "document.SOPform." + SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CODE + ".value = '" + requestCode + "';"
                                        + "document.SOPform." + SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME + ".value = '" + dcmName + "';"
                                        + "document.SOPform." + SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CREATION_DATE + ".value = '" + creationDate + "';"
                                        + "document.SOPform." + SOPInterfaceKey.INPUT_HIDDEN_REQUEST_STATUS + ".value = '" + requestStatusName + "';"
                                        + "SOPform.submit();\">");
                            %>                  
                        </td>

                        <td>
                            <%
                                out.print("<INPUT class=button type='button' value=\" Sales Request \" name=\"printPaymentSlip\" id=\"printPaymentSlip\" onclick=\"document.SOPform." + InterfaceKey.HASHMAP_KEY_ACTION + ".value = '" + SOPInterfaceKey.ACTION_PRINT_REQUEST_PAYMENT_SLIP + "';"
                                        + "document.SOPform." + SOPInterfaceKey.INPUT_HIDDEN_REQUEST_ID + ".value = '" + requestId + "';"
                                        + "document.SOPform." + SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CODE + ".value = '" + requestCode + "';"
                                        + "document.SOPform." + SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME + ".value = '" + dcmName + "';"
                                        + "document.SOPform." + SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CREATION_DATE + ".value = '" + creationDate + "';"
                                        + "document.SOPform." + SOPInterfaceKey.INPUT_HIDDEN_REQUEST_STATUS + ".value = '" + requestStatusName + "';"
                                        + "SOPform.submit();\">");
                            %>                  
                        </td> 
                    </tr>  
                    <%
                        }
                    %>
                </table>
                <br><br>
                <center>
                    <%
                        out.print("<INPUT class=button type='button' value=\" Create New Request \" name=\"newrequest\" id=\"newrequest\" onclick=\"document.SOPform." + InterfaceKey.HASHMAP_KEY_ACTION + ".value = '" + createAction + "';"
                                + "SOPform.submit();\">");

                        if (vecRequestList.size() != 0) {
                            out.print("<INPUT class=button type='button' value=\" Update Request Status \" name=\"updaterequest\" id=\"updaterequest\" onclick=\"document.SOPform." + InterfaceKey.HASHMAP_KEY_ACTION + ".value = '" + SOPInterfaceKey.ACTION_UPDATE_REQUEST_STATUS + "';"
                                    + "checkBeforeSubmit();\">");
                        }
                    %>
                </center>

                <%
                    if (objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ACTION)) {
                        String strAction = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
                        if (strAction.compareTo(SOPInterfaceKey.ACTION_SEARCH_REQUEST) == 0) {
                            out.println("<script>setSearchValues('" + searchRequestId + "','" + searchDcmName + "','" + searchRequestCreateDateFrom + "','" + searchRequestCreateDateTo + "','" + searchRequestStatus + "','" + searchWarehouseId + "');</script>");
                        }
                    }
                %>
                </body>
                </html>
