package kr.or.ddit.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ImagesFormServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		ServletContext context = req.getServletContext();
		File folder = (File)context.getAttribute("contentFolder");
		String[] filenames = folder.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				String mime = context.getMimeType(name);
				return mime.startsWith("image/");
			}
		});
		
		
		
		//action 속성의 값은 context/imageService, method ="get"
		InputStream img = this.getClass().getResourceAsStream("NewFile.html");
		InputStreamReader isr = new InputStreamReader(img,"UTF-8"); //InputStreamReader는 바이트스트림을 캐릭터스트림으로 바꿈
		BufferedReader br = new BufferedReader(isr);
		StringBuffer html = new StringBuffer();
		String temp = null;
		while((temp = br.readLine()) != null){	//readLine은 한번에 통으로 읽어 올 수 있음.
			html.append(temp+"\n");
		
		}
		StringBuffer sb = new StringBuffer();
		
		String pattern = "<option>%s</option>";
		for(int i = 0; i <filenames.length;  i++){
			sb.append(String.format(pattern, filenames[i]));
			
		
		}
		
		int start = html.indexOf("@imageService");
		int end = start + "@imageService".length();
		String replace = sb.toString();
		
		html.replace(start, end, replace);//치환할문자
		
		
		PrintWriter out = resp.getWriter();
		out.println(html.toString());
		out.close();
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
}
