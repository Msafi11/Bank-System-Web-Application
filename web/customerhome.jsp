<%-- 
    Document   : customerhome
    Created on : Dec 18, 2020, 7:03:56 PM
    Author     : Safix
    Name : Mohamed Safi Ahmed
    ID : 20170237
    Group : DS_IS_1
--%>

<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Safix Bank</title>
        <link rel="stylesheet" type="text/css" href="page2style.css">
        <link rel="icon" href="banking.png">
    </head>
    <body>
        <%
            String CustomerID  = request.getSession().getAttribute("sessionCI").toString();
            try{
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://127.0.0.1:3306/mydb";
                String user = "root";
                String password = "root";
                Connection Con = null;
                Statement Stmt = null;
                Statement Stmt2 = null;
                ResultSet rs = null;
                ResultSet rs2 = null;
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
                
                
                String query = "SELECT * FROM mydb.bankaccount  Where Customer_CustomerID = "
                        +Integer.parseInt(CustomerID);
                String query2 = "SELECT * FROM mydb.customer  Where CustomerID = "
                        +Integer.parseInt(CustomerID);
                Con = DriverManager.getConnection(url, user, password);
                Stmt = Con.createStatement();
                Stmt2 = Con.createStatement();
                rs = Stmt.executeQuery(query);
                rs2 = Stmt2.executeQuery(query2);
                
                if(rs.isBeforeFirst()){
                    
                
                
                while(rs2.next()){ %>
                <h1 > &#11088 Welcome Back   <%=rs2.getString("CustomerName") %> &#11088 </h1>
                
                <% }
                    while(rs.next()){ %>
                
                <h1 > Your Current Balance =  <%=rs.getInt("BACurrentBalance")  %> &#128178 </h1>
                <h1 > Created at   <%=rs.getDate("BACreationDate")  %> &#128198</h1>
                
                <button style='color: #fff !important;
text-transform: uppercase;
text-decoration: none;
background: green;
padding: 20px;
border-radius: 5px;
display: inline-block;
border: violet;
width: 150px;
height: auto;
transition: all 0.4s ease 0s; margin-left: 490px;' onclick="window.location.href='transactions.jsp';">Transactions</button>

                <button style='color: white !important;
text-transform: uppercase;
text-decoration: none;
background: #ed3330;
padding: 20px;
border-radius: 5px;
display: inline-block;
border: violet;
width: 150px;
height: auto;
transition: all 0.4s ease 0s; margin-left: 50px;' onclick="window.location.href='index.html';">Logout</button>
                
                <% 
                    }
Con.close();
Stmt.close();
rs.close();
}
else{ %>

<h1>&#9940 You Don't Have A Bank Account So You Must to Create One Now &#9940</h1>
<button style="align-items: center; margin-left: 600px;
  height: 50px;justify-content: center;display: flex; background-color: white;" onclick="document.location='addaccount'">ADD ACCOUNT</button>
<%
}

                
                
                    
              
                }catch (Exception ex) {
                ex.printStackTrace();
            }
%>
        
           
        
         
       
        
        
        
        
    </body>
</html>
