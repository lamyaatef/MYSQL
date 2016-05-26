/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

// make a method to insert a static text, textfield which take a node to append to
package com.mobinil.sds.core.utilities.jaspertest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author amr
 */
public class JRXMLDesignerManegerForXLS
{

    private static String file_path;
    private static String sql_query;

    private static DocumentBuilderFactory factory;
    private static DocumentBuilder builder;
    private static Vector all_report_column; // carry ReportColumn objects
    private static int rep_width;

    private static String variables_extenstion = "__sum var";
    private static String title_bg_color = "#FF9900";

    private static boolean isEtopup;

    public void designJRXMLFile(String file_path, String sql_query,
            Vector all_report_column, String output_file_name, boolean etopup)
    {
        isEtopup = etopup;
        if (etopup)
        {
            title_bg_color = "#ffffff";
        }
        JRXMLDesignerManegerForXLS.file_path = file_path;
        JRXMLDesignerManegerForXLS.sql_query = sql_query;
        JRXMLDesignerManegerForXLS.all_report_column = all_report_column;
        rep_width = 1400;

        System.out.println("output_file_name=" + output_file_name);
        parseXMLFile(output_file_name);
    }

    // documents shall be cleaned first from fields and details

    private static void parseXMLFile(String output_file_name)
    {
        try
        {
            factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            builder = factory.newDocumentBuilder();
            File xml_file = new File(file_path);
            File resulted_xml_file = new File(output_file_name);

            Document document = builder.parse(xml_file);

            Element root = document.getDocumentElement();

            // insert title logo image
            // insertLogoImage(document, logo_image_path);

            // insert sql query
            replaceValue(document, "queryString", sql_query);
            // insert variables
            // prepareTitleStaticTexts(document);
            // insert fields
            Vector fields_to_create_variables_on = new Vector();
            Node element = getNode(document, "variable");
            if (element != null)
            {
                for (int j = 0; j < all_report_column.size(); j++)
                {
                    ReportColumn report_column = (ReportColumn) all_report_column.get(j);
                    Element new_field_element = document.createElement("field");
                    new_field_element.setAttribute("name", report_column.getDB_field_name());
                    String filed_db_type = report_column.getDB_field_dataType();
                    new_field_element.setAttribute("class", report_column.getDB_field_dataType());
                    if (JRXMLDesignerManeger.isNumberType(filed_db_type))
                        fields_to_create_variables_on.add(new_field_element);
                    root.insertBefore(new_field_element, element);

                }
            }
            // insert field sum variables

            if (fields_to_create_variables_on.size() > 0 && !isEtopup)
                insertFieldsSumVariables(document, fields_to_create_variables_on, root);
            // insert column header
            Node columnHedaer_band = getNode(document, "columnHeader");
            prepareColumnHeaderBandStaticText(document, columnHedaer_band.getFirstChild());
            // insert detail
            // insertDetail(document, root);
            Node detail_band = getNode(document, "detail");
            prepareDetailTextField(document, detail_band.getFirstChild());
            // prepare footer content
            // prepareFooterStaticText(document);

            // construct the "do nothing" transformation
            Transformer t = TransformerFactory.newInstance().newTransformer();
            // set output properties to get a DOCTYPE node

            t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://jasperreports.sourceforge.net/dtds/jasperreport.dtd");
            t.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "//JasperReports//DTD Report Design//EN");
            // set indentation
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty(OutputKeys.METHOD, "xml");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            // apply the "do nothing" transformation and send the output to a
            // file
            DOMSource dom_source = new DOMSource(document);
            FileOutputStream out = new FileOutputStream(resulted_xml_file);
            StreamResult stream_res = new StreamResult(out);
            t.transform(dom_source, stream_res);

        } catch (Exception ex)
        {
            Logger.getLogger(JRXMLDesignerManegerForXLS.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    private static void replaceValue(Document doc, String tagName,
            String replaceValue)
    {
        NodeList nodeList = doc.getElementsByTagName(tagName);
        Node node = nodeList.item(0);
        node.getFirstChild().setNodeValue(replaceValue);
    }

    private static Node getNode(Document doc, String tagName)
    {
        NodeList nodeList = doc.getElementsByTagName(tagName);
        return nodeList.item(0);
        // node.getFirstChild().setNodeValue(replaceValue);
    }

    private static Node insertColumnHeader(Document document, Node root)
    {
        Node element = getNode(document, "detail");
        if (element != null)
        {
            Node header_column = prepareColumnHeaderBand(document); // prpare
                                                                    // band
                                                                    // contents
                                                                    // of column
                                                                    // header
            root.insertBefore(header_column, element);
            return header_column;
        }
        return null;
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////// COLUMN HEADER
    // /////////////////////////////////////////////////////////////////////////////////////////////////
    private static Node prepareColumnHeaderBand(Document document)
    {

        Element column_header_element = document.createElement("columnHeader");

        Element column_header_band_element = document.createElement("band");
        column_header_band_element.setAttribute("height", "20");
        // column_header_band_element.setAttribute("height", "50");

        // column_header_band_element.setAttribute("isSplitAllowed", "true");
        // column_header_band_element.setAttribute("isSplitAllowed", "false");

        Element column_header_band_rectangle_element = document.createElement("rectangle");
        column_header_band_rectangle_element.setAttribute("radius", "0");
        Element column_header_report_element = document.createElement("reportElement");
        column_header_report_element.setAttribute("backcolor", title_bg_color);
        column_header_report_element.setAttribute("forecolor", "#ffffff");
        column_header_report_element.setAttribute("height", "17");
        column_header_report_element.setAttribute("isPrintInFirstWholeBand", "false");
        column_header_report_element.setAttribute("isPrintRepeatedValues", "true");
        column_header_report_element.setAttribute("isPrintWhenDetailOverflows", "false");
        column_header_report_element.setAttribute("isRemoveLineWhenBlank", "false");
        column_header_report_element.setAttribute("key", "element-22");
        column_header_report_element.setAttribute("mode", "Opaque");
        column_header_report_element.setAttribute("positionType", "FixRelativeToTop");
        column_header_report_element.setAttribute("stretchType", "NoStretch");
        column_header_report_element.setAttribute("width", rep_width + "");
        column_header_report_element.setAttribute("x", "1");
        column_header_report_element.setAttribute("y", "1");
        Element graphic_element = document.createElement("graphicElement");
        graphic_element.setAttribute("pen", "Thin");
        graphic_element.setAttribute("stretchType", "NoStretch");

        column_header_element.appendChild(column_header_band_element);
        column_header_band_element.appendChild(column_header_band_rectangle_element);
        column_header_band_rectangle_element.appendChild(column_header_report_element);
        column_header_band_rectangle_element.appendChild(graphic_element);
        // prepare column header band
        prepareColumnHeaderBandStaticText(document, column_header_band_element);
        return column_header_element;
    }

    private static int cell_width = 300;

    private static void prepareColumnHeaderBandStaticText(Document document,
            Node header_band)
    {
        // int cell_width = rep_width/(all_report_column.size()+1);
        // int cell_width =40;
        int width = 2 * cell_width;
        int x_position = 0;
        for (int j = 0; j < all_report_column.size(); j++)
        {
            ReportColumn report_column = (ReportColumn) all_report_column.get(j);

            Element new_static_text_element = document.createElement("staticText");
            Element new_static_text_report_element = document.createElement("reportElement");
            new_static_text_report_element.setAttribute("backcolor", title_bg_color);
            // new_static_text_report_element.setAttribute("forecolor",
            // title_forground_color);
            new_static_text_report_element.setAttribute("height", "16");
            new_static_text_report_element.setAttribute("isPrintInFirstWholeBand", "false");
            new_static_text_report_element.setAttribute("isPrintRepeatedValues", "true");
            new_static_text_report_element.setAttribute("isPrintWhenDetailOverflows", "false");
            new_static_text_report_element.setAttribute("isRemoveLineWhenBlank", "false");
            new_static_text_report_element.setAttribute("key", "element-90");
            new_static_text_report_element.setAttribute("mode", "Opaque");
            new_static_text_report_element.setAttribute("positionType", "FixRelativeToTop");
            new_static_text_report_element.setAttribute("stretchType", "NoStretch");

            if (j == 0)
            {
                width = cell_width;
                new_static_text_report_element.setAttribute("width", width + "");
            } else
            {
                width = cell_width;
                new_static_text_report_element.setAttribute("width", width + "");
            }

            new_static_text_report_element.setAttribute("x", x_position + "");
            x_position += width;
            new_static_text_report_element.setAttribute("y", "1");

            Element new_static_text_box_element = document.createElement("box");
            new_static_text_box_element.setAttribute("bottomBorder", "Thin");
            new_static_text_box_element.setAttribute("bottomBorderColor", "#000000");
            new_static_text_box_element.setAttribute("leftBorder", "Thin");
            new_static_text_box_element.setAttribute("leftBorderColor", "#000000");
            new_static_text_box_element.setAttribute("leftPadding", "2");
            new_static_text_box_element.setAttribute("rightBorder", "Thin");
            new_static_text_box_element.setAttribute("rightBorderColor", "#000000");
            new_static_text_box_element.setAttribute("rightPadding", "2");
            new_static_text_box_element.setAttribute("topBorder", "Thin");
            new_static_text_box_element.setAttribute("topBorderColor", "#000000");

            Element new_static_text_textElement = document.createElement("textElement");
            Element new_static_text_font_element = document.createElement("font");
            
           
            //new_static_text_font_element.setAttribute("fontName","Times New Roman");
            
            new_static_text_font_element.setAttribute("size", "10");

            // CDATASection new_static_text_text_element =
            // document.createCDATASection("text");
            // Impl xx = (CDATASectionImpl)new_static_text_text_element;
            // new_static_text_text_element.appendChild(document.createElement("![CDATA["
            // + report_column.getHeader_name() + "]]"));
            // new_static_text_text_element.setTextContent(report_column.getHeader_name()
            // );
            // new_static_text_text_element.setNodeValue("s");

            Element new_static_text_text_element = document.createElement("text");
            CDATASection new_static_text_text_element_section = document.createCDATASection(report_column.getHeader_name());
            new_static_text_text_element.appendChild(new_static_text_text_element_section);

            new_static_text_textElement.appendChild(new_static_text_font_element);

            new_static_text_element.appendChild(new_static_text_report_element);
            new_static_text_element.appendChild(new_static_text_box_element);
            new_static_text_element.appendChild(new_static_text_textElement);
            new_static_text_element.appendChild(new_static_text_text_element);
            header_band.appendChild(new_static_text_element);

        }
        // return header_band;
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////// DETAIL
    // /////////////////////////////////////////////////////////////////////////////////////////////////

    private static Node insertDetail(Document document, Node root)
    {
        Node element = getNode(document, "columnFooter");
        if (element != null)
        {
            Node detail = prepareDetailsBand(document); // prpare band contents
                                                        // of column header
            root.insertBefore(detail, element);
            return detail;
        }
        return null;
    }

    private static Node prepareDetailsBand(Document document)
    {
        Element detail_element = document.createElement("detail");
        Element detail_band_element = document.createElement("band");
        detail_band_element.setAttribute("height", "19");
        // detail_band_element.setAttribute("isSplitAllowed", "true");
        /*
         * Element detail_band_line = document.createElement("line");
         * detail_band_line.setAttribute("direction", "TopDown");
         */
        Element detail_report_element = document.createElement("reportElement");
        detail_report_element.setAttribute("x", "0");
        detail_report_element.setAttribute("y", "17");
        detail_report_element.setAttribute("width", rep_width + "");
        detail_report_element.setAttribute("height", "0");
        detail_report_element.setAttribute("forecolor", "#808080");
        detail_report_element.setAttribute("key", "line");
        detail_report_element.setAttribute("positionType", "FixRelativeToBottom");
        /*
         * Element graphic_element = document.createElement("graphicElement");
         * graphic_element.setAttribute("stretchType", "NoStretch");
         * graphic_element.setAttribute("pen", "Thin");
         */

        detail_element.appendChild(detail_band_element);
        // detail_band_element.appendChild(detail_band_line);
        detail_band_element.appendChild(detail_report_element);
        // detail_band_line.appendChild(graphic_element);

        // prepare detail band
        prepareDetailTextField(document, detail_band_element);
        return detail_element;
    }

    private static void prepareDetailTextField(Document document,
            Node detail_band)
    {

        Node column_footer_element = getNode(document, "columnFooter").getFirstChild();
        // int cell_width = rep_width/(all_report_column.size()+1);
        // int cell_width = 40;
        int width = 2 * cell_width;
        int x_position = 0;
        String filed_db_type = "";
        for (int j = 0; j < all_report_column.size(); j++)
        {
            ReportColumn report_column = (ReportColumn) all_report_column.get(j);
            filed_db_type = report_column.getDB_field_dataType();

            Element new_textField_element = document.createElement("textField");
            new_textField_element.setAttribute("isStretchWithOverflow", "false");

            new_textField_element.setAttribute("isBlankWhenNull", "true");
            new_textField_element.setAttribute("evaluationTime", "Now");
            new_textField_element.setAttribute("hyperlinkType", "None");
            new_textField_element.setAttribute("hyperlinkTarget", "Self");

            Element new_textField_report_element = document.createElement("reportElement");
            new_textField_report_element.setAttribute("isPrintInFirstWholeBand", "false");
            new_textField_report_element.setAttribute("isPrintRepeatedValues", "true");
            new_textField_report_element.setAttribute("isPrintWhenDetailOverflows", "true");
            new_textField_report_element.setAttribute("isRemoveLineWhenBlank", "true");
            new_textField_report_element.setAttribute("key", "textField");
            // new_textField_report_element.setAttribute("mode", "Opaque");
            new_textField_report_element.setAttribute("positionType", "FixRelativeToTop");
            new_textField_report_element.setAttribute("stretchType", "RelativeToTallestObject");
            new_textField_report_element.setAttribute("height", "15");

            width = cell_width;
            if (j == 0)
            {

                new_textField_report_element.setAttribute("backcolor", title_bg_color);
                new_textField_report_element.setAttribute("width", width + "");
            } else
            {

                new_textField_report_element.setAttribute("width", width + "");
            }

            new_textField_report_element.setAttribute("x", x_position + "");
            new_textField_report_element.setAttribute("y", "1");

            Element new_textField_box_element = document.createElement("box");

            new_textField_box_element.setAttribute("bottomBorder", "None");
            new_textField_box_element.setAttribute("leftBorder", "None");
            new_textField_box_element.setAttribute("rightBorder", "None");
            new_textField_box_element.setAttribute("topBorder", "None");

            new_textField_box_element.setAttribute("bottomBorderColor", "#000000");
            new_textField_box_element.setAttribute("topBorderColor", "#000000");

            new_textField_box_element.setAttribute("leftBorderColor", "#000000");
            new_textField_box_element.setAttribute("rightBorderColor", "#000000");

            new_textField_box_element.setAttribute("leftPadding", "2");
            new_textField_box_element.setAttribute("rightPadding", "2");

            Element new_textField_textElement = document.createElement("textElement");
            Element new_textField_font_element = document.createElement("font");

            //new_textField_font_element.setAttribute("fontName","Times New Roman");
            new_textField_font_element.setAttribute("size", "10");
            Element new_textField_expression_element = document.createElement("textFieldExpression");
            new_textField_expression_element.setAttribute("class", report_column.getDB_field_dataType());
            CDATASection new_textField_expression_element_cdata = document.createCDATASection("$F{"
                    + report_column.getDB_field_name() + "}");
            new_textField_expression_element.appendChild(new_textField_expression_element_cdata);

            new_textField_textElement.appendChild(new_textField_font_element);

            System.out.println("J=" + j);
            System.out.println("filed_db_type=" + filed_db_type);
            if (JRXMLDesignerManeger.isNumberType(filed_db_type) && j != 0)
            {
                new_textField_element.setAttribute("pattern", "###,###,##0.0##");
                new_textField_textElement.setAttribute("textAlignment", "Right");
            } else
            {
                new_textField_element.setAttribute("pattern", "");
            }

            new_textField_element.appendChild(new_textField_report_element);
            new_textField_element.appendChild(new_textField_box_element);
            new_textField_element.appendChild(new_textField_textElement);
            new_textField_element.appendChild(new_textField_expression_element);
            detail_band.appendChild(new_textField_element);

            // insert column footer text field
            if (!isEtopup)
                insertColumnFooterTextField(document, column_footer_element, x_position, width, report_column.getDB_field_dataType(), report_column.getDB_field_name());

            x_position += width;
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////// Image
    // /////////////////////////////////////////////////////////////////////////////////////////////////

    public static void insertLogoImage(Document document, String image_path)
    {
        NodeList nodeList = document.getElementsByTagName("title");
        Node title_band = null;
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node n = nodeList.item(i);
            if (n.getFirstChild().getNodeName().equalsIgnoreCase("band"))
            {
                title_band = n.getFirstChild();
            }
        }

        Element image_element = document.createElement("image");
        // there exist default attributes inserted for that element
        image_element.setAttribute("evaluationTime", "Now");
        image_element.setAttribute("hyperlinkType", "None");
        image_element.setAttribute("hyperlinkTarget", "Self");

        Element image_report_element = document.createElement("reportElement");
        image_report_element.setAttribute("x", "1");
        image_report_element.setAttribute("y", "1");
        image_report_element.setAttribute("width", "84");
        image_report_element.setAttribute("height", "21");
        image_report_element.setAttribute("key", "image-1");

        Element image_box_element = document.createElement("box");
        image_box_element.setAttribute("topBorder", "None");
        image_box_element.setAttribute("topBorderColor", "#000000");
        image_box_element.setAttribute("leftBorder", "None");
        image_box_element.setAttribute("leftBorderColor", "#000000");
        image_box_element.setAttribute("rightBorder", "None");

        Element image_graphic_element = document.createElement("graphicElement");
        image_graphic_element.setAttribute("stretchType", "NoStretch");

        Element image_imageExpression_element = document.createElement("imageExpression");
        image_imageExpression_element.setAttribute("class", "java.lang.String");
        CDATASection image_data = document.createCDATASection(image_path);
        image_imageExpression_element.appendChild(image_data);

        image_element.appendChild(image_report_element);
        image_element.appendChild(image_box_element);
        image_element.appendChild(image_graphic_element);
        image_element.appendChild(image_imageExpression_element);

        title_band.appendChild(image_element);

    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////// Variables
    // /////////////////////////////////////////////////////////////////////////////////////////////////
    // public static void prepareTitleStaticTexts(Document document) {
    // NodeList nodeList = document.getElementsByTagName("variable");
    // for(int i=0;i<nodeList.getLength();i++)
    // {
    // Node variable = nodeList.item(i);
    // Element var = (Element)variable;
    //
    // Element variable_expression =
    // document.createElement("initialValueExpression");
    // CDATASection expression_data = document.createCDATASection("");
    // if(var.getAttribute("name").equalsIgnoreCase("to_person"))
    // expression_data.setData("new String(\"dd\")");
    // else if(var.getAttribute("name").equalsIgnoreCase("from_person"))
    // expression_data.setData("new String(\"dd\")");
    // else if(var.getAttribute("name").equalsIgnoreCase("cc"))
    // expression_data.setData("new String(\"dd\")");
    // else if(var.getAttribute("name").equalsIgnoreCase("subject"))
    // expression_data.setData("new String(\"dd\")");
    //
    //
    // variable_expression.appendChild(expression_data);
    // var.appendChild(variable_expression);
    // }
    // }

    // /////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////// Prepare Footer
    // /////////////////////////////////////////////////////////////////////////////////////////////////

    // /////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////// COLUMN FOOTER
    // /////////////////////////////////////////////////////////////////////////////////////////////////
    public static void insertFieldsSumVariables(Document document,
            Vector fields_to_create_variables_on, Node root)
    {
        Node variable_element = getNode(document, "variable"); // first variable
                                                               // element
        for (int i = 0; i < fields_to_create_variables_on.size(); i++)
        {
            Element field_element = (Element) fields_to_create_variables_on.get(i);
            Element new_variable_element = document.createElement("variable");
            new_variable_element.setAttribute("name", field_element.getAttribute("name")
                    + variables_extenstion);
            new_variable_element.setAttribute("class", field_element.getAttribute("class"));
            new_variable_element.setAttribute("resetType", "Report");
            new_variable_element.setAttribute("calculation", "Sum");

            Element variable_expression_element = document.createElement("variableExpression");

            CDATASection var_expr_data = document.createCDATASection("$F{"
                    + field_element.getAttribute("name") + "}");
            variable_expression_element.appendChild(var_expr_data);

            new_variable_element.appendChild(variable_expression_element);
            root.insertBefore(new_variable_element, variable_element);
        }
    }

    public static void insertColumnFooterTextField(Document document,
            Node column_footer_band, int x_position, int width,
            String filed_db_type, String field_name)
    {
        String var_name = field_name + variables_extenstion;
        // Element var_element = (Element)getNode(document, var_name);
        // = textFieldExpression.getAttribute("class")
        if (x_position == 0) // insert the static total title
        {
            Element new_static_text_element = document.createElement("staticText");
            Element new_static_text_report_element = document.createElement("reportElement");
            new_static_text_report_element.setAttribute("backcolor", "#FF9900");
            // new_static_text_report_element.setAttribute("forecolor",
            // "#FFFFFF");
            new_static_text_report_element.setAttribute("height", "19");
            new_static_text_report_element.setAttribute("isPrintInFirstWholeBand", "false");
            new_static_text_report_element.setAttribute("isPrintRepeatedValues", "true");
            new_static_text_report_element.setAttribute("isPrintWhenDetailOverflows", "false");
            new_static_text_report_element.setAttribute("isRemoveLineWhenBlank", "false");
            new_static_text_report_element.setAttribute("key", "element-90");
            new_static_text_report_element.setAttribute("mode", "Opaque");
            new_static_text_report_element.setAttribute("positionType", "FixRelativeToBottom");
            new_static_text_report_element.setAttribute("stretchType", "NoStretch");
            new_static_text_report_element.setAttribute("width", width + "");
            new_static_text_report_element.setAttribute("x", x_position + "");
            new_static_text_report_element.setAttribute("y", "0");

            Element new_static_text_box_element = document.createElement("box");
            new_static_text_box_element.setAttribute("bottomBorder", "Thin");
            new_static_text_box_element.setAttribute("bottomBorderColor", "#000000");
            new_static_text_box_element.setAttribute("leftBorder", "Thin");
            new_static_text_box_element.setAttribute("leftBorderColor", "#000000");
            new_static_text_box_element.setAttribute("leftPadding", "2");
            new_static_text_box_element.setAttribute("rightBorder", "Thin");
            new_static_text_box_element.setAttribute("rightBorderColor", "#000000");
            new_static_text_box_element.setAttribute("rightPadding", "2");
            new_static_text_box_element.setAttribute("topBorder", "Thin");
            new_static_text_box_element.setAttribute("topBorderColor", "#000000");

            Element new_static_text_textElement = document.createElement("textElement");
            Element new_static_text_font_element = document.createElement("font");
            //new_static_text_font_element.setAttribute("fontName","Times New Roman");
            
            new_static_text_font_element.setAttribute("size", "10");

            Element new_static_text_text_element = document.createElement("text");

            CDATASection new_static_text_text_element_section = document.createCDATASection("Total");
            new_static_text_text_element.appendChild(new_static_text_text_element_section);

            new_static_text_textElement.appendChild(new_static_text_font_element);

            new_static_text_element.appendChild(new_static_text_report_element);
            new_static_text_element.appendChild(new_static_text_box_element);
            new_static_text_element.appendChild(new_static_text_textElement);
            new_static_text_element.appendChild(new_static_text_text_element);

            column_footer_band.appendChild(new_static_text_element);
        }
        // if(isNumberType(filed_db_type))
        else
        {
            Element new_textField_element = document.createElement("textField");
            new_textField_element.setAttribute("isStretchWithOverflow", "true");

            new_textField_element.setAttribute("isBlankWhenNull", "true");
            new_textField_element.setAttribute("evaluationTime", "Now");
            new_textField_element.setAttribute("hyperlinkType", "None");
            new_textField_element.setAttribute("hyperlinkTarget", "Self");

            Element new_textField_report_element = document.createElement("reportElement");
            new_textField_report_element.setAttribute("isPrintInFirstWholeBand", "false");
            new_textField_report_element.setAttribute("isPrintRepeatedValues", "true");
            new_textField_report_element.setAttribute("isPrintWhenDetailOverflows", "true");
            new_textField_report_element.setAttribute("isRemoveLineWhenBlank", "false");
            new_textField_report_element.setAttribute("key", "textField");
            new_textField_report_element.setAttribute("mode", "Opaque");
            new_textField_report_element.setAttribute("positionType", "FixRelativeToTop");
            new_textField_report_element.setAttribute("stretchType", "NoStretch");
            new_textField_report_element.setAttribute("height", "19");
            new_textField_report_element.setAttribute("width", width + "");
            new_textField_report_element.setAttribute("x", x_position + "");
            new_textField_report_element.setAttribute("y", "0");
            // coloring
            new_textField_report_element.setAttribute("backcolor", "#FF9900");
            // new_textField_report_element.setAttribute("forecolor",
            // "#FFFFFF");

            Element new_textField_box_element = document.createElement("box");
            new_textField_box_element.setAttribute("bottomBorder", "Thin");
            new_textField_box_element.setAttribute("bottomBorderColor", "#000000");
            new_textField_box_element.setAttribute("leftBorder", "Thin");
            new_textField_box_element.setAttribute("leftBorderColor", "#000000");
            new_textField_box_element.setAttribute("leftPadding", "2");
            new_textField_box_element.setAttribute("rightBorder", "Thin");
            new_textField_box_element.setAttribute("rightBorderColor", "#000000");
            new_textField_box_element.setAttribute("rightPadding", "2");
            new_textField_box_element.setAttribute("topBorder", "Thin");
            new_textField_box_element.setAttribute("topBorderColor", "#000000");

            Element new_textField_textElement = document.createElement("textElement");
            Element new_textField_font_element = document.createElement("font");
            //new_textField_font_element.setAttribute("fontName","Times New Roman");        
            new_textField_font_element.setAttribute("size", "10");
            Element new_textField_expression_element = null;
            if (JRXMLDesignerManeger.isNumberType(filed_db_type))
            {
                new_textField_expression_element = document.createElement("textFieldExpression");
                new_textField_expression_element.setAttribute("class", filed_db_type);
                CDATASection new_textField_expression_element_cdata = document.createCDATASection("$V{"
                        + var_name + "}");
                new_textField_expression_element.appendChild(new_textField_expression_element_cdata);
                new_textField_element.setAttribute("pattern", "###,###,###");
                new_textField_textElement.setAttribute("textAlignment", "Right");
            } else
            {
                new_textField_element.setAttribute("pattern", "");
            }

            new_textField_textElement.appendChild(new_textField_font_element);

            new_textField_element.appendChild(new_textField_report_element);
            new_textField_element.appendChild(new_textField_box_element);
            new_textField_element.appendChild(new_textField_textElement);

            if (new_textField_expression_element != null)
                new_textField_element.appendChild(new_textField_expression_element);

            column_footer_band.appendChild(new_textField_element);
        }
    }

}
