package com.student;

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
 * Servlet implementation class StudentAuthenServlet
 */
@WebServlet("/StudentAuthenServlet")
public class StudentAuthenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentAuthenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		
		String studId = request.getParameter("stud-id");
		String studPass = request.getParameter("stud-pass");

		
		session.setAttribute("studId", studId);
		
		
		
		StudentDAO studentDAO = new StudentDAO();
		
		boolean exist = studentDAO.loginStud(studId, studPass);

		if(exist == true){
			
			response.sendRedirect("StudentHome.jsp");	
			
		}
		else{
			
			response.sendRedirect("StudLoginError.html");
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String studId = request.getParameter("reg-stud-id");
		String studName = request.getParameter("reg-stud-name");
		String studEmail = request.getParameter("reg-stud-email");
		String studPass = request.getParameter("reg-stud-pass");
		String studPhone = request.getParameter("reg-stud-phone");
		
		//System.out.println("student id = "+studId);
		//System.out.println(studName);
		//System.out.println(studEmail);
		//System.out.println(studPass);
		//System.out.println(studPhone);
		
		Student student = new Student();
		student.setStudId(studId);
		student.setStudName(studName);
		student.setStudEmail(studEmail);
		student.setStudPass(studPass);
		student.setStudPhone(studPhone);
		
		
		StudentDAO studentDAO = new StudentDAO();
			
		studentDAO.regStudent(student);
			
		response.sendRedirect("StudLogin.html");	
			

		
		
	}

}
