<%@ page  import ="com.mobinil.sds.core.utilities.Utility"
          import = "java.util.*"
          import = "java.io.*"
          import = "javax.servlet.*" 
          import = "javax.servlet.http.*"
          import = "javax.servlet.jsp.*"
          import = "java.io.PrintWriter"
          import = "java.io.IOException"
          import = "com.mobinil.sds.web.interfaces.dcm.*"         
          import = "com.mobinil.sds.core.system.fn.addfunction.model.*"
          import = "com.mobinil.sds.core.system.fn.importdata.model.*"
          import = "com.mobinil.sds.web.interfaces.*"
          import = "com.mobinil.sds.web.interfaces.fn.*"
          import = "com.mobinil.sds.web.interfaces.gn.querybuilder.*"
          import = "com.mobinil.sds.web.interfaces.dcm.*"
          import="com.mobinil.sds.core.system.dcm.pos.dao.*"
          import="com.mobinil.sds.core.system.dcm.genericModel.*"
          import="com.mobinil.sds.core.system.dcm.genericModel.DAO.*"
          import="com.mobinil.sds.core.system.dcm.pos.model.*"
          import="com.mobinil.sds.core.system.dcm.region.model.*"
%>

<% 
String appName = request.getContextPath();
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {
    
    document.formDataView.submit();
  }
</SCRIPT>
<html>

  <head>
  
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/yav-style.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav.js"></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav-config.js"></SCRIPT>
  </head>
