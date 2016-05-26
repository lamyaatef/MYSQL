/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.request.utility;

/**
 *
 * @author Salma
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.text.ParseException;
import java.util.*;
import javax.servlet.ServletConfig;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.*;


public class CommonUtil {

   
    private static Hashtable ebillIniParamters = new Hashtable();
    private static Hashtable ebillArabicIniParamters = new Hashtable();
    public static void initializeApplicationComponents(ServletConfig config) {
    }

  public static void initializeApplicationParamters() throws Exception {
        loadArabicIniParamters();

    }
    public static Vector<String> splitString(String strToSplit, String splitText) {
        if (strToSplit == null || splitText == null || strToSplit.equals("") || splitText.equals("")) {
            return null;
        }
        Vector<String> allSplitedStrs = new Vector<String>();
        copyArrayToVector(allSplitedStrs, strToSplit.split("[" + splitText + "]"));
        return allSplitedStrs;
    }

    public static void copyArrayToVector(Vector vec, Object[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].toString().equals("")) {
                vec.add(arr[i]);
            }
        }
    }

   

    /**
     * @return the ebillIniParamters
     */
    public static Hashtable getEbillIniParamters() {
        return ebillIniParamters;
    }

    /**
     * @param aEbillIniParamters the ebillIniParamters to set
     */
    public static void setEbillIniParamters(Hashtable aEbillIniParamters) {
        ebillIniParamters = aEbillIniParamters;
    }

    /**
     * @return the ebillArabicIniParamters
     */
    public static Hashtable getEbillArabicIniParamters() {
        return ebillArabicIniParamters;
    }

    /**
     * @param aEbillArabicIniParamters the ebillArabicIniParamters to set
     */
    public static void setEbillArabicIniParamters(Hashtable aEbillArabicIniParamters) {
        ebillArabicIniParamters = aEbillArabicIniParamters;
    }

    public String FormatSqlDate(String sqlDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = df.parse(sqlDate.toString());
        } catch (ParseException ex) {

            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("EEEEE, d MMMMM yyyy");
        java.util.Date javaDate = null;
        String yuiFormattedDate = formatter.format(date);
        //System.out.println(" Yui  date " + yuiFormattedDate);
        return yuiFormattedDate;

    }


     public static void loadArabicIniParamters() {
        try {

            //String configFilePath = new File("sds//ArabicParamters.ini").getAbsolutePath();
	   // System.out.println("file loading from:"+configFilePath);

            String configFilePath = "sds//ArabicParamters.ini";
            String currentDir = new File(configFilePath).getAbsolutePath();
            IniEditor iniEditor = new IniEditor();
            iniEditor.load(currentDir);
            //iniEditor.load(configFilePath);
            List sections = iniEditor.sectionNames();
            List options = null;
            String sectionNameStr = "";
            String optionNameStr = "";
            for (Object sectionNameObj : sections) {
                sectionNameStr = String.valueOf(sectionNameObj);
                options = iniEditor.optionNames(sectionNameStr);
                for (Object optionNameObj : options) {
                    optionNameStr = String.valueOf(optionNameObj);
                    getEbillArabicIniParamters().put(optionNameStr, iniEditor.get(sectionNameStr, optionNameStr));

                }

            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


}

