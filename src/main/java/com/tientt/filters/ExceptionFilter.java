package com.tientt.filters;

import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionFilter implements Filter {
    private final Logger logger = Logger.getLogger(ExceptionFilter.class);
    private FilterConfig filterConfig = null;
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            this.log(e.getMessage());
            e.printStackTrace();
            ((HttpServletResponse) response).sendError(500);
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
