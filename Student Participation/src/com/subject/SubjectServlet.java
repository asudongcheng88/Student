package com.subject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.classes.ClassDAO;
import com.classes.Classes;
import com.google.gson.Gson;
import com.student.StudentDAO;


/**
 * Servlet implementation class SubjectServlet
 */
@WebServlet("/SubjectServlet")
public class SubjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubjectServlet() {
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
		
		if(action.equalsIgnoreCase("for student details")){
			
			
			List<Subject> subjectArray = new ArrayList<Subject>();
			SubjectDAO subjectDAO = new SubjectDAO();
			
			String lecId = (String) session.getAttribute("lecId");
			
			System.out.println(lecId);

			subjectArray = subjectDAO.listTeachSubject(lecId);
			
			out.print(gson.toJson(subjectArray));
			
		}
		else if (action.equalsIgnoreCase("distinct subject")){
			//System.out.println("Enter servlet");
			List<Classes> subjectArray = new ArrayList<Classes>();
			ClassDAO classDAO = new ClassDAO();
			subjectArray = classDAO.distinctSubjectList();
			
			//System.out.println(subjectArray);
			
			out.print(gson.toJson(subjectArray));
			
		}else if(action.equalsIgnoreCase("distinct group")){
			List<Classes> groupArray = new ArrayList<Classes>();
			ClassDAO classDAO = new ClassDAO();
			
			String subjCode = request.getParameter("subjCode");
			
			//System.out.println(subjCode);
			
			groupArray = classDAO.distinctGroupList(subjCode);
			
			out.print(gson.toJson(groupArray));
			
		}else if(action.equalsIgnoreCase("classmate details")){
			
			List<Subject> subjectArray = new ArrayList<Subject>();		
			
			String studId = (String) session.getAttribute("studId");
			
			SubjectDAO subjectDAO = new SubjectDAO();
			
			subjectArray = subjectDAO.studentSubjectList(studId);
			
			out.print(gson.toJson(subjectArray));
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
