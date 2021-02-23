package com.tientt.filters;

import com.tientt.utils.XMLHelpers;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class ResourceValidationFilter implements Filter {
    private final Logger logger = Logger.getLogger(ResourceValidationFilter.class);
    private FilterConfig filterConfig = null;

    public void destroy() {
    }

    private boolean isResourceValid(Document document, String resource) throws
            XPathExpressionException {
        if (document == null || resource == null) {
            return false;
        }
        if (resource.contains("js") || resource.contains("css")) {
            return true;
        }
        String expression = "//pattern[normalize-space(value)='" +
                resource +
                "']";
        XPath xPath = XMLHelpers.getXPathObject();
        boolean isResourceValid = (boolean) xPath.evaluate(expression, document, XPathConstants.BOOLEAN);
        return isResourceValid;
    }

    private String extractResource(HttpServletRequest request){
        //servletPath will return /abc
        //we need to remove the "/" at the begin and remove all query string
        String resource = request.getServletPath();
        int paramIndex = resource.indexOf("?");
        if (paramIndex != -1) {
            resource = resource.substring(1, paramIndex - 1);
        } else {
            resource = resource.substring(1);
        }
        return resource;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;
        try {
            String resource = extractResource(httpReq);
            Document doc = (Document) request.getServletContext().getAttribute("DOMTREE");

            boolean isResourceValid = isResourceValid(doc, resource);
            System.out.println("----------------------------");
            System.out.println("Request resource: " + resource);
            System.out.println("Is resource valid: " + isResourceValid);
            if (isResourceValid){
                httpReq.setAttribute("RESOURCE", resource);
                chain.doFilter(request, response);
            } else{
                httpRes.sendError(404);
            }
        } catch (XPathExpressionException ex) {
            this.log(ResourceValidationFilter.class.getName() + ": "+ "XPathExpressionException: " + ex.getMessage());
            logger.error(ex.getMessage());
            httpRes.sendError(500);
        }
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void init(FilterConfig config) throws ServletException {
        filterConfig = config;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
