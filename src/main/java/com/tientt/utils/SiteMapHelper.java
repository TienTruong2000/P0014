package com.tientt.utils;

import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

public class SiteMapHelper {
    public static String getURL(String resource, Document document) throws XPathExpressionException {
        String expression = "//pattern[normalize-space(value)='"
                + resource
                + "']/mapping";
        XPath xpath = XMLHelpers.getXPathObject();
        return (String) xpath.evaluate(expression, document, XPathConstants.STRING);
    }
}
