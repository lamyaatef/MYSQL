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

         import="com.mobinil.sds.core.system.sop.quota.model.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
  <script>
   function validateInt(number)
   {
      return isInteger(number)
   }

  
   function isInteger (s)
   {
      var i;

      if (isEmpty(s))
      if (isInteger.arguments.length == 1) return 0;
      else return (isInteger.arguments[1] == true);

      for (i = 0; i < s.length; i++)
      {
         var c = s.charAt(i);

         if (!isDigit(c)) return false;
      }

      return true;
   }

   function isEmpty(s)
   {
      return ((s == null) || (s.length == 0))
   }

   function isDigit (c)
   {
      return ((c >= "0") && (c <= "9"))
   }


  
    function checkBeforeSubmit()
    {
      var validDays = parseInt(document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_VALID_DAYS%>.value)
      var recalculateDays = parseInt(document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_RECALCULATE_DAYS%>.value)
      var as = recalculateDays/validDays+"";
      if(validateInt(as) && recalculateDays>=validDays)
      { 
        SOPform.submit();
      }
      else
      {
         alert("recalculate days must be >= and dividable by valid days"); 
      }
    }
  </script>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  String pageHeader = "";
  if (strChannelId.equals("1"))
  pageHeader = "DCM Quota Setting";
  else
  pageHeader = "Franchise Quota Setting";
  Utility.logger.debug("Channel id issssssssssssssssss" + strChannelId);
  String strDcmID = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID);  

  DCMModel dcmModel = (DCMModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  String strDcmName = dcmModel.getDcmName();
  String strDcmCode = dcmModel.getDcmCode();
  
  DCMQuotaModel dcmQuotaModel = null;
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_COLLECTION))
  {
    dcmQuotaModel = (DCMQuotaModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION); 
  }
  
  //String dcmId = "";
  String dcmQuotaSettingId = "";
  int validDays = 0;
  int recalculateDays = 0;
  
  String strDcmQuotaAction = "insert";
  if(dcmQuotaModel != null)
  {
    //dcmId = dcmQuotaModel.getDcmId();
    dcmQuotaSettingId = dcmQuotaModel.getDcmQuotaSettingId();
    if(dcmQuotaSettingId != null)
    {
      strDcmQuotaAction = "update";
    }
    validDays = dcmQuotaModel.getValidDays();
    recalculateDays = dcmQuotaModel.getRecalculateDays();
  }
%>  
    <CENTER>
      <H2> <%=pageHeader%> </H2>
    </CENTER>

<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
                  " value=\""+strChannelId+"\">");

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_DCM_ID+"\""+
                  " value=\""+strDcmID+"\">");               

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.DCM_QUOTA_ACTION+"\""+
                  " value=\""+strDcmQuotaAction+"\">");             
%>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td colspan=2>DCM Name</td>
        <td colspan=2>DCM Code</td>
      </tr>
      <tr>
        <td colspan=2><%=strDcmName%></td>
        <td colspan=2><%=strDcmCode%></td>
      </tr>
    </table>
    <br><br>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      
      <tr class=TableTextNote>
        <td colspan=2>Validity Days : </td>
        <td colspan=7>
          <input type='text' name='<%=SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_VALID_DAYS%>' id='<%=SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_VALID_DAYS%>' value='<%=validDays%>'> 
        </td>
      </tr>
      <tr class=TableTextNote>
        <td colspan=2>Recalculate Days : </td>
        <td colspan=7>
          <input type='text' name='<%=SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_RECALCULATE_DAYS%>' id='<%=SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_RECALCULATE_DAYS%>' value='<%=recalculateDays%>'> 
        </td>
      </tr>
    </table>

    <br><br>
    <center>
<%
      out.print("<INPUT class=button type='button' value=\" Update \" name=\"update\" id=\"update\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_UPDATE_DCM_QUOTA_SETTING_VALUE+"';"+
                  "checkBeforeSubmit();\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");

%>                  
   </center>

   </form>   
  </body>
</html>
