package com.enroll;

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
import com.status.Status;
import com.status.StatusDAO;
import com.student.StudentDAO;

/**
 * Servlet implementation class EnrollServlet
 */
@WebServlet("/EnrollServlet")
public class EnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnrollServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		HttpSession session = request.getSession(true);
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		String lecId = (String) session.getAttribute("lecId");
		
		List<Enroll> enrollArray = new ArrayList<Enroll>();	
		EnrollDAO enrollDAO = new EnrollDAO();
		
		//System.out.println(action);
		
		if(action.equalsIgnoreCase("drop request")){			
			
			
			enrollArray = enrollDAO.showLeaveRequest(lecId);
			
			out.print(gson.toJson(enrollArray));
			
		}else if(action.equalsIgnoreCase("student point list")){
			
			
			
			String subjCode = request.getParameter("subjCode");
			String groupId = request.getParameter("groupId");
			
			//System.out.println(subjCode);
			//System.out.println(groupId);
			
			enrollArray = enrollDAO.studentPointList(subjCode, groupId);
					
			out.print(gson.toJson(enrollArray));
			
		}else if(action.equalsIgnoreCase("reminder drop request")){
			
			System.out.println(action);
			
			boolean gotRequest = enrollDAO.gotDropRequest(lecId);
			
			//System.out.println(gotRequest);
			
			out.print(gotRequest);
			
		}else if(action.equalsIgnoreCase("enroll student")){
			
			String groupId = request.getParameter("groupId");
			String subjCode = request.getParameter("subjCode");
			
			//System.out.println(groupId);
			//System.out.println(subjCode);
			
			StudentDAO studentDAO = new StudentDAO();
			//EnrollDAO enrollDAO = new EnrollDAO();
			String studId = (String) session.getAttribute("studId");
			
			boolean isStudent = studentDAO.checkStudent(studId, subjCode);
			
			System.out.println(isStudent);
			
			if(isStudent){
				
				int classId = enrollDAO.getClassForStudent(subjCode, groupId);

				enrollDAO.enrollClassForStudent(studId, classId); 
				
				response.sendRedirect("http://localhost:8081/FinalYearProject/StudRegClass.jsp:186");
				
			}else{
				
				response.sendRedirect("http://loca");
			}
			String exist = gson.toJson(isStudent);
			out.print(exist);

		}
			
		
		
		
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		System.out.println(action);
		HttpSession session = request.getSession(true);
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		if(action.equalsIgnoreCase("enroll student")){
			
			String groupId = request.getParameter("groupId");
			String subjCode = request.getParameter("subjCode");
			
			//System.out.println(groupId);
			//System.out.println(subjCode);
			
			StudentDAO studentDAO = new StudentDAO();
			EnrollDAO enrollDAO = new EnrollDAO();
			String studId = (String) session.getAttribute("studId");
			
			boolean isStudent = studentDAO.checkStudent(studId, subjCode);
			
			System.out.println(isStudent);
			
			if(isStudent){
				
				int classId = enrollDAO.getClassForStudent(subjCode, groupId);

				enrollDAO.enrollClassForStudent(studId, classId); 
				
			}
			String exist = gson.toJson(isStudent);
			out.print(exist);
			out.flush();
			out.close();
			
			
			
		}else if(action.equalsIgnoreCase("request leave")){
			
			int groupId = Integer.parseInt(request.getParameter("classId"));
			String studId = (String) session.getAttribute("studId");
			
			EnrollDAO enrollDAO = new EnrollDAO();
			
			enrollDAO.requestLeave(studId, groupId);
			
		}else if(action.equalsIgnoreCase("approve drop request")){
			
			int classId = Integer.parseInt(request.getParameter("classId"));
			String studId = request.getParameter("studId");
			//System.out.println(classId);
			
			EnrollDAO enrollDAO = new EnrollDAO();
			
			enrollDAO.approveRequest(studId, classId);
		}
	}

}
