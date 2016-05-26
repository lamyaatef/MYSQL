/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.utilities;

import com.mobinil.sds.web.interfaces.InterfaceKey;
import java.io.File;
import org.apache.commons.fileupload.*;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



/**
 *
 * @author mabdelaal
 */

public class GetUploadedFile {

        
    public static File getFile(HashMap paramHashMap, String fileLocUnderWebInf) throws Exception {
        HttpServletRequest request = (HttpServletRequest) paramHashMap.get(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET);
        File savedFile = null;
         //DiskFileUpload upload = new DiskFileUpload();
        
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload  upload = new ServletFileUpload (factory);      
// Create a new file upload handler

          
                //List items = upload.parseRequest(request);
        List items =upload.parseRequest(request);
        
        String fileUniqueName = "";
        // fileLocUnderWebInf like /scm/upload/
        String baseDirectory = request.getRealPath(fileLocUnderWebInf)+System.getProperty("file.separator");        
        String fileNameOnClient = "";

        Iterator itr = items.iterator();
        while (itr.hasNext()) {
            FileItem item = (FileItem) itr.next();
            // check if the current item is a form field or an uploaded file
            if (item.isFormField()) {
                // get the name of the field
                String fieldName = item.getFieldName();
                // if it is name, we can set it in request to thank the user
                if (fieldName.equals("name")) {
                    request.setAttribute("msg",
                            "Thank You: " + item.getString());
                }
            } else {
                fileNameOnClient = item.getName();
                Utility.logger.debug("fileNameOnClient"+ fileNameOnClient);
                String fileExt = fileNameOnClient.substring(fileNameOnClient.lastIndexOf("."), fileNameOnClient.length());
                fileUniqueName = System.currentTimeMillis() + fileExt;
                savedFile = new File(baseDirectory+ fileUniqueName);
                Utility.logger.debug("file " + savedFile);
                item.write(savedFile);

            }
        }
        return savedFile;

    }
//    public static void main(String [] args){
//    String filepath = "C:\\sasdasd\\asdasd\\asdasd.ddfd";
//        System.out.println(filepath.substring(filepath.lastIndexOf("."), filepath.length()));
//    }
}
