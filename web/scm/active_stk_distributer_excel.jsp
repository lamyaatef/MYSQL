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
         import ="java.io.*"
         import="com.mobinil.sds.core.system.scm.model.*"
         %>

<HTML>
    <HEAD>
        <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
        <TITLE>Data Import</TITLE>


    <html>
        <body>
</HEAD>
<script>

    function MyForm()
    {
            if(document.myform.myfile.value.lastIndexOf(".xls")==-1/*&&document.myform.myfile.value.lastIndexOf(".xlsx")==-1*/)
             {
             alert("Please upload only Excel file");
            return false;
             }

            document.myform.action=document.myform.action+'&'+'<%out.print(SCMInterfaceKey.DISTRIBUTER_CONTROL_SELECT + "");%>='+document.myform.<%out.print(SCMInterfaceKey.DISTRIBUTER_CONTROL_SELECT);%>.value+
                '&'+'<%out.print(SCMInterfaceKey.IMPORT_TABLE + "");%>='+'<%=SCMInterfaceKey.ACTIVE_STK_DIST_TABLE%>'

            document.myform.submit();

    }

</script>
<%
            String appName = request.getContextPath();
            String Slach = System.getProperty("file.separator");
            HashMap dataHashMap = null;
            dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String USER_ID = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String formAction = appName + "/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    + InterfaceKey.HASHMAP_KEY_ACTION + "="
                    + SCMInterfaceKey.ACTIVE_STK_DIST_EXCEL_PROCESS + "&" + InterfaceKey.HASHMAP_KEY_USER_ID + "=" + USER_ID ;

            String formNextAction = appName + "/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    + InterfaceKey.HASHMAP_KEY_ACTION + "="
                    + SCMInterfaceKey.ACTIVE_NEXT_TO_UPLOAD_ACTIVATED_STKS + "&" + InterfaceKey.HASHMAP_KEY_USER_ID + "=" + USER_ID ;
                    
            Object strIsContinue = dataHashMap.get(SCMInterfaceKey.IS_CONTINUE);
            
            boolean isContinue = strIsContinue !=null && ((String)strIsContinue).compareTo("true")==0? true : false ;
            boolean isAllFaild = strIsContinue !=null && ((String)strIsContinue).compareTo("false")==0? false : true ;

            if (isContinue) {
                String oldPath = (String)dataHashMap.get(SCMInterfaceKey.OLD_FILE_PATH);

                            dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, oldPath.substring(oldPath.lastIndexOf(Slach)+1, oldPath.length()));
                            dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "scm" + Slach + "upload" + Slach);
                            session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, dataHashMap);
                        }
            String alert = (String) dataHashMap.get(SCMInterfaceKey.DIST_MESSAGE);

             String disCode=(String) dataHashMap.get(SCMInterfaceKey.POS_CODE);

             Vector distributersList=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_DISTRIBUTERS);

              if(distributersList.size()!=0)
       {
%>

<div align="center">


        <br>
        <br>
        <h2>Active STK For Distributer</h2>
        <br>
        <br>

    <form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data" align="center">

        <table style="BORDER-COLLAPSE: collapse" width="80%" border="1" align="center" cellpadding="3" cellspacing="3">
            <tr>
                <%
                            if (dataHashMap.get(SCMInterfaceKey.POS_CODE) == null) {
                                dataHashMap.put(SCMInterfaceKey.POS_CODE, "");
                            }

                %>
                <td class="TableTextNote" align="center">Distributer Code </td>
           <td> <select name="<%=SCMInterfaceKey.DISTRIBUTER_CONTROL_SELECT%> + " id="<%=SCMInterfaceKey.DISTRIBUTER_CONTROL_SELECT%>">);
     <% for(int i = 0 ; i < distributersList.size() ; i++)
      {
         POSModel DistModel = (POSModel) distributersList.get(i);%>
         <option value=<%=DistModel.getPOS_Code()%> <%if(DistModel.getPOS_Code().equals(disCode)) out.print("selected");%> ><%=DistModel.getPOS_NAME()%></option>
      <%}%>
      </select>
               </td>
            <td class="TableTextNote" align="center">Excel Import File </td>
                <td class="TableTextNote" align="center">
                    <input type="file" name="myfile"></td>
                 </tr>
        </table>
        <br>
        <br>
        <center>
            <input class="button"  type="button" name="Submit" value="Save" onclick="MyForm();">
        </center>

    </form>
</div>
<%
if (isContinue){
    %>

    <div align="center">
    <form action="com.mobinil.sds.web.controller.UploadingFileServlet" name="GenerateSheet" id="GenerateSheet" method="post">
    </form>       
</div>
        <div align="center">
            <form action="<%=formNextAction%>" name="NextForm" id="NextForm" method="post" enctype="multipart/form-data">                
        <center>
            <input class="button"  type="button" name="Submit" value="Next" onclick="document.getElementById('excelActivateDiv').style.display='block';">
            <br>
            <br>
            <div id="excelActivateDiv" style="display: none">
            <table>
                <tr>
                    <td align="center">
                       Activate Excel File
                    </td>
                    <td align="center" >
                        <input type="file" name="myfile">
                    </td>
                </tr>
                <tr>
                    <td align="center" colspan="2">
                       <input class="button"  type="submit" name="Submit" value="Activate" onclick="if(document.NextForm.myfile.value.lastIndexOf('.xls')==-1){alert('Please upload only Excel file');return false;}">
                    </td>

                </tr>
            </table>
            </div>

        </center>

    </form>        
</div>
     <script>
        document.GenerateSheet.submit();
    </script>
<%
}
    if (!isAllFaild) {
                   out.println("<center><h3> Sorry, there is no valid stk in sheet </h3></center>");
               }
%>
   <%} else{%>

<h3> There's No Distributer in System </h3>
<%}%>

</html>
    </body>