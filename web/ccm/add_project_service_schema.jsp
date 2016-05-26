<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="com.mobinil.sds.core.utilities.Utility"
	import="javax.servlet.*" import="javax.servlet.http.*"
	import="java.io.PrintWriter" import="java.io.IOException"
	import="java.util.*" import="javax.servlet.jsp.*"
	import="com.mobinil.sds.web.interfaces.*"
	import="com.mobinil.sds.web.interfaces.ccm.CCMInterfaceKey"
	import="com.mobinil.sds.core.system.ccm.project.model.*"
	import="com.mobinil.sds.core.system.ccm.service.model.*"
	import="com.mobinil.sds.core.system.ccm.schema.model.*"
		import="com.mobinil.sds.core.system.ccm.entity.model.*"
		
		import="com.mobinil.sds.core.system.ccm.entityproject.model.*"

	%>
	<%
 
	HashMap objDataHashMap = (HashMap) request
		.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
 String  nextAction="";

	
	String	hidden=(String)  objDataHashMap.get("flag");
	if (hidden==null)
	{
		 hidden="qwe";
		
	}
	System.out.println("hidden edit flag in jsp isss:"+hidden);
 
 if (hidden.equals("edit"))
 {
	 
	 nextAction=CCMInterfaceKey.ACTION_UPDATE_PROJECT; 
	 
	 
 }
 else
 
 {
	 
	 nextAction=CCMInterfaceKey.ACTION_SAVE_PROJECT;  
	 
 }
 
 
    System.out.println("next action issssssssssss"+nextAction);
 String  nextAction2=CCMInterfaceKey.ACTION_SUB_ENTITY_TREE;

 
 
 
 	
 	 

 	Vector vecEntityTypeField = (Vector) objDataHashMap
 			.get(CCMInterfaceKey.VECTOR_ENTITY_TYPE_FIELDS);
 	String projectId = (String) objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID);
 	//System.out.println("project idddd in the jsp"+projectId);
 	
	
 	
	


	
	
	Vector vecServicesAssignedProject =(Vector) objDataHashMap.get(CCMInterfaceKey.VECTOR_SERVICE_ASSIGNED_PROJECT); 
	Vector vecEntityAssignedProject =(Vector) objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_ASSIGNED_PROJECT); 
	//System.out.println("Mahmoud edit VECTOR SIZE:"+vecEntityAssignedProject.size());
	//System.out.println("Mahmoud VECTOR SIZE:"+vecServicesAssignedProject.size());
 	Vector vecServicesList = (Vector) objDataHashMap.get(CCMInterfaceKey.VECTOR_SERVICE_LIST);
	Vector vecSchemasList = (Vector) objDataHashMap.get(CCMInterfaceKey.VECTOR_SCHEMA_LIST);
	Vector vecEntityList = (Vector) objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA);

	


 %>
	<%
	Vector  project= (Vector) objDataHashMap.get(CCMInterfaceKey.VECTOR_PROJECT);
String ProjectName="";
String ProjectAddress="";
String creationDate="";
String projectDetails="";
String projectStatus="";
String schemaName="";
String schemaId="";
if  (project.size()!=0) 
{	
	
	
	for (int i = 0; i < project.size(); i++) {
		
		ProjectModel model = (ProjectModel)  project.get(i); 
		schemaName=model.getSchemaName();
	   schemaId= model.getSchemaId();
		 ProjectName=  model.getProjectName();
		 ProjectAddress=  model.getProjectAddress();
		 
		    if (ProjectAddress==null){
		    	
		    	
		    	ProjectAddress="";	
		    }
		
		 creationDate=  model.getProjectCreationDate();
	    projectDetails=  model.getProjectDetail();
	    if (projectDetails==null){
	    	
	    	
	    	projectDetails="";	
	    }
		projectStatus=  model.getProjectStautusId();
   }
	
}
	%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252">
<link rel="stylesheet" type="text/css"
	href="../resources/css/Template1.css">
	<SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
</head>

<body>
<form name='CCMform' id='CCMform' action='' method='post'>
<script>
 
