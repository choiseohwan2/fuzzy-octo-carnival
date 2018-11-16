package kr.or.ddit.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.utils.CookieUtil;
import kr.or.ddit.utils.CookieUtil.TextType;

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
		
		// 쿠키값 : A,B(A라는 이미지와 B라는 이미지가 있을 때 , 로 구분한다라는 의미)
		String imgCookieValue = new CookieUtil(req).getCookieValue("imageCookie");
		String[] cookieValues=null;
		ObjectMapper mapper = new ObjectMapper();
		if(StringUtils.isBlank(imgCookieValue)) {
			cookieValues = new String[] {imgFile.getName()};
		}else {
			String[] cValues = mapper.readValue(imgCookieValue, String[].class);
			cookieValues = new String[cValues.length+1];
			System.arraycopy(cValues, 0, cookieValues, 0, cValues.length);
			cookieValues[cookieValues.length-1] = imgFile.getName();
		}
//		imgCookieValue = Arrays.toString(cookieValues);
//		imgCookieValue = imgCookieValue.replaceAll("[\\[\\]\\s]", "");
		
		// marshalling
		
		imgCookieValue = mapper.writeValueAsString(cookieValues);
		System.out.println(imgCookieValue);
		Cookie imageCookie = CookieUtil.createCookie("imageCookie", imgCookieValue, req.getContextPath(), TextType.PATH, 60*60*24*14);
		resp.addCookie(imageCookie);
		
		
		
		
//		//쿠키객체생성(쿠키유틸이용)
//		Cookie cookie =CookieUtil.createCookie("imgName", imgName); 
//		//쿠키 만료시간 설정
//		cookie.setMaxAge(60*60*10);
//		
//		cookie.setPath("/");
//		resp.addCookie(cookie);
		
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
