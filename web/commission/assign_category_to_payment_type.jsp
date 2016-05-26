<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.payment.*"
         import="com.mobinil.sds.core.system.payment.model.*"
         import="com.mobinil.sds.core.system.payment.dto.*"

%>
<%
    String appName = request.getContextPath();
    HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template2.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/utilities.js" type=text/javascript></SCRIPT>

    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/tree.js" type=text/javascript></SCRIPT>
  </head>
  <body>
    <Center>
      <H2>Payment Type Managment</H2>
    </Center>    
    <script type="text/javascript">
    function disableSelect(typeId,catId)
    {

      if(document.getElementById('<%out.print(PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_STATUS);%>'+typeId+'_'+catId).disabled)
      {
        document.getElementById('<%out.print(PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_STATUS);%>'+typeId+'_'+catId).disabled = false;
      }
      else
      {
        document.getElementById('<%out.print(PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_STATUS);%>'+typeId+'_'+catId).disabled = true;
      }
    }
      function assignCategoryToType(typeId) 
      {

      document.assignCategoryToTypeForm.<%out.print(PaymentInterfaceKey.PARAM_PAYMENT_TYPE_ID);%>.value=typeId;
      document.assignCategoryToTypeForm.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value="<%out.print(PaymentInterfaceKey.ACTION_SAVE_CATEGORY_TO_PAYMENT_TYPE);%>";
      assignCategoryToTypeForm.submit();
      }
      
      function toggle1(item1)
      {
        divs=document.getElementsByTagName("DIV");
        for (i=0;i<divs.length;i++) 
        {
          if(divs[i].id != item1 && divs[i].id != 'listofpaymentType')
          {
            divs[i].style.display="none";
          }
        }
        obj=document.getElementById(item1);
        if (obj!=null)
        {
          visible = obj.style.display!="none";
          if (visible) {
            obj.style.display="none";
          } 
          else {
             obj.style.display="";
          }
        }
      }
    </script>
    <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="assignCategoryToTypeForm" method="post">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" 
      value="<%out.print(PaymentInterfaceKey.ACTION_SAVE_CATEGORY_TO_PAYMENT_TYPE);%>">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" 
      value="<%out.print(userID);%>">

      <input type="hidden" name="<%out.print(PaymentInterfaceKey.PARAM_PAYMENT_TYPE_ID);%>" 
      value="">
      
      <div name="listofpaymentType" id="listofpaymentType" style="display:none">
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td width="100%" nowrap align=center>Payment Type Name</td>
                 

          <!--td width="10%" nowrap align=center>Edit</td-->
        </tr>
        
        <%
        showPaymentType(request, response, out);
        %>
        
      </TABLE>
      </div>

    <%
    showCategories(request, response, out);
    %>
    </form>
    
  </body>
</html>
<%
  /**
   * Display any error messages returning from the server.
   */ 

  String strError = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
  if(strError != null)
  {
    out.println("<script type=\"text/javascript\">alert('"+strError+"');</script>");
  }
%>

