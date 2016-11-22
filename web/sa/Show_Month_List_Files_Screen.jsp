
<%@page import="com.mobinil.sds.core.system.sa.monthList.model.MonthOfTheListModel"%>
<%@page import="com.mobinil.sds.core.system.paymentFileHistory.model.PaymentFileModel"%>
<%@page import="com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.paymentHistory.model.PaymentHistoryFileModel"%>
<%@page import="com.mobinil.sds.core.system.nomadFile.model.NomadFileModel"%>
<%@page import="com.mobinil.sds.core.system.nomad.dao.NomadFileDAO"%>
<%@page import="com.mobinil.sds.web.interfaces.sa.AdministrationInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.monthListFile.model.MonthListFileModel"%>
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
        <form  name='AUTHform' id='AUTHform'  method='post'>  
            
            <%

                HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
                String Slach = System.getProperty("file.separator");
                String ip = request.getLocalAddr();
                String userID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                System.out.println("USER ID JSP >> "+userID);
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

                
                String nextAction66 = AdministrationInterfaceKey.ACTION_DELETE_MONTH_LIST_FILE;
                System.out.println("ACTION .. "+nextAction66);
                String nextAction1 = "";
                nextAction1 = AuthResInterfaceKey.ACTION_VIEW_STATISTICS;

                //lamya
                String nextAction2 = "";
                nextAction2 = AdministrationInterfaceKey.ACTION_EXPORT_LIST_OF_THE_MONTH;

                // out.print (hidden_action);





            %> 
            <input type="hidden" id="test" name="<%=SCMInterfaceKey.BASE_DIRECTION%>" value="<%=request.getRealPath(Slach + "scm" + Slach + "upload" + Slach)%>">
            <CENTER>
                <H2>Show List of the Month Files</H2>
            </CENTER>

            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
                
                <TR class=TableHeader>
                    <td width="10%" nowrap align=middle>File Id</td>
                    <td width="20%" nowrap align=middle>User Name</td>
                    <td width="20%" nowrap align=middle>File Timestamp</td>
                    <td width="20%" nowrap align=middle>Month</td>
                    <td width="20%" nowrap align=middle>Year</td>
                    <td width="20%" nowrap align=middle>List</td>
                    <td width="30%" nowrap align=middle>Status</td>
                    <td width="20%" nowrap align=middle>Delete</td>
                    <td width="20%" nowrap align=middle>Export</td>
                

                </TR>
                <%
                    String appName = request.getContextPath();
                    String FileId = "";
                    String UserName="";
                    String POSCode = "";
                    Date FileTimestamp = new Date();
                    int Month=-1;
                    int Year=-1;
                    String List="";
                    String ChannelName = "";
                    String PayLevelName = "";
                    String Status="";
                    
                    
                    String base = request.getRealPath(Slach + "scm" + Slach + "upload" + Slach);
                    
                    if (files !=null)
                    {
                    for (int i = 0; i < files.size(); i++) {
                        MonthOfTheListModel model = (MonthOfTheListModel) files.get(i);
                        FileId = model.getHISTORY_FILE_ID();
                        //UserName = model.getUSER_ID();
                        UserName = model.getUSERNAME();
                        Month = model.getMONTH();
                        Year = model.getYEAR();
                        List = model.getLIST_NAME();
                        FileTimestamp = model.getTIMESTAMP();
                        Status = model.getSTATUS_ID();
                %>
                <TR class=TableTextNote>
                    <td width="20%" nowrap align=middle><%=FileId%></td>
                    <td width="40%" nowrap align=middle><%=UserName%></td>
                    <td width="40%" nowrap align=middle><%=FileTimestamp%></td>
                    <td width="40%" nowrap align=middle><%=Month%></td>
                    <td width="40%" nowrap align=middle><%=Year%></td>
                    <td width="40%" nowrap align=middle><%=List%></td>
                    <td width="40%" nowrap align=middle><%=Status%></td>

                    <TD width="10%" noWrap align=middle>
                        <%

                            /*if (Status.equalsIgnoreCase("Deleted")) {
                                out.println("<INPUT class=button type=button  disabled  value= \"Delete\"  name =\"Delete \" > ");

                            } else {
                                out.println("<INPUT class=button type=button    value= \"Delete\"  name =\"Delete \"onclick=\""
                                        + "loadDeleteField(" + model.getHISTORY_FILE_ID()+ ",'" + Status + "');\">");
                            }*/
                            
                            out.println("<INPUT class=button type=button    value= \"Delete\"  name =\"Delete \"onclick=\""
                                        + "loadDeleteField(" + model.getHISTORY_FILE_ID()+ ",'" + Status + "');\">");
                          
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
        function back ()
        {
            /*document.SheetRevenue.action="com.mobinil.sds.web.controller.WebControllerServlet?";
            document.SheetRevenue.action=document.SheetRevenue.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+'<%=SCMInterfaceKey.ACTION_SHOW_SAVE_LISTS%>';*/
            document.AUTHform.action = '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(SCMInterfaceKey.ACTION_SHOW_SAVE_LISTS);%>';                                                           
            document.AUTHform.submit();
            
        }
                function loadField(id,status)
                {
                    //document.AUTHform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value='<%=nextAction1%>';
                    document.AUTHform.action="com.mobinil.sds.web.controller.WebControllerServlet?"+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+'<%=AuthResInterfaceKey.ACTION_VIEW_STATISTICS%>';                
                    document.AUTHform.fieldId.value=id;
                    document.AUTHform.statusStr.value=status;
                    document.AUTHform.submit();
                }
                function loadDeleteField(id,status)
                {
                    
                    //document.AUTHform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value='<%=nextAction66%>';
                    document.AUTHform.action="com.mobinil.sds.web.controller.WebControllerServlet?"+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+'<%=AdministrationInterfaceKey.ACTION_DELETE_MONTH_LIST_FILE%>';
                    document.AUTHform.fieldId.value=id;
                    document.AUTHform.statusStr.value=status;                                                  
                    document.AUTHform.submit();
            
          
                }
                function loadExportField(id,status,base)
                {

                    //document.AUTHform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value='<%=nextAction2%>';
                    document.AUTHform.action="com.mobinil.sds.web.controller.WebControllerServlet?"+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+'<%=AdministrationInterfaceKey.ACTION_EXPORT_LIST_OF_THE_MONTH%>';                
                    document.AUTHform.fieldId.value=id;
                    document.AUTHform.statusStr.value=status;
                    document.AUTHform.baseDirectory.value=base;
                    document.AUTHform.submit();
          
                }
            </script>
                      <center>
    <input id="bckButton" name="bckButton" class='button' type='button' value='Back' onclick="back();"/>
    </center>
        </form>

    </body>
</html>