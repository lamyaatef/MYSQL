<%@ page buffer="256kb" %>
<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.IOException"
         import="java.io.PrintWriter"
         import="java.util.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import="com.mobinil.sds.core.system.gn.dataview.dto.*"
%>
<script>

function popUpDescription()
{
   if(eval("document.formDataViewList.<%=QueryBuilderInterfaceKey.PARAM_UPDATE_DATAVIEW_DATAVIEW_ID%>.options[document.formDataViewList.<%=QueryBuilderInterfaceKey.PARAM_UPDATE_DATAVIEW_DATAVIEW_ID%>.selectedIndex].desc != null"))
      eval("document.formDataViewList.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>.value = document.formDataViewList.<%=QueryBuilderInterfaceKey.PARAM_UPDATE_DATAVIEW_DATAVIEW_ID%>.options[document.formDataViewList.<%=QueryBuilderInterfaceKey.PARAM_UPDATE_DATAVIEW_DATAVIEW_ID%>.selectedIndex].desc")
   else
      eval("document.formDataViewList.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>.value = \"N/A\"");
    
}




</script>
<%!     
/**
 * Fill in the data views retrieved from the backend side that can be updated
 * @param argRequest HttpServletRequest,argOut JspWriter 
 * @throws Exception if the IO operation failed
 * @return 
 * @see 
 */
 
 private void fillInDataViews(HttpServletRequest argRequest,JspWriter argOut) throws IOException
 {
    int nCurrentIssue                                        = -1;
    StringBuffer strUserdefinedDataViewOptions               = new StringBuffer("<tr class=TableHeader><td class=TableHeader>Available Data Views</td><td class=TableHeader>");
    strUserdefinedDataViewOptions.append("<select onchange=\"return popUpDescription();\" name="+QueryBuilderInterfaceKey.PARAM_UPDATE_DATAVIEW_DATAVIEW_ID+" id="+QueryBuilderInterfaceKey.PARAM_UPDATE_DATAVIEW_DATAVIEW_ID+" style=\"HEIGHT: 22px\">");
             
    
    Vector colDataViews                                      = null;
    
    HashMap hmData                                           = new HashMap(100);
    BriefDataViewDTO dtoBriefDataView                        = null;
    
    hmData = (HashMap)argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
     
    colDataViews = (Vector)hmData.get(InterfaceKey.HASHMAP_KEY_COLLECTION); 
    if(colDataViews != null)
    {
        strUserdefinedDataViewOptions.append("<option desc=''></option>");
        for (int i = 0; i < colDataViews.size(); i++) 
        {
            dtoBriefDataView = (BriefDataViewDTO)colDataViews.elementAt(i);
            if (dtoBriefDataView.getOverRideSQL() !=null)
            {
            System.out.println(" not going to display "+ dtoBriefDataView.getDataViewName());
            System.out.println(" the over ride sql is  "+ dtoBriefDataView.getOverRideSQL());
            }
            else
            {            
              strUserdefinedDataViewOptions.append(" <option desc='"+dtoBriefDataView.getDataViewDescription()+"' value="+dtoBriefDataView.getDataViewID()+" title='"+dtoBriefDataView.getDataViewName()+"'>"+dtoBriefDataView.getDataViewName()+"</option> ");
            }
        
        }
    } 
    strUserdefinedDataViewOptions.append("</select>");
    argOut.println(strUserdefinedDataViewOptions.toString());
 }
%>
<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1256"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/leftmenu.css">
      <SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/validation.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/lst_add_remove.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/combobox.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/DataViews.js" type=text/javascript></SCRIPT>

<title>Data View List
</title>
</head>

<div style="display:none" id=<%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>>

<body leftmargin=0 topmargin=0>
   <form id="formDataViewList" name="formDataViewList" action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" method="post" target="_parent">
   <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" id="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="">    
   <CENTER>
      <H2>Data Views List</H2>
    </CENTER>
        <table  cellspacing="0" cellpadding="0" width="100%" border="0">
            <%fillInDataViews(request,out);%>
          <tr class=TableHeader>
            <td class=TableHeader valign = top>Data View Description</td>
            <td class=TableHeader><TEXTAREA readonly style="WIDTH: 509px; HEIGHT: 86px" name="<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>" id="<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>" rows=5 cols=47></TEXTAREA></td>
          </tr>
              <tr class=TableHeader>
            <td></td>
            <td class=TableHeader valign=top align=middle><input type="button" value="Update" class="button" onclick="return butUpdate_onclick()"></td>
          </tr>
        </table><br>
        </body>
       

<script>
function butUpdate_onclick()
{
  <% if(request.getParameter(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_LIST_GENERAL_DATAVIEWS))
  {%>  
      document.formDataViewList.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=QueryBuilderInterfaceKey.ACTION_UPDATE_GENERAL_DATAVIEW%>'
  <%}
  else
  if(request.getParameter(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_LIST_ELIGIBLE_DATAVIEWS))
  {%>  
      document.formDataViewList.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=QueryBuilderInterfaceKey.ACTION_UPDATE_ELIGIBLE_DATAVIEW%>'
  <%}
  else
  if(request.getParameter(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_LIST_KPI_DATAVIEWS))
  {%>  
      document.formDataViewList.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=QueryBuilderInterfaceKey.ACTION_UPDATE_KPI_DATAVIEW%>'
  <%}
  else
  if(request.getParameter(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_LIST_DCM_QUOTA_CALCULATION_DATAVIEWS))
  {%>  
      document.formDataViewList.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=QueryBuilderInterfaceKey.ACTION_UPDATE_DCM_QUOTA_CALCULATION_DATAVIEW%>'
  <%}
  %>
  if(document.formDataViewList.<%=QueryBuilderInterfaceKey.PARAM_UPDATE_DATAVIEW_DATAVIEW_ID%>.value =='')
  {
     alert(" Please select a dataview to update."); 
  }
  else
  {
    document.formDataViewList.submit();
  }
}
</script>


</div>
<script><%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>.style.display = "block"</script>

</html>