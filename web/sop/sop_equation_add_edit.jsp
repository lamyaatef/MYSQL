<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"
         import="com.mobinil.sds.web.interfaces.cc.*"
         import="com.mobinil.sds.core.system.sop.equations.dto.*"
         import="com.mobinil.sds.core.system.sop.equations.model.*"
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    <SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
    <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
  </head>
  <body>
    <Center>
      <H2>Equation</H2>
    </Center>
    <script>
        var arrElementTypesArray=new Array
             ( 
  <%fillInElementTypes(request,response,out);%>                    
             ) 
  var arrArithmaticOperatorsArray=new Array
             ( 
  <%fillInOperators(request,response,out);%>                    
             ) 
  var arrEquationTermsArray=new Array
             ( 
  <%fillInEquationTerms(request,response,out);%>
             )

  function penaltyeq_RowSet_add(current_value,current_name)
  {
      document.formEquationManagment.penaltyeq_count.value = penaltyeq.RowSet.getRowCount();
      ix = document.formEquationManagment.penaltyeq_count.value = penaltyeq.RowSet.getRowCount();
      penaltyeq.RowSet.add();
      current_name = current_name+","+current_name;
      for (i = 0; i < arrElementTypesArray.length; i++)
      {
        if (arrElementTypesArray[i] == current_value)
        {
          penaltyeq.RowSet.getCell(ix+1,1).cellElement.selectedIndex = i;
        }
      }
    }
    
    function function_removeRows()
    {
        i = confirm("This will remove this data")
        if (i==true){
          for(var i=1;i<=penaltyeq.getRowCount();i++){
            if(penaltyeq.getCell(i,4).getValue()==true)
            {
              penaltyeq.RowSet.deleteRow(i);
            }//end if  
          }//end for
        }//end if
        else{
          for(var i=penaltyeq.getRowCount();i>=1;i--){
            if(penaltyeq.getCell(i,4).getValue()==true){
              penaltyeq.getCell(i,4).setValue() == false
            }//end if
          }//end for
        }//end else
       }
       
       function deleteValues(argID)
       {
          var nRowIndex;
          var nSelectedValue = 0;
          var strPopUpColumnID = new String();
          var strID= new String(argID);
          nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
          eval("document.formEquationManagment.penaltyeq__R"+nRowIndex+"__C2.value = '';");
       }
       
       function openPenaltyEquationTerms(argID)
       {
         var objArguments = new Array(); 
         var strSelectedTypeName = "";
         var nRowIndex;
         var nSelectedValue = 0;
         var strPopUpColumnID = new String();
         var strID= new String(argID);
         nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
         strSelectedTypeName = eval("document.formEquationManagment.penaltyeq__R"+nRowIndex+"__C1.options[document.formEquationManagment.penaltyeq__R"+nRowIndex+"__C1.selectedIndex].text;");
         var strTermType = '<%=CommissionInterfaceKey.PENALTY_EQUATION_OBJECT_TYPE_TERM%>';

         if(strSelectedTypeName.toLowerCase() == strTermType.toLowerCase())
         {
             objArguments[0] = arrEquationTermsArray;
             objArguments[1] = "Equation Terms";
             var sFeatures="dialogHeight: 200px; dialogWidth: 350px"
             var objWin = showModalDialog("../cc/CC_PenaltyEquationHelp.jsp",objArguments,sFeatures);
             if(objWin != null)
             {
                eval("document.formEquationManagment.penaltyeq__R"+nRowIndex+"__C1.options[0].selectedID = '"+objWin[0]+"'");
                eval("document.formEquationManagment.penaltyeq__R"+nRowIndex+"__C2.value = '"+objWin[1]+"'");
            } 
         }
         else
         {  
             var strTermType = '<%=CommissionInterfaceKey.PENALTY_EQUATION_OBJECT_TYPE_OPERATOR%>';
             if(strSelectedTypeName.toLowerCase() == strTermType.toLowerCase())
             {
                 objArguments[0] = arrArithmaticOperatorsArray;
                 objArguments[1] = "Arithmatic Operators";
                 var sFeatures="dialogHeight: 200px; dialogWidth: 350px"
                 var objWin = showModalDialog("../cc/CC_PenaltyEquationHelp.jsp",objArguments,sFeatures);
                 if(objWin != null)
                 {
                    eval("document.formEquationManagment.penaltyeq__R"+nRowIndex+"__C1.options[0].selectedID = '"+objWin[0]+"'");
                    eval("document.formEquationManagment.penaltyeq__R"+nRowIndex+"__C2.value = '"+objWin[1]+"'");
                } 
             }
             else
                alert("Please select a Term or Operator for list of the available elements..");
         }       
     }
    function validateEquation()
    {
        var bValid = false; 
        for(var t=1;t<=penaltyeq.getRowCount();t++)
        {
            if(penaltyeq.getCell(t,2).getValue() != '')
            {
              bValid = false;
              strSelectedTypeName = eval("document.formEquationManagment.penaltyeq__R"+t+"__C1.options[document.formEquationManagment.penaltyeq__R"+t+"__C1.selectedIndex].text;");
              strValue = penaltyeq.getCell(t,2).getValue();
              var strTermType = '<%=CommissionInterfaceKey.PENALTY_EQUATION_OBJECT_TYPE_TERM%>';
              if(strSelectedTypeName.toLowerCase() == strTermType.toLowerCase())
              {
                for(var i=0;i<arrEquationTermsArray.length;i++)
                {
                    if(strValue == arrEquationTermsArray[i][0])
                    {
                        bValid = true;
                        eval("document.formEquationManagment.penaltyeq__R"+t+"__C1.options[0].selectedID = '"+arrEquationTermsArray[i][1]+"';");                          
                    }
                }
              }
              else
              {  
                 var strTermType = '<%=CommissionInterfaceKey.PENALTY_EQUATION_OBJECT_TYPE_OPERATOR%>';
                 if(strSelectedTypeName.toLowerCase() == strTermType.toLowerCase())
                 {
                   for(var i=0;i<arrArithmaticOperatorsArray.length;i++)
                   {

                        if(strValue == arrArithmaticOperatorsArray[i][0])
                        {
                          bValid = true;
                          eval("document.formEquationManagment.penaltyeq__R"+t+"__C1.options[0].selectedID = '"+arrArithmaticOperatorsArray[i][1]+"';");                          
                        }
                    } 
                 }
                 else
                 {
                    if(!isNaN(Number(strValue)))
                       bValid = true;
                   }     
              }      
              if(bValid == false)
              {
                   alert(" The equation element number "+t+" is not a valid element, please correct the error then try again") 
                   break;
              }
           }   
        }
        return bValid;
    }


    function saveEquation()
     {
          //if(validateForm())
          {
               for (var i = 1; i <= penaltyeq.getRowCount(); i++) 
               {
                   var objHidden= document.createElement("INPUT");
                   objHidden.type ="hidden";
                   objHidden.id= "<%=SOPInterfaceKey.CONTROL_HIDDEN_NAME_EQ_ELEMENT_TYPE_ID_PREFIX%>"+Number(i);
                   objHidden.name= "<%=SOPInterfaceKey.CONTROL_HIDDEN_NAME_EQ_ELEMENT_TYPE_ID_PREFIX%>"+Number(i);
                   objHidden.value = penaltyeq.getCell(i,1).getValue();
                   document.formEquationManagment.appendChild(objHidden);
                   var nValue; 
                   var strElementType = '<%=CommissionInterfaceKey.PENALTY_EQUATION_OBJECT_TYPE_FACTOR%>'
                   if(penaltyeq.RowSet.getCell(i,1).cellElement.options[penaltyeq.RowSet.getCell(i,1).cellElement.selectedIndex].text.toLowerCase() == strElementType.toLowerCase())
                      nValue = penaltyeq.getCell(i,2).getValue();
                   else
                      nValue = penaltyeq.RowSet.getCell(i,1).cellElement.options[0].selectedID;

                   var objHidden= document.createElement("INPUT");
                   objHidden.type ="hidden";
                   objHidden.id= "<%=SOPInterfaceKey.CONTROL_HIDDEN_NAME_EQ_ELEMENT_VALUE_PREFIX%>"+Number(i);
                   objHidden.name= "<%=SOPInterfaceKey.CONTROL_HIDDEN_NAME_EQ_ELEMENT_VALUE_PREFIX%>"+Number(i);
                   objHidden.value = nValue;
                   document.formEquationManagment.appendChild(objHidden);
              }
             // eval("document.formEquationManagment.penaltyeq_count.value="+penaltyeq.Rowset.getRowCount()+";");
          } 
        var equationName = document.formEquationManagment.<%=SOPInterfaceKey.INPUT_TEXT_EQUATION_NAME%>.value
        if(equationName == "")
        {
          alert("You must insert equation name.");
        }
        else
        {
          document.formEquationManagment.submit();
        }
     }
    </script>

    <%
        showEquation(request, response, out);
    %>    
    <TR class=TableHeader><TR><TD class=TableHeader vAlign=top colSpan=9>Add Element<A onclick="penaltyeq_RowSet_add('2','half_day');">
    <IMG alt="Click Here to add a element in the equation ... " src="../resources/img/img_sign_dn.gif" border=0></A>
    </TD></TR>
        
    <TR><TD class=TableTextColumn vAlign=top align=middle colSpan=2>
   <TABLE style="BORDER-COLLAPSE: collapse" borderColor=#000080 cellSpacing=0 cellPadding=0 align=left border=1>
   <TBODY><TR><TD class=TableTextColumn><INPUT type=hidden size=15 value=0 name=penaltyeq_count>
    <script>
    var penaltyeq=new DeepGrid("penaltyeq",0,"");
    penaltyeq.ColumnHeaders.add(null,null,"Element Type",null,300,null,DG_SELECT,arrElementTypesArray,null,"onChange=deleteValues(this.id)");
    penaltyeq.ColumnHeaders.add(null,null,"Element Value",null,300,null,DG_TEXT,null);
    penaltyeq.ColumnHeaders.add(null,null,"(+)    ",null,20,"center",DG_BUTTON,"    ...",null,"onClick = openPenaltyEquationTerms(this.id)");
    penaltyeq.ColumnHeaders.add(null,null,"Remove",null,60,null,DG_BOOLEAN,null,null,"onClick = function_removeRows()")
    </script>
    <%
    showEquationElements(request, response, out);

    
