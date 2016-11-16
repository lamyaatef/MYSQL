
<%@page import="org.primefaces.json.JSONArray"%>
<%@page import="org.primefaces.json.JSONObject"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.mobinil.sds.core.utilities.DBUtil"%>
<%@page import="com.mobinil.sds.core.system.request.dao.RequestDao"%>
<%@page contentType="text/html" pageEncoding="windows-1256"%>


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


<%
    int regionid = (request.getParameter("regionid")==null || request.getParameter("regionid").compareTo("")==0) ? -1 : Integer.parseInt(request.getParameter("regionid"));
    int managerid2 = (request.getParameter("managerid2")==null || request.getParameter("managerid2").compareTo("")==0) ? -1 : Integer.parseInt(request.getParameter("managerid2"));
    int userLevelId = (request.getParameter("userLevel")==null || request.getParameter("userLevel").compareTo("")==0) ? -1 : Integer.parseInt(request.getParameter("userLevel"));
    int supervisor=-1;
    int teamleader=-1;
    int salesrep=-1;
    String type = request.getParameter("type");
    String levelsArr = request.getParameter("arraySent");
    
    String[] levels = null; //user type levels
    if(levelsArr.contains("[") || levelsArr.contains("]"))
    {
        if (levelsArr!=null && levelsArr.compareTo("")!=0)
        {
            levelsArr = levelsArr.replace("[", "");
            levelsArr = levelsArr.replace("]", "");
            levels = levelsArr.split(",");
            for(int i=0;i<levels.length;i++)
            {
                System.out.println("LEVEL ["+i+"] : "+levels[i]);
            }
        }
    
  
   System.out.println("LEVELS : "+levelsArr);
    System.out.println("REGION ID : "+regionid);
    System.out.println("MANAGER ID2 : "+managerid2);
    System.out.println("USERLEVEL ID : "+userLevelId);
    System.out.println("TYPE OF REGION: "+type);
   
    supervisor = (levels == null || levels[0]==null || levels[0].compareTo("")==0) ? -1: Integer.parseInt(levels[0]);
    teamleader = (levels==null || levels[1]==null || levels[1].compareTo("")==0) ? -1: Integer.parseInt(levels[1]);
    salesrep = (levels == null || levels[2]==null || levels[2].compareTo("")==0) ? -1: Integer.parseInt(levels[2]);
    }
    else
    {
        int userLevel = Integer.parseInt(levelsArr);
        switch(userLevel)
        {
            case 4:
                supervisor = 4;
                break;
            case 5: 
                teamleader = 5;
                break;
            case 6:
                salesrep = 6;
                break;
        }
        
    }
    HashMap dataHashMap = new HashMap(100);
    //lamya
    boolean disabled = true;
    int returnedRegionID = -1;
    Vector <PlaceDataModel> children = new Vector();
    Connection con = Utility.getConnection();
    //lamya
    dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String strUserID = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    String alert = (String) dataHashMap.get(SCMInterfaceKey.REP_KIT_Alert);
    Vector regions = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_REGIONS);
    Vector IDTypeVector = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_ID_TYPE);
    Vector documentVec = (Vector) dataHashMap.get(SCMInterfaceKey.DOC_VECTOR);
    Vector<POSDetailModel> posDataVec = (Vector<POSDetailModel>) dataHashMap.get(SCMInterfaceKey.SIMILAR_POS_LIST);
    String region = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_REGION);
    String governrate = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_GOVER);
    String area = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_AREA);
    String city = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CITY);
    String district = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT);
    String ownerIdType = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE);
    String managerIdType = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE);
    String proposedDoc = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC);
    String distinationPage = (String) dataHashMap.get(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER);
    String totalPageNumbers = (String) dataHashMap.get(SCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER);
    Vector channelVec = (Vector) dataHashMap.get(SCMInterfaceKey.CHANNEL_VECTOR);
    Vector levelVec = (Vector) dataHashMap.get(SCMInterfaceKey.LEVEL_VECTOR);
    Vector PaymentLevelVec = (Vector) dataHashMap.get(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR);
    String posName = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_NAME);
    String level = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL);
    String channel = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL);
    String payment = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL);
    String Slach = System.getProperty("file.separator");

    if (posName == null) {
        posName = "";
    }

    String posCode = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CODE);
    if (posCode == null) {
        posCode = "";
    }
    String entryDate = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENTRY_DATE);
    entryDate = entryDate == null ? "*" : entryDate;
    String englishAddress = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENGLISH_ADDRESS);
    englishAddress = englishAddress == null ? "" : englishAddress;
    String posPhone = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_POS_PHONE);
    posPhone = posPhone == null ? "" : posPhone;
    String docLocation = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_DOC_LOCATION);
    docLocation = docLocation == null ? "" : docLocation;
    String dcmStatusId = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_SELECT_POS_STATUS);
    dcmStatusId = dcmStatusId == null ? "-1" : dcmStatusId;
    String stkStatusId = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_SELECT_STK_STATUS);
    stkStatusId = stkStatusId == null ? "-1" : stkStatusId;
    String psymentStatusId = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_SELECT_PAYMENT_STATUS);
    psymentStatusId = psymentStatusId == null ? "-1" : psymentStatusId;

    HashMap<String, String> dcmStatus = (HashMap<String, String>) dataHashMap.get(SCMInterfaceKey.HASHMAP_GEN_DCM);
    HashMap<String, String> stkStatus = (HashMap<String, String>) dataHashMap.get(SCMInterfaceKey.HASHMAP_STK_STATUS);
    HashMap<String, String> paymentStatus = (HashMap<String, String>) dataHashMap.get(SCMInterfaceKey.HASHMAP_CAM_PAY_STATUS);
    
    
    
    HashMap<String,HashMap> map = new HashMap();
    HashMap regs = drawSelectRegions(out,regionid);
    HashMap supers = drawSelectSupervisors(out,regionid,supervisor);
    HashMap leads = drawSelectSupervisors(out,regionid,teamleader);
    HashMap reps = drawSelectSupervisors(out,regionid,salesrep);
    HashMap superChildren = drawSelectSupervisorChildren(out, managerid2, regionid, userLevelId);
    map.put("districts", regs);
    map.put("users", supers);
    map.put("teams", leads);
    map.put("sales",reps);
    map.put("superChildren", superChildren);
    
  JSONObject js = new JSONObject();
  js.put("map", map);
  response.setContentType("text/x-json;charset=UTF-8");           
  response.setHeader("Cache-Control", "no-cache");
  response.setContentType("application/json");
  out.println(js);
