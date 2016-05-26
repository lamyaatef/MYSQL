

<%@ page  import ="com.mobinil.sds.core.utilities.Utility"
          import = "java.util.*"
          import = "java.io.*"
          import = "javax.servlet.*"
          import = "javax.servlet.http.*"
          import = "javax.servlet.jsp.*"
          import = "java.io.PrintWriter"
          import = "java.io.IOException"
          import = "com.mobinil.sds.web.interfaces.scm.*"
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
          import="com.mobinil.sds.core.system.request.model.*"
%>
<script>
      function drawCalender(argOrder,argValue)
  {
      document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(formDataView."+argOrder+",'dd-mm-yyyy','Choose date')\">");
      document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
  }
</script>
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

      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav.js"></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav-config.js"></SCRIPT>
  </head>

  <body>
    <%
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String alert = (String) dataHashMap.get(SCMInterfaceKey.REP_KIT_Alert);
    Vector regions        = (Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_REGIONS);
    HashMap <String,RegionModel> regionsChilds        = (HashMap <String,RegionModel>)dataHashMap.get(SCMInterfaceKey.CHILD_REGIONS_HM);
    Vector IDTypeVector   = (Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ID_TYPE);

    POSDetailModel posDetailModel = new POSDetailModel();
    posDetailModel = (POSDetailModel)dataHashMap.get(SCMInterfaceKey.POS_DETAIL_MODEL);
    //lamya
    Vector PaymentMethodVec = (Vector) dataHashMap.get(SCMInterfaceKey.PAYMENT_METHOD_VECTOR);
    //System.out.println("payment method vector size: "+PaymentMethodVec.size());
    String PaymentMethod = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD);
   // if (PaymentMethod == null) System.out.println("payment string : "+PaymentMethod);
    String level=(String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL);
    String level_for_pos = (String)dataHashMap.get("level_for_pos");
    String pos_channel= (String)dataHashMap.get("pos_channel");
    String channel=(String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL); 
    String channel_for_pos = (String)dataHashMap.get("channel_for_pos");
    //lamya

     Vector channelVec   = (Vector)dataHashMap.get(SCMInterfaceKey.CHANNEL_VECTOR);
     Vector levelVec   = (Vector)dataHashMap.get(SCMInterfaceKey.LEVEL_VECTOR);
     Vector documentVec   = (Vector)dataHashMap.get(SCMInterfaceKey.DOC_VECTOR);
     Vector rateVec   = (Vector)dataHashMap.get(SCMInterfaceKey.RATE_VECTOR);
      Vector PaymentLevelVec=(Vector)dataHashMap.get(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR);
     Vector iqrarTypeVec=(Vector)dataHashMap.get(SCMInterfaceKey.IQRAR_TYPE_VECTOR);
     Object parentIdObj =  dataHashMap.get(SCMInterfaceKey.CONTROL_REGION_PARENT_ID);
     String parentIdstr = parentIdObj==null ? "" : (String) parentIdObj;

     String disabledStrCity= "disabled=disabled";
     String cityIdVal = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CITY);
     disabledStrCity = cityIdVal!=null && cityIdVal.compareTo("0")!=0 ? "" : disabledStrCity;
     String disabledStrArea= "disabled=disabled";
     String areaIdVal = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_AREA);
     disabledStrArea = areaIdVal!=null && areaIdVal.compareTo("0")!=0 ? "" : disabledStrArea;




                    String posName = (String) dataHashMap.get("pos_name");
                    String posArabicName= (String) dataHashMap.get("pos_arabic_name");
                    String posCode=(String) dataHashMap.get("pos_code");
                    String posPayment=(String) dataHashMap.get("control_text_payment_level");
                    String posAddress=(String) dataHashMap.get("pos_address");
                    String posArabicAddress=(String) dataHashMap.get("pos_arabic_address");
                    String posRate=(String) dataHashMap.get("pos_rate");
                    String posPhone=(String) dataHashMap.get("pos_phone");
                    String posOwner=(String) dataHashMap.get("pos_owner_name");
                    String posOwnerphone=(String) dataHashMap.get("pos_owner_phone");
                    String posOwnerDate=(String) dataHashMap.get("name1");
                    String posIdType=(String) dataHashMap.get("pos_owner_id_type");
                    String posOwnerId=(String) dataHashMap.get("pos_onwer_id_number");
                    String posManager=(String) dataHashMap.get("pos_manager_name");
                    String posManagerPhone=(String) dataHashMap.get("pos_manager_phone");
                    String posManagerDate=(String) dataHashMap.get("name2");
                    String posManagerIdType=(String) dataHashMap.get("pos_manager_id_type");
                    String posManagerIdNumber=(String) dataHashMap.get("pos_manager_id_number");
                    String posDemo=(String) dataHashMap.get("pos_demo");
                    String posMail=(String) dataHashMap.get("pos_email");
                    String posDoc=(String) dataHashMap.get("pos_proposed_doc");
                    String posDocNum=(String) dataHashMap.get("pos_doc_num");
                    String posLoc=(String) dataHashMap.get("pos_doc_loc");
                    String stkDial=(String) dataHashMap.get("stk_dial");
                    String posIqrarType=(String) dataHashMap.get("control_text_pos_iqrar_type");
                    String posiqrarDate=(String) dataHashMap.get("control_text_iqrar_date");
                    String posStk=(String) dataHashMap.get("stk_verify");





      HashMap objDataHashMap = new HashMap();
      objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      String userID = request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);


      String superAdminFlag = (String)objDataHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);


      int UserID = Integer.parseInt(userID);


      out.println("<CENTER>");
      out.println("  <br><br><h2>POS Data entry with STK</h2><br><br>");
      out.println("</CENTER>");




      out.println("<form name='formDataView' action='' method='post'>");
