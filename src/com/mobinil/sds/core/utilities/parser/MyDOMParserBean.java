package com.mobinil.sds.core.utilities.parser;

import javax.xml.parsers.*;
import org.w3c.dom.*;

import com.mobinil.sds.core.utilities.Utility;

import java.io.*;

public class MyDOMParserBean
implements java.io.Serializable {



   public MyDOMParserBean() {
   }

   public static Document
   getDocument(String file) throws Exception {
   Document doc = null;
    try
    {
      // Step 1: create a DocumentBuilderFactory
      DocumentBuilderFactory dbf =
       DocumentBuilderFactory.newInstance();

      // Step 2: create a DocumentBuilder
      DocumentBuilder db = dbf.newDocumentBuilder();

      // Step 3: parse the input file to get a Document object
       doc = db.parse(new File(file));
     } 
     catch(Exception ex)
     {
        Utility.logger.debug("in  MyDOMParserBean.getDocument : " + ex. getMessage() );
     }
     return doc;
   }
}

