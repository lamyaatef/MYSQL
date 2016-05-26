
<%@page import="com.mobinil.sds.core.system.cam.payment.model.PaymentTypeModel"%>
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
        Vector freezepaymentVec = (Vector) dataHashMap.get(PaymentInterfaceKey.VECTOR_FREEZE_TYPES);
        
        if (freezepaymentVec == null)
                       {
            freezepaymentVec = new Vector();
            
        }
        String paymentName = "";

        if ((String) dataHashMap.get(PaymentInterfaceKey.CONTROL_FREEZE_PAYMENT_NAME) != null) {
            paymentName = (String) dataHashMap.get(PaymentInterfaceKey.CONTROL_FREEZE_PAYMENT_NAME);
        }

        String paymentType = "";

        if ((String) dataHashMap.get(PaymentInterfaceKey.CONTROL_FREEZE_PAYMENT_TYPE) != null) {
            paymentType = (String) dataHashMap.get(PaymentInterfaceKey.CONTROL_FREEZE_PAYMENT_TYPE);
        }

        String dcmCode = "";

        if ((String) dataHashMap.get(PaymentInterfaceKey.CONTROL_FREEZE_DCM_CODE) != null) {
            dcmCode = (String) dataHashMap.get(PaymentInterfaceKey.CONTROL_FREEZE_DCM_CODE);
        }
         
        String strUserID = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        System.out.println("ss1");
        Vector<PaymentModel> DataVec = (Vector<PaymentModel>) dataHashMap.get(PaymentInterfaceKey.VECTOR_SEARCH_FROZEN_PAYMENT);
        String reason = (String) dataHashMap.get(PaymentInterfaceKey.CONTROL_INPUT_FREEZE_REASON);
        String scmId = (String) dataHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_FREEZED_SCM_ID);
        String paymentId = (String) dataHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_FREEZED_PAYMENT_DETAIL_ID);
        String appName = request.getContextPath();


    %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        
        
<script>
function searchFreezePayment()
            {

        document.FreezePaymentForm.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=PaymentInterfaceKey.ACTION_SEARCH_FROZEN_PAYMENT%>';

        document.FreezePaymentForm.submit();
            }

  function freezePaymentTxt(paymentId, scmId)
{
    
    document.getElementById("excelreasonlabel").style.display="";
    document.getElementById("excelreason").style.display="";
    document.getElementById("excelposbutton").style.display="";
    
    
    document.FreezePaymentForm.<%= PaymentInterfaceKey.INPUT_HIDDEN_FREEZED_PAYMENT_DETAIL_ID%>.value  = paymentId;
    
    document.FreezePaymentForm.<%= PaymentInterfaceKey.INPUT_HIDDEN_FREEZED_SCM_ID%>.value  = scmId;
    
   
}
function submitRelease()
        {   
            
            document.FreezePaymentForm.<%= InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=PaymentInterfaceKey.ACTION_SUBMIT_FREEZED_PAYMENT%>';
            document.FreezePaymentForm.submit();            
            
            
        }
        
