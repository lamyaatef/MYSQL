<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"

%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
  <body>
<script>
    function checkBeforeSubmit()
    {
      
    }

    function Toggle(item) 
    {
      obj=document.getElementById(item);
      if (obj!=null) 
      {
        visible=(obj.style.display!="none")
        key=document.getElementById("x"+item);
        if (visible) 
        {
          obj.style.display="none";
          key.innerHTML="<img src='../resources/img/plus.gif' border='0'>";
        } 
        else 
        {
          obj.style.display="block";
          key.innerHTML="<img src='../resources/img/minus.gif' border='0'>";
        }
      }
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            
%>   
    <CENTER>
      <H2> Rigonal Management </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             
%> 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableTextNote>
        <td width=3%><A id="xparent1" href="javascript:Toggle('parent1');"><img src='../resources/img/plus.gif' border='0'></A></td>                                 
        <td align='left'><a href="#" onclick="">Egypt</a></td>
        <td align='right'><input type='radio' name='destintion' id='destintion'></td>
      </tr>
      <tr class=TableTextNote>
        <td colspan=3>
          <div style="display:none" id="parent1" name="parent1">
          <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            <tr>
              <td width=10%></td>
              <td width=3%><A id="xparent2" href="javascript:Toggle('parent2');"><img src='../resources/img/plus.gif' border='0'></A></td>                                 
              <%
                out.println("<td align='left'><a href=\"#\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_EDIT_REGION+"';"+
                      "DCMform.submit();\">Cairo</a></td>");
              %>  
              <td align='right'><input type='radio' name='destintion' id='destintion'></td>
            </tr>
            <tr>
              <td colspan=4>
                <div style="display:none" id="parent2" name="parent2">
                <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
                  <tr>
                    <td width=20%></td>
                    <td width=3%></td>                                 
                    <td align='left'><a href="#" onclick="">Masr El Gdida</a></td>
                    <td align='right'><input type='radio' name='destintion' id='destintion'></td>
                  </tr>
                  <tr>
                    <td width=20%></td>
                    <td width=3%></td>                                 
                    <td align='left'><a href="#" onclick="">Maadi</a></td>
                    <td align='right'><input type='radio' name='destintion' id='destintion'></td>
                  </tr>
                </table>
                </div>
              </td>
            </tr>
            <tr>
              <td width=10%></td>
              <td width=3%><A id="xparent3" href="javascript:Toggle('parent3');"><img src='../resources/img/plus.gif' border='0'></A></td>                                 
              <td align='left'><a href="#" onclick="">Alex</a></td>
              <td align='right'><input type='radio' name='destintion' id='destintion'></td>
            </tr>
            <tr>
              <td width=10%></td>
              <td width=3%><A id="xparent4" href="javascript:Toggle('parent4');"><img src='../resources/img/plus.gif' border='0'></A></td>                                 
              <td align='left'><a href="#" onclick="">Aswan</a></td>
              <td align='right'><input type='radio' name='destintion' id='destintion'></td>
            </tr>
          </table>
          </div>
        </td>
      </tr>
      </table>

      <br><br>
      <center>
        <%
        out.print("<INPUT class=button type=button value=\" Finish \" name=\"moveregions\" id=\"moveregions\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_REGIONAL_MANAGEMENT_TREE+"';"+
                  "DCMform.submit();\">");          
        %>
      </center>
</form>
</body>
</html>
