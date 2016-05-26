<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import=" com.mobinil.sds.web.interfaces.cam.*"
         import=" com.mobinil.sds.core.system.cam.accrual.model.*"
         import ="com.mobinil.sds.core.system.cam.accrual.dao.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
         import ="com.mobinil.sds.core.system.cam.payment.model.*"
         import="com.mobinil.sds.core.system.cam.memo.model.*" 


         %>
<%
    String appName = request.getContextPath();
    String realPath = request.getRealPath("/cam/camMemosFiles/");
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    String formName = "memo_mgt_form";
    String pageHeader = null;


    HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    //ArrayList memo_error_msg=(ArrayList)dataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_REPORT_ERROR_MSG );
    String userID = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    String action = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
    String msg = (String) dataHashMap.get(MemoInterfaceKey.CONTROL_ACCRUAL_MESSAGE);


    if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_READY_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_DELETE_READY_MEMO) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_READY_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_EDIT_READY_MEMO)) {
        pageHeader = "Ready Memos";
    } else if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_ISSUED_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_DELETE_ISSUED_MEMO) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_ISSUE_READY_MEMO) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_ISSUED_MEMOS)) {
        pageHeader = "Issued Memos";


    } else if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_EDIT_READY_MEMO)) {
        pageHeader = "Edit Memo Text";
    } else if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_SALES_APPROVED_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SALES_APPROVED_TO_AP) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SALES_APPROVED_TO_LOGESTIC) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SALES_APPROVED_TO_CREDIT_MODULE)) {
        pageHeader = "Sales Approved Memos ";
    } else if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_SALES_APPROVED_TO_AP) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_TO_AP)) {
        pageHeader = "Sales Approved To AP ";
    } else if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_SALES_APPROVED_TO_LOGESTIC) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_TO_LOGESTIC)) {
        pageHeader = "Sales Approved To Logestic ";
    }


    Vector memoTypes = (Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_TYPES);
    Vector paymentTypes = (Vector) dataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES);
    Vector channels = (Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_DCM_CHANNELS);
    Vector subChannel = (Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS);
    String paymentTypeId = (String) dataHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID);
    Vector paymentMethods = (Vector) dataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD);
    Vector memos = (Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_ALL_MEMOS);

    // System.out.println("sub Channel size:"+subChannel.size());


