package com.tientt.filters;

import com.tientt.utils.SiteMapHelper;
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
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class DispatcherFilter implements Filter {
    private FilterConfig filterConfig = null;
    private final Logger logger = Logger.getLogger(DispatcherFilter.class);

    public DispatcherFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        httpReq.setCharacterEncoding("UTF-8");
        httpRes.setCharacterEncoding("UTF-8");
        httpRes.setHeader("Cache-Control", "no-cache, no-store");


        //servletPath will return /abc
        //we need to remove the "/" at the begin
        String resource = httpReq.getServletPath().substring(1);
        if (resource.contains("js")) {
            chain.doFilter(request, response);
        } else {
            try {
                Document doc = (Document) request.getServletContext().getAttribute("DOMTREE");

                String url = SiteMapHelper.getURL(resource, doc);
                if (url != null) {
                    httpReq.getRequestDispatcher(url).forward(request, response);
                } else {
                    httpRes.sendError(404);
                }
            } catch (XPathExpressionException ex) {
                this.log(DispatcherFilter.class.getName() + ": " + "XPathExpressionException: " + ex.getMessage());
                logger.error(ex.getMessage());
                httpRes.sendError(500);
            }
        }
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;

    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
