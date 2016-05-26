/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.request.utility;

/**
 *
 * @author Salma
 */
import java.io.*;
import java.util.zip.*;

public class Zip {
   static final int BUFFER = 2048;

   public static void deleteFilePdf(String folderPath)
   {
      File f = new File(folderPath/*"F:\\EbillFinal\\SDS\\build\\web\\downloadItems"*/);
      String files[] = f.list();

         for (int i=0; i<files.length; i++)
         {
            File f1 = new File(folderPath+File.separator+files[i]);
            f1.delete();
         }

   }

   public static String zipPdfFile(String pdfFoldeFullPath , String outFilePath)
   {
       java.util.Date dateNow = new java.util.Date();
         int imonth = dateNow.getMonth() + 1;
         int iyear = dateNow.getYear() + 1900;
      String fullZipPath = "";
      String zipName = (new StringBuffer("[")).append(dateNow.getDate()).append("-").append(imonth).append("-").append(iyear).append("]-").append(dateNow.getHours()).append(".").append(dateNow.getMinutes()).append(".").append(dateNow.getSeconds()).append("_IqrarFiles").append(".").append("zip").toString();

        try {
         BufferedInputStream origin = null;
         
          fullZipPath = outFilePath+File.separator+zipName;
         FileOutputStream dest = new FileOutputStream(fullZipPath);
         ZipOutputStream out = new ZipOutputStream(new
         BufferedOutputStream(dest));
         //out.setMethod(ZipOutputStream.DEFLATED);
         byte data[] = new byte[BUFFER];
         // get a list of files from current directory
         File f = new File(pdfFoldeFullPath/*"F:\\EbillFinal\\SDS\\build\\web\\downloadItems"*/);
         String files[] = f.list();

         for (int i=0; i<files.length; i++) {
            System.out.println("Adding: "+files[i]);
            String filePath = pdfFoldeFullPath +File.separator+files[i];
            FileInputStream fi = new FileInputStream(filePath);
            origin = new
              BufferedInputStream(fi, BUFFER);
            ZipEntry entry = new ZipEntry(files[i]);
            out.putNextEntry(entry);
            int count;
            while((count = origin.read(data, 0,
              BUFFER)) != -1) {
               out.write(data, 0, count);
            }
            origin.close();
         }
         out.close();
      } catch(Exception e) {
         e.printStackTrace();
      }
        return zipName;
   }
   public static void main (String argv[])
   {
     
   }
}


