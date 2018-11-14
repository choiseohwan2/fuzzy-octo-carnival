package kr.or.ddit.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value="/imageService.do", loadOnStartup=2)
public class ImageServiceServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 요청 파라미터를 통해서 어떤 이미지를 보내야하는지를 확인(확보) : 파라미터명(Image)
		String imgName = req.getParameter("selname");
		//파라미터 검증과정
		if(imgName==null || imgName.trim().length()==0) {
			resp.sendError(400);
			return;
		}
		ServletContext context = req.getServletContext();
		
		resp.setContentType(context.getMimeType(imgName));
		
		// 이미지 스트리밍 서비스 제공...
		File folder = (File)getServletContext().getAttribute("contentFolder");
		File imgFile = new File(folder, imgName);
		if(!imgFile.exists()) {
			resp.sendError(404);
			return;
		} //검증과정끝
		int pointer = -1;
		byte[] buffer = new byte[1024];
		FileInputStream fis = new FileInputStream(imgFile);
		OutputStream os = resp.getOutputStream();
		while((pointer = fis.read(buffer)) != -1){ // -1 : EOF문자
			os.write(buffer, 0, pointer);
		}
		fis.close();
		os.close();
		
	}
}
