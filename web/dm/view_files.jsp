  <%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dm.DMInterfaceKey"
         import = "com.mobinil.sds.core.system.dm.file.model.*"
       
%>

<%@page import="com.mobinil.sds.core.system.dataMigration.model.fileModel"%><html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <script type="text/javascript">
      function loadField(id)
      {
		
          document.DMform.fieldId.value=id;
      }
      </script>
    </head>
  <body>
  <form  name='DMform' id='DMform' action='' method='post'>  
<%
 
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);


    Vector files  = (Vector) objDataHashMap.get(DMInterfaceKey.VECTOR_FILES);

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
 " value=\""+"\">");
  
out.println("<input type=\"hidden\" name=fieldId value=\""+"\">");
  
  
  String hidden_action=(String) objDataHashMap.get(DMInterfaceKey.HIDDEN_ACTION);
  System.out.println("action issssss"+hidden_action);
  
  String nextAction="";
  String message=null;
  nextAction=DMInterfaceKey.ACTION_DELETE_FILE;
  
  String nextAction1="";
  nextAction1=DMInterfaceKey.ACTION_VIEW_ZIP_UPLOAD_STATSTICS;
 // out.print (hidden_action);
 
   String nextAction2="";
   nextAction2=DMInterfaceKey.ACTION_VIEW_AUTH_CALL_INVALID_DCM_CODE;
   
   String  nextAction3=DMInterfaceKey.ACTION_AUTH_CALL_IMPORT;
  
  
   
   
  if(hidden_action.equalsIgnoreCase("view_file"))
	  
  {
	 
  
  nextAction=DMInterfaceKey.ACTION_DELETE_FILE;
  message="Migrated File";
  
	  
	  
  }
 
  if(hidden_action.equalsIgnoreCase("view_cash"))
	  
  {
	 
	  
	  nextAction=DMInterfaceKey.ACTION_DELETE_CASH;
	 message="Payment Cash";
	  
	  
  }
  if(hidden_action.equalsIgnoreCase("view_master"))
	  
  {
	 
	  
	  nextAction=DMInterfaceKey.ACTION_DELETE_MASTER;
	  message="Payment Visa";
	  
	  
  }
  if(hidden_action.equalsIgnoreCase("view_dist"))
	  
  {
	 
	  
	  nextAction=DMInterfaceKey.ACTION_DELETE_DIST;
	  message="Distribution Complemantry";
	  
	  
  }
  if(hidden_action.equalsIgnoreCase("view_visa"))
	  
  {
		  
	  nextAction=DMInterfaceKey.ACTION_DELETE_VISA;
	   message="Payment Visa"; 
  } 
  
  
  
  if(hidden_action.equalsIgnoreCase("view_auth_call"))
	  
  {
		  

	  nextAction=DMInterfaceKey.ACTION_DELETE_AUTH_CALL;
	message="Auth_Call";
 }
  
  
  if(hidden_action.equalsIgnoreCase("view_pos_his"))
	  
  {
		  
	  nextAction=DMInterfaceKey.ACTION_DELETE_POS_HIS;
	   message="Payment History";
 }
  
  if(hidden_action.equalsIgnoreCase("view_pos_elg"))
	  
  {
		  
	  nextAction=DMInterfaceKey.ACTION_DELETE_POS_ELG;
	 message="POS Eligibility Check";
 }
  
  
  if(hidden_action.equalsIgnoreCase("view_pos_del"))
	  
  {
		  
	  nextAction=DMInterfaceKey.ACTION_DELETE_POS_DEL;
	   message="POS Delivery Check";
 }
  
  
  else
  {
	  message="Migrated File";
  }
  
