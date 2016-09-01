
<%@page import="com.mobinil.sds.core.system.paymentFileHistory.model.PaymentFileModel"%>
<%@page import="com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.paymentHistory.model.PaymentHistoryFileModel"%>
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
                String Slach = System.getProperty("file.separator");
                String ip = request.getLocalAddr();
                Vector files = (Vector) objDataHashMap.get(AdministrationInterfaceKey.VECTOR_FILES);
                out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_ACTION + "\""
                        + " value=\"" + "\">");
                out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_USER_ID + "\""
                        + " value=\"" + request.getSession().getValue(InterfaceKey.HASHMAP_KEY_USER_ID) + "\">");

                out.println("<input type=\"hidden\" name=fieldId id=fieldId value=\"" + "\">");
                out.println("<input type=\"hidden\" name=statusStr id=statusStr value=\"" + "\">");
                out.println("<input type=\"hidden\" name=baseDirectory id=baseDirectory value=\"" + "\">");    
                //String hidden_action=(String) objDataHashMap.get(AuthResInterfaceKey.HIDDEN_ACTION);
                //System.out.println("action issssss"+hidden_action);

                String nextAction = "";
                nextAction = AdministrationInterfaceKey.ACTION_DELETE_HISTORY_FILE;

                String nextAction1 = "";
                nextAction1 = AuthResInterfaceKey.ACTION_VIEW_STATISTICS;

                //lamya
                String nextAction2 = "";
                nextAction2 = AdministrationInterfaceKey.ACTION_EXPORT_PAYMENT_LEVEL_HISTORY;

                // out.print (hidden_action);





            %> 
            <input type="hidden" id="test" name="<%=SCMInterfaceKey.BASE_DIRECTION%>" value="<%=request.getRealPath(Slach + "scm" + Slach + "upload" + Slach)%>">
            <CENTER>
                <H2>Show Payment Level History Files</H2>
            </CENTER>

            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
                
                <TR class=TableHeader>
                    <td width="10%" nowrap align=middle>File Id</td>
                    <td width="20%" nowrap align=middle>User Name</td>
                    <td width="20%" nowrap align=middle>File Timestamp</td>
                    <td width="20%" nowrap align=middle>Month</td>
                    <td width="20%" nowrap align=middle>Year</td>
                    <td width="30%" nowrap align=middle>Status</td>
                    <td width="20%" nowrap align=middle>Delete</td>
                    <td width="20%" nowrap align=middle>Export</td>
                

                </TR>
                <%

                    String FileId = "";
                    String UserName="";
                    String POSCode = "";
                    String FileTimestamp = "";
                    String Month="";
                    String Year="";
                    String ChannelName = "";
                    String PayLevelName = "";
                    String Status="";
                    
                    
                    String base = request.getRealPath(Slach + "scm" + Slach + "upload" + Slach);
                    
                    if (files !=null)
                    {
                    for (int i = 0; i < files.size(); i++) {
                        PaymentFileModel model = (PaymentFileModel) files.get(i);
                        FileId = model.getHISTORY_FILE_ID();
                        UserName = model.getUSERNAME();
                        Month = model.getFILE_MONTH();
                        Year = model.getFILE_YEAR();
                        FileTimestamp = model.getFILE_TIMESTAMP();
                        Status = model.getSTATUS_NAME();
                %>
                <TR class=TableTextNote>
                    <td width="20%" nowrap align=middle><%=FileId%></td>
                    <td width="40%" nowrap align=middle><%=UserName%></td>
                    <td width="40%" nowrap align=middle><%=FileTimestamp%></td>
                    <td width="40%" nowrap align=middle><%=Month%></td>
                    <td width="40%" nowrap align=middle><%=Year%></td>
                    <td width="40%" nowrap align=middle><%=Status%></td>

                    <TD width="10%" noWrap align=middle>
                        <%

                            if (Status.equalsIgnoreCase("Deleted")) {
                                out.println("<INPUT class=button type=button  disabled  value= \"Delete\"  name =\"Delete \" > ");

                            } else {
                                out.println("<INPUT class=button type=button    value= \"Delete\"  name =\"Delete \"onclick=\""
                                        + "loadDeleteField(" + model.getHISTORY_FILE_ID()+ ",'" + Status + "');\">");
                            }
                            
                            
                          
                        %>
                    </TD>

                    <TD width="10%" noWrap align=middle>
                        <%

                            out.println("<INPUT class=button type=button    value= \"Export\"  name =\"Export \"onclick=\""
                                        + "loadExportField(" + model.getHISTORY_FILE_ID()+ ",'" + Status + "', '"+base+"');\">");
                           
                            
                            
                          
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
                function loadExportField(id,status,base)
                {

                    AUTHform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value='<%=nextAction2%>';
                    document.AUTHform.fieldId.value=id;
                    document.AUTHform.statusStr.value=status;
                    document.AUTHform.baseDirectory.value=base;
                    AUTHform.submit();
          
                }
            </script>
        </form>

    </body>
</html>