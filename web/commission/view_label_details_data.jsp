
<%@page import="com.mobinil.mcss.commissionLabel.CommissionLabelInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.commission.model.LabelModel"%>
<%@page import="com.mobinil.sds.core.facade.handlers.AACMHandler"%>
<%@page import="com.mobinil.sds.core.system.Model"%>
<%@page import="com.mobinil.sds.core.system.commission.dao.CommissionDAO"%>
<%@page import="com.mobinil.sds.core.utilities.Utility"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.web.interfaces.commission.*"  
         import="com.mobinil.sds.web.interfaces.payment.*"
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.dcm.region.model.*"
         import="com.mobinil.sds.core.system.dcm.user.model.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import="com.mobinil.sds.core.system.gn.dataview.dto.*"         
         import="com.mobinil.sds.core.system.commission.model.*"
         import="com.mobinil.sds.core.system.payment.model.*"
         import="com.mobinil.sds.core.system.commission.factor.model.*"        
         %>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
        <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
    </head>
    <SCRIPT language="javascript">

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
                    if(eval(argObject+".getCell("+i+",6).getValue()")==true){
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
        <center>
            <%
                out.println("<input class=\"button\" type=\"button\" value=\"Save\" onclick=\"formDataView.submit();\">");
            %>
            <input type="button" class="button" value=" Back " onclick="history.go(-1);">
        </center> 
    </form>

    <%!
        public void showPOSRelationDetails(HttpServletRequest request, HttpServletResponse response, JspWriter out)
                throws ServletException, IOException {

            String appName = request.getContextPath();

            out.println("<CENTER>");
            out.println("  <H2>Labels Details</H2>");
            out.println("</CENTER>");

            HashMap dataHashMap = new HashMap(100);

            String formActionSave = appName + "/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    + InterfaceKey.HASHMAP_KEY_ACTION + "="
                    + CommissionInterfaceKey.ACTION_SAVE_LABEL_DETAILS_DATA;

            out.println("<form name='formDataView' action='" + formActionSave + "' method='post'>");

            dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

            String strUserID = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

            Vector labelDetailsVEc = (Vector) dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

            String labelId = (String) dataHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
            if (dataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE)) {
                String strErrorMsg = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
                out.println("<script>alert('" + strErrorMsg + "');</script>");
            }

            out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_USER_ID + "\""
                    + " value=\"" + strUserID + "\">");

            out.println("<input type=\"hidden\" name=\"" + CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID + "\""
                    + " value=\"" + labelId + "\">");

            out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
            out.println("<TR>");
            out.println("  <TD class=TableTextNote vAlign=top colSpan=2>Labels Details");
            out.println("    <A onclick=\"data_view_RowSet_add('2','half_day','labels__count','labels','UserDefinedDataViewArray'); \"><IMG");
            out.println("    alt=\"Click Here to add New Row for Data view ... \" ");
            out.println("    src=\"../resources/img/img_sign_dn.gif\" border=0></A> ");
            out.println("  </TD>");
            out.println("</TR>");
            out.println("<tr>");
            out.println("<td class=TableTextNote colspan=2><INPUT type=hidden size=15 value=0 name='labels__count'>");
            out.println("<SCRIPT>  ");
            out.println("   var labels=new DeepGrid(\"labels\",0,\"\")   ");


            out.println("   labels.ColumnHeaders.add(null,null,\"DCM Code\",null,150,\"center\",DG_TEXT,null,null,null)   ");
            out.println("   labels.ColumnHeaders.add(null,null,\"Amount\",null,150,\"center\",DG_TEXT,null,null,null)   ");
            LabelModel label = (LabelModel) dataHashMap.get(CommissionLabelInterfaceKey.LABEL_MODEL);


            if (label.getTwoValues()!=1) {
                out.println("   labels.ColumnHeaders.add(null,null,\"Category\",null,150,\"center\",DG_TEXT,null,null,null)   ");
            }
            else
              {
                out.println("   labels.ColumnHeaders.add(null,null,\"\",null,50,\"center\",DG_HIDDEN,null,null,null)   ");
            }
            //out.println("   labels.ColumnHeaders.add(null,null,\"Remove\",null,50,\"center\",DG_BOOLEAN,null,null,\" onclick = app_need_removeRows('labels')\")   ");    
    
                   out.println("   labels.ColumnHeaders.add(null,null,\"\",null,50,\"center\",DG_HIDDEN,\"0\",null,null)   ");
            out.println("   labels.ColumnHeaders.add(null,null,\"\",null,50,\"center\",DG_HIDDEN,\"new\",null,null)   ");
            out.println("   labels.ColumnHeaders.add(null,null,\"Remove\",null,50,\"center\",DG_BOOLEAN,null,null,\" onclick = app_need_removeRows('labels')\")   ");
            out.println("   data_view_RowSet_add('2','half_day','labels__count','labels','UserDefinedDataViewArray'); ");

            if (labelDetailsVEc.size() != 0) {

                LabelModel labelModel = (LabelModel) labelDetailsVEc.get(0);
                out.println("document.formDataView.labels__R1__C1.value='" + labelModel.getDcmCode() + "';");
                out.println("document.formDataView.labels__R1__C2.value='" + labelModel.getAmount() + "';");

                if (labelModel.getCategory() == null || labelModel.getCategory().equals("")) {
                    out.println("document.formDataView.labels__R1__C3.value='';");
                } else {
                    out.println("document.formDataView.labels__R1__C3.value='" + labelModel.getCategory() + "';");
                }
                
                
                //out.println("document.formDataView.CHK__labels__R1__C3.disabled=true"); 
                out.println("document.formDataView.labels__R1__C4.value='" + labelModel.getLabelDetailId() + "';");
                out.println("document.formDataView.labels__R1__C5.value='old';");
                out.println("document.formDataView.CHK__labels__R1__C6.disabled=true");


                //out.println("<input type=\"hidden\" name=\"labels__R1__C4\""+
                //" value=\""+"\">");

                for (int i = 1; i < labelDetailsVEc.size(); i++) {
                    out.println("   data_view_RowSet_add('2','half_day','labels__count','labels','UserDefinedDataViewArray'); ");
                    labelModel = (LabelModel) labelDetailsVEc.get(i);
                    int j = i + 1;
                    if (labelModel.getCategory() == null || labelModel.getCategory().equals("")) {
                        out.println("document.formDataView.labels__R" + j + "__C1.value='" + labelModel.getDcmCode() + "';");
                        out.println("document.formDataView.labels__R" + j + "__C2.value='" + labelModel.getAmount() + "';");
                    } else {
                        out.println("document.formDataView.labels__R" + j + "__C1.value='" + labelModel.getDcmCode() + "';");
                        out.println("document.formDataView.labels__R" + j + "__C2.value='" + labelModel.getAmount() + "';");
                        out.println("document.formDataView.labels__R" + j + "__C3.value='" + labelModel.getCategory() + "';");
                    }

                    //out.println("document.formDataView.CHK__labels__R"+j+"__C3.disabled=true;");
                    out.println("document.formDataView.labels__R" + j + "__C4.value='" + labelModel.getLabelDetailId() + "';");
                    out.println("document.formDataView.labels__R" + j + "__C5.value='old';");
                    out.println("document.formDataView.CHK__labels__R" + j + "__C6.disabled=true;");

                    //out.println("<input type=\"hidden\" name=\"labels__R"+j+"__C4\""+
                    // " value=\""+"\">");
                }
            }
            out.println("</SCRIPT>  ");
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
        }


    %>