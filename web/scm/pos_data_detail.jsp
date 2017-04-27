<%-- 
    Document   : pos_data_detail
    Created on : Nov 2, 2010, 2:19:17 PM
    Author     : Salma
--%>



<%@ page  import ="com.mobinil.sds.core.utilities.Utility"
          import = "java.util.*"
          import = "java.io.*"
          import = "javax.servlet.*"
          import = "javax.servlet.http.*"
          import = "javax.servlet.jsp.*"
          import = "java.io.PrintWriter"
          import = "java.io.IOException"
          import = "com.mobinil.sds.web.interfaces.scm.*"
          import = "com.mobinil.sds.core.system.fn.addfunction.model.*"
          import = "com.mobinil.sds.core.system.fn.importdata.model.*"
          import = "com.mobinil.sds.web.interfaces.*"
          import = "com.mobinil.sds.web.interfaces.fn.*"
          import = "com.mobinil.sds.web.interfaces.gn.querybuilder.*"
          import = "com.mobinil.sds.web.interfaces.dcm.*"
          import="com.mobinil.sds.core.system.dcm.pos.dao.*"
          import="com.mobinil.sds.core.system.dcm.genericModel.*"
          import="com.mobinil.sds.core.system.dcm.genericModel.DAO.*"
          import="com.mobinil.sds.core.system.dcm.pos.model.*"
          import="com.mobinil.sds.core.system.dcm.region.model.*"
          import="com.mobinil.sds.core.system.request.model.*"
%>

<%
String appName = request.getContextPath();
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {

    document.formDataView.submit();
  }
</SCRIPT>
<html>

  <head>

    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">

      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav.js"></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav-config.js"></SCRIPT>
  </head>

  <body >
    <%
     HashMap objDataHashMap = new HashMap();
     objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
     POSDetailModel posDetailModel =  (POSDetailModel)objDataHashMap.get(SCMInterfaceKey.POS_DETAIL_MODEL);
     objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
     String userID = request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
     PosModel posData = (PosModel) objDataHashMap.get(SCMInterfaceKey.SIMILAR_POS_LIST);
     UserDataModel userData = (UserDataModel) objDataHashMap.get(SCMInterfaceKey.SIMILAR_USER_LIST);
     UserDataModel teamleaderData = (UserDataModel) objDataHashMap.get(SCMInterfaceKey.SIMILAR_TEAMLEADER_LIST);
     UserDataModel salesrepData = (UserDataModel) objDataHashMap.get(SCMInterfaceKey.SIMILAR_SALESREP_LIST);
      
     out.println("<br><br><CENTER>");
      out.println("  <h2>POS Data Detail</h2>");
      out.println("</CENTER><br><br>");

      out.println("<CENTER>");
      //out.println("  <br><br><h2>POS Data Detail</h2><br><br>");
      out.println("</CENTER>");


     

      out.println("<form name='formDataView' action='' method='post'>");
%>

      <input type="hidden" name=<%=InterfaceKey.HASHMAP_KEY_ACTION%> >
