/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

// make a method to insert a static text, textfield which take a node to append to
package com.mobinil.sds.core.utilities.jaspertest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

/**
 * 
 * @author amr
 */
public class JRXMLDesignerManeger
{

    private static String template_file_path_to_parse, sql_query,
            logo_image_path;
    private static String report_reviewer, report_reviewer_position,
            report_owner, report_owner_position, approval_person_1,
            approval_person_1_position, approval_person_2,
            approval_person_2_position, footer_summery_line, memo_date,
            auto_gen_msg, memo_title,himself_manger,himself_manager_position;

    private static String from, to, subject, cc;
    private static DocumentBuilderFactory factory;
    private static DocumentBuilder builder;
    private static Vector all_report_column; // carry ReportColumn objects
   
    private static int rep_width;  
    private static int last_page_height;
    
    
     
    
    private static int margin_width;
    private static String variables_extenstion = "__sum var";

    public void designJRXMLFile(String[] params, Vector all_report_column,
            String output_file_name, int query_row_count)
    {
        JRXMLDesignerManeger.template_file_path_to_parse = params[0];
        JRXMLDesignerManeger.sql_query = params[1];
        JRXMLDesignerManeger.logo_image_path = params[2];
        JRXMLDesignerManeger.cc = params[3];
        JRXMLDesignerManeger.to = params[4];
        JRXMLDesignerManeger.from = params[5];
        JRXMLDesignerManeger.subject = params[6];
        JRXMLDesignerManeger.report_reviewer = params[7];
        JRXMLDesignerManeger.report_reviewer_position = params[8];
        JRXMLDesignerManeger.report_owner = params[10];
        JRXMLDesignerManeger.report_owner_position = params[11];
        JRXMLDesignerManeger.approval_person_1 = params[12];
        JRXMLDesignerManeger.approval_person_1_position = params[13];
        JRXMLDesignerManeger.approval_person_2 = params[14];
        JRXMLDesignerManeger.approval_person_2_position = params[15];
        JRXMLDesignerManeger.footer_summery_line = params[16];

   
        
        JRXMLDesignerManeger.memo_date = params[17];
        JRXMLDesignerManeger.auto_gen_msg = params[18];
        
        JRXMLDesignerManeger.memo_title = params[19];
        JRXMLDesignerManeger.all_report_column = all_report_column;
        //title_band_height = 164; // pixel

        JRXMLDesignerManeger.himself_manger = params[21];
        JRXMLDesignerManeger.himself_manager_position = params [22];

        margin_width = 30 * 2;
        //detail_element_height = 19;
        rep_width = 595 - margin_width; // 60 are the margins pixels
        // last_page_height =
        // 842-title_band_height-column_header_band_height-margin_height-
        // (query_row_count*detail_element_height)-2; // default A4 height ..
        // if(last_page_height<0)
        last_page_height = 165;

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
            File xml_file = new File(template_file_path_to_parse);
            File resulted_xml_file = new File(output_file_name);

            System.out.println("xml file= "+ xml_file);

            Document document = builder.parse(xml_file);

            System.out.println(" d1 document = "+ document);

            Element root = document.getDocumentElement();

            // insert title logo image
            insertLogoImage(document, logo_image_path);

            // insert sql query
            replaceValue(document, "queryString", sql_query);
            // insert variables
            prepareTitleStaticTexts(document);

            System.out.println(" d2 document = "+ document);

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
                    if (isNumberType(filed_db_type))
                        fields_to_create_variables_on.add(new_field_element);
                    root.insertBefore(new_field_element, element);

                }
            }
            // insert field sum variables
            if (fields_to_create_variables_on.size() > 0)
                insertFieldsSumVariables(document, fields_to_create_variables_on, root);
            // insert column header
            Node columnHedaer_band = getNode(document, "columnHeader");
            prepareColumnHeaderBandStaticText(document, columnHedaer_band.getFirstChild());
            // insert detail
            // insertDetail(document, root);
            Node detail_band = getNode(document, "detail");

            System.out.println(" d3 document = "+ document);
            prepareDetailTextField(document, detail_band.getFirstChild());

            // prepare footer content
            prepareLastPageFooterStaticText(document);
