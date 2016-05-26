<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.DAO.*"
         import="com.mobinil.sds.core.system.dcm.function.model.*"
         import="com.mobinil.sds.core.system.dcm.function.dao.*"
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>
:::: SDS ::::
</title>
</head>
<body>
 <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
 <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
 <% 
    HashMap objDataHashMap = new HashMap();
    objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    int rowCount = Integer.parseInt((String)objDataHashMap.get(DCMInterfaceKey.ROW_COUNT));
    Vector columnNames = (Vector) objDataHashMap.get(DCMInterfaceKey.VECTOR_COLUMN_LIST);
    Vector modelsVector = (Vector)objDataHashMap.get(DCMInterfaceKey.VECTOR_TABLE_MODELS);
    
 %>
        <TR class=TableHeader>
<%
      

  %>           <%
      out.println("<TR class=TableTextNote>");
          for(int i = 0 ; i < columnNames.size() ; i++){
            
                String fieldValue = "";
                String fieldName  = "";
                GenericModel gm   = new GenericModel();
                switch(i){
                  case 0:
                  {
                    gm = (GenericModel)modelsVector.get(i);
                    fieldValue = gm.get_field_1_value();
                    fieldName  = gm.get_field_1_name();              
                    out.println("<TD>" + fieldName + "</TD>");
                    }
                  break;
                  case 1:
                  {
                    gm = (GenericModel)modelsVector.get(i);
                    fieldValue = gm.get_field_2_value();
                    fieldName  = gm.get_field_2_name();
                    out.println("<TD>" + fieldName + "</TD>");
                  }
                  break;                  
                  case 2:
                 {
                    gm = (GenericModel)modelsVector.get(i);
                    fieldValue = gm.get_field_3_value();
                    fieldName  = gm.get_field_3_name();
                    out.println("<TD>" + fieldName + "</TD>");
                  }
                  break;                  
                  case 3:
                  {
                    gm = (GenericModel)modelsVector.get(i);
                    fieldValue = gm.get_field_4_value();
                    fieldName  = gm.get_field_4_name();
                    out.println("<TD>" + fieldName + "</TD>");
                  }
                  break;                  
                  case 4:
                  {
                     gm = (GenericModel)modelsVector.get(i);
                    fieldValue = gm.get_field_5_value();
                    fieldName  = gm.get_field_5_name();
                    out.println("<TD>" + fieldName + "</TD>");
                  }
                   break;
                   default:
                   {
                   }
                   
            
                
               
                
          }
               out.println("<TD>" +  "<input type=\"text\" value=\"\" class=\"\">"+ "</TD>");
          
          out.println("<TR class=TableTextNote>");
          }
          %>
          </table>
          <center><input type="submit" value="Save" class="button"></center>
</body>
</html>
