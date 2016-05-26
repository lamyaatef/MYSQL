<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page  import ="javax.servlet.*" 
          import="javax.servlet.http.*"
          import="java.io.PrintWriter"
          import="java.io.IOException"
          import="com.mobinil.sds.web.interfaces.gn.ua.UserAccountInterfaceKey"
          import="com.mobinil.sds.core.system.sop.schemas.dao.*"
          import="javax.ejb.SessionBean"
          import ="javax.ejb.SessionContext"
          import ="java.util.Vector"
          import=" java.util.HashMap"
          import=" java.lang.Object"
          import="java.sql.*"
          import="com.mobinil.sds.web.interfaces.InterfaceKey"
          import="com.mobinil.sds.web.interfaces.sop.SOPInterfaceKey"
          import="com.mobinil.sds.web.interfaces.sa.*"
          import="com.mobinil.sds.web.interfaces.gn.querybuilder.QueryBuilderInterfaceKey"
          import="com.mobinil.sds.core.utilities.*"
          import="com.mobinil.sds.core.system.sop.schemas.dao.*"
          import="com.mobinil.sds.core.system.sop.schemas.model.*"
          import="com.mobinil.sds.core.system.sop.requests.dao.*"
          import="com.mobinil.sds.core.system.sop.requests.model.*"
          import="com.mobinil.sds.core.system.sop.equations.dao.*"
          import="com.mobinil.sds.core.system.sop.equations.dto.*"
          import="com.mobinil.sds.core.system.sop.equations.model.*"
          import="com.mobinil.sds.core.system.sop.quota.dao.*"
          import="com.mobinil.sds.core.system.sop.quota.model.*"
          import="com.mobinil.sds.core.system.gn.dcm.dto.*"
          import="com.mobinil.sds.core.system.gn.dcm.dao.*"
          import="com.mobinil.sds.core.system.gn.dcm.model.*"
          import="com.mobinil.sds.core.system.sa.importdata.dao.*"
          import="com.mobinil.sds.core.system.sa.users.dao.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
<%
    Connection con = Utility.getConnection();
    String dcmCode = request.getParameter("dcm");
    String requestCode = request.getParameter("request");
    if((dcmCode==null||dcmCode.compareTo("")==0) && (requestCode==null||requestCode.compareTo("")==0))
    {
  %>
  <script>alert("Please enter DCM code or request code in URL.")</script>
  <%
    }
    if(dcmCode==null)dcmCode = "";
    if(requestCode==null)requestCode="";
    Vector vecRequests = RequestDAO.getActiveOrInactiveRequestByDCMCode(con,dcmCode,requestCode);
    Vector vecRequestDetail = new Vector(); 
    String dcmName="";
    String requestCreationDate ="";
    String requestStatus ="";
    if(vecRequests.size()>0)
    {
      RequestModel requestModel = (RequestModel)vecRequests.get(0);
      String requestId = requestModel.getRequestId();
      vecRequestDetail = RequestDAO.getRequestDetails(con,requestId);
      dcmName = requestModel.getDcmName();
      dcmCode = requestModel.getDcmCode();
      requestCreationDate = requestModel.getCreationDate();
      requestCode = requestModel.getRequestCode();
      requestStatus = requestModel.getRequestStatusName();
    }
     else
    {
        dcmName = RequestDAO.getDCMName(con,dcmCode);
    }
     String appName = request.getContextPath();
%>

<center>
<TABLE border=0 cellSpacing=0 cellPadding=0>
              <tr>
                <td><IMG src="<%out.print(appName);%>/resources/img/sds_mobinil.bmp"></td>
                </tr>
                </table>
                </center>
                <br><br>
    <CENTER>
    <CENTER>
      <H2> Requests Detail </H2>
    </CENTER>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <tr class=TableHeader>
      <td>Request Code</td>
      <td>DCM Code</td>
      <td>DCM Name</td>
      <td>Request Creation Date</td>
      <td>Request Status</td>
    </tr>
    <%
      if(dcmCode.compareTo("")!=0)
      {
      %>
    <tr>
      <td><%=requestCode%></td>
      <td><%=dcmCode%></td>
      <td><%=dcmName%></td>
      <td><%=requestCreationDate%></td>
      <td><%=requestStatus%></td>
    </tr>
    <%
    }
    %>
    </table>
    <br><br>
     <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
           <tr class=TableHeader>
            <td>Product Name</td>
            <td>Price</td>
            <td>Amount</td>
            <td>Total</td>
          </tr>
  <%
    for(int i=0;i<vecRequestDetail.size();i++)
    {
      RequestDetailModel requestDetailModel = (RequestDetailModel)vecRequestDetail.get(i);
      String requestDetailId = requestDetailModel.getRequestDetailId();
      String requestId = requestDetailModel.getRequestId();
      String schemaProductId = requestDetailModel.getSchemaProductId();
      String minimumLimit = requestDetailModel.getMinimumLimit();
      String maximumLimit = requestDetailModel.getMaximumLimit();
      String productAmount = requestDetailModel.getProductAmount();
      String hasQuantity = requestDetailModel.getHasQuantity();
      String productNameEnglish = requestDetailModel.getProductNameEnglish();
      String productNameArabic = requestDetailModel.getProductNameArabic();
      String productPrice = requestDetailModel.getProductPrice();
      float intProductPrice = Float.parseFloat(productPrice);
      float intProductAmount = Float.parseFloat(productAmount);
      float total = intProductPrice * intProductAmount;
      if(productAmount.compareTo("0")!=0)
      {
      %>
<tr>
      <td width=25%><%=productNameEnglish%></td>
      <td width=25%><%=productPrice%></td>
      <td width=25%><%=productAmount%></td>
      <td width=25%><%=total%> L.E</td>
    </tr>
    <%
    }
    }%>
          </table>
          <br>

          <br><br>
    <center>
    <input type="button" class="button" value=" Print " onclick="window.print();">
    </center>
</body>
</html>