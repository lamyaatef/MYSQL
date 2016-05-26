<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.web.interfaces.commission.*"  
         import="com.mobinil.sds.web.interfaces.payment.*"
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.dcm.region.model.*"
         import="com.mobinil.sds.core.system.dcm.user.model.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import="com.mobinil.sds.core.system.gn.dataview.dto.*"         
         import="com.mobinil.sds.core.system.commission.model.*"
         import="com.mobinil.sds.core.system.payment.model.*"
         import="com.mobinil.sds.core.system.commission.factor.model.*"

         %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
    <body>

        <%
            HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

            String strUserID = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

            LabelModel labelModelData = (LabelModel) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

            String labelId = (String) objDataHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
        %>

    <CENTER>
        <H2> Label  </H2>
    </CENTER>

    <form name="COMform" action="" method="post">
        <%
            out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_ACTION + "\""
                    + " value=\"" + "\">");

            out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_USER_ID + "\""
                    + " value=\"" + strUserID + "\">");
            /////////////////

            //boolean test =  (boolean)objDataHashMap.

            ////////////////



            String nextAction = CommissionInterfaceKey.ACTION_SAVE_LABEL_DATA;
            String labelName = "";
           
        
            String labelDescription = "";
            if (labelModelData != null) {
                nextAction = CommissionInterfaceKey.ACTION_UPDATE_LABEL_DATA;
                labelName = labelModelData.getLabelName();
                labelDescription = labelModelData.getLabelDescription();
                if (labelDescription == null) {
                    labelDescription = "";
                }
                //labelId = labelModelData.getLabelId();
                Utility.logger.debug("iddddddddddddddddd isssssssssss " + labelId);
            }
            out.println("<input type=\"hidden\" name=\"" + CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID + "\""
                    + " value=\"" + labelId + "\">");

        %>
        <center>
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>  
                <tr>
                    <td class=TableHeader nowrap>Name</td>
                    <td class=TableTextNote><input type="text" name="label_name" value="<%=labelName%>"></td>
                </tr>
                <tr>
                    <td class=TableHeader nowrap>Description</td>
                    <td class=TableTextNote><input type="text" name="label_description" value="<%=labelDescription%>"></td>
                </tr>
                <tr>
                    <td class=TableHeader nowrap>Category</td>
                    <td class=TableTextNote>
                        <input type="radio" name="radio_category" id="radio_category_1" value="2Values" 
                               <%
                                   if (labelModelData.getTwoValues() == 1) {
                               %>
                               checked
                               <%}%>
                               >Two Values
                        <input type="radio" name="radio_category" id="radio_category_2" value="3Values" <%
                            if (labelModelData.getThreeValues() == 1) {
                               %>
                               checked
                               <%}%>>Three Values</td>
                </tr>
            </table>
        </center>
        <br>
        <center>
            <%
                out.print("<INPUT class=button type='button' value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.COMform." + InterfaceKey.HASHMAP_KEY_ACTION + ".value = '" + nextAction + "';"
                        + "COMform.submit();\">");

                out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
            %>
        </center>
    </form>
</body>
</html>
