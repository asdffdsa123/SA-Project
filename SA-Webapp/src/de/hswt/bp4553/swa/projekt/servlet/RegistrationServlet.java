package de.hswt.bp4553.swa.projekt.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FilenameUtils;

import de.hswt.bp4553.swa.projekt.client.RegistrationRemoteClient;
import de.hswt.bp4553.swa.projekt.client.socket.SocketClient;

public class RegistrationServlet extends HttpServlet{
	
	private static final String ENCODING = StandardCharsets.UTF_8.name();
	
	private static final Logger log = Logger.getLogger(RegistrationServlet.class.getName());

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
	    try {
	        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
	        for (FileItem item : items) {
	        	if("file".equals(item.getFieldName())){
	        		handleFileUpload(item.getInputStream());
	        	}else{
	        		log.warning("Field ignored: " + item.getFieldName());
	        	}
	        }
	    } catch (FileUploadException e) {
	        throw new ServletException("Cannot parse multipart request.", e);
	    }
	    resp.sendRedirect("/bp4553");
	}
	
	private void handleFileUpload(InputStream in) throws IOException{
		try(Scanner sc = new Scanner(in, ENCODING)){
			List<String> lines = new ArrayList<>();
			lines.add(sc.nextLine());
			RegistrationRemoteClient client = new SocketClient();
			client.groupRegister(lines);
		}
	}
	
}
