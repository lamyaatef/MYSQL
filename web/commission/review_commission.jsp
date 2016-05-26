<%@page import="com.mobinil.mcss.commission.CommissionReviewModel"%>
<%@page import="com.mobinil.mcss.commission.CommissionIIIInterfaceKey"%>
<%@ page import="com.mobinil.sds.core.utilities.Utility"
         import="javax.servlet.*" import="javax.servlet.http.*"
         import="java.io.PrintWriter" import="java.io.IOException"
         import="java.util.*" import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.commission.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.core.system.commission.model.*"%>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

    <link href="../resources/css/Template2.css" rel="stylesheet"
          type="text/css" />
    <SCRIPT language=JavaScript src="../resources/js/calendar.js"
    type=text/javascript></SCRIPT>
    <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
    <script type="text/javascript"
    src="../resources/js/jquery_tablesorter_pack.js"></script>
    <SCRIPT language=JavaScript src="../resources/js/tree.js"
    type=text/javascript></SCRIPT>
    <link rel="stylesheet" href="../resources/css/themes/blue/style.css"
          type="text/css" />
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    <script language="javascript">



       
    
    </script>
    <script>
        function checkForSubmit()
        {	
            COMform.submit()
        }
    </script>
</head>

<body>


<center>
    <H2>Commission Review</H2>
</center>

<%

    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    Vector<CommissionReviewModel> DataVec = (Vector<CommissionReviewModel>) dataHashMap.get(CommissionIIIInterfaceKey.VECTOR_COMMISSION_REVIEW);


%>



<table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1  id="CommissionTable">

    <tr class=TableHeader>
        <td nowrap align=middle>Commission Name</td>
        <td nowrap align=middle>Category</td>
        <td nowrap align=middle>Start Date</td>
        <td nowrap align=middle>End Date</td>
        <td nowrap align=middle>Description</td>
        <td nowrap align=middle>Data View Name</td>
        <td nowrap align=middle>Data View SQL</td>
        <td nowrap align=middle>Creation Date</td>
        <td nowrap align=middle>Parameter</td>
        <td nowrap align=middle>Value</td>




    </tr>

    <%
        for (int i = 0; i < DataVec.size(); i++) {

    %>

    <tr>
        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
            <%=  DataVec.get(i).getCommissionName()%>
        </td>

        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
            <%=  DataVec.get(i).getCommissionTypeCategoryName()%>
        </td>

        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
            <%=  DataVec.get(i).getCommissionStartDate()%>
        </td>

        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
            <%=  DataVec.get(i).getCommissionEndDate()%>
        </td>
        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
            <%=  DataVec.get(i).getCommissionDescription()%>
        </td>

        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
            <%=  DataVec.get(i).getCommissionDataViewName()%>
        </td>

        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
            <%=  DataVec.get(i).getCommissionDataViewSQL()%>
        </td>

        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
            <%=  DataVec.get(i).getCommissionCreationDate()%>
        </td>
        
        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
            <%=  DataVec.get(i).getLabelText()%>
        </td>
        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
            <%=  DataVec.get(i).getValue()%>
        </td>
    </tr>

    <%}%>

</table>



<p>&nbsp;</p>

</form>
<p>&nbsp;</p>
</body>
</html>

