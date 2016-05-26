<%-- 
    Document   : generate_pos_branch_code
    Created on : Dec 5, 2010, 7:08:15 PM
    Author     : sand
--%>

<%@page 
          import ="com.mobinil.sds.core.utilities.Utility.*"
          import = "java.util.*"
          import = "java.io.*"
          import = "javax.servlet.*"
          import = "javax.servlet.http.*"
          import = "javax.servlet.jsp.*"
          import = "java.io.PrintWriter"
          import = "java.io.IOException"
          import = "com.mobinil.sds.web.interfaces.scm.*"
          import = "com.mobinil.sds.core.system.fn.addfunction.model.*"
          import = "com.mobinil.sds.core.system.fn.importdata.model.*"
          import = "com.mobinil.sds.web.interfaces.*"
          import = "com.mobinil.sds.web.interfaces.fn.*"
          import = "com.mobinil.sds.web.interfaces.gn.querybuilder.*"
          import = "com.mobinil.sds.web.interfaces.dcm.*"
          import="com.mobinil.sds.core.system.dcm.pos.dao.*"
          import="com.mobinil.sds.core.system.dcm.genericModel.*"
          import="com.mobinil.sds.core.system.dcm.genericModel.DAO.*"
          import="com.mobinil.sds.core.system.dcm.pos.model.*"
          import="com.mobinil.sds.core.system.dcm.region.model.*"
          import="com.mobinil.sds.core.system.request.model.*"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>POS Code Generation</title>
    </head>
    <% String appName = request.getContextPath();
    HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    request.getSession(false).putValue("hm_for_pos_data_entry", objDataHashMap);
    String posLevel = (String)objDataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL);
    String posChannel = (String)objDataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL);
    String posPayment = (String)objDataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL);
    
/*
    String action=appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
            +InterfaceKey.HASHMAP_KEY_ACTION+"="
            +SCMInterfaceKey.ACTION_RETURN_GENERATE_CODE;
 * */
    %>
    <body>
        <center>
        <h1>POS Code Generation</h1>
        <br>
        <br>
        
        <form name="generateCode" method="post" >
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=SCMInterfaceKey.ACTION_RETURN_GENERATE_CODE%>">
            <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL%>" value="<%=posLevel%>">
            <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL%>" value="<%=posChannel%>">
            <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL%>" value="<%=posPayment%>">
            
        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border=1>
       <tr>
        <td  class="TableTextNote" align="center">Parent Code</td>
        <td  class="TableTextNote" align="center"><input type="text" name="<%=SCMInterfaceKey.PARENT_POS_CODE%>"></td>
        <td  class="TableTextNote" align="center"><input type="button" name="Generate" value="Generate Code" onclick="getcode();"></td>
        </tr>
        </table>
        </form>
        </center>
    </body>
 <script>
  function getcode()
  {
      if(document.generateCode.<%=SCMInterfaceKey.PARENT_POS_CODE%>.value=="")
      {
            alert("Please Write Parent Code");
          return;
      }else
          document.generateCode.submit();
  }
 </script>


</html>
