/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.utilities.jaspertest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 * 
 * @author amr
 */
public class JasperManager {
	private static String[] all_file_path;
	private static String[] all_queries, all_excel_sheet_names;
	private static String file_path_to_export;

	public static void setParameter(String[] all_file_path, String[] all_queries,String file_path_to_export, String[] all_excel_sheet_names) {
		JasperManager.all_file_path = all_file_path;
	//	System.out.println("dddddddddd 1: "+all_file_path[0]+"  sssdddddddddd 1: "+JasperManager.all_file_path[0]);
		JasperManager.all_queries=all_queries; 
		JasperManager.file_path_to_export=file_path_to_export;
		JasperManager.all_excel_sheet_names=all_excel_sheet_names;
	} 

	// To Amr :D : ass values to variables
	public static void exportXLSReport(Connection connection) {
		try {
	//		JRProperties.setProperty(JRProperties.COMPILER_XML_VALIDATION, false);
//			JRProperties.setProperty(JRProperties.EXPORT_XML_VALIDATION, false);
			//System.out.println("dddddddddd 1: "+JasperManager.all_file_path.length);
		    System.out.println(all_file_path[0]);
			JasperDesign jasperDesign_1 = JRXmlLoader.load(all_file_path[0]);
			JRDesignQuery query_1 = new JRDesignQuery();
			query_1.setText(all_queries[0]);
			//System.out.println("ddddddddddddddddddddddd "+all_queries.length);
			jasperDesign_1.setQuery(query_1);
			JasperReport jasperReport_1 = JasperCompileManager
					.compileReport(jasperDesign_1);
			JasperPrint jasperPrint_1 = JasperFillManager.fillReport(
					jasperReport_1, null, connection);
			int j = 1;
			for (int i = 1; i < all_queries.length; i++) {

				// ///////////////////////////////////////// 
				JasperDesign jasperDesign_2 = JRXmlLoader.load(all_file_path[i]);
				JRDesignQuery query_2 = new JRDesignQuery();
				query_2.setText(all_queries[i]);
				//System.out.println("aaaaaaaaa: "+all_queries[i]);
				jasperDesign_2.setQuery(query_2);
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign_2);
				JasperPrint jasperPrint_i = JasperFillManager.fillReport(
						jasperReport, null, connection);
				List pages = new ArrayList(jasperPrint_i.getPages());
				
				for (int count = 0; count < pages.size(); count++) {
					jasperPrint_1.addPage(j, (JRPrintPage) pages.get(count));
					j++;
				}
			}

			exportXLS(connection, jasperPrint_1);

			// JasperExportManager.exportReportToPdfFile(jasperPrint,
			// "temp.pdf");
		} catch (Exception ex) {
			String connectMsg = "Could not create the report "
					+ ex.getMessage() + " " + ex.getLocalizedMessage();
			System.out.println(connectMsg);
			ex.printStackTrace();
		}
	}

	public static void exportPDFReport(Connection connection) {
		try {
			JasperDesign jasperDesign_1 = JRXmlLoader.load(all_file_path[0]);
			JRDesignQuery query_1 = new JRDesignQuery();
			query_1.setText(all_queries[0]);
			jasperDesign_1.setQuery(query_1);
			JasperReport jasperReport_1 = JasperCompileManager.compileReport(jasperDesign_1);
			JasperPrint jasperPrint_1 = JasperFillManager.fillReport(jasperReport_1, null, connection);
			 JasperExportManager.exportReportToPdfFile(jasperPrint_1,file_path_to_export);
			 int x=0;
		} catch (Exception ex) {
			String connectMsg = "Could not create the report "
					+ ex.getMessage() + " " + ex.getLocalizedMessage();
			System.out.println(connectMsg);
		}
	}

	
	public static void exportXLS(Connection connection_, JasperPrint jasperPrint) {
		FileOutputStream output; 
		 
		try {
			output = new FileOutputStream(new File(file_path_to_export));
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);
			//exporter.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET,Integer.decode("65000"));
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.TRUE);
			exporter
					.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
					file_path_to_export);
			exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES,
					all_excel_sheet_names);
			// exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS,Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.OFFSET_X, new Integer(0));
			exporter.setParameter(JRXlsExporterParameter.OFFSET_Y, new Integer(0));
			exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER,
					Boolean.FALSE);
			exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
					new Boolean(true));
			exporter.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, new Boolean(false));
			exporter.exportReport();
			output.flush();
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void readQueries(String[] queries, String[] file_paths, String[] parameters)
	{
		
		try {
		for(int i=0;i<file_paths.length;i++)
		{
			
				BufferedReader file_reader=new BufferedReader(new FileReader(file_paths[i]));
				String file_line = "";
				queries[i]="";
				while((file_line=file_reader.readLine()) != null)
				{
					queries[i] += file_line+" ";
				}
				queries[i] = setQueryParameters(queries[i], parameters[i]);
				
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String setQueryParameters(String query, String parameters)
	{
		String[] parameters_value_type_pairs = parameters.split("#");
		String searchFor="?";
		 int len = searchFor.length();
		 //ArrayList par_indexes =new ArrayList();
		 
		    
		if(parameters_value_type_pairs!=null && parameters_value_type_pairs.length>0)
		{
			int start = -1;
			for(int i=0;i<parameters_value_type_pairs.length;i=i+2)
			{
				if(i < parameters_value_type_pairs.length-1)
				{
					if(parameters_value_type_pairs[i+1].equalsIgnoreCase("string"))
						parameters_value_type_pairs[i] = "'"+parameters_value_type_pairs[i]+"'";
					
						  start = query.indexOf(searchFor);
						  if(start!=-1)
							  query = replaceCharAt(query, start,parameters_value_type_pairs[i]);
									
					
				}
				
			}
		}
		return query;
	}
	public static String setQueryParameter(String query, String query_parameter)
	{
		 
		String searchFor="?";
		 int len = searchFor.length();
		 //ArrayList par_indexes =new ArrayList();
		 int start = 0;
		 start = query.indexOf(searchFor);
		  while(start != -1)
		  {
			  start = query.indexOf(searchFor);
			  if(start != -1)
				  //query = replaceCharAt(query, start,parameters_value_type_pairs[i]);
				  query = replaceCharAt(query, start,query_parameter);
		  }
		
		return query;
	}
	public static String replaceCharAt(String s, int pos, String c) {
		//int pos_int = pos.intValue();
		
		String temp  =s.substring(0,pos) + c+" " + s.substring(pos+1);
		   return  temp;
		}
}







