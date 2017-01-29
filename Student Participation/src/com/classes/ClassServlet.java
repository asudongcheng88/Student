package com.classes;

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

import com.enroll.EnrollDAO;
import com.google.gson.Gson;
import com.subject.Subject;
import com.subject.SubjectDAO;

/**
 * Servlet implementation class ClassServlet
 */
@WebServlet("/ClassServlet")
public class ClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassServlet() {
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
		
		if(action.equalsIgnoreCase("view class") || action.equalsIgnoreCase("add point")){
		
		/*----------List all teaching subject in class menu--------*/
		
			
			
			
			String lecId = (String) session.getAttribute("lecId");
			
			ClassDAO classDAO = new ClassDAO();
			List<Classes> classArray = new ArrayList<Classes>();
			
			classArray = classDAO.classDetails(lecId);
			
			out.print(gson.toJson(classArray));
			
		
		}else if(action.equalsIgnoreCase("view class for student")){			//view class for student
			
			List<Classes> classArray = new ArrayList<Classes>();
			
			String studId = (String) session.getAttribute("studId");
			
			ClassDAO classDAO = new ClassDAO();
			
			classArray = classDAO.studentViewClass(studId);
			out.print(gson.toJson(classArray));
			
		}else if(action.equalsIgnoreCase("subject list")){
			
			String studId = (String) session.getAttribute("studId");
			
			System.out.println("this is stud id "+studId);
			
			SubjectDAO subjectDAO = new SubjectDAO();
			List<Subject> subjectList = new ArrayList<Subject>();
			
			subjectList = subjectDAO.studentSubjectList(studId);
			
			out.print(gson.toJson(subjectList));
			
		}
		
		out.flush();
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String groupId = request.getParameter("groupId");
		String subjCode = request.getParameter("subjCode");
		String subjName = request.getParameter("subjName");
		
		HttpSession session = request.getSession(true);
		String lecId = (String) session.getAttribute("lecId");
		
		//System.out.println(groupId);
		//System.out.println(subjCode);
		//System.out.println(subjName);
		//System.out.println(lecId);
		
		SubjectDAO subjectDAO = new SubjectDAO();
		
		subjectDAO.registerSubject(subjCode, subjName);
		
		ClassDAO classDAO = new ClassDAO();
		
		classDAO.registerClass(groupId, subjCode, lecId);
		
		
	}

}
