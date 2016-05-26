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

 <html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Entity List</title>
</head>
 <body>
  <form  name='CCMform' id='CCMform' action='' method='post' >  

      
   <script type="text/javascript">
      function loadField(id)
      {
		
          document.CCMform.entityId.value=id;
      }
      </script> 
      
         <script type="text/javascript">
    //  function loadEntityTypeId(typeId)
    //  {
		
      //    document.CCMform.entityTypeId.value=typeId;
   //   }
      </script> 
     
 <script>

    function loadRecordId(id)
    {
		
        document.CCMform.recordId.value=id;
    }

    


    
    </script> 
   
<script>


  function checkBeforeSubmit()
  {
   if(eval("document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID%>.value") == "")
      {
	      	
        alert("Choose Entity Type.");
        
        return;
      }
  
      
    //  if(!IsNumeric(eval("document.CCMform.<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_CODE%>.value")))
    //  {
    //     alert("Entity Code must be a number.");
    //     return; 
   //   }
      CCMform.submit();
    }


   // function setSearchValues(entityCode,entityName,EntityID)
//  {
  //  document.getElementById("<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_CODE%>").value = entityCode;
  //  document.getElementById("<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME%>").value = entityName;
  //  document.getElementById("<%=CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID%>").value =EntityID;

 // }
 
  </script>

  
  
  <%

  



	  //System.out.println("hidden  action isssssssssssssssssssssssssss"+hiddenAction);






  
  
     HashMap objDataHashMap = (HashMap)request.getAttribute

 (InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	 Vector vecEntityType = (Vector)objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_TYPES);
	 
	  String hiddenAction = (String) objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ACTION);

	 
	 String strUserID   =   (String)objDataHashMap.get

 (InterfaceKey.HASHMAP_KEY_USER_ID);

	 String strEntityTypelId =   (String)objDataHashMap.get

 (CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
	 String strEntitylType =     (String)objDataHashMap.get

(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_NAME);
	 
	//  String strEntitylType = (String)objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_NAME);
	  String nextAction="";

	  if (hiddenAction.equals("1")) 
	   {
	   nextAction = CCMInterfaceKey.ACTION_ADD_SUB_ENTITIES_NEW;
	   }
	   
	  if (hiddenAction.equals("2")) 
	   { 	
	  nextAction = CCMInterfaceKey.ACTION_ADD_ENTITIES_FIRST_LEVEL;
	   }
     // Vector vecEntityadditionalFieldLabel =(Vector) objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL);
       String entityTypeName= (String) objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_NAME);
       String  page_header=entityTypeName;
       if (entityTypeName==null){
    	   
    	   page_header="Entity List";
    	   
    	   
       }
      
       System.out.println("type name isssssssssss:"+entityTypeName);
       String  projectId = (String)objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID);
       String  projectName = (String)objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME);
     System.out.println("project id in choose entity jsp issss:"+projectId);
     
      Vector vecEntityMandatoryData= (Vector)   objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA);
     
      Vector vecEntityAditionalFieldData=(Vector)   objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_DATA);
    // prent id to passed to the add sub entity new action in the handler  
     String   parentIdpassed= (String)   objDataHashMap.get(CCMInterfaceKey.INPUT_HIDDEN_PARENT_ID);
    String entityTypeId=(String) objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
      System.out.println("parent idpassed from TRee page issssssss:"+parentIdpassed);
      
      
	    
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                " value=\""+"\">");   
     
	    
     out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.INPUT_HIDDEN_PARENT_ID+"\""+
                " value=\""+"\">");   

   
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                " value=\""+strUserID+"\">");
     
     out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID+"\""+
             " value=\""+"\">");   
     
     
     
     out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME+"\""+
             " value=\""+projectName+"\">");  
     
     out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID+"\""+
             " value=\""+entityTypeId+"\">");  
     
     
     out.println("<input type=\"hidden\" name=recordId value=\""+"\">");
     out.println("<input type=\"hidden\" name=entityId value=\""+"\">");
    
     
     String searcheEntityCode= "";
     String searchEntityName = "";
     String searchEntityType = "";
    
    if(objDataHashMap.containsKey(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_CODE))
    { 
    	searcheEntityCode = (String)objDataHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_CODE);
    	if (searcheEntityCode==null){
    		searcheEntityCode="";
    		
    	}
    }
    if(objDataHashMap.containsKey(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME))
    {
    	searchEntityName = (String)objDataHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME);
      	if (searchEntityName==null){
      		searchEntityName="";
    		
    	}
    }
    if(objDataHashMap.containsKey(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID))
    {
    	searchEntityType = (String)objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
    }
    

            
%> 

<%-- 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" 

