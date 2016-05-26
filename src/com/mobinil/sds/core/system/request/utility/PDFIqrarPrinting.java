/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.request.utility;

/**
 *
 * @author Salma
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mobinil.sds.core.system.request.model.PosIqrarModel;

import java.util.Random;

public class PDFIqrarPrinting {

    public static void main(String[] args) {
    }

    public static String createPDFIqrar(String fileDirector, String imagePath, PosIqrarModel posModel, String issuingUser, String userSupervisor) {
        CommonUtil.getEbillArabicIniParamters().clear();
        CommonUtil.loadArabicIniParamters();
        String fileName = "";
        String fileSubName = "";

        try {
            String fileDir = fileDirector + fileDirector.charAt(fileDirector.length() - 14) /*
                     * "/"
                     */;
            java.util.Date dateNow = new java.util.Date();
            int imonth = dateNow.getMonth() + 1;
            int iyear = dateNow.getYear() + 1900;
            String strdate = (new StringBuffer("[")).append(dateNow.getDate()).append("-").append(imonth).append("-").append(iyear).append("]-").append(dateNow.getHours()).append(".").append(dateNow.getMinutes()).append(".").append(dateNow.getSeconds()).append(posModel.getPosCode()).append("_").toString();
            fileName = (new StringBuffer(String.valueOf(fileDir))).append(strdate).append("Iqrar-Printing").append(".pdf").toString();
            fileSubName = (new StringBuffer(String.valueOf(strdate))).append("Iqrar-Printing").append(".pdf").toString();
            OutputStream file = new FileOutputStream(new File(fileName));
            System.out.println("$$$$$$$$$$$$$$$$$ " + fileName);
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            document.open();

            String fontPath = "sds//arial.ttf";
            Font fontSizeSixBold = new Font(BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12, Font.NORMAL);



            PdfPTable tableHeaderTwo = new PdfPTable(2);
            tableHeaderTwo.getDefaultCell().setBorder(Rectangle.BOX);
            tableHeaderTwo.setWidthPercentage(100);

            PdfPCell cellOneHeaderTwo = null;
            cellOneHeaderTwo = setCellFormateAlignLeft(cellOneHeaderTwo, "Sim Serial#");
            tableHeaderTwo.addCell(cellOneHeaderTwo);

            PdfPCell cellTwoHeaderTwo = null;
            cellTwoHeaderTwo = setCellFormateAlignRight(cellTwoHeaderTwo, "Dial");
            tableHeaderTwo.addCell(cellTwoHeaderTwo);






            PdfPTable tableHeaderOne = new PdfPTable(1);
            tableHeaderOne.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            tableHeaderOne.setWidthPercentage(100);

            PdfPCell cellOneHeaderOne = null;
            cellOneHeaderOne = setCellFormateAlignLeft(cellOneHeaderOne, "Ex : " + issuingUser);
            tableHeaderOne.addCell(cellOneHeaderOne);

            PdfPCell cellThreeHeaderOne = null;
            cellThreeHeaderOne = setCellFormateAlignLeft(cellThreeHeaderOne, "FR : " + userSupervisor);
            tableHeaderOne.addCell(cellThreeHeaderOne);

            PdfPCell cellTwoHeaderOne = new PdfPCell(new Paragraph(""));
            cellTwoHeaderOne.addElement(tableHeaderTwo);
            tableHeaderOne.addCell(cellTwoHeaderOne);



            float[] columnSizes = {1f, 1.1f, 1f};
            PdfPTable tableHeaderMain = new PdfPTable(columnSizes);
            tableHeaderMain.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            tableHeaderMain.setWidthPercentage(85);

            PdfPCell cellThreeHeaderMain = null;


            cellThreeHeaderMain = new PdfPCell(new Paragraph(""));
            cellThreeHeaderMain.setBorder(Rectangle.NO_BORDER);
            cellThreeHeaderMain.addElement(tableHeaderOne);
            tableHeaderMain.addCell(cellThreeHeaderMain);

            String newline = System.getProperty("line.separator");
            Paragraph paragraph = new Paragraph(newline + newline + newline + (String) CommonUtil.getEbillArabicIniParamters().get("iqrartitle"), new Font(BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 14, Font.NORMAL));
            paragraph.setAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cellTwoHeaderMain = new PdfPCell(paragraph);
            cellTwoHeaderMain.setBorder(Rectangle.NO_BORDER);
            cellTwoHeaderMain.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTwoHeaderMain.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            tableHeaderMain.addCell(cellTwoHeaderMain);

            /// image
