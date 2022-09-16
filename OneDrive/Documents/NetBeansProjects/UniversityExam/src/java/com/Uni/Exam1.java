/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.Uni;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.MultipartConfig;

import javax.servlet.http.Part;
import javax.servlet.http.*;
@MultipartConfig
public class Exam1 extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
           /* out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Exam1</title>");            
            out.println("</head>");
            out.println("<body>");*/
            //out.println("<h1>Servlet Exam1 at " + request.getContextPath() + "</h1>");
            String studname = request.getParameter("txtUser");
            String mothername= request.getParameter("txtmum");
            String PRN = request.getParameter("txtprn");
            String mob = request.getParameter("txtnum");
            String mail = request.getParameter("txtmail");
            String state = request.getParameter("txtstate");
           
            String pass = request.getParameter("txtpwd2");
            Part part = request.getPart("image");
            //String fileName = part.getSubmittedFileName();
            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            out.println(fileName);
            
            //connection
            
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/University","root","vandit@123");
                
                //query..
                String q = "insert into Exam(name , mother , prn ,mobile , email , state , password , photo) values (?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(q);
                pst.setString(1,studname);
                 pst.setString(2,mothername);
                  pst.setString(3,PRN);
                   pst.setString(4,mob);
                    pst.setString(5,mail);
                     pst.setString(6,state);
                     
                      pst.setString(7,pass);
                      pst.setString(8,fileName);
                      
                       pst.executeUpdate();
                       
                       //upload photo...in local folder..
                       InputStream is = part.getInputStream();
                       byte[] data = new byte[is.available()];
                       is.read(data);
                       String path = request.getRealPath("/")+"img"+File.separator+fileName;
                       
                       FileOutputStream fout = new FileOutputStream(path);
                       fout.write(data);
                       
                       fout.close();
                         out.println("Database updation Done!....");
            }
            catch (ClassNotFoundException ex) {
            Logger.getLogger(Exam1.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(Exam1.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            //out.println("</body>");
            //out.println("</html>");
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
