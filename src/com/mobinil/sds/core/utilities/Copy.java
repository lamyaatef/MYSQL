/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.utilities;
import java.io.*;
import java.nio.channels.*;
/**
 *
 * @author mabdelaal
 */

/**
 * Sample code that copies files in a similar manner to the cp(1) program.
 */

public class Copy {
	public static void main(String [] arf)
        {
        
            System.out.println(new File("sds/pos_target_files").getAbsolutePath());
        }
	public void copyDirectory(File srcPath, File dstPath) throws IOException{
		if (srcPath.isDirectory())
		{
			if (!dstPath.exists())
			{
				dstPath.mkdir();
			}

			String files[] = srcPath.list();
			for(int i = 0; i < files.length; i++)
			{
				copyDirectory(new File(srcPath, files[i]), new File(dstPath, files[i]));
			}
		}
		else
		{
			if(!srcPath.exists())
			{
				System.out.println("File or directory does not exist.");
				System.exit(0);
			}
			else
			{
				InputStream in = new FileInputStream(srcPath);
		        OutputStream out = new FileOutputStream(dstPath);
    
				// Transfer bytes from in to out
		        byte[] buf = new byte[1024];
				int len;
		        while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
		        out.close();
			}
		}
//		System.out.println("Directory copied.");
	}
}