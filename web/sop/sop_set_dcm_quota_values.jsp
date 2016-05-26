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
  function checkBeforeSubmit()
  {
    var reason = document.SOPform.<%=SOPInterfaceKey.INPUT_TEXTAREA_DCM_QUOTA_CHANGE_REASON%>.value 
    if(reason == "")
    {
     alert("Change reason can't be empty"); 
    }
    else
    {
      var inputs = document.getElementsByTagName("input");
      for(var i=0;i<inputs.length;i++)
      {
        var needeInput = inputs[i].name.indexOf('<%=SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_VALUE%>');
        if(needeInput == 0)
        {
          if(inputs[i].value == "")
          {
            inputs[i].value = "0";
          }
          else
          {
            if(!checknumber(inputs[i].value))
            {
              alert("Quota must be a number.");
              return;
            }
          }
        }
      }
      SOPform.submit();
    }
  }

  function checknumber(text)
  {
    var x=text
    var anum=/(^\d+$)|(^\d+\.\d+$)/
    if (anum.test(x))
    testresult=true
    else
    {
    testresult=false
    }
    return (testresult)
  }
  </script>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  String pageHeader = "";
  String nextAction ="";
  if (strChannelId.equals("1"))
  {
    pageHeader = "DCM Quota";
    nextAction = SOPInterfaceKey.ACTION_SELECT_DCM_QUOTA_VALUES;
  }
  else
  {
    pageHeader = "Franchise Quota";
    nextAction = SOPInterfaceKey.ACTION_SELECT_FRANCHISE_QUOTA_VALUES;
  }
  Utility.logger.debug("Channel id issssssssssssssssss" + strChannelId);
  String strDcmID = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID); 

  DCMQuotaModel dcmQuotaSettingModel = (DCMQuotaModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  Vector vecDcmQuotaValues = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

  DCMModel dcmModel = (DCMModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
  String strDcmName = dcmModel.getDcmName();
  String strDcmCode = dcmModel.getDcmCode();
  
  //String dcmId = "";
  String dcmQuotaSettingId = "";
  int validDays = 1;
  int recalculateDays = 1;
  int numberOfPeriods = 1;
  String strDcmQuotaAction = "insert";
  if(dcmQuotaSettingModel != null)
  {
    //dcmId = dcmQuotaModel.getDcmId();
    dcmQuotaSettingId = dcmQuotaSettingModel.getDcmQuotaSettingId();
    if(dcmQuotaSettingId != null)
    {
      validDays = dcmQuotaSettingModel.getValidDays();
      recalculateDays = dcmQuotaSettingModel.getRecalculateDays();
      numberOfPeriods = recalculateDays/validDays;
    } 
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

  


  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
     String errMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
     out.println("<script>alert('"+errMsg+"');");
     out.println("document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';");
     out.println("SOPform.submit();</script>");
  }                  
%>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td colspan=3>DCM Name</td>
        <td colspan=3>DCM Code</td>
      </tr>
      <tr>
        <td colspan=3><%=strDcmName%></td>
        <td colspan=3><%=strDcmCode%></td>
      </tr>
    </table>
    <br><br>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
<%
  Calendar currentDate = Calendar.getInstance();
  
  int curYear = currentDate.getTime().getYear()+1900;
  int curMonth = currentDate.getTime().getMonth()+1;
  int curDay = currentDate.getTime().getDate();

  String curDate = curYear+"/"+curMonth+"/"+curDay;
  
  if(vecDcmQuotaValues.size() == 0)
  {
    for(int i=0;i<numberOfPeriods;i++)
    {
      %>
      <tr class=TableTextNote>
        <td colspan=2>DCM Quota : </td>
        <td colspan=2><input type='text' name='<%=SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_VALUE%>_<%=i%>' id='<%=SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_VALUE%>_<%=i%>'></td>

        <td colspan=2>Valid From : </td>
        <td colspan=2><%=curDate%>
        <input type='hidden' name='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_VALID_FROM%>_<%=i%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_VALID_FROM%>_<%=i%>' value='<%=curDate%>'>
        </td>
<%
  currentDate.add(Calendar.DAY_OF_WEEK,validDays);

  curYear = currentDate.getTime().getYear()+1900;
  curMonth = currentDate.getTime().getMonth()+1;
  curDay = currentDate.getTime().getDate();

  curDate = curYear+"/"+curMonth+"/"+curDay;
%>
        <td colspan=2>Valid To : </td>
        <td colspan=2><%=curDate%>
        <input type='hidden' name='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_VALID_TO%>_<%=i%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_VALID_TO%>_<%=i%>' value='<%=curDate%>'>
        </td>
      </tr>
      <%
      if(i==numberOfPeriods-1)
      {
      currentDate.add(Calendar.DAY_OF_WEEK,1);

      curYear = currentDate.getTime().getYear()+1900;
      curMonth = currentDate.getTime().getMonth()+1;
      curDay = currentDate.getTime().getDate();

      curDate = curYear+"/"+curMonth+"/"+curDay;
      %>
        <input type='hidden' name='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_RECALCULATE_DAY%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_RECALCULATE_DAY%>' value='<%=curDate%>'>
      <%  
      }
    }
  }
  else
  {
  strDcmQuotaAction = "update";
    for(int j =0;j<vecDcmQuotaValues.size();j++)
    {
      DCMQuotaModel dcmQuotaValuesModel = (DCMQuotaModel)vecDcmQuotaValues.get(j);
      String dcmQuotaId = dcmQuotaValuesModel.getDcmQuotaId();
      String dcmQuota = dcmQuotaValuesModel.getDcmQuota();
      Date validFrom = dcmQuotaValuesModel.getValidFrom();
      Date validTo = dcmQuotaValuesModel.getValidTo();
      %>
      <tr class=TableTextNote>
        <td colspan=2>DCM Quota : </td>
        <td colspan=2><input type='text' name='<%=SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_VALUE%>_<%=dcmQuotaId%>' id='<%=SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_VALUE%>_<%=dcmQuotaId%>' value="<%=dcmQuota%>"></td>

        <td colspan=2>Valid From : </td>
        <td colspan=2><%=validFrom%>
        <input type='hidden' name='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_VALID_FROM%>_<%=dcmQuotaId%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_VALID_FROM%>_<%=dcmQuotaId%>' value='<%=validFrom%>'>
        </td>
        
        <td colspan=2>Valid To : </td>
        <td colspan=2><%=validTo%>
        <input type='hidden' name='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_VALID_TO%>_<%=dcmQuotaId%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_VALID_TO%>_<%=dcmQuotaId%>' value='<%=validTo%>'>
        </td>
      </tr>
      <%
      out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_VALUE_OLD+"_"+dcmQuotaId+"\""+
                  " value=\""+dcmQuota+"\">");
    }
  }
%>
      <tr class=TableTextNote>
        <td colspan=2>Change Reason : </td>
        <td colspan=10><textarea name='<%=SOPInterfaceKey.INPUT_TEXTAREA_DCM_QUOTA_CHANGE_REASON%>' id='<%=SOPInterfaceKey.INPUT_TEXTAREA_DCM_QUOTA_CHANGE_REASON%>' cols=75></textarea></td>
      </tr>    
    </table> 

<%
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.DCM_QUOTA_ACTION+"\""+
                  " value=\""+strDcmQuotaAction+"\">");  
%>

    <br><br>
    <center>
<%
      out.print("<INPUT class=button type='button' value=\" Update \" name=\"update\" id=\"update\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_UPDATE_DCM_QUOTA_VALUE+"';"+
                  "checkBeforeSubmit();\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");

%>                  
   </center>
  </body>
</html>
