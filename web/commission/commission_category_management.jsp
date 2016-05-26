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
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.dcm.region.model.*"
         import="com.mobinil.sds.core.system.dcm.user.model.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

<link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
    <SCRIPT language=JavaScript src="../resources/js/tree.js" type=text/javascript></SCRIPT>
</head>

<body>
<center>
<H2>Commission Type Category Management </H2>
</center>
<%
 /* HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector commissionTypes = (Vector)objDataHashMap.get(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE);
  Vector commissionCategories =(Vector)objDataHashMap.get(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE_CATEGORY);*/

%>

<form name="COMform" action="" method="post">
<%
  
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
  out.println("<input type=\"hidden\" name=\""+CommissionInterfaceKey.INPUT_HIDDEN_CATEGORY_ID+"\""+
                  " value=\""+"\">");                  
  out.println("<input type=\"hidden\" name=\""+CommissionInterfaceKey.INPUT_HIDDEN_TYPE_ID+"\""+
                  " value=\""+"\">");                  

/*  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             */
                                    
%>

<table style="BORDER-COLLAPSE: collapse" width="100%" border="1" align="center" cellpadding="0" cellspacing="1" style="gray3"> 
<tr>
  <td height="14" colspan="6" class="TableHeader">Commission Type Category Details</td>
</tr>

    <%
/*      for(int i = 0 ; i < commissionTypes.size() ; i++)
      {
        GenericModel commissionTypeModel = (GenericModel)commissionTypes.get(i);
        out.println("<option value='"+commissionTypeModel.get_primary_key_value()+"'> "+commissionTypeModel.get_field_2_value());
        out.println(" </option> ");
      }*/
       showPrivileges(request, response, out);
    %>    

</table>
</form>

</body>
</html>
<%!
public void showPrivileges(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
 
  if(! objDataHashMap.isEmpty())
    {
      out.println("<TR class=TableHeader>");
      out.println("<td nowrap align=center colspan=6><font size=2>Commission Type</font></a></TD></TR>");
      Vector commissionTypes = (Vector)objDataHashMap.get(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE);
      Vector commissionCategories =(Vector)objDataHashMap.get(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE_CATEGORY);

      for(int i = 0; i< commissionTypes.size(); i++)
      {
        GenericModel commissionTypeModel = (GenericModel)commissionTypes.get(i);    
        
        out.println("<TR class=TableTextNote>");
        out.println("<td>");
        out.println("<a id=x"+commissionTypeModel.get_primary_key_value()+" href=\"javascript:Toggle('"+commissionTypeModel.get_primary_key_value()+"');\">");
        out.print("<IMG height=16 hspace=0 src=\""+appName+"/resources/img/plus.gif\" width=16 border=0 >");
        out.print("</a>");
        out.print(commissionTypeModel.get_field_2_value()+"</TD></TR>");
        out.println("<TR class=TableHeader>");
        out.println("<TD colspan=6 class=TableTextNote>");
        out.println("<div style=\"DISPLAY: none\" id="+commissionTypeModel.get_primary_key_value()+" name="+commissionTypeModel.get_field_2_value()+">");
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
        out.println("<TR class=TableHeader>");
        out.println("<td  class=TableHeader colspan=2 width=\"30%\">Category Name</td>");
        out.println("<td  class=TableHeader colspan=2 width=\"45%\">Category Description</td>");
        out.println("<td  class=TableHeader align=middle colspan=2 width=\"15%\">Category Status</td></tr>");
        out.println("</tr>");
        for(int j = 0; j<commissionCategories.size(); j++)
        {
          GenericModel categoriesModel = (GenericModel)commissionCategories.get(j);
          if(categoriesModel.get_field_3_value().equals(commissionTypeModel.get_primary_key_value()))
          {
          out.println("<TR>");
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+"  colspan=2 >"+categoriesModel.get_field_2_value()+"</TD>");
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+" colspan=2>"+categoriesModel.get_field_4_value()+"</TD>");
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+"   align=middle colspan=2>");
          out.println("<input type='button' class=\"button\" value=\"Edit\" name='edit_"+categoriesModel.get_primary_key_value()+"' onclick=\""+
                        "document.COMform."+CommissionInterfaceKey.INPUT_HIDDEN_TYPE_ID+".value='"+commissionTypeModel.get_primary_key_value()+"';"+
                        "document.COMform."+CommissionInterfaceKey.INPUT_HIDDEN_CATEGORY_ID+".value='"+categoriesModel.get_primary_key_value()+"';"+
                        "document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+CommissionInterfaceKey.ACTION_COMMISSION_EDIT_COMMISSION_CATEGORY+"';"+
                        "COMform.submit();\">");
          }
        }
        out.println("<tr><td colspan='6' ALIGN='MIDDLE' ><input type='button' class='button'"+
                    " name='newCtaegory' value='Add New Category' onclick=\""+
                    "document.COMform."+CommissionInterfaceKey.INPUT_HIDDEN_TYPE_ID+".value='"+commissionTypeModel.get_primary_key_value()+"';"+                  
                    "document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+CommissionInterfaceKey.ACTION_COMMISSION_CREATE_NEW_COMMISSION_CATEGORY+"';"+                    
                    "COMform.submit();"+
                    "\"></td></tr>");
        out.println("</TABLE></td></tr>");

        out.println("</div>");
        
      }
    }
  }
  %>