<%! 

  public void showPaymentType(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty()){
      Vector vecPaymentType = (Vector)dataHashMap.get(PaymentInterfaceKey.VEC_PAYMENT_TYPE);
      for(int i = 0; i<vecPaymentType.size(); i++){
        PaymentTypeDTO ptdto= (PaymentTypeDTO) vecPaymentType.elementAt(i);


        int typeId = ptdto.getTypeId().intValue();
        String typeName = ptdto.getTypeName();

        out.println("<TR class=\""+InterfaceKey.STYLE[i%2]+"\">");
        out.println("<TD><a href=\"#divname"+typeId+"\" onclick=\"javascript:toggle1('"+typeId+"');\">"+typeName+"</a></TD>");
        out.println("</TR>");
      }
    }
  }


  public void showCategories(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty())
    {
    

      Vector vecPaymentType = (Vector)dataHashMap.get(PaymentInterfaceKey.VEC_PAYMENT_TYPE);
      for(int i = 0; i<vecPaymentType.size(); i++){
        PaymentTypeDTO ptdto= (PaymentTypeDTO) vecPaymentType.elementAt(i);

         int typeId = ptdto.getTypeId().intValue();
        String typeName = ptdto.getTypeName();
        out.println("<a name=\"divname"+typeId+"\" id=\"divname"+typeId+"\">");
        out.println("<div style=\"DISPLAY: none\" id="+typeId+" name="+typeId+">");


        Vector vecCategoryList = (Vector) dataHashMap.get (PaymentInterfaceKey.VEC_COMMISSIION_CATEGORY) ;
        Vector vecConstrainList = (Vector) dataHashMap.get(PaymentInterfaceKey.VEC_COMMISSION_CONSTRAIN);
        Vector vecTypeCategoryList = ptdto.getTypeCategories();
//        for(int k = 0; k<vecPersonReportList.size(); k++)
//        {
//              PersonReportModel personReportModel = (PersonReportModel)vecPersonReportList.get(k);
//              String personReportId = personReportModel.getPersonReportId();
//              String typeId = personReportModel.gettypeId();
//              String reportId = personReportModel.getReportId();
//              String groupId = personReportModel.getGroupId();
//        }

        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
        out.println("<TR class=TableHeader>");

        out.println("<td width=\"80%\" nowrap align=center>Commission Category</td>");
        out.println("<td width=\"15%\" nowrap align=center>Is Mandatory</td>");
        out.println("<td width=\"5%\" nowrap align=center>Assign</td>");  
        out.println("</TR>");
        for(int j = 0; j<vecCategoryList.size(); j++)
        {
          CommissionCategoryDTO ccdto =  (CommissionCategoryDTO) vecCategoryList.elementAt (j);
          Integer catId = ccdto.getCommissionTypeCategoryId();
          String catName=ccdto.getCommissionTypeCategoryName();
//          String catType=ccdto.getCommissionTypeCategoryMandatory();
//          System.out.println("catType in vecCategoryList is"+catType);
          String tdStyle =InterfaceKey.STYLE[i%2];

          


        out.println("<TR class=TableTextNote>");
        out.println("<td class="+InterfaceKey.STYLE[j%2]+">");
        
        out.print(catName);    

        out.println("</td>");
        
          out.println("<TD align=center class="+InterfaceKey.STYLE[j%2]+">"); 
          out.println(" <SELECT disabled onchange=\"document.getElementById('update"+typeId+"').disabled = false;\"  name=\""+PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_STATUS+typeId+"_"+catId+"\">");

          for (int g=0; g<vecConstrainList.size(); g++)
          {
          CommissionConstrainDTO constrain = (CommissionConstrainDTO)vecConstrainList.elementAt(g);
          out.println("<OPTION value="+constrain.getConstrianId().intValue());
        int  intCatType = constrain.getConstrianId().intValue();
        String catType = intCatType+"";
        for (int t=0;t<vecTypeCategoryList.size();t++)
        {
        CommissionCategoryDTO ddc = (CommissionCategoryDTO)vecTypeCategoryList.elementAt(t);
        Integer nCatId = ddc.getCommissionTypeCategoryId();
        Integer nTypeId = ddc.getPaymentTypeId();
        String nCatType =ddc.getCommissionTypeCategoryMandatory();
        if (nCatId.compareTo(catId)==0&&nTypeId.intValue()==typeId&&catType.compareTo(nCatType)==0){
//        if (nCatId.compareTo(catId)==0&&nTypeId.intValue()==typeId){
          out.print("selected");}
        }

          out.println(">"+constrain.getConstrianName()+"</OPTION>");
          } 
          out.println("</SELECT></TD>");    

         out.println("<td width=\"5%\" align=center class="+InterfaceKey.STYLE[j%2]+">");


         out.println("<input type=\"checkbox\" name="+PaymentInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX+typeId+"_"+catId+ 
         " onclick=\"document.getElementById('update"+typeId+"').disabled = false;disableSelect("+typeId+","+catId.intValue()+");\"");
                 boolean bridg=false;
          for(int k = 0; k<vecTypeCategoryList.size(); k++)
          {
        CommissionCategoryDTO ddc = (CommissionCategoryDTO)vecTypeCategoryList.elementAt(k);
        Integer nCatId = ddc.getCommissionTypeCategoryId();
        Integer nTypeId = ddc.getPaymentTypeId();

         if (nCatId.compareTo(catId)==0&&nTypeId.intValue()==typeId){
                      out.print(" checked");
                      bridg = true;
                      break;                      
         }
        }
                    out.println("></td>");
          if(bridg)out.print("<script>document.getElementById('"+PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_STATUS+typeId+"_"+catId+"').disabled = false</script>");
         out.println("</tr>");
        }
         out.println("</TABLE>");
       

        out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
        out.println("<tr>");
        out.println("<td align=center>");
        out.println("<input class=button type=\"button\" onClick=assignCategoryToType("+typeId+") name=\"update"+typeId+"\" value=\"Update "+typeName+"\" disabled>");
        out.println("</td>");
        out.println("</tr>");
//        out.println("</table></form></div></a>");
        out.println("</table></div></a>");

      }
    }
    out.println("<script>var obj = document.getElementById('listofpaymentType');obj.style.display='';</script>");    
  }
%>