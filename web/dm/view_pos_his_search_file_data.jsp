<%@ page contentType="text/html;charset=windows-1252"%>
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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<%@page import="com.mobinil.sds.web.interfaces.dm.DMInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.AuthResStatisticsModel"%>
<%@page import="com.mobinil.sds.core.system.dataMigration.model.* "%>
<%@page import="com.mobinil.sds.core.system.cam.excelImport.Sheet"%>

<%@page import="com.mobinil.sds.web.interfaces.*"%>
<%@page import="com.mobinil.sds.core.system.cr.contract.model.lcsProductMappingModel"%><html>


<head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search File Data</title>
</head>
 <body>

    <CENTER>
      <H2>Search File Data</H2>
    </CENTER>
  <form  name='Authform' id='Authform' action='' method='post'>  
  
       <%!public void printToStream(String s, JspWriter out)throws Exception
    {
      out.println(s);    
    }%>

  <%

     HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	 Vector vecdata = (Vector)objDataHashMap.get(DMInterfaceKey.VECTOR_SEARCH_FILES_DATA);
	 
	 if(vecdata==null || vecdata.size()==0)
	 {
		 
		 
		    printToStream("<h3>",out);
		    printToStream("No valid codes in search file :",out);
		    printToStream("</h3>",out);	 
		 
		 
	 }

	 String strUserID     =   (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	 
	String  fileId= (String) objDataHashMap.get(DMInterfaceKey.CONTROL_HIDDEN_FILE_ID);

	
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                " value=\""+"\">");   

     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                " value=\""+strUserID+"\">"); 
         
