
<%@ page import="com.mobinil.sds.core.utilities.Utility"
         import="javax.servlet.*" import="javax.servlet.http.*"
         import="java.io.PrintWriter" import="java.io.IOException"
         import="java.util.*" import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.commission.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.core.system.commission.model.*"
         import="com.mobinil.mcss.level_relation_management.LevelRelationManagementInterfaceKey"
         import="com.mobinil.mcss.level_relation_management.model.LevelZeroToLevelOne"%>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

    <link href="../resources/css/Template2.css" rel="stylesheet"
          type="text/css" />
    <SCRIPT language=JavaScript src="../resources/js/calendar.js"
    type=text/javascript></SCRIPT>
    <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
    <script type="text/javascript"
    src="../resources/js/jquery_tablesorter_pack.js"></script>
    <SCRIPT language=JavaScript src="../resources/js/tree.js"
    type=text/javascript></SCRIPT>
    <link rel="stylesheet" href="../resources/css/themes/blue/style.css"
          type="text/css" />
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    <script language="javascript">



        function removeAllOptions(selectname)
        {
            var elSel = document.getElementById(selectname);
            if (elSel.length == 0) 
            {
                return;
            }
            elSel.remove(0);
            removeAllOptions(selectname)  	
        }
		      
        function addOption(optvalue,opttext,selectname)
        {

      
            var elSel = document.getElementById(selectname);
            var elOptNew = new Option();
		
            elOptNew.text =  opttext;
            elOptNew.value =  optvalue;
			
            elSel.options[elSel.length] = elOptNew;		
        }
        
    
    </script>
    <script>
        function checkForSubmit()
        {	
            COMform.submit()
        }
    </script>
</head>

<body>

    <%
        HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String appName = request.getContextPath();
        String strUserID = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        Vector levels = (Vector) objDataHashMap.get(LevelRelationManagementInterfaceKey.VECTOR_LEVEL_ZERO_TO_LEVEL_ONE_DATA);
        String COMFormAction = appName + "/servlet/com.mobinil.sds.web.controller.WebControllerServlet?";


    %>

<center>
    <H2>Level Zero To Level One</H2>
</center>

<%

//System.out.println("&&&&&&&&&&&&&&&*************");
//System.out.println("savedCommissionId = "+ savedCommissionId);

%>

<form name="COMform" id="COMform" action='<%=COMFormAction%>' method="post">




    <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <%
            for (int i = 0; i < levels.size(); i++) {
                LevelZeroToLevelOne levelZeroToLevelOne = (LevelZeroToLevelOne) levels.get(i);

                int preChannelId = 0;
                if (i != 0) {
                    preChannelId = Integer.parseInt(((LevelZeroToLevelOne) levels.get(i - 1)).getLevelZeroID());
                }
                if (Integer.parseInt(levelZeroToLevelOne.getLevelZeroID()) != preChannelId) {
                    int channelId = levelZeroToLevelOne.getId();
                    String channelName = levelZeroToLevelOne.getLevelZeroName();
                    if (i != 0) {
                        out.println("</table></div></td></tr>");
                    }
        %>
        <TR class=TableTextNote>
            <td class="<% out.print(InterfaceKey.STYLE[i % 2]);%>">
                <a style="text-decoration:none;" id=x<% out.print(channelId);%> href="javascript:Toggle('<% out.print(channelId);%>');"> <IMG
                        height=16 hspace=0 src="<% out.print(appName);%>/resources/img/plus.gif" width=16
                        border=0> </a>
                    <%out.print(" " + channelName);%>
            </td>

        </TR>
        <TR>
            <td class="<% out.print(InterfaceKey.STYLE[i % 2]);%>">

                <div style="DISPLAY: none" id="<% out.print(channelId);%>" name=<%out.print(" " + channelName);%>>
                    <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1 class="<% out.print(InterfaceKey.STYLE[i % 2]);%>" id="CommissionTable">

                        <tr class=TableHeader>
                            <td nowrap align=middle>Level One Name</td>
                            <td nowrap align=middle>Level One DCM Code</td>

                            <td nowrap align=middle>Check</td>                
                            <td nowrap align=middle>Delete</td>                

                        </tr>

                        <%
                                }
                                //out.println("<tbody>");

                                ////////////////////

                                out.println("<tr>");


                                out.println("<td class=" + InterfaceKey.STYLE[i % 2] + ">"
                                        + levelZeroToLevelOne.getLevelOneName()
                                        + "</td>");

                                out.println("<td  class=" + InterfaceKey.STYLE[i % 2] + ">"
                                        + levelZeroToLevelOne.getLevelOneDcmCode()
                                        + "</td>");


                                out.println("<td align=\"center\" ><input type=\"checkbox\" name=" + LevelRelationManagementInterfaceKey.CONTROL_SHOW_LEVEL_ZERO_TO_LEVEL_ONE_CHECKBOX + levelZeroToLevelOne.getId() + " id=" + LevelRelationManagementInterfaceKey.CONTROL_SHOW_LEVEL_ZERO_TO_LEVEL_ONE_CHECKBOX + levelZeroToLevelOne.getId() + " value=" + levelZeroToLevelOne.isGuaranteeFlag() + "");

                                if (levelZeroToLevelOne.isGuaranteeFlag()) {
                                    out.println("checked");
                                }
                                out.println("></td>");



                                out.println("<td align=\"center\" ><input  type=\"button\" name="
                                        + levelZeroToLevelOne.getId() + "name="
                                        + levelZeroToLevelOne.getId() + " name="
                                        + levelZeroToLevelOne.getId() + " value=\"Delete\" "
                                        + " onclick=\"deleteLevel(" + levelZeroToLevelOne.getId() + ")\"/> </td>");



                                /*
                                 *
                                 * out.println("<td
                                 * align=\"center\"></td>");
                                 */
                                out.println("</tr>");
                                //////////////////////////////////////////   


                                if (i == (levels.size() - 1)) {
                                    out.println("</table></div></td></tr>");
                                }
                            }
                            //out.println("</tbody>");

                        %>

                    </table>

                    <center>
                        <input class=button type="button" name="new" value="Submit Changes"
                               onclick="submitChanges()">

                    </center>

                    </form>
                    <p>&nbsp;</p>
                    </body>
                    </html>

                    <script>
                        function submitChanges()
                        {
     
                            document.COMform.action=document.COMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(LevelRelationManagementInterfaceKey.ACTION_EDIT_LEVEL_ZERO_TO_LEVEL_ONE);%>'+
                                '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>

                            document.COMform.submit();
                        }
                        function deleteLevel(i)
                        {
                            var row=i;
   
                            document.COMform.action=document.COMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(LevelRelationManagementInterfaceKey.ACTION_DELETE_LEVEL_ZERO_TO_LEVEL_ONE);%>'+
                                '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(LevelRelationManagementInterfaceKey.INPUT_HIDDEN_LEVEL_ZERO_TO_LEVEL_ONE_ID + "");%>='+row


                            document.COMform.submit();
                        }
                    </script>