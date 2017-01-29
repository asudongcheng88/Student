package com.reward;

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
import com.notification.NotiDAO;
import com.student.Student;
import com.student.StudentDAO;

/**
 * Servlet implementation class RewardServlet
 */
@WebServlet("/RewardServlet")
public class RewardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RewardServlet() {
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
		
		String action = request.getParameter("action");
		String lecId = (String) session.getAttribute("lecId");
		
		
		if(action.equalsIgnoreCase("view reward")){
			
			RewardDAO rewardDAO = new RewardDAO();
			
			List<Reward> rewardArray1 = new ArrayList<Reward>();
			List<Reward> rewardArray2 = new ArrayList<Reward>();
			List<Reward> rewardArray3 = new ArrayList<Reward>();
			
			rewardArray1 = rewardDAO.getRewardList(lecId);
			rewardArray2 = rewardDAO.getFixRewardList();
			
			//rewardArray3 = rewardDAO.concatenate(rewardArray1, rewardArray2);
			
			for(Reward rew:rewardArray1){
				
				Reward reward = new Reward();
				reward.setRewardType(rew.getRewardType());
				reward.setRewardPoint(rew.getRewardPoint());
				
				rewardArray3.add(reward);
				
			}
			
			for(Reward rew:rewardArray2){
				
				Reward reward = new Reward();
				reward.setRewardType(rew.getRewardType());
				reward.setRewardPoint(rew.getRewardPoint());
				
				rewardArray3.add(reward);
				
			}
			
			out.print(gson.toJson(rewardArray3));
			
		}else if(action.equalsIgnoreCase("give point")){

			
			String groupId = request.getParameter("selectedGroup");
			String subjName = request.getParameter("selectedSubject");
			
			
			List<Student> studentArray = new ArrayList<Student>();
			StudentDAO studentDAO = new StudentDAO();
			
			//System.out.println("parameter");
			//System.out.println(lecId);
			//System.out.println(subjName);
			//System.out.println(groupId);
			
			studentArray = studentDAO.studentList(lecId, subjName, groupId);
			
			RewardDAO rewardDAO = new RewardDAO();
			List<Reward> rewardArray = new ArrayList<Reward>();
			List<Reward> fixRewardArray = new ArrayList<Reward>();
			fixRewardArray = rewardDAO.getFixRewardList();
			
			rewardArray = rewardDAO.getRewardList(lecId);
			
			//System.out.println(studentArray);
			
			String json1 = gson.toJson(studentArray);
			String json2 = gson.toJson(rewardArray);
			String json3 = gson.toJson(fixRewardArray);
			
			String bigjson = json1 + "%" + json2 + "%" + json3;
			out.print(bigjson);
			
		}else if(action.equalsIgnoreCase("view reward for student")){
			
			String studId = (String) session.getAttribute("studId");
			String subjCode = request.getParameter("subjCode");
			
			//System.out.println(studId);
			//System.out.println(subjCode);
			
			
			RewardDAO rewardDAO = new RewardDAO();
			List<Reward> rewardArray1 = new ArrayList<Reward>();
			List<Reward> rewardArray2 = new ArrayList<Reward>();
			List<Reward> rewardArray3 = new ArrayList<Reward>();
			
			rewardArray1 = rewardDAO.getRewardListForStudent(studId, subjCode);
			
			rewardArray2 = rewardDAO.getFixRewardList();
			
			for(Reward rew:rewardArray1){
				
				Reward reward = new Reward();
				reward.setRewardType(rew.getRewardType());
				reward.setRewardPoint(rew.getRewardPoint());
				
				rewardArray3.add(reward);
				
			}
			
			for(Reward rew:rewardArray2){
				
				Reward reward = new Reward();
				reward.setRewardType(rew.getRewardType());
				reward.setRewardPoint(rew.getRewardPoint());
				
				rewardArray3.add(reward);
				
			}
			
			//System.out.println(rewardArray.size());
			
			out.print(gson.toJson(rewardArray3));
		}
		
		out.flush();
		out.close();
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		HttpSession session = request.getSession(true);
		
		String lecId = (String) session.getAttribute("lecId");
		String rewardType = request.getParameter("rewardType");
		
		RewardDAO rewardDAO = new RewardDAO();
		
		if(action.equalsIgnoreCase("add reward")){			
			
			int rewardPoint = Integer.parseInt(request.getParameter("rewardPoint"));
			
			boolean exist = rewardDAO.rewardIsExist(rewardType);
			
			System.out.print(exist);
			
			if(!exist){
				
				rewardDAO.addReward(lecId, rewardType, rewardPoint);
				
			}
			
			out.print(exist);
			out.flush();
			out.close();
			
		}else if(action.equalsIgnoreCase("give reward")){
			
			//int rewardPoint = rewardDAO.getRewardValue(lecId, rewardType);
			
			//System.out.println("This is reward point "+rewardPoint);
			int rewardPoint = Integer.parseInt(request.getParameter("rewardPoint"));
			String groupId = request.getParameter("groupId");
			String subjCode = request.getParameter("subjCode");
			String studId = request.getParameter("studId"); 
			String subjName = request.getParameter("subjName");
			
			StudentDAO studentDAO = new StudentDAO();
			
			int classId = studentDAO.getStudentClass(studId, subjCode, groupId);
			
			System.out.println("this is subjCode id "+subjCode);
			
			System.out.println("this is group id "+groupId);
			
			System.out.println("this is class id "+classId);
			
			System.out.println("this is student id "+studId);
			
			System.out.println("this is subject name "+subjName);
			
			rewardDAO.giveStudentReward(studId, classId, rewardPoint);
			
			String todayDate = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
			
			System.out.println(todayDate);
			
			NotiDAO notiDAO = new NotiDAO();
			
			notiDAO.insertNoti(studId, subjName, rewardType, todayDate);
		}
		
		
	}

}
