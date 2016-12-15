package com.mobinil.sds.core.system.scm.importdata;



import com.mobinil.sds.core.system.authenticationResult.dao.AuthResDAO;
import com.mobinil.sds.core.system.commercial.dao.CommercialFileDAO;
import com.mobinil.sds.core.system.nomad.dao.NomadFileDAO;
import com.mobinil.sds.core.system.supervisor.dao.SalesrepFileDAO;
import com.mobinil.sds.core.system.supervisor.dao.SupervisorFileDAO;
import com.mobinil.sds.core.system.supervisor.dao.TeamleaderFileDAO;
import com.mobinil.sds.core.system.tango.dao.TangoFileDAO;
        import java.io.File;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.PrintStream;
        import java.sql.Connection;
        import java.sql.Statement;

        import javax.xml.parsers.ParserConfigurationException;

        import com.mobinil.sds.core.utilities.Utility;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
        import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
        import org.apache.poi.openxml4j.opc.OPCPackage;
        import org.apache.poi.openxml4j.opc.PackageAccess;
        import org.apache.poi.ss.usermodel.DataFormatter;
        import org.apache.poi.ss.util.CellReference;
        import org.apache.poi.util.SAXHelper;
        import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
        import org.apache.poi.xssf.eventusermodel.XSSFReader;
        import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
        import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
        import org.apache.poi.xssf.model.StylesTable;
        import org.apache.poi.xssf.usermodel.XSSFComment;
        import org.xml.sax.ContentHandler;
        import org.xml.sax.InputSource;
        import org.xml.sax.SAXException;
        import org.xml.sax.XMLReader;

public class CommercialImporter {
    /**
     * Uses the XSSF Event SAX helpers to do most of the work
     *  of parsing the Sheet XML, and outputs the contents
     *  as a (basic) CSV.
     */
    private static final String UPDATED_ON="Update on";
    
    private static final String SELLER_USERNAME="Seller Username";
    private static final String STATUS_NAME= "Status";
    private Long fileID;
    private Connection con = null;
    private Statement stat = null;
    private String filePath = null;
    private int minColumns = 0 ;
    private int numberOfRowsInserted = 0 ;

    public int getNumberOfRowsInserted ()
    {
        return numberOfRowsInserted;
    }