<SCRIPT language="javascript">

  var rules=new Array();
  rules[0]='<%=DCMInterfaceKey.CONTROL_TEXT_POS_NAME%>|required|POS Name must have value.';
  rules[1]='<%=DCMInterfaceKey.CONTROL_TEXT_POS_CODE%>|required|POS Code must have value.';
  rules[2]='<%=DCMInterfaceKey.CONTROL_TEXT_POS_COMMERCIAL_NUMBER%>|required|Commercial number must have value.';
  rules[3]='<%=DCMInterfaceKey.CONTROL_TEXT_POS_COMMERCIAL_NUMBER%>|integer|Commercial number must be number.';
  rules[4]='<%=DCMInterfaceKey.CONTROL_TEXT_POS_TAX_ID%>|required|Tax ID must have value.';
  rules[5]='<%=DCMInterfaceKey.CONTROL_TEXT_POS_TAX_ID%>|integer|Tax ID must be number.';
  rules[6]='<%=DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME%>|required|Owner Name must have value.';
  rules[7]='<%=DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER%>|required|Owner ID Number must have value.';
  rules[8]='<%=DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER%>|integer|Owner ID Number must be number.';
  rules[9]='<%=DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME%>|required|Manager Name must have value.';
  rules[10]='<%=DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER%>|required|Manager ID Number must have value.';
  rules[11]='<%=DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER%>|integer|Manager ID Number must be number.';  
  rules[12]='<%=DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE%>|notequal|*|Owner birth date can not be empty.';  
  rules[13]='<%=DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_BIRTH_DATE%>|notequal|*|Manager birth date can not be empty.';  


  
  function drawCalender(argOrder,argValue)
  {
      document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(formDataView."+argOrder+",'yyyy-mm-dd','Choose date')\">");
      document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
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
      //for (var i = 0; i < eval(argArrayDataView+".length"); i++)
      //{
      //   if (eval(argArrayDataView+"["+i+"]") == argCurrentValue)
      //   {
      //      eval(argControlName+".RowSet.getCell("+Number(ix+1)+",1).cellElement.selectedIndex ="+i+";");                               
      //   }
      //} 
    }

    function app_need_removeRows(argObject)
    {
      i = confirm("This will remove this data")
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
<body>
    <%
    showPOSRelationDetails(request, response, out);
    %>        
    <br><br>
      </form>
<%!
  public void showPOSRelationDetails(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  { 

      HashMap objDataHashMap = new HashMap();
      objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      String userID = request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);

      String superAdminFlag = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);          

      
      int UserID = Integer.parseInt(userID);

      String strFlagSuperAdmin = "0";
      if(objDataHashMap.containsKey(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG))
      strFlagSuperAdmin = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
            
      String saveAction = (String)objDataHashMap.get(DCMInterfaceKey.DCM_SAVE_POS_TYPE);
      
      String appName = request.getContextPath();
      out.println("<CENTER>");
      out.println("  <H2>POS Details</H2>");
      out.println("</CENTER>");
      String formActionSave = "";
      HashMap dataHashMap = new HashMap(100);
      Utility.logger.debug(saveAction);
      boolean editPos = false;
      if(saveAction != null && saveAction.equals(DCMInterfaceKey.DCM_SAVE_POS_TYPE_EDIT)){
       /*  formActionSave = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                          +InterfaceKey.HASHMAP_KEY_ACTION+"="
                          +DCMInterfaceKey.ACTION_DCM_SAVE_EDITED_POS;*/
         formActionSave = DCMInterfaceKey.ACTION_DCM_SAVE_EDITED_POS;
         editPos = true;
                        }
      else{                        
/*         formActionSave = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                          +InterfaceKey.HASHMAP_KEY_ACTION+"="
              +DCMInterfaceKey.ACTION_SAVE_NEW_POS; */
              if(saveAction.equals(DCMInterfaceKey.DCM_SAVE_POS_TYPE_NEW))
                formActionSave = DCMInterfaceKey.ACTION_SAVE_NEW_POS;
                else if(saveAction.equals(DCMInterfaceKey.DCM_USER_SAVE_POS_TYPE_NEW))
                formActionSave = DCMInterfaceKey.ACTION_USER_SAVE_NEW_POS;
              }
//    out.println("<form name='formDataView' action='"+formActionSave+"' method='post'>");
      out.println("<form name='formDataView' action='' method='post'>");    
      out.println("<input type='hidden' name='"+InterfaceKey.HASHMAP_KEY_ACTION+"' value='"+"'>");

      out.println("<input type='hidden' name='"+DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG+"' value='"+superAdminFlag+"'>");

      
      if(saveAction != null && saveAction.equals(DCMInterfaceKey.DCM_SAVE_POS_TYPE_EDIT)){
          int posID = Integer.parseInt((String)objDataHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_ID));
         out.println("<input type='hidden' name='"+DCMInterfaceKey.INPUT_HIDDEN_POS_ID+"' value='"+posID+"'>");
         }
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    
    Vector regions        = (Vector)dataHashMap.get(DCMInterfaceKey.VECTOR_REGIONS);
    Vector IDTypeVector   = (Vector)dataHashMap.get(DCMInterfaceKey.VECTOR_ID_TYPE);
    Vector legalFormVec   = (Vector)dataHashMap.get(DCMInterfaceKey.VECTOR_LEGAL_FORM);    
    Vector placeTypeVec   = (Vector)dataHashMap.get(DCMInterfaceKey.VECTOR_PLACE_TYPE);
    Vector POSPhones      = new Vector();
    Vector POSManagerPhones = new Vector();
    Vector POSOwnerPhones = new Vector();  
    Vector POSPartners =  new Vector();
    POSDetailModel posDetailModel = new POSDetailModel();
    posDetailModel = (POSDetailModel)dataHashMap.get(DCMInterfaceKey.POS_DETAIL_MODEL);

    int v = 0;
    GenericModel  gm, placeTypeGM,IDTypeModel;
    RegionModel regionModel;
         
          
    
    out.println("<input type='hidden' name='"+InterfaceKey.HASHMAP_KEY_USER_ID+"' value='"+UserID+"'>");

    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
    out.println("<TR>");
    out.println("      <TD class=TableTextNote width='40%'>POS Name</TD>");
    out.println("      <TD><input type='text' name='"+DCMInterfaceKey.CONTROL_TEXT_POS_NAME+"' id='"+DCMInterfaceKey.CONTROL_TEXT_POS_NAME+"'></TD>");
    out.println("</tr>");
    String posCodeReadOnly = "";
    if(editPos)posCodeReadOnly = "readonly";
    out.println("<TR>");
    out.println("      <TD class=TableTextNote width='40%'>POS Code</TD>");
    out.println("      <TD><input type='text'  "+posCodeReadOnly+" name='"+DCMInterfaceKey.CONTROL_TEXT_POS_CODE+"' id='"+DCMInterfaceKey.CONTROL_TEXT_POS_CODE+"'></TD>");
    out.println("</tr>");
    out.println("<TR>");
    out.println("      <TD class=TableTextNote>Address</TD>");
    out.println("      <TD><TEXTAREA name=\""+DCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS+"\" id=\"\" cols=50 rows=5></TEXTAREA></TD>");
    out.println("</tr>");   
    out.println("<TR>");
    out.println("      <TD class=TableTextNote>Region</TD>");
    out.println("      <TD><select name='"+DCMInterfaceKey.CONTROL_TEXT_POS_REGION+"' id='"+DCMInterfaceKey.CONTROL_TEXT_POS_REGION+"'>");
      
      for(int i = 0 ; i < regions.size() ; i++){
      regionModel = (RegionModel)regions.get(i);
         out.println("<option value='"+ regionModel.getRegionId()+"'>"+regionModel.getRegionName()+"</option>");
         }
    out.println("</select></TD>");
    out.println("</tr>");
    out.println("<TR>");
    out.println("      <TD class=TableTextNote>E-mail</TD>");
    out.println("      <TD><input type='text' name='"+DCMInterfaceKey.CONTROL_TEXT_POS_EMAIL+"' id=''></TD>");
    out.println("</tr>");
    
    out.println("<TR class=TableTextNote>");
    out.println("      <TD colspan=2>");
    
    out.println("<table border=0 align='center' width='100%'>");
    out.println("<TR>");
    out.println("  <TD class=TableTextNote vAlign=top colSpan=2>Phones");
    out.println("    <A onclick=\"data_view_RowSet_add('2','half_day','phones_count','phones','phones');\"><IMG"); 
    out.println("    alt=\"Click Here to add New Phone Number ... \" "); 
    out.println("    src=\"../resources/img/img_sign_dn.gif\" border=0></A> "); 
    out.println("  </TD>");
    out.println("</TR>");
    out.println("<tr>");
    out.println("<td width='36%'><INPUT type=hidden size=15 value=0 name=phones_count></td>");
    out.println("<td>");
    out.println("<SCRIPT>  ");
    out.println("   var phones=new DeepGrid(\"phones\",0,\"\")   ");
    out.println("   phones.ColumnHeaders.add(null,null,\"Phone Number\",null,200,\"center\",DG_TEXT,null,null,null)   ");
    out.println("   phones.ColumnHeaders.add(null,null,\"Remove\",null,60,\"center\",DG_BOOLEAN,null,null,\"onClick = app_need_removeRows('phones')\")   ");
    //out.println("   data_view_RowSet_add('2','half_day','phones_count','phones','phones'); ");
    out.println("</SCRIPT>  ");  
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");

    out.println("</td>");
    out.println("</tr>");
        String legalFormName =  "";
        String placeTypeName = "";
        String ownerIDTypeName = "";    
    out.println("<TR>");
    out.println("      <TD class=TableTextNote>Commercial No.</TD>");
    out.println("      <TD><input type='text' name='"+DCMInterfaceKey.CONTROL_TEXT_POS_COMMERCIAL_NUMBER+"' id=''></TD>");
    out.println("</tr>");
    out.println("<TR>");
    out.println("      <TD class=TableTextNote>Tax ID</TD>");
    out.println("      <TD><input type='text' name='"+DCMInterfaceKey.CONTROL_TEXT_POS_TAX_ID+"' id=''></TD>");
    out.println("</tr>");
    out.println("<TR>");
    out.println("      <TD class=TableTextNote>Legal Form</TD>");
    out.println("      <TD><select name='"+DCMInterfaceKey.CONTROL_TEXT_POS_LEGAL_FORM+"' id=''>");
        for(int i = 0 ; i < legalFormVec.size() ; i++){
      String selection = "";
      gm = (GenericModel)legalFormVec.get(i);
      if(legalFormName != null && legalFormName.equals(gm.get_field_2_value())) selection= "selected";
        out.println("<option value ='"+gm.get_primary_key_value()+"' "+selection+">"+gm.get_field_2_value()+"</option>");
      
      }
    out.println("</select></TD>");
    
    
    out.println("</tr>");

    out.println("<TR class=TableTextNote>");
    out.println("      <TD colspan=2>");
    
    out.println("<table border=0 align='center' width='100%'>");
    out.println("<TR>");
    out.println("  <TD class=TableTextNote vAlign=top colSpan=2>Partners");
    out.println("    <A onclick=\"data_view_RowSet_add('2','half_day','partners_count','partners','partners');\"><IMG"); 
    out.println("    alt=\"Click Here to add Partner ... \" "); 
    out.println("    src=\"../resources/img/img_sign_dn.gif\" border=0></A> "); 
    out.println("  </TD>");
    out.println("</TR>");
    out.println("<tr>");
    out.println("<td width='36%'><INPUT type=hidden size=15 value=0 name=partners_count></td>");
    out.println("<td>");
    out.println("<SCRIPT>  ");
    out.println("   var partners=new DeepGrid(\"partners\",0,\"\")   ");
    out.println("   partners.ColumnHeaders.add(null,null,\"Partner\",null,200,\"center\",DG_TEXT,null,null,null)   ");
    out.println("   partners.ColumnHeaders.add(null,null,\"Remove\",null,60,\"center\",DG_BOOLEAN,null,null,\"onClick = app_need_removeRows('partners')\")   ");
