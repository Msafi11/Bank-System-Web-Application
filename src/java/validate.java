/*
Name : Mohamed Safi Ahmed
ID : 20170237
Group : DS_IS_1
*/

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ahmed
 */
@WebServlet(urlPatterns = {"/validate"})
public class validate extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://127.0.0.1:3306/mydb";
                String user = "root";
                String password = "root";
                Connection Con = null;
                Statement Stmt = null;
                ResultSet rs = null;
                int id  = 0;
                
                String CustomerID = request.getParameter("CustomerID");
                String CustomerPass = request.getParameter("CustomerPass");
                 String query = "SELECT * FROM mydb.Customer Where CustomerID = "
                        + Integer.parseInt(CustomerID)
                        + " AND CustomerPass = "
                        + CustomerPass;
                
                
                Con = DriverManager.getConnection(url, user, password);
                Stmt = Con.createStatement();
                rs = Stmt.executeQuery(query);
                
                
                if(rs.isBeforeFirst()){
                    
                    HttpSession session = request.getSession(true);
                   
                    session.setAttribute("sessionCI", CustomerID);
                    session.setAttribute("sessionCP", CustomerPass);
                    session.setAttribute("cancelBool", 0);
                    rs.close();
                    Stmt.close();
                    Con.close();
                    
                    response.sendRedirect("customerhome.jsp");
                    
                }
                else{
                    rs.close();
                    Stmt.close();
                    Con.close();
                    
                    response.sendRedirect("index.html");
                    
                    
                    
                }
               
                
                
                
                
            } catch (Exception ex) {
                ex.printStackTrace();
                out.close();
            }
        }
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
