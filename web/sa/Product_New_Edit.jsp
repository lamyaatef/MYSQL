<%@page import="com.mobinil.sds.core.system.sa.product.model.ProductModel"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
%>
<%
/**
 * User_New_Edit.jsp:
 * Add or edit a user.
 * 
 * showUser(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Display the form in which a user will be edited or added.
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
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type=text/javascript></SCRIPT>
  </head>
  <javascript> <SCRIPT language=JavaScript>
    
    function caDownload (flag ) {
        
        if ( NonBlank(document.getElementById('<% out.print(AdministrationInterfaceKey.CONTROL_TEXT_NAME_PRODUCT_NAME );%>'), true, 'Product Name') && NonBlank(document.getElementById('<% out.print(AdministrationInterfaceKey.CONTROL_TEXT_NAME_PRODUCT_DESC );%>'), true, 'Product Descrption'))
       
        {
            var elem = document.getElementById('<% out.print(InterfaceKey.HASHMAP_KEY_ACTION ); %>');

            if (!flag ) {        

                    elem.value = '<%out.print(AdministrationInterfaceKey.ACTION_UPDATE_PRODUCT);%>' ;
                }

            else
                {
                    elem.value = '<%out.print(AdministrationInterfaceKey.ACTION_ADD_PRODUCT);%>' ;
                }


             document.getElementById("ProductManagement").submit();

 
        }

   }
</script>

  <body>
        <%
        showProduct(request, response, out);
        %>
  </body>
</html>

<%!
  /**
   * showUser method: 
   * Display the form in which a user will be edited or added.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showProduct(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    String appName = request.getContextPath();

    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(dataHashMap != null)
    {
      String productID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
      System.out.println("jsppp productID : "+productID);
      String header = "New Product";
      String productName = "";
      String productDesc = "";
      String productCatName = "";
      String productCatId = "";
      ProductModel newProductModel = (ProductModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      System.out.println("here newProductModel "+newProductModel);
      if(newProductModel != null)
      {
        productName = newProductModel.getProductName();
        productDesc = newProductModel.getProductDesc();
        productCatId = newProductModel.getProductCategoryId();
        productCatName = newProductModel.getProductCategoryName();
        header = productName;  
      }
      out.println("<Center>");
      out.println("<H2>"+header+"</H2>");
      out.println("</Center>");
      out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" id=\"ProductManagement\" name=\"ProductManagement\" method=\"post\">");
      out.println("<input type=\"hidden\" id=\"action\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" >");

      out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME+"\" "+
                     "value=\""+serverName+"\">");
      out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT+"\" "+
                     "value=\""+serverPort+"\">");
      out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH+"\" "+
                     "value=\""+appName+"\">");
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" "+
                     "value="+productID+">");

      out.println("<input type=\"hidden\" name=\""+UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID+"\" value=\""+productID+"\">");
      out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
      
      
      
      
      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width=\"20%\">Product Name *</TD><TD class=TableTextNote>");
      out.println("<input size=\"50%\" type=\"text\" id=\""+AdministrationInterfaceKey.CONTROL_TEXT_NAME_PRODUCT_NAME+"\" name=\""+AdministrationInterfaceKey.CONTROL_TEXT_NAME_PRODUCT_NAME+"\"");
      if(newProductModel != null)
      {
        out.print(" value=\""+productName+"\"");
      }
      out.print(">");
      out.println("</TD></TR>");
      /////////////////////////////////////
      
      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width=\"20%\">Product Description *</TD><TD class=TableTextNote>");
      out.println("<input size=\"50%\" type=\"text\" id=\""+AdministrationInterfaceKey.CONTROL_TEXT_NAME_PRODUCT_DESC+"\" name=\""+AdministrationInterfaceKey.CONTROL_TEXT_NAME_PRODUCT_DESC+"\"");
      if(newProductModel != null)
      {
        out.print(" value=\""+productDesc+"\"");
      }
      out.print(">");
      out.println("</TD></TR>");
      
      //////////////
      out.println("<TR class=TableTextNote>");
        out.println("<TD class=TableTextNote width=\"20%\">Product Category Name *</TD>");
        out.println("<TD class=TableTextNote>");
       // out.println("<SELECT name="+AdministrationInterfaceKey.CONTROL_TEXT_NAME_PRODUCT_CAT_NAME+
        //            " onchange=\"document.ProductManagement.update.disabled = false;\">");
        out.println("<SELECT name="+AdministrationInterfaceKey.CONTROL_TEXT_NAME_PRODUCT_CAT_NAME+" >");
        Vector additionalDataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        String selectedCat = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_FIELD);
        for(int j = 0; j<additionalDataVector.size(); j++)
        {
          productCatName = ((ProductModel)additionalDataVector.elementAt(j)).getProductCategoryName();
          //String statusName = ((UserStatusModel)additionalDataVector.elementAt(j)).getUserStatusName();
          out.println("<option value=\""+productCatName+"\""); 
         
          if(selectedCat!=null && selectedCat.compareTo( productCatName) == 0 ) 
        //  if (selectedCat == productCatName)
              //hagry comment:  never use == to compare two strings always use .compareTo then equal the result to 0 since == is a memory comparing location 
          {
            out.print("selected");
          }
          out.print(">"+productCatName+"</option>");
        }
        out.println("</SELECT>");
        out.println("</TD></TR>");
      //////////////
      
     /* out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width=\"20%\">Product Category Name *</TD><TD class=TableTextNote>");
      out.println("<input size=\"50%\" type=\"text\" name=\""+AdministrationInterfaceKey.CONTROL_TEXT_NAME_PRODUCT_CAT_NAME+"\"");
      if(newProductModel != null)
      {
        out.print(" value=\""+productCatName+"\"");
      }
      out.print(">");
      out.println("</TD></TR>");*/
      
      
      /*if(newProductModel != null)
      {
        out.println("<TR class=TableTextNote>");
        out.println("<TD class=TableTextNote width=\"20%\">User Status</TD>");
        out.println("<TD class=TableTextNote>");
        out.println("<SELECT name="+UserInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+productID+
                    " onchange=\"document.UserManagment.update.disabled = false;\">");
        Vector additionalDataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        for(int j = 0; j<additionalDataVector.size(); j++)
        {
          int statusID = ((UserStatusModel)additionalDataVector.elementAt(j)).getUserStatusID();
          String statusName = ((UserStatusModel)additionalDataVector.elementAt(j)).getUserStatusName();
          out.println("<option value=\""+statusID+"\""); 
          if(userStatusID == statusID)
          {
            out.print("selected");
          }
          out.print(">"+statusName+"</option>");
        }
        out.println("</SELECT>");
        out.println("</TD></TR>");


        out.println("<TR class=TableTextNote>");
        out.println("<TD class=TableTextNote width=\"20%\">User Lock</TD>");
        out.println("<TD class=TableTextNote>");
        out.println("<SELECT name="+UserInterfaceKey.CONTROL_SELSCT_LOCK_PREFIX+productID+
                    " onchange=\"document.UserManagment.update.disabled = false;\">");

        Vector vecLocks = (Vector)dataHashMap.get(UserInterfaceKey.HASHMAP_KEY_USER_LOCK_LIST);

         for(int j = 0; j<vecLocks.size(); j++)
        {
          int lockId = ((UserLockModel)vecLocks.elementAt(j)).getUserLockID();
          String lockName = ((UserLockModel)vecLocks.elementAt(j)).getUserLockName();
           out.println("<option value=\""+lockId+"\""); 
          if(userLockId < maxLockTimes.intValue() && lockName.compareTo("NOT LOCKED")==0)
          {
            out.print("selected");
          }
          out.print(">"+lockName+"</option>");
          

        }
        
        out.println("</SELECT>");
        out.println("</TD></TR>");
//        for(int j = 0; j<additionalDataVector.size(); j++)
//        {
//          int statusID = ((UserStatusModel)additionalDataVector.elementAt(j)).getUserStatusID();
//          String statusName = ((UserStatusModel)additionalDataVector.elementAt(j)).getUserStatusName();
//          out.println("<option value=\""+statusID+"\""); 
//          if(userStatusID == statusID)
//          {
//            out.print("selected");
//          }
//          out.print(">"+statusName+"</option>");
//        }
        
        
      }*/
      out.println("<TR class=TableTextNote><TD colspan=2 class=TableTextNote><font size=1>* indicates mandatory fields.</font></TD></TR></TABLE>");
      out.println("</TABLE>");
    
      out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
      out.println("<tr>");
      out.println("<td align=center>");
      
      if(newProductModel != null)
      {
          out.println("<input class=button type=\"button\" name=\"save\" onclick=\"caDownload(false)\" ");
        out.print(" value=\" Update \"");
      }
      else
      {
          out.println("<input class=button type=\"button\" name=\"save\" onclick=\"caDownload(true)\" ");
        out.print(" value=\"   Add   \"");
      }
      
      out.println("</td>");
      out.println("</tr>");
      out.println("</table></form>");
    }
  }
%>
