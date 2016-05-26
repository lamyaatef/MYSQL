<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="com.mobinil.sds.core.utilities.Utility"
	import="javax.servlet.*" import="javax.servlet.http.*"
	import="java.io.PrintWriter" import="java.io.IOException"
	import="java.util.*" import="javax.servlet.jsp.*"
	import="com.mobinil.sds.web.interfaces.*"
	import="com.mobinil.sds.web.interfaces.ccm.CCMInterfaceKey"
	import="com.mobinil.sds.core.system.ccm.entity.model.*"%>

<html>
<head>

      <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
      <script type="text/javascript" src="../resources/js/jquery_tablesorter_pack.js"></script>
    <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252">
<link rel="stylesheet" type="text/css"
	href="../resources/css/Template1.css">
</head>

<body>
<script language="javascript">
function drawCalender(argOrder,argValue)
{

    document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(CCMform."+argOrder+",'yyyy-mm-dd','Choose date')\">");
    document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
}
</script>

<%
  HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String entityTypeId = (String) objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
  String entityId = (String) objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID);
  
  String action=(String) objDataHashMap.get("action");
  System.out.println("action issssssss "+action);
  System.out.println("entity type id in  create page isssssssssssssssssssssss"+entityTypeId);
  
  
  String Entityname=(String)  objDataHashMap.get(CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME);
  String code1=(String)  objDataHashMap.get(CCMInterfaceKey.INPUT_TEXT_ENTITY_CODE);
  String address1=(String)  objDataHashMap.get(CCMInterfaceKey.INPUT_TEXT_ENTITY_ADDRESS);
  // For eedit
  Vector vecmandatoryData =(Vector)  objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA);
  Vector vecadditionalData =(Vector)  objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_DATA);
  
  
 	Vector  vecEntityadditionalLabel = (Vector) objDataHashMap
 			.get(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL);
 	
 	// Error Message
 	String ErrorMessage =(String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
 	if(ErrorMessage==null){ErrorMessage="fjhgfh";}
if(!ErrorMessage.equals(""))
{
	 

	//String nextAction=CCMInterfaceKey.ACTION_UPDATE_ENTITY;

	 //   String flag="";
		/// flag = (String) objDataHashMap.get("flag");
	//	System.out.println("flaggg isssssssssss"+flag);
		
		
	//	if (flag.compareTo("1")==0){
	//		System.out.println(" here in flag one save");
	//		nextAction=CCMInterfaceKey.ACTION_SAVE_NEW_ENTITY;	
	//	}
	//	if (flag.compareTo("2")==0){
	//		System.out.println(" here in flag two  view");
//			nextAction=CCMInterfaceKey.ACTION_VIEW_CREATE_NEW_ENTITY;
			
 		//}
	
 	
 	System.out.println("error message for  checkinggggggggggggggg VALIDATION IN JSP isssssssss"+ErrorMessage);
 	//for Drawing
	Vector  vecEntityOptionalLabel = (Vector) objDataHashMap
		.get(CCMInterfaceKey.VECTOR_ENTITY_OPTIONAL_FIELD_LABEL);
 	
 
  String nextAction=(String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
 	
	
 
 	String EntityName="";
 	String Code="";
 	String Address="";
 	String date="";

 	for (int i=0;i<vecEntityadditionalLabel.size();i++){
 		EntityAditionalFieldLabelModel model = (EntityAditionalFieldLabelModel) vecEntityadditionalLabel.get(i);
    	
         String EntityFieldLabel = model.getEntityFieldLabel();
         


         
    }

 %>
<form name='CCMform' id='CCMform' action='' method='post'>


<% out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID+"\""+
        " value=\""+entityTypeId+"\">");  %>
        
        <% out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID+"\""+
        " value=\""+entityId+"\">");  %>
<script>
function checkBeforeSubmit()
{
  var NAME = document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME%>.value;
  var CODE = document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_CODE%>.value;
  var ADDRESS = document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_ADDRESS%>.value;
 
  if( NAME == "")
  {
    alert("NAME MUST NOT BE EMPTY");
    return;
  }
  if(CODE=="")
  {
  alert("CODE COULD NOT BE Empty");
 } if(ADDRESS=="")
 {

 alert("ADDRESS COULD NOT BE NULL");
 }

else
{
 CCMform.submit();
}

}
</script>


<CENTER>
<H2>Create New Entity</H2>
</CENTER>
<%
	out.println("<input type=\"hidden\" name=\""
			+ InterfaceKey.HASHMAP_KEY_ACTION + "\"" + " value=\""
			+ "\">");

%>
<center>
<TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
	cellSpacing=2 cellPadding=1 width="746" border=1>
	<TR class="TableTextNote">
		<TD class="TableTextNote" width=\"20%\"> EntityName*</TD>
		<TD class="TableTextNote" colSpan=4><INPUT
			style="WIDTH: 200px; HEIGHT: 22px" size=20
			name="<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME%>"
			id="<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME%>"
				  value="<%
			 if(action.equals(CCMInterfaceKey.ACTION_SAVE_NEW_ENTITY)||action.equals(CCMInterfaceKey.ACTION_UPDATE_ENTITY))
					 {
				 out.print(Entityname);
					 }
			
			 if(action.equals(CCMInterfaceKey.ACTION_EDIT_ENTITY))
			 {
		 System.out.println("before loop  "+action + " vector size is "+vecmandatoryData.size());
        for (int i=0;i<vecmandatoryData.size();i++)
	 	    {
		 		
           EntityMandatoryDataModel model = (EntityMandatoryDataModel) vecmandatoryData.get(i);
	 	     String  entiyName=model.getEntityyName(); 
	 	     System.out.print("nameeeeeeeeeee  in edit jsp issssssss"+entiyName);

	 	  out.print(entiyName);
	

	 	    }
		 
		 
			 }
			
			 %>"></td>
	</tr>
	
	
		<TR class="TableTextNote">
		<TD class="TableTextNote" width=\"20%\"> Code*</TD>
		<TD class="TableTextNote" colSpan=4><INPUT
			style="WIDTH: 200px; HEIGHT: 22px" size=20
			name="<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_CODE%>"
			id="<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_CODE%>"
			  value="<%
			 if(action.equals(CCMInterfaceKey.ACTION_SAVE_NEW_ENTITY)||action.equals(CCMInterfaceKey.ACTION_UPDATE_ENTITY))
					 {
				 out.print(code1);
					 }
			 if(action.equals(CCMInterfaceKey.ACTION_EDIT_ENTITY))
					 {
				 
		        for (int i=0;i<vecmandatoryData.size();i++)
			 	    {
				 		
	               EntityMandatoryDataModel model = (EntityMandatoryDataModel) vecmandatoryData.get(i);
			 	     String  entiycode=model.getEntityCode(); 

			 	  out.print(entiycode);

			 	    }

					 }
			
			
			 %>"></td>

	</tr>


	<TR class=TableTextNote>
		<TD class=TableTextNote width=\"20%\"> Address*</TD>

		<TD class="TableTextNote" colSpan=4><INPUT
			style="WIDTH: 200px; HEIGHT: 22px" size=50
			name="<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_ADDRESS%>"
			 id="<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_ADDRESS%>"
		  value="<%
			 if(action.equals(CCMInterfaceKey.ACTION_SAVE_NEW_ENTITY)||action.equals(CCMInterfaceKey.ACTION_UPDATE_ENTITY))
					 {
				 out.print(address1);
					 }
			 
			 if(action.equals(CCMInterfaceKey.ACTION_EDIT_ENTITY))
			 {
		 
        for (int i=0;i<vecmandatoryData.size();i++)
	 	    {
		 		
           EntityMandatoryDataModel model = (EntityMandatoryDataModel) vecmandatoryData.get(i);
	 	     String  entiyAdress=model.getEntityAdress(); 

	 	  out.print(entiyAdress);
	
	 	    }

			 }
			 %>"></td>
			
	</Tr>
	
</table>
</center>
<br><br>
<center>
<TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
	cellSpacing=2 cellPadding=1 width="746" border=1>
	<TR class="TableTextNote">
		
<%  	 	// for Drawing
 	for (int i=0;i<vecEntityOptionalLabel.size();i++)
 {
 		
 	EntityOptionalFieldModel model = (EntityOptionalFieldModel) vecEntityOptionalLabel.get(i);
   	
         String EntityFieldLabel = model.getEntityfieldLabel();
         String EntityFieldType=model.getEntityFieldTypeName();
         String isMandatory = model.getIsMandatory();
         if (isMandatory.equals("1"))
         {
        	 EntityFieldLabel=EntityFieldLabel+"*"; 
        	 
         }
         
       
		String j =(String) objDataHashMap.get( CCMInterfaceKey.CONTROL_INPUT_TEXT_AND_FIELD_ID+model.getEntityFieldId());
         
         String k =(String) objDataHashMap.get( CCMInterfaceKey.CONTROL_INPUT_DATE_AND_FIELD_ID+model.getEntityFieldId());
               
         String l =(String) objDataHashMap.get( CCMInterfaceKey.CONTROL_INPUT_NUMBER_AND_FIELD_ID+model.getEntityFieldId());
               
         String m=(String)  objDataHashMap.get(CCMInterfaceKey.CONTROL_INPUT_CHECKBOX_AND_FIELD_ID+model.getEntityFieldId());
         
 
    if (EntityFieldType.equals("Number"))
 
     {
	  System.out.println("Numberrrr");
    %>
	 <TR class=TableTextNote>
		 <TD class=TableTextNote width=\"20%\"><%=EntityFieldLabel%></TD>
 
		<TD class="TableTextNote" colSpan=4><INPUT
			style="WIDTH: 200px; HEIGHT: 22px" size=20
			name="<%=CCMInterfaceKey.CONTROL_INPUT_NUMBER_AND_FIELD_ID+ model.getEntityFieldId()+""%>"
			 id="<%=CCMInterfaceKey.CONTROL_INPUT_NUMBER_AND_FIELD_ID+ model.getEntityFieldId()+""%>" 
			 value="<%
			 if(action.equals(CCMInterfaceKey.ACTION_SAVE_NEW_ENTITY)||action.equals(CCMInterfaceKey.ACTION_UPDATE_ENTITY))
					 {
				 out.print(l);
					 }
			 
			 
			 if(action.equals(CCMInterfaceKey.ACTION_EDIT_ENTITY))
			 {
				 
			        for (int q=0;q<vecadditionalData.size();q++)
				 	    {
					 		
			        	EntityAditionalFieldModel model1 = (EntityAditionalFieldModel) vecadditionalData.get(q);
				     String  fieldvalue  =model1.getEntityFieldValue();
				 	 String EntityFieldId = model1.getEntityFieldID();
				 	   
				 	  
				 	   if (EntityFieldId.equals(model.getEntityFieldId())) 
				 	    {
				 	     out.print(fieldvalue);
				 	    }
				
				 	    }
				
			 }
	 
			 
			 %>"></td>
	</Tr>
   <%
     }
   %>

<%
   if (EntityFieldType.equals("Text"))
 
    {

    %>
	<TR class=TableTextNote>
		<TD class=TableTextNote width=\"40%\"><%=EntityFieldLabel%></TD>

		<TD class="TableTextNote" colSpan=4><INPUT
			style="WIDTH: 250px; HEIGHT: 60px" size=50
			name="<%=CCMInterfaceKey.CONTROL_INPUT_TEXT_AND_FIELD_ID+ model.getEntityFieldId()%>"
			 id="<%=CCMInterfaceKey.CONTROL_INPUT_TEXT_AND_FIELD_ID+ model.getEntityFieldId()%>"
			  value="<%
		
			 
			 if(action.equals(CCMInterfaceKey.ACTION_SAVE_NEW_ENTITY)||action.equals(CCMInterfaceKey.ACTION_UPDATE_ENTITY))
			 {
		    out.print(j);
			 }
			 
			 if(action.equals(CCMInterfaceKey.ACTION_EDIT_ENTITY))
			 {
				 
			        for (int q=0;q<vecadditionalData.size();q++)
				 	    {
					 		
			        	EntityAditionalFieldModel model1 = (EntityAditionalFieldModel) vecadditionalData.get(q);
				     String  fieldvalue  =model1.getEntityFieldValue();
				 	 String EntityFieldId = model1.getEntityFieldID();
				 	   
				 	  
				 	   if (EntityFieldId.equals(model.getEntityFieldId())) 
				 	    {
				 	     out.print(fieldvalue);
				 	    }
				
				 	    }
				
			 }
			 %>"></td>
	</Tr>
   <%
     }
    %>

    <%     
     
    if (EntityFieldType.equals("List"))
    {

    %>
	<TR class=TableTextNote>
		<TD class=TableTextNote width=\"20%\"><%=EntityFieldLabel%></TD>
		<TD class="TableTextNote" colSpan=4>
			
 <%
 out.println("<select name='"+CCMInterfaceKey.CONTROL_INPUT_LIST_AND_FIELD_ID+ model.getEntityFieldId()+"\"");

 out.println("</select>");
  %>

		</td>

	</Tr>
   <%
     }
   %>
                                                       

      <%
     if(EntityFieldType.equals("Date"))
 
    {
	String entityDate = "";
    %>
	<TR class=TableTextNote> 
 <td class=TableTextNote  width=\"20%\"><%=EntityFieldLabel%></td>

 <td class=TableTextNote  colSpan=4 style="WIDTH: 200px; HEIGHT: 22px; size:20">
<%

if(action.equals(CCMInterfaceKey.ACTION_SAVE_NEW_ENTITY)||action.equals(CCMInterfaceKey.ACTION_UPDATE_ENTITY))
{
	System.out.println("kkkkkk issssssssss"+k);
	entityDate=k;
}

if(action.equals(CCMInterfaceKey.ACTION_EDIT_ENTITY))
{
	 
       for (int q=0;q<vecadditionalData.size();q++)
	 	    {
		 		
       	EntityAditionalFieldModel model1 = (EntityAditionalFieldModel) vecadditionalData.get(q);
	     String  fieldvalue  =model1.getEntityFieldValue();
	 	 String EntityFieldId = model1.getEntityFieldID();
	 	   
	 	  
	 	   if (EntityFieldId.equals(model.getEntityFieldId())) 
	 	    {
	 		  entityDate=fieldvalue;
	 	    }
	
	 	    }
	
}
%>
 <script>
 drawCalender('<%=CCMInterfaceKey.CONTROL_INPUT_DATE_AND_FIELD_ID+model.getEntityFieldId()%>','"<%=entityDate%>"');
 </script>
 </td>
  </Tr>
   <%
     }
   %>

      <%
     if (EntityFieldType.equals("CheckBox"))
 
    {
    	     String checked="checked";
			 if(action.equals(CCMInterfaceKey.ACTION_SAVE_NEW_ENTITY)||action.equals(CCMInterfaceKey.ACTION_UPDATE_ENTITY)) 
			
			 {
			  checked=m;
			 }
			 
			 if(action.equals(CCMInterfaceKey.ACTION_EDIT_ENTITY))
			 {
				 
			        for (int q=0;q<vecadditionalData.size();q++)
				 	    {
					 		
			        	EntityAditionalFieldModel model1 = (EntityAditionalFieldModel) vecadditionalData.get(q);
				     String  fieldvalue  =model1.getEntityFieldValue();
				 	 String EntityFieldId = model1.getEntityFieldID();
				 	   
				 	  
				 	   if (EntityFieldId.equals(model.getEntityFieldId())) 
				 	    {
				 	     checked=fieldvalue;
				 	     System.out.println("checked isssssssssssssss"+checked);
				 	    }
				
				 	    }
				
			 }	 
			 
    %>
	<TR class=TableTextNote>
		<TD class=TableTextNote width=\"20%\"><%=EntityFieldLabel%></TD>

		<TD class="TableTextNote" colSpan=4>
			
			<input TYPE=checkbox 
		       name="<%=CCMInterfaceKey.CONTROL_INPUT_CHECKBOX_AND_FIELD_ID+ model.getEntityFieldId()+""%>"
                value="<%=checked%>">

		</td>
	</Tr>
   <%
     }
   %>
   
   

   <%
     }
   %>
   
<TR class=TableTextNote><TD colspan=2 class=TableTextNote><font size=1>* indicates mandatory fields.</font></TD>
</table>

</CENTER>

  <center>
  
   <br><br>
   <br><br>
 
    <%
        System.out.println("The action issssssssssssssss " +nextAction); 
        out.print("<INPUT class=button type='button' value=\" Save \" name=\"Save\" id=\"details\" onclick=\"document.CCMform."
        +InterfaceKey.HASHMAP_KEY_ACTION
		+ ".value = '"+nextAction+ "';" + "checkBeforeSubmit();\">");
        
  
        System.out.println("The action issssssssssssssss " +nextAction); 
        out.print("<INPUT class=button type='button' value=\" Update \" name=\"Update\" id=\"details\" onclick=\"document.CCMform."
        +InterfaceKey.HASHMAP_KEY_ACTION
		+ ".value = '"+nextAction+ "';" + "checkBeforeSubmit();\">");
 
    %> 
<br><br>
    </center>
</form>
<%
 }else{
%>
<form name='CCMform' id='CCMform' action='' method='post'>
<% 
out.println("<input type=\"hidden\" name=\""
			+ InterfaceKey.HASHMAP_KEY_ACTION + "\"" + " value=\""
			+ "\">");




out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID+"\""+
        " value=\""+entityTypeId+"\">");  
%>

<script>
document.CCMform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=CCMInterfaceKey.ACTION_VIEW_ENTITY_MANAGEMENT%>';
document.CCMform.submit();

</script>
</form>
<% 
}
%>
</body>
</html>
