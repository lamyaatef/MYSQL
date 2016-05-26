<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.payment.*" 
         import="com.mobinil.sds.web.interfaces.commission.*"          
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.core.system.payment.model.*"
         import="com.mobinil.sds.core.system.commission.model.*"
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
<script>

  function move_up(argobj)
    {
          var i = argobj.charAt(13);
          var k = argobj.charAt(14);
          if(k=="_")
          {
            
          }
          else
          {
              i = i+k;
          }
          if(i==1)
          {
              return;
          }
          if(document.getElementById("field__R"+i+"__C1"))
          {
          var tempfiledType = document.getElementById("field__R"+i+"__C1").value;
          var tempfiledName = document.getElementById("field__R"+i+"__C2").value;
          selectlist = eval("document.formDataViewGroupBy.field__R"+i+"__C2");
          var j = i-1;
          while(!document.getElementById("field__R"+j+"__C1"))
          {
            j=j-1;
            if(j==0)
            {
            return;
            }
          }  
          var tempfiledTypeabove = document.getElementById("field__R"+j+"__C1").value;
          var tempfiledNameabove = document.getElementById("field__R"+j+"__C2").value;
          selectlistabove = eval("document.formDataViewGroupBy.field__R"+j+"__C2");
          
          var selectlistabovearr = new Array()
          for(var m=0;m<selectlistabove.options.length;m++)
          {
              selectlistabovearr[m] = selectlistabove.options[m].value+"&"+selectlistabove.options[m].text
          }
          var selectlistarr = new Array()
          for(var s=0;s<selectlist.options.length;s++)
          {
              selectlistarr[s] = selectlist.options[s].value+"&"+selectlist.options[s].text
          }
          
          selectnameX = "field__R"+i+"__C2";
          selectnameaboveX = "field__R"+j+"__C2";
          removeAllOptions(selectnameX);
          removeAllOptions(selectnameaboveX);

          document.getElementById("field__R"+i+"__C1").value = tempfiledTypeabove;
          for(var f=0;f<selectlistabovearr.length;f++)
          {
              optionX = selectlistabovearr[f].split("&");
              optionvalueX = optionX[0];
              optiontextX = optionX[1];
              addOption(optionvalueX,optiontextX,selectnameX);
          }
          document.getElementById("field__R"+i+"__C2").value = tempfiledNameabove;
          
          document.getElementById("field__R"+j+"__C1").value = tempfiledType;
          for(var n=0;n<selectlistarr.length;n++)
          {
              optionY = selectlistarr[n].split("&");
              optionvalueY = optionY[0];
              optiontextY = optionY[1];
              addOption(optionvalueY,optiontextY,selectnameaboveX);
          }          
          document.getElementById("field__R"+j+"__C2").value = tempfiledName;
          }
          else
          {
          return;
          }
    }

    
		function removeAllOptions(selectname)
		{
		var elSel = document.getElementById(selectname);
		if (elSel.length == 0) 
			{
		  	return;
		  }
		elSel.remove(0);
		removeAllOptions(selectname)  	
		}
		      
		function addOption(optvalue,opttext,selectname)
		{
			var elSel = document.getElementById(selectname);
			var elOptNew = new Option();
		
			elOptNew.text =  opttext;
			elOptNew.value =  optvalue;
			
			elSel.options[elSel.length] = elOptNew;		
		}
    function move_down(argobj)
    {
          var i = argobj.charAt(13);
          var k = argobj.charAt(14);
          if(k=="_")
          {
              i = (i*1)+1;
          }
          else
          {
              i = i+k;
              i = (i*1)+1;
          }
          if(document.getElementById("field__R"+i+"__C1"))
          {
          var tempfiledType = document.getElementById("field__R"+i+"__C1").value;
          var tempfiledName = document.getElementById("field__R"+i+"__C2").value;
          selectlist = eval("document.formDataViewGroupBy.field__R"+i+"__C2");
          var j = i-1;
          while(!document.getElementById("field__R"+j+"__C1"))
          {
            j=j-1;
            if(j==0)
            {
            return;
            }
          }  
          var tempfiledTypeabove = document.getElementById("field__R"+j+"__C1").value;
          var tempfiledNameabove = document.getElementById("field__R"+j+"__C2").value;
          selectlistabove = eval("document.formDataViewGroupBy.field__R"+j+"__C2");
          
          var selectlistabovearr = new Array()
          for(var m=0;m<selectlistabove.options.length;m++)
          {
              selectlistabovearr[m] = selectlistabove.options[m].value+"&"+selectlistabove.options[m].text
          }
          var selectlistarr = new Array()
          for(var s=0;s<selectlist.options.length;s++)
          {
              selectlistarr[s] = selectlist.options[s].value+"&"+selectlist.options[s].text
          }
          
          selectnameX = "field__R"+i+"__C2";
          selectnameaboveX = "field__R"+j+"__C2";
          removeAllOptions(selectnameX);
          removeAllOptions(selectnameaboveX);

          document.getElementById("field__R"+i+"__C1").value = tempfiledTypeabove;
          for(var f=0;f<selectlistabovearr.length;f++)
          {
              optionX = selectlistabovearr[f].split("&");
              optionvalueX = optionX[0];
              optiontextX = optionX[1];
              addOption(optionvalueX,optiontextX,selectnameX);
          }
          document.getElementById("field__R"+i+"__C2").value = tempfiledNameabove;
          
          document.getElementById("field__R"+j+"__C1").value = tempfiledType;
          for(var n=0;n<selectlistarr.length;n++)
          {
              optionY = selectlistarr[n].split("&");
              optionvalueY = optionY[0];
              optiontextY = optionY[1];
              addOption(optionvalueY,optiontextY,selectnameaboveX);
          }          
          document.getElementById("field__R"+j+"__C2").value = tempfiledName;
          }
          else
          {
          return;
          }
    }
    
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
      ix = eval("document.formDataView."+argCounterName+".value = Number(document.formDataView."+argCounterName+".value) + 1;");
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
          if(eval(argObject+".getCell("+i+",3).getValue()")==true){
            eval(argObject+".RowSet.deleteRow("+i+");");
          }//end if
        }//end for
      }//end if
      else
      {
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",3).getValue()")==true){
            eval(argObject+".getCell("+i+",3).setValue == false; ") 
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

        eval("nSelectedIndex = document.formDataView."+strID+".selectedIndex");
        if(nSelectedIndex > 0)
        {
            var strDescrioption;
            var arrPairs=new Array();
            var strPairs=new String(eval(argVersionArrayName+"["+nSelectedIndex+"];"));
            strDescrioption = eval(argDescriptionArrayName+"["+nSelectedIndex+"];");
            arrPairs = strPairs.split(",");

           eval("document.formDataView."+strPopUpColumnID+".add(objOption);");
     eval("document.formDataView."+strPopUpColumnIDVersion+".value ="+arrPairs[0]+";");
     if(strDescrioption == "" || strDescrioption == null || strDescrioption == "null")
         strDescrioption = "N/A";
     eval("document.formDataView."+strPopUpColumnIDDescription+".value ='"+strDescrioption+"';");

      }
      else
      {
        eval("document.formDataView."+strPopUpColumnIDVersion+".value ='';");
        eval("document.formDataView."+strPopUpColumnIDDescription+".value ='';");
      }
    }
   
  </SCRIPT>

