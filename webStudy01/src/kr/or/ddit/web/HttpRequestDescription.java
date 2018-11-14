package kr.or.ddit.web;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Http 프로토콜의 request packaging 규칙
 * Request Line : URL, Http Method, Protocol/version
 * 	Http Method(Request Method) : 현재 요청의 목적/의도/수단/방식
 * 	GET : 조회 (R)
 *  	  Body가 존재하지 않기때문에, 전송할 최소한의 데이터를 Line의 URL에 덧붙임.
 *  	  ex)http://localhost/webStudy01/gugudan.do?minDan=4&maxDan=7 // ? 뒤에 있는 문장이 쿼리스트링, 파라미터와 파라미터의 구분자를 &
 *		  주소:포트넘버/세부경로?QueryString								  
 *	  	  QueryString : segment1&segment2&.....						  // minDan=4 이 한 쌍을 세그먼트라고 함
 *		  segment : param_name=param_value 						 	  // minDan을 파라미터명 4가 파라미터값
 * 	POST : (신규)전송 및 등록 (C) 										  
 *  PUT : 전송 및 수정 (U)
 *  DELETE : 삭제 (D)
 *  TRACE : 디버깅
 *  OPTIONS : ex) http method의 지원여부 확인
 *  HEAD : 응답데이터를 body가 없는 형태로 받는다.
 * Request Header : 클라이언트와 요청에 관한 부가 정보(metadata)
 * 		헤더명 : 헤더값 .....
 * Request Body(Contents, Message body) : method가 POST일 경우, 형성
 *		: 클라이언트가 서버로 전송할 데이터들...(요청 파라미터)
 *
 * *** client의 요청 정보를 서버에서 확인하는 방법
 * HttpServletRequest의 활용
 * 1) 파라미터 확보 : 기본적으로 모든 파라미터는 문자열의 형태로 전송됨.
 * 		String getParameter(name) : 하나의 파라미터명으로 하나의 값이 전송.
 * 		String[] getParameterValues(name) : 동일한 파라미터명으로 열개의 값이 전송.
 * 		Enumeration getParameterNames()
 * 		**** Map<String, String[]> getParameterMap() ****
 * 		** 요청 파라미터에 특수문자가 포함된 경우,
 * 		IETF에서 정의한 RFC2396 규약에 따라
 * 		모든 특수문자는 URLEncoding(Percent Encoding) 방식으로 인코딩되어 전송.
 * 		
 * 		인코딩된 특수문자 확보
 * 		한글 깨짐 -> 메서드확인 doget이깨지면 서블릿에서 인코딩확인->그래도 깨지면 서버관리자한테 문의
 * 		GET : 서버에 설정을 통한 디코딩 설정.
 * 			server.xml -> URIEncoding : 특정 인코딩 방식으로 고정.
 * 					   -> useBodyEncodingForURI : POST 방식에서 사용하는 requset.setCharacterEncoding(encoding) 사용.
 * 		POST : requset.setCharacterEncoding(encoding) - 파라미터를 꺼내기 전에 설정해야함.
 * 2) 요청 헤더의 확보 (헤더를 꺼낼때)
 * 		String getHeader(name) : 모든 헤더는 문자열로 전송
 * 		int getIntHeader(name)
 * 		long getDateHeader(name) : 날짜 확인시, new Date(long) 모든 언어를 막론하고 모든 날짜데이터는 밀리세컨드로 계산 그래서 long타입으로 가져온다.
 * 		Enumeration getHeaders(name)
 * 		Enumeration getHeaderNames()
 * 3) 기타 요청과 관련된 메소드들...
 * 		service 메소드에 기술됨(참고)
 * 		
 */
@WebServlet("/httpReq.do")
public class HttpRequestDescription extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		// 객체 초기화 직후
	}

	public void destroy() {
		// 객체 소멸 전
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//service는 메서드에 상관없이 모든 요청에 콜백
		
		//아래 메서드들은 외워두자
		System.out.println(request.getCharacterEncoding()); //get방식이므로 body가 없기 때문에 null값이 뜬다
		System.out.println(request.getContentType()); //get방식이므로 body가 없기 때문에 null값이 뜬다
		System.out.println(request.getContentLength()); //get방식이므로 body가 없기 때문에 null값이 뜬다
		System.out.println(request.getContextPath()); //contextPath 서버가 도메인을 저장하므로 도메인명을 생략한다
		System.out.println(request.getLocalAddr()); //서버사이드 IP address
		System.out.println(request.getRemoteAddr()); //클라이언트 사이드 IP address
		System.out.println(request.getRemotePort()); //클라이언트의 포트넘버. (참고로 현재 서버포트는 http의 기본포트인 80을 사용 중.)
		System.out.println(request.getMethod()); //http메서드를 꺼낼 수 있다
		System.out.println(request.getRequestURI());
		System.out.println(request.getProtocol());
		// 매 요청시 마다 호출
		// Http method에 상관없이 실행될 코드를 넣는다.
		super.service(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
