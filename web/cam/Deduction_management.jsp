<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import=" com.mobinil.sds.web.interfaces.cam.*"
         import=" com.mobinil.sds.core.system.cam.deduction.model.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
         import ="com.mobinil.sds.core.system.cam.common.model.*"
         import ="com.mobinil.sds.core.system.cam.payment.model.*"
         %>
<%
    String appName = request.getContextPath();
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    String form_name = "deduction_mgt_form";
    String page_header = "Deduction List";
//get data hashmap specidifed b4 in kpihandler

    HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String action_view_or_mgmt = (String) dataHashMap.get(DeductionInterfaceKey.ACTION_VIEW_DEDUCTION_FOR_VIEW);

    Vector all_deduciton = (Vector) dataHashMap.get(DeductionInterfaceKey.VECTOR_DEDUCTION);
    HashMap all_deduction_details = (HashMap) dataHashMap.get(DeductionInterfaceKey.HASHMAP_DEDUCTION_MAKER_ACTIONS_DETAILS);
    Vector all_deduction_reason = (Vector) dataHashMap.get(DeductionInterfaceKey.VECTOR_DEDUCTION_REASON);
    Vector all_deduction_status = (Vector) dataHashMap.get(DeductionInterfaceKey.VECTOR_DEDUCTION_STATUS);
    Vector all_pay_group_type = (Vector) dataHashMap.get(DeductionInterfaceKey.VECTOR_PAY_GROUP_TYPE);

    String display_hidden = "display:none";
    String search_action = DeductionInterfaceKey.ACTION_VIEW_DEDUCTION_FOR_VIEW;
    if (action_view_or_mgmt == null) {
        display_hidden = "";
        search_action = DeductionInterfaceKey.ACTION_VIEW_DEDUCTION;
    }

