<%-- 
   Document   : DeleteClosedPayment
   Created on : Oct 17, 2012, 12:17:55 AM
   Author     : SAND
--%>


<%@page import="com.mobinil.sds.core.system.cam.payment.model.PaymentModel"%>
<%@page import="com.mobinil.sds.core.system.request.model.CityModel"%>
<%@page import="com.mobinil.sds.web.interfaces.dcm.DCMInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.dcm.genericModel.GenericModel"%>
<%@page import="com.mobinil.sds.core.system.request.model.PlaceDataModel"%>
<%@page import="com.mobinil.sds.web.interfaces.cam.PaymentInterfaceKey"%>
<%@page import="com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey"%>
<%@page import="com.mobinil.sds.web.interfaces.InterfaceKey"%>
<%@page import="javax.xml.soap.Detail"%>
<%@page contentType="text/html" pageEncoding="windows-1256"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"

    <%@ page  import ="com.mobinil.sds.core.utilities.Utility"
              import = "java.util.*"
              import = "java.io.*"
              import = "javax.servlet.*"
              import = "javax.servlet.http.*"
              import = "javax.servlet.jsp.*"
              import = "java.io.PrintWriter"
              import = "java.io.IOException"
              %>





    <%
        HashMap dataHashMap = new HashMap(100);
        dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String paymentName = "";

        if ((String) dataHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_NAME) != null) {
            paymentName = (String) dataHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_NAME);
        }
        String paymentDetailId = "";
        if ((String) dataHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_ID) != null) {
            paymentDetailId = (String) dataHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_ID);
        }


        String strUserID = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        Vector<PaymentModel> DataVec = (Vector<PaymentModel>) dataHashMap.get(PaymentInterfaceKey.VECTOR_SEARCH_CLOSED_PAYMENT);




    %>



    <html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script>
        function searchClosedPayment()
        {

            document.PaymentForm.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=PaymentInterfaceKey.ACTION_SEARCH_CLOSED_PAYMENT%>';

            document.PaymentForm.submit();
        }
        
        
        function updateStatus(id)
        {
            document.PaymentDetailManagement.<%= PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_DETAIL_ID%>.value  = id;
            document.PaymentDetailManagement.<%= InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=PaymentInterfaceKey.ACTION_UPDATE_PAYMENT_DETAIL%>';
            document.PaymentDetailManagement.submit();
            
        }
        
        
    </script>

</head>
<body>
    <form name='PaymentForm' id='PaymentForm' action='' method='post'>
        <input type="hidden"  name=<%=InterfaceKey.HASHMAP_KEY_ACTION%> id=<%=InterfaceKey.HASHMAP_KEY_ACTION%> >
        <input type=hidden name=<%=InterfaceKey.HASHMAP_KEY_USER_ID%> value="<%=strUserID%>" >
        <br><br>
        <center><h2> Payment Management</h2></center>
        <br><br>


        <center><h2> Closed Payment Search</h2></center>
        <br>
        <br>

        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            <tr class=TableHeader>
                <td align='left' colspan=6>Search</td>
            </tr>
            <TR class=TableTextNote>
                <td align=middle>Payment Name</td>
                <td align=middle><input type='text' name="<%=PaymentInterfaceKey.CONTROL_PAYMENT_NAME%>" id="<%=PaymentInterfaceKey.CONTROL_PAYMENT_NAME%>" value="<%=paymentName%>" ></td>
                <td align=middle>Payment Detail ID</td>
                <td align=middle><input type='text' name="<%=PaymentInterfaceKey.CONTROL_PAYMENT_ID%>" id="<%=PaymentInterfaceKey.CONTROL_PAYMENT_ID%>" value="<%=paymentDetailId%>" ></td>
            </tr>


        </table>  


        <br><br>
        <center>
            <input class=button type="button" name="new" value="Search"
                   onclick="searchClosedPayment()">

        </center>


    </form>


    <%
        if (DataVec != null) {
            if (DataVec.size() != 0) {

    %>
    <form name='PaymentDetailManagement' id='PaymentDetailManagement' action='' method='post'>
        <input type="hidden"  name=<%=InterfaceKey.HASHMAP_KEY_ACTION%> id=<%=InterfaceKey.HASHMAP_KEY_ACTION%> >
        <input type=hidden name=<%=InterfaceKey.HASHMAP_KEY_USER_ID%> value="<%=strUserID%>" >
        <input type="hidden" name="<%= PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_DETAIL_ID%>" id="<%= PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_DETAIL_ID%>">


        <table class="sortable" style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="90%" border="1">
            <tr >
                <td class=TableHeader nowrap align=center>PAYMENT NAME</td>
                <td class=TableHeader nowrap align=center>PAYMENT ID</td>
                <td class=TableHeader nowrap align=center>PAYMENT STATUS TYPE NAME</td>
                <td class=TableHeader nowrap align=center>PAYMENT TYPE NAME</td>
                <td class=TableHeader nowrap align=center>PAYMENT START DATE</td>
                <td class=TableHeader nowrap align=center>PAYMENT END DATE</td>
                <td class=TableHeader nowrap align=center>UPDATE STATUS</td>

            </tr>

            <%
                for (int i = 0; i < DataVec.size(); i++) {

                    System.out.println("Payment:" + DataVec.get(i).getPaymentName());%>

            <tr>
                <% if (DataVec.get(i).getPaymentName() == null) {
                        DataVec.get(i).setPaymentName("");
                    }%>
                <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=DataVec.get(i).getPaymentName()%></td>



                <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">

                    <% if ((Integer) DataVec.get(i).getPaymentDetailId() == null) {
                            System.out.print("");
                        } else {%>
                    <%=  DataVec.get(i).getPaymentDetailId()%>
                    <%  }

                    %>
                </td>

                <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">

                    <% if (DataVec.get(i).getPaymentStatusTypeName() == null) {
                            System.out.print("");
                        } else {%>
                    <%=DataVec.get(i).getPaymentStatusTypeName()%>
                    <%    }

                    %>
                </td>


                <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">

                    <% if (DataVec.get(i).getPaymentTypeName() == null) {
                            System.out.print("");
                        } else {%>
                    <%=DataVec.get(i).getPaymentTypeName()%>
                    <%  }

                    %>
                </td>




                <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">

                    <% if ((Date) DataVec.get(i).getPaymentStartDate() == null) {
                            System.out.print("");
                        } else {%>
                    <%=DataVec.get(i).getPaymentStartDate()%>
                    <%  }

                    %>
                </td>



                <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">

                    <% if ((Date) DataVec.get(i).getPaymentEndDate() == null) {
                            System.out.print("");
                        } else {%>
                    <%=DataVec.get(i).getPaymentEndDate()%>
                    <% }

                    %>
                </td>

                <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
                    <input class=button  type="button"  value="DELETE" onclick="updateStatus(<%=DataVec.get(i).getPaymentDetailId()%>)">




                </td>


            </tr>


            <%
                }
            %>
        </table>

    </form>
    <%
            }
        }
    %>

</body>
</html>
