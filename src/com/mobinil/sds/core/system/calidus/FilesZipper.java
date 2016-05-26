package com.mobinil.sds.core.system.calidus;

import java.io.*;


import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import java.util.ArrayList;
import java.util.List;


public class FilesZipper
{
    List<String> fileList;
    public static String OUTPUT_ZIP_FILE = "/home/sand/ZipUploads/CalidusZip";//"http://localhost:8080/home/sand/Downloads/SDSDCMExport/src/CalidusZip";
    public static final String SOURCE_FOLDER = "/home/sand/Downloads/SDSDCMExport/src/zip";
	
    public FilesZipper(){
	fileList = new ArrayList<String>();
    }
	
    /*public static void main( String[] args )
    {
    	FilesZipper appZip = new FilesZipper();
    	appZip.generateFileList(new File(SOURCE_FOLDER));
    	appZip.zipIt(OUTPUT_ZIP_FILE);
    }*/
    
    public void zipIt(String zipFile){

     System.out.println("Zip It : "+zipFile);
        byte[] buffer = new byte[1024];
    	
     try{
         System.out.println("before file out stream");
    		
    	FileOutputStream fos = new FileOutputStream(zipFile);
        System.out.println("after file out stream");
    	ZipOutputStream zos = new ZipOutputStream(fos);
    		
   
    		
    	for(String file : this.fileList){
    			
                System.out.println("in file list loop");
    		System.out.println("File Added : " + file);
    		ZipEntry ze= new ZipEntry(file);
        	zos.putNextEntry(ze);
               
        	FileInputStream in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
       	   
        	int len;
        	while ((len = in.read(buffer)) > 0) {
        		zos.write(buffer, 0, len);
        	}
               
        	in.close();
                zos.closeEntry();
    	}
    		
    	//zos.closeEntry();
    	//remember close it
    	zos.close();
          
    }catch(IOException ex){
       ex.printStackTrace();   
    }
   }
    
    public void generateFileList(File node){

    	//add file only
        System.out.println("in generate file list : "+node);
	if(node.isFile()){
            System.out.println("if is file");
		fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
	}
		
	if(node.isDirectory()){
            System.out.println("if is directory");
		String[] subNote = node.list();
		for(String filename : subNote){
			generateFileList(new File(node, filename));
		}
	}
 
    }

   
    private String generateZipEntry(String file){
        System.out.println("in generate zip entry : "+file);
    	return file.substring(SOURCE_FOLDER.length()+1, file.length());
    }
}