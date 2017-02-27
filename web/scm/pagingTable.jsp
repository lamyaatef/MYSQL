<%--
    Document   : newjsp
    Created on : Aug 8, 2010, 9:14:05 PM
    Author     : mabdelaal
--%>

<%@page import="com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">



<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Vector"%>

<%

            boolean isFirst = false;
            boolean isLast = false;

            String pageNumber = (String) request.getParameter(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER);

            Long intPageNumber = 0L;
            Long intTotalPageNumber = 0L;
            String stringTotalPageNumber = (String) request.getParameter(SCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER);

            try{
             intPageNumber = Long.valueOf(pageNumber);
              intTotalPageNumber = Long.valueOf(stringTotalPageNumber);
			}catch(Exception e){
            e.printStackTrace();
            }


        	isFirst = intPageNumber==0 ? true : false;

        	//System.out.println("intPageNumber = " + intPageNumber);
        	//System.out.println("intTotalPageNumbe = " + intTotalPageNumber);


         	isLast = (intPageNumber == intTotalPageNumber -1 ) ?  true : false;

            Integer nextPageNumber =  Integer.valueOf(pageNumber) + 1;
            Integer prevPageNumber = Integer.valueOf(pageNumber) - 1;

            pageNumber = Long.valueOf(pageNumber) + 1 +"";

            intPageNumber = intPageNumber +  1;

            String firstPageNumber = (String) request.getParameter(SCMInterfaceKey.FIRST_PAGE_NUMBER);
            String lastPageNumber=null;

            if ( Integer.valueOf(stringTotalPageNumber) == 0)

            	 lastPageNumber = stringTotalPageNumber;
            else
            	 lastPageNumber = Integer.valueOf(stringTotalPageNumber) - 1 +"";







            String actionNameForPaging = (String) request.getParameter(SCMInterfaceKey.ACTION_WHEN_CLICK_ENTER_PAGING);


%>


<table>
    <tr>
        <td>&nbsp;</td>
        <td><span style="font-weight: bolder"><%= intPageNumber%>/<%=stringTotalPageNumber%></span></td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;&nbsp;&nbsp;</td>

        <td>
            <%if (!isFirst) {%>
            <a href="javascript:putDestinationToHidden('<%=firstPageNumber%>');DevChangePageActionWithSubmit('<%=actionNameForPaging%>')" style="font-weight: bolder"><font class="titleStyle">|&lt;</font></a>
            <%} else {%>
            <label style="font-weight: bolder;"></label>
            <%}%>
        </td>
        <td>
            <%if (!isFirst) {%>
            <a href="javascript:putDestinationToHidden('<%=prevPageNumber%>');DevChangePageActionWithSubmit('<%=actionNameForPaging%>')" style="font-weight: bolder;" ><font class="titleStyle">&lt;</font></a>
            <%} else {%>
            <label style="font-weight: bolder;"><font class="titleStyle">&lt;</font></label>
            <%}%>


        </td>
        <td>
            <%if (!isLast) {%>
            <a href="javascript:putDestinationToHidden('<%=nextPageNumber%>');DevChangePageActionWithSubmit('<%=actionNameForPaging%>')" style="font-weight: bolder" ><font class="titleStyle">&gt;</font></a>
            <%} else {%>
            <label style="font-weight: bolder;"><font class="titleStyle">&gt;</font></label>
            <%}%>
        </td>
        <td>
            <%if (!isLast) {%>
            <a href="javascript:putDestinationToHidden('<%=lastPageNumber%>');DevChangePageActionWithSubmit('<%=actionNameForPaging%>')" style="font-weight: bolder" ><font class="titleStyle">&gt;|</font></a>
            <%} else {%>
            <label style="font-weight: bolder;"><font class="titleStyle">&gt;|</font></label>
            <%}%>
        </td>
    </tr>

</table>
            <input name="buttonForGoPage" id="buttonForGoPage" type="button" onclick="javascript:if (checkBeforeSubmit()){putDestinationToHidden(document.getElementById('<%=SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER%>').value - 1 );DevChangePageActionWithSubmit('<%=actionNameForPaging%>');}" style="display: none;"/>

<input type="hidden" id="destinationPage" name="destinationPage" value="0"/>
<input type="hidden" id="currentPage" name="currentPage" value="<%=pageNumber%>"/>

<script type="text/javascript">

    function putDestinationToHidden (pageNumber){
        document.getElementById('destinationPage').value = pageNumber;

    }
    function checkBeforeSubmit(){
       if (document.getElementById('<%=SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER%>').value > <%=stringTotalPageNumber%>)
       {
           alert('Page number greater than maximum pages.');
           return false;
       }
       if (document.getElementById('<%=SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER%>').value <= 0)
       {
           alert('Page number less than 0.');
           return false;
       }
       return true;



    }

</script>