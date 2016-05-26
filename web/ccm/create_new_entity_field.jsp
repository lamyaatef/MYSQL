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
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252">
<link rel="stylesheet" type="text/css"
	href="../resources/css/Template1.css">
</head>

<body>
<form name='CCMform' id='CCMform' action='' method='post'><script>
 
function fillControl(controlName,controlValue)
{
	alert(controlName);
	alert(controlValue);
	document.getElementById(controlName).value = controlValue;
}
</script>


<script>
function IsNumeric(sText)

{
   var ValidChars = "0123456789";
   var IsNumber=true;
   var Char;

 
   for (i = 0; i < sText.length && IsNumber == true; i++) 
      { 
      Char = sText.charAt(i); 
      if (ValidChars.indexOf(Char) == -1) 
         {
         IsNumber = false;
         alert('Order must be a number');
         }
      }
   return IsNumber;
   
   }



</script>

<script>
  function checkBeforeSubmit()
  {
    var label = document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_FIELD_LABEL_NAME%>.value;
    var order = document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_FIELD_ORDER%>.value;
    var type = document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_FIELD_TYPE_ID%>.value;
    if(label == "")
    {
      alert("Must enter a field name");
      return;
    }


    if(order== "")
    {
      alert("Must enter a order");
      return;
    }
    
    if(order== "")
    {
      alert("Must enter a order");
      return;
      
    }

    if(order!= "" || order !=null)
    {
      
    	IsNumeric(order);
    }

    if(type == "" || type==null)
    {
      alert("Must choose a type");
      return;
    }

    
    else
    {
    	CCMform.submit();
    }
  }
  </script> <%
 	HashMap objDataHashMap = (HashMap) request
 			.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String entityTypeId = (String) objDataHashMap
	.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
 	Vector vecEntityTypeField = (Vector) objDataHashMap
 			.get(CCMInterfaceKey.VECTOR_ENTITY_TYPE_FIELDS);
 	
 	Vector vecEntityFieldTypes = (Vector) objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_FIELD_TYPES);
 	
 	String nextAction = CCMInterfaceKey.ACTION_SAVE_ENTITY_FIELD_DEFINITION;
 	
 	System.out.println("vec size isssss"+vecEntityTypeField.size());
 	String orderId="";
 	
 	String isMandatory="";
 	String fieldLabel="";
 	
 	 String entity_field_type_id="";
 	String fieldrecord="";
 	
 	
	
	//System.out.println("111111111111");
		
	for (int i = 0; i < vecEntityTypeField.size(); i++) {
		  nextAction = CCMInterfaceKey.ACTION_UPDATE_ENTITY_FIELD_DEFINITION;
		 EntityTypeFieldModel entityTypeFieldModel = ( EntityTypeFieldModel) vecEntityTypeField.get(i);
		 
		 EntityFieldTypeModel entityFieldTypeModel = (EntityFieldTypeModel) vecEntityFieldTypes
			.get(i);
	      orderId= entityTypeFieldModel.getOrderId() ;
	       
		  isMandatory= entityTypeFieldModel .getIsMandatory();
		  fieldLabel= entityTypeFieldModel .getEntityFieldLabel();
		  
		    entity_field_type_id= entityTypeFieldModel .getEntityFieldTypeId();

		  //CCM_ENTITY_FIELD_RECORD
		    fieldrecord= entityTypeFieldModel.getEntityFieldId();
		  
		
		  
			out.println("<input type=\"hidden\" name=\""
		 			+ CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_FIELD_ID+ "\""
		 			+ " value=\"" + fieldrecord+ "\">");
		  
	
	    }

 	out.println("<input type=\"hidden\" name=\""
		+ CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID + "\""
 	+ " value=\"" + entityTypeId + "\">");
 	
 	System.out.println("lasttttttttttttttttttttttttttttt type_id"+ entityTypeId);

 %>