border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      
      <TR class=TableTextNote>
        <td align=middle>Entity Name</td>
        <td align=middle><input type='text' name='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME%>' id='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME%>'></td>
        
                <td align=middle>Entity Code</td>
        <td align=middle><input type='text' name='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_CODE%>' id='<%=CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_CODE%>'></td>
        
        <td align=middle>Entity Type</td>
        <td align=middle>
 
 <%

 out.println("<select name='"+CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID+"' id='"+CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID+"' onchange=\"document.CCMform."+CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID+".value = document.CCMform."+CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID+".options[document.CCMform."+CCMInterfaceKey.CONTROL_Select_ENTITY_TYPE_ID+".selectedIndex].text;\"> ");
 out.println("<option value=\"\"></option>");
 
 int vecSize = vecEntityType.size();
 for (int i=0;i<vecSize;i++){
 	EntityTypesModel model = (EntityTypesModel)vecEntityType.get(i);
 	 String EntityID = model.getENTITY_TYPE_ID();
      String EntityName = model.getENTITY_TYPE_NAME();
   out.println("<option value="+EntityID+">"+EntityName+"</option>");  
 }
 out.println("</select>");
 %>
        </td>
      </TR>

      <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchentity\" id=\"searchentity\" onclick=\"document.CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_SEARCH_ENTITY+"';"+
                 "checkBeforeSubmit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','','');\">");          
      
        %>
        </td>
      </tr>
      
</TABLE>

<br><br>
  --%> 
  
  
    <CENTER>
   <center> <h2> <%=page_header%> </h2></center>
    </CENTER>
    
    
     <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
     
  <%
  
  if(vecEntityMandatoryData.size()!=0)
   {
    %>
    <tr class=TableHeader>
        <td width="20%" nowrap align=middle>Entity Name</td>
        <td width="20%" nowrap align=middle>Entity Code</td>
        <td width="20%" nowrap align=middle>Entity Adddress</td>
        <td width="20%" nowrap align=middle>Add</td>
   <!--      <td width="20%" nowrap align=middle>View Entity Details</td>   --> 
   </tr>


     <% 
      
        for(int i=0;i<vecEntityMandatoryData.size();i++)
    {
      EntityMandatoryDataModel entityMandatoryDataModel = (EntityMandatoryDataModel) 

vecEntityMandatoryData.get(i);
       String entityId=entityMandatoryDataModel.getEntityId();
       String recordId=entityMandatoryDataModel.getRecordId();
       
       
      String entityName= entityMandatoryDataModel.getEntityyName();
      String entityCode= entityMandatoryDataModel.getEntityCode();
      String entityAddress= entityMandatoryDataModel.getEntityAdress();
      String entityType= entityMandatoryDataModel.getEntityTypeID();
      
   //   String entityTypeId=entityMandatoryDataModel.getEntityTypeID();
     // out.print("<INPUT class=button type=button value=\" View Detail \" name=\"viewOptionalDetail\" id=\"searchentity\" onclick=\"document.CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_VIEW_ENTITY_OPTIONAL_DETAILS+"';"+
     // "CCMform.submit();\">");
      
   
     %>

      <tr class=<%=InterfaceKey.STYLE[i%2]%>>
        <td><%=entityName%></td>
        <td><%=entityCode%></td>
        <td><%=entityAddress%></td>
        
        
        <td>
       <center>
             <%
             if (hiddenAction.equals("1")){ 
        out.println("<INPUT class=button type=button value= \"Add\"  name =\"Save \"    onclick=\"CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_ADD_SUB_ENTITIES_NEW+"';"+
        	
        		"document.CCMform."+CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID+".value = '"+projectId+"';"+
        		"document.CCMform."+CCMInterfaceKey.INPUT_HIDDEN_PARENT_ID+".value = '"+parentIdpassed+"';"+
                  "loadField("+entityMandatoryDataModel.getEntityId()+");CCMform.submit();\">"); 
             }
             else if (hiddenAction.equals("2"))
             {
            	 
            	   out.println("<INPUT class=button type=button value= \"Add\"  name =\"Add Sub Entity \"    onclick=\"CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_ADD_ENTITIES_FIRST_LEVEL+"';"+
            	        	
                   		"document.CCMform."+CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID+".value = '"+projectId+"';"+
                   	
                             "loadField("+entityMandatoryDataModel.getEntityId()+");CCMform.submit();\">");  
            	 
             }
               
        %>
        </center>
       </td>

       <!--
            <td>
              <center>
             <%
           
     //   out.println("<INPUT class=button type=button value= \"View Entity Details\"  name =\"View \"    onclick=\"CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_VIEW_ENTITY_OPTIONAL_DETAILS+"';"+
        	//	  "loadField("+entityMandatoryDataModel.getEntityId()+");CCMform.submit();\">"); 
     
        %>
           </center>
       </td>
     -->
        <% 
        }
        %>
        <% 
        }
        %>
        
    </tr> 
    

     </TABLE>
     <br> <br>


    
    <%
//if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ACTION))
 // {
  //  String strAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
  //  if(strAction.compareTo(CCMInterfaceKey.ACTION_SEARCH_ENTITY)==0)
  //  {
   //   out.println("<script>setSearchValues('"+searcheEntityCode+"','"+searchEntityName+"','"+searchEntityType+"');</script>");
   // }
 // }
%>
 
    </form>
    </body>
    </html>

