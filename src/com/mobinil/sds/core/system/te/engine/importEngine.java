package com.mobinil.sds.core.system.te.engine;
import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.*;


public class importEngine 
{
  public importEngine()
  {
  }
  public static Object getFileHashMap(String fileName)
  {
        Vector fileArray= new Vector();
        Vector errorFileArray= new Vector();
        HashMap fileData = new HashMap();
    try {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String str;
        while ((str = in.readLine()) != null) {
        
        int len = str.length();
        if (len<=30){
        fileArray.add(str);        
        }
        else{
        errorFileArray.add(str);
        }
        }
        in.close();
   

        System.out.println(fileArray.size());
    if (fileArray.size()>50000){
    return "File data range must be between 0 and 50000.";
    }
    else
    {
    fileData.put("validData",fileArray);
    fileData.put("invalidData",errorFileArray);
    return fileData;
    }
         } catch (IOException e) {
    }
    return null;

  }
}