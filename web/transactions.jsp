

<%-- 
    Document   : transactions
    Created on : Dec 20, 2020, 11:33:50 AM
    Author     : Safix
    Name : Mohamed Safi Ahmed
    ID : 20170237
    Group : DS_IS_1
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.Random"%>
<%@page import="java.sql.Date"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Safix Bank</title>
        <link rel="icon" href="banking.png">
        <link rel="stylesheet" type="text/css" href="transactionsCSS.css">
    </head>
    <body>
        <%
            
            String CustomerID  = request.getSession().getAttribute("sessionCI").toString();
            int cancelBool  = Integer.parseInt(request.getSession().getAttribute("cancelBool").toString());
             

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
                Statement Stmt5 = null;
                Statement Stmt6 = null;
                Statement Stmt7 = null;
                Statement Stmt8 = null;
                Statement Stmt9 = null;
                Statement Stmt10 = null;
                
                
                
                ResultSet rs = null;
                ResultSet rs2 = null;
                ResultSet rss = null;
                ResultSet rsss = null;
                ResultSet rssss = null;
                
                
                ResultSet rs4 = null;
                ResultSet rs5 = null;
                
                
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
                
                
                String query = "Select COUNT(*) from mydb.banktransaction";
                String query2 = "SET @row_number = 0;";
                String query3 = "SELECT (@row_number:=@row_number + 1) AS num,BankTransactionID,BTCreationDate,BTAmount,BTToAccount FROM mydb.banktransaction WHERE BTFromAccount = " + Integer.parseInt(CustomerID)  ;
                Con = DriverManager.getConnection(url, user, password);
                Stmt = Con.createStatement();
                Stmt2 = Con.createStatement();
                Stmt3 = Con.createStatement();
                
                Stmt4 = Con.createStatement();
                Stmt5 = Con.createStatement();
                Stmt6 = Con.createStatement();
                Stmt7 = Con.createStatement();
                Stmt8 = Con.createStatement();
                Stmt9 = Con.createStatement();
                Stmt10 = Con.createStatement();
                
               
                
                Stmt2.executeQuery(query2);
                rss = Stmt3.executeQuery(query3);
               
                
                
                
                
                
                
                Random random = new Random();
                
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                
                %>
                <table id="t">
                <tr>
    <th>BankTransactionID</th>
    <th>BTCreationDate</th> 
    <th>BTAmount</th>
    <th>BTToAccount</th>
                </tr>
             <%
                while(rss.next()){
             %>
    <tr>
        
    <td><%=rss.getString("BankTransactionID")%></td>
    <td><%=rss.getString("BTCreationDate")%></td>
    <td><%=rss.getString("BTAmount")%>$</td>
    <td><%=rss.getString("BTToAccount")%></td>
  </tr>
  
  <% }

  %>
  
                </table>
  
  <h1>MAKE A TRANSACTION</h1>
  <form >
        <input type="text" placeholder="AMOUNT" name="AmountToBeTransfered"> <br>
        <input type="text" placeholder="TO" name="ToID"><br><br>
        <input  style="background-color: greenyellow;" type="submit" name="TRANSFER" value="TRANSFER">
  </form>
  <% 
      
   String x = request.getParameter("submit");
   
   rsss = Stmt9.executeQuery("SELECT * FROM mydb.bankaccount WHERE Customer_CustomerID =  " + Integer.parseInt(CustomerID) );
   rsss.next();
   
   
   
   if((request.getParameter("TRANSFER") != null))
   {
       
       
       if(rsss.getInt("BACurrentBalance") >= Integer.parseInt(request.getParameter("AmountToBeTransfered")))
       {
           int AmountToBeTransfered = Integer.parseInt(request.getParameter("AmountToBeTransfered"));
           int ToID  = Integer.parseInt(request.getParameter("ToID"));
           rssss = Stmt10.executeQuery("SELECT * FROM mydb.bankaccount WHERE Customer_CustomerID =  "+ Integer.parseInt(request.getParameter("ToID")) );
      
           if(rssss.isBeforeFirst()){
               String Update1 = "UPDATE mydb.bankaccount SET BACurrentBalance = (BACurrentBalance+" +AmountToBeTransfered +") WHERE Customer_CustomerID = "+ ToID ;
               Stmt4.executeUpdate(Update1);
               String Update2 = "UPDATE mydb.bankaccount SET BACurrentBalance = (BACurrentBalance-" +AmountToBeTransfered +") WHERE Customer_CustomerID = "+ Integer.parseInt(CustomerID); 
               Stmt5.executeUpdate(Update2);

               String getID = "SELECT * From mydb.bankaccount WHERE Customer_CustomerID = " + Integer.parseInt(CustomerID) ;
               rs4 = Stmt7.executeQuery(getID);
               rs4.next();


               String Insert1 = "INSERT INTO mydb.banktransaction (BankTransactionID, BTCreationDate, BTAmount, BTFromAccount, BTToAccount, BankAccount_BankAccountID)" 
                               +" VALUES( "
                               + random.nextInt(100) + ","
                               + "'" + formatter.format(date) + "'"  +", "
                               + AmountToBeTransfered + ", "
                               + Integer.parseInt(CustomerID) + ", "
                               + ToID + ", "
                               + rs4.getString("BankAccountID")
                               +" )";
               Stmt8.executeUpdate(Insert1);




                                  
       
  %>
       <div class="alert">
  <span  class="closebtn" onclick="this.parentElement.style.display='none'; window.location.href='transactions.jsp';">&times;</span> 
  <strong>Congrats!</strong> Your Transaction  Successfully Done.
</div>
  <% }else{
  %>
  <div style="background-color: red; padding: 20px; color: black; margin-top: -20px; text-align: center;">
  <span  class="closebtn" onclick="this.parentElement.style.display='none'; window.location.href='transactions.jsp';">&times;</span> 
  <strong>Sorry!</strong> Your Transaction Cannot be Done(We Cannot found a Bank Account with this ID).
</div>
  <% }}else{ %>
  <div style="background-color: red; padding: 20px; color: black; margin-top: -20px; text-align: center;">
  <span  class="closebtn" onclick="this.parentElement.style.display='none'; window.location.href='transactions.jsp';">&times;</span> 
  <strong>Sorry!</strong> Your Transaction Cannot be Done(Your Balance is not enough).
</div>
<%}}
  %>
  
  
    
  <h1 class="h11">CANCEL A TRANSACTION</h1>
  <form class="form2" action="cancelCheck">
        <input type="text" placeholder="BankTransactionID" name="BankTransactionID"> <br>
        <input style="background-color: red;" type="submit" value="CANCEL THIS TRANSACTION">
  </form>
  <button style='color: #fff !important;
text-transform: uppercase;
text-decoration: none;
background: green;
padding: 20px;
border-radius: 5px;
display: inline-block;
border: violet;
width: 150px;
height: 60px;
margin-bottom: auto;
transition: all 0.4s ease 0s; margin-left: 840px;' onclick="window.location.href='customerhome.jsp';">Account Home</button>

                <button style='color: white !important;
text-transform: uppercase;
text-decoration: none;
background: #ed3330;
padding: 20px;
border-radius: 5px;
display: inline-block;
border: violet;
width: 150px;
height: 60px;
margin-bottom: auto;
transition: all 0.4s ease 0s; margin-left: 50px;' onclick="window.location.href='index.html';">Logout</button>
                    
                <% 
                    if(cancelBool == 1){ %>
            <div class="alert">
  <span  class="closebtn" onclick="this.parentElement.style.display='none'; window.location.href='transactions.jsp';">&times;</span> 
  <strong>Congrats!</strong> Your Transaction Successfully Canceled .
</div>    
           
           <%        }

session.setAttribute("cancelBool", 0);

           

                    
              

                
                Con.close();
                
                
            }catch (Exception ex) {
                ex.printStackTrace();
            }
            %>
    </body>
</html>
