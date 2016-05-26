<%-- 
    Document   : assign_stk_distributer
    Created on : 17/10/2010, 13:48:36
    Author     : Ahmed Adel
--%>

<%@page import="com.lowagie.text.Document"%>
<%@ page contentType="text/html;charset=windows-1252"
         import="org.apache.commons.fileupload.*"
         import="java.util.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.scm.*"
         import="com.mobinil.sds.core.system.sa.importdata.model.*"
         import="com.mobinil.sds.core.system.scm.model.*"
         import ="java.io.*"
         %>

<HTML>
    <HEAD>
        <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
        <TITLE>Data Import</TITLE>



</HEAD>
<script>
    function isInteger(s)
    {   var i;
        for (i = 0; i < s.length; i++)
        {
            // Check that current character is number.
            var c = s.charAt(i);
            if (((c < "0") || (c > "9"))) return false;
        }
        if(s>0)
        {
            return true;
        }
    }
    function MyForm()
    {

        var i=isInteger(document.myform.<%out.print(SCMInterfaceKey.STK_QUANTITY);%>.value);

        if(i)
        {
            document.myform.action=document.myform.action= document.myform.action+'&'+'<%out.print(SCMInterfaceKey.DISTRIBUTER_CONTROL_SELECT + "");%>='+document.myform.<%out.print(SCMInterfaceKey.DISTRIBUTER_CONTROL_SELECT);%>.value+
                '&'+'<%out.print(SCMInterfaceKey.STK_QUANTITY + "");%>='+document.myform.<%out.print(SCMInterfaceKey.STK_QUANTITY);%>.value
            document.myform.submit();
        }else{
            alert("Quantity must be number");
        }
    }

    function show()
    {
    
        document.getElementById("Excel").style.display="";
    
    }

</script>
<%
        String appName = request.getContextPath();
        HashMap dataHashMap = null;
         dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
         String USER_ID = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
         String Slach=System.getProperty("file.separator");
	String baseDirectory = request.getRealPath(Slach+"scm"+Slach+"upload"+Slach);
        String formAction = appName + "/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    + InterfaceKey.HASHMAP_KEY_ACTION + "="
                    + SCMInterfaceKey.ASSIGN_STK_DISTRIBUTER_PROCESS + "&" + InterfaceKey.HASHMAP_KEY_USER_ID + "=" + USER_ID + "&" + SCMInterfaceKey.BASE_DIR + "=" + baseDirectory;


       String alert = (String) dataHashMap.get(SCMInterfaceKey.DIST_MESSAGE);

       String disCode=(String) dataHashMap.get(SCMInterfaceKey.POS_CODE);

       Vector distributersList=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_DISTRIBUTERS);

       if(distributersList.size()!=0)
       {
%>

<div align="center">


        <br>
        <br>
        <h2>Assign STK To Distributer</h2>
        <br>
        <br>

    <form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data" align="center">

        <table style="BORDER-COLLAPSE: collapse" width="80%" border="1" align="center" cellpadding="3" cellspacing="3">
            <tr>
                <%
                           if (disCode== null) {
                                disCode="";
                            }
                            if (dataHashMap.get(SCMInterfaceKey.STK_QUANTITY) == null) {
                                dataHashMap.put(SCMInterfaceKey.STK_QUANTITY, "");
                            }

                %>
               <td class="TableTextNote" align="center">Distributer Name</td>

                <td> <select name="<%=SCMInterfaceKey.DISTRIBUTER_CONTROL_SELECT%> + " id="<%=SCMInterfaceKey.DISTRIBUTER_CONTROL_SELECT%>">);
     <% for(int i = 0 ; i < distributersList.size() ; i++)
      {
         POSModel DistModel = (POSModel) distributersList.get(i);%>
         <option value=<%=DistModel.getPOS_Code()%> <%if(DistModel.getPOS_Code().equals(disCode)) out.print("selected");%> ><%=DistModel.getPOS_NAME()%></option>
      <%}%>
      </select>
               </td>
                <td class="TableTextNote" align="center">STKs Quantity</td>
                <td class="tableTextNote" align="center">
                    <input type="text" name="<%=SCMInterfaceKey.STK_QUANTITY%>" id="<%=SCMInterfaceKey.STK_QUANTITY%>" value="<%=dataHashMap.get(SCMInterfaceKey.STK_QUANTITY)%>" /></td>
            </tr>
        </table>
        <br>
        <br>
        <center>
            <input class="button"  type="button" name="Submit" value="Save" onclick="MyForm();">            
        </center>

    </form>
</div>


<div name="Excel" id="Excel" align="center" style="display: none" >
    <%
                
                session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT ,dataHashMap);
                out.println("<form action=\"com.mobinil.sds.web.controller.UploadingFileServlet\" name=\"GenerateSheet\" method=\"post\">");
                out.println("<input class=\"button\"  type=\"submit\" name=\"Submit\" value=\"Download Excel Sheet\" onclick=\"Sheet();\">");
                out.println("</form>");
                
    %>
  
</div>
<%}else{%> <h1>There's No distributers</h1>
<%}%>
<%
            if (alert != null) {
                out.println("<script>");
                out.println("alert(\"" + alert + "\");");
                out.println("</script>");

                if (alert.compareTo("Operation Completed Sucessfully") == 0) {
                    out.println("<script>");
                    out.println("show();");
                    out.println("</script>");
                }
            }
%>
<script>
function Sheet()
{
    document.GenerateSheet.Submit.disabled=true;
    document.GenerateSheet.submit();


}

</script>