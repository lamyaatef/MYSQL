<%@page import="com.mobinil.sds.core.system.sa.product.model.ProductModel"%>
<%@page import="com.mobinil.sds.core.system.request.model.ChannelModel"%>
<%@page import="com.mobinil.sds.core.system.dataMigration.model.PaymentLevelModel"%>
<%@ page import="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
%>
<%
/**
 * User_Managment.jsp:
 * Display the system Users and each user roles.
 * 
 * showUsers(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Display the system Users.
 *
 * showRoles(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Build hidden divs, each holding roles of one user.
 * A user roles div will be visible if the user was selected.
 *
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 
    System.out.println("IN PRODUCT MANAGEMENT jsp");
    String appName = request.getContextPath();
   // HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_COLLECTION);
    //String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  //  System.out.println("user id : "+userID);

%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template2.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/utilities.js" type=text/javascript></SCRIPT>
  </head>
  <body>
    <Center>
      <H2>Product Management</H2>
    </Center>
    <script type="text/javascript">
      function toggle(item1)
      {
        divs=document.getElementsByTagName("DIV");
        for (i=0;i<divs.length;i++) 
        {
          if(divs[i].id != item1 && divs[i].id != 'listofusers')
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
    <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="ProductManagement" method="post">

      
      
     
      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" 
      value="<%out.print(AdministrationInterfaceKey.ACTION_ADD_PRODUCT);%>">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" 
      value="<%out.print("anyname");%>">

      <input type="hidden" name="<%out.print(AdministrationInterfaceKey.CONTROL_HIDDEN_NAME_PRODUCT_ID);%>" value=0>
      
      
      
      
      
      <div name="listofusers" id="listofusers" >
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td width="10%" nowrap align=center>Product ID</td>
          <td width="30%" nowrap align=center>Product Name</td>
          <td width="10%" nowrap align=center>Product Description</td>
          <td width="10%" nowrap align=center>Product Category ID</td>
          <td width="30%" nowrap align=center>Product Category Name</td>
          <td width="10%" nowrap align=center>Edit</td>
        </tr>	
        <%
       showProducts(request, response, out);
        %>
      </TABLE>
      
      
      <table  cellspacing="2" cellpadding="1" border="0" width="100%">
        <tr>
          <td align=center>
            <input class=button type="button" name="new" value=" New Product " 
            onclick="document.ProductManagement.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(AdministrationInterfaceKey.ACTION_NEW_PRODUCT);%>';ProductManagement.submit();">
            
          </td>
        </tr>
      </table>   
      </div>
    </form>
    <%
   //showRoles(request, response, out);
    %>
  </body>
</html>
<%
  /**
   * Display any error messages returning from the server.
   */ 

  /*String strError = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
  if(strError != null)
  {
    out.println("<script type=\"text/javascript\">alert('"+strError+"');</script>");
  }*/
%>

<%!
  /**
   * showUsers method: 
   * Display the system Users.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showProducts(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    System.out.println("in show product product management jsp");
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty()){
      
      Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      System.out.println("inside datahashmap if dataVector.size() : "+dataVector.size()); 
      for(int i = 0; i<dataVector.size(); i++){
          System.out.println("Products SIZE : "+dataVector.size());
        ProductModel productModel = (ProductModel) dataVector.get(i);
        String productId = productModel.getProductId();
        String productName = productModel.getProductName();
        String productDesc = productModel.getProductDesc();
        String productCatId = productModel.getProductCategoryId();
        String productCatName = productModel.getProductCategoryName();
        out.println("<TR class=TableTextNote>");
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+"><a href=\"#divname"+productId+"\" onclick=\"javascript:toggle('"+productId+"');\">"+productId+"</a></TD>");
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+">"+productName+"</TD>");
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+">"+productDesc+"</TD>");
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+"><a href=\"#divname"+productCatId+"\" onclick=\"javascript:toggle('"+productCatId+"');\">"+productCatId+"</a></TD>");
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+">"+productCatName+"</TD>");
        
        
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+" align=middle>");
        out.println("<input class=button type=\"button\" name=\""+AdministrationInterfaceKey.CONTROL_BUTTON_NAME_EDIT_PRODUCT_PREFIX+productId+"\" value=\"   Edit   \"");
        out.println("<input type=\"hidden\" name=\""+AdministrationInterfaceKey.CONTROL_HIDDEN_NAME_PRODUCT_ID+"\" value=\""+productId+"\" ");
        out.println("onclick=\"document.ProductManagement."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+AdministrationInterfaceKey.ACTION_EDIT_PRODUCT+"';");
        out.println("document.ProductManagement."+AdministrationInterfaceKey.CONTROL_HIDDEN_NAME_PRODUCT_ID+".value='"+productId+"';");
        out.println("ProductManagement.submit();\">");
        out.println("</TD>");
        out.println("</TR>");
      }
    }
  }

%>