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
                Vector files = (Vector) objDataHashMap.get(AuthResInterfaceKey.VECTOR_FILES);
                out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_ACTION + "\""
                        + " value=\"" + "\">");
                out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_USER_ID + "\""
                        + " value=\"" + request.getSession().getValue(InterfaceKey.HASHMAP_KEY_USER_ID) + "\">");

                out.println("<input type=\"hidden\" name=fieldId id=fieldId value=\"" + "\">");
                out.println("<input type=\"hidden\" name=statusStr id=statusStr value=\"" + "\">");

                //String hidden_action=(String) objDataHashMap.get(AuthResInterfaceKey.HIDDEN_ACTION);
                //System.out.println("action issssss"+hidden_action);

                String nextAction = "";
                nextAction = AuthResInterfaceKey.ACTION_DELETE_AUTH_RES_FILE;

                String nextAction1 = "";
                nextAction1 = AuthResInterfaceKey.ACTION_VIEW_STATISTICS;


                // out.print (hidden_action);





            %>  
            <CENTER>
                <H2>Authentication Result File </H2>
            </CENTER>

            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
                <TR class=TableHeader>
                    <td width="10%" nowrap align=middle>File Id</td>
                    <td width="20%" nowrap align=middle>Year</td>
                    <td width="20%" nowrap align=middle>Month</td>
                    <td width="20%" nowrap align=middle>Label</td>
                    <td width="30%" nowrap align=middle>Status</td>
                    <td width="20%" nowrap align=middle>Delete</td>
                    <td width="30%" nowrap align=middle>View Statistics</td>


                </TR>
                <%

                    String FileId = "";
                    String Year = "";
                    String Month = "";
                    String Status = "";
                    String Label = "";
                    for (int i = 0; i < files.size(); i++) {
                        AuthResFileModel model = (AuthResFileModel) files.get(i);
                        FileId = model.getFILE_ID();
                        Year = model.getYEAR();
                        Month = model.getMONTH();
                        Status = model.getSTATUS();
                        Label = model.getLABEL_NAME();


                %>
                <TR class=TableTextNote>
                    <td width="20%" nowrap align=middle><%=FileId%></td>
                    <td width="40%" nowrap align=middle><%=Year%></td>
                    <td width="40%" nowrap align=middle><%=Month%></td>
                    <td width="40%" nowrap align=middle><%=Label%></td>
                    <td width="40%" nowrap align=middle><%=Status%></td>

                    <TD width="10%" noWrap align=middle>
                        <%

                            if (Status.equalsIgnoreCase("Deleted")) {
                                out.println("<INPUT class=button type=button  disabled  value= \"Delete\"  name =\"Delete \" > ");

                            } else {
                                out.println("<INPUT class=button type=button    value= \"Delete\"  name =\"Delete \"onclick=\""
                                        + "loadDeleteField(" + model.getFILE_ID() + ",'" + Status + "');\">");
                            }
                        %>
                    </TD>



                    <TD width="10%" noWrap align=middle>
                        <%

                            if (Status.equalsIgnoreCase("processing") || Status.equalsIgnoreCase("Deleted")) {
                                out.println("<INPUT class=button type=button  disabled  value= \"View Statistics\"  name =\"View Statistics \" > ");

                            } else {
                                out.println("<INPUT class=button type=button    value= \"View Statistics\"  name =\"View Statistics \"onclick=\""
                                        + "loadField(" + model.getFILE_ID() + ",'');\">");
                            }
                        %>
                    </TD>
                    <%
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