%>
<script language="JavaScript">
    <%
        String incoming_action = (String) dataHashMap.get(MemoInterfaceKey.CONTROL_INCOMING_ACITON);

        if (incoming_action != null) {
            String download_page_url = appName + "/cam/PopUpDownloadPage.jsp";
    %> 
        nw = window.open('<%=download_page_url%>','Warnings','width=400,height=400');
    <%}%>
        function submitSearch()
        {	
            if(document.getElementById("<%=MemoInterfaceKey.CONTROL_MEMO_NAME%>").value == ""&&document.getElementById("<%=MemoInterfaceKey.CONTROL_MEMO_ID%>").value == "" && document.getElementById("<%=MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID%>").value == "" && document.getElementById("<%=MemoInterfaceKey.CONTROL_SELECT_STATE_ID%>").value == ""
		&& document.getElementById("<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID%>").value == "" && document.getElementById("<%=MemoInterfaceKey.CONTROL_INSERTED_START_DATE%>").value == "Start_Date" &&
		document.getElementById("<%=MemoInterfaceKey.CONTROL_INSERTED_END_DATE%>").value == "End_Date")
            {
                alert("you have to select search criteria");
                return;
            }


    <%if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_READY_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_DELETE_READY_MEMO) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_READY_MEMOS)) {%>
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_SEARCH_READY_MEMOS%>";
            document.<%=formName%>.submit();
    <%}%>
    <%if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_ISSUED_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_DELETE_ISSUED_MEMO) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_ISSUE_READY_MEMO) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_ISSUED_MEMOS)) {%>
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_SEARCH_ISSUED_MEMOS%>";
            document.<%=formName%>.submit();
    <%}%>
    <% if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_SALES_APPROVAL) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVAL)) {%>
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVAL%>";
            document.<%=formName%>.submit();
    <%}%>
    <% if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_SALES_APPROVED_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_MEMOS)) {%>
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_MEMOS%>";
            document.<%=formName%>.submit();
    <%}%>
    <% if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_SALES_APPROVED_TO_AP) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_TO_AP)) {%>
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_TO_AP%>";
            document.<%=formName%>.submit();
    <%}%> 
        
    <% if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_SALES_APPROVED_TO_LOGESTIC) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_TO_LOGESTIC)) {%>
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_TO_LOGESTIC%>";
            document.<%=formName%>.submit();
    <%}%>
        }
        function toggleSpecific()
        {

            document.getElementById("1").style.display='';
            document.getElementById("2").style.display='';
            document.getElementById("3").style.display='none';
            document.getElementById("4").style.display='none';
            //document.getElementById("3").style.display='none';
            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID%>.value= "2";

        }

        function toggleCollection()
        {
            //document.getElementById("3").style.display='';
            document.getElementById("1").style.display='none';
            document.getElementById("2").style.display='none';
            document.getElementById("3").style.display='';
            document.getElementById("4").style.display='';
            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID%>.value= "1";
        }
        function toggleNone()
        {
            //document.getElementById("3").style.display='';
            document.getElementById("1").style.display='none';
            document.getElementById("2").style.display='none';
            document.getElementById("3").style.display='none';
            document.getElementById("4").style.display='none';
            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID%>.value= "-1";
        }
        function toggleChange()
        {
            if(document.getElementById("MemoType").value == "Specific")
		toggleSpecific();
            else if(document.getElementById("MemoType").value == "Collection")
		toggleCollection();
            else
		toggleNone();
		
        }
        function drawCalender(argOrder,argValue)
        {
            document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(memo_mgt_form."+argOrder+",'mm/dd/yyyy','Choose date')\">");
            document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
        }
        function submitViewMemo(memoId,memoName,stateName)
        {

            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_NAME%>.value = memoName;
            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME%>.value = stateName;

            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_MEMO%>";
            document.<%=formName%>.submit();
        }

        function submitIssue(memoId)
        {

            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_ISSUE_READY_MEMO%>";
            document.<%=formName%>.submit();
        }

        function submitViewPayments(memoId,memoName)
        {
            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_NAME%>.value = memoName;
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_MEMO_PAYMENT_NAMES_TYPES%>";
            document.<%=formName%>.submit();
        }

        function submitDelete(memoId)
        {
            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
    <%if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_READY_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_DELETE_READY_MEMO) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_READY_MEMOS)) {%>
	
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_DELETE_READY_MEMO%>";
            document.<%=formName%>.submit();
    <%}%>
    <%if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_ISSUED_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_DELETE_ISSUED_MEMO) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_ISSUE_READY_MEMO) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_ISSUED_MEMOS)) {%>
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_DELETE_ISSUED_MEMO%>";
            document.<%=formName%>.submit();
    <%}%>



        }
        function submitGetReportExcel(memoId,methodId)
        {
            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_PAYMENT_METHOD%>.value = methodId;
	
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_EXPORT_MEMO_TO_EXCEL%>";
            document.<%=formName%>.submit();
        }
        function submitFinanceIssue(memoId)
        {

            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_SALES_APPROVED%>";
            document.<%=formName%>.submit();
        }
        function submitApproveToAP(memoId)
        {

            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_SALES_APPROVED_TO_AP%>";
            document.<%=formName%>.submit();
        }
        function submitApproveToLogestic(memoId)
        {

            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_SALES_APPROVED_TO_LOGESTIC%>";
            document.<%=formName%>.submit();
        }
        function submitAddToCreditModule(memoId)
        {

            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_SALES_APPROVED_TO_CREDIT_MODULE%>";
            document.<%=formName%>.submit();
        }


        function submitGetReport(memoId)
        {
            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
	
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_GET_MEMO_REPORT%>";
            document.<%=formName%>.submit();
        }
        function showAccrualMessage(accrualMsg)
        {
            alert(accrualMsg);
        }
        function submitEditMemo(memoId)
        {
            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_EDIT_READY_MEMO%>";
            document.<%=formName%>.submit();
	
        }
        function submitViewEditMemo(memoId)
        {
            document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_EDIT_READY_MEMO%>";
            document.<%=formName%>.submit();
	
        }