%>  
     

     <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
     
  <%
  BufferedWriter oout =null;
  
	String file_path = request.getRealPath("/dm/uploadtext");

		 
	

  if(vecdata.size()!=0)
  {
   System.out.println("ddddddddddddddddddd : "+file_path);
		 //FileWriter fstream = new FileWriter("c://invalid_sims.txt");
		 
		 try{
		 file_path  = file_path + "/PAY_HIS-Data "+fileId+".txt";
		
		 FileWriter fstream = new FileWriter(file_path);
	     oout = new BufferedWriter(fstream);
		 }
	
			 
	catch (IOException ex)
	{
			 
		ex.printStackTrace();
		
		
	    printToStream("<h3>",out);
	    printToStream("Operation Completed Not Done:",out);
	    printToStream("</h3>",out);
		
		
	}
 

    %>
    
   <%--   
    
      <tr class=TableHeader>
      <td>Search File ID</td>
      <td>SIM Serial</td>
      <td>Dial</td>
      <td>Activation Date</td>
      <td>POS CODE</td>
      <td>Second POS CODE</td>
      <td>STF BATCH Date</td>
      <td>CIF Batch Date </td>
      <td>Sheet Distributor Code</td>
      <td>Sheet POS CODE</td>
       <td>LCS DCM ID </td>
      <td>LCS Init Date</td>
       <td> LCS_Contract_Typoe_ID </td>
      <td>Second POS Name</td>
      <td>Sheet Super Dealer</td>
      

     
        
     </tr> 
     
    --%>
        
         <%    

       
    for (int i=0;i<vecdata.size();i++){
    	 PosHisSearchfileDataModel model = (PosHisSearchfileDataModel) vecdata.get(i);
  
        String SEARCH_ID  =model.getSEARCH_ID();             
    	String  CODE   =model.getCODE();         
        String  PHASE_DATE  =model.getPHASE_DATE();  
    	if(PHASE_DATE==null)
    	{
    		PHASE_DATE="";
    		
    	}
    	String MEMO_NAME =  model.getMEMO_NAME();
    	if(MEMO_NAME==null)
    	{
    		
    		MEMO_NAME="";	
    		
    	}
    	String   TOTAL_AMOUNT=model.getTOTAL_AMOUNT();     
    	
    	if(TOTAL_AMOUNT==null)
    	{
    		
    		TOTAL_AMOUNT ="";    		
    	}
    	String AMOUNT =model.getAMOUNT();
    	
    	
    	
    	if(AMOUNT==null)
    	{

    		AMOUNT="";
        }
    	String   DONE =model.getDONE();
    	
    	if(DONE==null)
    	{
    		DONE= "" ;
         }
    	
    	
    	
 	String   MSG =model.getMSG();
    	
    	if(MSG==null)
    	{
    		MSG= "" ;
         }
    	
	String   SALES_TAX =model.getSALES_TAX();
        	
        	if(SALES_TAX==null)
        	{
        		SALES_TAX= "" ;
   	
            }

        String   REG_DATE =model.getREG_DATE();
        	
        	if(REG_DATE==null)
        	{
        		REG_DATE= "" ;
             }
        	
        	
  	String   COMMENT1 =model.getCOMMENT1();
        	
        	if(COMMENT1==null)
        	{
        		COMMENT1= "" ;
             }  	
        	
        	
  	String   COMMENT2 =model.getCOMMENT2();
        	
        	if(COMMENT2==null)
        	{
        		COMMENT2= "" ;
             }     	
        	
 
        	
  	String   MEMEO_DESC =model.getMEMEO_DESC();
        	
        	if(MEMEO_DESC==null)
        	{
        		MEMEO_DESC= "" ;
             }  
        
        	
        	
        	
          	String   DEDUCTION_FEES =model.getDEDUCTION_FEES();
                	
                	if(DEDUCTION_FEES==null)
                	{
                		DEDUCTION_FEES= "" ;
                     }
        	
                	
                	
     	String   REMAINED_DEDUCTION =model.getREMAINED_DEDUCTION();
                	
                	if(REMAINED_DEDUCTION==null)
                	{
                		REMAINED_DEDUCTION= "" ;
                     }
      	
                	
	String   FINANCE_DATE =model.getFINANCE_DATE();
                	
                	if(FINANCE_DATE==null)
                	{
                		FINANCE_DATE= "" ;
                     }
    
                	
	String   BUDGET_ID =model.getBUDGET_ID();
                	
                	if(BUDGET_ID==null)
                	{
                		BUDGET_ID= "" ;
          
                	
                	}
                	
   String   BUDGET_NAME =model.getBUDGET_NAME();
                	
                	if(BUDGET_NAME==null)
                	{
                		BUDGET_NAME= "" ;
                     }

        		
        	
        	
        	
   try{
    oout.write(SEARCH_ID);
    oout.write(",");
    oout.write(CODE);
    oout.write(",");
    oout.write(PHASE_DATE);
    oout.write(",");
    oout.write(MEMO_NAME);
    oout.write(",");
    oout.write(TOTAL_AMOUNT);
    oout.write(",");
    oout.write(AMOUNT);
    oout.write(",");
    oout.write(MSG);
    oout.write(",");
    oout.write(SALES_TAX);
    oout.write(",");
    oout.write(REG_DATE);
    oout.write(",");
    oout.write(COMMENT1);
    oout.write(",");
    oout.write(COMMENT2);
    oout.write(",");
    oout.write(MEMEO_DESC);
    oout.write(",");
    oout.write(DEDUCTION_FEES);
    oout.write(",");
    oout.write(REMAINED_DEDUCTION);
    oout.write(",");
    oout.write(FINANCE_DATE);
    oout.write(",");
    oout.write(BUDGET_ID);
    oout.write(",");
    oout.write(BUDGET_NAME);
    oout.write("\n");
    oout.flush();
   }
   
  catch(Exception ex){
	//fe record matktbsh 
	ex.printStackTrace();
	
   }
    
    
    
%>

<%--  
            <td> <%=SEARCH_ID %></td>  
             <td> <%=SIM_SERIAL %></td>  
            <td > <%=DIAL %></td>  
            <td> <%=ACTIVATION_DATE %></td>
            <td> <%=POS_CODE %></td>  
            <td> <%=SECOND_POS_CODE %></td>
            <td> <%=STF_BATCH_DATE %></td>  
             <td> <%=CIF_BATCH_DATE %></td>
            <td > <%=SHEET_DISTRIBUTOR_CODE %></td>  
            <td > <%=SHEET_POS_CODE%></td>  
            <td> <%=LCS_DCM_ID %></td>
            <td > <%=LCS_INIT_DATE %></td>  
            <td> <%=LCS_CONTRACT_TYPE_ID %></td>
            <td> <%=SECOND_POS_NAME %></td>  
            <td> <%=SHEET_SUPER_DEALER %></td>
 
   </tr>
   --%>
    <%
    
 

    
     }
         oout.close();
         
         printToStream("<h3>",out);
         printToStream("Operation Completed Successfully: " + file_path,out);
         printToStream("</h3>",out);
         
        
   %>
       

     </TABLE>
     
<% 
  } 
   else
   
   {
	
	
    printToStream("<h3>",out);
    printToStream("Operation Completed Not Done:",out);
    printToStream("</h3>",out);	
	
}




%>

 </body>
 </html>