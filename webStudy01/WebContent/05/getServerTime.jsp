<%@page import="java.util.Objects"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%!
	//이곳은 선언부입니다! 우리는 지금 marshalling 이라는 메서드를 생성해줌
	//반환타입 메서드명(파라미터)
	String marshalling(Map<String, Object> originalData){//마샬링의 타켓은  map 그 결과 문자열이 만들어짐
	
		StringBuffer result = new StringBuffer();//스트링 버퍼 객체 생성
		result.append("{");//버퍼 객체에 "{"  문자열 추가
			// "{"의 의미는 json 객체를 생성해줄때 기본적으로 {열어주고 }닫아줘야함
		String jsonPattern = "\"%s\":\"%s\",";//json객체의 기본형식은 맵이 키:값으로 구성되있듯이
		
		//json객체는 "파라미터명":"값" <형식임 
		for(Entry<String, Object> entry:originalData.entrySet()){//entry란? 맵의 하나하나의 요소들을 엔트리라함<
		//entrySet을해주면 맵에 담겨있는 값중 랜덤으로(기본적으로 맵에 들어갈때 순서가없기때문에) entry에 담아줌
		//즉 여기서 entry 객체변수에는 orginalData에 담긴 key값과 value값을 갖고있음 
			String propName = entry.getKey();//맵에 담긴 키값과 value값중 랜덤으로 뽑힌값의 키를 propName에 담아줌
// 			String propValue = entry.getValue().toString();
			String propValue = Objects.toString(entry.getValue(), "");//랜덤으로 뽑힌값중 value값을 propValue에 담아줌
			result.append(
				String.format(jsonPattern, propName, propValue)
					);//json객체의 기본형식인 "파라미터명":"값(문자열)"을 세팅해주기위해서 포멧을 세팅해줌
		}
		//포멧형식 예: String format="윤지영은 나이가 %d 이고 %s 입니다." 일 경우
		
		//String.format의 형식 (문자열(스트링 변수),)
		//마지막 콤마 없애기 위해
		int lastIndex = result.lastIndexOf(",");// 기본적으로 json객체의 
		//"파라미터":"값",
		//"파라미터":"값" 이형식임 (파라미터가 2개일떄)
		//그러나 위 포문 로직상 jsonPattern에 ,<가 반듯이 들어가므로 마지막 "파라미터":"값",<이형식이 되므로 버퍼의
		//마지막 번째 형식에 ","가 있으므로 그걸 제거해주기위해서 마지막줄부터 ","를 찾아서 그 index<즉 그 위치를 
		//정수형으로 반환
		
		result.deleteCharAt(lastIndex);//deletlCharAt메서드는 int형을 매개변수로 받아 그 위치의 문자하나를 제거 
		//즉 마지막 "파라미터":"값" ,< 이부분을 제거해주기위한 형식임
		
		result.append("}");//그리고 json객체는 기본적으로 {열고 '}' 닫아주므로 마지막에 추가
		//즉 제이슨 객체의 형식이 끝남
		return result.toString();//그 결과를 반환
	}
%>
<%--
	매 1초마다 현재 서버의 시각을 JSON 형태로 전송.
	응답으로 전송될 JSON 객체 내s는
	현재 서버의 시각을  의미하는 serverTime 이라는 프로퍼티가 있다.
--%>
<%
	response.setHeader("Refresh", "1"); //1초마다 갱신
	Date now = new Date();
	Map<String, Object> javaObject = new LinkedHashMap<>(); //마샬링의 대상이 되는 부분
	javaObject.put("serverTime", now);
	String json = marshalling(javaObject); 
	out.print(json);
	
%>

<%-- {
	"serverTime" : "<%= now%>"
} --%>