%>
<script language="JavaScript">
 
    function submitEdit(rowId)
    {
        document.<%=form_name%>.<%=DeductionInterfaceKey.CONTROL_DEDUCTION_ID%>.value=rowId;
        document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=DeductionInterfaceKey.ACTION_EDIT_DEDUCTION%>";
        document.<%=form_name%>.submit();
    }
    function submitUpdateStatuses()
    {
        document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=DeductionInterfaceKey.ACTION_UDPATE_DEDUCTION_STATUSES%>";
        document.<%=form_name%>.submit();
    }

    function submitSearch()
    {
	 
        document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=search_action%>";
        document.<%=form_name%>.submit();
    }

    function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value='"+argValue+"' class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(<%=form_name%>."+argOrder+",'mm/dd/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }



    function toggle(item1)
    {
        divs=document.getElementsByTagName("DIV");
        for (i=0;i<divs.length;i++) 
        {
            if(divs[i].id != item1 && divs[i].id != 'listofusers')
            {
                divs[i].style.display="none";
            }
        }
        obj=document.getElementById(item1);
        if (obj!=null)
        {
            visible = obj.style.display!="none";
            if (visible) {
                obj.style.display="none";
            } 
            else {
                obj.style.display="";
            }
        }
    }


</script>

<%@page import="com.mobinil.sds.core.utilities.Utility"%><html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
    </head>
    <body>
    <center><h2> <%=page_header%></h2></center>

    <%

    %>
    <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=form_name%>" method="post">
        <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
        <input type="hidden" name="<%=DeductionInterfaceKey.CONTROL_DEDUCTION_ID%>" value="-1">   

        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            <TR class=TableHeader>
                <td>Date From</td>
                <td>
                    <script>
                        drawCalender('<%=DeductionInterfaceKey.CONTROL_SEARCH_DATE_FROM%>','');
                    </script>

                </td>
                <td>Date To</td>
                <td>
                    <script>
                        drawCalender('<%=DeductionInterfaceKey.CONTROL_SEARCH_DATE_TO%>','');
                    </script>

                </td>
                <td></td><td></td>
            </TR>
            <TR class=TableHeader>
                <TD>Payment type group</TD>
                <TD>
                    <%
                        if (all_pay_group_type != null && all_pay_group_type.size() > 0) {%>
                    <select name="<%=DeductionInterfaceKey.CONTROL_SELECT_SEARCH_PAY_GROUP_TYPE%>" id="<%=DeductionInterfaceKey.CONTROL_SELECT_SEARCH_PAY_GROUP_TYPE%>">
                        <option>
                            <%

                                for (int j = 0; j < all_pay_group_type.size(); j++) {
                                    PaymentTypeGroupModel type = (PaymentTypeGroupModel) all_pay_group_type.get(j);

                            %>
                        <option   value="<%=type.getGroupId()%>"><%=type.getGroupName()%></option>
                        <%}%>
                    </select>
                    <%}%>
                </TD>
                <TD>Status</TD>
                <TD>
                    <%
                        if (all_deduction_status != null && all_deduction_status.size() > 0) {%>
                    <select  name="<%=DeductionInterfaceKey.CONTROL_SELECT_SEARCH_STATUS%>" id="<%=DeductionInterfaceKey.CONTROL_SELECT_SEARCH_STATUS%>">
                        <option>
                            <%

                                for (int j = 0; j < all_deduction_status.size(); j++) {
                                    StatusModel status = (StatusModel) all_deduction_status.get(j);

                            %>
                        <option   value="<%=status.getStatus_id()%>"><%=status.getStatus_name()%></option>
                        <%}%>
                    </select>
                    <%}%>
                </TD>
                <TD>Reason</TD>
                <td>
                    <%
                        if (all_deduction_reason != null && all_deduction_reason.size() > 0) {%>
                    <select name="<%=DeductionInterfaceKey.CONTROL_SELECT_SEARCH_REASON%>" id="<%=DeductionInterfaceKey.CONTROL_SELECT_SEARCH_REASON%>">
                        <option>
                            <%

                                for (int j = 0; j < all_deduction_reason.size(); j++) {
                                    ReasonModel reason = (ReasonModel) all_deduction_reason.get(j);

                            %>
                        <option  value="<%=reason.getReason_id()%>"><%=reason.getReason_name()%></option>
                        <%}%>
                    </select>
                    <%}%>
                </td>
            </TR>
            </table>
            <table  cellspacing="2" cellpadding="1" border="0" width="100%">
            <tr>
            <td align="center"><input class="button" type="button" value="  Search  " onclick="javascript:submitSearch()"></td>
             </tr>
             <table>
        
        <%
            if (all_deduciton != null && all_deduciton.size() > 0) {
        %>
        <div name="listofusers" id="listofusers" style="display:none">
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
                <TR class=TableHeader>
                    <td>Value</td>
                    <td>DCM name</td>
                    <td>DCM Code</td>
                    <td>Payment type group</td>
                    <td>Creation date</td>
                    <td>Reason</td>
                    <td>Status</td>          
                    <td style="<%=display_hidden%>" >Edit</td>
                </TR>

                <%
                    for (int i = 0; i < all_deduciton.size(); i++) {
                        DeductionModel item = (DeductionModel) all_deduciton.get(i);
                        boolean included = false;

                        String ded_date = Utility.getFormattedDate(item.getDeduction_date());
                %>
                <TR class=TableTextNote>
                    <td><%=item.getDeduction_value()%></td>
                    <td><a href="#divname<%=item.getDeduction_id()%>" onclick="javascript:toggle('<%=item.getDeduction_id()%>');"><%=item.getDcm_name()%></a></td>
                    <td><%=item.getDcm_code()%></td>
                    <td><%=item.getPayment_group_type_name()%></td>
                    <td><%=ded_date%></td>
                    <td><%=item.getReason_name()%></td>
                    <td>
                        <%
                            if (all_deduction_status != null && all_deduction_status.size() > 0) {%>
                        <select disabled="disabled" name="<%=DeductionInterfaceKey.CONTROL_SELECT_STATUS + item.getDeduction_id()%>" id="<%=DeductionInterfaceKey.CONTROL_SELECT_STATUS + item.getDeduction_id()%>">
                            <%
                                String status_selected = "";
                                for (int j = 0; j < all_deduction_status.size(); j++) {
                                    StatusModel status = (StatusModel) all_deduction_status.get(j);
                                    if (status.getStatus_name().equalsIgnoreCase(item.getStatus_name())) {
                                        status_selected = "selected";
                                    } else {
                                        status_selected = "";
                                    }
                            %>
                            <option <%=status_selected%> value="<%=status.getStatus_id()%>"><%=status.getStatus_name()%></option>
                            <%}%>
                        </select>
                        <%}%>
                    </td>
                    <td style="<%=display_hidden%>" ><input class="button" type="button" value="Edit" onclick="submitEdit(<%=item.getDeduction_id()%>)"></td>

                </TR>
                <%}%>
            </TABLE>
        </div>
        <%

            for (int i = 0; i < all_deduciton.size(); i++) {
                DeductionModel item = (DeductionModel) all_deduciton.get(i);
                Vector ded_details = null;
                if (all_deduction_details != null) {
                    ded_details = (Vector) all_deduction_details.get(item.getDeduction_id() + "");
                }
                if (ded_details != null) {
        %>
        <a name="divname<%=item.getDeduction_id()%>" id="divname<%=item.getDeduction_id()%>">
            <div style="DISPLAY: none" id="<%=item.getDeduction_id()%>" name="<%=item.getDeduction_id()%>">
                <table style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
                    <TR class=TableHeader>
                        <td>Maker</td>
                        <td>Memo</td>
                        <td>Payment</td>
                        <td>Deleted Value</td>
                        <td>Action</td>
                        <td>Action Date</td>
                    </TR>
                    <% for (int j = 0; j < ded_details.size(); j++) {
                            DeductionDetailsModel dedtails_model = (DeductionDetailsModel) ded_details.get(j);

                    %>
                    <tr class=TableTextNote>
                        <td><%=dedtails_model.getMaker_name()%></td>
                        <td><%=dedtails_model.getMemo_name()%></td>
                        <td><%=dedtails_model.getPayment_name()%></td>
                        <td><%=dedtails_model.getDeleted_value()%></td>
                        <td><%=dedtails_model.getAction_name()%></td>
                        <td><%=Utility.getFormattedDate(dedtails_model.getAction_date())%></td>
                    </tr>
                    <%}%>
                </table>
            </div>
        </a>
        <% }
                }
            }%>
        <br>
        <table  cellspacing="2" cellpadding="1" border="0" width="100%">
            <tr>
                <%-- <td align="center">
                     <input class=button type="button" name="new" value="Update status"
                                onclick="submitUpdateStatuses();">
                 </td>
                --%>

            </tr>
        </table>
        <%
            if (all_deduciton != null && all_deduciton.size() > 0) {
        %>      
        <script>var obj = document.getElementById('listofusers');obj.style.display='';</script>
        <% }%>
    </form>
</body>
</html>
