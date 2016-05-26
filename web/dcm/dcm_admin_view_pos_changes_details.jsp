<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.core.system.dcm.pos.dao.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.DAO.*"
         import="com.mobinil.sds.core.system.dcm.pos.model.*"
         import="com.mobinil.sds.core.system.dcm.pos.dao.*"
         import="com.mobinil.sds.core.system.dcm.city.model.*"
%>

<% 
String appName = request.getContextPath();
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {

  }
</SCRIPT>
<html>

  <head>
  
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
  </head>
<SCRIPT language="javascript">
  </SCRIPT>
<body>
    <%
    showPOSRelationDetails(request, response, out);
    %>        
    <br><br>
    <center>
      <input class='button' type='button' value='Accept' onclick=checkbeforSubmit();>
      <input class='button' type='button' value='Reject' onclick=checkbeforSubmit();>
    </center>
      </form>
<%!
  public void showPOSRelationDetails(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  { 
    
    String appName = request.getContextPath();

    
    out.println("<CENTER>");
    out.println("  <H2>POS Changes Details</H2>");
    out.println("</CENTER>");
    
  
    
    HashMap dataHashMap = new HashMap(100);

    String formActionSave = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                        +InterfaceKey.HASHMAP_KEY_ACTION+"="
                        +"action";
    
    out.println("<form name='formDataView' action='"+formActionSave+"' method='post'>");


    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        POSDetailModel changesModel = (POSDetailModel)dataHashMap.get(DCMInterfaceKey.DCM_CHANGES_POS);    
    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD width='40%'>POS Name</TD>");
    out.println("      <TD>"+changesModel.getPosName()+"</TD>");
    out.println("</tr>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Address</TD>");
    out.println("      <TD>"+changesModel.getPosAddress()+"</TD>");
    out.println("</tr>");      
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Area</TD>");
    out.println("      <TD>Area</TD>");
    out.println("</tr>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>City</TD>");
    out.println("      <TD>"+changesModel.getPosCityName()+"</TD>");
    out.println("</tr>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Governate</TD>");
    out.println("      <TD>Governate</TD>");
    out.println("</tr>"); 
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>E-mail</TD>");
    out.println("      <TD>"+changesModel.getPosEmail()+"</TD>");
    out.println("</tr>");
    
    out.println("<TR class=TableTextNote>");
    out.println("      <TD colspan=2>");
    
    out.println("<table border=0 align='center' width='100%'>");
    out.println("<TR>");
    out.println("  <TD class=TableTextNote vAlign=top colSpan=2>Phones");
    out.println("  </TD>");
    out.println("</TR>");
    for(int i = 0 ; changesModel.getPosPhones()!=null && i < changesModel.getPosPhones().size() ; i++){  
    out.println("<tr>");
        int j = i + 1;
    out.println("<td width='36%'></td>");
    out.println("<td>");
    out.println("phone_"+j+"  "+changesModel.getPosPhones().get(i));
    out.println("</td>");
    out.println("</tr>");
    }
    out.println("</table>");

    out.println("</td>");
    out.println("</tr>");

    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Commercial No.</TD>");
    out.println("      <TD>"+changesModel.getPosCommercialNumber()+"</TD>");
    out.println("</tr>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Tax ID</TD>");
    out.println("      <TD>"+changesModel.getPosTaxID()+"</TD>");
    out.println("</tr>");
    
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Legal Form</TD>");
    out.println("      <TD>"+changesModel.getPosLegalFormName()+"</TD>");
    out.println("</tr>");

    out.println("<TR class=TableTextNote>");
    out.println("      <TD colspan=2>");
    
    out.println("<table border=0 align='center' width='100%'>");
    out.println("<TR>");
    out.println("  <TD class=TableTextNote vAlign=top colSpan=2>Partners");
    out.println("  </TD>");
    out.println("</TR>");
    for(int i = 0 ; changesModel.getPosPartners()!=null && i < changesModel.getPosPartners().size() ; i++){
    out.println("<tr>");
        int j = i + 1;
    out.println("<td width='36%'></td>");
    out.println("<td>");
    out.println("Partner_"+j+"  "+changesModel.getPosPartners().get(i));
    out.println("</td>");
    out.println("</tr>");
    }
   
    out.println("</table>");

    out.println("</td>");
    out.println("</tr>");

    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Place</TD>");
    out.println("      <TD>"+changesModel.getPosPlaceTypeName()+"</TD>");
    out.println("</tr>");

    out.println("<TR class=TableTextNote>");
    out.println("      <TD colspan=2>");
    out.println("      <table border=0 align='center' width='100%'>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD width='40%'>Owner Name</TD>");
    out.println("      <TD>"+changesModel.getPosOwnerName()+"</TD>");
    out.println("</tr>");

    out.println("<TR class=TableTextNote>");
    out.println("      <TD colspan=2>");
    
    out.println("<table border=0 align='center' width='100%'>");
    out.println("<TR>");
    out.println("  <TD class=TableTextNote vAlign=top colSpan=2>Owner Phones");
    out.println("  </TD>");
    out.println("</TR>");
    for(int i = 0 ; changesModel.getPosOwnerPhones() != null && i < changesModel.getPosOwnerPhones().size() ; i++){
        int j = i + 1;
    out.println("<tr>");
    out.println("<td width='36%'></td>");
    out.println("<td>");
    out.println("Phone_"+j+"  "+changesModel.getPosOwnerPhones().get(i));
    out.println("</td>");
    out.println("</tr>");
    }
    out.println("</table>");

    out.println("</td>");
    out.println("</tr>");
    
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Owner Birth Date</TD>");
    out.println("      <TD>"+changesModel.getPosOwnerBirthDate()+"</TD>");
    out.println("</tr>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Owner I.D Type</TD>");
    out.println("      <TD>"+changesModel.getPosOwnerIDTypeName()+"</TD>");
    out.println("</tr>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Owner I.D Number</TD>");
    out.println("      <TD>"+changesModel.getPosOwnerIDNumber()+"</TD>");
    out.println("</tr>");

    out.println("      </table>");
    out.println("      </TD>");
    out.println("</tr>");

    out.println("<TR class=TableTextNote>");
    out.println("      <TD colspan=2>");
    out.println("      <table border=0 align='center' width='100%'>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD width='40%'>Manager Name</TD>");
    out.println("      <TD>"+changesModel.getPosManagerName()+"</TD>");
    out.println("</tr>");

    out.println("<TR class=TableTextNote>");
    out.println("      <TD colspan=2>");
    
    out.println("<table border=0 align='center' width='100%'>");
    out.println("<TR>");
    out.println("  <TD class=TableTextNote vAlign=top colSpan=2>Manager Phones");
    out.println("  </TD>");
    out.println("</TR>");
    for(int i = 0 ; changesModel.getPosManagerPhones() != null && i < changesModel.getPosManagerPhones().size() ; i++){
    out.println("<tr>");
    int j = i + 1;
    out.println("<td width='36%'></td>");
    out.println("<td>");
    out.println("Phone_"+j+"  "+changesModel.getPosManagerPhones().get(i));
    out.println("</td>");
    out.println("</tr>");
    }
    out.println("</table>");

    out.println("</td>");
    out.println("</tr>");
    
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Manager Birth Date</TD>");
    out.println("      <TD>"+changesModel.getPosManagerBirthDate()+"</TD>");
    out.println("</tr>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Manager I.D Type</TD>");
    out.println("      <TD>"+changesModel.getPosManagerIDTypeName()+"</TD>");
    out.println("</tr>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Manager I.D Number</TD>");
    out.println("      <TD>"+changesModel.getPosManagerIDNumber()+"</TD>");
    out.println("</tr>");

    out.println("      </table>");
    out.println("      </TD>");
    out.println("</tr>");

    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Changes Made By Sales Agent:</TD>");
    out.println("      <TD>"+changesModel.getUserName()+"</TD>");
    out.println("</tr>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Changes Timestamp</TD>");
   // out.println("      <TD>"+changesModel.getPosTimeStamp()+"</TD>");
    out.println("      <TD>"+"</TD>");
    out.println("</tr>");
    
    out.println("</table>");
}
%>

</body>
</html>