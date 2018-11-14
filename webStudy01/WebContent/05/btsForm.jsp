<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	public Map<String, String[]> singerMap = new LinkedHashMap<>();
{
	singerMap.put("B001", new String[]{"RM","/WEB-INF/bts/rm.jsp"});
	singerMap.put("B002", new String[]{"슈가","/WEB-INF/bts/sugar.jsp"});
	singerMap.put("B003", new String[]{"제이홉","/WEB-INF/bts/jhope.jsp"});
	singerMap.put("B004", new String[]{"진","/WEB-INF/bts/jin.jsp"});
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>05/btsForm.jsp</title>
<script>
function changeHandler(){
	document.forms[0].submit();
}
</script>
</head>
<body>
<form action="<%=request.getContextPath() %>/05/getBTS.jsp" name="memberForm">
	<select name="member" onchange="changeHandler();">
		<option value="">멤버 선택</option>
			<%
				String pattern = "<option value=%s>%s</option>";
				for(Entry<String, String[]> entry : singerMap.entrySet()){
					out.print(String.format(pattern, entry.getKey(), entry.getValue()[0] ));
				}
			
			%>
<!-- 		<option value="B004">진</option> -->
	</select>

</form>
</body>
</html>