function fillControl(controlName,controlValue)
{
	alert(controlName);
	alert(controlValue);
	document.getElementById(controlName).value = controlValue;
}
</script>

<script>
function checkBeforeSubmit()
{
	// S = document.CCMform.<%=CCMInterfaceKey.CONTROL_SELECT_SCHEMA_ID%>.value;
  var NAME = document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_PROJECT_NAME%>.value;
  var DATE = document.CCMform.<%=CCMInterfaceKey.CONTROL_TEXT_PROJECT_START_DATE%>.value;

  
  var ss = document.getElementById('<%=CCMInterfaceKey.CONTROL_SELECT_SCHEMA_ID%>');
  var S = ss.options(ss.selectedIndex).text;
 
 // alert(ss.options(ss.selectedIndex).text);
  var SERVICE = document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME%>.value;
 
  if( NAME == "")
  {
    alert("PROJECT NAME MUST NOT BE EMPTY");
    return;
  }
  if(DATE=="")
  {
  alert("START DATE MUST NOT BE EMPTY");
 } 
  else  if(S=="")
 {

  alert("MUST CHOOSE SCHEMA FOR PROJECT");
 }

  else if(SERVICE==","||SERVICE==",,")
  {

   alert("MUST CHOOSE SERVICE FOR PROJECT");
  }

else
{

	//document.CCMform.<%out.print(CCMInterfaceKey.CONTROL_SELECT_SCHEMA_ID);%>.value = S;
 CCMform.submit();
}

}
</script>





  <script>
    function funaddservice()
    {
    	
      var serviceobj = eval("document.CCMform.<%=CCMInterfaceKey.CONTROL_SERVICE_NAME%>");
      var serviceSelectedIndex = serviceobj.selectedIndex;
      var service = serviceobj.options[serviceSelectedIndex].text+",";
      var  Id = serviceobj.options[serviceSelectedIndex].value+",";

      var serviceNameInterface = eval("document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME%>.value");
      serviceNameInterface = serviceNameInterface.replace(","+service,",");
      eval("document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME%>.value = serviceNameInterface");

      var serviceIdInterface = eval("document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_SERVICE_ID%>.value");
      serviceIdInterface = serviceIdInterface.replace(","+Id,",");
      eval("document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_SERVICE_ID%>.value = serviceIdInterface");

      eval("document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME%>.value = document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME%>.value+service");
      eval("document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_SERVICE_ID%>.value = document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_SERVICE_ID%>.value+Id");
    } 

    function funremoveservice()
    {
        var serviceobj = eval("document.CCMform.<%=CCMInterfaceKey.CONTROL_SERVICE_NAME%>");
        var serviceSelectedIndex = serviceobj.selectedIndex;
        var service = serviceobj.options[serviceSelectedIndex].text+",";
        var  Id = serviceobj.options[serviceSelectedIndex].value+",";
      
        var serviceNameInterface = eval("document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME%>.value");
        serviceNameInterface = serviceNameInterface.replace(","+service,",");
        eval("document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME%>.value = serviceNameInterface");
        
      var serviceIdInterface = eval("document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_SERVICE_ID%>.value")
      serviceIdInterface = serviceIdInterface.replace(","+Id,",");
      eval("document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_SERVICE_ID%>.value = serviceIdInterface");
    } 
    
 </script> 
 
   <script>
    function funaddentity()
    {
    	
      var entityobj = eval("document.CCMform.<%=CCMInterfaceKey.CONTROL_ENTITY_NAME%>");
      var entitySelectedIndex = entityobj.selectedIndex;
      var entity = entityobj.options[entitySelectedIndex].text+",";
      var  Id = entityobj.options[entitySelectedIndex].value+",";

      var entityNameInterface = eval("document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME%>.value");
      entityNameInterface = entityNameInterface.replace(","+entity,",");
      eval("document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME%>.value = entityNameInterface");

      var entityIdInterface = eval("document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID%>.value");
      entityIdInterface = entityIdInterface.replace(","+Id,",");
      eval("document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID%>.value = entityIdInterface");

      eval("document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME%>.value = document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME%>.value+entity");
      eval("document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID%>.value = document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID%>.value+Id");
    } 

    function funremoveentity()
    {
        var entityobj = eval("document.CCMform.<%=CCMInterfaceKey.CONTROL_ENTITY_NAME%>");
        var entitySelectedIndex = entityobj.selectedIndex;
        var entity = entityobj.options[entitySelectedIndex].text+",";
        var  Id = entityobj.options[entitySelectedIndex].value+",";
      
        var entityNameInterface = eval("document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME%>.value");
        entityNameInterface = entityNameInterface.replace(","+entity,",");
        eval("document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME%>.value = entityNameInterface");
        
      var entityIdInterface = eval("document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID%>.value")
      entityIdInterface = entityIdInterface.replace(","+Id,",");
      eval("document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID%>.value = entityIdInterface");
    } 
    
 </script> 
 <script>
 function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(CCMform."+argOrder+",'dd/mm/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }
 </script>
 
<CENTER>
<H2>Project Management</H2>
</CENTER>
<%
	out.println("<input type=\"hidden\" name=\""
			+ InterfaceKey.HASHMAP_KEY_ACTION + "\"" + " value=\""
			+ "\">");

out.println("<input type=\"hidden\" name=\""
		+ CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID +"1"+ "\"" + " value=\""
		+ projectId+"\">");

String userID = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
out.println("<input type=\"hidden\" name=\""
		+ InterfaceKey.HASHMAP_KEY_USER_ID + "\"" + " value=\""
		+ userID+"\">");


%>


<center>
<TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
	cellSpacing=2 cellPadding=1 width="746" border=1>
	<TR class="TableTextNote">
		<TD class="TableTextNote" width=\"20%\">Project Name</TD>
		<TD class="TableTextNote" colSpan=4><INPUT
			style="WIDTH: 200px; HEIGHT: 22px" size=20
			name="<%=CCMInterfaceKey.INPUT_TEXT_PROJECT_NAME%>"
			id="<%=CCMInterfaceKey.INPUT_TEXT_PROJECT_NAME%>"></td>
	</tr>
	
		<TR class="TableTextNote">
		<TD class="TableTextNote" width=\"20%\">Project address</TD>
		<TD class="TableTextNote" colSpan=4><INPUT
			style="WIDTH: 200px; HEIGHT: 22px" size=20
			name="<%=CCMInterfaceKey.INPUT_TEXT_PROJECT_ADDRESS%>"
			id="<%=CCMInterfaceKey.INPUT_TEXT_PROJECT_ADDRESS%>"></td>
	</tr>
		
		
  <tr>
    <td height="27" class="TableTextNote">Project Start Date </td>
    <td class="TableTextNote"><script>drawCalender('<%=CCMInterfaceKey.CONTROL_TEXT_PROJECT_START_DATE%>', "*");</script></td>
  </tr>

		
		<TR class="TableTextNote">
		<TD class="TableTextNote" width=\"20%\">Project Description</TD>
		<TD class="TableTextNote" colSpan=4><INPUT
			style="WIDTH: 400px; HEIGHT: 44px" size=40
			name="<%=CCMInterfaceKey.INPUT_TEXT_PROJECT_DESCRIPTION%>"
			id="<%=CCMInterfaceKey.INPUT_TEXT_PROJECT_DESCRIPTION%>"></td>
	</tr>






	<TR class=TableTextNote>
		<TD class=TableTextNote width="20%">Schema</TD>
		
		
		 <td>
          <%
        out.println("<select name='"+CCMInterfaceKey.CONTROL_SELECT_SCHEMA_ID+"' id='"+CCMInterfaceKey.CONTROL_SELECT_SCHEMA_ID+"'> ");
        out.println("<option value=''></option>");  
        String option_selected="";
        for (int i=0;i<vecSchemasList.size();i++){

        	
        	SchemaModel schema_model = (SchemaModel) vecSchemasList.get(i);
        	
        	      String 	Schema_schemaId= schema_model.getSchemaId();
        	      String option_schemaName= schema_model.getSchemaName();
        	      if(option_schemaName.equals(schemaName))
        	      {
        	    	  option_selected = "selected";
        	      }
        
             %>
         <option value="<%=Schema_schemaId%>" <%=option_selected %>> <%=option_schemaName%></option>
         
        
             <% 
             
             
             
         /* out.println("<option value="+entityFieldTypeId+">"+entityyFieldTypeName+"</option>"); */ 
        }
        out.println("</select>");
     
%>
 </td>
</tr>

</table>
<br>
<br>
<table>
<TR class=TableTextNote>
          <TD width=\"20%\">Project Service: </TD>
           
             
       
           <TD>
            <INPUT readonly type='text' style="WIDTH: 375px; HEIGHT: 22px" size=66 name="<%=CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME%>" id="<%=CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME%>" value=","> 
            <input type='hidden' name="<%=CCMInterfaceKey.CONTROL_HIDDEN_SERVICE_ID%>" id="<%=CCMInterfaceKey.CONTROL_HIDDEN_SERVICE_ID%>"  value= "," >
            
    
             <% 
             String value="";
             String idvalue="";
             String tmep=",";
             String ids=",";
          if (hidden.equals("edit"))
          { 
        	  String        servicename="";
        	  String 	      serviceid="";
            for (int l=0;l<vecServicesAssignedProject.size();l++){

            	  System.out.println("here in for loop");
               
              ServiceModel model = (ServiceModel) vecServicesAssignedProject.get(l);
           	  serviceid= model.getServiceId();
              servicename= model.getServiceName();
              String  servDesc=model.getServiceDescription();

           
                value=servicename;
                idvalue=serviceid;
               
                tmep += value+",";
               
                ids  +=idvalue+",";
              //  out.println(value);
              
                System.out.println(" service iddss in edit action issss:"+idvalue);
                System.out.println(" service name in edit action issss:"+value);
                
             }
          }
            %>  
           
           
    
            
          </td>


          <TD width=\"20%\">Select Service: </TD>
          <TD>
            <select name='<%=CCMInterfaceKey.CONTROL_SERVICE_NAME%>' id='<%=CCMInterfaceKey.CONTROL_SERVICE_NAME%>'>
            <option value=''></option>
            <%
            
            for (int i=0;i<vecServicesList.size();i++){

            	
            	ServiceModel model = (ServiceModel) vecServicesList.get(i);
            	
            	 String 	serviceId= model.getServiceId();
            	 String      serviceName= model.getServiceName();
    
              %>
              <option value="<%=serviceId%>"><%=serviceName%></option>
              <%
            }
            %>
            </select>
          </td>
          <td> 
            <input type='button' class='button' name='addservice' id='addservice' value='Assign Service' onClick="funaddservice();">
            
            <input type='button' class='button' name='removeservice' id='removeservice' value='Remove Service' onClick="funremoveservice();">
   
          </td>
        </tr>
</table>
<br>
<br>
<%-- 
<table>

<TR class=TableTextNote>
          <TD width=\"20%\">Project Entities: </TD>
          <TD>
            <INPUT readonly type='text' style="WIDTH: 375px; HEIGHT: 22px" size=66 name="<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME%>" id="<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME%>" value=",">
            <input type='hidden' name="<%=CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID%>" id="<%=CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID%>" value=",">
       <% 
       String ids1=",";
       String idvalue1="";
         String value1="";
             String tmep1=",";
          if (hidden.equals("edit"))
          { 
        	  String       entityname="";
        	  String 	      entityid="";
            for (int l=0;l<vecEntityAssignedProject.size();l++){

            	  System.out.println("here in for loop");
               
            	  EntityMandatoryDataModel model = (EntityMandatoryDataModel) vecEntityAssignedProject.get(l);
            	entityid= model.getEntityId();
            	entityname= model.getEntityyName();
           

           
                value1=entityname;
                
                idvalue1=entityid; 
                tmep1 += value1+",";
                
                
               
                ids1  +=idvalue1+",";
                
                
                System.out.println(" entity ID in edit action issss:"+idvalue1);
                System.out.println(" entity name in edit action issss:"+value1);
                
             }
          }
            %>  
         

         
          </td>
          <TD width=\"20%\">Select Entity: </TD>
          <TD>
            <select name='<%=CCMInterfaceKey.CONTROL_ENTITY_NAME%>' id='<%=CCMInterfaceKey.CONTROL_ENTITY_NAME%>'>
            <option value=''></option>
            <%
            
            for (int p=0;p<vecEntityList.size();p++){

            	
            	EntityMandatoryDataModel model = (EntityMandatoryDataModel) vecEntityList.get(p);
            	
            	 String 	entityId= model.getEntityId();
            	 String      entityName= model.getEntityyName();
    
              %>
              <option value="<%=entityId%>"><%=entityName%></option>
              <%
            }
            %>
            </select>
          </td>
          <td> 
            <input type='button' class='button' name='addentity' id='addentity' value='Assign Entity' onClick="funaddentity();">
            
            <input type='button' class='button' name='removeentity' id='removeentity' value='Remove Entity' onClick="funremoveentity();">
          </td>
        </tr>


</table>

--%>



<script>

		document.CCMform.<%out.print(CCMInterfaceKey.INPUT_TEXT_PROJECT_NAME);%>.value='<%out.print(ProjectName);%>';
    
        document.CCMform.<%out.print(CCMInterfaceKey.CONTROL_TEXT_PROJECT_START_DATE);%>.value='<%out.print(creationDate);%>';
        document.CCMform.<%out.print(CCMInterfaceKey.INPUT_TEXT_PROJECT_ADDRESS);%>.value='<%out.print(ProjectAddress);%>';
        document.CCMform.<%out.print(CCMInterfaceKey.INPUT_TEXT_PROJECT_DESCRIPTION);%>.value='<%out.print(projectDetails);%>';

        document.CCMform.<%out.print(CCMInterfaceKey.CONTROL_SELECT_SCHEMA_ID);%>.value='<%out.print(schemaId);%>';
</script>
</center>


<center><br>
<%  if (hidden.equals("edit"))
 {
	 

   out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID+"\""+
        " value=\""+projectId+"\">"); 
	
		out.print("<INPUT class=button type='button' value=\" Save \" name=\"Save\" id=\"details\" onclick=\"document.CCMform."
					+ InterfaceKey.HASHMAP_KEY_ACTION
				+ ".value = '"
				+ nextAction
				+ "';" + "checkBeforeSubmit();\">");
  
		
 }
		%>
   
   
		<br>
		<br>
		<% 
		out.print("<INPUT class=button type='button' value=\" Add Entities\" name=\"Add Sub Entities\" id=\"Add Sub Entities\" onclick=\"document.CCMform."
				+ InterfaceKey.HASHMAP_KEY_ACTION
			+ ".value = '"
			+ nextAction2
				+ "';" + "checkBeforeSubmit();\">");
	%>
	
	
	<% 
	
	  //out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.CONTROL_SELECT_SCHEMA_ID+"\""+" value=\"\">"); 

out.print("<INPUT class=button type=button value=\"Back \" name=\"Back\" id=\"Back\" onclick=\"document.CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_VIEW_PROJECT_MANAGEMENT+"';"+
" submit();\">");
	
	%>	
<br>
</center>
      <script>
            var temp = document.getElementById('<%=CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME%>');
            temp.value= '<%=tmep%>';

            var ids = document.getElementById('<%=CCMInterfaceKey.CONTROL_HIDDEN_SERVICE_ID%>');
            ids.value= '<%=ids%>';
       </script>
     <%--     
       <script>
            var temp1 = document.getElementById('<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME%>');
            temp1.value= '<%=tmep1%>';

            var ids1 = document.getElementById('<%=CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID%>');
            ids1.value= '<%=ids1%>';
        
        </script>
        
        --%>   
     </form>
   </body>
</html>