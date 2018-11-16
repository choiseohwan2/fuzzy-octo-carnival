<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.Objects"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	request.setCharacterEncoding("UTF-8");
	String failedId = request.getParameter("mem_id");
	String message = (String)session.getAttribute("message");
	if(failedId ==null || failedId.trim().length()==0){
		failedId="";
	}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login/loginForm.jsp</title>
<script type="text/javascript">
	<%
		if(StringUtils.isNotBlank(message)){
	%>
	alert("<%=message%>");
	<%
			session.removeAttribute("message");
		}
	%>
</script>
</head>
<body>

<form action="<%=request.getContextPath() %>/login/loginCheck.jsp" method="post"> <!-- action은 처리자이자 경로 -->
	<ul>
		<li>
			아이디 : <input type="text" name="mem_id" value="<%=Objects.toString(failedId)%>"/>
			<label><input type="checkbox" name="idChecked" value="idSaved" /> 아이디기억하기
			</label>
			
			<!-- 체크하면 전송 체크인하면 비전송
				1. 쿠키로 기억 일주일동안 살리기
				-> 로그인체크 (리다이렉트하기전에)
				2. 일주일동안 아이디 상태복원
				3. 근데 체크상태도 유지해야함
				-> 로그인폼jsp에서 작업
				--------------------------
				4. 로그인에 성공하는 과정에서 체크하지 않았을 때 기존에 등록되있던 쿠키까지 삭제되도록 -->
		</li>
		
		<li>
			비밀번호 : <input type ="password" name="mem_pass"/>
			<input type="submit" value="로그인" />
		</li>
	</ul>
</form>
</body>
</html>