package com.group;

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

import com.google.gson.Gson;

/**
 * Servlet implementation class GroupServlet
 */
@WebServlet("/GroupServlet")
public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		GroupDAO groupDAO = new GroupDAO();
		List<Group> groupArray = new ArrayList<Group>();
		HttpSession session = request.getSession(true);
		
		String action = request.getParameter("action");
		
		if(action.equalsIgnoreCase("Register")){			
			
			groupArray = groupDAO.groupList();
			
			//out.print(gson.toJson(groupArray));
			
			
		}else if(action.equalsIgnoreCase("Student details")){
			
			String lecId = (String) session.getAttribute("lecId");
			String subjName = request.getParameter("selectedSubject");
			
			groupArray = groupDAO.lecSubjectGroupList(lecId, subjName);
			
			//out.print(gson.toJson(groupArray));
			
		}else if(action.equalsIgnoreCase("classmate details")){
			//System.out.println(action);
			String studId = (String) session.getAttribute("studId");
			String subjName = request.getParameter("subjName");
			
			//System.out.println("stud id = "+studId);
			//System.out.println("subject name = "+subjName);
			groupArray = groupDAO.studentGroupList(studId, subjName);
			
		}
		out.print(gson.toJson(groupArray));
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
