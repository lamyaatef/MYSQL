<%@page import="com.mobinil.sds.core.system.dataMigration.model.fileModel"%>
<%@page import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"%>
<%@page import="com.mobinil.sds.web.interfaces.* "%>

<%@page  import="java.util.*"%>
<%@page import="com.mobinil.sds.core.system.dataMigration.model.*"%>


<%@page import="com.mobinil.sds.web.interfaces.dm.DMInterfaceKey"%>







<%@page import="com.mobinil.sds.core.system.authenticationResult.model.AuthResSearchFileModel"%><html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <script type="text/javascript">
      function loadField(id)
      {
		
          document.AUTHform.fieldId.value=id;
      }


      function loadMonth(id)
      {
		
          document.AUTHform.month.value=id;
      }


      function loadYear(id)
      {
		
          document.AUTHform.year.value=id;
      }
      
      </script>
    </head>
  <body>
  <form  name='AUTHform' id='AUTHform' action='' method='post'>  
<%
 
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);


    Vector files  = (Vector) objDataHashMap.get(DMInterfaceKey.VECTOR_SEARCH_FILES);

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
 " value=\""+"\">");
  
out.println("<input type=\"hidden\" name=fieldId value=\""+"\">");
out.println("<input type=\"hidden\" name=month value=\""+"\">");
out.println("<input type=\"hidden\" name=year value=\""+"\">");
  
  
  //String hidden_action=(String) objDataHashMap.get(AuthResInterfaceKey.HIDDEN_ACTION);
  //System.out.println("action issssss"+hidden_action);
  
  String nextAction="";
  nextAction=DMInterfaceKey.ACTION_DELETE_PAY_HIS_SEARCH_FILE;
  
  String nextAction1="";
  nextAction1=DMInterfaceKey.ACTION_VIEW_DEL_CHK_SEARCH_FILE;
  
  
  String nextAction2="";
  nextAction2=DMInterfaceKey.ACTION_VIEW_PAY_HIS_SEARCH_FILE_DATA;
  
  String   nextAction3="";
nextAction3=DMInterfaceKey.ACTION_VIEW_PAY_HIS_SEARCH_FILE_INVALID_POS;
 // out.print (hidden_action);
 



  
%>  
    <CENTER>
      <H2>PAY HIS File </H2>
    </CENTER>

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR class=TableHeader>
        <td width="10%" nowrap align=middle>Search File Id</td>
        <td width="20%" nowrap align=middle>Year</td>
         <td width="20%" nowrap align=middle>Month</td>
         <td width="20%" nowrap align=middle>Description</td>
         <td width="30%" nowrap align=middle>Status</td>
          <td width="20%" nowrap align=middle>Delete</td>
         <td width="30%" nowrap align=middle>Export Invalid CODE</td>
         <td width="30%" nowrap align=middle>Export Search File Data</td>


      </TR>
      <%
      
        String Search_FileId="";
    	String Year="";
    	String Month="";
    	
    	String Status="";

    	String Description="";
  
    	for (int i=0; i< files.size(); i++)
        {
    		ElgChkSearchFileModel model = (ElgChkSearchFileModel) files.get(i);            
        	Search_FileId =  model.getSEARCH_FILE_ID();
        	Year=model.getYEAR();
        	Month=model.getMONTH();
        	Status=model.getSTATUS();
       
        	Description=model.getDESCRIPTION();
        	if(Description==null)
        		{
        		
        		Description="";
        		
        		}
     
        	
      %>
      <TR class=TableTextNote>
        <td width="20%" nowrap align=middle><%=Search_FileId%></td>
        <td width="40%" nowrap align=middle><%=Year%></td>
        <td width="40%" nowrap align=middle><%=Month%></td>
         <td width="60%" nowrap align=middle><%=Description%></td>
        <td width="40%" nowrap align=middle><%=Status%></td>
     
        <TD width="10%" noWrap align=middle>
        <%
        
        if (Status.equalsIgnoreCase("processing") || Status.equalsIgnoreCase("Deleted")){
        out.println("<INPUT class=button type=button  disabled  value= \"Delete\"  name =\"Delete \" > ");
 
       }
        
        else{
      out.println("<INPUT class=button type=button    value= \"Delete\"  name =\"Delete \"onclick=\"AUTHform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+nextAction+"';"+
"loadField("+model.getSEARCH_FILE_ID()+");AUTHform.submit();\">"); 
           }
        %>
        </TD>
        
        
        

              <TD width="10%" noWrap align=middle>
        <%
        
        if (Status.equalsIgnoreCase("processing") || Status.equalsIgnoreCase("Deleted")){
        out.println("<INPUT class=button type=button  disabled  value= \"Export Invalid POS \"  name =\"Export Invalid POS\" > ");
 
       }
        
        else{
      out.println("<INPUT class=button type=button    value= \"Export Invalid POS\"  name =\"Export Invalid DCM\"onclick=\"AUTHform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+nextAction3+"';"+
  "loadField("+model.getSEARCH_FILE_ID()+");AUTHform.submit();\">"); 
           }
        %>
        </TD>   
        
        

    
    
            
         <TD width="10%" noWrap align=middle>
        <%
        
        if (Status.equalsIgnoreCase("processing") || Status.equalsIgnoreCase("Deleted")){
        out.println("<INPUT class=button type=button  disabled  value= \"Export Search File Data \"  name =\"Export Search File Data\" > ");
 
       }
        
        else{
      out.println("<INPUT class=button type=button    value= \"Export Search File Data\"  name =\"Export Search File Data\"onclick=\"AUTHform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+nextAction2+"';"+
"loadField("+model.getSEARCH_FILE_ID()+"),loadMonth("+model.getMONTH()+"),loadYear("+model.getYEAR()+");AUTHform.submit();\">"); 
           }
        %>
        </TD>
    <%
      }
      %>
  </TR>
       
  
    </table>
 </form>  
  </body>
</html>