//    out.println("   data_view_RowSet_add('2','half_day','partners_count','partners','partners');  ");    
    out.println("</SCRIPT>  ");  
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");

    out.println("</td>");
    out.println("</tr>");

    out.println("<TR>");
    out.println("      <TD class=TableTextNote>Place</TD>");
    out.println("      <TD><select name='"+DCMInterfaceKey.CONTROL_TEXT_POS_PLACE_TYPE+"' id=''>");

    for(int i = 0 ; i < placeTypeVec.size() ; i++){
      String selection = "";
      placeTypeGM = (GenericModel)placeTypeVec.get(i);
      if(placeTypeName != null && placeTypeName.equals(placeTypeGM.get_field_2_value())) selection = "selected";

      out.println("<option value='"+placeTypeGM.get_primary_key_value()+"' "+selection+">"+placeTypeGM.get_field_2_value()+"</option>");
      }
    out.println("</select></TD>");
    out.println("</tr>");

    out.println("<TR class=TableTextNote>");
    out.println("      <TD colspan=2>");
    out.println("      <table border=0 align='center' width='100%'>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD width='40%'>Owner Name</TD>");
    out.println("      <TD><input type='text' name='"+DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME+"' id=''></TD>");
    out.println("</tr>");

    out.println("<TR class=TableTextNote>");
    out.println("      <TD colspan=2>");
    
    out.println("<table border=0 align='center' width='100%'>");
    out.println("<TR>");
    out.println("  <TD class=TableTextNote vAlign=top colSpan=2>Owner Phones");
    out.println("    <A onclick=\"data_view_RowSet_add('2','half_day','owner_phones_count','owner_phones','owner_phones');\"><IMG"); 
    out.println("    alt=\"Click Here to add Owner Phone ... \" "); 
    out.println("    src=\"../resources/img/img_sign_dn.gif\" border=0></A> "); 
    out.println("  </TD>");
    out.println("</TR>");
    out.println("<tr>");
    out.println("<td width='36%'><INPUT type=hidden size=15 value=0 name=owner_phones_count></td>");
    out.println("<td>");
    out.println("<SCRIPT>  ");
    out.println("   var owner_phones=new DeepGrid(\"owner_phones\",0,\"\")   ");
    out.println("   owner_phones.ColumnHeaders.add(null,null,\"Phone\",null,200,\"center\",DG_TEXT,null,null,null)   ");
    out.println("   owner_phones.ColumnHeaders.add(null,null,\"Remove\",null,60,\"center\",DG_BOOLEAN,null,null,\"onClick = app_need_removeRows('owner_phones')\")   ");
