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
         import = "com.mobinil.sds.core.system.ccm.entity.model.*"
       
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <script type="text/javascript">
      function loadField(id)
      {
		
          document.CCMform.fieldId.value=id;
      }

      function  Myalert()
      {

    	  alert('Cannot Delete this field  Data exists '); 


       }
      </script>
    </head>
  <body>
  <form  name='CCMform' id='CCMform' action='' method='post'>  
<%


  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String entiyTypeId=(String)objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
  System.out.println("Type:"+entiyTypeId);
 // Vector vecSurveyList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
 // Vector vecSurveyStatusList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
    Vector vecetypefields  = (Vector) objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_TYPE_FIELDS);
  //if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  //{
    //String strMsg = (String) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_MESSAGE) ;
    //out.println("<script>alert('"+strMsg+"');</script>");
  //}
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
		  
                  " value=\""+"\">");
  out.println("<input type=\"hidden\" name=fieldId value=\""+"\">");

  

	String Message="";
	Message =(String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
	if (Message!=null)
	{
		  out.print( "<script>");
		  out.print("Myalert();");
	 
	      out.print( "</script>");
		
	}
  
%>  
    <CENTER>
      <H2> Entity Type Fields</H2>
    </CENTER>

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR class=TableHeader>
        <td width="25%" nowrap align=middle>Order</td>
        <td width="25%" nowrap align=middle>Label</td>
        <TD width="10%" noWrap align=middle>IS Mandatory</TD>
        <TD width="25%" noWrap align=middle>Field Type</TD>
        <TD width="10%" noWrap align=middle>Edit</TD>
        <TD width="10%" noWrap align=middle>Delete</TD>
      </TR>
      <%
  
        
        String entityFieldId="";
    	String orderId="";
    	String entityTypeId="";
    	String sqlQuery="";
    	String entityFieldLabel="";
    	String entityFieldTypeId="";
    	String isMandatory="";
    	String entityFieldType="";

        
        for (int i=0; i<vecetypefields.size(); i++)
        {
        	EntityTypeFieldModel entitytypefieldsModel = (EntityTypeFieldModel) vecetypefields.get(i);            
        	entityFieldId = entitytypefieldsModel.getEntityFieldId();
        	orderId = entitytypefieldsModel.getOrderId();
        	//System.out.println("The order id isssssssssss " + orderId);
        	entityTypeId  = entitytypefieldsModel.getEntityTypeId();
        	System.out.println("The entity type  id isssssssssss " + entityTypeId );
        	sqlQuery = entitytypefieldsModel.getSqlQuery();
        	entityFieldLabel= entitytypefieldsModel.getEntityFieldLabel();
        	System.out.println("The label isssssssss " + entityFieldLabel);
        	entityFieldTypeId = entitytypefieldsModel.getEntityFieldTypeId();
        	isMandatory= entitytypefieldsModel.getIsMandatory(); 
        	System.out.println ("Is Mandatory isssssssssssss " + isMandatory);
        	if(isMandatory.compareTo("1")==0)
        		isMandatory = "Yes";
        	    else
        	    isMandatory = "No";
        	
        	
        	entityFieldType=entitytypefieldsModel.getEntityFieldTypeName();
  	 
          
      %>
      <TR class=TableTextNote>
        <td width="20%" nowrap align=middle><%=orderId%></td>
        <td width="20%" nowrap align=middle><%=entityFieldLabel%></td>
         <td width="20%" nowrap align=middle><%=isMandatory%></td>
        <TD width="10%" noWrap align=middle><%=entityFieldType%></TD>
        <TD width="10%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"Edit\"  name =\"Edit \"    onclick=\"CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_EDIT_ENTITY_FIELDS_DEFINITION+"';"+
        "loadField("+entitytypefieldsModel.getEntityFieldId()+");CCMform.submit();\">"); 
        %>
        </TD>
        
        <TD width="10%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"Delete\"  name =\"Delete\"    onclick=\"CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_DELETE_ENTITY_FIELDS_DEFINITION+"';"+
        "loadField("+entitytypefieldsModel.getEntityFieldId()+");CCMform.submit();\">"); 
        %>
        </TD>

      </TR>
       
      <%
      }
      %>
    </table>

    <br><br>
      <center>
      <%
        
       out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID+"\""+
                  " value=\""+entiyTypeId+"\">");
       // System.out.println("entity typeeeeeeeeeeeee issssssssssss"+entiyTypeId);
     out.println("<INPUT class=button type=button value= \"Add New Field\"  name =\"add new \"    onclick=\"CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_CREATE_NEW_ENTITY_FIELD+"';"+
       "CCMform.submit();\">");
	
      %>
      </center>
      
  </form>
    
  </body>
</html>
