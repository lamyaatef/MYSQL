<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.core.system.cr.batch.dao.*"
         import="com.mobinil.sds.core.system.cr.batch.model.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.model.*"
         import="com.mobinil.sds.core.system.cr.sheet.dto.*"
         import="com.mobinil.sds.core.system.cr.warning.model.*"
%>
<%
/**
 * Batch_Details_Print.jsp:
 * Display all the sheets ordered by batchs befor being printed
 * and the windows print dialog.
 * Batchs displayed are those of the search result from "Batch_Search.jsp".
 * 
 * showBatchs(HttpServletRequest , HttpServletResponse , JspWriter): 
 * used to display the sheets orderd by batchs.
 *
 *
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

    String appName = request.getContextPath();
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/tree.js" type=text/javascript></SCRIPT>
  </head>
  <body>
    <Center>
      <H2>Batch Details</H2>
    </Center>
    <%
    showBatchs(request, response, out);
    %>
    <script>
      window.print();
    </script>
  </body>
</html>

<%!
/**
 * showBatchs method:
 * Display the sheets orderd by batchs.
 *
 * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
 * @return  
 * @throws  ServletException, IOException if the jsp failed
 * @see    
 * 
*/
  public void showBatchs(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    if(dataVector != null && dataVector.size()!=0)
    {
      for(int i = 0; i<dataVector.size(); i++)
      {
        BatchDto newBatchDto = (BatchDto)dataVector.elementAt(i);
        if(newBatchDto != null)
        {
          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
          out.println("<TR class=TableHeader>");
          out.println("<td width=\"10%\" nowrap align=center><font size=2>Batch ID</font></a></TD>");          
          out.println("<td width=\"20%\" nowrap align=center><font size=2>DCM Name</font></a></TD>");
          out.println("<td width=\"20%\" nowrap align=center><font size=2>Creation Date</font></a></TD>");
          out.println("<td width=\"30%\" nowrap align=center><font size=2>Batch Type</font></a></TD>");
          out.println("<td width=\"20%\" nowrap align=center><font size=2>Batch Status</font></a></TD>");

          String batchID = newBatchDto.getBatchModel().getBatchId();
          String dcmName = newBatchDto.getBatchModel().getBatchDCMName();;
          String date = newBatchDto.getBatchModel().getBatchDate();
          String batchType = newBatchDto.getBatchModel().getBatchType();
          String batchStatus = newBatchDto.getBatchModel().getBatchStatus();
          out.println("<TR>");
          out.println("<td nowrap align=center>"+batchID+"</a></td>");          
          out.println("<td nowrap>"+dcmName+"</td>");
          out.println("<td nowrap align = center>"+date+"</td>");
          out.println("<td nowrap>"+batchType+"</td>");
          out.println("<td nowrap>"+batchStatus+"</td>");
          out.print("</tr>");
          out.println("<TR>");
          out.println("<TD colspan=6>");
          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
          out.println("<TR class=TableHeader>");
          out.println("<td align=center width=\"10%\">Sheet Serial</td>");
          out.println("<td align=center width=\"10%\"><font size=2>POS</font></TD>");
          out.println("<td align=center width=\"10%\"><font size=2>Super Dealer</font></TD>");
          //out.println("<td align=center width=\"12%\">Contract Type</td>");
          //out.println("<td align=center width=\"5%\">Count</td>");
          out.println("<td align=center width=\"5%\">Total</td>");
          out.println("<td align=center width=\"15%\">Sheet Status</td>");
          out.println("<td align=center width=\"33%\">Warnings</td></tr>");
          for(int j=0; j<newBatchDto.getSheetModelsSize(); j++)
          {
            SheetModel newSheetModel = newBatchDto.getSheetAt(j);
            String sheetID = newSheetModel.getSheetId();
            String sheetName = newSheetModel.getSheetName();
            String sheetStatusName = newSheetModel.getSheetStatusName();
            String sheetPOSCode = newSheetModel.getSheetPOSCode();
            String sheetSuperDealerCode = newSheetModel.getSheetSuperDealerCode();
            SheetDto sheetDto = newBatchDto.getSheetDto();
            Vector sheetWarningsVector = sheetDto.getSheetWarning(sheetID);
            int contractCount = newSheetModel.getContractsCount();
            String contractCountStatment = "";
            Enumeration contractsCountTypes = newSheetModel.getContractsTypesCount().keys();
            Enumeration contractsCountTypesCounter = newSheetModel.getContractsTypesCount().elements();
            out.println("<TR>");
            out.println("<td nowrap align=center>"+sheetName+"</td>");
            out.println("<td nowrap align=center>"+sheetPOSCode+"</td>");
            out.println("<td nowrap align=center>"+sheetSuperDealerCode+"</td>");
            out.println("<td align=center>"+contractCount+"</td>");
            /*out.println("<td nowrap align=left colspan=2>");
            out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=1 cellPadding=1 width=\"100%\" border=0>");
            while (contractsCountTypes.hasMoreElements())
            {          
              out.println("<tr><td width=\"65%\">"+(String)contractsCountTypes.nextElement()+"</td>");
              out.println("<td align=center>"+(String)contractsCountTypesCounter.nextElement()+"</td></tr>");
              out.println("<tr><td colspan =2 bgColor=#f1f1ed></td></tr>");
            }
            out.println("<tr><td bgColor=#f1f1ed><font style=\"FONT-WEIGHT: bold\">DEF Total</font></td>");
            out.println("<td bgColor=#f1f1ed align=center><font style=\"FONT-WEIGHT: bold\">"+contractCount+"</font></td></tr>");
            out.println("</Table>");
            out.println("</td>");*/
            out.println("<td nowrap align=left>"+sheetStatusName+"</td>");
            out.println("<td align=left>");
            if(sheetWarningsVector !=null)
            {
              String sheetWarnings = "";
              for (int l=0; l<sheetWarningsVector.size(); l++)
              {          
                sheetWarnings+=((SheetWarningModel)sheetWarningsVector.elementAt(l)).getSheetWarningName()+"<br>";
              }
              out.println(sheetWarnings);
            }
            out.println("</td></tr>");
          }
          out.println("</TABLE>");      
          out.print("</td>");
          out.print("</tr>");
          out.println("</TABLE>");
          out.println("<br>");
          out.println("<p style=\"page-break-before: always\">");
          out.println("<br>");
        }
      }
    
    }
  }
%>