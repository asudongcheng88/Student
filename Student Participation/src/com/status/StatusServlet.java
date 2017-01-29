package com.status;

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

import com.enroll.Enroll;
import com.enroll.EnrollDAO;
import com.google.gson.Gson;

/**
 * Servlet implementation class StatusServlet
 */
@WebServlet("/StatusServlet")
public class StatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatusServlet() {
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
		
		if(action.equalsIgnoreCase("student point list")){
			
			String subjCode = request.getParameter("subjCode");
			String groupId = request.getParameter("groupId");
			
			EnrollDAO enrollDAO = new EnrollDAO();
			
			List<Enroll> studentPointList = new ArrayList<Enroll>();
			
			studentPointList = enrollDAO.studentPointList(subjCode, groupId);
			
			out.print(gson.toJson(studentPointList));
			
		}else if(action.equalsIgnoreCase("log out")){
			
			HttpSession session = request.getSession(true);
			
			session.setAttribute("lecId", null);
			
			String lecId = (String) session.getAttribute("lecId");
			
			out.print(lecId);
			
		}else if(action.equalsIgnoreCase("sign in")){
			
			HttpSession session = request.getSession(true);
			
			String lecId = (String) session.getAttribute("lecId");
			
			out.print(lecId);
			
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
