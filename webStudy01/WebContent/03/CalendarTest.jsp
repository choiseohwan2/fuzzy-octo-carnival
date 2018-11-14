<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Locale"%>
<%@page import="org.apache.tomcat.jni.Local"%>
<%@page import="java.time.Year"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<link href="../css/Calendar.css" rel="stylesheet" type="text/css">
	<link href="../css/select.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	function langChange() {
		document.formName.submit();//언어선택 이벤트처리..
	}
	function sendIt() {
		document.formName2.submit();//전달 이벤트처리

	}
	function sendIt2() {
		document.formName2.submit();//다음달 이벤트 처리
	}
</script>
<%
	String selMonth = request.getParameter("month");
	String selYear = request.getParameter("year");
	Calendar calendar = Calendar.getInstance();//캘린더 객체생성
	Calendar startDay = Calendar.getInstance();//시작날짜를 위한 객체생성
	Calendar endDay = Calendar.getInstance();//마지막 날짜를 위한 객체 생성
	int year = calendar.get(Calendar.YEAR);//현재 년도를 받아옴
	int month = calendar.get(Calendar.MONTH);//현재 달
	int day = calendar.get(Calendar.DATE);//오늘 날
	if (selMonth != null) {//만약 선택 값이 있으면 그 선택한걸로 달 설정
		month = Integer.parseInt(selMonth);
	}
	if (selYear != null)
		year = Integer.parseInt(selYear);//동일하게 년 설정
	//검증부분
	startDay.set(year, month, 1);//시작날짜 세팅
	endDay.set(year, month, startDay.getActualMaximum(Calendar.DATE));//종료날짜 세팅
	int START_DAY_OF_WEEK = startDay.get(Calendar.DAY_OF_WEEK);//무슨 요일에서 시작하나 찾기위한 값
		System.out.println("초기->시작날"+startDay.get(Calendar.DATE));
		System.out.println("초기->마지막날"+endDay.get(Calendar.DATE));
	startDay.add(Calendar.DATE, -startDay.get(Calendar.DAY_OF_WEEK) + 1);//저번달 날짜를 받아오기 위함
	endDay.add(Calendar.DATE, 7 - endDay.get(Calendar.DAY_OF_WEEK));//다음달 날짜를 받아오기 위함
		System.out.println("저번달 마지막주 일요일부터 ->시작날"+startDay.get(Calendar.DATE));
		System.out.println("다음달 ->첫번째 주 토요일부터 "+endDay.get(Calendar.DATE));
	System.out.println(day);

	String lang = request.getParameter("lang");//언어 가져오는 메서드
	Locale locale = null;
// 	locale = request.getLocale();//사용자 언어 가지고오기
	Locale currentLocale = request.getLocale();////동일
	if (lang != null && lang.trim().length() != 0) {
		currentLocale = Locale.forLanguageTag(lang);//선택된 값 가져오기, 선택이 안되면 기본값으로 사용자 언어를 사용함
	}

// 	Map<String, String[]> langMap = new HashMap<String, String[]>();
// 	Locale locale3 = request.getLocale();

	if (lang != null) {

		System.out.print(lang);
	}
