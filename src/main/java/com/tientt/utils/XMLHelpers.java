package com.tientt.utils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.Serializable;

public class XMLHelpers implements Serializable {
    public static Document parseDOMFromFile(String filePath) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(filePath);
        return doc;
    }

    public static XPath getXPathObject(){
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        return xPath;
    }
}
