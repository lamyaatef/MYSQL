  <%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.ccm.CCMInterfaceKey"
         import = "com.mobinil.sds.core.system.ccm.project.model.*"
       
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <script type="text/javascript">


      function setSearchValues(projectName,projectSchema)
      {
        document.getElementById("<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_NAME%>").value = projectName;

        document.getElementById("<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA%>").value=projectSchema;
       // document.getElementById("<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_ADDRESS%>").value = projectAddress;
     

      }



      
      function loadField(id)
      {
		
          document.CCMform.fieldId.value=id;
      }


      function loadProjectName(name)
      {
		//alert(name);
          document.CCMform.project.value=name;
      }

      </script>
    </head>
  <body>
  <form  name='CCMform' id='CCMform' action='' method='post'>  
<%


  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

String userID = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    Vector vecProjecttList = (Vector) objDataHashMap.get(CCMInterfaceKey.VECTOR_PROJECT_LIST);
  //String startDate = "*";
  
  	
						
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
		  
                  " value=\""+"\">");
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
		  
          " value=\""+userID+"\">");
  
  out.println("<input type=\"hidden\" name=fieldId value=\""+"\">");
  
  out.println("<input type=\"hidden\" name=project value=\""+"\">");
  
  
  
  String searchprojectName= "";
  String searchProjectAddress = "";
  String searchSchema="";
 
 
 if(objDataHashMap.containsKey(CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_NAME))
 { 
	 searchprojectName = (String)objDataHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_NAME);
 	if (searchprojectName==null){
 		searchprojectName="";
 		
 	}
 }
 if(objDataHashMap.containsKey(CCMInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA))
 {

	 searchSchema = (String)objDataHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA);
	 System.out.println("searchSchema isssss"+searchSchema);
   	if (searchSchema==null){
   		searchSchema="";
 		
 	}
 }


  
%>  
    <CENTER>
      <H2> Project Management</H2>
    </CENTER>
    
    
    
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" 

border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      
      <TR class=TableTextNote>
        <td align=middle>Project Name</td>
        <td align=middle><input type='text' name='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_NAME%>' id='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_NAME%>'></td>
        <%-- 
                <td align=middle>Addresse</td>
        <td align=middle><input type='text' name='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_ADDRESS%>' id='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_ADDRESS%>'></td>
        --%>
        
        <td align=middle>Schema</td>
        <td align=middle><input type='text' name='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA%>' id='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA%>'></td>
	

      </TR>

      <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchentity\" id=\"searchentity\" onclick=\"document.CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_SEARCH_PROJECT+"';"+
                 "CCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','');\">");          
      
        %>
        </td>
      </tr>
      
</TABLE>

<br><br>
<% if (vecProjecttList.size()!=0) { %>

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR class=TableHeader>
        <td width="20%" nowrap align=middle>Project Name</td>
         <td width="40%" nowrap align=middle>Project Address</td>
          <td width="20%" nowrap align=middle>Project start Date</td>
        <td width="60%" nowrap align=middle>Project Description</td>
        <td width="60%" nowrap align=middle>Schema</td>
         <td width="20%" nowrap align=middle>View Entity  </td>
          <td width="20%" nowrap align=middle>View Service</td>
          <td width="20%" nowrap align=middle>Edit Project</td>
         <td width="20%" nowrap align=middle>Delete Project</td>
      

      </TR>
      <%
  
        
        String Name="";
    	String Description="";
    	String Address="";
    	String Date="";
    	String Schema="";
    

        
        for (int i=0; i< vecProjecttList.size(); i++)
        {
        	ProjectModel model = (ProjectModel) vecProjecttList.get(i);            
        	Name =  model.getProjectName();
        	Description=model.getProjectDetail();
        	Address=model.getProjectAddress();
        	Date=model.getProjectCreationDate();
        	Schema=model.getSchemaName();
        	
        	if (Description==null){
        		Description="";
        	}
        	if (Address==null){
        		Address="";
        	}
        	
      %>
      <TR class=TableTextNote>
        <td width="20%" nowrap align=middle><%=Name%></td>
         <td width="20%" nowrap align=middle><%=Address%></td>
          <td width="20%" nowrap align=middle><%=Date%></td>
        <td width="40%" nowrap align=middle><%=Description%></td>
        <td width="20%" nowrap align=middle><%=Schema%></td>
     
           <TD width="10%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"View  Entity\"  name =\"View Entity \"    onclick=\"CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_EDIT_SUB_ENTITY_TREE+"';"+
        "loadProjectName('"+model.getProjectName()+"');"+    
        "loadField('"+model.getProjectId()+"');CCMform.submit();\">"); 
        %>
        </TD>
<TD width="20%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"View Service\"  name =\"View Service \"    onclick=\"CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_VIEW_SERVICE_ASSIGNED_PROJECT+"';"+
        "loadProjectName('"+model.getProjectName()+"') ;"+    
        "loadField('"+model.getProjectId()+"');CCMform.submit();\">"); 
        %>
        </TD>


<TD width="20%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"Edit Project\"  name =\"Edit Project\"    onclick=\"CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_EDIT_PROJECT+"';"+
         
        "loadField('"+model.getProjectId()+"');CCMform.submit();\">"); 
        %>
        </TD>
        
        <TD width="20%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"Delete Project\"  name =\"Delete Project\"    onclick=\"CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_DELETE_PROJECT+"';"+
         
        "loadField('"+model.getProjectId()+"');CCMform.submit();\">"); 
        %>
        </TD>
        
        

      </TR>
         <%
      }
      %>
      <%
      }
      %>
      
            <%
      if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ACTION))
        {
          String strAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
         // System.out.println("search schema isssssssss  " +searchSchema);
          
          if(strAction.compareTo(CCMInterfaceKey.ACTION_SEARCH_PROJECT)==0)
          {
        	//out.println("<script> alert('"+searchSchema+"');   </script>");
            out.println("<script> setSearchValues('"+searchprojectName+"','"+searchSchema+"');</script>");
          }
        }
      %>
      
      
    </table>

    <br><br>
      <center>
      <%

     out.println("<INPUT class=button type=button value= \"ADD NEW PROJECT\"  name =\"add new \"   + onclick=\"CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_ADD_PROJECT_SERVICE_SCHEMA+"';"+
       "CCMform.submit();\">");
       %>
      
      
      </center>
      

      
  </form>  
  </body>
</html>