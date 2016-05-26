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
//String real_path = request.getRealPath("/cam/camMemosFiles/");
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    String formName = "memo_mgt_form";
    String pageHeader = "DCM Details";

    HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String userID = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    String action = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
    if (action.equals(MemoInterfaceKey.ACTION_VIEW_GEN_DCM_DETAILS) || action.equals(MemoInterfaceKey.ACTION_SEARCH_GEN_DCM_DETAILS)) {
        pageHeader = "Gen DCM Details";
    }


    String dcmMode = (String) dataHashMap.get(PaymentInterfaceKey.CONTROL_DCM_MODE);

%>
<script language="JavaScript">
    function submitSearch( )
    {
	
        if (document.getElementById("<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>").value == "" &&
            document.getElementById("<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>").value == ""&&
            document.getElementById("<%=PaymentInterfaceKey.CONTROL_STK_NUMBER%>").value == "")
        {
            alert("there is no criteria");
            return ;
        }
        else 
        {
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_SEARCH_DCM_DETAILS%>";
            document.<%=formName%>.submit();
        }
    }

    function submitSearchGen( )
    {

        if (document.getElementById("<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>").value == "" &&
            document.getElementById("<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>").value == ""&&
            document.getElementById("<%=PaymentInterfaceKey.CONTROL_STK_NUMBER%>").value == "")
        {
            alert("there is no criteria");
            return ;
        }
        else 
        {
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_SEARCH_GEN_DCM_DETAILS%>";
            document.<%=formName%>.submit();
        }
    }
    function DCMStatusNotExist()
    {
        alert("This DCM Has No Status For Payment In The Accounting Module"); 
    }
    function DCMNotExist()
    {
        alert("There Is No DCM BY These Criteria");
    }
    function submitAdd()
    {
        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_INSERT_NEW_DCM_PAYMENT_STATUS%>";
        document.<%=formName%>.submit();
    }
    function submitExportExcel(dcmCode)
    {

        document.<%=formName%>.<%=PaymentInterfaceKey.CONTROL_HIDDEN_DCM_CODE%>.value=dcmCode;
        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_EXPORT_DCM_DETAILS_EXCEL%>";
        document.<%=formName%>.submit();
	
    }

    function submitGenExportExcel(dcmCode)
    {

        document.<%=formName%>.<%=PaymentInterfaceKey.CONTROL_HIDDEN_DCM_CODE%>.value=dcmCode;
        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_EXPORT_GEN_DCM_DETAILS_EXCEL%>";
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
        <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
        <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
        <input type="hidden" name="<%=PaymentInterfaceKey.CONTROL_HIDDEN_DCM_MODE%>" value="<%=dcmMode%>">
        <input type="hidden" name="<%=PaymentInterfaceKey.CONTROL_HIDDEN_DCM_CODE%>" value="-1">

        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
                <td colspan="4" align="center">Search</td>
            </TR> 
            <tr>
                <td>DCM Name</td>
                <td><input type="text" name="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>" id="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>"></td>
                <td>DCM Code</td>
                <td><input type="text" name="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>" id="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>"></td>
            </tr>
            <tr>
                <td>STK Number</td>
                <td><input type="text" name="<%=PaymentInterfaceKey.CONTROL_STK_NUMBER%>" id="<%=PaymentInterfaceKey.CONTROL_STK_NUMBER%>"></td>
            </tr>

            <%if (action.equals(MemoInterfaceKey.ACTION_VIEW_DCM_DETAILS) || action.equals(MemoInterfaceKey.ACTION_SEARCH_DCM_DETAILS)) {
                    System.out.println("hellooo");
            %>
            <tr>
                <td colspan="4" align="center"><input class="button" type="button" value="  Search  " onclick="javascript:submitSearch()"></td>
            </tr>
            <%} else if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_GEN_DCM_DETAILS) || action.equals(MemoInterfaceKey.ACTION_SEARCH_GEN_DCM_DETAILS)) {%>
            <tr>
                <td colspan="4" align="center"><input class="button" type="button" value="  Search  " onclick="javascript:submitSearchGen()"></td>
            </tr>
            <%}%>

        </table>
        <br><br>
        <%
            if (action.equals(MemoInterfaceKey.ACTION_SEARCH_DCM_DETAILS)) {
                //the results from search
                //the status - reason part
                Vector dcmStatusModel = (Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_DCM_DETAILS_STATUS);

                //the payments not in any memo part
                Vector dcmPayments = (Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_CLOSED_PAYMENTS);
                //all the memos includes the dcm
                Vector dcmMemos = (Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_ALL_MEMOS);
                //the deductions made on this dcm
                Vector dcmDedudtions = (Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_DCM_DEDUCTIONS);
                //for the payment status history
                Vector dcmStatusHistory = (Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_PAYMENT_STAUTS_HISTORY);

                //for the memo history
                Vector memoHistory = (Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_HISTORY);



        %>
        <%if (dcmStatusModel.size() != 0) {
                PaymentScmStatusModel dcmModel = (PaymentScmStatusModel) dcmStatusModel.get(0);
                if (dcmModel.getStatus() == null) {
                    Vector paymentStatusCases = (Vector) dataHashMap.get(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES);
                    Vector statusReason = (Vector) dataHashMap.get(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON);

        %>
        <script>
            DCMStatusNotExist();
        </script>

        <%
        } else {%>
        <%if (dcmStatusModel != null || dcmStatusModel.size() != 0) {
        %>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
                <td colspan="8" align="center">DCM Status</td>
            </TR>
            <TR class="TableHeader">
                <td>DCM Name</td>
                <td>DCM Code</td>
                <td>DCM Email</td>
                <td>Channel</td>
                <td>Status</td>
                <td>Reason</td>
                <td>STK Number</td>
                <td>STK Status</td>
            </tr>
            <%if (dcmStatusModel.size() != 0) {
                    //PaymentScmStatusModel dcmModel=(PaymentScmStatusModel)dcmStatusModel.get(0);
            %>
            <tr>
                <td><%=dcmModel.getDcmName()%></td>
                <td><%=dcmModel.getDcmCode()%></td>
                <td><%=dcmModel.getEmail()%></td>
                <td><%=dcmModel.getChannel()%></td>
                <td><%=dcmModel.getStatus()%></td>
                <%if (dcmModel.getReason() != null && !dcmModel.getReason().equals("ELIGIBLE")) {%>
                <td><%=dcmModel.getReason()%></td>
                <%} else {%>
                <td></td>
                <%}%>
                <td><%=dcmModel.getStkNumber()%></td>
                <td><%=dcmModel.getStkNumberStatus()%></td>

            </tr>
            <%}%>
        </TABLE>
        <%}%>
        <br>

        <%if (dcmPayments != null || dcmPayments.size() != 0) {
        %>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
                <td colspan="2" align="center">DCM Payments</td>
            </TR>
            <TR class="TableHeader">
                <td>Payment Name</td>
                <td>Commission value</td>
            </tr>
            <%for (int i = 0; i < dcmPayments.size(); i++) {
                    MemoMembersModel payments = (MemoMembersModel) dcmPayments.get(i);%>
            <tr>

                <td><%=payments.getPaymetName()%></td>
                <td><%=payments.getScmCommissionValue()%></td>
            </tr>
            <%}%>
        </TABLE>
        <% }%>
        <br>

        <%if (dcmMemos != null || dcmMemos.size() != 0) {
        %>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
                <td colspan="9" align="center">DCM Memos</td>
            </TR>
            <TR class="TableHeader">
                <td>Memo Name</td>
                <td>Memo Type</td>
                <td>Memo State</td>
                <td>Payment Names</td>
                <td>Commission Value</td>
                <td>Deduction Value</td>
                <td>Creation Date</td>
                <td>Payment Date</td>
                <td>Issue Date</td>
                <td>Finance Issue Date</td>
            </tr>
            <%for (int i = 0; i < dcmMemos.size(); i++) {
                    MemoModel memo = (MemoModel) dcmMemos.get(i);%>
            <tr>

                <td><%=memo.getName()%></td>
                <td><%=memo.getType()%></td>
                <td><%=memo.getState()%></td>
                <td><%
                    Vector PaymentNames = memo.getPaymentNames();
                    for (int j = 0; j < PaymentNames.size(); j++) {
                        MemoMembersModel paymentName = (MemoMembersModel) PaymentNames.get(j);

                    %>
                    <%=paymentName.getPaymetName() + ","%>

                    <%}%>
                </td>
                <td><%=memo.getCommissionValue()%></td>
                <td><%=memo.getDeductionValue()%></td>
                <td><%=memo.getStartDate()%></td>
                <td><%=memo.getPaymentDate()%></td>
                <td><%=memo.getIssueDate()%></td>
                <td><%=memo.getFinanceIssueDate()%></td>
            </tr>
            <%}%>
        </TABLE>
        <% }%>

        <br>



        <%
            if (dcmDedudtions != null || dcmDedudtions.size() != 0) {

        %>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
                <td colspan="6" align="center">DCM Deductions</td>
            </TR>
            <TR class="TableHeader">
                <td>Deduction value</td>
                <td>Reason</td>
                <td>Status</td>
                <td>Deduction Date</td>
                <td>Deduction Type</td>
                <td>Remaining Value</td>
            </tr>
            <%for (int i = 0; i < dcmDedudtions.size(); i++) {
                    DcmDeductionsModel dcmDeduction = (DcmDeductionsModel) dcmDedudtions.get(i);
            %>
            <tr>
                <td><%=dcmDeduction.getDeductionValue()%></td>
                <td><%=dcmDeduction.getReasonName()%></td>
                <td><%=dcmDeduction.getStatusName()%></td>
                <td><%=dcmDeduction.getDeductionDate()%></td>
                <% if (dcmDeduction.getDeductionType() != null) {%>
                <td><%=dcmDeduction.getDeductionType()%></td>
                <%} else {%>
                <td></td>
                <%}%>
                <td><%=dcmDeduction.getRemainingValue()%></td>

            </tr>
            <%}%>
        </table>

        <%}//for the dcm deductions table  %>
        <br>
        <%//for the payment status history
            if (dcmStatusHistory != null || dcmStatusHistory.size() != 0) {

        %>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
                <td colspan="6" align="center">DCM Payment Status History</td>
            </TR>
            <TR class="TableHeader">
                <td>Action</td>
                <td>Reason</td>
                <td>Date</td>
                <td>User</td>
            </tr>
            <%for (int i = 0; i < dcmStatusHistory.size(); i++) {
                    DcmStatusHistoryModel dcmHistory = (DcmStatusHistoryModel) dcmStatusHistory.get(i);

            %>
            <tr>
                <td><%=dcmHistory.getActionName()%></td>
                <td><%=dcmHistory.getReason()%></td>
                <td><%=dcmHistory.getDeductionDate()%></td>
                <td><%=dcmHistory.getUserName()%></td>
            </tr>
            <%}%>
        </table>
        <%}//for the dcm status history
        %>
        <br>
        <%
            //for the memo history
            if (memoHistory != null || memoHistory.size() != 0) {%>

        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
                <td colspan="6" align="center">DCM Memo Insertion and Deletion History</td>
            </TR>
            <TR class="TableHeader">
                <td>Memo Name</td>
                <td>Action</td>
                <td>Reason</td>
                <td>Date</td>
                <td>User</td>
            </tr>
            <%for (int i = 0; i < memoHistory.size(); i++) {
                    DcmMemoHistoryModel dcmMemoHistory = (DcmMemoHistoryModel) memoHistory.get(i);
            %>
            <tr>
                <td><%=dcmMemoHistory.getMemoName()%></td>
                <td><%=dcmMemoHistory.getActionName()%></td>
                <td><%=dcmMemoHistory.getReason()%></td>
                <td><%=dcmMemoHistory.getActionDate()%></td>
                <td><%=dcmMemoHistory.getUserName()%></td>
            </tr>
            <%}%>
        </table>

        <%}%>
        <%}%>
        <br>
        <table style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="0">
            <tr>
                <td colspan="7" align="center"><input class="button" type="button" value="  Export As Excel  " onclick="javascript:submitExportExcel('<%=dcmModel.getDcmCode()%>')"></td>
            </tr>
        </table>
        <%} else {
        %>
        <%//there is no dcm %>
        <script>
            DCMNotExist();
        </script>
        <%}%>





        <%
        } else if (action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_GEN_DCM_DETAILS))// for the search action
        {
            Vector dcmStatusModel = (Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_DCM_DETAILS_STATUS);

            //the deductions made on this dcm
            Vector dcmDedudtions = (Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_DCM_DEDUCTIONS);
            //for the payment status history
            Vector dcmStatusHistory = (Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_PAYMENT_STAUTS_HISTORY);

            //for the memo history
            Vector memoHistory = (Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_HISTORY);

        %>
        <%if (dcmStatusModel.size() != 0) {
                PaymentScmStatusModel dcmModel = (PaymentScmStatusModel) dcmStatusModel.get(0);
                if (dcmModel.getStatus() == null) {
                    Vector paymentStatusCases = (Vector) dataHashMap.get(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES);
                    Vector statusReason = (Vector) dataHashMap.get(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON);

        %>
        <script>
            DCMStatusNotExist();
        </script>

        <%} else {%>

        <%if (dcmStatusModel != null || dcmStatusModel.size() != 0) {
        %>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
                <td colspan="8" align="center">DCM Status</td>
            </TR>
            <TR class="TableHeader">
                <td>DCM Name</td>
                <td>DCM Code</td>
                <td>DCM Email</td>
                <td>Channel</td>
                <td>Status</td>
                <td>Reason</td>
                <td>STK Number</td>
                <td>STK Status</td>
            </tr>
            <%if (dcmStatusModel.size() != 0) {
                    //PaymentScmStatusModel dcmModel=(PaymentScmStatusModel)dcmStatusModel.get(0);
            %>
            <tr>
                <td><%=dcmModel.getDcmName()%></td>
                <td><%=dcmModel.getDcmCode()%></td>
                <td><%=dcmModel.getEmail()%></td>
                <td><%=dcmModel.getChannel()%></td>
                <td><%=dcmModel.getStatus()%></td>
                <%if (dcmModel.getReason() != null && !dcmModel.getReason().equals("ELIGIBLE")) {%>
                <td><%=dcmModel.getReason()%></td>
                <%} else {%>
                <td></td>
                <%}%>
                <td><%=dcmModel.getStkNumber()%></td>
                <td><%=dcmModel.getStkNumberStatus()%></td>
            </tr>
            <%}%>
        </TABLE>
        <%}%>
        <br>

        <%
            if (dcmDedudtions != null || dcmDedudtions.size() != 0) {

        %>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
                <td colspan="6" align="center">DCM Deductions</td>
            </TR>
            <TR class="TableHeader">
                <td>Deduction value</td>
                <td>Reason</td>
                <td>Status</td>
                <td>Deduction Date</td>
                <td>Deduction Type</td>
                <td>Remaining Value</td>
            </tr>
            <%for (int i = 0; i < dcmDedudtions.size(); i++) {
                    DcmDeductionsModel dcmDeduction = (DcmDeductionsModel) dcmDedudtions.get(i);
            %>
            <tr>
                <td><%=dcmDeduction.getDeductionValue()%></td>
                <td><%=dcmDeduction.getReasonName()%></td>
                <td><%=dcmDeduction.getStatusName()%></td>
                <td><%=dcmDeduction.getDeductionDate()%></td>
                <% if (dcmDeduction.getDeductionType() != null) {%>
                <td><%=dcmDeduction.getDeductionType()%></td>
                <%} else {%>
                <td></td>
                <%}%>
                <td><%=dcmDeduction.getRemainingValue()%></td>

            </tr>
            <%}%>
        </table>

        <%}//for the dcm deductions table  %>
        <br>
        <%//for the payment status history
            if (dcmStatusHistory != null || dcmStatusHistory.size() != 0) {

        %>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
                <td colspan="6" align="center">DCM Payment Status History</td>
            </TR>
            <TR class="TableHeader">
                <td>Action</td>
                <td>Reason</td>
                <td>Date</td>
                <td>User</td>
            </tr>
            <%for (int i = 0; i < dcmStatusHistory.size(); i++) {
                    DcmStatusHistoryModel dcmHistory = (DcmStatusHistoryModel) dcmStatusHistory.get(i);
                    System.out.println("status history payment name: " + dcmHistory.getPaymentName());
            %>
            <tr>
                <td><%=dcmHistory.getActionName()%></td>
                <td><%=dcmHistory.getReason()%></td>
                <td><%=dcmHistory.getDeductionDate()%></td>
                <td><%=dcmHistory.getUserName()%></td>
            </tr>
            <%}%>
        </table>
        <%}//for the dcm status history
        %>
        <br>

        <%
            //for the memo history
            if (memoHistory != null || memoHistory.size() != 0) {%>

        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
                <td colspan="6" align="center">DCM Memo Insertion and Deletion History</td>
            </TR>
            <TR class="TableHeader">
                <td>Memo Name</td>
                <td>Action</td>
                <td>Reason</td>
                <td>Date</td>
                <td>User</td>
            </tr>
            <%for (int i = 0; i < memoHistory.size(); i++) {
                    DcmMemoHistoryModel dcmMemoHistory = (DcmMemoHistoryModel) memoHistory.get(i);
            %>
            <tr>
                <td><%=dcmMemoHistory.getMemoName()%></td>
                <td><%=dcmMemoHistory.getActionName()%></td>
                <td><%=dcmMemoHistory.getReason()%></td>
                <td><%=dcmMemoHistory.getActionDate()%></td>
                <td><%=dcmMemoHistory.getUserName()%></td>
            </tr>
            <%}%>
        </table>
        <%}%>
        <%}%>
        <br>
        <table style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="0">
            <tr>
                <td colspan="7" align="center"><input class="button" type="button" value="  Export As Excel  " onclick="javascript:submitGenExportExcel('<%=dcmModel.getDcmCode()%>')"></td>
            </tr>
        </table>
        <%	} else {%>
        <%//there is no dcm %>
        <script>
            DCMNotExist();
        </script>
        <%}%>


        <%}%>

    </form>
</body>
</html>