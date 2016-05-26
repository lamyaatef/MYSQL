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
         import ="com.mobinil.sds.core.system.ccm.entity.model.*"
       
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Entity List</title>
</head>
 <body>

    <CENTER>
      <H2>Entity Detail</H2>
    </CENTER>
  <form  name='CCMform' id='CCMform' action='' method='post'>  

  <%

     HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	 Vector vecEntityType = (Vector)objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_TYPES);
	 String strUserID     =   (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

	 String strEntityTypelId =   (String)objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
	 String strEntitylType =     (String)objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_NAME);
	 
	 
      Vector vecEntityadditionalFieldLabel =(Vector) objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL);
     Vector vecEntityMandatoryData= (Vector)   objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA);
     
      Vector vecEntityAditionalFieldData=(Vector)   objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_DATA);
	    
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                " value=\""+"\">");   

     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                " value=\""+strUserID+"\">"); 

                  
   // out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_REQUEST_ID+"\""+
   //               " value=\""+"\">"); 
            
%>  
     

     <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
     
  <%
  
  if(vecEntityAditionalFieldData.size()!=0)
   {
    %>
    <tr class=TableHeader>
      <td>Entity Name</td>
      <td>Entity Code</td>
      <td>Entity Adddress</td>
      
         <%    
    
     for (int i=0;i<vecEntityadditionalFieldLabel.size();i++){
    	 EntityAditionalFieldLabelModel model = (EntityAditionalFieldLabelModel) vecEntityadditionalFieldLabel.get(i);
 	     String EntityFieldLabel = model.getEntityFieldLabel();
         %>
      <td > <%= EntityFieldLabel %></td>  
   <%
   
  }
   %>
   </tr>
   
     <% 
      
        for(int i=0;i<vecEntityMandatoryData.size();i++)
    {
      EntityMandatoryDataModel entityMandatoryDataModel = (EntityMandatoryDataModel) vecEntityMandatoryData.get(i);
       String entityId=entityMandatoryDataModel.getEntityId();
       
       
      String entityName= entityMandatoryDataModel.getEntityyName();
      String entityCode= entityMandatoryDataModel.getEntityCode();
      String entityAddress= entityMandatoryDataModel.getEntityAdress();
      
   

     %>

      <tr class=<%=InterfaceKey.STYLE[i%2]%>>
        <td><%=entityName%></td>
        <td><%=entityCode%></td>
        <td><%=entityAddress%></td>
        
     <% 

     
 
          for(int j=0;j<vecEntityAditionalFieldData.size();j++)
          {
           EntityAditionalFieldModel model = (EntityAditionalFieldModel)vecEntityAditionalFieldData.get(j);
       	    String fieldValue = model.getEntityFieldValue();
       	    System.out.println("The optional  value is " + fieldValue);
       	    if(model.getEntityId().equals(entityId))
       	   {
       	    
     %>
          <td><%=fieldValue%></td>  
          
     <%  
       	    }
      }	  
    
     %>            
        <% 
        }
        %>
        <% 
        }
        %>
    </tr> 

     </TABLE>
     <br><br>
     <center>
     <% 
        out.println("<INPUT class=button type=button value= \"Back\"  name =\"Back \"    onclick=\"CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_VIEW_ENTITY_MANAGEMENT+"';"+
       "CCMform.submit();\">");
        
        %>
        </center>
    </body>
    </html>