</head>
<body>
<form name="formDataView" action="" method="post">
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String paymentID =(String)objDataHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID);
  Vector commissionVector = (Vector)objDataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_COMMISSION_LIST);
  Vector paymentCommission =(Vector)objDataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_COMMISSION);
  String pageHeader = (String)objDataHashMap.get(PaymentInterfaceKey.INPUT_TEXT_PAGE_HEADER);
  %>
<%
  
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             
                                   
  out.println("<input type=\"hidden\" name=\""+PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID+"\""+
                  " value=\""+paymentID+"\">");             


%>
<center>    <H2><%=pageHeader %></H2>   </center>

   <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>     	 
   
       <tr class=TableHeader> 
    
     <td>Commission Name</td>
   
      <td>Assigned</td>
     </tr>

     
 <%
   String  checkValue="";
 String CheckedCommissionNAME="";
 
 
for (int j=0;j<commissionVector.size();j++){
	CommissionModel  commissionModel  = (CommissionModel ) commissionVector.get(j);
        	int commissionId = commissionModel.getCommissionID();
        	String commissionName = commissionModel.getCommissionName();
       	   // String Product = model2.getPRODUCT_NAME();
      
       	 for (int k=0;k<paymentCommission.size();k++){
 			checkValue="";     	
 			CommissionModel commissionModel2 = (CommissionModel) paymentCommission.get(k);
         	
 			CheckedCommissionNAME=commissionModel2.getCommissionName();
         	
         	if(CheckedCommissionNAME.equals(commissionName))
 	       {
         		
         		  
         		System.out.println("name1 "+CheckedCommissionNAME + "   name 2 = "+commissionName);
         		
	     		checkValue="checked";
	     		break;
	      	
          	}
         	else
    			checkValue="";     	
         	

       	 }
       	
       		   
       		   
    
       	  
       	 
       	    
       %>
       <tr>
        
       
        <td> <%=commissionName %></td> 
        <td><input type="checkbox" name="<%=PaymentInterfaceKey.CONTROL_HIDDEN_COMMISSION_CHECKED+ commissionId %>" <%=checkValue%> ></td>
        
  </tr>
   <%
}
     
   %>
 </TABLE>
 <br><br>
 <center> 
  <%
   out.print("<INPUT class=button type=button value=\" Submit \" name=\"Submit\" id=\"Submit\" onclick=\"document.formDataView."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+PaymentInterfaceKey.ACTION_SAVE_PAYMENT_COMMISSION+"';"+
                 "formDataView.submit();\">");
 %>
 </center> 
 

</form>
</body>
</html>