//    out.println("   data_view_RowSet_add('2','half_day','owner_phones_count','owner_phones','owner_phones');  ");    
    out.println("</SCRIPT>  ");  
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");

    out.println("</td>");
    out.println("</tr>");
    
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Owner Birth Date</TD>");
    out.println("      <TD><Script>drawCalender('"+DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE+"',\"*\");</script></TD>");
    out.println("</tr>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Owner I.D Type</TD>");
    out.println("      <TD><select name='"+DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE+"'>");

    for(int i = 0 ; i < IDTypeVector.size() ; i++){
      IDTypeModel = (GenericModel)IDTypeVector.get(i);
      String selection = "";
      if(ownerIDTypeName != null && ownerIDTypeName.equals(IDTypeModel.get_field_2_value())) selection = "selected";
      out.println("<option value='" + IDTypeModel.get_primary_key_value() + "' "+selection+">"+IDTypeModel.get_field_2_value() + "</option>");
    }
    out.println("</select></TD>");
    out.println("</tr>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Owner I.D Number</TD>");
    out.println("      <TD><input type='text' name='"+DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER+"' id=''></TD>");
    out.println("</tr>");

    out.println("      </table>");
    out.println("      </TD>");
    out.println("</tr>");

    out.println("<TR class=TableTextNote>");
    out.println("      <TD colspan=2>");
    out.println("      <table border=0 align='center' width='100%'>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD width='40%'>Manager Name</TD>");
    out.println("      <TD><input type='text' name='"+DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME+"' id=''></TD>");
    out.println("</tr>");

    out.println("<TR class=TableTextNote>");
    out.println("      <TD colspan=2>");
    
    out.println("<table border=0 align='center' width='100%'>");
    out.println("<TR>");
    out.println("  <TD class=TableTextNote vAlign=top colSpan=2>Manager Phones");
    out.println("    <A onclick=\"data_view_RowSet_add('2','half_day','manager_phones_count','manager_phones','manager_phones');\"><IMG"); 
    out.println("    alt=\"Click Here to add Manager Phone ... \" "); 
    out.println("    src=\"../resources/img/img_sign_dn.gif\" border=0></A> "); 
    out.println("  </TD>");
    out.println("</TR>");
    out.println("<tr>");
    out.println("<td width='36%'><INPUT type=hidden size=15 value=0 name=manager_phones_count></td>");
    out.println("<td>");
    out.println("<SCRIPT>  ");
    out.println("   var manager_phones=new DeepGrid(\"manager_phones\",0,\"\")   ");
    out.println("   manager_phones.ColumnHeaders.add(null,null,\"Phone\",null,200,\"center\",DG_TEXT,null,null,null)   ");
    out.println("   manager_phones.ColumnHeaders.add(null,null,\"Remove\",null,60,\"center\",DG_BOOLEAN,null,null,\"onClick = app_need_removeRows('manager_phones')\")   ");
