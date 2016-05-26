package com.mobinil.sds.core.system.calidus;

import java.io.*;
import java.sql.ResultSet;
//import EasyXLS.*;
//import EasyXLS.Constants.*;
//import com.isea.easyxls.EasyXls;
import java.lang.Object;
import java.sql.ResultSetMetaData;
import java.util.Date;
/**
 * Created by home on 3/21/15.
 *
 */
public class PositionFileExporter {

    //final static String filePath = "/Users/home/Desktop/mhagry_Projects/SDSDCMExport/src/Query5(1) - Query5(2).csv";
    //final static String positionFilePath  = "/Users/home/Desktop/mhagry_Projects/SDSDCMExport/src/Position.csv";
    public final static String positionFilePath  = "/home/sand/Downloads/SDSDCMExport/src/zip/PositionLamya.csv";
    final static String batchName ="MNSH_";//"MNSH_20150321141000";
    final static String effectiveStartDate= "20140101";
   
    
    public static void constructFile(ResultSet rs) throws Exception
    //public static void main (String args[])
    {
       /* ExcelDocument xls = new ExcelDocument();
        System.out.println("Writing file C:\\Samples\\Tutorial01.xls.");
        xls.easy_WriteXLSFile_FromResultSet("C:\\Samples\\Tutorial01.xls", resultset, new ExcelAutoFormat(Styles.AUTOFORMAT_EASYXLS1), "Sheet1");

             // Confirm export of Excel file
        if (xls.easy_getError().equals(""))
                     System.out.println("File successfully created.");
        else
           System.out.println("Error encountered: " + xls.easy_getError());
        xls.Dispose();*/
        
        System.out.println("Hi begining of code : "+rs);
        
        Date d = new Date();
        String sDate = d.toString();
        
        batchName.concat(sDate);

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        String positionTitle="STAGEPOSITIONSEQ,BATCHNAME,POSITIONNAME,EFFECTIVESTARTDATE,"
                + "EFFECTIVEENDDATE 5  ,POSITIONSEQ,MANAGERSEQ "
                + ",PAYEEID,PAYEETYPE,PAYEESEQ ,TITLESEQ,PLANNAME,"
                + "MANAGERNAME 3,TITLENAME,POSITIONGROUPNAME,"
                + "TARGETCOMPENSATION,UNITTYPEFORTARGETCOMPENSATION,"
                + "STAGEPROCESSDATE ,STAGEPROCESSFLAG ,BUSINESSUNITNAME,"
                + "BUSINESSUNITMAP ,DESCRIPTION,GENERICATTRIBUTE1,"
                + "GENERICATTRIBUTE2,GENERICATTRIBUTE3,GENERICATTRIBUTE4,"
                + "GENERICATTRIBUTE5,GENERICATTRIBUTE6,GENERICATTRIBUTE7,"
                + "GENERICATTRIBUTE8,GENERICATTRIBUTE9,GENERICATTRIBUTE10,"
                + "GENERICATTRIBUTE11,GENERICATTRIBUTE12,GENERICATTRIBUTE13,"
                + "GENERICATTRIBUTE14,GENERICATTRIBUTE15,GENERICATTRIBUTE16,"
                + "GENERICNUMBER1,UNITTYPEFORGENERICNUMBER1,GENERICNUMBER2,"
                + "UNITTYPEFORGENERICNUMBER2,GENERICNUMBER3,UNITTYPEFORGENERICNUMBER3,"
                + "GENERICNUMBER4,UNITTYPEFORGENERICNUMBER4,GENERICNUMBER5,"
                + "UNITTYPEFORGENERICNUMBER5,GENERICNUMBER6,UNITTYPEFORGENERICNUMBER6,"
                + "GENERICDATE1,GENERICDATE2,GENERICDATE3,GENERICDATE4,GENERICDATE5,"
                + "GENERICDATE6,GENERICBOOLEAN1,GENERICBOOLEAN2,GENERICBOOLEAN3,"
                + "GENERICBOOLEAN4,GENERICBOOLEAN5,GENERICBOOLEAN6,CREDITSTARTDATE,"
                + "CREDITENDDATE,PROCESSINGSTARTDATE,PROCESSINGENDDATE,STAGEERRORCODE";

        try {
            //input
            //br = new BufferedReader(new FileReader(filePath));
            //output
            
            File fileDir = new File(positionFilePath);
            //output stream
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"));
            out.append(positionTitle).append("\r\n");
            //reading the input
            //br.readLine();
            //  while (    (line = rs.getString(columnIndex)/*br.readLine()*/) != null  && rs.next()    ) {
            
            int columnIndex = 1;
            int rowNumber = 1;
            ResultSetMetaData mData = rs.getMetaData();
            int noOfColumns = mData.getColumnCount();
            while(columnIndex<=noOfColumns)
            {
               System.out.println("String ["+columnIndex+"]  : "+mData.getColumnName(columnIndex)+"\n"); 
               columnIndex++;
            }
            
            
            while (rs.next()) {  
               System.out.println("records : "+rowNumber+"\n");
               // System.out.println("String ["+columnIndex+"]  : "+rs.getString(columnIndex)+"  "+mData.getColumnName(columnIndex)+"\n");
               // System.out.println("inside whileeeeeeeeeee row no : "+rowNumber);
               // line = rs.getString(columnIndex);  
               // System.out.println("inside construct file : line is : "+line);
                // use comma as separator
               // String[] data = line.split(cvsSplitBy);

             //   System.out.println("first column in resutl set : "+rs.getString(1));    
              //  System.out.println("second column in resutl set : "+rs.getString(2));   
              //  System.out.println("second column in resutl set : "+rs.getString(3));  
              //  System.out.println(line);
              //  out.append(line).append("\r\n");
                //rs.getString(3);
                String channelValue = rs.getString(4);//data[3];
                System.out.println("channel value : "+channelValue);
                String channelName =channelValue;
                if (channelValue !=null)
                {
                    if ((channelValue.compareTo("Authorized Agents Regional")==0) || (channelValue.compareTo("Authorized Super Dealers")==0)
                            || ( channelValue.compareTo("Authorized Agents")==0))
                        channelName = "Enterprise";
                }

                String titleValue = rs.getString(35);//data[34];
                String tName =titleValue;
                if(titleValue != null)
                {
                    if (titleValue.compareTo("Khadamaty")==0)
                        tName= "Kmaty";

                    if (titleValue.compareTo("Momken")==0)
                        tName= "Momkn";



                    if ((titleValue.compareTo("Authorized Agents Regional")==0) || (titleValue.compareTo("Authorized Super Dealers")==0)
                            || ( titleValue.compareTo("Authorized Agents")==0))
                        tName="Enterprise";
                }
//how to deal with line here
                /*if (line.contains("B-Tech")) {
                    tName = "B-Tech";
                }*/
                if((rs.getString("English_Name"))!=null && (rs.getString("POS_English_Name"))!=null && (rs.getString("Pay_Level_Name"))!=null)
                {
                    if (/*line.contains("Online")*/rs.getString("English_Name").contains("B-Tech") || rs.getString("POS_English_Name").contains("B-Tech") || rs.getString("Pay_Level_Name").contains("B-Tech")) 
                        tName = "B-Tech";
                }
                

                System.out.println("channel value = "+channelValue);
                if(channelValue!=null)
                {
                    if (channelValue.compareTo("Distribution")==0) 
                     tName= "WO/Sign-POS";
                }
                   // System.out.println("check ");

                    if (/*data.length > 45 &&*/ /*data[44]*/rs.getString(44) != null && /*data[44]*/rs.getString(44).compareTo("Y") == 0)
                        tName = "Level 1";
                    else if (/*data.length > 45 && data[45]*/ rs.getString(45) != null && /*data[45]*/rs.getString(45).compareTo("Y") == 0)
                        tName = "Ex-POS";
                    else if (/*data.length > 46 && data[46]*/rs.getString(46) != null && /*data[46]*/rs.getString(46).compareTo("Y") == 0)
                        tName = "W/Sign-POS";
                    else if (/*data[34]*/ rs.getString(35)!=null && rs.getString(35).trim().compareTo("Level 0")==0) //34
                    {tName = "Level 0";}


                

                System.out.println("tname = "+ tName);
              //  System.out.println("data[34]" + data[34] +"  "+(data[34].trim().compareTo("Level 0")));
                if (/*data[34]*/rs.getString(35)!=null && rs.getString(35).compareTo("Mobinil Express")==0) //34
                    tName = "Express";
                if (rs.getString("COMMERCIAL_GOV")!=null)
                {
                    if (rs.getString("COMMERCIAL_GOV").contains("Online"))/*if (line.contains("Online"))*/
                        {
                            tName ="Online Sales";
                        }
                }



                out.append(",");
                out.append(batchName+",");
                out.append(/*data[0]*/rs.getString(1)+"-"+tName+",");//position Name


                out.append(effectiveStartDate +",");
                out.append(",");//EFFECTIVEENDDATE
                out.append(",");//POSITIONSEQ
                out.append(",");//MANAGERSEQ
                out.append(/*data[0]*/rs.getString(1)+",");//PAYEEID
                out.append(",");//PAYEETYPE
                out.append(",");//PAYEESEQ
                out.append(",");//TITLESEQ
                out.append(",");//PLANNAME
                out.append(channelName+",");//MANAGERNAME
                out.append(tName + ","); //titleName
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                if (channelValue !=null)
                {
                    if (channelValue.compareTo("Distribution")==0)
                    out.append(tName + ","); //GENERICATTRIBUTE1
                    else
                    out.append(",");
                }

                out.append(/*data[1]*/rs.getString(2)+",");//name
                out.append(",");
                out.append(",");
                out.append(/*data[11]*/rs.getString(12)+",");//sales region 11
                out.append(/*data[13]*/rs.getString(14)+",");//governerate 13
                out.append(/*data[12]*/rs.getString(13)+",");//city 12
                out.append(/*data[15]*/rs.getString(16)+",");//district 15
                out.append(/*data[18]*/rs.getString(19)+",");//area 18

               if (rs.getString(29) != null )/*data[29]*/
                { 
                    if (rs.getString(29).startsWith("0"))
                         out.append(/*data[29]*/rs.getString(29)+",");//stk phone
                    else
                         out.append("0"+/*data[29]*/rs.getString(29)+",");//stk phone
               }
               out.append(/*data[20]*/rs.getString(21)+",");//doc number 20

               if (rs.getString(25)!=null)
                {
                    if (/*data[24]*/rs.getString(25).startsWith("0"))
                        out.append(/*data[24]*/rs.getString(25)+",");//phone number 24
                    else
                        out.append("0"+/*data[24]*/rs.getString(25)+",");
                }


                out.append(/*data[30]*/rs.getString(30)+",");
                out.append(",");
                out.append(/*data[33]*/rs.getString(33)+",");
                out.append(/*data[23]*/rs.getString(23)+",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append("1,");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");


                out.append("\r\n");


                if (tName !=null)
                {
                if (tName.compareTo("Level 1")==0)
                {
                    out.append(",");
                    out.append(batchName+",");
                    out.append(/*data[0]*/rs.getString(1)+"-"+"WH-POS"+",");//position Name 0


                    out.append(effectiveStartDate +",");
                    out.append(",");//EFFECTIVEENDDATE
                    out.append(",");//POSITIONSEQ
                    out.append(",");//MANAGERSEQ
                    out.append(/*data[0]*/rs.getString(1)+",");//PAYEEID 0
                    out.append(",");//PAYEETYPE
                    out.append(",");//PAYEESEQ
                    out.append(",");//TITLESEQ
                    out.append(",");//PLANNAME
                    out.append(channelName+",");//MANAGERNAME
                    out.append("WH-POS" + ","); //titleName
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(tName + ","); //GENERICATTRIBUTE1
                    out.append(/*data[1]*/rs.getString(2)+",");//name 1
                    out.append(",");
                    out.append(",");
                    out.append(/*data[11]*/rs.getString(12)+",");//sales region 11
                    out.append(/*data[13]*/rs.getString(14)+",");//governerate 13
                    out.append(/*data[12]*/rs.getString(13)+",");//city 12
                    out.append(/*data[15]*/rs.getString(16)+",");//district 15
                    out.append(/*data[19]*/rs.getString(20)+",");//address 19
                    out.append("0"+/*data[24]*/rs.getString(25)+",");//owner phone 24
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append("1,");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(",");
                    out.append(""); //last line no comma


                    out.append("\r\n");
                }
                }
                rowNumber++;
               // columnIndex++;
            }
            

            out.flush();
            out.close();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }




                System.out.println("End of code");
    }
}
