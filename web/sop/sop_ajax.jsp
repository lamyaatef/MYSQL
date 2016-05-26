
<%@ page import ="com.mobinil.sds.core.utilities.Utility"        
         import="java.util.*"         
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"
         import="com.mobinil.sds.core.system.sop.schemas.model.*"
		 import="com.mobinil.sds.core.system.sop.requests.model.*"
         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.dao.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
%>



<%
String channelId = request.getParameter(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID).toString();
String userId=(String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
String  data ="";
System.out.println("data isss "+channelId);







String strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
java.sql.Connection con = Utility.getConnection();
DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, userId,channelId,con);
Utility.closeConnection(con);
data = ":" ;
for (int index = 0 ; index<dcmDto.getDcmModelsSize();index++)
{
  DCMModel model = dcmDto.getDcm(index);       
  int dcmID = model.getDcmId();
  String dcmName = model.getDcmName();
  data +=  dcmID +","+ dcmName+":";
}



out.println(data);

   
 %>