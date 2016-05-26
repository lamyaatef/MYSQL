
<%@page import="com.mobinil.mcss.commissionLabel.CommissionLabelDAO"%>
<%@page import="com.mobinil.sds.core.utilities.Utility"%>
<%@page import="com.mobinil.sds.core.system.commission.dao.CommissionDAO"%>
<%@ page contentType="Application/vnd.excel;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
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
        <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>     
    </head>

    <body>

        <%
            HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String strUserID = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Vector labelDetailsVEc = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
            java.sql.Connection con = Utility.getConnection();

            response.addHeader("Content-Disposition", "attachment; filename=report.xls");

        %>


        <form name='ACCform' id='ACCform' action='' method='post'>

            <%

                out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_ACTION + "\""
                        + " value=\"" + "\">");

                out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_USER_ID + "\""
                        + " value=\"" + strUserID + "\">");

                //out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
                //      " value=\""+channelId+"\">");  

            %>


            <% 
            LabelModel labelModel;
            LabelModel labelModel1 = CommissionLabelDAO.selectLabelData(con, (String) objDataHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID));
            if (labelModel1.getTwoValues() == 1 && labelModel1.getThreeValues() == 0)
                               {%>
                <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
                <tr class=TableHeader>
                <td align='center'>DCM Code</td>
                <td align='center'>Amount</td>
                <%  } else if (labelModel1.getTwoValues() == 0 && labelModel1.getThreeValues() == 1){%>
            
        
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
                <tr class=TableHeader>
                <td align='center'>DCM Code</td>
                <td align='center'>Amount</td>
                <td align='center'>Category</td>

            </tr> 
            <%}%>
                <%
                    String dcmCode;
                    String amount;
                    String category;
                  //  LabelModel labelModel;

                  //  LabelModel labelModel1 = CommissionDAO.selectLabelData(con, (String) objDataHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID));


                    for (int i = 0; i < labelDetailsVEc.size(); i++) {
                        labelModel = (LabelModel) labelDetailsVEc.get(i);
                        System.out.println("Lable Two values" + labelModel.getTwoValues());
                        System.out.println("Lable Three values" + labelModel.getThreeValues());
                        if (labelModel1.getTwoValues() == 1 && labelModel1.getThreeValues() == 0) {
                            dcmCode = labelModel.getDcmCode();
                            amount = labelModel.getAmount();


                %>

                <tr class=<%=InterfaceKey.STYLE[i % 2]%>>
                <td align='center'><%=dcmCode%></td>
                <td align='center'><%=amount%></td>

                </tr>
                <%
                } else if (labelModel1.getTwoValues() == 0 && labelModel1.getThreeValues() == 1) {

                    System.out.println("Draw Three Values Excel");
                    dcmCode = labelModel.getDcmCode();
                    System.out.println("Dcm Code is :" + dcmCode);
                    amount = labelModel.getAmount();
                    category = labelModel.getCategory();

                %>
                <tr class=<%=InterfaceKey.STYLE[i % 2]%>>
                <td align='center'><%=dcmCode%></td>
                <td align='center'><%=amount%></td>
                <td align='center'><%=category%></td>
                </tr>    

                <%}
                    }
                %>



            </TABLE>


        </form>
    </body>
</html>