<%
      
      out.println("<input type='hidden' name='" + InterfaceKey.HASHMAP_KEY_USER_ID + "' value='" + userID + "'>");

      out.println("<TABLE align=center style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"80%\" border=1>");
      out.println("<TR>");
      out.println("<TD class=TableTextNote>POS Data ");

      out.println("</TD>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("<TD colspan=2>");
      out.println("<table border=0 align='center' width='100%'>");
      out.println("<TR class=TableTextNote>");


      out.println("<TD class=TableTextNote width='40%'>POS Name</TD>");
      if(posData.getPosDetailModel().getPosName() !=null)
          out.println("<TD class=TableTextNote>" + posData.getPosDetailModel().getPosName());
      out.println("</TD>");
      out.println("</tr>");
      
      
      
      
      
      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=0 align='center' width='100%'>");
      out.println("<TR class=TableTextNote>");


      out.println("<TD class=TableTextNote width='40%'>POS Status</TD>");
      if(posData.getStatusName() !=null)
          out.println("<TD class=TableTextNote>" + posData.getStatusName());
      out.println("</TD>");
      out.println("</tr>");
      
      
      
      ////lamya checkboxes 
      out.println("<TD class=TableTextNote> POS is Exclusive </TD>");
      if(posData.isIsEX()==true)
          out.println("<TD class=TableTextNote> Yes </TD>");
      else out.println("<TD class=TableTextNote> No </TD>");
      out.println("</tr>");
      
      out.println("<TD class=TableTextNote> POS is Level One </TD>");
      if(posData.isIsL1()==true)
          out.println("<TD class=TableTextNote> Yes </TD>");
      else out.println("<TD class=TableTextNote> No </TD>");
      out.println("</tr>");
      
      out.println("<TD class=TableTextNote> POS is Orange Money </TD>");
      if(posData.isIsMobicash()==true)
          out.println("<TD class=TableTextNote> Yes </TD>");
      else out.println("<TD class=TableTextNote> No </TD>");
      out.println("<TD class=TableTextNote> Dial Number: "+posData.getMobicashNum()+"</TD>");
      out.println("</tr>");
      
      out.println("<TD class=TableTextNote> POS is Nomad </TD>");
      if(posData.isIsNomad()==true)
          out.println("<TD class=TableTextNote> Yes </TD>");
      else out.println("<TD class=TableTextNote> No </TD>");
      out.println("</tr>");
      
      out.println("<TD class=TableTextNote> POS is Quality Club </TD>");
      if(posData.isIsQC()==true)
          out.println("<TD class=TableTextNote> Yes </TD>");
      else out.println("<TD class=TableTextNote> No </TD>");
      out.println("</tr>");
      
      out.println("<TD class=TableTextNote> POS Has Sign </TD>");
      if(posData.isIsSignSet()==true)
          out.println("<TD class=TableTextNote> Yes </TD>");
      else out.println("<TD class=TableTextNote> No </TD>");
      out.println("</tr>");
      
      out.println("<TD class=TableTextNote> POS Reports to Calidus </TD>");
      if(posData.isReportToCalidus()==true)
          out.println("<TD class=TableTextNote> Yes </TD>");
      else out.println("<TD class=TableTextNote> No </TD>");
      out.println("</tr>");
      
      ///////lamya end of checkboxes
      

      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>POS Code</TD>");
      if(posData.getPosDetailModel().getPOSCode() !=null)
          out.println("      <TD class=TableTextNote><font style=font-size: 11px;font-family: tahoma;line-height: 15px>" + posData.getPosDetailModel().getPOSCode() + "</font></TD>");
      out.println("</tr>");


      out.println("</table>");
      out.println("</TD>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=0 align='center' width='100%'>");
      /////////////////////////////
      out.println("<TR class=TableTextNote>");


      out.println("      <TD class=TableTextNote width='40%'>Payment Level</TD>");
       if(posData.getPaymentLevel() !=null)
         out.println("      <TD class=TableTextNote>"+ posData.getPaymentLevel()+ "</TD>");


      out.println("</tr>");
      
      
      
      
      out.println("<TR class=TableTextNote>");


      out.println("      <TD class=TableTextNote width='40%'>Payment Method</TD>");
       if(posData.getPaymentMethod()!=null)
         out.println("      <TD class=TableTextNote>"+ posData.getPaymentMethod()+ "</TD>");


      out.println("</tr>");
      //////////////////////////
      out.println("<TR class=TableTextNote>");


      out.println("      <TD class=TableTextNote width='40%'>Channel</TD>");
       if(posData.getChannel() !=null)
         out.println("      <TD class=TableTextNote>"+ posData.getChannel() + "</TD>");


      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>Level</TD>");
       if(posData.getLevel() !=null)
           out.println("      <TD class=TableTextNote>" + posData.getLevel() + "</TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>Branch Of</TD>");
       if(posData.getBranchOf() !=null)
          out.println("      <TD class=TableTextNote>" + posData.getBranchOf() + "</TD>");
      out.println("</tr>");




      out.println("</table>");
      out.println("</TD>");
      out.println("</tr>");


      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=0 align='center' width='100%'>");
      out.println("<TR class=TableTextNote>");


      out.println("     <TD class=TableTextNote width='40%'>Address</TD>");
       if(posData.getPosDetailModel().getPosAddress() !=null)
          out.println("      <TD class=TableTextNote>" + posData.getPosDetailModel().getPosAddress()+ "</TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote  width='40%'>Region</TD>");
       if(posData.getRegion() !=null)
           out.println("      <TD class=TableTextNote>" + posData.getRegion() + "</TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>Governrate</TD>");
       if(posData.getGovernrate() !=null)
            out.println("      <TD class=TableTextNote>" + posData.getGovernrate() + "</TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>City</TD>");
       if(posData.getCity() !=null)
            out.println("      <TD class=TableTextNote>" + posData.getCity() + "</TD>");
      out.println("</tr>");


      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>District</TD>");
       if(posData.getDistrict() !=null)
            out.println("      <TD class=TableTextNote>" + posData.getArea() + "</TD>");
      out.println("</tr>");
      
      
      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>Image District</TD>");
       if(posData.getDistrict() !=null)
            out.println("      <TD class=TableTextNote>" + posData.getImgDist() + "</TD>");
      out.println("</tr>");
      

      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>Area</TD>");
       if(posData.getArea() !=null)
          out.println("      <TD class=TableTextNote>" + posData.getDistrict() + "</TD>");
      out.println("</tr>");

      out.println("</table>");
      out.println("</TD>");
      out.println("</tr>");


      out.println("<TR>");
      out.println("<TD class='TableTextNote' colspan=2><br><hr><strong>Owner Data</strong><hr> " );

      out.println("</TD>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=0 align='center' width='100%'>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD width='40%'>Owner Name</TD>");
       if(posData.getPosDetailModel().getPosOwnerName() !=null)
            out.println("      <TD class=TableTextNote>" + posData.getPosDetailModel().getPosOwnerName()+ "</TD>");
      out.println("</tr>");

    //  out.println("<TR class=TableTextNote>");
      //out.println(" <TD colspan=2>");

      //out.println("<table border=0 align='center' width='100%'>");
      out.println("<TR>");
      out.println("  <TD class=TableTextNote vAlign=top width='40%'>Owner Phone");
      out.println("  </TD>");
      out.println("  <TD width='60%'>");
      if(posData.getPosDetailModel().getPosOwnerPhones()!= null)
      {
            out.println(" <table>");
        for(int i = 0 ; i < posData.getPosDetailModel().getPosOwnerPhones().size() ; i++  )
          {
             out.println("  <tr><td class=TableTextNote align=left>" + posData.getPosDetailModel().getPosOwnerPhones().get(i) + "</td></tr>");
          }
         out.println(" </table>");
      }
      else
          {
            out.println("");
          }
      out.println("</TD>");

      out.println("</td>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD class=TableTextNote>Owner Birth Date</TD>");
       if(posData.getPosDetailModel().getPosOwnerBirthDate() !=null)
      out.println("      <TD class=TableTextNote>" + posData.getPosDetailModel().getPosOwnerBirthDate() + "</TD>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD class=TableTextNote>Owner I.D Type</TD>");
       if(posData.getPosDetailModel().getPosOwnerIDTypeName() !=null)
        out.println("      <TD class=TableTextNote>" + posData.getPosDetailModel().getPosOwnerIDTypeName() + "</TD>");

      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD class=TableTextNote>Owner I.D Number</TD>");
       if(posData.getPosDetailModel().getPosOwnerIDNumber() !=null)
       out.println("      <TD class=TableTextNote>" + posData.getPosDetailModel().getPosOwnerIDNumber() + "</TD>");
     out.println("</tr>");

      out.println("      </table>");
      out.println("      </TD>");
      out.println("</tr>");


      ///////////////////////////////////////
      out.println("<TR>");
      out.println("<TD class='TableTextNote' colspan=2><br><hr><strong>Supervisor Data</strong><hr>");

      out.println("</TD>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=0 align='center' width='100%'>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD width='40%'>Supervisor Name</TD>");
      if(userData.getUserFullName()!=null)
            out.println("      <TD class=TableTextNote>" + userData.getUserFullName()+ "</TD>");
      else
          out.println("      <TD class=TableTextNote> </TD>");
      out.println("</tr>");
      
      out.println("<TR>");
      out.println("  <TD class=TableTextNote vAlign=top width='40%'>Supervisor Mobile</TD>");
      if (userData.getUserMobile()!=null)
            out.println("  <TD width='60%' class=TableTextNote align=left>" + userData.getUserMobile() + "</td></tr>");
      else
          out.println("  <TD width='60%' class=TableTextNote align=left> </td></tr>");
    
      
      out.println("<TR class=TableTextNote>");
      out.println("      <TD class=TableTextNote>Supervisor Email</TD>");
      if (userData.getUserEmail()!=null)
            out.println("      <TD class=TableTextNote>" + userData.getUserEmail() + "</TD>");
      else
            out.println("      <TD class=TableTextNote> </TD>");

      out.println("</tr>");
      

      out.println("      </table>");
      out.println("      </TD>");
      out.println("</tr>");

      
      //////////////////////////////////////////////////////////////
      
      
      
      
      ///////////////////////////////////////
      out.println("<TR>");
      out.println("<TD class='TableTextNote' colspan=2><br><hr><strong>Teamleader Data</strong><hr>");

      out.println("</TD>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=0 align='center' width='100%'>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD width='40%'>Teamleader Name</TD>");
      if(userData.getUserFullName()!=null)
            out.println("      <TD class=TableTextNote>" + teamleaderData.getUserFullName()+ "</TD>");
      else
          out.println("      <TD class=TableTextNote> </TD>");
      out.println("</tr>");
      
      out.println("<TR>");
      out.println("  <TD class=TableTextNote vAlign=top width='40%'>Teamleader Mobile</TD>");
      if (userData.getUserMobile()!=null)
            out.println("  <TD width='60%' class=TableTextNote align=left>" + teamleaderData.getUserMobile() + "</td></tr>");
      else
          out.println("  <TD width='60%' class=TableTextNote align=left> </td></tr>");
    
      
      out.println("<TR class=TableTextNote>");
      out.println("      <TD class=TableTextNote>Teamleader Email</TD>");
      if (userData.getUserEmail()!=null)
            out.println("      <TD class=TableTextNote>" + teamleaderData.getUserEmail() + "</TD>");
      else
            out.println("      <TD class=TableTextNote> </TD>");

      out.println("</tr>");
      

      out.println("      </table>");
      out.println("      </TD>");
      out.println("</tr>");

      
      //////////////////////////////////////////////////////////////
      
      ///////////////////////////////////////
      out.println("<TR>");
      out.println("<TD class='TableTextNote' colspan=2><br><hr><strong>Salesrep Data</strong><hr>");

      out.println("</TD>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=0 align='center' width='100%'>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD width='40%'>Salesrep Name</TD>");
      if(userData.getUserFullName()!=null)
            out.println("      <TD class=TableTextNote>" + salesrepData.getUserFullName()+ "</TD>");
      else
          out.println("      <TD class=TableTextNote> </TD>");
      out.println("</tr>");
      
      out.println("<TR>");
      out.println("  <TD class=TableTextNote vAlign=top width='40%'>Salesrep Mobile</TD>");
      if (userData.getUserMobile()!=null)
            out.println("  <TD width='60%' class=TableTextNote align=left>" + salesrepData.getUserMobile() + "</td></tr>");
      else
          out.println("  <TD width='60%' class=TableTextNote align=left> </td></tr>");
    
      
      out.println("<TR class=TableTextNote>");
      out.println("      <TD class=TableTextNote>Salesrep Email</TD>");
      if (userData.getUserEmail()!=null)
            out.println("      <TD class=TableTextNote>" + salesrepData.getUserEmail() + "</TD>");
      else
            out.println("      <TD class=TableTextNote> </TD>");

      out.println("</tr>");
      

      out.println("      </table>");
      out.println("      </TD>");
      out.println("</tr>");

      
      //////////////////////////////////////////////////////////////
      
      
      out.println("<TR>");
      out.println("<TD class='TableTextNote' colspan=2><br><hr><strong>Manager Data</strong><hr>");

      out.println("</TD>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=0 align='center' width='100%'>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD width='40%'>Manager Name</TD>");
       if(posData.getPosDetailModel().getPosManagerName() !=null)
       out.println("      <TD width='60%'>" + posData.getPosDetailModel().getPosManagerName() + "</TD>");
     out.println("</tr>");

      out.println("<TR>");
      out.println("  <TD class=TableTextNote vAlign=top width='40%'>Manager Phone");
      out.println("  </TD>");


      out.println("  <TD width='60%'>");
      if(posData.getPosDetailModel().getPosManagerPhones() != null)
      {
            out.println(" <table>");
        for(int i = 0 ; i < posData.getPosDetailModel().getPosManagerPhones().size() ; i++  )
          {
            if (posData.getPosDetailModel().getPosManagerPhones().get(i)==null){
               out.println("  <tr><td class=TableTextNote align=left> </td></tr>");
          }else
              {
              out.println("  <tr><td class=TableTextNote align=left>"+posData.getPosDetailModel().getPosManagerPhones().get(i)+"</td></tr>");
              }

        }
         out.println(" </table>");
      }
      else
          {
            out.println(" ");
          }
      out.println("</TD>");
      
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD class=TableTextNote>Manager Birth Date</TD>");
       if(posData.getPosDetailModel().getPosManagerBirthDate() !=null)
      out.println("      <TD class=TableTextNote>" + posData.getPosDetailModel().getPosManagerBirthDate() + "</TD>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD class=TableTextNote>Manager I.D Type</TD>");
       if(posData.getPosDetailModel().getPosManagerIDTypeName() !=null)
         out.println("      <TD class=TableTextNote>" + posData.getPosDetailModel().getPosManagerIDTypeName() + "</TD>");

      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD class=TableTextNote>Manager I.D Number</TD>");
       if(posData.getPosDetailModel().getPosManagerIDNumber() !=null)
        out.println("      <TD class=TableTextNote>" + posData.getPosDetailModel().getPosManagerIDNumber() + "</TD>");
     out.println("</tr>");
     out.println("      </table>");
      out.println("      </TD>");
      out.println("</tr>");









       out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=0 align='center' width='100%'>");
      out.println("<TR class=TableTextNote>");


      out.println("<TD class=TableTextNote width='40%'>Demo Line Number</TD>");
       if(posData.getDemoLineNum() !=null)
      out.println("<TD class=TableTextNote>" +posData.getDemoLineNum() + "");
      out.println("</TD>");
      out.println("</tr>");



      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>E-mail</TD>");
       if(posData.getPosDetailModel().getPosEmail() !=null)
      out.println("      <TD class=TableTextNote>" + posData.getPosDetailModel().getPosEmail() + "</TD>");
      out.println("</tr>");


      out.println("</table>");
      out.println("</TD>");
      out.println("</tr>");



      out.println("<TR>");
      out.println("<TD class=TableTextNote>Documents ");

      out.println("</TD>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=0 align='center' width='100%'>");
      out.println("<TR class=TableTextNote>");


      out.println("      <TD class=TableTextNote width='40%'>Proposed Documents");
      if(posData.getDocumentTypeName() !=null)
      out.println("      <TD class=TableTextNote>" + posData.getDocumentTypeName() + "</TD>");
      out.println("</tr>");


      out.println("<TR>");
      out.println("      <TD class=TableTextNote>Document Number</TD>");
       if(posData.getDocNumber() !=null)
         out.println("      <TD class=TableTextNote>" + posData.getDocNumber() + "</TD>");
      out.println("</tr>");
      out.println("<TR>");
      out.println("      <TD class=TableTextNote>Document Location</TD>");
       if(posData.getDocLocation() !=null)
         out.println("      <TD class=TableTextNote>" + posData.getDocLocation() + "</TD>");
      out.println("</tr>");


      out.println("</table>");
      out.println("</TD>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table align='center' width='100%'>");
      out.println("<TR class=TableTextNote>");


      out.println("      <TD class=TableTextNote width='40%'>place</TD>");
       if(posData.getRate() !=null)
         out.println("      <TD class=TableTextNote>" + posData.getRate() + "</TD>");
      out.println("</tr>");


     


      out.println("</table>");
      out.println("</TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("<TD class=TableTextNote>STK Data ");

      out.println("</TD>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=0 align='center' width='100%'>");

      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width='40%'>STK Dial Number</TD>");
       if(posData.getStkDialNumber() !=null)
      out.println("      <TD class=TableTextNote>" + posData.getStkDialNumber() + "</TD>");
      out.println("</tr>");


      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width='40%'>STK Verification</TD>");
       if(posData.getStkVerify() !=null)
         out.println("      <TD class=TableTextNote>" + posData.getStkVerify() + "</TD>");
         out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width='40%'>Survey ID</TD>");
       if(posData.getPosDetailModel().getSurveyID() !=null)
         out.println("<TD class=TableTextNote>" + posData.getPosDetailModel().getSurveyID() + "</TD>");
         out.println("</tr>");

      


      out.println("</table>");
      out.println("</TD>");
      out.println("</tr>");

      out.println("</table>");

      out.println("</form>");

    %>
<center>
<input class='button' type='button' value='Back' onclick="history.go(-1);"/>

    <br><br>
</CENTER>
</body>

</html>