System.out.println(" d4 document = "+ document);
            // construct the "do nothing" transformation
            Transformer t = TransformerFactory.newInstance().newTransformer();
            // set output properties to get a DOCTYPE node

            // t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
            // "http://jasperreports.sourceforge.net/dtds/jasperreport.dtd");
            // t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, new
            // InputSource(new FileReader("" + "/jasperreport.dtd"));

            t.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "//JasperReports//DTD Report Design//EN");
            // set indentFation
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty(OutputKeys.METHOD, "xml");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            // apply the "do nothing" transformation and send the output to a
            // file

            System.out.println(" t  = "+ t );
            System.out.println("document = "+ document);
            System.out.println("resulted_xml_file = " + resulted_xml_file);
            t.transform(new DOMSource(document), new StreamResult(new FileOutputStream(resulted_xml_file)));

        } catch (Exception ex)
        {
            Logger.getLogger(JRXMLDesignerManeger.class.getName()).log(Level.SEVERE, null, ex);
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
        column_header_band_element.setAttribute("height", "16");
        // column_header_band_element.setAttribute("isSplitAllowed", "true");
        Element column_header_band_rectangle_element = document.createElement("rectangle");
        column_header_band_rectangle_element.setAttribute("radius", "0");
        Element column_header_report_element = document.createElement("reportElement");
        column_header_report_element.setAttribute("backcolor", "#FF9900");
        column_header_report_element.setAttribute("forecolor", "#000000");
        column_header_report_element.setAttribute("height", "12");

        column_header_report_element.setAttribute("isPrintInFirstWholeBand", "false");
        column_header_report_element.setAttribute("isPrintRepeatedValues", "true");
        column_header_report_element.setAttribute("isPrintWhenDetailOverflows", "false");
        column_header_report_element.setAttribute("isRemoveLineWhenBlank", "true");
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

    private static void prepareColumnHeaderBandStaticText(Document document,
            Node header_band)
    {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("prepareColumnHeaderBandStaticText");

        int cell_width = rep_width / (all_report_column.size() + 1);
        int width = 2 * cell_width;
        int x_position = 0;
        for (int j = 0; j < all_report_column.size(); j++)
        {
            ReportColumn report_column = (ReportColumn) all_report_column.get(j);

            Element new_static_text_element = document.createElement("staticText");
            Element new_static_text_report_element = document.createElement("reportElement");
            new_static_text_report_element.setAttribute("backcolor", "#FF9900");
            //new_static_text_report_element.setAttribute("forecolor", "#FFFFFF");
            new_static_text_report_element.setAttribute("height", "16");
            new_static_text_report_element.setAttribute("isPrintInFirstWholeBand", "false");
            new_static_text_report_element.setAttribute("isPrintRepeatedValues", "true");
            new_static_text_report_element.setAttribute("isPrintWhenDetailOverflows", "false");
            new_static_text_report_element.setAttribute("isRemoveLineWhenBlank", "true");
            new_static_text_report_element.setAttribute("key", "element-90");
            new_static_text_report_element.setAttribute("mode", "Opaque");
            new_static_text_report_element.setAttribute("positionType", "FixRelativeToTop");
            new_static_text_report_element.setAttribute("stretchType", "RelativeToTallestObject");
            if (j == 0)
            {
                new_static_text_report_element.setAttribute("width", width + "");
            } else
            {
                width = cell_width;
                new_static_text_report_element.setAttribute("width", width + "");
            }
            new_static_text_report_element.setAttribute("x", x_position + "");
            x_position += width;
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
            //new_static_text_font_element.setAttribute("fontName", "Tahoma");
            new_static_text_font_element.setAttribute("size", "6");

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

        Element detail_band_line = document.createElement("line");
        detail_band_line.setAttribute("direction", "TopDown");

        Element detail_report_element = document.createElement("reportElement");
        detail_report_element.setAttribute("x", "0");
        detail_report_element.setAttribute("y", "0");
        detail_report_element.setAttribute("width", rep_width + "");
        detail_report_element.setAttribute("height", "0");
        detail_report_element.setAttribute("forecolor", "#808080");
        detail_report_element.setAttribute("key", "line");
        detail_report_element.setAttribute("positionType", "FixRelativeToBottom");

        Element graphic_element = document.createElement("graphicElement");
        graphic_element.setAttribute("stretchType", "NoStretch");
        graphic_element.setAttribute("pen", "Thin");

        detail_element.appendChild(detail_band_element);
        detail_band_element.appendChild(detail_band_line);
        detail_band_line.appendChild(detail_report_element);
        detail_band_line.appendChild(graphic_element);
        // prepare detail band
        prepareDetailTextField(document, detail_band_element);
        return detail_element;
    }

    private static void prepareDetailTextField(Document document,
            Node detail_band)
    {
        Node column_footer_element = getNode(document, "columnFooter").getFirstChild();
        Node last_page_footer_element = getNode(document, "lastPageFooter").getFirstChild();
        int cell_width = rep_width / (all_report_column.size() + 1);
        int width = 2 * cell_width;
        int x_position = 0;
        String filed_db_type = "";

        for (int j = 0; j < all_report_column.size(); j++)
        {
            // last_page_height -= 19;

            ReportColumn report_column = (ReportColumn) all_report_column.get(j);
            filed_db_type = report_column.getDB_field_dataType();

            Element new_textField_element = document.createElement("textField");
            new_textField_element.setAttribute("isStretchWithOverflow", "true");

            // new_textField_element.setAttribute("isBlankWhenNull", "true");
            new_textField_element.setAttribute("isBlankWhenNull", "false");
            new_textField_element.setAttribute("evaluationTime", "Now");
            new_textField_element.setAttribute("hyperlinkType", "None");
            new_textField_element.setAttribute("hyperlinkTarget", "Self");

            Element new_textField_report_element = document.createElement("reportElement");
            new_textField_report_element.setAttribute("isPrintInFirstWholeBand", "false");
            new_textField_report_element.setAttribute("isPrintRepeatedValues", "true");
            new_textField_report_element.setAttribute("isPrintWhenDetailOverflows", "true");
            new_textField_report_element.setAttribute("isRemoveLineWhenBlank", "true");
            new_textField_report_element.setAttribute("key", "textField");
            new_textField_report_element.setAttribute("mode", "Opaque");
            new_textField_report_element.setAttribute("positionType", "FixRelativeToTop");
            new_textField_report_element.setAttribute("stretchType", "RelativeToTallestObject");
            
            new_textField_report_element.setAttribute("height", "14");
            
            if (j == 0)
            {
                new_textField_report_element.setAttribute("backcolor", "#FF9900");
                new_textField_report_element.setAttribute("width", width + "");
            } else
            {
                width = cell_width;
                new_textField_report_element.setAttribute("width", width + "");
            }
            new_textField_report_element.setAttribute("x", x_position + "");
            new_textField_report_element.setAttribute("y", "0");

            Element new_textField_box_element = document.createElement("box");
            new_textField_box_element.setAttribute("bottomBorder", "Thin");
            new_textField_box_element.setAttribute("leftBorder", "Thin");
            new_textField_box_element.setAttribute("rightBorder", "Thin");
            new_textField_box_element.setAttribute("topBorder", "Thin");

            new_textField_box_element.setAttribute("bottomBorderColor", "#000000");
            new_textField_box_element.setAttribute("leftBorderColor", "#000000");
            new_textField_box_element.setAttribute("rightBorderColor", "#000000");
            new_textField_box_element.setAttribute("leftPadding", "2");
            new_textField_box_element.setAttribute("rightPadding", "2");
            new_textField_box_element.setAttribute("topBorderColor", "#000000");

            Element new_textField_textElement = document.createElement("textElement");
            Element new_textField_font_element = document.createElement("font");
             //new_textField_font_element.setAttribute("fontName","Tahoma");
            new_textField_font_element.setAttribute("size", "8");
            Element new_textField_expression_element = document.createElement("textFieldExpression");
            new_textField_expression_element.setAttribute("class", report_column.getDB_field_dataType());
            CDATASection new_textField_expression_element_cdata = document.createCDATASection("$F{"
                    + report_column.getDB_field_name() + "}");
            new_textField_expression_element.appendChild(new_textField_expression_element_cdata);

            new_textField_textElement.appendChild(new_textField_font_element);

            if (isNumberType(filed_db_type))
            {
                new_textField_element.setAttribute("pattern", "###,###,###");
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
            insertLastPageFooterTextField(document, column_footer_element, x_position, width, filed_db_type, report_column.getDB_field_name());

            // insertLastPageFooterTextField(document,last_page_footer_element,
            // x_position, width, filed_db_type ,
            // report_column.getDB_field_name());

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
        image_report_element.setAttribute("width", "126");
        image_report_element.setAttribute("height", "32");
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
        CDATASection image_data = document.createCDATASection("\"" + image_path
                + "\"");
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
    public static void prepareTitleStaticTexts(Document document)
    {
        NodeList nodeList = document.getElementsByTagName("text");
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node n = nodeList.item(i);
            Node text_data = n.getFirstChild();
            if (text_data.getNodeValue().equalsIgnoreCase("To_person"))
            {
                text_data.setNodeValue(to);
            } else if (text_data.getNodeValue().equalsIgnoreCase("From_person"))
            {
                text_data.setNodeValue(from);
            } else if (text_data.getNodeValue().equalsIgnoreCase("CC_person"))
            {
                text_data.setNodeValue(cc);
            } else if (text_data.getNodeValue().equalsIgnoreCase("Subject_person"))
            {
                text_data.setNodeValue(subject);
            } else if (text_data.getNodeValue().equalsIgnoreCase("memo_date"))
            {
                text_data.setNodeValue(memo_date);
            }
        }
        // nodeList = title_band.getChildNodes();
        // for(int i=0;i<nodeList.getLength();i++)
        // {
        // Node title_band_nodes = nodeList.item(i);
        //
        // }
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////// Prepare Footer
    // /////////////////////////////////////////////////////////////////////////////////////////////////
    public static void prepareLastPageFooterStaticText(Document document)
    {
        NodeList lastPageFooter = document.getElementsByTagName("lastPageFooter");
        NodeList all_doc_text_element = document.getElementsByTagName("text");
        Element lastPageFooter_band = (Element) lastPageFooter.item(0).getFirstChild();
        lastPageFooter_band.setAttribute("height", "" + (last_page_height));
        for (int i = 0; i < all_doc_text_element.getLength(); i++)
        {
            CDATASection text_cdate = (CDATASection) all_doc_text_element.item(i).getFirstChild();
            if (text_cdate.getNodeValue().equalsIgnoreCase("report_reviewer"))
            {
                text_cdate.setNodeValue(report_reviewer);
            } else if (text_cdate.getNodeValue().equalsIgnoreCase("report_reviewer_position"))
            {
                text_cdate.setNodeValue(report_reviewer_position);
            }
             else
             if(text_cdate.getNodeValue().equalsIgnoreCase("himself_manager"))
             {
             text_cdate.setNodeValue(himself_manger);
             }else
             if(text_cdate.getNodeValue().equalsIgnoreCase("himself_manager_position"))
             {
             text_cdate.setNodeValue(himself_manager_position);
             }
            else if (text_cdate.getNodeValue().equalsIgnoreCase("report_owner"))
            {
                text_cdate.setNodeValue(report_owner);
            } else if (text_cdate.getNodeValue().equalsIgnoreCase("report_owner_position"))
            {
                text_cdate.setNodeValue(report_owner_position);
            } else if (text_cdate.getNodeValue().equalsIgnoreCase("approval_person_1"))
            {
                text_cdate.setNodeValue(approval_person_1);
            } else if (text_cdate.getNodeValue().equalsIgnoreCase("approval_person_1_position"))
            {
                text_cdate.setNodeValue(approval_person_1_position);
            } else if (text_cdate.getNodeValue().equalsIgnoreCase("approval_person_2"))
            {
                text_cdate.setNodeValue(approval_person_2);
            } else if (text_cdate.getNodeValue().equalsIgnoreCase("approval_person_2_position"))
            {
                text_cdate.setNodeValue(approval_person_2_position);
            }

            else if (text_cdate.getNodeValue().equalsIgnoreCase("footer_summery_line"))
            {
                text_cdate.setNodeValue(subject);
            } else if (text_cdate.getNodeValue().equalsIgnoreCase("auto_gen_msg"))
            {
                text_cdate.setNodeValue(auto_gen_msg);
            } else if (text_cdate.getNodeValue().equalsIgnoreCase("footer_summery_line_pf"))
            {
                text_cdate.setNodeValue(subject);
            } else if (text_cdate.getNodeValue().equalsIgnoreCase("auto_gen_msg_pf"))
            {
                text_cdate.setNodeValue(auto_gen_msg);
            } else if (text_cdate.getNodeValue().equalsIgnoreCase("memo_title"))
            {
                System.out.println("node value in memo_title " + text_cdate.getTextContent() );
                System.out.println("memo_+title = "+ memo_title);
                text_cdate.setNodeValue(memo_title);
            }

        }
    }

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

    public static void insertLastPageFooterTextField(Document document,
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
            //new_static_text_report_element.setAttribute("forecolor", "#FFFFFF");
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
             //new_static_text_font_element.setAttribute("fontName", "Tahoma");
            new_static_text_font_element.setAttribute("size", "8");

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
            //new_textField_report_element.setAttribute("forecolor", "#FFFFFF");

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
            //new_textField_font_element.setAttribute("fontName","Tahoma");
            new_textField_font_element.setAttribute("size", "8");
            Element new_textField_expression_element = null;
            if (isNumberType(filed_db_type))
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
                // new_textField_element.setAttribute("pattern", "");
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

    public static boolean isNumberType(String filed_db_type)
    {

        if (filed_db_type.equalsIgnoreCase("java.math.BigDecimal")
                || filed_db_type.equalsIgnoreCase("java.util.Integer")
                || filed_db_type.equalsIgnoreCase("java.util.Double"))
            return true;
        else
            return false;
    }
    // /////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////// SETTERS AND GETTERS
    // /////////////////////////////////////////////////////////////////////////////////////////////////
    // public static String getCc() {
    // return cc;
    // }
    //
    // public static void setCc(String cc) {
    // JRXMLDesignerManeger.cc = cc;
    // }
    //
    // public static String getFrom() {
    // return from;
    // }
    //
    // public static void setFrom(String from) {
    // JRXMLDesignerManeger.from = from;
    // }
    //
    // public static String getSubject() {
    // return subject;
    // }
    //
    // public static void setSubject(String subject) {
    // JRXMLDesignerManeger.subject = subject;
    // }
    //
    // public static String getTo() {
    // return to;
    // }
    //
    // public static void setTo(String to) {
    // JRXMLDesignerManeger.to = to;
    // }
}