%>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		Locale[] locales = Locale.getAvailableLocales();
	%>
	<form action="" name="formName">
	<div class="styled-select">
		<select name="lang" onchange="langChange()">
			<option value="">언어선택</option>
			<%
				String pattern = "<option value=%s %s >%s</option>";
				for (Locale lo : locales) {
					String seloption = "";
					if (currentLocale.equals(lo)) {
						seloption = "selected";
					}
					if (lo.getDisplayLanguage() != null && lo.getDisplayLanguage().trim().length() != 0)
						out.println(String.format(pattern, lo.toLanguageTag(), seloption, lo.getDisplayLanguage()));
				}
			%>
		</select>
		</div>
	</form>

	<table id="calendar">
		<caption>
				<form name="formName2">
					<select name="year" onchange="sendIt()">
						<%
							String option = "<option value=%s %s >%s년</option>";
							for (int i = year - 5; i <= year; i++) {
								String sel = "";
								if (i == year) {
									sel = "selected";
								}
								out.print(String.format(option, i, sel, i));

							}
							for (int i = year + 1; i < year + 5; i++) {
								String sel = "";
								out.print(String.format(option, i, sel, i));
							}
						%>

					</select> <select name="month" onchange="sendIt2()">
						<%
							option = "<option value=%s %s>%s월";
							for (int i = 0; i <= 11; i++) {
								String sel = "";
								if (i == month) {
									sel = "selected";
								}
								out.print(String.format(option, i, sel, i + 1));
							}
						%>
					</select>
				</form>
				<%
					if (month < 0) {
						month = 11;
						year -= 1;
					}
				//달은 기본적으로 0->1월 1은->2월 그러므로 month가 0보다작으면 달을 11로 설정 그러면 12월
				//그리고 현재 년도에서 -1에서 전년도로 바꿔줌
				
				%> 
				<a href="<%=request.getContextPath()%>/03/CalendarTest.jsp?year=<%=year%>&month=<%=month - 1%>">◀</a>
				<!--전달 이벤트 처리부분 a링크를 경로로줘서 year에는 년도 motnh는 현재 달에서 -1만큼   -->
				<a href="<%=request.getContextPath()%>/03/CalendarTest.jsp"><%=year%>년<%=month + 1%>월</a>

				<%
					if (month > 10) {
						month = -1;
						year += 1;
				//반대로 month가 11일경우 12월이므로 	month흫 -1로 
				//년도는 1년 +
					}
				
				%> <a
				href="<%=request.getContextPath()%>/03/CalendarTest.jsp?year=<%=year%>&month=<%=month+1 %>">▶</a>
				<!--이부분에서 달을 +1해주는이유 0이 1월이기 때문에  -->
		
		</caption>
		<tr class="weekdays">
			<%
			// 일 월 화 수 목 금 토 를 받아오기위한 부분 번역도 됨
				for (int i = 1; i <= 7; i++) {
					calendar.set(Calendar.DAY_OF_WEEK, i);//캘린더 객체.set()에 Calendar.DAY_OF_WEEK를 해주면 요일 반환해줍니다
					//기본적으로 1이면 일요일 2 월 .. .. .. . 7 토 요일
					
					
					String dayOfWeekStr = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, currentLocale);
					//위에서 세팅해준 켈린더객체.getDisplayName(캘린더객체,형식,언어)를 지정해줄수 있음
					//Calendar.long 이면 월요일 short면 월 이런식으로
					//currentLocale은 언어 타입
					out.print("<th scope='col'>" + dayOfWeekStr + "</th>");//요일 세팅부분
				}
			%>
		</tr>
		<tr class='days'>
		<%
			String tag = "<td class='day' style='%s'><div class='date'>%d</div></td>";
			for (int i = 1; startDay.before(endDay) || startDay.equals(endDay); startDay.add(Calendar.DATE, 1)) {
				//이부분이 좀 신기할건데 기본적으로 포문 조건부에 논리형이 올수 있음
				//시작 날짜가 마지막 날보다 앞서면 true 아니면 false 
				//시작 날짜와 마지막 날짜와 동일하면 true 아니면 false
				//false가 오면 반복을 멈춤
				
				//둘다 펄스여야만 반복 이 멈춤 
				//날짜에서 .add()메소드는 하루씩 증가 해준다는거임
				int printDay = startDay.get(Calendar.DATE);
				if (printDay == day) {
					out.print(String.format(tag, "border:3px solid blue;", printDay));
					//오늘날짜와 printday값이 같을경우 테두리주는 상황
				} else {
					out.print(String.format(tag, "border: 0px;", printDay));
				}
				if (i++ % 7 == 0) {
					out.print("</tr>");
				}
			}
		%>
	</table>
</body>
</html>