package com.tientt.filters;

import com.tientt.entities.TblRole;
import com.tientt.entities.TblUser;
import com.tientt.utils.XMLHelpers;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class AuthorizationFilter implements Filter {
    private final Logger logger = Logger.getLogger(AuthorizationFilter.class);
    private FilterConfig filterConfig = null;

    public void destroy() {
    }

    private boolean isAuthorized(HttpSession session, Document document, String resoruce)
            throws XPathExpressionException {
        String expression = "//pattern[normalize-space(value)='"
                + resoruce
                + "']/roles/role";
        XPath xPath = XMLHelpers.getXPathObject();
        NodeList roles = (NodeList) xPath.evaluate(expression, document, XPathConstants.NODESET);

        TblUser user = (TblUser) session.getAttribute("USER");
        TblRole userRole = user.getRole();

        for (int i = 0; i < roles.getLength(); i++) {
            Element roleElement = (Element) roles.item(i);
            String role = roleElement.getTextContent();
            if (role.equals(userRole.getName())) {
                return true;
            }
        }
        return false;
    }



    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;
        HttpSession session = httpReq.getSession(false);
        try {
            String resource = (String) request.getAttribute("RESOURCE");
            Document doc = (Document) request.getServletContext().getAttribute("DOMTREE");
            Boolean needAuthorization = (Boolean)request.getAttribute("NEED_CHECK_AUTHORIZATION");
            System.out.println("Is resource need authorization: " + (needAuthorization != null && needAuthorization == true));
            if (needAuthorization != null && needAuthorization == true){
                boolean isAuthorized = isAuthorized(session, doc, resource);
                if (isAuthorized){
                    chain.doFilter(request, response);
                }//end user is not authorized for this resource
                else{
                    httpRes.sendError(401);
                }
            }//end if resource does not need check for authorization
            else{
                chain.doFilter(request, response);
            }
        } catch (XPathExpressionException ex) {
            this.log(AuthorizationFilter.class.getName() + ": "+ "XPathExpressionException: " + ex.getMessage());
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
