<%@ page contentType="text/html;charset=windows-1252"%>

<%@ page import ="javax.servlet.*"

         import="javax.servlet.http.*"

         import="java.io.PrintWriter"

         import="java.io.IOException"

         import="java.util.*"

         import="javax.servlet.jsp.*"

         import="com.mobinil.sds.web.interfaces.*"

         import="com.mobinil.sds.web.interfaces.sfr.*"

 

         import ="com.mobinil.sds.core.system.sfr.sheets.model.*"  

%>

<html>

  <head>

      <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">

      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">

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

      ix = ix;

      eval(argControlName+".RowSet.add()");

      argCurrentName = argCurrentName+","+argCurrentName;

      eval("document.SFRform.user_defined_data_view__R"+ix+"__C1.focus()");

      for (var i = 0; i < eval(argArrayDataView+".length"); i++)

      {

         if (eval(argArrayDataView+"["+i+"]") == argCurrentValue)

         {

            eval(argControlName+".RowSet.getCell("+Number(ix+1)+",1).cellElement.selectedIndex ="+i+";");                              

         }

      }

    }

 

    function app_need_removeRows(argObject,showMsg,inputRemoveName)

    {

    if(showMsg)

    {

      i = confirm("This will remove this data")

    }

    else

    {

      i = true;

    }

      if (i==true){

        for(var i=eval(argObject+".getRowCount()");i>=1;i--){

          if(eval(argObject+".getCell("+i+",2).getValue()")==true){

            eval(argObject+".RowSet.deleteRow("+i+");");

          }//end if

        }//end for

      }//end if

      else

      {

        for(var i=eval(argObject+".getRowCount()");i>=1;i--){

          if(eval(argObject+".getCell("+i+",2).getValue()")==true){

            eval(argObject+".getCell("+i+",2).setValue == false; ")

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

    function checkBeforeSubmit()

    {
        
        

      document.SFRform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=SFRInterfaceKey.ACTION_SAVE_SHEET_CONTRACTS_SIM_NUMBER%>';

      var inputs = document.getElementsByTagName("input");

      var inputsError = 0;

      for(var i=0;i<inputs.length;i++)

      {

        if(inputs[i].name.charAt((inputs[i].name.length)-1) == "1")

        {

          if(inputs[i].value.length == 0)

          {

            var tmpName = inputs[i].name;

            var obj1 = tmpName.substring(0,tmpName.length-1) +"2";

            

            eval("document.SFRform.CHK__"+obj1+".checked = true");

          }

          else

          {

            if(!checknumber(inputs[i].value) || inputs[i].value.length != 24 )

            {

              if(!checknumber(inputs[i].value) || inputs[i].value.length != 20 )

              {
                  
         

              inputsError++;

              }

            }
            
            if(!checkForDuplicates(inputs[i].value,inputs[i].name))
                {   
                    
                   
                    inputsError++;
                }

          }

        }

      }
    

        app_need_removeRows('user_defined_data_view',false);     

      if(inputsError > 0)

      {
          
        alert(inputsError+" contract SIM numbers are not valid");

      }

      else

      {

        SFRform.submit();

      }

    }
    

 

    function getNumberOfContractsScaned()

    {

      var inputs = document.getElementsByTagName("input");

      var inputsError = 0;

      var numberOfContractsScaned = 0;

      for(var i=0;i<inputs.length;i++)

      {

        if(inputs[i].name.charAt((inputs[i].name.length)-1) == "1")

        {

          var tmpName = inputs[i].name;

          var obj1 = tmpName.substring(0,tmpName.length-1) +"2";

          if(inputs[i].value.length == 0 || eval("document.SFRform.CHK__"+obj1+".checked == true"))

          {

            eval("document.SFRform.CHK__"+obj1+".checked = true");

          }

          else

          {

              numberOfContractsScaned++;

          }

        }

      }

        app_need_removeRows('user_defined_data_view',false);

        document.SFRform.number_of_sims_scaned.value = numberOfContractsScaned;

      //if(inputsError > 0)

      //{

      //  alert(inputsError+" contract SIM numbers are not valid");

      //}

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

 

    function checkForDuplicates(inputValue,inputName)

    {

      var inputs = document.getElementsByTagName("input");

      for(var i=0;i<inputs.length;i++)

      {

        if(inputs[i].name.charAt((inputs[i].name.length)-1) == "1" && inputs[i].name != inputName)

        {

          if(inputs[i].value == inputValue)

          {

            alert("This SIM already scaned in this sheet.");
            return false;

          }

        }

        else if(inputs[i].name == inputName)

        {

          if((!checknumber(inputValue) || inputValue.length != 20))

          {

            if((!checknumber(inputValue) || inputValue.length != 24))

            {

            alert("Invalid SIM Number.");
            return false;
            }

          }

        }

      }
      return true;

    }

  </SCRIPT>   

  <body onKeyPress="if(event.keyCode==13){getNumberOfContractsScaned();data_view_RowSet_add('2','half_day','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');}">

    <CENTER>

      <H2> Sheet Contracts </H2>

    </CENTER>

  <form name='SFRform' id='SFRform' action='' method='post'>

  <%

  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

 

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String strBatchID = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);

  String errMsg = "";

 

   

 

Object o = objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);

 

  String flag = null;

  if (o != null)

        flag = (String) o ;

  String strSheetSerial = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);

  String strSheetId = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);

  String sheetSIMGroup = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP);

  String sheetStatusTypeId = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID);

 

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+

                    " value=\""+"\">");

 

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+

                  " value=\""+strUserID+"\">"); 

 

    out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID+"\""+

                    " value=\""+strBatchID+"\">");         

   

    out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_FLAG+"\""+

            " value=\""+flag+"\">");

 

    out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+"\""+

                    " value=\""+strSheetSerial+"\">");

 

    out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+"\""+

                    " value=\""+strSheetId+"\">");

                   

    out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP+"\""+

                    " value=\""+sheetSIMGroup+"\">");

 

   

    if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))

    {

      errMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);

      System.out.println("The message issssssssss " + errMsg);

      out.println("<script>alert('"+errMsg+"');</script>");

      %>

      <script>

      document.SFRform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=SFRInterfaceKey.ACTION_ADD_NEW_SHEET%>';

      SFRform.submit();

     

      </script>

    <%

    }

   

    Vector SIMList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);                   

 

    Vector sheetDetails = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

    String batchId = "";

    String posId = "";

    String posName = "";

    String posCode = "";

    String secondPosId = "";

    String secondPosCode = "";

    int sheetSIMCount = 0;

    String sheetStatusTypeIdX = "";

    String sheetStatusTypeNameX = "";

    int sheetSIMScanedTotal = 0;

    int sheetSIMScanedDublicate = 0;

    int sheetSIMScanedUnique = 0;

    if(sheetDetails.size()>0)

    {

      SheetModel sheetModel = (SheetModel)sheetDetails.get(0);

      batchId = sheetModel.getBatchId();

      posId = sheetModel.getPosId();

      posName = sheetModel.getPosName();

      posCode = sheetModel.getPosCode();

      secondPosId = sheetModel.getSecondPosId();

      secondPosCode = sheetModel.getSecondPosCode();

      sheetSIMCount = sheetModel.getSheetSIMCount();

      sheetStatusTypeIdX = sheetModel.getSheetStatusTypeId();

      sheetStatusTypeNameX = sheetModel.getSheetStatusTypeName();

      sheetSIMScanedTotal = sheetModel.getSheetSIMScanedTotal();

      sheetSIMScanedDublicate = sheetModel.getSheetSIMScanedDublicate();

      sheetSIMScanedUnique = sheetModel.getSheetSIMScanedUnique();

    }

    %>

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>

      <tr class=TableHeader>

        <td align='center'>Sheet Serial</td>

        <td align='center'>POS Code</td>

        <td align='center'>Second POS Code</td>

        <td align='center'>Sheet SIM Count</td>

      </tr>

      <tr>

        <td align='center'><%=strSheetSerial%></td>

        <td align='center'><%=posCode%></td>

        <td align='center'><%=secondPosCode%></td>

        <td align='center'><%=sheetSIMCount%></td>

      </tr>

    </table>

 

      <br><br>

 

     <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="25%" border=1>

      <tr class=TableHeader>

        <td align='center'>Number Of SIMs Scaned</td>

      </tr>

      <tr>

        <td align='center'><input type="text" name="number_of_sims_scaned" id="number_of_sims_scaned" value="<%=SIMList.size()%>" readonly style="border-width:0px;text-align:center;"></td>

      </tr>

    </table>

 

      <br><br> 

    <%

    if(sheetStatusTypeId.compareTo(SFRInterfaceKey.CONST_SHEET_STATUS_TYPE_ACCEPTED)==0){

    %>

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 border=1 align=center width='50%'>

      <TR>

        <TD class=TableHeader vAlign=top colSpan=2>Sheet Contracts

            <A onclick="getNumberOfContractsScaned();data_view_RowSet_add('2','half_day','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');">

              <IMG alt="Click Here to add New Row for Data view ... " src="../resources/img/img_sign_dn.gif" border=0>

            </A> 

        </TD>

      </TR>

 

      <tr>

        <td colspan=2><INPUT type=hidden size=15 value=0 name=user_defined_data_view_count>

          <SCRIPT>

             var user_defined_data_view=new DeepGrid("user_defined_data_view",0,"")

             user_defined_data_view.ColumnHeaders.add(null,null,"Contract SIM Number",null,250,"center",DG_TEXT,null,null,"onChange = checkForDuplicates(this.value,this.name);","style=width:200")   

             user_defined_data_view.ColumnHeaders.add(null,null,"Remove",null,60,null,DG_BOOLEAN,null,null,"onClick = getNumberOfContractsScaned();")  
             
             user_defined_data_view.ColumnHeaders.add(null,null,"",null,0,"center",DG_HIDDEN,null)  
             

             user_defined_data_view.ColumnHeaders.add(null,null,"",null,100,"center",DG_LABEL,null,null,null,null)
             
              user_defined_data_view.ColumnHeaders.add(null,null,"Info Source",null,240,"left",DG_LABEL,null,null)
             
             

          </SCRIPT>

        </td>

      </tr>

     

    </table>

    <br><br>

    <center>

    <input type="button" class="button" value=" Save " onclick="checkBeforeSubmit();">

   

   

 

    <%

    out.println("<script>");

    for(int i=0;i<SIMList.size();i++)

    {

      SIMModel simModel = (SIMModel)SIMList.get(i);

      String simId = simModel.getSimId();

      String simSerial = simModel.getSimSerial();

      String sheetId = simModel.getSheetId();

      String simStatusTypeId = simModel.getSimStatusTypeId();

      String simStatusTypeName = simModel.getSimStatusTypeName();
      
       String simInfoSource = simModel.getSimInfoSource();

      int rowIndex = i+1;

      
    
     

      out.println("eval(\"user_defined_data_view.RowSet.add("+rowIndex+");\"); ");

      out.println("eval(\"document.SFRform.user_defined_data_view__R"+rowIndex+"__C1.value = '"+simSerial+"';\"); ");

      out.println("eval(\"document.SFRform.user_defined_data_view__R"+rowIndex+"__C3.value = '"+simId+"_"+simSerial+"';\"); ");


      if(simStatusTypeId.compareTo(SFRInterfaceKey.CONST_SIM_STATUS_TYPE_REJECTED)==0)

      {

        //out.println("eval(\"document.SFRform.user_defined_data_view__R"+rowIndex+"__C4.style.color = 'FF0000';\"); ");

      }
out.println("eval(\"document.SFRform.user_defined_data_view__R"+rowIndex+"__C4.value = '"+simInfoSource+"';\"); ");
    }
    
    
      

    out.println("eval(\"document.SFRform.user_defined_data_view_count.value = "+SIMList.size()+" ;\"); ");

    out.println("</script>");

    out.println("<script>");

      out.println("data_view_RowSet_add('2','half_day','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');");

    out.println("</script>");

    }

    else

    {

    %>

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 border=1 align=center>

      <tr class=TableHeader>

        <td align='center'>Contract SIM Number</td>

      </tr>

      <%

    for(int i=0;i<SIMList.size();i++)

    {

      SIMModel simModel = (SIMModel)SIMList.get(i);

      String simId = simModel.getSimId();

      String simSerial = simModel.getSimSerial();

      String sheetId = simModel.getSheetId();

      String simStatusTypeId = simModel.getSimStatusTypeId();

      String simStatusTypeName = simModel.getSimStatusTypeName();
      
     
      %>

      <tr class=TableTextNote>

        <td align='center'><%=simSerial%></td>
        
        
      </tr> 

      <%

    }

      %>

    </table>

    <br><br>

    <center>

    <%

    }

    %>

  

   

          <input type="button" class="button" value=" Back " onclick="history.go(-1);">

          <input type="button" class="button" value=" Print " onclick="window.print();">

        </center>

      </form>  

  </body>

</html>

