<%-- 
    Document   : barcode_stock
    Created on : 28/10/2010, 14:52:23
    Author     : Ahmed Adel
--%>

<%@ page contentType="text/html;charset=windows-1252"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.scm.model.*"
              
              %>
<%
            String appName = request.getContextPath();
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            //String confMessage=(String)dataHashMap.get(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE);
            String formName="updatePOSCode";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            //String codeUpdated = (String)dataHashMap.put(SCMInterfaceKey.POS_CODE_UPDATED,null);
            String codeUpdated = (String)dataHashMap.get(SCMInterfaceKey.POS_CODE_UPDATED);
            System.out.println("code updated begin jsp : "+codeUpdated);
            %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <title>POS Alphanumeric Code</title>
        
    </head>
    <body>
        <br>
        <br>
        <div align="center">
        <h2>POS Letter Code Update</h2>
        <br>
        <br>
        
        <br>
         <form name="<%=formName%>"  method="post" action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%=InterfaceKey.HASHMAP_KEY_ACTION%>=<%=SCMInterfaceKey.ACTION_UPDATE_POS_LETTER_CODE%>">
                   <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
                    <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >

                        <tr>
                        <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Old POS Code:</font></td>
                        <td nowrap align=center colspan="2"><font style="font-size: 11px;font-family: tahoma;line-height: 15px">
                                <input type="text" name="<%=SCMInterfaceKey.POS_OLD_CODE%>" value="">
                            </font></td>
                    </tr>
                    <tr>
                        <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">New POS Letter Code:</font></td>
                        <td nowrap align=center colspan="2"><font style="font-size: 11px;font-family: tahoma;line-height: 15px">
                                <input type="text" name="<%=SCMInterfaceKey.POS_NEW_LETTER_CODE%>" value="">
                            </font></td>
                    </tr>
                    <tr>
                        <td colspan="3" align="center">
                            <input type="submit" class="submit" name="Submit" value="Update POS Code" >
                        </td>
                    </tr>
                    </table>

         </form>
                            
        <%
        codeUpdated=(String)dataHashMap.get(SCMInterfaceKey.POS_CODE_UPDATED);
        System.out.println("code updated in jsp : "+codeUpdated);
        if(codeUpdated!=null&& codeUpdated.compareToIgnoreCase("yes")==0){
         
         %>
                   <tr>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Code Updated</font></td>
                    </tr>
               <%} else if(codeUpdated!=null&& codeUpdated.compareToIgnoreCase("no")==0){%>
               
                    <tr>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Code Not Updated</font></td>
                        
                    </tr>
                <%}%>
                
                <br>


        </div>
    </body>
</html>
