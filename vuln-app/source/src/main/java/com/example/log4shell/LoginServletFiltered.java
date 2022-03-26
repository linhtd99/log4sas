package com.example.log4shell;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.sun.deploy.net.HttpRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;


@WebServlet(name = "loginServletFiltered", value = "/loginFiltered")
public class LoginServletFiltered extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = req.getParameter("uname");
        String password = req.getParameter("password");
	String userAgent = req.getHeader("user-agent");
	if(Pattern.matches("(.*?)\\$(\\{|%7B)jndi:(ldap[s]?|rmi|dns|nis|iiop|corba|nds|http):/[^\n]+(.*?)",userAgent)){
	    userAgent = userAgent.replaceAll("\\$(\\{|%7B)jndi:(ldap[s]?|rmi|dns|nis|iiop|corba|nds|http):/[^\n|\\s]+","[stripped out]");
	}

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
	    out.println("User-Agent: " + userAgent);
        }
    }

    public void destroy() {
    }
}
