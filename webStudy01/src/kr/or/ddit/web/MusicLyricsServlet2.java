package kr.or.ddit.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/song") //등록 및 매핑
public class MusicLyricsServlet2 extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//파라미터 확보
		String song = req.getParameter("song"); //jsp파일에 있는 data타입의 이름을 파라미터로 가져온다.
		int status = 0;
		String message = null;
		//검증
		if(song == null || song.trim().length()==0) {
			status = HttpServletResponse.SC_BAD_REQUEST;
			message = "가사파일을 선택해주세요";
		}
		
		File folder = (File)getServletContext().getAttribute("contentFolder");
		File songFile = new File(folder, song);
		if(!songFile.exists()) {
			status = HttpServletResponse.SC_NOT_FOUND;
			message = "해당 곡은 가사가 없습니다";
		}
		if(status!=0) {
			resp.sendError(status, message);
			return;
		}
		//응답데이터 - mimeType설정
		resp.setContentType("text/html;charset=UTF-8");
//		java 1.7 :try with resource 구문
		try (
//				Closable 객체를 할당      //예외가 발생하던 안하던간에 상관없이 close된다.
				FileInputStream fis = new FileInputStream(songFile);
				InputStreamReader isr = new InputStreamReader(fis, "MS949");
				BufferedReader reader = new BufferedReader(isr);
				PrintWriter out = resp.getWriter();
				){
			String temp = null;
			StringBuffer html = new StringBuffer();
			while((temp=reader.readLine())!=null) {
				html.append("<p>"+temp+"</p>");
				System.out.println(temp);
			}
			out.println(html);
		
		}
	}
}
