package com.attendance;

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

import com.classes.Classes;
import com.enroll.EnrollDAO;
import com.google.gson.Gson;
import com.group.Group;
import com.group.GroupDAO;
import com.student.Student;
import com.student.StudentDAO;
import com.subject.Subject;
import com.subject.SubjectDAO;

/**
 * Servlet implementation class AttendanceServlet
 */
@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendanceServlet() {
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
		
		String lecId = (String) session.getAttribute("lecId");
		
		if(action.equalsIgnoreCase("student list")){
			
			//System.out.println(action);
			
			String subjName = request.getParameter("selectedSubject");
			String groupId = request.getParameter("selectedGroup");
			
			studentArray = studentDAO.studentList(lecId, subjName, groupId);
			
			String todayDate = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
			
			/*
			 * This date formate is dd/mm/yyyy
			 * but database accept mm/dd/yyyy format
			 */
			
			//System.out.println(todayDate);
			
			String json1 = gson.toJson(studentArray);
			String json2 = gson.toJson(todayDate);
			String bigData = json1 + "%" + json2;
			out.print(bigData);
			
			
			
		}else if(action.equalsIgnoreCase("student attendance")){
			System.out.println(action);
			PrintWriter outStudAttd = response.getWriter();
			
			String subjCode = request.getParameter("subjCode");
			String subjName = request.getParameter("subjName");
			String groupId = request.getParameter("selectedGroup");
			
			//System.out.println("subj code = "+subjCode);
			
			AttendanceDAO attdDAO = new AttendanceDAO();
			
			List<Attendance> attdArray = new ArrayList<Attendance>();
			
			//this line description please refer in method
			
			attdArray = attdDAO.studentAttendance(lecId, subjName, subjCode, groupId);
			
			out.print(gson.toJson(attdArray));

			
		}else if(action.equalsIgnoreCase("student attendance alert")){
			
			System.out.println(action);
			
			PrintWriter outStudAttdAlert = response.getWriter();
			
			AttendanceDAO attdDAO = new AttendanceDAO();
			
			List<Attendance> attdAlertArray = new ArrayList<Attendance>();
			
			List<Group> subjGroupArray = new ArrayList<Group>();
			
			//get subject name and subject code from lec id
			
			SubjectDAO subjectDAO = new SubjectDAO();
			
			List<Subject> teachSubject = subjectDAO.listTeachSubject(lecId);
			
			subjGroupArray = attdDAO.getSubjectAndGroup(teachSubject, lecId);
			
			attdAlertArray = attdDAO.attendanceAlert(subjGroupArray, lecId);
			
			out.print(gson.toJson(attdAlertArray));
			
		}else if(action.equalsIgnoreCase("attendance record")){
			
			HttpSession studSess = request.getSession(true);
			
			String studId = (String) studSess.getAttribute("studId");
			
			List<Subject> subjectArray = new ArrayList<Subject>();
			SubjectDAO subjectDAO = new SubjectDAO();

			GroupDAO groupDAO = new GroupDAO();
			AttendanceDAO attdDAO = new AttendanceDAO();
			
			List<Attendance> attdRecord = new ArrayList<Attendance>();
			
			subjectArray = subjectDAO.studentSubjectList(studId);
			
			for(Subject sub:subjectArray){
				
				String subjCode = sub.getSubjCode();
				String subjName = sub.getSubjName();
				
				String groupId = groupDAO.getStudentGroupId(studId, subjName);
				
				int present = attdDAO.attendanceRecord(studId, subjCode, groupId);
				
				int classHeld = attdDAO.countDistinctDate(subjCode, groupId);
				
				int absent = classHeld - present;
					
				Attendance attd = new Attendance();
					
				attd.setSubjCode(subjCode);
				attd.setSubjName(subjName);
				attd.setPresent(present);
				attd.setTotalDayClass(classHeld);
				attd.setAbsence(absent);
					
				attdRecord.add(attd);
				
			}
			
			out.print(gson.toJson(attdRecord));
			
		}
		out.flush();
		out.close();
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		String lecId = (String) session.getAttribute("lecId");
		String subjCode = request.getParameter("subjCode");
		String groupId = request.getParameter("groupId");
		String todayDate = request.getParameter("todayDate");
		
		
		
		String allStudId[] = request.getParameterValues("allStudId[]");
		
		/*
		 * if there are no student come to class 
		 * it will return null
		 * there should be code to handling null value
		 * but it doesnt make sense all student are absent
		 */
		
		System.out.println(allStudId);
		
		AttendanceDAO attdDAO = new AttendanceDAO();
		
		int classId = attdDAO.getClassId(lecId, subjCode, groupId);
		
		attdDAO.insertAttendance(classId,allStudId, todayDate);
		
		
	}

}