%>
<input type="hidden" name="<%= SCMInterfaceKey.CONTROL_REGION_PARENT_ID%>" id="<%= SCMInterfaceKey.CONTROL_REGION_PARENT_ID%>" value="<%=parentIdstr %>" />
      <input type="hidden" name=<%=InterfaceKey.HASHMAP_KEY_ACTION%> >
<%
      out.println("<input type='hidden' name='"+SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG+"' value='"+superAdminFlag+"'>");


    int v = 0;
    GenericModel  gm, placeTypeGM,IDTypeModel;
    RegionModel regionModel;



      out.println("<input type='hidden' name='" + InterfaceKey.HASHMAP_KEY_USER_ID + "' value='" + UserID + "'>");

      out.println("<TABLE align=center style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"80%\" border=1>");
      out.println("<tr>");
      out.println("<td colspan=2>POS Data ");

      out.println("</td>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=1 align='center' width='100%'>");
      out.println("<TR class=TableTextNote>");


      out.println("<TD class=TableTextNote width='40%'>POS Name</TD>");
      out.println("<TD><input type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_NAME + "' value='"+posName+"'>");
      out.println("&nbsp;&nbsp;");
      out.println("<input class='button' type='button' value=Similars  onclick=similarName(); />");
      out.println("</TD>");
      out.println("</tr>");


      out.println("<TD class=TableTextNote width='40%'>POS Arabic Name</TD>");
      out.println("<TD><input type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_NAME + "' value='"+posArabicName+"'>");
      out.println("</TD>");
      out.println("</tr>");
      out.println("</tr>");


      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>POS Code</TD>");
      out.println("      <TD><input type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_CODE + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_CODE + "' value='"+posCode+"'></TD>");
      out.println("</tr>");

/*
      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");*/
      out.println("      <table border=0 align='center' width='100%'>");
     out.println("<TR class=TableTextNote>");

/*
      out.println("      <TD class=TableTextNote width='40%'>Channel</TD>");
      out.println("<TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL + "'>");
      for(int i = 0 ; i < channelVec.size() ; i++)
      {
         ChannelModel channelModel = (ChannelModel) channelVec.get(i);
         out.println("<option value="+channelModel.getChannelId()+">"+channelModel.getChannelName()+"</option>");
      }
      out.println("</select></td>");


      out.println("</tr>");


      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>Level</TD>");
      out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL + "'>");
      for(int i = 0 ; i < levelVec.size() ; i++)
      {
         LevelModel levelModel = (LevelModel) levelVec.get(i);
         out.println("<option value="+levelModel.getLevelId()+">"+levelModel.getLevelName()+"</option>");
      }
      out.println("</select></td>");
      out.println("</tr>");
*/
      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>Payment Level</TD>");
      out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL + "' id='" + SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL + "'>");
       for(int i = 0 ; i < PaymentLevelVec.size() ; i++)
      {
         PaymentModel PaymentModel = (PaymentModel) PaymentLevelVec.get(i);
          
         if(posPayment==null && PaymentModel.getPaymentId()==9){
         out.println("<option value="+PaymentModel.getPaymentId()+" selected>"+PaymentModel.getPaymentName()+"</option>");
          }
          else{
         out.println("<option value="+PaymentModel.getPaymentId()+">"+PaymentModel.getPaymentName()+"</option>");  
         }
            if((posPayment!=null &&posPayment.compareTo(PaymentModel.getPaymentId()+"")==0)){
            out.println("<option value="+PaymentModel.getPaymentId()+" selected>"+PaymentModel.getPaymentName()+"</option>");
            }
      }

      out.println("</select></td>");
      out.println("</tr>");
      //lamya
      out.println("<TR>");
        out.println("      <TD class=TableTextNote width='40%'>Payment Method</TD>");
        out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD  + "' id='" + SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD  + "'>");
        //out.println("<option>--</option>");
        for (int i = 0; i < PaymentMethodVec.size(); i++) {
            PaymentMethodModel payModel = (PaymentMethodModel) PaymentMethodVec.get(i);
             if (PaymentMethod.compareTo(payModel.getPaymentMethodId()+ "") == 0) {
                 %>
                <option value="<%=payModel.getPaymentMethodId()%>" selected><%=payModel.getPaymentMethodName()%></option>
                <%
                } 
             
             else {
                %>
                <option value="<%=payModel.getPaymentMethodId()%>" ><%=payModel.getPaymentMethodName()%></option>
                <%
                        }
                    }
                    out.println("</select></td>");
                    out.println("</tr>");
        
        
                    
        out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>Level</TD>");
      out.println("      <TD align=\"left\"><select onchange=\"onSelectChannel_Level()\" name='" + SCMInterfaceKey.LEVEL_FOR_POS  + "' id='" + SCMInterfaceKey.LEVEL_FOR_POS + "' >");
      //out.println("<option>--</option>");
      level = level==null || level.compareTo("")==0 ? level_for_pos : level ;
       for(int i = 0 ; i < levelVec.size() ; i++)
                            {
                             LevelModel levelModel=(LevelModel)levelVec.get(i);
                              
                        %>
                          <option value="<%=levelModel.getLevelId() %>" <%=((level.compareTo(levelModel.getLevelId()+"")==0)? "selected" : "") %>><%=levelModel.getLevelName()%></option>
                       <%                          
                             }
      out.println("</select></td>");
      out.println("</tr>");
            
      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>Channel</TD>");
      out.println("<TD align=\"left\"><select onchange=\"onSelectChannel_Level()\" name='" + SCMInterfaceKey.CHANNEL_FOR_POS + "' id='" + SCMInterfaceKey.CHANNEL_FOR_POS + "'>");
      //out.println("<option>--</option>");
      channel = channel==null || channel.compareTo("")==0 ? channel_for_pos : channel ;
      for(int i = 0 ; i < channelVec.size() ; i++)
                            {
                             ChannelModel channelModel=(ChannelModel)channelVec.get(i);
                        %>
                          <option value="<%=channelModel.getChannelId() %>" <%=((channel.compareTo(channelModel.getChannelId()+"")==0)?"selected":"")%> ><%=channelModel.getChannelName()%></option>
                       <% 
                             }
      out.println("</select></td>");


      out.println("</tr>");
        
        
                    
                    
                    
                    
        out.println("<TR>");
        out.println(" <td><input type=checkbox name=report_to_calidus value=yes><font size='1'>Report to Calidus</font></td>");
        out.println("</tr>");
        
        out.println("<TR>");
        out.println(" <td><input type=checkbox name=set_sign value=yes><font size='1'>Has Sign</font></td>");
        out.println("</tr>");
        out.println("</TR>");     
        
        
        out.println("<TR>");
        out.println(" <td><input type=checkbox name=pos_level_one value=yes><font size='1'>POS Level One</font></td>");
        out.println("</tr>");



        out.println("<TR>");
        out.println(" <td><input type=checkbox name=quality_club value=yes><font size='1'>Quality Control</font></td>");
        out.println("</tr>");



        out.println("<TR>");
        out.println(" <td><input type=checkbox name=exclusive value=yes><font size='1'>Exclusive</font></td>");
        out.println("</tr>");
        
        out.println("<TR>");
        out.println(" <td><input type=checkbox name=nomad value=yes><font size='1'>Nomad</font></td>");
        out.println("</tr>");
        
        
        out.println("<TR>");
        out.println(" <td><input type=checkbox name=mobicash value=yes><font size='1'>Orange Money</font></td>");
        out.println("</tr>");
        
        
      //lamya
      
      /*out.println("<TR>");
      out.println("<TD class=TableTextNote width='40%'>Branch Of</TD>");
      out.println("<TD><input type='text'  name='" + SCMInterfaceKey.CONTROL_TEXT_POS_BRANCH + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_BRANCH + "'> <font class=txt> Code of main branch without .000</font></TD>");
      out.println("</tr>");*/


      out.println("</table>");
      out.println("</TD>");
      out.println("</tr>");



      out.println("<TR class=TableTextNote>");


      out.println("     <TD class=TableTextNote width='40%'>Address</TD>");
      out.println("      <TD><TEXTAREA name=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS + "\" cols=50 rows=5 >" + posAddress + "</TEXTAREA></TD>");
      out.println("</tr>");

      out.println("     <TD class=TableTextNote width='40%'>Arabic Address</TD>");
      out.println("      <TD><TEXTAREA name=\"" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_ADDRESS + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_ADDRESS + "\" cols=50 rows=5 >" + posArabicAddress + "</TEXTAREA></TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote  width='40%'>Region</TD>");
      out.println("      <TD><select onChange=AddGovernrate(); name='" + SCMInterfaceKey.CONTROL_TEXT_POS_REGION + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_REGION + "'>");

      out.println("<option value=0>--</option>");
String selectedId = (String)dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_REGION);
       for(int i = 0 ; i < regions.size() ; i++)
      {
        PlaceDataModel placeDataModel = (PlaceDataModel) regions.get(i);
        if(placeDataModel.getTypeId() == 1)
            out.println("<option "+((selectedId!=null && selectedId.compareTo(placeDataModel.getRegionId()+"")==0) ? "selected" : "")+" value="+placeDataModel.getRegionId()+">"+placeDataModel.getRegionName()+"</option>");
      }
      out.println("</select></TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>Governrate</TD>");
      out.println("      <TD>");
        drowRegionChild(out,regionsChilds,"2",SCMInterfaceKey.CONTROL_TEXT_POS_GOVER,((String)dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_GOVER)));
            out.println("</TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>City</TD>");
      out.println("      <TD>");
        drowRegionChild(out,regionsChilds,"3",SCMInterfaceKey.CONTROL_TEXT_POS_CITY,((String)dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CITY)));
      out.println("&nbsp;&nbsp;");
      out.println("<input class='button' "+disabledStrCity+" type='button' name='similar_city' id='similar_city' value='All POS in City'  onclick=similarCity(); />");
      out.println("</TD>");
      out.println("</tr>");


      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>District</TD>");
      out.println("      <TD>");
        drowRegionChild(out,regionsChilds,"4",SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT,((String)dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT)));
            out.println("</TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>Area</TD>");
      out.println("<TD>");
              drowRegionChild(out,regionsChilds,"5",SCMInterfaceKey.CONTROL_TEXT_POS_AREA,((String)dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_AREA)));
       out.println("&nbsp;&nbsp;");
      out.println("<input class='button' "+disabledStrArea+" name='similar_area' id='similar_area' type='button' value='All POS in Area'  onclick=similarArea(); />");
      out.println("</TD>");
      out.println("</tr>");


      out.println("  <input type=hidden value=1 name='" + SCMInterfaceKey.CONTROL_TEXT_POS_RATE + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_RATE+ " value='"+posRate+"'>");

      out.println("<TR>");
      out.println("  <TD class=TableTextNote>Phone");
      out.println("  </TD>");
      out.println("  <TD class=TableTextNote><input type='text' name='"+ SCMInterfaceKey.CONTROL_TEXT_POS_PHONE +"' id='"+ SCMInterfaceKey.CONTROL_TEXT_POS_PHONE +"' value='"+posPhone+"'></TD>");
      out.println("  </TD>");
      out.println("</TR>");
      out.println("</table>");

      out.println("</td>");
      out.println("</tr>");

      /////////////////////lamya////////////////////////////////////////////
            /////////////////////////////////////////////////////////
        out.println("<TR>");
        out.println("<TD colspan=2 class=TableTextNote>Supervisor Data ");
        out.println("</TD>");
        out.println("</tr>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD colspan=2>");
        out.println("      <table border=1 align='center' width='100%'>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD width='40%'>Supervisor Name</TD>");
        out.println("      <TD><input type='text' value='' name='" + SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_NAME + "'></TD>");
       
        out.println("</tr>");



        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Supervisor Mobile Phone</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_MOBILE+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_MOBILE+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Supervisor Email</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_EMAIL+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_EMAIL+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Supervisor Address</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_ADDRESS+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_ADDRESS+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        ////added
         out.println("</tr>");

        out.println("      </table>");
        out.println("      </TD>");
        out.println("</tr>");
        ///added - end
        
        
        ///////////////////////////////////////////////////////
        
        //////////////////////////////////////////////////////////
        out.println("<TR>");
        out.println("<TD colspan=2 class=TableTextNote>TeamLeader Data ");
        out.println("</TD>");
        out.println("</tr>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD colspan=2>");
        out.println("      <table border=1 align='center' width='100%'>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD width='40%'>TeamLeader Name</TD>");
        out.println("      <TD><input type='text' value='' name='" + SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_NAME + "'></TD>");
        out.println("</tr>");



        out.println("<TR class=TableTextNote>");
        out.println("      <TD>TeamLeader Mobile Phone</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_MOBILE+"' id='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_MOBILE+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>TeamLeader Email</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_EMAIL+"' id='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_EMAIL+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>TeamLeader Address</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_ADDRESS+"' id='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_ADDRESS+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        ////added
         out.println("</tr>");

        out.println("      </table>");
        out.println("      </TD>");
        out.println("</tr>");
        ///added - end
        ///////////////////////////////////////////////////////////
        
        
        //////////////////////////////////////////////////////////////////////
        out.println("<TR>");
        out.println("<TD colspan=2 class=TableTextNote>Salesrep Data ");
        out.println("</TD>");
        out.println("</tr>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD colspan=2>");
        out.println("      <table border=1 align='center' width='100%'>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD width='40%'>Salesrep Name</TD>");
        out.println("      <TD><input type='text' value='' name='" + SCMInterfaceKey.CONTROL_TEXT_SALESREP_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_SALESREP_NAME + "'></TD>");
        out.println("</tr>");



        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Salesrep Mobile Phone</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_MOBILE+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_MOBILE+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Salesrep Email</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_EMAIL+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_EMAIL+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Salesrep Address</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_ADDRESS+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_ADDRESS+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        ////added
         out.println("</tr>");

        out.println("      </table>");
        out.println("      </TD>");
        out.println("</tr>");
        ///added - end
        
        /////////////////////////////////////////////////////////////////////////

      ///////////////////lamya - end//////////////////////////////
      
      
      String ownerIDTypeName = "";


      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=1 align='center' width='80%'>");
      out.println("<tr>");
      out.println("<td colspan=2>Owner Data ");
      out.println("</td>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD width='40%'>Owner Name</TD>");
      out.println("      <TD><input type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME + "' value='"+posOwner+"'></TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("  <TD class=TableTextNote >Owner Phone");
      out.println("  </TD>");
      out.println("  <TD class=TableTextNote ><input type='text' name='"+ SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_PHONE +"' id='"+ SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_PHONE +"' value=\"" + posOwnerphone + "\">");
      out.println("  </TD>");
      out.println("</TR>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD >Owner Birth Date</TD>");
      out.println("      <TD><Script>drawCalender('"+ SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE + "',\""+(posOwnerDate==null||posOwnerDate.compareTo("")==0?"*": posOwnerDate)+"\");</script></TD>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD>Owner ID Type</TD>");
      out.println("      <TD><select id '" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE + "' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE + "' >");

      for (int i = 0; i < IDTypeVector.size(); i++) {
          IDTypeModel = (GenericModel) IDTypeVector.get(i);
          String selection = "";
          if (ownerIDTypeName != null && ownerIDTypeName.equals(IDTypeModel.get_field_2_value()) &&ownerIDTypeName.equals(posIdType) ) {
              selection = "selected";
          }

          out.println("<option value='" + IDTypeModel.get_primary_key_value() + "' " + selection + ">" + IDTypeModel.get_field_2_value() + "</option>");
      }
      out.println("</select></TD>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD>Owner ID Number</TD>");
      out.println("      <TD><input type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER + "' maxlength=\"14\" value='"+posOwnerId+"'></TD>");
      out.println("</tr>");

      out.println("      </table>");
      out.println("      </TD>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=1 align='center' width='80%'>");
       out.println("<tr>");
      out.println("<td colspan=2>Manager Data ");
      out.println("</td>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD width='40%'>Manager Name</TD>");
      out.println("      <TD><input type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME + "' value='"+posManager+"'></TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("  <TD class=TableTextNote >Manager Phone");
      out.println("  </TD>");
        out.println("  <TD class=TableTextNote><input type='text' name='"+ SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE +"' id='"+ SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE +"' value='"+posManagerPhone+"' >");
      out.println("  </TD>");
      out.println("</TR>");


      out.println("<TR class=TableTextNote>");
      out.println("      <TD>Manager Birth Date</TD>");
      out.println("      <TD><Script>drawCalender('" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_BIRTH_DATE + "',\""+(posManagerDate==null||posManagerDate.compareTo("")==0?"*": posManagerDate)+"\");</script></TD>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD>Manager ID Type</TD>");
      out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE + "' value='"+posManagerIdType+"'>");
      out.println("<option></option>");
      for (int i = 0; i < IDTypeVector.size(); i++) {

          IDTypeModel = (GenericModel) IDTypeVector.get(i);
          String selection = "";
          if (ownerIDTypeName != null && ownerIDTypeName.equals(IDTypeModel.get_field_2_value())) {
              selection = "selected";
          }
          if (posManagerIdType!=null && posManagerIdType.compareTo("")!=0&& posManagerIdType.compareTo(IDTypeModel.get_primary_key_value())==0){
              selection = "selected";
          }
          out.println("<option value='" + IDTypeModel.get_primary_key_value() + "' " + selection + ">" + IDTypeModel.get_field_2_value() + "</option>");
      }

      out.println("</select></TD>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD>Manager ID Number</TD>");
      out.println("      <TD><input type='text' onkeyup=\"onKeyPressManagerIDType()\" name='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER + "' maxlength=\"14\" value='"+posManagerIdNumber+"'></TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote>Demo Line Number</TD>");
      out.println("      <TD><input type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_DEMO + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_DEMO + "' value='"+posDemo+"'></TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote>E-mail</TD>");
      out.println("      <TD><input type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL + "' value='"+posMail+"'></TD>");
      out.println("</tr>");

      out.println("      </table>");
      out.println("      </TD>");
      out.println("</tr>");


      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=1 align='center' width='80%'>");
       out.println("<tr>");
      out.println("<td colspan=2>Documents ");
      out.println("</td>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");


      out.println("      <TD class=TableTextNote width='40%'>Proposed Documents");
      out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC + "' value='"+posDoc+"' onchange=\"doc(this)\">");
      out.println("<option></option>");
      for(int i = 0 ; i < documentVec.size() ; i++)
      {
         ProposedDocument docModel = (ProposedDocument) documentVec.get(i);
         out.println("<option value="+docModel.getDocId()+" "+(posDoc.compareTo(docModel.getDocId()+"")==0? "selected":"" )+">"+docModel.getDocName()+"</option>");
      }
      out.println("</select></td>");
      out.println("</tr>");


      out.println("<TR>");
      out.println("      <TD class=TableTextNote>Document Number</TD>");
      out.println("      <TD><input type='text' name=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM + "\" value='"+posDocNum+"' cols=50 rows=5></TEXTAREA></TD>");
      out.println("</tr>");
      out.println("<TR>");
      out.println("      <TD class=TableTextNote>Document Location</TD>");
      out.println("      <TD><input type='text' name=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_DOC_LOC + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_DOC_LOC + "\" value='"+posLoc+"' cols=50 rows=5></TEXTAREA></TD>");
      out.println("</tr>");
%>
      <script>
      document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM%>.disabled=true;
      </script>
<%

      out.println("</table>");
      out.println("</TD>");
      out.println("</tr>");


      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=1 align='center' width='80%'>");
      out.println("<tr>");
      out.println("<td>STK Data ");
      out.println("</td>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width='40%'>STK Dial Number</TD>");
      out.println("<TD><input type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_STK_DIAL + "' id='" + SCMInterfaceKey.CONTROL_TEXT_STK_DIAL + "' value='"+stkDial+"' ></TD>");
      out.println("</tr>");
      out.println("</tr>");
      out.println("</tr>");
      out.println("</table>");


      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=1 align='center' width='80%'>");
       out.println("<tr>");
      out.println("<td colspan=2>Iqrar Data");

      out.println("</td>");
      out.println("</tr>");
      out.println("<TD class=TableTextNote width='40%'>Iqrar Reciveing Type");
      out.println("<TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_POS_IQRAR_TYPE + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_IQRAR_TYPE + "' onchange=\"iqrar(this);\">");
      for(int i = 0 ; i < iqrarTypeVec.size() ; i++)
      {
               IqrarTypeModel iqrarModel = (IqrarTypeModel) iqrarTypeVec.get(i);
         out.println("<option value="+iqrarModel.getTypeId()+" "+(posIqrarType.compareTo(iqrarModel.getTypeId()+"")==0?"selected":"")+">"+iqrarModel.getName()+"</option>");
      }
      out.println("</select></td>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width='40%'>Iqrar Receiving Date</TD>");
      out.println("<TD><Script>drawCalender('"+SCMInterfaceKey.CONTROL_TEXT_IQRAR_DATE+"',\""+(posiqrarDate==null||posiqrarDate.compareTo("")==0?"*": posiqrarDate)+"\");</script></TD>");
      out.println("</tr>");
      out.println("</table>");

%>
      <script>
      document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_IQRAR_DATE%>.disabled=true;
      </script>
<%


      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("<table border=1 align='center' width='80%'>");
      out.println("<tr>");
      out.println("<td colspan=2>General Data ");
      out.println("</td>");
      out.println("</tr>");
       out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width='40%'>Survey Receiving Date</TD>");
      out.println("<TD><Script>drawCalender('" +SCMInterfaceKey.CONTROL_TEXT_STK_VERIFICATION+  "',\""+(posStk==null||posStk.compareTo("")==0?"*": posStk)+"\");</script></TD>");
      out.println("</tr>");
      out.println("</tr>");
      out.println("</tr>");
      out.println("</table>");


      out.println("</table>");
      out.println("</TD>");
      out.println("</tr>");

      out.println("</table>");

     out.println("<center>");
     out.println("<input class='button' type='button' value='Save' onclick=saveForm(); \">");
     out.println("<input type=\"button\" class=\"button\" value=\" Back \" onclick=\"history.go(-1);\">");
     out.println("</center>");

      out.println("</form>");

    %>
    <br><br>
    </body>
<SCRIPT language="javascript">
    
// Declaring required variables
var digits = "0123456789";
// non-digit characters which are allowed in phone numbers
var phoneNumberDelimiters = "()- ";
// characters which are allowed in international phone numbers
// (a leading + is OK)
var validWorldPhoneChars = phoneNumberDelimiters + "+";
// Minimum no of digits in an international phone no.
var minDigitsInIPhoneNumber = 10;
function IsPhoneInteger(s)
{   var i;
    for (i = 0; i < s.length; i++)
    {   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}
function trimPhone(s)
{   var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not a whitespace, append to returnString.
    for (i = 0; i < s.length; i++)
    {   
        // Check that current character isn't whitespace.
        var c = s.charAt(i);
        if (c != " ") returnString += c;
    }
    return returnString;
}
function stripCharsInBag(s, bag)
{   var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++)
    {   
        // Check that current character isn't whitespace.
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function checkInternationalPhone(strPhone){
var bracket=3
strPhone=trimPhone(strPhone)
if(strPhone.indexOf("+")!=-1) return false
if(strPhone.indexOf("-")!=-1)return false
if(strPhone.indexOf("(")!=-1)return false
if(strPhone.indexOf(")")!=-1)return false
s=stripCharsInBag(strPhone,validWorldPhoneChars);
return (IsPhoneInteger(s));
}

function ValidateForm(){
	var Phone=document.frmSample.txtPhone
	
	if ((Phone.value==null)||(Phone.value=="")){
		alert("Please Enter your Phone Number")
		Phone.focus()
		return false
	}
	if (checkInternationalPhone(Phone.value)==false){
		alert("Please Enter a Valid Phone Number")
		Phone.value=""
		Phone.focus()
		return false
	}
	return true
 }
function similarName()
{
  if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_NAME %>.value") == "")
  {
         alert("Please Enter POS Name ..");
 }
else
    {
 document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_POS_DATA_SIMILAR_NAME%>';
 formDataView.submit();
}
}

function similarCity()
{
    if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY %>.value") == "0")
  {
         alert("Please Enter City ..");
 }
else
    {
   document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_POS_DATA_SIMILAR_CITY %>';
   formDataView.submit();
}
}


function similarArea()
{
  if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA %>.value") == "0")
  {
    alert("Please Enter Area ..");
  }
  else
    {
      document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_POS_DATA_SIMILAR_AREA %>';
      formDataView.submit();
    }

}


function saveForm()
{
    var flag = 0;
   // var posCode = document.getElementById('<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE %>').value;
   // alert(posCode);
   // var indicator = posCode.indexof('.001');
var docLoc = trimAll(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_LOC%>.value);
document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_LOC%>.value = docLoc;
if( docLoc == "")
    {
        flag = 1;
         alert("Please Enter Document Location ..");
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_NAME%>.value") == "")
    {
        flag = 1;
         alert("Please Enter POS Name ..");
    }
     else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL %>.value") == "")
    {
        flag=1;
        alert("You must Enter STK Dial Number ...");
            return;
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL %>.value") != "")
    {

        if(checkInternationalPhone(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL %>.value"))==false)
       {
        flag = 1;
        alert("STK Dial Number Accepts Numbers Only ...");
       }

    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE%>.value") == "")
    {
        flag = 1;
         alert("Please Enter POS code ..");
    }
   else if(isNaN(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE %>.value")))
    {
        flag = 1;
       alert("POS Code Accepts Numbers Only ...");
    }
   // else if(indicator== -1)
    //{
    // flag = 1;
    // alert("POS Code Must Contais .001  ...");
   // }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS %>.value") == "")
    {
         flag = 1;
         alert("Please Enter Address ..");
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION %>.value") == "0")
    {
         flag = 1;
         alert("Please Enter Region ..");
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER %>.value") == "0")
    {
         flag = 1;
         alert("Please Enter Governrate ..");
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY %>.value") == "0")
    {
         flag = 1;
         alert("Please Enter City ..");
    }

   if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.value") == "0")
    {
         flag = 1;
         alert("Please Enter District ..");
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA %>.value") == "0")
    {
         flag = 1;
         alert("Please Enter Area ..");
    }

    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME %>.value") == "")
    {
         flag = 1;
         alert("Please Enter Owner Name ..");
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER %>.value") == "")
    {
         flag = 1;
         alert("Please Enter Owner ID Number ..");
    }
    else if(isNaN(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER %>.value")))
    {
       flag = 1;
       alert("Owner ID Number Accepts Numbers Only ...");
    }

    else if(isNaN(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER %>.value")))
    {
       flag = 1;
       alert("Manager ID Number Accepts Numbers Only ...");
    }
    else if(checkInternationalPhone(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE %>.value"))==false)
       {
        flag = 1;
        alert("Manager Phone Number Accepts Numbers Only ...");
       }
   

     else if(checkInternationalPhone(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DEMO %>.value"))==false)
    {
       flag = 1;
       alert("Demo Line Accepts Numbers Only ...");
    }

    else if(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_PHONE %>.value==""){
         alert("Please enter valid POS phone.");
         return;
    }
      else if(checkInternationalPhone(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_PHONE %>.value)==false){
         alert("Please POS Phone Must Be Numric.");
         return;
    }
    

    else if(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_PHONE %>.value==""){
          alert("Please enter valid POS Owner phone.");
          return;
    }
    else if(checkInternationalPhone(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_PHONE %>.value)==false){
         alert("Please Owner Phone Must Be Numric.");
         return;
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_IQRAR_TYPE %>.value") == "2")
    {
        if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_IQRAR_DATE%>.value") == "*" )
        {
            flag=1;
        alert("You must Enter Iqrar Date ...");
        }
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL %>.value") != "")
        {
                var str = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL %>.value;
            	var at="@"
		var dot="."
		var lat=str.indexOf(at)
		var lstr=str.length
		var ldot=str.indexOf(dot)
		if (str.indexOf(at)==-1)
                {
		   flag = 1;
                   alert("Invalid E-mail Formate ..")

		}

		else if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr)
                {
		   flag = 1;
                   alert("Invalid E-mail Formate ..")

		}

		else if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr)
                {
		    flag = 1;
                    alert("Invalid E-mail Formate ..")

		}

		else if (str.indexOf(at,(lat+1))!=-1)
                {
		    flag = 1;
                    alert("Invalid E-mail Formate ..")

		 }

		else if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot)
                {
		    flag = 1;

                    alert("Invalid E-mail Formate ..")

		 }

		else if (str.indexOf(dot,(lat+2))==-1)
                {
		    flag = 1;
                    alert("Invalid E-mail Formate ..")

		 }

		else if (str.indexOf(" ")!=-1)
                {
		    flag = 1;
                    alert("Invalid E-mail Formate ..")

		 }
        }


     else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE %>.value") == "*")
       {
         flag = 1;
         alert("Please Enter Owner Birth date ..");
       }

        else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME%>.value") != "")
       {
         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE %>.value") == "")
       {
         flag = 1;
         alert("Please Enter the Manager Phone  ..");
       }
       }
        else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE%>.value") != "")
       {
            if(checkInternationalPhone(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE %>.value"))==false)
       {
        flag = 1;
        alert("Manager Phone Number Accepts Numbers Only ...");
       }

         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME %>.value") == "")
       {
         flag = 1;
         alert("Please Enter the Manager Name  ..");
       }
       }

        else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL %>.value") != "")
    {
        if(checkInternationalPhone(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL %>.value"))==false)
       {
        flag = 1;
        alert("STK Dial Number Accepts Numbers Only ...");
       }
         
    }



   if(flag != 1)
        {
        if(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_VERIFICATION%>.value=="*")
             {
        alert("You must Enter Survey Date ...");
                    return;
             }
             
       if (lengthRestriction(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE%>,9,10 )==false)
            {

            alert("Please enter POS Code between " +9+ " and " +10+ " characters");
            return;
            }

        if (lengthRestriction(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>,10,11 )==false)
            {

            alert("Please enter STK Dial Number between " +10+ " and " +11+ " characters");
            return;
            }
             else{
                 
             document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_PHONE%>.value=trimPhone(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_PHONE%>.value);
             document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.value=trimPhone(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.value);
             document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE%>.value=trimPhone(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE%>.value);
             document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_PHONE%>.value=trimPhone(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_PHONE%>.value);

          document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_SAVE_POS_DATA_ENTRY_WITH_STK %>';
          formDataView.submit();
        }
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




 function AddGovernrate()
                {
                    var regionId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>.value;
                    buildPatternOfRegions();
                    if (regionId!=0){
                        getRegionChild();
                    }
                }

                function AddCity()
                {
                    var regionId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>.value;
                   buildPatternOfRegions();
                    if (regionId!=0){
                        getRegionChild();
                    }
                }
                function AddDistrict()
                {
                    var regionId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.value;
                    buildPatternOfRegions();
                    if (regionId!=0){
                        getRegionChild();
                    }
                }
                function AddArea()
                {
                    var regionId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT %>.value;
                    buildPatternOfRegions();
                    if (regionId!=0){
                        getRegionChild();
                    }
                }
                function onChangeArea()
                {
                    var regionId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA %>.value;
                    buildPatternOfRegions();
                    if (regionId!=0){
                        document.formDataView.similar_area.disabled=false;
                    }
                    else
                        {

                            document.formDataView.similar_area.disabled=true;
                        }
                }
          function getRegionChild()
          {
              document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_GET_REGION_CHILD_WITH_STK %>';
                formDataView.submit();
          }
          function buildPatternOfRegions(){
var disCt = "0";
          var disArea = "0";
    document.formDataView.<%=SCMInterfaceKey.CONTROL_REGION_PARENT_ID%>.value='';
    var regId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>.value;
    var govId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>.value;
    var cityId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.value;
    var distId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.value;
    var areaId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.value;

    if (regId==0){
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>.selectedIndex=0;
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.selectedIndex=0;
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.selectedIndex=0;
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.selectedIndex=0;
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>');
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>');
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>');
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>');
         disCt=1;
        disArea=1;
    }
    if (govId==0){
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.selectedIndex=0;
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.selectedIndex=0;
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.selectedIndex=0;

        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>');
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>');
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>');
         disCt=1;
        disArea=1;
    }
    if (cityId==0){
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.selectedIndex=0;
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.selectedIndex=0;

        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>');
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>');
         disCt=1;
        disArea=1;
    }
    if (distId==0){
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.selectedIndex=0;
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>');

        disArea=1;
    }

    if (disArea==1)
        {
        document.formDataView.similar_area.disabled=true;
        }
    if (disCt==1)
        {
        document.formDataView.similar_city.disabled=true;
        }

    document.formDataView.<%=SCMInterfaceKey.CONTROL_REGION_PARENT_ID%>.value = regId+'-'+govId+'-'+cityId+'-'+distId+'-'+areaId;
    //alert(regId+'-'+govId+'-'+cityId+'-'+distId+'-'+areaId)

}
function removeAllOptions(selectName){
    document.getElementById(selectName).length = 0;
    document.getElementById(selectName).options[0] = new Option(' -- ', '0');

}

function iqrar(selectBox)
{
    var selectedIndex=selectBox.selectedIndex;
    var selectedValue=selectBox.options[selectedIndex].value;
    if(selectedValue=="2")
        {
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_IQRAR_DATE %>.disabled=false;
        }
        else{

            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_IQRAR_DATE %>.disabled=true;
        }
}
function doc(selectBox)
{
    var selectedIndex=selectBox.selectedIndex;
    var selectedValue=selectBox.options[selectedIndex].value;
    if(selectedValue=="")
        {
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM %>.disabled=true;
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM %>.value="";
    }
        else{
             if(selectedValue=="4")
                 {
                      document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM %>.disabled=false;
                     document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM %>.value="memo";

                 }else{
                      document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM %>.disabled=false;
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM %>.value="";

            }
        }
}
function lengthRestriction(elem,min,max){

        var uInput = elem.value;
	if(uInput.length >= min && uInput.length <= max ||uInput.length ==0){
		return true;
	}else{
		
            elem.focus();
		return false;
	}
}
function trimAll(sString)
        {
            while (sString.substring(0,1) == ' ')
            {
                sString = sString.substring(1, sString.length);
            }
            while (sString.substring(sString.length-1, sString.length) == ' ')
            {
                sString = sString.substring(0,sString.length-1);
            }
            return sString;
        }
function onKeyPressManagerIDType(){
    var indx = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE%>.selectedIndex;
    //alert(indx);
    if (indx==0)
        {
            alert ('Please select Manager ID Type.');
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER%>.value='';
        }
}
  </SCRIPT>


</html>
<%!
private void drowRegionChild(JspWriter out, HashMap<String, RegionModel> regionsChilds, String regionType, String ControlName, String selectedId) throws IOException {
    //System.out.println("regionType iss "+regionType);

String functionName = regionType.compareTo("2") == 0 ? "onChange=AddCity();" : regionType.compareTo("3") == 0 ? "onChange=AddDistrict();" :
    regionType.compareTo("4") == 0 ? "onChange=AddArea();" :
        regionType.compareTo("5") == 0 ? "onChange=onChangeArea();" : "";   


        out.println("<select " + functionName + "  name='" + ControlName + "' id='" + ControlName + "'>");
        out.println("<option value=0>--</option>");
        if(regionsChilds!=null && !regionsChilds.isEmpty()){
        for (String regId : regionsChilds.keySet()) {

            String regName = regionsChilds.get(regId).getRegionName();
            String levl = regionsChilds.get(regId).getRegionLevelTypeId();
            //String selected = "selected";
            if (regionType.compareTo(levl) == 0) {
                out.println("<option ");
                if (selectedId != null && regId.compareTo(selectedId)==0){
                out.print("selected");
                }
                out.print(" value=" + regId + ">" + regName + "</option>");
            }
        }}
        out.println("</select>");


}%>