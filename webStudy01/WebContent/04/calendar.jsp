<%@page import="java.util.Locale"%>
<%@page import="java.text.DateFormatSymbols"%>
<%@page import="java.util.Calendar"%>
<%@page import="static java.util.Calendar.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>04/calendar.jsp</title>
<style>
	.sunday{
		background-color: red;
	}
	.saturday{
		background-color: blue;
	}
	table{
		width: 100%;
		height: 500px;
		border-collapse: collapse;
	}
	td,th{
		border: 1px solid black;
	}
</style>
<script type="text/javascript">/*  이전달, 다음달으 클릭했을때, form태그안에 입력했을때 */
	function eventHandler(year, month) { //이전달이나 다음달을 클릭했을때 받아오는 값을 파라미터로
		var form = document.forms[0];
		if((year && month) || month==0){//자바는 year가 boolean타입이어야하지만 자바스크립트는 정해져있지 않다 반드시 값이 넘어올때만 true가 된다
			form.year.value = year;
			form.month.value = month;
		}
		form.submit();
		return false;
	}
</script>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8"); //아래 파라미터에 특수문자가 있을경우 이와같은 코드를 통해 셋팅해준다
	String yearStr = request.getParameter("year");
	String monthStr = request.getParameter("month");
	String language = request.getParameter("language");
	Locale clinetLocale = request.getLocale();
	
	//언어를 선택하면 그 언어로 바뀌게 설정
	if(language!=null && language.trim().length()>0){
		clinetLocale = Locale.forLanguageTag(language);
	}
	DateFormatSymbols symbols = new DateFormatSymbols(clinetLocale);
	
	
	Calendar cal = getInstance();
	
	//if문에 속하면 파라미터값으로 받은 년/월로 셋팅된다.
	if(yearStr !=null && yearStr.matches("\\d{4}")
			&& monthStr != null && monthStr.matches("1[0-1]|\\d")){
		cal.set(YEAR, Integer.parseInt(yearStr));
		cal.set(MONTH, Integer.parseInt(monthStr));
	}
	int currentYear = cal.get(YEAR);
	int currentMonth = cal.get(MONTH);
	
	cal.set(DAY_OF_MONTH, 1);
	int firstDayOfWeek = cal.get(DAY_OF_WEEK);
	int offset = firstDayOfWeek -1;
	int lastDate = cal.getActualMaximum(DAY_OF_MONTH);
	
	//이전달
	cal.add(MONTH, -1);
	int beforYear = cal.get(YEAR);
	int beforMonth = cal.get(MONTH);
	
	//다음달
	cal.add(MONTH, 2);
	int nextYear = cal.get(YEAR);
	int nextMonth = cal.get(MONTH);
	
	//현재 시스템에서 제공하는 모든 locale정보 제공
	Locale[] locales = Locale.getAvailableLocales();
%>
<form><!-- get방식 -->
<h4>
<%-- <a href="<%=request.getContextPath() %>/04/calendar.jsp?year=<%=beforYear %>&month=<%=beforMonth%>&language=<%=clinetLocale%>">이전달</a> --%>
<a href="javascript:eventHandler(<%=beforYear%>, <%=beforMonth%>);">이전달</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="number" name="year" value="<%=currentYear%>"
	onblur="eventHandler();"
/>년
<select name="month" onchange="eventHandler();">
	<%
		String[] monthStrings = symbols.getShortMonths();
		for(int idx = 0; idx < monthStrings.length; idx++){
			out.println(String.format("<option value='%d' %s>%s</option>", 
					idx, idx==currentMonth?"selected":"" ,monthStrings[idx]));
		}
	%>
</select>
<select name="language" onchange="eventHandler();">
	<%
		//언어선택 UI	
		for(Locale tmp : locales){
			out.println(String.format("<option value='%s' %s>%s</option>", tmp.toLanguageTag(),
					tmp.equals(clinetLocale)?"selected":"",tmp.getDisplayLanguage(clinetLocale)));			
		}
	%>
</select>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a onclick="eventHandler(<%=nextYear%>, <%=nextMonth%>);">다음달</a>
</h4>
</form>
<table>
<thead>
	<tr>
		<%
			//요일출력
			String[] dateStrings = symbols.getShortWeekdays();
			for(int idx = Calendar.SUNDAY; idx<=Calendar.SATURDAY; idx++){
				out.println(String.format("<th>%s</th>", dateStrings[idx]));
			}
		%>
	</tr>
</thead>
<tbody>

<%
	//날짜 출력
	int dayCount = 1;
	for(int row=1; row <=6; row++){
		%>
		<tr>
		<%
		for(int col=1; col <=7; col++){
			int dateChar = dayCount++ - offset; //날짜를 맞게 출력
			if(dateChar < 1 || dateChar > lastDate){
				out.println("<td>&nbsp;</td>");
			}else{	
				String clzValue = "normal";
				if(col==1){
					clzValue = "sunday";
				}else if(col==7){
					clzValue = "saturday";
				}
			out.println(String.format(
					"<td class='%s'>%d</td>", clzValue, dateChar
					));
			}
		}
		%>
		</tr>
		<%
	}
%>
</tbody>
</table>
</body>
</html>