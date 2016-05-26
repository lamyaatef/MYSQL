<%@ page contentType="Application/TXT;charset=windows-1256"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="java.io.*"
         import="javax.servlet.jsp.*"
        import="com.mobinil.sds.web.interfaces.*"
        import = " com.mobinil.sds.core.system.*"
         
        
       
       
%>

 
<%@page import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.AuthResStatisticsModel"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.AuthResSearchDataModel"%>
<%@page import="com.mobinil.sds.core.system.cam.excelImport.Sheet"%>
<%@page import="com.mobinil.sds.core.system.cr.contract.model.lcsProductMappingModel"%>
 
    
  
       <%!public void printToStream(String s, JspWriter out)throws Exception
    {
      out.println(s);    
    }%>

  <%

     HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	 Vector vecdata = (Vector)objDataHashMap.get(AuthResInterfaceKey.VECTOR_SEARCH_FILES_DATA);
	 BufferedWriter oout =null;
	  String appName = request.getContextPath();
		String file_path = request.getRealPath("/authRes/uploadtext");
		String fileName = "";
	 String strUserID     =   (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	 
	String  fileId= (String) objDataHashMap.get(AuthResInterfaceKey.CONTROL_HIDDEN_FILE_ID);

	
     
     
     if(vecdata.size()!=0)
     {
        file_path  = file_path + "\\Auth_Res_Search-Data"+fileId+".txt";
  		 fileName  = "Auth_Res_Search-Data"+fileId+".txt";
      System.out.println("ddddddddddddddddddd: "+file_path);
   		 //FileWriter fstream = new FileWriter("c://invalid_sims.txt");
   		 
   		/* try{
   			 
   		
   		 
   		 }
   	
   			 
   	catch (IOException ex)
   	{
   			 
   		ex.printStackTrace();
   		
   		
   	    printToStream("<h3>",out);
   	    printToStream("Operation Completed Not Done:",out);
   	    printToStream("</h3>",out);
   		
   		
   	}*/
   	response.addHeader("Content-Disposition", "attachment; filename="+fileName);
         
%>

  





 
     
  
    
   
         <%    
    	
        
     for (int i=0;i<vecdata.size();i++){
    	AuthResSearchDataModel model = (AuthResSearchDataModel) vecdata.get(i);
    	
    	

    	String SEARCH_ID  =model.getSEARCH_ID();             
    	String   SIM_SERIAL   =model.getSIM_SERIAL();         
    	String  DIAL              =model.getDIAL();  
    	if(DIAL==null)
    	{
           DIAL="";
    		
    	}
    	String ACTIVATION_DATE =        model.getACTIVATION_DATE();
    	if(ACTIVATION_DATE==null)
    	{
    		
    		ACTIVATION_DATE="";	
    		
    	}
    	String  POS_CODE=model.getPOS_CODE();     
    	
    	if(POS_CODE==null)
    	{
    		
    		POS_CODE ="";    		
    	}
    	String SECOND_POS_CODE =model.getSECOND_POS_CODE();
    	
    	if(SECOND_POS_CODE==null)
    	{

    		SECOND_POS_CODE="";
        }
    	String   STF_BATCH_DATE =model.getSTF_BATCH_DATE();
    	
    	if(STF_BATCH_DATE==null)
    	{
    		STF_BATCH_DATE = "" ;
         }
    	String  CIF_BATCH_DATE =model.getCIF_BATCH_DATE() ;      
    	if(CIF_BATCH_DATE==null)
    	{
    		CIF_BATCH_DATE = "";
        }
    	String SHEET_DISTRIBUTOR_CODE=model.getSHEET_DISTRIBUTOR_CODE();
    	
    	
    	if(SHEET_DISTRIBUTOR_CODE==null)
    		
    	{
	
    		SHEET_DISTRIBUTOR_CODE ="" ;
    	}
    	
    	
    	String  SHEET_POS_CODE  =model.getSHEET_POS_CODE();        
    	
    	
    	
    	if(SHEET_POS_CODE==null)
    	{
    		
    		SHEET_POS_CODE ="" ;
    	}
    	String LCS_DCM_ID =model.getLCS_DCM_ID()  ; 
    	
    	
    	if (LCS_DCM_ID==null){
    		
    		LCS_DCM_ID ="" ;
    		
    	}
    	String LCS_INIT_DATE  =model.getLCS_INIT_DATE();
    	if(LCS_INIT_DATE==null){
    		
    		LCS_INIT_DATE ="";
    		
    		
    	}
    	String LCS_CONTRACT_TYPE_ID  =model.getLCS_CONTRACT_TYPE_ID();
    	
    	if(LCS_CONTRACT_TYPE_ID==null)
     		
    	{
    		
    		LCS_CONTRACT_TYPE_ID ="" ;
    		
    		
    	}
    	String SECOND_POS_NAME  =model.getSECOND_POS_CODE();       
    	if(SECOND_POS_NAME==null){
    		
    		
    		SECOND_POS_NAME ="";
    		
    	}
    	String SHEET_SUPER_DEALER=model.getSHEET_SUPER_DEALER();    
    if(SHEET_SUPER_DEALER==null)
   {
	
   SHEET_SUPER_DEALER ="" ;

 }
   try{
    out.print(SEARCH_ID);
    out.print(",");
    out.print(SIM_SERIAL);
    out.print(",");
    out.print(DIAL);
    out.print(",");
    out.print(ACTIVATION_DATE);
    out.print(",");
    out.print(POS_CODE);
    out.print(",");
    out.print(SECOND_POS_CODE);
    out.print(",");
    out.print(STF_BATCH_DATE);
    out.print(",");
    out.print(CIF_BATCH_DATE);
    out.print(",");
    out.print(SHEET_DISTRIBUTOR_CODE);
    out.print(",");
    out.print(SHEET_POS_CODE);
    out.print(",");
    out.print(LCS_DCM_ID);
    out.print(",");
    out.print(LCS_INIT_DATE);
    out.print(",");
    out.print(LCS_CONTRACT_TYPE_ID);
    out.print(",");
    out.print(SECOND_POS_NAME);
    out.print(",");
    out.print(SHEET_SUPER_DEALER);
    out.print(",");
    out.println("");
    

   }
   
  catch(Exception ex){
	//fe record matktbsh 
	ex.printStackTrace();
	
   }
    
    
    
    
 

    
     }
         
         
       
         
        
   %>
       

     
     
<% 
  } 
   else
   
   {
	
	
    
	
}


    

%>
 
 