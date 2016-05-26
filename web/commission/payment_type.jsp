<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.payment.*"
         import="com.mobinil.sds.core.system.payment.model.*"
         import="com.mobinil.sds.core.system.payment.dto.*"%>

<%
    String appName = request.getContextPath();
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    <SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
    <link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
    <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
    <script type="text/javascript" src="../resources/js/jquery_tablesorter_pack.js"></script>
    <link rel="stylesheet" href="../resources/css/themes/blue/style.css" type="text/css"/>
    

    

  </head>
  <body>
  <script language="javascript">
        $(document).ready(function(){$("#groupListTable").tablesorter(); });

  </script>
    <CENTER>
      <H2> Payment Type List</H2>
    </CENTER>
  

    <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formReportList" id="formReportList" method="post">&nbsp;

    <%
      HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);     
      String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
      System.out.println("user id from jsp payment"+strUserID);
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
      out.println("<input type=\"hidden\" name=\""+PaymentInterfaceKey.PARAM_LOAD_PAYMENT_TYPE_ID+"\""+
                  " value=\""+"\">"); 

       out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");                    

                 

      out.println("<script>");      
      out.println("function newPaymentType() {");
      out.println("document.formReportList."+InterfaceKey.HASHMAP_KEY_ACTION+
                    ".value=\""+PaymentInterfaceKey.ACTION_NEW_PAYMENT_TYPE+"\";");
      out.println("formReportList.submit();}");

      out.println("function savePaymentTypeStatus() {");
      out.println("document.formReportList."+InterfaceKey.HASHMAP_KEY_ACTION+".value=\""+PaymentInterfaceKey.ACTION_SAVE_PAYMENT_TYPE_STATUS+"\";");        
      out.println("formReportList.submit();}");

      out.println("function loadPaymentType(paymentTypeId) {");
      out.println("document.formReportList."+InterfaceKey.HASHMAP_KEY_ACTION+
                    ".value=\""+PaymentInterfaceKey.ACTION_NEW_PAYMENT_TYPE+"\";");
      out.println("document.formReportList."+PaymentInterfaceKey.PARAM_LOAD_PAYMENT_TYPE_ID+
                    ".value=paymentTypeId;");        
      out.println("formReportList.submit();}");
      
      out.println("</script>");




    %>

<%
showPaymentList((HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT) , out, request);
%>
          </td>
        </tr>
      </table>
     
    </form>
   </body>
</html>

<%!
public void showPaymentList(HashMap objDataHashMap , JspWriter out, HttpServletRequest request)
{
    String appName = request.getContextPath();
try{
  if (objDataHashMap == null)
  {
  
   return;
   }
  
  Vector vecPaymentTypeList = (Vector) objDataHashMap.get (PaymentInterfaceKey.VEC_PAYMENT_TYPE) ;
  
  if (vecPaymentTypeList ==null)
  {
    return;
  }
  //Utility.logger.debug ( "number of Reports ------> " +  vecGroupList.size() ) ;
  int paymentTypeListSize = vecPaymentTypeList.size();
  out.println("<TABLE class=\"tablesorter\" id=\"groupListTable\">");
    out.println("<thead>");
  out.println("<TR>");
out.println("<th ><font size=2>Payment Type</font></a></th><th ><font size=2>Payment Method</font></a></th><th><font size=2>Status</font></a></th><th><font size=2>Edit</font></a></th></TR></thead><tbody>");

  for (int i=0; i<paymentTypeListSize; i++)
  {
PaymentTypeDTO currPaymentType = (PaymentTypeDTO)vecPaymentTypeList.elementAt(i);
Integer typeId = currPaymentType.getTypeId();
Integer methodId = currPaymentType.getMethodTypeId();
String methodName = currPaymentType.getMethodTypeName();
Integer statusTypeId = currPaymentType.getTypeStatusId();
    String tdStyle =InterfaceKey.STYLE[i%2];   
    out.println("<TR class=\""+InterfaceKey.STYLE[i%2]+"\">");
    out.println("<td width=\"50%\">"+currPaymentType.getTypeName());
    out.println("</td>");    
    out.println("<td align=center width=\"20%\">"+methodName);
    out.println("</td>");

    Vector paymentTypeStatusVec = (Vector) objDataHashMap.get(PaymentInterfaceKey.VEC_PAYMENT_TYPE_STATUS);
        out.println("<td align=center width=\"10%\">");
    out.println("<select id=selectPaymentType"+typeId.intValue()+" name=selectPaymentType"+typeId.intValue()+" >");

          for(int k= 0; k<paymentTypeStatusVec.size(); k++)
          {
            PaymentTypeStatusDTO ptsdto = (PaymentTypeStatusDTO) paymentTypeStatusVec.get(k); 
            
            String optionselected = "";
            int intPaymentTypeStatusId = ptsdto.getStatusId().intValue();
            String paymentTypeStatusId = ptsdto.getStatusId().intValue()+"";
            String paymentTypeStatusName = ptsdto.getStatusName();
            if (statusTypeId.intValue()== intPaymentTypeStatusId){
              optionselected = "selected";
            }
            out.println("<option value="+paymentTypeStatusId+" "+optionselected+">"+paymentTypeStatusName+"</option>");

          }
          out.println("</select>");
          
          out.println("</td>");
    out.println("<td align=center width=\"10%\">");
    out.println("<input class=button type=\"button\" name=\"edit\" value=\" Edit \" onclick=\"loadPaymentType("+typeId.intValue()+")\"");
    out.println(">");
    out.println("</td>");

    out.println("</tr>");
    

  }
      out.println("</tbody>");
  out.println("</TABLE>");

  out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=0>");
  out.println("<TR><td align=middle>");
           out.println("<INPUT class=button type=button onclick=\"newPaymentType();\" value=\"New Payment Type\">");
           out.println(" <INPUT class=button type=button onclick=\"savePaymentTypeStatus();\" value=\"Save Payment Type Status\">");
  out.println("</TR></td></table><br>");


  }
  catch(Exception e)
  {
  e.printStackTrace();
  }
}
%>