</script>


        
        
    </head>
    <body>
        
        <form name='FreezePaymentForm' id='FreezePaymentForm' action='' method='post'>
      <input type="hidden"  name=<%=InterfaceKey.HASHMAP_KEY_ACTION%> id=<%=InterfaceKey.HASHMAP_KEY_ACTION%> >
      <input type=hidden name=<%=InterfaceKey.HASHMAP_KEY_USER_ID%> value="<%=strUserID%>" >
        
        <br><br>

        <center><h2> Freeze Payment </h2></center>
        <br><br>
       <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            <tr class=TableHeader>
                <td align='center' colspan=6>Search Frozen payment</td>
            </tr>
            <TR class=TableTextNote>
               
                <td align=middle>Payment Name</td>
                <td align=middle><input type='text' name="<%=PaymentInterfaceKey.CONTROL_FREEZE_PAYMENT_NAME%>" id="<%=PaymentInterfaceKey.CONTROL_FREEZE_PAYMENT_NAME%>" value="<%=paymentName%>" ></td>
                <td align=middle>Payment type </td>
                <td align=middle>
                    <select name="<%= PaymentInterfaceKey.CONTROL_FREEZE_PAYMENT_TYPE%>" id="<%= PaymentInterfaceKey.CONTROL_FREEZE_PAYMENT_TYPE%>">
                        <option value="" >--</option>
                        <%
                            for (int i = 0; i < freezepaymentVec.size(); i++) {
                                PaymentTypeModel paymentTypeModel = (PaymentTypeModel) freezepaymentVec.get(i);
                        %>
                        <option value="<%=paymentTypeModel.getPaymentTypeId()%>"><%=paymentTypeModel.getPaymentTypeName()%></option>
                        <%}%>
                    </select>


                </td> 
                  </tr>
                
                  <TR class=TableTextNote>
                <td align=middle>Dcm code </td>
                <td align=middle><input type='text' name="<%=PaymentInterfaceKey.CONTROL_FREEZE_DCM_CODE%>" id="<%=PaymentInterfaceKey.CONTROL_FREEZE_DCM_CODE%>" value="<%=dcmCode%>" ></td>

            </tr>


        </table>  
                
                
          <br><br>
        <center>
            <input class=button type="button" name="new" value="Search"
                   onclick="searchFreezePayment()">

        </center>
        
              <%
        
    if (DataVec != null) {
            if (DataVec.size() != 0) {

    %>
    
    
    
    <input type="hidden" name="<%= PaymentInterfaceKey.INPUT_HIDDEN_FREEZED_PAYMENT_DETAIL_ID%>" id="<%= PaymentInterfaceKey.INPUT_HIDDEN_FREEZED_PAYMENT_DETAIL_ID%>">
    <input type="hidden" name="<%= PaymentInterfaceKey.INPUT_HIDDEN_FREEZED_SCM_ID%>" id="<%= PaymentInterfaceKey.INPUT_HIDDEN_FREEZED_SCM_ID%>">
    
        

        <table class="sortable" style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="90%" border="1">
            <tr>
                
                <td class=TableHeader nowrap align=center>DCM NAME</td>
                <td class=TableHeader nowrap align=center>DCM CODE</td>
                <td class=TableHeader nowrap align=center>PAYMENT DETAIL ID</td>
                <td class=TableHeader nowrap align=center>PAYMENT NAME</td>
                <td class=TableHeader nowrap align=center>PAYMENT START DATE</td>
                <td class=TableHeader nowrap align=center>PAYMENT END DATE</td>
                <td class=TableHeader nowrap align=center>RECORD ID</td>
                <td class=TableHeader nowrap align=center>SCM COMMISSION VALUE</td>
                <td class=TableHeader nowrap align=center>PAYMENT TYPE NAME</td>
                <td class=TableHeader nowrap align=center>RELEASE PAYMENT</td>
                
        
            </tr>
            <%
                for (int i = 0; i < DataVec.size(); i++) {

             %>

            <tr>
           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
           <%=  DataVec.get(i).getDCMName()%>
           </td>
           
           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
           <%=  DataVec.get(i).getDCMCode()%>
           </td>
          
           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
           <%=  DataVec.get(i).getPaymentDetailId()%>
           </td>
           
           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
           <%=  DataVec.get(i).getPaymentName()%>
           </td>
           
           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
           <%=  DataVec.get(i).getPaymentStartDate()%>
           </td>
           
           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
           <%=  DataVec.get(i).getPaymentEndDate()%>
           </td>
           
           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
           <%=  DataVec.get(i).getPaymentRecordId()%>
           </td>
           
           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
           <%=  DataVec.get(i).getPaymentSCMCommissionValue()%>
           </td>
         
           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
           <%=  DataVec.get(i).getPaymentTypeName()%>
           </td>
           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
             <input class=button  type="button"  value="FreezePayment" onclick="freezePaymentTxt(<%=  DataVec.get(i).getPaymentDetailId() %> , <%=  DataVec.get(i).getPaymentSCMId()%>);">

           </td>
           </tr>
          
      
            <%
                }
            %>
        </table>
        
        
 <table class="sortable" align="center" style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="90%" border="1">
 
  

        
           <tr>
            <td align="center" style="vertical-align:middle">
            <label align="center" id="excelreasonlabel" style="display: none">Reason</label>
            </td>
          </tr>
          <tr>
            <td align="center" style="vertical-align:middle">
            <textarea  cols="50" rows="10" name="<%=PaymentInterfaceKey.CONTROL_INPUT_FREEZE_REASON%>" id="excelreason" value="<%=reason%>" style="display: none"></textarea>
            </td>
         </tr>
         
         <tr>
         <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
        
        <input type="button" class="button" id="excelposbutton" name="Submit" value="Submit" style="display: none" onclick="submitRelease();">
         </td>
        </tr>
      
        </table>
      
      </form>
    <%
            }
        }
    %>
          
          
          
          
          
    </body>
</html>
