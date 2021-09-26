/*
    Name : Mohamed Safi Ahmed
    ID : 20170237
    Group : DS_IS_1
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 *
 * @author Safix
 */
@WebServlet(urlPatterns = {"/addaccount"})
public class addaccount extends HttpServlet {

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
                Random random = new Random();
                
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                
                
                String CustomerID  = request.getSession().getAttribute("sessionCI").toString();
                
                String query = "INSERT INTO mydb.bankaccount (BankAccountID, BACreationDate, BACurrentBalance, Customer_CustomerID)" 
                               +" VALUES( "
                               + random.nextInt(100) + ","
                               + "'" + formatter.format(date) + "'"  +", "
                               + 1000 + ", "
                               + Integer.parseInt(CustomerID)
                               +" )";
                
                Con = DriverManager.getConnection(url, user, password);
                Stmt = Con.createStatement();
                Stmt.executeUpdate(query);
                
               
                
                
                    
                    
                    Con.close();
                    response.sendRedirect("customerhome.jsp");
                   
                    
                    
                    
                    
                    
                    
                
                
            }catch (Exception ex) {
                ex.printStackTrace();
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
