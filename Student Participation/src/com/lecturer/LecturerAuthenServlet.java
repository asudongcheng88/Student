package com.lecturer;

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
 * Servlet implementation class LecturerAuthenServlet
 */
@WebServlet("/LecturerAuthenServlet")
public class LecturerAuthenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LecturerAuthenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		String lecId = request.getParameter("lec-id");
		String lecPass = request.getParameter("lec-pass");
		
		
		
		//System.out.println(lecId);
		//System.out.println(lecPass);
		
		LecturerDAO lecturerDAO = new LecturerDAO();
		
		boolean exist = lecturerDAO.loginLect(lecId, lecPass);
		
		//System.out.println("lec login = "+exist);

		
		if (exist == true){
			session.setAttribute("lecId", lecId);
			System.out.println("is true");
			response.sendRedirect("LecturerHome.jsp");
			
		}else{
			
			response.sendRedirect("LecLoginError.html");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String lecId = request.getParameter("reg-lec-id");
		String lecName = request.getParameter("reg-lec-name");
		String lecEmail = request.getParameter("reg-lec-email");
		String lecPass = request.getParameter("reg-lec-pass");
		
		//System.out.println("id = "+lecName);
		
		LecturerDAO lecturerDAO = new LecturerDAO();
		lecturerDAO.regLecturer(lecId, lecName, lecEmail, lecPass);
		//lecturerDAO.regLecturer("adad", "adas", "adasd", "asdasd");
		
		response.sendRedirect("LecLogin.html");	
		
		
	}

}
