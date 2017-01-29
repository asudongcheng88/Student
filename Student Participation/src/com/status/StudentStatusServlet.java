package com.status;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class StudentStatusServlet
 */
@WebServlet("/StudentStatusServlet")
public class StudentStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentStatusServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();
		
		if(action.equalsIgnoreCase("log out")){
			
			HttpSession session = request.getSession(true);
			
			session.setAttribute("studId", null);
			
			String studId = (String) session.getAttribute("studId");
			
			out.print(studId);
			
		}else if(action.equalsIgnoreCase("sign in")){
			
			HttpSession session = request.getSession(true);
			
			String studId = (String) session.getAttribute("studId");
			
			out.print(studId);
			
		}
		
		out.flush();
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