</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL="STYLESHEET" TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language="JavaScript" src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
    </head>
    <body>

    <center><h2> <%=pageHeader%></h2></center>

    <%

    %>
    <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
        <input type="hidden" name="<%=MemoInterfaceKey.CONTROL_INCOMING_ACITON%>" value="<%=action%>">
        <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
        <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
        <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID%>" value="-1">
        <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>" value="-1">
        <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_NAME%>" value="-1">
        <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_COMMENT%>" value="comment">            
        <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME%>" value="-1">
        <input type="hidden" name="<%=MemoInterfaceKey.CONTROL_SELECT_STATE_ID%>" value="-1">
        <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_PAYMENT_METHOD%>" value="-1">
        <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_APPLIACTION_CONTEXT%>" value="<%= realPath%>">
        <%if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_EDIT_READY_MEMO)) {
                String memoId = (String) dataHashMap.get(MemoInterfaceKey.CONTROL_MEMO_ID);

                String text = (String) dataHashMap.get(MemoInterfaceKey.MEMO_COMMENT);
                String title = (String) dataHashMap.get(MemoInterfaceKey.MEMO_TITLE);

        %>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border=1>

            <tr class="TableHeader">
                <td>Memo Title</td>
            </tr>
            <tr>

                <td><input type="text" name="<%=MemoInterfaceKey.MEMO_TITLE%>" id="<%=MemoInterfaceKey.MEMO_TITLE%>" value="<%=title%>">
                </td>
            </tr>
            <tr class="TableHeader">
                <td>Memo Text</td>
            </tr>
            <tr>

                <td><textarea rows="10" cols="90%"  name="<%=MemoInterfaceKey.MEMO_COMMENT%>" id="<%=MemoInterfaceKey.MEMO_COMMENT%>" ><%=text%></textarea>
                </td>
            </tr>



            <tr>
                <td align="center"><input class="button" type="button" name="Update" value="Update" onclick="javascript:submitEditMemo('<%=memoId%>')" ></td>
            </tr>




        </TABLE>
        <%} else {%>

        <%
            if (msg != null) {
                if (msg.equals("DONE")) {%>
        <script>
            showAccrualMessage("The Issuing operation DONE \n The Accrual Value was Sufficient to the DCM Commisssions");
        </script>

        <%} else if (msg.equals("NOT DONE")) {%>
        <script>
            showAccrualMessage("The Issuing operation NOT DONE \n The Accrual Value was NOT Sufficient to the DCM Commisssions");
        </script>
        <%}
            }
        %>

        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            <TR class=TableHeader>
                <td colspan="4" align="center"><b>Search</b></td>
            </TR>

            <tr>
                <td>Memo Name</td><td><input type="text" name="<%=MemoInterfaceKey.CONTROL_MEMO_NAME%>" id="<%=MemoInterfaceKey.CONTROL_MEMO_NAME%>"></td>
                    <%if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_READY_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_DELETE_READY_MEMO) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_READY_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_EDIT_READY_MEMO)) {%>
                <td>Memo ID</td><td><input type="text" name="<%=MemoInterfaceKey.CONTROL_MEMO_ID%>" id="<%=MemoInterfaceKey.CONTROL_MEMO_ID%>"></td>
                    <%}%>
            </tr>
            <tr>       
                <td >Channel</td>
                <td >
                    <select name="<%=MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID%>" id="<%=MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID%>">
                        <option value=""></option>
                        <%
                            if (channels != null || channels.size() != 0) {
                                for (int i = 0; i < channels.size(); i++) {
                                    ChannelModel channel = (ChannelModel) channels.get(i);

                        %>
                        <option  value="<%=channel.getChannelId()%>"><%=channel.getChannelName()%></option>
                        <%}
                            }%> 
                    </select>  
                </td>

                <td>Sub Channel</td>
                <td>
                    <select name="<%=MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID%>" id="<%=MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID%>">
                        <option value=""></option>
                        <%
                            if (subChannel != null || subChannel.size() != 0) {
                                for (int i = 0; i < subChannel.size(); i++) {
                                    SubChannelModel subchannel = (SubChannelModel) subChannel.get(i);

                        %>
                        <option  value="<%=subchannel.getSubChannelId()%>"><%=subchannel.getCriteriaName()%></option>
                        <%}
                            }%> 
                    </select>
                </td>


            </tr>
            <tr>
                <td>From Date</td>
                <td>
                    <script>
                        drawCalender('<%=MemoInterfaceKey.CONTROL_INSERTED_START_DATE%>','Start_Date');
                    </script>
                </td>
                <td>To Date</td>
                <td>
                    <script>
                        drawCalender('<%=MemoInterfaceKey.CONTROL_INSERTED_END_DATE%>','End_Date');
                    </script>
                </td>
            </tr>

            <tr>
                <td>Memo Type</td>
                <td >
                    <select name="MemoType" id="MemoType" onchange="javaScript:toggleChange();">
                        <option value=" "></option>
                        <option value="Specific" >Specific</option>
                        <option value="Collection" >Collection</option>
                    </select>
                </td>

                <td id="1" style="display:none">Payment Type</td>
                <td id="2" style="display:none">
                    <select name="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID%>" id="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID%>">
                        <option value=""></option>
                        <%if (memoTypes != null || memoTypes.size() != 0) {
                                for (int i = 0; i < paymentTypes.size(); i++) {
                                    PaymentTypeModel type = (PaymentTypeModel) paymentTypes.get(i);

                        %>
                        <option  value="<%=type.getPaymentTypeId()%>"><%=type.getPaymentTypeName()%></option>
                        <%}
                            }%>
                    </select>
                </td>

                <td id="3" style="display:none">Payment Method</td>
                <td id="4" style="display:none">
                    <select name="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID%>" id="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID%>">
                        <option value=""></option>
                        <%if (memoTypes != null || memoTypes.size() != 0) {
                                for (int i = 0; i < paymentMethods.size(); i++) {
                                    PaymentTypeMethodModel type = (PaymentTypeMethodModel) paymentMethods.get(i);

                        %>
                        <option  value="<%=type.getPaymentTypeMethodId()%>"><%=type.getPaymentTypeMethodName()%></option>
                        <%}
                            }%>
                    </select>
                </td>

            </tr>                                                      
        </TABLE>

        <table  cellspacing="2" cellpadding="1" border="0" width="100%">
            <tr>
                <td align=center>
                    <input class=button type="button" name="search" value="Search"
                           onclick="javascript:submitSearch()">
                </td>


            </tr>
        </table>

        <br><br>
        <% if (memos.size() != 0 && memos != null) {%>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border=1>
            <tr class="TableHeader">
                <td>ID</td>
                <td>Name</td>
                <td>Description</td>
                <td>Creation Date</td>
                <td>Channel</td>
                <td>Type</td>
                <%if (!(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_SALES_APPROVED_MEMOS))) {
                        if (!(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_MEMOS))) {
                            if (!(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SALES_APPROVED_TO_AP))) {
                                if (!(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SALES_APPROVED_TO_CREDIT_MODULE))) {
                                    if (!(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SALES_APPROVED_TO_LOGESTIC))) {
                                        if (!(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_SALES_APPROVED_TO_AP))) {
                                            if (!(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_SALES_APPROVED_TO_LOGESTIC))) {
                                                if (!(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_TO_AP))) {
                                                    if (!(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_TO_LOGESTIC))) {
                %>
                <td>Delete</td>
                <td>Action</td>
                <%         }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }%>
                <%if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_SALES_APPROVED_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_MEMOS)) {

                %>
                <td>Approve To AP</td>
                <td>Approve To Logestic</td>
                <td>Add To Credit Advice</td>
                <%}%>
                <td>Export excel</td>
                <td>Export PDF Report</td> 
                <td>Get EToPop Excel</td>
                <%if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_READY_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_DELETE_READY_MEMO) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_READY_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_EDIT_READY_MEMO)) {%>
                <td>Edit</td>
                <td>View Payments</td>
                <%}%>


            </tr>
            <%


                for (int i = 0; i < memos.size(); i++) {
                    MemoModel memo = (MemoModel) memos.get(i);

            %>
            <tr>
                <td>
                    <%=memo.getId()%>
                </td>
                <td name="<%=MemoInterfaceKey.CONTROL_MEMO_ID%>" id="<%=MemoInterfaceKey.CONTROL_MEMO_ID%>">
                    <a href="javaScript:submitViewMemo('<%=memo.getId()%>','<%=memo.getName()%>','<%=memo.getState()%>')" ><%=memo.getName()%></a>
                </td>
                <%if (memo.getDesc() != null) {%>
                <td ><%=memo.getDesc()%></td>
                <%} else {%>
                <td></td>
                <%}%>
                <%if (memo.getStartDate() != null) {%>
                <td><%=memo.getStartDate()%></td>
                <%} else {%>
                <td></td>
                <%}%>

                <%if (memo.getChannel() != null) {%>
                <td><%=memo.getChannel()%></td>
                <%} else {%>
                <td></td>
                <%}%>

                <%if (memo.getType() != null) {%>
                <td><%=memo.getType()%></td>
                <%} else {%>
                <td></td>
                <%}%>


                <%if (memo.getState().equalsIgnoreCase("READY")) {%>
                <td><input class=button type="button" name="delete" value="delete" onclick="javascript:submitDelete(<%=memo.getId()%>)"></td>
                <td><input class=button type="button" name="issue" value="Issue" onclick="javascript:submitIssue('<%=memo.getId()%>')"></td>

                <%}%>
                <%if (memo.getState().equalsIgnoreCase("ISSUED")) {%>
                <td><input class=button type="button" name="delete" value="delete" onclick="javascript:submitDelete(<%=memo.getId()%>)"></td>
                <td><input class=button type="button" name="revise" value="Revise" onclick="javascript:submitFinanceIssue('<%=memo.getId()%>')"></td>             
                    <%}%>
                    <%if (memo.getState().equalsIgnoreCase("SALES APPROVED")) {%>
                <td><input class=button type="button" name="approve_to_ap" value="Approve TO AP" onclick="javascript:submitApproveToAP('<%=memo.getId()%>')"></td>             
                <td><input class=button type="button" name="approve_to_logestic" value="Approve TO Logestic" onclick="javascript:submitApproveToLogestic('<%=memo.getId()%>')"></td>
                <td><input class=button type="button" name="add_to_credit_module" value="Add TO Credit Module" onclick="javascript:submitAddToCreditModule('<%=memo.getId()%>')"></td>
                    <%}%>
                <td><input class=button type="button" name="view_memo_report" value="export excel" onclick="javascript:submitGetReportExcel('<%=memo.getId()%>','0')"></td>
                <td><input class=button type="button" name="view_memo_report" value="view memo report" onclick="javascript:submitGetReport('<%=memo.getId()%>')"></td>
                    <%System.out.println("memo id = " + memo.getId() + "  payment method= " + memo.getPaymentMethod());

                        if (memo.getPaymentMethod() != null && memo.getPaymentMethod().compareTo("FOC") == 0) {
                    %>
                <td><input class=button type="button" name="view_memo_report" value="export ETOPOP Memo" onclick="javascript:submitGetReportExcel('<%=memo.getId()%>','1')"></td>
                    <%} else {%>
                <td></td>
                <%}%>
                <%if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_READY_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_DELETE_READY_MEMO) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_READY_MEMOS) || action.equalsIgnoreCase(MemoInterfaceKey.ACTION_EDIT_READY_MEMO)) {%>
                <td><input class="button" type="button" name="Edit" value="Edit" onclick="javascript:submitViewEditMemo('<%=memo.getId()%>')" ></td>
                <td><input class=button type="button" name="viewmemopayments" value="View Payments" onclick="javascript:submitViewPayments('<%=memo.getId()%>','<%=memo.getName()%>')"></td>
                    <%}%>            
            </tr>
            <%}%> 
        </table>

        <%}
            }%>



    </form>
</body>
</html>
