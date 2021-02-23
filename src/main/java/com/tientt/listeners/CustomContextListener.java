package com.tientt.listeners;

import com.tientt.utils.XMLHelpers;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class CustomContextListener implements ServletContextListener {

    private final Logger logger = Logger.getLogger(CustomContextListener.class);

    private void configLog4j(ServletContext context) {
        String fileLocation = context.getInitParameter("log4j-config-location");
        String realPath = context.getRealPath(fileLocation);
        PropertyConfigurator.configure(realPath);
    }

    private void loadSiteMap(ServletContext context) {
        try {
            String fileLocation = context.getInitParameter("sitemap-location");
            String realPath = context.getRealPath(fileLocation);
            Document doc = XMLHelpers.parseDOMFromFile(realPath);
            if (doc!=null){
                context.setAttribute("DOMTREE", doc);
            }
        } catch (IOException  ex) {
            logger.error(CustomContextListener.class.getName() + " : " +"IOException: "+ex.getMessage());
        } catch (SAXException ex){
            logger.error(CustomContextListener.class.getName() + " : " +"SAXException: "+ex.getMessage());
        } catch (ParserConfigurationException ex){
            logger.error(CustomContextListener.class.getName() + " : " +"ParserConfigurationException: "+ex.getMessage());
        }

    }
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        configLog4j(context);
        loadSiteMap(context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}

