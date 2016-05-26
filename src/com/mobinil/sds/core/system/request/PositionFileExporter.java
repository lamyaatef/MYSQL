import java.io.*;

/**
 * Created by home on 3/21/15.
 *
 */
public class PositionFileExporter {

    final static String filePath = "/Users/home/Desktop/mhagry_Projects/SDSDCMExport/src/Query5(1) - Query5(2).csv";
    final static String positionFilePath  = "/Users/home/Desktop/mhagry_Projects/SDSDCMExport/src/Position.csv";
    final static String batchName ="MNSH_20150321141000";
    final static String effectiveStartDate= "20140101";
    public static void main (String args[])
    {
        System.out.println("Hi begining of code");

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        String positionTitle="STAGEPOSITIONSEQ,BATCHNAME,POSITIONNAME,"
                + "EFFECTIVESTARTDATE,EFFECTIVEENDDATE 5  ,POSITIONSEQ,"
                + "MANAGERSEQ ,PAYEEID,PAYEETYPE,PAYEESEQ ,TITLESEQ,"
                + "PLANNAME,MANAGERNAME 3,TITLENAME,POSITIONGROUPNAME,"
                + "TARGETCOMPENSATION,UNITTYPEFORTARGETCOMPENSATION,"
                + "STAGEPROCESSDATE ,STAGEPROCESSFLAG ,BUSINESSUNITNAME,"
                + "BUSINESSUNITMAP ,DESCRIPTION,GENERICATTRIBUTE1,"
                + "GENERICATTRIBUTE2,GENERICATTRIBUTE3,GENERICATTRIBUTE4,"
                + "GENERICATTRIBUTE5,GENERICATTRIBUTE6,GENERICATTRIBUTE7,"
                + "GENERICATTRIBUTE8,GENERICATTRIBUTE9,GENERICATTRIBUTE10,"
                + "GENERICATTRIBUTE11,GENERICATTRIBUTE12,GENERICATTRIBUTE13,"
                + "GENERICATTRIBUTE14,GENERICATTRIBUTE15,GENERICATTRIBUTE16,"
                + "GENERICNUMBER1,UNITTYPEFORGENERICNUMBER1,GENERICNUMBER2,"
                + "UNITTYPEFORGENERICNUMBER2,GENERICNUMBER3,"
                + "UNITTYPEFORGENERICNUMBER3,GENERICNUMBER4,"
                + "UNITTYPEFORGENERICNUMBER4,GENERICNUMBER5,"
                + "UNITTYPEFORGENERICNUMBER5,GENERICNUMBER6,"
                + "UNITTYPEFORGENERICNUMBER6,GENERICDATE1,"
                + "GENERICDATE2,GENERICDATE3,GENERICDATE4,"
                + "GENERICDATE5,GENERICDATE6,GENERICBOOLEAN1,"
                + "GENERICBOOLEAN2,GENERICBOOLEAN3,GENERICBOOLEAN4,"
                + "GENERICBOOLEAN5,GENERICBOOLEAN6,CREDITSTARTDATE,"
                + "CREDITENDDATE,PROCESSINGSTARTDATE,PROCESSINGENDDATE,STAGEERRORCODE";

        try {

            br = new BufferedReader(new FileReader(filePath));

            File fileDir = new File(positionFilePath);

            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"));
            out.append(positionTitle).append("\r\n");
            br.readLine();


            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(cvsSplitBy);


              //  System.out.println(line);
              //  out.append(line).append("\r\n");
                String channelValue = data[3];
                String channelName =channelValue;

                if ((channelValue.compareTo("Authorized Agents Regional")==0) || (channelValue.compareTo("Authorized Super Dealers")==0)
                        || ( channelValue.compareTo("Authorized Agents")==0))
                    channelName = "Enterprise";


                String titleValue = data[34];
                String tName =titleValue;
                if (titleValue.compareTo("Khadamaty")==0)
                    tName= "Kmaty";

                if (titleValue.compareTo("Momken")==0)
                    tName= "Momkn";



                if ((titleValue.compareTo("Authorized Agents Regional")==0) || (titleValue.compareTo("Authorized Super Dealers")==0)
                        || ( titleValue.compareTo("Authorized Agents")==0))
                    tName="Enterprise";

                if (line.contains("B-Tech")) {
                    tName = "B-Tech";
                }

                System.out.println("channel value = "+channelValue);

                if (channelValue.compareTo("Distribution")==0) {
                    tName= "WO/Sign-POS";
                    System.out.println("check ");

                    if (data.length > 45 && data[44] != null && data[44].compareTo("Y") == 0)
                        tName = "Level 1";
                    else if (data.length > 45 && data[45] != null && data[45].compareTo("Y") == 0)
                        tName = "Ex-POS";
                    else if (data.length > 46 && data[46] != null && data[46].compareTo("Y") == 0)
                        tName = "W/Sign-POS";
                    else if (data[34].trim().compareTo("Level 0")==0)
                    {tName = "Level 0";}


                }

                System.out.println("tname = "+ tName);
                System.out.println("data[34]" + data[34] +"  "+(data[34].trim().compareTo("Level 0")));
                if (data[34].compareTo("Mobinil Express")==0)
                    tName = "Express";

                if (line.contains("Online"))
                {
                    tName ="Online Sales";
                }



                out.append(",");
                out.append(batchName+",");
                out.append(data[0]+"-"+tName+",");//position Name


                out.append(effectiveStartDate +",");
                out.append(",");//EFFECTIVEENDDATE
                out.append(",");//POSITIONSEQ
                out.append(",");//MANAGERSEQ
                out.append(data[0]+",");//PAYEEID
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

                if (channelValue.compareTo("Distribution")==0)
                out.append(tName + ","); //GENERICATTRIBUTE1
                else
                out.append(",");

                out.append(data[1]+",");//name
                out.append(",");
                out.append(",");
                out.append(data[11]+",");//sales region
                out.append(data[13]+",");//governerate
                out.append(data[12]+",");//city
                out.append(data[15]+",");//district
                out.append(data[18]+",");//area

               if (data[29].startsWith("0"))
                out.append(data[29]+",");//stk phone
                else
                   out.append("0"+data[29]+",");//stk phone

                out.append(data[20]+",");//doc number

                if (data[24].startsWith("0"))
                out.append(data[24]+",");//phone number
                else
                out.append("0"+data[24]+",");


                out.append(data[30]+",");
                out.append(",");
                out.append(data[33]+",");
                out.append(data[23]+",");
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



                if (tName.compareTo("Level 1")==0)
                {
                    out.append(",");
                    out.append(batchName+",");
                    out.append(data[0]+"-"+"WH-POS"+",");//position Name


                    out.append(effectiveStartDate +",");
                    out.append(",");//EFFECTIVEENDDATE
                    out.append(",");//POSITIONSEQ
                    out.append(",");//MANAGERSEQ
                    out.append(data[0]+",");//PAYEEID
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
                    out.append(data[1]+",");//name
                    out.append(",");
                    out.append(",");
                    out.append(data[11]+",");//sales region
                    out.append(data[13]+",");//governerate
                    out.append(data[12]+",");//city
                    out.append(data[15]+",");//district
                    out.append(data[19]+",");//address
                    out.append("0"+data[24]+",");//owner phone
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