%>
</body>
<%
  HashMap dataHashMap = new HashMap(100);
  dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strChannelId = (String)dataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  Utility.logger.debug("Channel id issssssssssss " + strChannelId);
  
  String strError = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
  if(strError != null)
  {
    out.println("<script type=\"text/javascript\">alert('"+strError+"');</script>");
  }

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
                  " value=\""+strChannelId+"\">"); 
  %>

<%!
  public void fillInElementTypes(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
      String strElementTypeID;
      String strElementTypeName;
      HashMap hmDataHashMap = new HashMap(100);
      hmDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      EquationDTO dtoEquation = (EquationDTO)hmDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);       
      Vector colElementTypes = dtoEquation.getVecEquationObjectType(); 
      if(colElementTypes != null)
      for (int i = 0; i <colElementTypes.size() ; i++) 
      {
         strElementTypeID = ((EquationObjectTypeModel)colElementTypes.elementAt(i)).getEquationObjectTypeId();
         strElementTypeName = ((EquationObjectTypeModel)colElementTypes.elementAt(i)).getEquationObjectTypeName();
         out.println("new Array('"+strElementTypeName+"',"+strElementTypeID+")");
         if(i<colElementTypes.size()-1)
           out.println(",");
      }
  }
  public void fillInOperators(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
      String strOperatorID;
      String strOperatorValue;
      HashMap hmDataHashMap = new HashMap(100);
      hmDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      EquationDTO dtoEquation = (EquationDTO)hmDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);       
      Vector colOperators = dtoEquation.getVecEquationOperator();   
      if(colOperators != null)
      for (int i = 0; i <colOperators.size() ; i++) 
      {
         strOperatorID = ((EquationOperatorModel)colOperators.elementAt(i)).getEquationOperatorId();
         strOperatorValue = ((EquationOperatorModel)colOperators.elementAt(i)).getEquationOperator();
         out.println("new Array('"+strOperatorValue+"',"+strOperatorID+")");
         if(i<colOperators.size()-1)
           out.println(",");
      }
  }
    public void fillInEquationTerms(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
      String equationTermID;
      String strEquationTermName;
      HashMap hmDataHashMap = new HashMap(100);
      hmDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      EquationDTO dtoEquation = (EquationDTO)hmDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);       
      Vector colEquationTerms = dtoEquation.getVecEquationTerms();
      if(colEquationTerms != null)
      for (int i = 0; i <colEquationTerms.size() ; i++) 
      {
         equationTermID = ((EquationTermModel)colEquationTerms.elementAt(i)).getEquationTermId();
         strEquationTermName = ((EquationTermModel)colEquationTerms.elementAt(i)).getEquationTermName();
         out.println("new Array('"+strEquationTermName+"',"+equationTermID+")");
         if(i<colEquationTerms.size()-1)
           out.println(",");
      }
  }

