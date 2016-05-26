import java.io.*;

/**
 * Created by home on 3/21/15.
 */
public class ParticipantFileExporter {

    final static String filePath = "/Users/home/Desktop/mhagry_Projects/SDSDCMExport/src/Query5(1) - Query5(2).csv";
    final static String participantFilePath  = "/Users/home/Desktop/mhagry_Projects/SDSDCMExport/src/Participant.csv";
    final static String batchName ="MNSH_20150321141000";
    final static String effectiveStartDate= "20140101";
    public static void main (String args[])
    {
        System.out.println("Hi begining of code");

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        String positionTitle="STAGEPARTICIPANTSEQ,BATCHNAME,PAYEEID,EFFECTIVESTARTDATE,"
                + "EFFECTIVEENDDATE 3,PAYEESEQ ,PREFIX,FIRSTNAME,MIDDLENAME,"
                + "LASTNAME,SUFFIX,TAXID,SALARY,UNITTYPEFORSALARY,HIREDATE,TERMINATIONDATE,"
                + "STAGEPROCESSDATE ,STAGEPROCESSFLAG ,BUSINESSUNITNAME,BUSINESSUNITMAP ,"
                + "DESCRIPTION,GENERICATTRIBUTE1,GENERICATTRIBUTE2,GENERICATTRIBUTE3,GENERICATTRIBUTE4,"
                + "GENERICATTRIBUTE5,GENERICATTRIBUTE6,GENERICATTRIBUTE7,GENERICATTRIBUTE8,"
                + "GENERICATTRIBUTE9,GENERICATTRIBUTE10,GENERICATTRIBUTE11,GENERICATTRIBUTE12,"
                + "GENERICATTRIBUTE13,GENERICATTRIBUTE14,GENERICATTRIBUTE15,GENERICATTRIBUTE16,"
                + "GENERICNUMBER1,UNITTYPEFORGENERICNUMBER1,GENERICNUMBER2,UNITTYPEFORGENERICNUMBER2,"
                + "GENERICNUMBER3,UNITTYPEFORGENERICNUMBER3,GENERICNUMBER4,UNITTYPEFORGENERICNUMBER4,"
                + "GENERICNUMBER5,UNITTYPEFORGENERICNUMBER5,GENERICNUMBER6,UNITTYPEFORGENERICNUMBER6,"
                + "GENERICDATE1,GENERICDATE2,GENERICDATE3,GENERICDATE4,GENERICDATE5,GENERICDATE6,"
                + "GENERICBOOLEAN1,GENERICBOOLEAN2,GENERICBOOLEAN3,GENERICBOOLEAN4,GENERICBOOLEAN5,"
                + "GENERICBOOLEAN6,USERID,STAGEERRORCODE";
        try {

            br = new BufferedReader(new FileReader(filePath));

            File fileDir = new File(participantFilePath);

            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"));
            out.append(positionTitle).append("\r\n");
            br.readLine();



            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(cvsSplitBy);


                //  System.out.println(line);
                //  out.append(line).append("\r\n");
                String channelValue = data[3];
                String channelName = channelValue;

                if ((channelValue.compareTo("Authorized Agents Regional")==0) || (channelValue.compareTo("Authorized Super Dealers")==0)
                        || ( channelValue.compareTo("Authorized Agents")==0))
                    channelName="Enterprise";


                System.out.println(line);
                System.out.println("lenght ="+ data.length);

                String titleValue = data[34];
                System.out.println(titleValue);
                String tName = titleValue;

                if (titleValue.compareTo("Khadamaty") == 0)
                    tName = "Kmaty";

                if (titleValue.compareTo("Momken") == 0)
                    tName = "Momkn";


                if ((titleValue.compareTo("Authorized Agents Regional")==0) || (titleValue.compareTo("Authorized Super Dealers")==0)
                        || ( titleValue.compareTo("Authorized Agents")==0))
                    tName = "Enterprise";

                if (line.contains("B-Tech")) {
                    tName = "B-Tech";
                }

                if (channelValue.compareTo("Distribution") == 0) {
                    tName = "WO/Sign-POS";
                    if (data.length > 45 && data[44] != null && data[44].compareTo("Y") == 0)
                        tName = "Level 1";
                    else if (data.length > 45 && data[45] != null && data[45].compareTo("Y") == 0)
                        tName = "Ex-POS";
                    else if (data.length > 46 && data[46] != null && data[46].compareTo("Y") == 0)
                        tName = "W/Sign-POS";
                    else if (data[34].trim().compareTo("Level 0")==0)
                    {tName = "Level 0";}

                }

                if (data[34].compareTo("Mobinil Express") == 0)
                    tName = "Express";

                if (line.contains("Online")) {
                    tName = "Online Sales";
                }


                out.append(",");
                out.append(batchName + ",");
                out.append(data[0] + ",");//position Name


                out.append(effectiveStartDate + ",");
                out.append(",");//EFFECTIVEENDDATE

                out.append(",");
                out.append(",");


                out.append(",");
                out.append(",");
                out.append(data[8] + ","); //last name

                out.append(",");
                out.append(","); //tax id
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(",");
                out.append(channelName + ",");//channel name
                out.append(tName + ","); //sub channel


                if (tName.compareTo("Add and Win")==0)
                {
                    out.append("Credit Advice" +",");
                }

                else if ((tName.compareTo("Enterprise") == 0) )
                {
                    out.append("Cheque"+",");
                }
                else   if ((tName.compareTo("Level 1")==0) || (tName.compareTo("Level 1")==0) ||  (tName.compareTo("WO/Sign-POS") == 0) || (tName.compareTo("Ex-POS")==0) || (tName.compareTo("W/Sign-POS")==0) )
                {
                    out.append("E-Topup"+",");

                }
                else
                {
                    out.append("Credit Advice"+",");
                }


                for (int i = 0 ; i <23; i++)
                    out.append(",");

                out.append("1,");

                for (int i = 0 ; i <13; i++)
                    out.append(",");

                out.append(data[0] + ",");//position Name

                out.append(",");

                out.append("\r\n");

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
