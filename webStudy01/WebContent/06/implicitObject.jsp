<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>06/implicitObject.jsp</title>
</head>
<body>
<h4> 기본 객체(내장 객체)</h4>
<pre>
	** pageContext(PageContext타입) : JSP 페이지와 관련된 모든 정보를 가진 객체.(밑의 기본객체들을 포함하고 있다는 의미)
	request(HttpServletRequest타입) : 요청과 클라이언트에 대한 정보를 캡슐화한 객체
	response(HttpServletResponse타입) : 응답과 관련된 모든 정보를 캡슐화한 객체
	out(JSPWriter타입) : 출력버퍼에 데이터를 기록하거나 버퍼를 제어하기 위해 사용되는 출력 스트림
	session(HttpSession타입) : 한 세션안에서 발생하는 모든 정보를 캡슐화한 객체
	application(ServletContext타입) : 컨텍스트(어플리케이션)와 서버에 대한 정보를 가진 객체
	<%=application.hashCode() %>
	config(ServletConfig타입) : 서블릿 등록과 관련된 정보를 가진 객체
	page(Object타입) : 현재 JSP 페이지에 대한 래퍼런스
	exception(Throwable타입) : 발생한 예외에 대한 정보를 가진 객체
							 :예외(에러)가 발생한 경우, 에러를 처리하는 페이지에서 사용됨.
							 (page 지시자의 isErrorPage로 활성화함.)
	
	page가 제일 빨리 죽고 범위도 좁다. application이 가장 범위가 크고 오래 살아남는다.
<!-- 	page < request < session < ... < ... < application -->
	
	<%=exception %>
	
</pre>
</body>
</html>