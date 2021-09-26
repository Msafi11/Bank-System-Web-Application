
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Safix
 */
@WebServlet(urlPatterns = {"/cancelCheck"})
public class cancelCheck extends HttpServlet {

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
            try{
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://127.0.0.1:3306/mydb";
                String user = "root";
                String password = "root";
                Connection Con = null;
                Statement Stmt = null;
                Statement Stmt2 = null;
                Statement Stmt3 = null;
                Statement Stmt4 = null;
                ResultSet rs = null;
                ResultSet rs2 = null;
                ResultSet rs3 = null;
                ResultSet rs4 = null;
                
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                
                
                int CustomerID  = Integer.parseInt(request.getSession().getAttribute("sessionCI").toString());
                int BankTransactionID = Integer.parseInt(request.getParameter("BankTransactionID"));
                
                String getDate = "SELECT * FROM mydb.banktransaction WHERE BankTransactionID = "+BankTransactionID;
                
                Con = DriverManager.getConnection(url, user, password);
                Stmt = Con.createStatement();
                Stmt2 = Con.createStatement();
                Stmt3 = Con.createStatement();
                Stmt4 = Con.createStatement();
                rs = Stmt.executeQuery(getDate);
                rs.next();
                int amountToBeCanceled = Integer.parseInt(rs.getString("BTAmount"));
                int BTFrom  = Integer.parseInt(rs.getString("BTFromAccount"));
                int BTTo  = Integer.parseInt(rs.getString("BTToAccount"));
                
                
                LocalDate d1 = LocalDate.parse(formatter.format(date), DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate d2 = LocalDate.parse(rs.getString("BTCreationDate"), DateTimeFormatter.ISO_LOCAL_DATE);
                Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
                long diffDays = diff.toDays();
                
                if(diffDays == 1 || diffDays==0){
                    HttpSession session = request.getSession(true);
                    session.setAttribute("cancelBool", 1);
                    String Update1 = "UPDATE mydb.bankaccount SET BACurrentBalance = (BACurrentBalance+" +amountToBeCanceled +") WHERE Customer_CustomerID = "+ BTFrom ;
                    Stmt2.executeUpdate(Update1);
                    String Update2 = "UPDATE mydb.bankaccount SET BACurrentBalance = (BACurrentBalance-" +amountToBeCanceled +") WHERE Customer_CustomerID = "+ BTTo ;
                    Stmt3.executeUpdate(Update2);
                    String DeleteTransaction = "DELETE FROM mydb.banktransaction WHERE BankTransactionID = "+BankTransactionID;
                    Stmt4.executeUpdate(DeleteTransaction);
                    rs.close();
                    Stmt.close();
                    Stmt2.close();
                    Stmt3.close();
                    Stmt4.close();
                    Con.close();
                    
                    response.sendRedirect("transactions.jsp");
                    
                    
                }else{
                    rs.close();
                    Stmt.close();
                    Stmt2.close();
                    Stmt3.close();
                    Stmt4.close();
                    Con.close();
                    response.sendRedirect("transactions.jsp");
                }
                
                
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