//            Image chartImage = Image.getInstance(imagePath+File.separator+"img"+File.separator+"main_logo_mobinil.gif");
            Image chartImage = Image.getInstance(imagePath + "/img/main_logo_mobinil.gif");
            chartImage.setBorder(0);
            PdfPCell cellOneHeaderMain = new PdfPCell(new Paragraph(""));
            cellOneHeaderMain.setBorder(Rectangle.NO_BORDER);
            cellOneHeaderMain.addElement(chartImage);
            tableHeaderMain.addCell(cellOneHeaderMain);


            document.add(tableHeaderMain);

            Paragraph paragraphser = new Paragraph((String) CommonUtil.getEbillArabicIniParamters().get("Ser#"), fontSizeSixBold);
            document.add(paragraphser);


            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            PdfPTable tableOneMain = new PdfPTable(1);
            tableOneMain.setWidthPercentage(85);

            tableOneMain.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableOneMain.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableOneMain.setSpacingBefore(10f);
            tableOneMain.setSpacingAfter(10f);


            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(95);

            PdfPCell cell = null;
            String name = "";
            if (posModel.getOwnerName() == null) {
                name = "";
            } else {
                name = posModel.getOwnerName();
            }
            cell = setCellFormate(cell, (String) CommonUtil.getEbillArabicIniParamters().get("iqratitlemain") + " : " + name);
            table.addCell(cell);

            String posNo = "";
            if (posModel.getOwnerIdNo() == null) {
                posNo = "";
            } else {
                posNo = posModel.getOwnerIdNo();
            }
            PdfPCell cellTwo = null;
            cellTwo = setCellFormate(cellTwo, (String) CommonUtil.getEbillArabicIniParamters().get("iqrarsecondtitle") + " : " + posNo);
            table.addCell(cellTwo);


            PdfPCell cellThree = null;
            cellThree = setCellFormate(cellThree, (String) CommonUtil.getEbillArabicIniParamters().get("iqrarthirdtitle") + " : " + "");
            table.addCell(cellThree);


            String posName = "";
            if (posModel.getPosName() == null) {
                posName = "";
            } else {
                posName = posModel.getPosName();
            }

            PdfPCell cellFour = null;
            cellFour = setCellFormate(cellFour, (String) CommonUtil.getEbillArabicIniParamters().get("iqrarfourthtitle") + " : " + posName);
            table.addCell(cellFour);


            String posCode = "";
            if (posModel.getPosCode() == null) {
                posCode = "";
            } else {
                posCode = posModel.getPosCode();
            }

            PdfPCell cellFive = null;
            cellFive = setCellFormate(cellFive, (String) CommonUtil.getEbillArabicIniParamters().get("iqrarfifthtitle") + " : " + posCode);
            table.addCell(cellFive);

            PdfPCell cellSix = null;
            cellSix = setCellFormate(cellSix, (String) CommonUtil.getEbillArabicIniParamters().get("iqrarsixthtitle") + " : " + posModel.getDistricName());
            table.addCell(cellSix);

            PdfPCell cellSeven = null;
            cellSeven = setCellFormate(cellSeven, (String) CommonUtil.getEbillArabicIniParamters().get("iqrarseventitle") + " : " + posModel.getAreaName());
            table.addCell(cellSeven);

            PdfPCell cellOneTableOneMain = new PdfPCell(new Paragraph(""));
            cellOneTableOneMain.addElement(table);
            tableOneMain.addCell(cellOneTableOneMain);

            document.add(tableOneMain);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            PdfPTable tableLabel = new PdfPTable(1);
            tableLabel.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            tableLabel.setWidthPercentage(28);

            Paragraph paragraphIqrar = new Paragraph((String) CommonUtil.getEbillArabicIniParamters().get("iqrarstate"), fontSizeSixBold);
            paragraphIqrar.setAlignment(Element.ALIGN_CENTER);
            PdfPCell cellLabel = new PdfPCell(paragraphIqrar);
            cellLabel.setBorder(Rectangle.NO_BORDER);
            cellLabel.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            tableLabel.addCell(cellLabel);

            document.add(tableLabel);

            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            PdfPTable tableTwoMain = new PdfPTable(1);
            tableTwoMain.setWidthPercentage(85);
            tableTwoMain.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableTwoMain.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableTwoMain.getDefaultCell().setBorder(PdfCell.BOX);
            tableTwoMain.setSpacingBefore(20f);
            tableTwoMain.setSpacingAfter(20f);

            PdfPTable tableTwo = new PdfPTable(2);
            tableTwo.setWidthPercentage(95);


            PdfPCell cellTableTwo = null;
            cellTableTwo = setCellFormate(cellTableTwo, (String) CommonUtil.getEbillArabicIniParamters().get("iqrartabletwoonetitle") + " : " + posModel.getStkNo());
            cellTableTwo.setColspan(2);
            tableTwo.addCell(cellTableTwo);

            PdfPCell cellTwoTableTwo = null;
            cellTwoTableTwo = setCellFormate(cellTwoTableTwo, (String) CommonUtil.getEbillArabicIniParamters().get("titlefive") + " : ", "");
            tableTwo.addCell(cellTwoTableTwo);

            PdfPCell cellThreeTableTwo = null;
            cellThreeTableTwo = setCellFormate(cellThreeTableTwo, (String) CommonUtil.getEbillArabicIniParamters().get("titethree") + " : ", "");
            tableTwo.addCell(cellThreeTableTwo);

            PdfPCell cellFourTableTwo = null;
            cellFourTableTwo = setCellFormate(cellFourTableTwo, (String) CommonUtil.getEbillArabicIniParamters().get("titlesix") + " : ", "");
            tableTwo.addCell(cellFourTableTwo);

            PdfPCell cellFiveTableTwo = null;
            cellFiveTableTwo = setCellFormate(cellFiveTableTwo, (String) CommonUtil.getEbillArabicIniParamters().get("titlefour") + " : ", "");
            tableTwo.addCell(cellFiveTableTwo);



            PdfPCell cellOneTableTwoMain = new PdfPCell(new Paragraph(""));
            cellOneTableTwoMain.addElement(tableTwo);
            tableTwoMain.addCell(cellOneTableTwoMain);

            document.add(tableTwoMain);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            PdfPTable tableData = new PdfPTable(1);
            tableData.setWidthPercentage(85);
            tableData.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableData.setSpacingBefore(10f);
            tableData.setSpacingAfter(10f);

            PdfPCell cellOneData = null;
            cellOneData = setCellFormate(cellOneData, (String) CommonUtil.getEbillArabicIniParamters().get("datalineone"));
            tableData.addCell(cellOneData);

            PdfPCell cellTwoData = null;
            cellTwoData = setCellFormate(cellTwoData, (String) CommonUtil.getEbillArabicIniParamters().get("datalinetwo"));
            tableData.addCell(cellTwoData);


            PdfPCell cellThreeData = null;
            cellThreeData = setCellFormate(cellThreeData, " -1 " + (String) CommonUtil.getEbillArabicIniParamters().get("datalinethree"));
            tableData.addCell(cellThreeData);

            PdfPCell cellFourData = null;
            cellFourData = setCellFormate(cellFourData, " -2 " + (String) CommonUtil.getEbillArabicIniParamters().get("datalinefour"));
            tableData.addCell(cellFourData);

            PdfPCell cellFiveData = null;
            cellFiveData = setCellFormate(cellFiveData, " -3 " + (String) CommonUtil.getEbillArabicIniParamters().get("datalinefive"));
            tableData.addCell(cellFiveData);

            PdfPCell cellSixData = null;
            cellSixData = setCellFormate(cellSixData, " -4 " + (String) CommonUtil.getEbillArabicIniParamters().get("datalinesix"));
            tableData.addCell(cellSixData);

            PdfPCell cellSevenData = null;
            cellSevenData = setCellFormate(cellSevenData, " -5 " + (String) CommonUtil.getEbillArabicIniParamters().get("datalineseven"));
            tableData.addCell(cellSevenData);

            PdfPCell cellEightData = null;
            cellEightData = setCellFormate(cellEightData, " -6 " + (String) CommonUtil.getEbillArabicIniParamters().get("datalineeight"));
            tableData.addCell(cellEightData);

            document.add(tableData);

            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            PdfPTable tableEndData = new PdfPTable(1);
            tableEndData.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            tableEndData.setWidthPercentage(100);


            PdfPCell cellOneEndData = null;
            cellOneEndData = setCellFormate(cellOneEndData, (String) CommonUtil.getEbillArabicIniParamters().get("dataendone") + " : ");
            tableEndData.addCell(cellOneEndData);

            PdfPCell cellTwoEndData = null;
            cellTwoEndData = setCellFormate(cellTwoEndData, (String) CommonUtil.getEbillArabicIniParamters().get("dataendtwo") + " : ");
            tableEndData.addCell(cellTwoEndData);


            PdfPCell cellThreeEndData = null;
            cellThreeEndData = setCellFormate(cellThreeEndData, (String) CommonUtil.getEbillArabicIniParamters().get("dataendthree") + " : ");
            tableEndData.addCell(cellThreeEndData);



            PdfPTable tableMainEndData = new PdfPTable(2);
            tableMainEndData.setWidthPercentage(85);
            tableMainEndData.getDefaultCell().setBorder(PdfCell.BOX);
            tableMainEndData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableMainEndData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableMainEndData.setSpacingBefore(10f);
            tableMainEndData.setSpacingAfter(10f);



            PdfPCell cellTwoMainEndData = null;
            cellTwoMainEndData = setCellFormateWithBorder(cellTwoMainEndData, "                              " + (String) CommonUtil.getEbillArabicIniParamters().get("dataendfour"));
            tableMainEndData.addCell(cellTwoMainEndData);

            PdfPCell cellOneMainEndData = new PdfPCell(new Paragraph(""));
            cellOneMainEndData.addElement(tableEndData);
            tableMainEndData.addCell(cellOneMainEndData);

            document.add(tableMainEndData);

            document.close();
            file.close();



        } catch (Exception e) {

            e.printStackTrace();
        }
        return fileSubName;
    }

    public static PdfPCell setCellFormate(PdfPCell cell, String cellContent, String chunkString) throws IOException, DocumentException {
        String fontPath = "sds//arial.ttf";
        Font fontSizeSixBold = new Font(BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 11, Font.NORMAL);
        Paragraph paragraph = new Paragraph(chunkString + cellContent, fontSizeSixBold);
        paragraph.setAlignment(Element.ALIGN_RIGHT);

        // Chunk chunk = new Chunk(chunkString);
        //chunk.setUnderline(0.0f, 0f);
        //paragraph.add(chunk);

        cell = new PdfPCell(paragraph);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setBorderWidthBottom(0f);
        cell.setBorderWidthLeft(0f);
        cell.setBorderWidthRight(0f);
        cell.setBorderWidthTop(0f);

        cell.setPadding(7.0f);

        return cell;
    }

    public static PdfPCell setCellFormateWithBorder(PdfPCell cell, String cellContent) throws IOException, DocumentException {
        String fontPath = "sds//arial.ttf";
        Font fontSizeSixBold = new Font(BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10, Font.NORMAL);
        Paragraph paragraph = new Paragraph(cellContent, fontSizeSixBold);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        cell = new PdfPCell(paragraph);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setPadding(6.0f);

        return cell;
    }

    public static PdfPCell setCellFormateAlignLeft(PdfPCell cell, String cellContent) throws IOException, DocumentException {
        String fontPath = "sds//arial.ttf";
        Font fontSizeSixBold = new Font(BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 8, Font.NORMAL);
        Paragraph paragraph = new Paragraph(cellContent, fontSizeSixBold);
        paragraph.setAlignment(Element.ALIGN_LEFT);

        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        cell.setPadding(6.0f);

        return cell;
    }

    public static PdfPCell setCellFormateAlignRight(PdfPCell cell, String cellContent) throws IOException, DocumentException {
        String fontPath = "sds//arial.ttf";
        Font fontSizeSixBold = new Font(BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 8, Font.NORMAL);
        Paragraph paragraph = new Paragraph(cellContent, fontSizeSixBold);
        paragraph.setAlignment(Element.ALIGN_TOP);

        cell = new PdfPCell(paragraph);
        cell.setFixedHeight(60f);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(6.0f);

        return cell;
    }

    public static PdfPCell setCellFormate(PdfPCell cell, String cellContent) throws IOException, DocumentException {
        String fontPath = "sds//arial.ttf";
        Font fontSizeSixBold = new Font(BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10, Font.NORMAL);
        Paragraph paragraph = new Paragraph(cellContent, fontSizeSixBold);
        paragraph.setAlignment(Element.ALIGN_RIGHT);

        cell = new PdfPCell(paragraph);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(6.0f);

        return cell;
    }

    public static Paragraph setParagraphFormate(String cellContent) throws IOException, DocumentException {
        String fontPath = "sds//arial.ttf";
        Font fontSizeSixBold = new Font(BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 11, Font.NORMAL);
        Paragraph paragraph = new Paragraph(cellContent, fontSizeSixBold);
        paragraph.setAlignment(Element.ALIGN_RIGHT);

        return paragraph;
    }
}
