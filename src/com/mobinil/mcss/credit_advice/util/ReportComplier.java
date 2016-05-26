package com.mobinil.mcss.credit_advice.util;

import java.io.File;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;


public class ReportComplier
{

	public ReportComplier()
	{
		super();
	}

	public static void main(String arg[])
	{
		for (int i = 0; i < arg.length; i++)
		{
			String string = arg[i];
			try
			{
				compileAllFiles(string);
			}
			catch (JRException e)
			{

				e.printStackTrace();
			}
		}

	}

	public static void compileAllFiles(String directory) throws JRException
	{
		File reportDirectoryFile = new File(directory);
		File[] files = reportDirectoryFile.listFiles();
		String reportDirectory = null;
		for (int i = 0; i < files.length; i++)
		{
			if (files[i].isDirectory())
			{
				reportDirectory = files[i].getPath();
				System.out.println("%%%%%%%%%" + reportDirectory);
				compileAllFiles(reportDirectory);
			}
			else
			{
				String name = files[i].getName();
				if (name.endsWith(".jrxml") || name.endsWith(".xml"))
				{
					System.out.println("----------" + directory + "/" + files[i].getName());
					try
					{
						JasperCompileManager.compileReportToFile(directory + "/" + files[i].getName());
					}
					catch (JRException e)
					{

						e.printStackTrace();
					}
					System.err.println(directory + "\\" + files[i].getName() + "  ************************");
				}
			}

		}

	}
}
