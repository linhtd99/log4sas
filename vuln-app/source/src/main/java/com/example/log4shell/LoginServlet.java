package com.example.log4shell;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.sun.deploy.net.HttpRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = req.getParameter("uname");
        String password = req.getParameter("password");
	String userAgent = req.getHeader("user-agent");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");

        if(userName.equals("admin") && password.equals("password")){
            out.println("Welcome Back Admin");
        }
        else{

            // vulnerable code
            Logger logger = LogManager.getLogger(com.example.log4shell.log4j.class);
            //logger.error(userName);
	    logger.error(userAgent);

            out.println("<code>The credentials you entered was invalid, <u>the request's information will be logged</u> </code>");
	    out.println("User-Agent: "+userAgent);
        }
    }

    public void destroy() {
    }
}