    public void  clean()
    {
        try{
            this.stat.close();
            Utility.closeConnection(con);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public CommercialImporter (String userId,String fileDate,Long fileID , String filePath , int minColumns)
    {
        System.out.println("file path : "+filePath+" and user id from hashmap : "+userId);
        int updateOn=-1;
        int sellerIndx = -1;
        int statusIndx = -1;
        boolean isemptyField= false;
        this.fileID = fileID;
        this.filePath = filePath;
        this.minColumns = minColumns;
        try{
            this.con  = Utility.getConnection();
            this.stat = con.createStatement();
            File xlsxFile = new File(filePath);

            //OPCPackage p = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ);
            //this.xlsxPackage = p; removed by lamya for now
        }
        catch (Exception e)
        {
            System.out.println("FILE EXCEPTION");
            e.printStackTrace();
        }
        
        //lamya : CSV file
        //Long fileid = null;
        String line = null;
        BufferedReader input = null;
        int count = 0;
        
                try {
                  //  System.out.println("before buffered reader : "+filePath);
                    input = new BufferedReader(new FileReader(filePath));
                    System.out.println("input is for csv : "+input);
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                //    System.out.println("buffered reader exception "+e1);
                    e1.printStackTrace();
                }
                
                try {


            //    System.out.println("NOMAD line = input.readLine() "+input.readLine());
                    while ((line = input.readLine()) != null) {
                        count++;
                       
                         if (count > 1) {//!=0
                            System.out.println("^^^^^^^^^^start^^^^^^^^^\n");
                            String fields = input.readLine();//line;
                            String v1 = fields;
                            String[] lineFields = null;
                           System.out.println("LINE V1 %%%% "+v1); 
                            if (v1.contains(","))
                                lineFields = v1.split(","); // \t
                            else if (v1.contains("\t"))
                                lineFields = v1.split("\t"); // \t
                            
                           System.out.println("LINE  %%%% "+lineFields);
                            if(lineFields!=null)
                           {
                               isemptyField = false;
                               if(lineFields.length<2)
                                   isemptyField = true;
                            System.out.println("LINE not null %%%% "+lineFields);
                           
                            if (v1 == null) 
                                v1 = "";
                            CommercialFileDAO.insertCommercialData(con, stat,userId,lineFields,count/*,fileDate,updateOn*/);
                            System.out.println("^^^^^^^^^^end^^^^^^^^^");
                           
                           }  
                           break; 
                        }
                       // System.out.println("count isssssss      " + count);
                         
                    }
                    this.numberOfRowsInserted = 0;//SalesrepFileDAO.getSalesrepDataRecords(con, stat, fileID);
                    System.out.println("rows %%%% "+this.numberOfRowsInserted);

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    try {
                        input.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                //lamya 

    }


    public CommercialImporter(OPCPackage pkg) {
        this.xlsxPackage = pkg;

    }

    private class SupervisorSheetToCSV implements SheetContentsHandler {
        private boolean firstCellOfRow = false;
        private int currentRow = -1;
        private int currentCol = -1;
        private StringBuffer str = null;


        private void outputMissingRows(int number) {
            for (int i=0; i<number; i++) {
                for (int j=0; j<minColumns; j++) {
                  //  output.append(',');
                }
                //output.append('\n');
            }
        }

        public void startRow(int rowNum) {
            str = new StringBuffer();
            str.append("insert into gen_dcm_nomad ( GEN_DCM_NOMAD_FILE_ID, CONTRACT_NUMBER, TYPE, SOURCE ,MSISDN, SIM_NUMBER, ID_NUMBER, SELLER, RECEIVED_ON, UPDATE_ON, UPDATED_BY, STATUS, CATEGORY\n" +
                    ") values ("+fileID +",");


            // If there were gaps, output the missing rows
            outputMissingRows(rowNum -currentRow-1);
            // Prepare for this row
            firstCellOfRow = true;
            currentRow = rowNum;
            currentCol = -1;
        }

        public void endRow(int rowNum) {
            // Ensure the minimum number of columns
            for (int i=currentCol; i<minColumns; i++) {
                //output.append(',');
            }

            //System.out.println(str.toString());
            try {
                
                if (numberOfRowsInserted>0)//removed by laya
                    stat.execute(str.toString()); //removed by lamya
                    
              
                
                numberOfRowsInserted ++ ;
                
                if (numberOfRowsInserted % 1000 == 0)
                    System.out.println("inserted so far "+numberOfRowsInserted);
                
                System.out.println(str.toString());
            }
            catch (Exception e)
            {
                System.out.println("sql :"+ str.toString() ); 
                e.printStackTrace();
            }
            
           // output.append('\n');
        }

        public void cell(String cellReference, String formattedValue,
                         XSSFComment comment) {



            if (firstCellOfRow) {
                firstCellOfRow = false;
            }
            else {
            //    output.append(',');
            }


            // Did we miss any cells?
            int thisCol = (new CellReference(cellReference)).getCol();
            int missedCols = thisCol - currentCol - 1;
            for (int i=0; i<missedCols; i++) {
                //output.append(',');
            }
            currentCol = thisCol;

            if (currentCol == 7 || currentCol ==8)
            {
                str.append("to_date('").append(formattedValue).append("','mm/dd/yy hh24:mi')");
            }
            else
            str.append("'").append(formattedValue).append("'");

            if (currentCol != 11)
            {
                str.append(",");
            }
            else{
                str.append(")");
            }
            // Number or string?
            try {
                Double.parseDouble(formattedValue);
              //  output.append(formattedValue);
            } catch (NumberFormatException e) {
               // output.append('"');
             //   output.append(formattedValue);
              //  output.append('"');
            }

        }

        public void headerFooter(String text, boolean isHeader, String tagName) {
            // Skip, no headers or footers in CSV
        }
    }


    ///////////////////////////////////////

    private  OPCPackage xlsxPackage  ;






    /**
     * Parses and shows the content of one sheet
     * using the specified styles and shared-strings tables.
     *
     * @param styles
     * @param strings
     * @param sheetInputStream
     */
    public void processSheet(
            StylesTable styles,
            ReadOnlySharedStringsTable strings,
            SheetContentsHandler sheetHandler,
            InputStream sheetInputStream)
            throws IOException, ParserConfigurationException, SAXException {
        DataFormatter formatter = new DataFormatter();
        InputSource sheetSource = new InputSource(sheetInputStream);
        try {
            XMLReader sheetParser = SAXHelper.newXMLReader();
            ContentHandler handler = new XSSFSheetXMLHandler(
                    styles, null, strings, sheetHandler, formatter, false);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);
        } catch(ParserConfigurationException e) {
            throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
        }
    }

    /**
     * Initiates the processing of the XLS workbook file to CSV.
     *
     * @throws IOException
     * @throws OpenXML4JException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void process()
            throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        int index = 0;
        while (iter.hasNext()) {
            InputStream stream = iter.next();
            String sheetName = iter.getSheetName();
          //  this.output.println();
          //  this.output.println(sheetName + " [index=" + index + "]:");
            processSheet(styles, strings, new SupervisorSheetToCSV(), stream);
            stream.close();
            ++index;
        }
    }

    public static void main(String[] args) throws Exception {

        File xlsxFile = new File("/Users/home/Desktop/Work Projects/sds/src/Nomad report.xlsx");
        if (!xlsxFile.exists()) {
            System.err.println("Not found or not a file: " + xlsxFile.getPath());
            return;
        }

        int minColumns = -1;
        if (args.length >= 2)
            minColumns = Integer.parseInt(args[1]);

        // The package open is instantaneous, as it should be.
        OPCPackage p = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ);
        CommercialImporter xlsx2csv = new CommercialImporter(p);
        xlsx2csv.process();
        p.close();
    }
}