%>  
    <CENTER>
      <H2><%=message%> </H2>
    </CENTER>

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR class=TableHeader>
        <td width="10%" nowrap align=middle>File Id</td>
        <td width="20%" nowrap align=middle>Year</td>
         <td width="20%" nowrap align=middle>Month</td>
         <td width="30%" nowrap align=middle>Status</td>
          <td width="20%" nowrap align=middle>Delete</td>
          <% if(nextAction.compareTo(DMInterfaceKey.ACTION_DELETE_FILE)==0){ %>
           <td width="20%" nowrap align=middle>View Statistics</td>
           <%} %>
           
           
                 <% if(nextAction.compareTo(DMInterfaceKey.ACTION_DELETE_AUTH_CALL)==0){ %>
           <td width="20%" nowrap align=middle>View Invalid DCM CODE</td>
            <td width="20%" nowrap align=middle>Append File</td>
           <%} %>

      </TR>
      <%
      
        String FileId="";
    	String Year="";
    	String Month="";
    	String Status="";
  
    	for (int i=0; i< files.size(); i++)
        {
        	fileModel model = (fileModel) files.get(i);            
        	FileId =  model.getFILE_ID();
        	Year=model.getYEAR();
        	Month=model.getMONTH();
        	Status=model.getSTATUS();
     
        	
      %>
      <TR class=TableTextNote>
        <td width="20%" nowrap align=middle><%=FileId%></td>
        <td width="40%" nowrap align=middle><%=Year%></td>
        <td width="40%" nowrap align=middle><%=Month%></td>
        <td width="40%" nowrap align=middle><%=Status%></td>
     
        <TD width="10%" noWrap align=middle>
        <%
        
        if (Status.equalsIgnoreCase("processing") || Status.equalsIgnoreCase("Deleted")){
        out.println("<INPUT class=button type=button  disabled  value= \"Delete\"  name =\"Delete \" > ");
 
       }
        
        else{
      out.println("<INPUT class=button type=button    value= \"Delete\"  name =\"Delete \"onclick=\"DMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+nextAction+"';"+
"loadField("+model.getFILE_ID()+");DMform.submit();\">"); 
           }
        %>
        </TD>
        
      <% if(nextAction.compareTo(DMInterfaceKey.ACTION_DELETE_FILE)==0) { %>
      <TD width="10%" noWrap align=middle>
              
        <%
        
        if (Status.equalsIgnoreCase("processing") || Status.equalsIgnoreCase("Deleted")){
        out.println("<INPUT class=button type=button  disabled  value= \"View Statistics\"  name =\"View Statistics \" > ");
      
        
        }
        
        else{
        	
        	 out.println("<INPUT class=button type=button    value= \"View Statistics \"  name =\"View Statistics \"    onclick=\"DMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction1+"';"+
        	         "loadField("+model.getFILE_ID()+");DMform.submit();\">"); 
           }
        %>
        </TD>
        
     <%
      }
      %>
      
   <% if(nextAction.compareTo(DMInterfaceKey.ACTION_DELETE_AUTH_CALL)==0) { %>
      <TD width="10%" noWrap align=middle>
              
        <%
        
        if (Status.equalsIgnoreCase("processing") || Status.equalsIgnoreCase("Deleted")){
        out.println("<INPUT class=button type=button  disabled  value= \"View Invalid DCM CODE\"  name =\"View Invalid DCM CODE \" > ");
        
        
        
        
      
        
        }
        
        else{
        	
        	 out.println("<INPUT class=button type=button    value= \"View Invalid DCM CODE \"  name =\"View Invalid DCM CODE \"    onclick=\"DMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction2+"';"+
        	         "loadField("+model.getFILE_ID()+");DMform.submit();\">"); 
        	 
       }
        %>
        </TD>
        
     <%
      }
      %>
      
      
      
      
      
      
         <% if(nextAction.compareTo(DMInterfaceKey.ACTION_DELETE_AUTH_CALL)==0) { %>
      <TD width="10%" noWrap align=middle>
              
        <%
        
        if (Status.equalsIgnoreCase("processing") || Status.equalsIgnoreCase("Deleted")){
        out.println("<INPUT class=button type=button  disabled  value= \"Append File\"  name =\"Append File \" > ");
        
        
        
        
      
        
        }
        
        else{
     
        	out.println("<INPUT class=button type=button    value= \"Append File\"  name =\"Append File \"    onclick=\"DMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction3+"';"+
        	         "loadField("+model.getFILE_ID()+");DMform.submit();\">"); 
        	 
        	 
        	 
           }
        %>
        </TD>
        
     <%
      }
      %>
      
      
      
      
      
      
      
  </TR>
       
      <%
      }
      %>
    </table>

   </form>  
  </body>
</html>