public void showEquation(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
{
    HashMap hmDataHashMap = new HashMap(100);
    hmDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    EquationDTO dtoEquation  = (EquationDTO)hmDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    String strUserId = (String)hmDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
//    String strErrorMessage  = (String)hmDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    
    Vector colEquationOperators = null;
    Vector vecEquation = null;    
    if(dtoEquation != null)
    {
        colEquationOperators = dtoEquation.getVecEquationOperator();
        vecEquation = dtoEquation.getVecEquation();
        
        out.println("<form action=\"../servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"formEquationManagment\" id=\"formEquationManagment\" method=\"post\">");
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
    }
    out.println("<input type=\"hidden\" name="+InterfaceKey.HASHMAP_KEY_ACTION+" id="+InterfaceKey.HASHMAP_KEY_ACTION+">");

    out.println("<input type=\"hidden\" name="+InterfaceKey.HASHMAP_KEY_USER_ID+" id="+strUserId+">");

    int equationId = 0;
    String equationTitle = "";
    String equationDescription ="";
    
    if(vecEquation.size() != 0)
    {
      EquationModel equationModel = (EquationModel)vecEquation.get(0);
      equationId = equationModel.getEquationId();
      equationTitle = equationModel.getEquationTitle();
      equationDescription = equationModel.getEquationDescription();
      if(equationDescription == null)equationDescription = "";
    }
    out.println("<input type=\"hidden\" name="+SOPInterfaceKey.INPUT_HIDDEN_EQUATION_ID+" id="+SOPInterfaceKey.INPUT_HIDDEN_EQUATION_ID+" value='"+equationId+"'>");
    
    out.println("<TR><TD class=TableTextNote nowrap width=\"200px\">Equation Name:</TD><TD class=TableTextNote>");
    out.println("<input type=\"text\" name="+SOPInterfaceKey.INPUT_TEXT_EQUATION_NAME+" id="+SOPInterfaceKey.INPUT_TEXT_EQUATION_NAME+" value='"+equationTitle+"' >");
    out.println("</TD></TR>");

    out.println("<TR><TD class=TableTextNote nowrap width=\"200px\">Equation Description:</TD><TD class=TableTextNote>");
    out.println("<TEXTAREA name="+SOPInterfaceKey.INPUT_TEXTAREA_EQUATION_DESCRIPTION+" id="+SOPInterfaceKey.INPUT_TEXTAREA_EQUATION_DESCRIPTION+" maxlength=256 style=\"WIDTH: 550px; HEIGHT: 50px\" rows=1 cols=40>");
    out.println(equationDescription+"</TEXTAREA></TD></TR>");
 //   out.println("<TR><TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=0>");
}

  public void showEquationElements(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap hmDataHashMap = new HashMap(100);
    hmDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    EquationDTO dtoEquation = (EquationDTO)hmDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);      
    Vector colEquationElements = dtoEquation.getVecEquationElements(); 
    Vector vecEquation = dtoEquation.getVecEquation();
    if(colEquationElements != null)
    {
        if(colEquationElements.size() > 0)
        {
          out.println("<script>");
          out.println("eval(\"document.formEquationManagment.penaltyeq_count.value = '"+ colEquationElements.size() +"';\");");
          out.println("var arrValuesIDs = new Array()");
          for(int i = 0; i< colEquationElements.size(); i++)
          {
            EquationElementModel equationElementModel = (EquationElementModel)colEquationElements.get(i);
            int equationElementType = equationElementModel.getEquationElementType();
            String equationElementValue = equationElementModel.getEquationElementValue();
            String equationElementValueId = equationElementModel.getEquationElementValueId();
            
            int nRowIndex = i+1;
            
            out.println("arrValuesIDs["+i+"] = "+equationElementType+";");
            out.println("eval(\"penaltyeq.RowSet.add("+nRowIndex+");\");");
            out.println("eval(\"document.formEquationManagment.penaltyeq__R"+nRowIndex+"__C1.value = '"+ equationElementType +"';\");");
            out.println("eval(\"document.formEquationManagment.penaltyeq__R"+nRowIndex+"__C2.value = '"+ equationElementValue +"';\");");
            out.println("eval(\"document.formEquationManagment.penaltyeq__R"+nRowIndex+"__C1.options[0].selectedID = '"+equationElementValueId+"';\");");
            
           }
           out.println("</script>");
        }  
    }
    else
    {
          out.println("<script language=javascript>");
          out.println("penaltyeq_RowSet_add('','');");
          out.println("</script>");
    }
    String nextAction = "";
    if(vecEquation.size()==0)
    {
      nextAction = SOPInterfaceKey.ACTION_SAVE_EQUATION;
    }
    else if(vecEquation.size()>0)
    {
      nextAction = SOPInterfaceKey.ACTION_UPDATE_EQUATION;
    }
    out.println("</TD></TR></TBODY></Table></TD></TR><tr class=TableTextNote><td align=center colspan=2>");
    out.println("        <input class=\"button\" type=\"button\" name=\"butSave\" value=\" Save \" ");
    out.println("        onclick=\"document.formEquationManagment."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+nextAction+"';saveEquation();\">");
    out.println("</td></tr></TABLE>");

  }
   

%>
<br>
<center>
<%
out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>
</center>
</html>
