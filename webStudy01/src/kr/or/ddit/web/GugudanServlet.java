package kr.or.ddit.web;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;
import javax.servlet.annotation.*;


@WebServlet(value="/gugudan.do")
public class GugudanServlet extends HttpServlet {
	/*@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String minDanStr = req.getParameter("minDan");
		String maxDanStr = req.getParameter("maxDan");
		
		int minDan = 2;
		int maxDan = 9;
		if(minDanStr != null && minDanStr.matches("\\d")) {
			minDan = Integer.parseInt(minDanStr);
		}

		if(maxDanStr != null && maxDanStr.matches("[0-9]")) {
			maxDan = Integer.parseInt(maxDanStr);
			
		}
		// 2~9단까지의 구구단을 table 태그를 이용하여 출력.
		// 단, 한 행에 한 단씩.
		// 테스트시에는 /gugudan.do 요청을 사용
		// web.xml을 사용하지 말 것.
		resp.setContentType("text/html;charset=UTF-8");
		InputStream in = this.getClass().getResourceAsStream("gugudan.205");
		InputStreamReader isr = new InputStreamReader(in,"UTF-8"); //InputStreamReader는 바이트스트림을 캐릭터스트림으로 바꿈
		BufferedReader br = new BufferedReader(isr);
		StringBuffer html = new StringBuffer();
		String temp = null;
		while((temp = br.readLine()) != null){ //readLine은 한번에 통으로 읽어 올 수 있음.
			html.append(temp);
		}
		
		
		StringBuffer sb = new StringBuffer();
		
		for(int i = minDan; i < maxDan; i++){
			sb.append("<tr>");
			for(int j =1; j <10; j++){
				sb.append(String.format("<td>%d * %d = %d</td>", i, j, i*j));
			}
			sb.append("</tr>");
		}
		
		int start = html.indexOf("@gugudan");
		int end = start + "@gugudan".length();
		String replace = sb.toString();
		
		html.replace(start, end, replace);//치환할문자
		
		
		PrintWriter out = resp.getWriter();
		out.println(html.toString());
		out.close();
	}
		
		
	
	
	public GugudanServlet (){
		System.out.println(this.getClass().getName());
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException
	{	
		
	}*/
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String minDanStr = req.getParameter("minDan");
		String maxDanStr = req.getParameter("maxDan");
		
		int minDan = 2;
		int maxDan = 9;
		if(minDanStr != null && minDanStr.matches("\\d")) {
			minDan = Integer.parseInt(minDanStr);
		}

		if(maxDanStr != null && maxDanStr.matches("[0-9]")) {
			maxDan = Integer.parseInt(maxDanStr);
			
		}
		// 2~9단까지의 구구단을 table 태그를 이용하여 출력.
		// 단, 한 행에 한 단씩.
		// 테스트시에는 /gugudan.do 요청을 사용
		// web.xml을 사용하지 말 것.
		resp.setContentType("text/html;charset=UTF-8");
		InputStream in = this.getClass().getResourceAsStream("gugudan.205");
		InputStreamReader isr = new InputStreamReader(in,"UTF-8"); //InputStreamReader는 바이트스트림을 캐릭터스트림으로 바꿈
		BufferedReader br = new BufferedReader(isr);
		StringBuffer html = new StringBuffer();
		String temp = null;
		while((temp = br.readLine()) != null){ //readLine은 한번에 통으로 읽어 올 수 있음.
			html.append(temp);
		}
		
		
		StringBuffer sb = new StringBuffer();
		
		for(int i = minDan; i < maxDan; i++){
			sb.append("<tr>");
			for(int j =1; j <10; j++){
				sb.append(String.format("<td>%d * %d = %d</td>", i, j, i*j));
			}
			sb.append("</tr>");
		}
		
		int start = html.indexOf("@gugudan");
		int end = start + "@gugudan".length();
		String replace = sb.toString();
		
		html.replace(start, end, replace);//치환할문자
		
		
		PrintWriter out = resp.getWriter();
		out.println(html.toString());
//		out.close();
	}
}