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
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.AuthResInvalidsimSerialModel"%>
  
     <%!public void printToStream(String s, JspWriter out)throws Exception
    {
      out.println(s);    
    }%>
  

  <%

     HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	 Vector vecdata = (Vector)objDataHashMap.get(AuthResInterfaceKey.VECTOR_SEARCH_FILES_INVALID_SIM);

	 String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	 
	 String fileId = (String)objDataHashMap.get(AuthResInterfaceKey.CONTROL_HIDDEN_FILE_ID);

	
    

            
%>  
     

      
     
  <%
     BufferedWriter oout =null;
  	String file_path = request.getRealPath("/authRes/uploadtext");
	System.out.println("ddddddddddddddddddd: "+file_path);
	 //FileWriter fstream = new FileWriter("c://invalid_sims.txt");
	 file_path  = file_path + "/invalid_sims"+fileId+".txt";
	 String fileName = "invalid_sims"+fileId+".txt";
	
 
     if(vecdata.size()!=0)
     {
    	 
    	 try{
    		 FileWriter fstream = new FileWriter(file_path);
    	     oout = new BufferedWriter(fstream);
    		 }
    		 catch(IOException  ex)
    		 {
    			 
    			 ex.printStackTrace();
    
    			 
    		 }
    		 
    		 response.addHeader("Content-Disposition", "attachment; filename="+fileName);
    
        
     for (int i=0;i<vecdata.size();i++){
    	AuthResInvalidsimSerialModel model = (AuthResInvalidsimSerialModel) vecdata.get(i);
    	
    	
    	
    
    	
 

    	String SEARCH_ID =model.getSEARCH_ID();             
    	String  SIM_SERIAL =model.getSIM_SERIAL();         
    	

    	

try{

    out.print(SEARCH_ID);
    out.print(",");
    out.print(SIM_SERIAL);
    out.println("");
  
   }
   
  catch(Exception ex){
	
	ex.printStackTrace();
	
}
    
    
    
        }}
     
 
     
     
  
     %>
     

 
