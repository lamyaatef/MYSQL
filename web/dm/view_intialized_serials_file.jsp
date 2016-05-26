<%@ page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                   
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.ac.*"
              import ="java.sql.Timestamp"
              import="com.mobinil.sds.web.interfaces.dm.*"
              import = "com.mobinil.sds.core.system.dataMigration.model.*"
              import = "com.mobinil.sds.core.system.dataMigration.DAO.DataMigrationDao"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"              
              %>
              
              <html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

	

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+strMsg+"');</script>");
  }

  Vector serialsFile = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  
  
%>
<center>
<H2> Not Intialized Serials File </H2>
    </CENTER>
<form name='DMform' id='DMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  
  
  out.println("<input type=\"hidden\" name=\""+DMInterfaceKey.INPUT_HIDDEN_NOT_INTIALIZED_FILE_ID+"\""+
          " value=\""+"\">");
               
%>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <tr class=TableHeader>
    <td align='center'>File Id</td>
     <td align='center'>Month</td>
       <td align='center'>Year</td>
       <td align='center'>Status</td>
       <td align='center'>User Id</td>
       <td align='center'>TimeStamp</td>
       <td align='center'>Payment Level</td>
       <td align='center'>Problematic Lavel</td>
       <td align='center'>Delete</td>
    </tr>
<%
  for(int i=0;i<serialsFile.size();i++)
  {
	NotIntializedSerialsModel notIntializedSerialsModel = (NotIntializedSerialsModel)serialsFile.get(i);
    String fileId = notIntializedSerialsModel.getFileId();
    String month = notIntializedSerialsModel.getMonth();
    String year = notIntializedSerialsModel.getYear();
    String status = notIntializedSerialsModel.getStatus();
    String userId = notIntializedSerialsModel.getUserId();
    Timestamp time_stamp = notIntializedSerialsModel.getTime_stamp();
    String paymentlevelName = notIntializedSerialsModel.getPaymentLevelName();
    if (paymentlevelName == null){
    	paymentlevelName = "";
    }
    String problematicName = notIntializedSerialsModel.getProblematicName();
    if (problematicName == null)
    {
    	problematicName = "";
    }
%>
<tr class=<%=InterfaceKey.STYLE[i%2]%>>
  <td align='center'><%=fileId%></td>
   <td align='center'><%=month%></td>
   <td align='center'><%=year%></td>
    <td align='center'><%=status%></td>       
     <td align='center'><%=userId%></td>  
     <td align='center'><%=time_stamp%></td>  
     <td align='center'><%=paymentlevelName%></td>
     <td align='center'><%=problematicName%></td>
     <td align='center'>
          <%
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.DMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DMInterfaceKey.ACTION_DELETE_INTIALIZED_SERIALS_FILE+"';"+
                  "document.DMform."+DMInterfaceKey.INPUT_HIDDEN_NOT_INTIALIZED_FILE_ID+".value = '"+fileId+"';"+
                  "DMform.submit();\">");
          %>
           </td>       
 </tr>
<%
  }
%>
</table>
 <br><br>
    
 </form>
  </body>
</html>
              