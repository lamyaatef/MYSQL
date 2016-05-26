<%@page import="com.mobinil.sds.core.system.nomadFile.model.NomadFileModel"%>
<%@page import="com.mobinil.sds.core.system.nomad.dao.NomadFileDAO"%>
<%@page import="com.mobinil.sds.web.interfaces.sa.AdministrationInterfaceKey"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"

         import = "com.mobinil.sds.core.system.dm.file.model.*"

         %>

<%@page import="com.mobinil.sds.core.system.dataMigration.model.fileModel"%>
<%@page import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.AuthResFileModel"%><html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">      
    </head>
    <body>
        <form  name='AUTHform' id='AUTHform' action='' method='post'>  
            <%

                HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

                String ip = request.getLocalAddr();
                Vector files = (Vector) objDataHashMap.get(AdministrationInterfaceKey.VECTOR_FILES);
                out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_ACTION + "\""
                        + " value=\"" + "\">");
                out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_USER_ID + "\""
                        + " value=\"" + request.getSession().getValue(InterfaceKey.HASHMAP_KEY_USER_ID) + "\">");

                out.println("<input type=\"hidden\" name=fieldId id=fieldId value=\"" + "\">");
                out.println("<input type=\"hidden\" name=statusStr id=statusStr value=\"" + "\">");

                //String hidden_action=(String) objDataHashMap.get(AuthResInterfaceKey.HIDDEN_ACTION);
                //System.out.println("action issssss"+hidden_action);

                String nextAction = "";
                nextAction = AdministrationInterfaceKey.ACTION_DELETE_NOMAD_FILE;

                String nextAction1 = "";
                nextAction1 = AuthResInterfaceKey.ACTION_VIEW_STATISTICS;


                // out.print (hidden_action);





            %>  
            <CENTER>
                <H2>Show Nomad File </H2>
            </CENTER>

            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
                <TR class=TableHeader>
                    <td width="10%" nowrap align=middle>File Id</td>
                    <td width="20%" nowrap align=middle>User Name</td>
                    <td width="20%" nowrap align=middle>File Creation Date</td>
                    <td width="20%" nowrap align=middle>File Upload Date</td>
                    <td width="20%" nowrap align=middle>Minimum Date</td>
                    <td width="20%" nowrap align=middle>Maximum Date</td>
                    <td width="30%" nowrap align=middle>Number of Records</td>
                    <td width="30%" nowrap align=middle>Status</td>
                    <td width="20%" nowrap align=middle>Delete</td>
                    


                </TR>
                <%

                    String FileId = "";
                    String UserId = "";
                    String UserName="";
                    String FileCreateDate = "";
                    String FileUploadDate = "";
                    String MaxDate="";
                    String MinDate="";
                    String Label = "";
                    String Status="";
                    String numberOfRecords = "";
                    if (files !=null)
                    {
                    for (int i = 0; i < files.size(); i++) {
                        NomadFileModel model = (NomadFileModel) files.get(i);
                        FileId = model.getGEN_DCM_NOMAD_FILE_ID();
                        UserId = model.getUSER_ID();
                        UserName = model.getUSERNAME();
                        FileCreateDate = model.getFILE_CREATION_DATE();
                        FileUploadDate = model.getFILE_UPLOAD_DATE();
                        MinDate = model.getMAX_DATE();
                        MaxDate = model.getMIN_DATE();
                        Label = model.getLABEL_ID();
                        numberOfRecords = model.getTOTAL_NUMBER_OF_RECORDS();
                        Status = model.getSTATUS();

                %>
                <TR class=TableTextNote>
                    <td width="20%" nowrap align=middle><%=FileId%></td>
                    <td width="40%" nowrap align=middle><%=UserName%></td>
                    <td width="40%" nowrap align=middle><%=FileCreateDate%></td>
                    <td width="40%" nowrap align=middle><%=FileUploadDate%></td>
                    <td width="40%" nowrap align=middle><%=MinDate%></td>
                    <td width="40%" nowrap align=middle><%=MaxDate%></td>
                    <td width="40%" nowrap align=middle><%=numberOfRecords%></td>
                    <td width="40%" nowrap align=middle><%=Status%></td>

                    <TD width="10%" noWrap align=middle>
                        <%

                            if (Status.equalsIgnoreCase("Deleted")) {
                                out.println("<INPUT class=button type=button  disabled  value= \"Delete\"  name =\"Delete \" > ");

                            } else {
                                out.println("<INPUT class=button type=button    value= \"Delete\"  name =\"Delete \"onclick=\""
                                        + "loadDeleteField(" + model.getGEN_DCM_NOMAD_FILE_ID()+ ",'" + Status + "');\">");
                            }
                        %>
                    </TD>



                    
                    <%
                        }
                    }

                    %>
                </TR>


            </table>
            <script type="text/javascript">
                function loadField(id,status)
                {
                    AUTHform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value='<%=nextAction1%>';
                    document.AUTHform.fieldId.value=id;
                    document.AUTHform.statusStr.value=status;
                    AUTHform.submit();
                }
                function loadDeleteField(id,status)
                {

                    AUTHform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value='<%=nextAction%>';
                    document.AUTHform.fieldId.value=id;
                    document.AUTHform.statusStr.value=status;
                    AUTHform.submit();
          
                }
            </script>
        </form>

    </body>
</html>