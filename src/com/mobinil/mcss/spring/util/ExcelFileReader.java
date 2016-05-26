/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.spring.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author Gado
 */
public class ExcelFileReader {

    public static <T> List<T> ReadExcel(CommonsMultipartFile file, Class<T> clazz) throws IOException, InvalidFormatException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {

        System.out.println("read excel");
        Workbook wb = org.apache.poi.ss.usermodel.WorkbookFactory.create(file.getInputStream());
        Field f[] = clazz.getDeclaredFields();

        List<T> result = new ArrayList<T>();
        Sheet sheet = wb.getSheetAt(0);
        int lastRowNumber = sheet.getLastRowNum();
        for (int row = 1; row < lastRowNumber; row++) {
            System.out.println("########### Row " + row);
            Object singleObject = clazz.newInstance();
            for (Field fs : f) {
                Annotation fieldannos[] = fs.getAnnotations();
                for (Annotation fieldAnnotation : fieldannos) {
                    if (fieldAnnotation.annotationType().equals(GExcel.class)) {
                        GExcel a = (GExcel) fieldAnnotation;
                        int excelColumn = a.excelColumn();
                        char[] charsOfMethod = new char[1];
                        charsOfMethod[0] = fs.getName().charAt(0);
                        String FirstChar = new String(charsOfMethod);
                        String upperFirstChar = FirstChar.toUpperCase();
                        System.out.println("method name  =" + "set" + upperFirstChar + fs.getName().substring(1));

                        Method method = clazz.getMethod("set" + upperFirstChar + fs.getName().substring(1), fs.getType());
                        Class parameter[] = method.getParameterTypes();

                        for (int i = 0; i < parameter.length; i++) {
                            System.out.println("parameter " + i + " type = " + parameter[i].getSimpleName());
                        }

                        System.out.println("excelColumn = " + excelColumn);
                        System.out.println("row = " + row);
                        //    System.out.println("value = " + sheet.getRow(row).getCell(excelColumn).getStringCellValue());
                        try {

                            if (sheet.getRow(row).getCell(excelColumn).getCellType() == 1) {
                                System.out.println(sheet.getRow(row).getCell(excelColumn).getStringCellValue());
                                method.invoke(singleObject, parameter[0].cast(sheet.getRow(row).getCell(excelColumn).getStringCellValue()));
                            }
                            if (sheet.getRow(row).getCell(excelColumn).getCellType() == 0) {

                                System.out.print(sheet.getRow(row).getCell(excelColumn).getNumericCellValue());
                                Integer value = (int) sheet.getRow(row).getCell(excelColumn).getNumericCellValue();
                                method.invoke(singleObject,
                                        parameter[0].cast(value));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                    result.add(clazz.cast(singleObject));
                    System.out.println("######### end row");
                }

            }


        }
        return result;
    }
}
