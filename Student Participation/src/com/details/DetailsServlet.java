package com.details;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.student.Student;
import com.student.StudentDAO;

/**
 * Servlet implementation class DetailsServlet
 */
@WebServlet("/DetailsServlet")
public class DetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		HttpSession session = request.getSession(true);
		
		List<Student> studentArray = new ArrayList<Student>();
		
		StudentDAO studentDAO = new StudentDAO();
		
		if(action.equalsIgnoreCase("student list")){
			
			//System.out.println(action);
			String lecId = (String) session.getAttribute("lecId");
			String subjName = request.getParameter("selectedSubject");
			String groupId = request.getParameter("selectedGroup");
			
			studentArray = studentDAO.studentList(lecId, subjName, groupId);
			out.print(gson.toJson(studentArray));
			
			
		}else if(action.equalsIgnoreCase("classmate details")){
			
			//System.out.println("classmate details");
			String studId = (String) session.getAttribute("studId");
			String subjName = request.getParameter("subjName");
			String groupId = request.getParameter("groupId");
			
			studentArray = studentDAO.classMateDetails(subjName, groupId);
			out.print(gson.toJson(studentArray));
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
