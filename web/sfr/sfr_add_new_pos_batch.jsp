<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sfr.*"

%>
<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/leftmenu.css">
      <SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/validation.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/lst_add_remove.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/combobox.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/DataViews.js" type=text/javascript></SCRIPT>
  </head>

  <SCRIPT language="javascript">
  function checkQuotes()
  {
    var nKeyCode = event.keyCode;
    if( Number(nKeyCode)== 34 )
    {
        alert("You are not allowed to use the (\") character");
        event.keyCode =0;
        return false;
    }    
    if( Number(nKeyCode)== 39 )
    {
       alert("You are not allowed to use the (\') character");
       event.keyCode = 0;
       return false;
    }   
    return true;
  }

  function data_view_RowSet_add(argCurrentValue,argCurrentName,argCounterName,argControlName,argArrayDataView)
  {
  //Number("+ argControlName + ".RowSet.getRowCount())
      ix = eval("document.SFRform."+argCounterName+".value = Number(document.SFRform."+argCounterName+".value) + 1;");
      ix = ix-1;
      eval(argControlName+".RowSet.add()");
      argCurrentName = argCurrentName+","+argCurrentName;
      for (var i = 0; i < eval(argArrayDataView+".length"); i++)
      {
         if (eval(argArrayDataView+"["+i+"]") == argCurrentValue)
         {
            eval(argControlName+".RowSet.getCell("+Number(ix+1)+",1).cellElement.selectedIndex ="+i+";");                               
         }
      } 
    }

    function app_need_removeRows(argObject)
    {
      i = confirm("This will remove this data")
      if (i==true){
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",5).getValue()")==true){
            eval(argObject+".RowSet.deleteRow("+i+");");
          }//end if
        }//end for
      }//end if
      else
      {
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",5).getValue()")==true){
            eval(argObject+".getCell("+i+",5).setValue == false; ") 
          }//end if
        }//end for
      }//end else
    }

    var UserDefinedDataViewArray =new Array();    
    function popUp(argObj,argVersionArrayName,argDescriptionArrayName)
    {
        var nRowIndex;
        var nSelectedIndex = 0;
        var strPopUpColumnIDVersion = new String();
        var strPopUpColumnIDDescription = new String();
        var strID= new String(argObj.id);
        nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
        strPopUpColumnIDVersion = strID.substring(0,strID.indexOf("__C")+3);
        strPopUpColumnIDVersion +=2
        strPopUpColumnIDDescription = strID.substring(0,strID.indexOf("__C")+3);
        strPopUpColumnIDDescription +=3;

        eval("nSelectedIndex = document.SFRform."+strID+".selectedIndex");
        if(nSelectedIndex > 0)
        {
            var strDescrioption;
            var arrPairs=new Array();
            var strPairs=new String(eval(argVersionArrayName+"["+nSelectedIndex+"];"));
            strDescrioption = eval(argDescriptionArrayName+"["+nSelectedIndex+"];");
            arrPairs = strPairs.split(",");

           eval("document.SFRform."+strPopUpColumnID+".add(objOption);");
     eval("document.SFRform."+strPopUpColumnIDVersion+".value ="+arrPairs[0]+";");
     if(strDescrioption == "" || strDescrioption == null || strDescrioption == "null")
         strDescrioption = "N/A";
     eval("document.SFRform."+strPopUpColumnIDDescription+".value ='"+strDescrioption+"';");

      }
      else
      {
        eval("document.SFRform."+strPopUpColumnIDVersion+".value ='';");
        eval("document.SFRfrom."+strPopUpColumnIDDescription+".value ='';");
      }
    }

    function checknumber(text)
    {
      var x=text
      var anum=/(^\d+$)|(^\d+\.\d+$)/
      if (anum.test(x))
      testresult=true
      else
      {
      testresult=false
      }
      return (testresult)
    }
  
    function checkBeforeSubmit()
    {
      document.SFRform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=SFRInterfaceKey.ACTION_ASSIGN_POS_SHEET_CONTRACTS_SIM_NUMBER%>';
      var inputs = document.getElementsByTagName("input");
      var inputsError = 0;
      for(var i=0;i<inputs.length;i++)
      {
        if(inputs[i].name.charAt((inputs[i].name.length)-1) == "3" || inputs[i].name.charAt((inputs[i].name.length)-1) == "4")
        {
          if(!checknumber(inputs[i].value))
          {
            inputsError++;
          }
        }
      }
      if(inputsError > 0)
      {
        alert("Sheet Serial and Contracts Count must be number and not empty");
      }
      else
      {
        SFRform.submit();
      }
    }

    function addNewEmptyRow(tmpObj)
    {
      var tmpName = tmpObj.name;
      var obj1 = tmpName.substring(0,tmpName.length-1) +"1";
      var obj2 = tmpName.substring(0,tmpName.length-1) +"2";
      var txtObj1 = eval("document.SFRform."+obj1+".value");
      var txtObj2 = eval("document.SFRform."+obj2+".value");
      if((txtObj1 == "" && txtObj2 != "") || (txtObj1 != "" && txtObj2 == ""))
        data_view_RowSet_add('2','half_day','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');
    }
  </SCRIPT>  
<body>
    <CENTER>
      <H2> Create New batch </H2>
    </CENTER>
  <form name='SFRform' id='SFRform' action='' method='post'>
  <%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                    " value=\""+"\">");

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");                
  %>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR>
        <TD class=TableHeader vAlign=top colSpan=2>Batch Sheets
            <A onclick="data_view_RowSet_add('2','half_day','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');">
              <IMG alt="Click Here to add New Row for Data view ... " src="../resources/img/img_sign_dn.gif" border=0>
            </A>  
        </TD>
      </TR>

      <tr>
        <td colspan=2><INPUT type=hidden size=15 value=0 name=user_defined_data_view_count>
          <SCRIPT>
             var user_defined_data_view=new DeepGrid("user_defined_data_view",0,"") 
             user_defined_data_view.ColumnHeaders.add(null,null,"POS Code",null,175,"center",DG_TEXT,null,null,"onChange=addNewEmptyRow(this)")   
             user_defined_data_view.ColumnHeaders.add(null,null,"Second POS Code",null,175,"center",DG_TEXT,null,null,"onChange=addNewEmptyRow(this)")  
             user_defined_data_view.ColumnHeaders.add(null,null,"Sheet Serial",null,175,"center",DG_TEXT,null)
             user_defined_data_view.ColumnHeaders.add(null,null,"Contracts Count",null,175,"center",DG_TEXT,null)  
             user_defined_data_view.ColumnHeaders.add(null,null,"Remove",null,60,null,DG_BOOLEAN,null,null,"onClick = app_need_removeRows('user_defined_data_view')") 
             //user_defined_data_view.ColumnHeaders.add(null,null,"",null,0,"center",DG_HIDDEN,null)   
          </SCRIPT>
        </td>
      </tr>
      
    </table>
    
    <br><br>
    <center>
    <input type="button" class="button" value=" Save " onclick="checkBeforeSubmit();">
    </center>

    <script>
      data_view_RowSet_add('2','half_day','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');
    </script>
  </form>
</body>
</html>
