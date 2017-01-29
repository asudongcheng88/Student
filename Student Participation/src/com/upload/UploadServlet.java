package com.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Servlet implementation class UploaddServlet
 */
@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static Logger LOGGER = 
            Logger.getLogger(UploadServlet.class.getCanonicalName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		
		String lecId = (String) session.getAttribute("lecId");
		
		System.out.println("This is lecturer id = " + lecId);
		
		Excel excel = new Excel();
		final Part filePart = request.getPart("file");
		String subCode = request.getParameter("upload-sub-code");

		final String fileName = excel.getFileName(filePart);
		
		String fileType = excel.getFileType(fileName);
		
		excel.uploadFile(filePart, fileType, subCode, lecId);
		
		response.sendRedirect("LecStudOptions.jsp");
	}
}