<CENTER>
<H2>Edit Entity Field</H2>
</CENTER>
<%
	out.println("<input type=\"hidden\" name=\""
			+ InterfaceKey.HASHMAP_KEY_ACTION + "\"" + " value=\""
			+ "\">");


%>
<center>
<TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
	cellSpacing=2 cellPadding=1 width="746" border=1>
	
	
		<TR class=TableTextNote>
		<TD class=TableTextNote width=\"20%\">FIELD NAME</TD>

		<TD class="TableTextNote" colSpan=4><INPUT
			style="WIDTH: 200px; HEIGHT: 22px" size=20
			name="<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_FIELD_LABEL_NAME%>"
			id="<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_FIELD_LABEL_NAME%>"></td>
	</tr>
	
	
	
	<TR class="TableTextNote">
		<TD class="TableTextNote" width=\"20%\">Order</TD>
		<TD class="TableTextNote" colSpan=4><INPUT
			style="WIDTH: 200px; HEIGHT: 22px" size=20
			name="<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_FIELD_ORDER%>"
			id="<%=CCMInterfaceKey.INPUT_TEXT_ENTITY_FIELD_ORDER%>"></td>
	</tr>


	<TR class=TableTextNote>
		<TD class=TableTextNote width="20%">Field Type</TD>
		
		
		 <td>
          <%
        out.println("<select name='"+CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_FIELD_TYPE_ID+"' id='"+CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_FIELD_TYPE_ID+"' > ");
        out.println("<option value=\"\"></option>");
        int vecSize = vecEntityFieldTypes.size();
        for (int i=0;i<vecSize;i++){

        	
        	EntityFieldTypeModel entityFieldTypeModel = (EntityFieldTypeModel) vecEntityFieldTypes.get(i);
        	
        	 String 	entityFieldTypeId = entityFieldTypeModel.getEntityFieldTypeId();
        	 String      entityyFieldTypeName = entityFieldTypeModel.getEntityyFieldType();
        	 String entityFieldTypeSelected = "";
             if(entityFieldTypeId.compareTo(entity_field_type_id)==0)
             {
            	 entityFieldTypeSelected = "selected";
             }
             %>
             <option value='<%=entityFieldTypeId%>' <%=entityFieldTypeSelected%>><%=entityyFieldTypeName%></option>
             <% 
         /* out.println("<option value="+entityFieldTypeId+">"+entityyFieldTypeName+"</option>"); */ 
        }
        out.println("</select>");
     
%>
 </td>

<td class=TableTextNote nowrap> IS Mandatory</td>
	<td class=TableTextNote><select name="IS_Mandatory">
		<option value=''></option>
		<option value="1">yes</option>
		<option value="0">no</option>
</select></td>
</table>
<%if  (vecEntityTypeField!=null) 
	%>
<script>

		document.CCMform.<%out.print(CCMInterfaceKey.INPUT_TEXT_ENTITY_FIELD_LABEL_NAME);%>.value='<%out.print(fieldLabel);%>';
        document.CCMform.<%out.print(CCMInterfaceKey.INPUT_TEXT_ENTITY_FIELD_ORDER);%>.value='<%out.print(orderId);%>';
        document.CCMform.<%out.print(CCMInterfaceKey.INPUT_IS_MANADATORY);%>.value='<%out.print(isMandatory);%>';    
        document.CCMform.<%out.print(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_FIELD_TYPE_ID);%>.value='<%out.print(entity_field_type_id);%>';
      
  
 </script>
</center>


<center><br>
<%
           
	          System.out.println("The action issssssssssssssss " + nextAction);
	
			out.print("<INPUT class=button type='button' value=\" Save \" name=\"Save\" id=\"details\" onclick=\"document.CCMform."
					+ InterfaceKey.HASHMAP_KEY_ACTION
					+ ".value = '"
					+ nextAction
					+ "';" + "checkBeforeSubmit();\">");
%> <br>
</center>
</form>
</body>
</html>
