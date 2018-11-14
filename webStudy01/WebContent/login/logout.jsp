<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	
	//로그아웃
// 	session.removeAttribute("authMember");
	session.invalidate(); //바인드되어있는 것들을 언바인드로 만드는 메서드
	//이동(index.jsp, redirect)
	response.sendRedirect(request.getContextPath()+"/");
%>