//    out.println("   data_view_RowSet_add('2','half_day','manager_phones_count','manager_phones','manager_phones');  ");    
    out.println("</SCRIPT>  ");  
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");

    out.println("</td>");
    out.println("</tr>");
    
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Manager Birth Date</TD>");
    out.println("      <TD><Script>drawCalender('"+DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_BIRTH_DATE+"',\"*\");</script></TD>");
    out.println("</tr>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Manager I.D Type</TD>");
    out.println("      <TD><select name='"+DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE+"'>");
    
        for(int i = 0 ; i < IDTypeVector.size() ; i++){
        
      IDTypeModel = (GenericModel)IDTypeVector.get(i);
      
      String selection = "";
      if(posDetailModel != null &&
          posDetailModel.getPosManagerIDTypeName() != null &&
          posDetailModel.getPosManagerIDTypeName().equals(IDTypeModel.get_field_2_value())) 
              selection = "selected"; 
              out.println("<option value='" + IDTypeModel.get_field_1_value() + 
                          "' "+selection+">"+IDTypeModel.get_field_2_value() + "</option>");
//    Utility.logger.debug("XxxxXxxxXxxxX:  MANAGER ID TYPE  ID:  "+ posDetailModel.getPOSManagerIDTypeName());                          
    }

    out.println("</select></TD>");
    out.println("</tr>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Manager I.D Number</TD>");
    out.println("      <TD><input type='text' name='"+DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER+"' id=''></TD>");
    out.println("</tr>");

    out.println("      </table>");
    out.println("      </TD>");
    out.println("</tr>");
    
    out.println("</table>");
    if(posDetailModel != null){
      out.print("<script language=\"javascript\">");
      out.print("document.formDataView."+ DCMInterfaceKey.CONTROL_TEXT_POS_NAME+".value='"+posDetailModel.getPosName()+"';\n");
      out.print("document.formDataView."+ DCMInterfaceKey.CONTROL_TEXT_POS_CODE+".value='"+posDetailModel.getPOSCode()+"';\n");
      out.print("document.formDataView."+ DCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS+".value='"+posDetailModel.getPosAddress()+"';\n");
      out.print("document.formDataView."+ DCMInterfaceKey.CONTROL_TEXT_POS_EMAIL+".value='"+posDetailModel.getPosEmail()+"';\n");
      out.print("document.formDataView."+ DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME+".value='"+posDetailModel.getPosManagerName()+"';\n");
      out.print("document.formDataView."+ DCMInterfaceKey.CONTROL_TEXT_POS_COMMERCIAL_NUMBER+".value='"+posDetailModel.getPosCommercialNumber()+"';\n");
      out.print("document.formDataView."+ DCMInterfaceKey.CONTROL_TEXT_POS_TAX_ID+".value='"+posDetailModel.getPosTaxID()+"';\n");    

      out.print("document.formDataView."+ DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME+".value='"+posDetailModel.getPosOwnerName()+"';\n");
      Utility.logger.debug("The date issssssssssssss " + posDetailModel.getPosOwnerBirthDate());
      out.print("document.formDataView."+ DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE+".value='"+posDetailModel.getPosOwnerBirthDate()+"';\n"); 
      out.print("document.formDataView."+ DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_BIRTH_DATE+".value='"+posDetailModel.getPosManagerBirthDate()+"';\n");
      out.print("document.formDataView."+ DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER+".value='"+posDetailModel.getPosOwnerIDNumber()+"';\n");
      out.print("document.formDataView."+ DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER+".value='"+posDetailModel.getPosManagerIDNumber()+"';\n");
  
      out.print("</script>");
    }
    if(posDetailModel != null)
    {
        legalFormName =  posDetailModel.getPosLegalFormName();
        placeTypeName = posDetailModel.getPosPlaceTypeName();
        ownerIDTypeName = posDetailModel.getPosOwnerIDTypeName();
        POSPhones = posDetailModel.getPosPhones();
        POSManagerPhones = posDetailModel.getPosManagerPhones();
        POSOwnerPhones = posDetailModel.getPosOwnerPhones();
        POSPartners = posDetailModel.getPosPartners();

        out.println("<script>");
        for(int h= 0; h < POSPhones.size() ; h++)
        {
        v = h+1;
        String phone = (String)POSPhones.get(h);
        out.println("eval(\"phones.RowSet.add("+v+");\"); ");
        out.println("eval(\"document.formDataView.phones__R"+v+"__C1.value = '"+ phone +"';\"); ");
        
     }
     out.println("eval(\"document.formDataView.phones_count.value = "+ POSPhones.size() +" ;\"); ");
        for(int h= 0; h < POSManagerPhones.size() ; h++)
        {
        v = h+1;
        String phone = (String)POSManagerPhones.get(h);
        out.println("eval(\"manager_phones.RowSet.add("+v+");\"); ");
        out.println("eval(\"document.formDataView.manager_phones__R"+v+"__C1.value = '"+ phone +"';\"); ");
        
     }
     out.println("eval(\"document.formDataView.manager_phones_count.value = "+ POSManagerPhones.size() +" ;\"); ");
        for(int h= 0; h < POSOwnerPhones.size() ; h++)
        {
        v = h+1;
        String phone = (String)POSOwnerPhones.get(h);
        out.println("eval(\"owner_phones.RowSet.add("+v+");\"); ");
        out.println("eval(\"document.formDataView.owner_phones__R"+v+"__C1.value = '"+ phone +"';\"); ");
        
     }
     out.println("eval(\"document.formDataView.owner_phones_count.value = "+ POSOwnerPhones.size() +" ;\"); ");
        for(int h= 0; h < POSPartners.size() ; h++)
        {
        v = h+1;
        String partner = (String)POSPartners.get(h);
        out.println("eval(\"partners.RowSet.add("+v+");\"); ");
        out.println("eval(\"document.formDataView.partners__R"+v+"__C1.value = '"+ partner +"';\"); ");
        
     }
     out.println("eval(\"document.formDataView.partners_count.value = "+ POSPartners.size() +" ;\"); ");

     out.println("</script>"); 
    }
         out.println("<center>");
           out.println("<input class='button' type='button' value='Save' onclick=\""+
                        "document.formDataView."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+formActionSave+"';"+
                        "if(performCheck('formDataView', rules, 'classic')){formDataView.submit();}\">");  
           out.println("<input type=\"button\" class=\"button\" value=\" Back \" onclick=\"history.go(-1);\">");
     out.println("</center>");


}
   //out.println(formActionSave);
%>

</body>
</html>