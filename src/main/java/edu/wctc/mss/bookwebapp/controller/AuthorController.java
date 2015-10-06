package edu.wctc.mss.bookwebapp.controller;

import edu.wctc.mss.bookwebapp.model.Author;
import edu.wctc.mss.bookwebapp.model.AuthorDao;
import edu.wctc.mss.bookwebapp.model.AuthorDaoStrategy;
import edu.wctc.mss.bookwebapp.model.AuthorService;
//import edu.wctc.mss.bookwebapp.model.ConnPoolAuthorDao;
import edu.wctc.mss.bookwebapp.model.DBStrategy;
import edu.wctc.mss.bookwebapp.model.MySqlDbStrategy;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * The main controller for author-related activities
 *
 * @author jlombardo
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listAuthors.jsp";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String ACTION_PARAM = "action";
    
    private String dbStrategyClassName;
    private String daoClassName;
    private DBStrategy db;
    private AuthorDaoStrategy authorDao;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        
        String email = this.getServletContext().getInitParameter("email");
        System.out.println(email);
        
        dbStrategyClassName = this.getServletConfig().getInitParameter("dbStrategy");
        daoClassName = this.getServletConfig().getInitParameter("authorDao");
        
//        try {
//            
//            Class c = Class.forName(dbStrategyClassName);
//            db = (DBStrategy)c.newInstance();
//            
//            c = Class.forName(daoClassName);
//            
//            
//        } catch (Exception e) {
//            System.out.println(e.getLocalizedMessage());
//        }
        

        db = new MySqlDbStrategy();
        AuthorDaoStrategy authDao
                = new AuthorDao(db, "com.mysql.jdbc.Driver",
                        "jdbc:mysql://localhost:3306/book", "root", "admin");
        AuthorService authService = new AuthorService(authDao);

        try {
            /*
             Here's what the connection pool version looks like.
             */
//            Context ctx = new InitialContext();
//            DataSource ds = (DataSource)ctx.lookup("jdbc/book");
//            AuthorDaoStrategy authDao = new ConnPoolAuthorDao(ds, new MySqlDbStrategy());
//            AuthorService authService = new AuthorService(authDao);

            /*
             Determine what action to take based on a passed in QueryString
             Parameter
             */
             
            if (action.equals(LIST_ACTION)) {
                List<Author> authors = null;
                authors = authService.getAllAuthors();
                request.setAttribute("authors", authors);
                destination = LIST_PAGE;

            } else if (action.equals(ADD_ACTION)) {
                // Hard coded for now, for testing purposes
                // authService.addAuthor("Mike Schoenauer", new Date());
                
                String newAuthorName = request.getParameter("newName");
                String date = request.getParameter("newDate");
                Date newDate = new Date(date);
                
                System.out.println("Adding Author: " + newAuthorName + date);
                
                authService.addAuthor(newAuthorName, newDate);
                
                destination = "/index.html";
                
            } else if (action.equals(UPDATE_ACTION)) {
                // Hard code for now, for testing purposes
                //authService.updateAuthor(1,"author_name","Steve Schoenauer");
                
                String id = request.getParameter("author_id");
                int newAuthorID = Integer.parseInt(id);
                String newAuthorName = request.getParameter("new_name");
                
                
                authService.updateAuthor(newAuthorID, "author_name", newAuthorName);
                
                destination = "/index.html";
                
            } else if (action.equals(DELETE_ACTION)) {
                // 
                //authService.deleteAuthor(1);
                
                String id = request.getParameter("author_id");
                int newAuthorID = Integer.parseInt(id);
                
                authService.deleteAuthor(newAuthorID);
                
                destination = "/index.html";
                
            } else {
                // no param identified in request, must be an error
                request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
                destination = LIST_PAGE;
            }
            
        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        // Forward to destination page
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
