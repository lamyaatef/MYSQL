<%@ page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                   
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.ac.*"
              import="com.mobinil.sds.web.interfaces.dm.*"
              import = "com.mobinil.sds.core.system.dataMigration.model.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"              
              %>

<%
String appName = request.getContextPath();


    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    
    Vector problematicVec = (Vector) dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    Vector paymentLevelVec = (Vector) dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
   
    String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +DMInterfaceKey.ACTION_NOT_INTIALIZED_SERIALS_IMPORT_PROCESS+
                    "&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
                     +strUserID;
        
%>    

<script>

function submitForm()
{

  var year1= document.myform.<%out.print(DMInterfaceKey.CONTROL_SELECTED_YEAR);%>.value;
  var month1 = document.myform.<%out.print(DMInterfaceKey.CONTROL_SELECTED_MONTH);%>.value;
  var problematicLabelId = document.myform.<%out.print(DMInterfaceKey.CONTROL_HIDDEN_PROBLEMATIC_LABEL_ID);%>.value;
  var paymentLevelId = document.myform.<%out.print(DMInterfaceKey.CONTROL_HIDDEN_PAYMENT_LEVEL_ID);%>.value;

  var fileName = document.myform.myfile.value;
    if(fileName == "")
    {
      alert("File field can not be empty.");
      return;
    }
    fileName = fileName.substring(fileName.length - 4, fileName.length);
    fileName = fileName.toLowerCase();
    if(fileName != ".txt")
    {
      alert("Invalid input. Please specify a right Text file.");
      return;
    }
  
  
  document.myform.action = document.myform.action+'&'+'<%out.print(DMInterfaceKey.CONTROL_SELECTED_YEAR);%>='+year1;
  document.myform.action = document.myform.action+'&'+'<%out.print(DMInterfaceKey.CONTROL_SELECTED_MONTH);%>='+month1;
  document.myform.action = document.myform.action+'&'+'<%out.print(DMInterfaceKey.CONTROL_HIDDEN_PROBLEMATIC_LABEL_ID);%>='+problematicLabelId;
  document.myform.action = document.myform.action+'&'+'<%out.print(DMInterfaceKey.CONTROL_HIDDEN_PAYMENT_LEVEL_ID);%>='+paymentLevelId;
   
  document.myform.Submit.disabled = true;
  
  document.myform.submit();
  
}
</script>

<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <TITLE>Not Intialized Serials Import</TITLE>
      <center>
      <h2>Not Intialized Serials Import</h2>
      </center>
   </HEAD>
 <body onkeypress = "if(event.keyCode==13){myform.submit();}">
<form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">
          <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1 align="center">
                <tr class=TableHeader>
                    <td>
                  
                <font size="4" >  <b>Upload</b> </font> <br></td><td></td></tr>
                  
                <br>
                <tr>
                <td width="30%">Year:</td>
                <td>
                <select name="<%=DMInterfaceKey.CONTROL_SELECTED_YEAR%>" id="<%=DMInterfaceKey.CONTROL_SELECTED_YEAR %>">
                <option></option>
                <%for(int i=2000; i<2020;i++){ %>
                <option value="<%=i %>"><%=i %></option>
                <%} %>
                </select>
                </td>
                </tr>
                
                <tr>
                <td width="30%">Month:</td>
                <td>
                <select  name="<%=DMInterfaceKey.CONTROL_SELECTED_MONTH%>" id="<%=DMInterfaceKey.CONTROL_SELECTED_MONTH%>">
                <option></option>
                <%
                String [] months = {" " ,"Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sept", "Oct", "Nove", "Dec"};
                for(int i=1; i<months.length;i++){
                	%>
                <option value="<%=i%>"><%=months[i]%></option>
                <%} %>
                </select>
                    </td>
                </tr>
                <tr>
                <td width="30%">Problematic Label:</td>
                <td>
                <select  name="<%=DMInterfaceKey.CONTROL_HIDDEN_PROBLEMATIC_LABEL_ID%>" id="<%=DMInterfaceKey.CONTROL_HIDDEN_PROBLEMATIC_LABEL_ID%>">
                <option></option>
                <%
                
                for(int i=0; i<problematicVec.size();i++){
                	
                	ProblematicModel  problematicModel = (ProblematicModel)problematicVec.get(i);
                    String problematicId = problematicModel.getProblematicId();
                    String problematicName = problematicModel.getProblematicName();
                	
                	
                	%>
         <option value="<%=problematicId%>"><%=problematicName%></option>
                <%} %>
                </select>
                </td>
                </tr>
                <tr>
                <td width="30%">Payment Level:</td>
                <td>
                <select  name="<%=DMInterfaceKey.CONTROL_HIDDEN_PAYMENT_LEVEL_ID%>" id="<%=DMInterfaceKey.CONTROL_HIDDEN_PAYMENT_LEVEL_ID%>">
                <option></option>
                <%
                
                for(int j=0; j<paymentLevelVec.size();j++){
                	
                	PaymentLevelModel  paymentLevelModel = (PaymentLevelModel)paymentLevelVec.get(j);
                    String paymentLevelId = paymentLevelModel.getPaymentLevelId();
                    String paymentLevelName = paymentLevelModel.getPaymentLevelName();
                	
                	
                	%>
         <option value="<%=paymentLevelId%>"><%=paymentLevelName%></option>
                <%} %>
                </select>
                </td>
                </tr>
                <tr>
                <td>Upload:</td>
                <td  align="left">
                <input type="hidden" name="hiddenField">
    <input type="file" name="myfile">
    </td>
                </tr>
           
                  

    
</table>

<BR>
<BR>
<BR>
    <center> 
    
    <input class="button" type="button" name="Submit" value="Upload" onclick="submitForm();">
   
    </center>
    </form>
</HTML>
              