 
<%@ page        import="java.util.*"
				import="java.sql.*"
                import="com.mobinil.sds.web.interfaces.*"
                import="com.mobinil.sds.core.utilities.*"
                import="com.mobinil.sds.web.interfaces.te.*"
                import="java.io.*"
                import="com.mobinil.sds.core.system.te.dao.*"
                import="com.mobinil.sds.core.system.te.dto.*"
%>

<%
  String appName = request.getContextPath();
  System.out.println("appname issssssssss "+appName);
  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
  String taskId = (String)dataHashMap.get(TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID);
  String path = (String)dataHashMap.get(TaskInterfaceKey.FILE_EXPORT);
  System.out.println("path in export issssssssss "+taskId);
  String file_path =appName+"/te/upload/export"+taskId+".csv";  
  response.addHeader("Content-Disposition", "attachment; filename=export"+taskId+".csv");
  drawResults (request, out,taskId);  
  %>
  <%! 
  private void drawResults (HttpServletRequest argRequest,JspWriter argOut,String taskId) throws IOException,SQLException
  {
	  Connection con = Utility.getConnection();
	  Vector taskataVec = TaskDAO.getTaskRows(con,taskId);
	  
	  for (int k = 0; k < taskataVec.size(); k++) 
		{
			SimDataDTO sddto = (SimDataDTO) taskataVec.elementAt(k);
			if (k == 0) {
				String temp = "Sim No, Activation Date,Dcm Name,Dcm Code,Transaction Name,Product Name, Lcs Issue Date \n";
				argOut.print(temp);
			}
			String activation = "";
			String dcmName = "";
			String dcmCode = "";
			String productName = "";
			String transacionTypeName = "";
			String issue = "";
			if (sddto.getActivation() != null) activation = sddto.getActivation();			
			if (sddto.getDcmCode() != null) dcmCode = sddto.getDcmCode();
			if (sddto.getDcmName() != null) dcmName = sddto.getDcmName();
			if (sddto.getProductName() != null) productName = sddto.getProductName();
			if (sddto.getTransactionTypeName() != null) transacionTypeName= sddto.getTransactionTypeName();
			if (sddto.getLcsIssueDate() != null) issue = sddto.getLcsIssueDate();
			String temp = sddto.getSimNo() + ","
					+ activation+ ","
					+dcmName+ ","
					+dcmCode+ ","
					+transacionTypeName+ ","
					+productName+ ","
					+issue
					+ "\n";
			argOut.print(temp);

		}
	  
	        	
	     
	     
	     
	      if (con != null) {
				Utility.closeConnection(con);
			}
	   
  }
  
  %>




