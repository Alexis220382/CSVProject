package paging;

import dao.CSVFileDAO;
import dao.PagingDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "paging", urlPatterns = {"/paging"})
public class Paging extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PagingDAO pagingDAO = null;
        CSVFileDAO csvFileDAO = null;
        int currentPageNumber;
        int begin_item = 0;

        try {
            HttpSession session = request.getSession();
            pagingDAO = new PagingDAO();
            csvFileDAO = new CSVFileDAO();

            if(request.getParameter("remove_all") != null){
                csvFileDAO.removeCSVFile();
            }

            if(request.getParameter("remove_contact") != null){
                csvFileDAO.removeCSVFileBySurname(request.getParameter("remove_surname"));
            }

            if(request.getParameter("rows_on_page") != null){
                int rows_on_page = Integer.parseInt(request.getParameter("rows_on_page"));
                if(Integer.parseInt(request.getParameter("rows_on_page")) > pagingDAO.getCountRows()){
                    rows_on_page = pagingDAO.getCountRows()-1;
                }
                session.setAttribute("rows_on_page", rows_on_page);
            }

            if(request.getParameter("page") != null){
                currentPageNumber = Integer.parseInt(request.getParameter("page"))-1;
            }else{
                currentPageNumber = 0;
            }

            String ORDER_BY = "surname";
            if(request.getParameter("name") != null){
                ORDER_BY = "name";
            }
            if(request.getParameter("login") != null){
                ORDER_BY = "login";
            }
            if(request.getParameter("email") != null){
                ORDER_BY = "email";
            }
            if(request.getParameter("phone_number") != null){
                ORDER_BY = "phoneNumber";
            }
            if(request.getParameter("page") != null) {
                begin_item = (Integer.parseInt(request.getParameter("page"))-1) * ((Integer)session.getAttribute("rows_on_page"));
            }

            request.setAttribute("rows", pagingDAO.getAllSortedPagingCSVFile(ORDER_BY, begin_item,
                    (Integer)session.getAttribute("rows_on_page")));

            session.setAttribute("pages", preparePagingString(currentPageNumber, pagingDAO.getCountRows(),
                    (Integer)session.getAttribute("rows_on_page"), "paging"));

            request.getRequestDispatcher("paging.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println(e.toString());
        }finally {
            pagingDAO.conClosePaging();
            csvFileDAO.conCloseCSVFile();
        }
    }

    public static String preparePagingString(int currentPageNumber, int totalItems, int itemsPerPage, String pageLocation) {

        String ret = "";
        if (totalItems <= itemsPerPage)
            return ret;

        int totalPages = (totalItems / itemsPerPage);
        if (totalItems % itemsPerPage != 0)
            totalPages++;
        if(currentPageNumber >= totalPages)
            currentPageNumber = totalPages-1;
        if (totalItems <= currentPageNumber * itemsPerPage)
            currentPageNumber = 1;
        int start = currentPageNumber - 4;
        if (start < 0)
            start = 0;
        int end = currentPageNumber + 5;
        if (end >= totalPages)
            end = totalPages;

        if (start > 0)
            ret += "<a name='page' href='" + pageLocation + "?page=1\'>1</a>&nbsp;";
        if (start > 1)
            ret += "...,&nbsp;";
        for (int i = start; i < end; i++) {
            if (i == currentPageNumber)
                ret += "<strong>" + (i + 1) + "</strong>";
            else
                ret += "<a name='page' href='" + pageLocation + "?page=" + (i + 1) + "'>" + (i + 1) + "</a>";
            if (i + 1 < totalPages)
                ret += ",&nbsp;";
        }
        if (end + 1 < totalPages)
            ret += "...,&nbsp;";
        if (end < totalPages) {
            if (totalPages == currentPageNumber)
                ret += "<strong>" + totalPages + "</strong>";
            else
                ret += "<a name='page' href='" + pageLocation + "?page=" + totalPages + "'>" + totalPages + "</a>";
        }
        ret += "&nbsp;&nbsp;";
        return ret;
    }


}