%>

   
        
        <%!
        private HashMap drawSelectRegions(JspWriter out,int idd) throws Exception {

            
            HashMap<String,String> region = new HashMap();
        String isSelected = "selected";
        Connection con = Utility.getConnection();
        if(idd!=-1)
        {
            System.out.println("IN DRAW regions: "+idd);
            Vector <PlaceDataModel> children= RequestDao.getAllRegionDataListChild(con, idd);
            if (children != null && !children.isEmpty()) {
               //   out.println("<option value=''>--</option>");

                for (PlaceDataModel placeDataModel : children) {
                       // out.println("<option value=" + placeDataModel.getRegionId()+">"+placeDataModel.getRegionName()+"</option>");
                  region.put(Integer.toString(placeDataModel.getRegionId()), placeDataModel.getRegionName());
                }


            }


        }
        return region;

    }
        
        private HashMap drawSelectSupervisors(JspWriter out,int idd, int userLevel) throws Exception {

            
            HashMap<String,String> supervisor = new HashMap();
        String isSelected = "selected";
        Connection con = Utility.getConnection();
        if(idd!=-1 && userLevel!=-1)
        {
            System.out.println("IN DRAW supervisors: "+idd);
            Vector <UserDataModel> supervisors= RequestDao.getUserDataList(con, idd,/*4*/userLevel);
            System.out.println("SUPERVISORS/TEAMLEADERS "+supervisors);
            if (supervisors != null && !supervisors.isEmpty()) {
                //  out.println("<option value=''>--</option>");
            
                for (UserDataModel userDataModel : supervisors) {
                    System.out.println("SUPERVISOR/TEAMLEADER -- "+userDataModel.getUserId());     
                    // out.println("<option value=" + userDataModel.getUserDetailId()+">"+userDataModel.getUserFullName()+"</option>");
                    //supervisor.put(userDataModel.getUserDetailId(), userDataModel.getUserFullName());
                    supervisor.put(userDataModel.getDcmUserId(), userDataModel.getUserFullName());
                }


            }

        }
        return supervisor;

    }
        
        
        
        
        private HashMap drawSelectSupervisorChildren(JspWriter out,int managerId, int regionId,int userLevel) throws Exception {

           
            HashMap<String,String> children = new HashMap();
        String isSelected = "selected";
        Connection con = Utility.getConnection();
        if(managerId!=-1 && regionId!=-1 && userLevel!=-1)
        {
            System.out.println("IN DRAW supervisor children: "+"manager id "+managerId+" region id "+regionId+" level type required "+userLevel);
            Vector <UserDataModel> userChildren= RequestDao.getUserChildDataList(con, managerId, regionId, userLevel);
            if (userChildren != null && !userChildren.isEmpty()) {
                //  out.println("<option value=''>--</option>");

                for (UserDataModel userDataModel : userChildren) {
                       // out.println("<option value=" + userDataModel.getUserDetailId()+">"+userDataModel.getUserFullName()+"</option>");
                     //  children.put(userDataModel.getUserDetailId(), userDataModel.getUserFullName());
                      children.put(userDataModel.getUserId(), userDataModel.getUserFullName());
                }


            }

        }
      
        return children;

    }
        
        %>
   