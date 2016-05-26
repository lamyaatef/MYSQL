package com.mobinil.sds.web.controller;

import java.io.*;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.InterfaceKey;

/**
 * Servlet implementation class UploadingFileServlet
 */
public class UploadingFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadingFileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doOperation(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doOperation(request, response);
	}

	public void doOperation(HttpServletRequest request,
			HttpServletResponse response) {
		/* example of preparing parameters
dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, unique_file+".pdf");
dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "/cam/camMemosFiles/generated PDF files/");
			*/
		HttpSession session = request.getSession();
		HashMap dataHashMap = (HashMap) session.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
		String file_name = (String) dataHashMap.get(InterfaceKey.EXPORT_FILE_PATH); // complete file path
		String module_subPath = (String) dataHashMap.get(InterfaceKey.MODULE_SUB_PATH); // complete file path

		String fileType = file_name.substring(file_name.indexOf(".") + 1,file_name.length());
		String file_path = request.getRealPath(module_subPath + file_name);
                if (fileType.trim().equalsIgnoreCase("txt")) {
			response.setContentType("text/plain");
		} else if (fileType.trim().equalsIgnoreCase("doc")) {
			response.setContentType("application/msword");
		} else if (fileType.trim().equalsIgnoreCase("xls")) {
			response.setContentType("application/vnd.ms-excel");
		} else if (fileType.trim().equalsIgnoreCase("pdf")) {
			response.setContentType("application/pdf");
		} else if (fileType.trim().equalsIgnoreCase("ppt")) {
			response.setContentType("application/ppt");
		} else {
			response.setContentType("application/octet-stream");
		}

		response.setHeader("Content-Disposition", "attachment; filename=\""+ file_name + "\"");
		response.setHeader("cache-control", "no-cache");

		try {
			InputStream isStream = null;
			ServletOutputStream sosStream = null;
			File file = new File(file_path);
			isStream = new FileInputStream(file);
			sosStream = response.getOutputStream();
			int ibit = 256; 
			while ((ibit) >= 0) { 
				 ibit = isStream.read();
				 sosStream.write(ibit); 
				 }			 
			 sosStream.flush();
			 sosStream.close();
			 isStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.removeAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	}

}
