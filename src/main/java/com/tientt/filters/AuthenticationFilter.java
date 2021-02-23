package com.tientt.filters;

import com.tientt.commons.Role;
import com.tientt.entities.TblUser;
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
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class AuthenticationFilter implements Filter {
    private final Logger logger = Logger.getLogger(AuthenticationFilter.class);
    private FilterConfig filterConfig = null;
    private final String SHOW_ADMIN_PAGE_SERVLET = "ShowAdminPageAction";
    private final String SHOW_STUDENT_PAGE_SERVLET = "ShowStudentPageAction";

    public void destroy() {
    }

    private boolean isStaticFile(String resource){
        int dotIndex = resource.indexOf(".");
        if (dotIndex != -1){
            String fileType = resource.substring(dotIndex+1);
            System.out.println("File type: " + fileType);
            if (fileType.equals("js") || fileType.equals("css")){
                return true;
            }
        }
        return false;
    }
    private boolean isResourceBelongToGuest(Document document, String resource) throws XPathExpressionException {
        String expression = "//pattern[normalize-space(value)='"
                + resource
                + "']/roles/role[.='Guest']";
        XPath xpath = XMLHelpers.getXPathObject();
        boolean belongToGuest = (boolean) xpath.evaluate(expression, document, XPathConstants.BOOLEAN);
        return belongToGuest;
    }

    private String getUserMainPage(Document document, HttpSession session ){
        TblUser user = (TblUser) session.getAttribute("USER");
        if (user.getRole().getID() == Role.ADMIN){
            return SHOW_ADMIN_PAGE_SERVLET;
        }
        if (user.getRole().getID() == Role.STUDENT){
            return SHOW_STUDENT_PAGE_SERVLET;
        }
        return "";
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;
        HttpSession session = httpReq.getSession(false);
        try {
            String resource = (String) request.getAttribute("RESOURCE");
            Document doc = (Document) request.getServletContext().getAttribute("DOMTREE");
            boolean isResourceStaticFile = isStaticFile(resource);
            if (isResourceStaticFile){
                chain.doFilter(request, response);
            }//end if resource is not static file
            else{
                boolean isUserLogin = session != null && session.getAttribute("USER") != null;
                boolean isResourceBelongToGuest = isResourceBelongToGuest(doc, resource);
                System.out.println("Is user login: " + isUserLogin);
                System.out.println("Is resource belong to guest: " + isResourceBelongToGuest);
                if (isUserLogin == true && isResourceBelongToGuest == false){
                    request.setAttribute("NEED_CHECK_AUTHORIZATION", true);
                    chain.doFilter(request, response);
                }
                if (isUserLogin == true && isResourceBelongToGuest == true){
                    String url = getUserMainPage(doc, session);
                    httpRes.sendRedirect(url);
                }
                if (isUserLogin == false && isResourceBelongToGuest == false){
                    httpRes.sendError(403);
                }
                if (isUserLogin == false && isResourceBelongToGuest == true){
                    chain.doFilter(request, response);
                }
            }//end else to deal with resource is not static file
        } catch (XPathExpressionException ex) {
            this.log(AuthenticationFilter.class.getName() + ": "+ "XPathExpressionException: " + ex.getMessage());
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
