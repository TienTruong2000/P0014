package com.tientt.servlets.admin.question;

import com.tientt.blos.interfaces.TblQuestionBLO;
import com.tientt.entities.TblQuestion;
import com.tientt.utils.SiteMapHelper;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.List;

public class QuestionSearchServlet extends HttpServlet {
    private final String ADMIN_PAGE = "AdminPage";
    private final Logger logger = Logger.getLogger(QuestionSearchServlet.class);
    private final TblQuestionBLO questionBLO = TblQuestionBLO.newInstance();

    private int getPage(String pageString) {
        int page = 1;
        try {
            page = Integer.parseInt(pageString);
        } catch (NumberFormatException ex) {
        }
        return page;
    }

    private Integer getPreviousPage(int currentPage) {
        Integer previousPage = null;
        if (currentPage > 1) {
            previousPage = currentPage - 1;
        }
        return previousPage;
    }

    private Integer getNextPage(int currentPage, int maxPage) {
        Integer nextPage = null;
        if (currentPage < maxPage) {
            nextPage = currentPage + 1;
        }
        return nextPage;
    }

    private void setupPaging(HttpServletRequest request) {
        String content = request.getParameter("txtSearchQuestionContent");
        String subjectID = request.getParameter("searchQuestionSubjectID");
        String status = request.getParameter("searchQuestionStatus");
        String pageString = request.getParameter("page");
        int currentPage = getPage(pageString);
        int maxPage =
                questionBLO.
                        getMaxPageByContentAndSubjectIDAndStatusStringAndPage
                                (content, subjectID, status);

        Integer nextPage = getNextPage(currentPage, maxPage);
        Integer previousPage = getPreviousPage(currentPage);

        request.setAttribute("NEXT_PAGE", nextPage);
        request.setAttribute("PREV_PAGE", previousPage);
        request.setAttribute("PAGE", currentPage);
    }

    private void setupResultList(HttpServletRequest request) {
        String content = request.getParameter("txtSearchQuestionContent");
        String subjectID = request.getParameter("searchQuestionSubjectID");
        String status = request.getParameter("searchQuestionStatus");
        String pageString = request.getParameter("page");

        int currentPage = getPage(pageString);
        List<TblQuestion> listQuestion =
                questionBLO.findQuestionByContentAndSubjectIDAndStatusStringAndPage
                        (content, subjectID, status, currentPage);
        request.setAttribute("SEARCH_RESULT", listQuestion);
    }



    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        try {
            setupResultList(request);
            setupPaging(request);

            Document document = (Document) this.getServletContext().getAttribute("DOMTREE");
            String url = SiteMapHelper.getURL(ADMIN_PAGE, document);
            request.getRequestDispatcher(url).forward(request, response);
        } catch (XPathExpressionException ex) {
            this.log(QuestionSearchServlet.class.getName() + ": " + "XPathExpressionException: " + ex.getMessage());
            logger.error(ex.getMessage());
            response.sendError(500);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
