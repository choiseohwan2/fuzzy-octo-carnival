package kr.or.ddit.web.calculate;

public enum Mime {
//	if(accept.contains("plain")) {
//	mime ="text/plain;charset=UTF-8";
//	}else if(accept.contains("json")) {
//	mime = "application/json;charset=UTF-8";
//	result = "{\"result\":\""+result+"\"}";
//	}else {
//	mime = "text/html;charset=UTF-8";
//	result = "<p>"+result+"</p>";
//	}
	PLAIN("text/plain;charset=UTF-8",(result)-> {return result;}),
	HTML("text/html;charset=UTF-8",(result)-> {return "<p>"+result+"</p>";}),
	JSON("application/json;charset=UTF-8", (result) -> {return "{\"result\":\""+result+"\"}";});
	
	private String type;
	private RealMime realMime; 
	
	Mime(String sign, RealMime realMime){
		this.type = type;
		this.realMime = realMime;
		
	}
	
	public String getRealMime(String result) {
		return realMime.realMime(result);
	}
	public String getType() {
		return type;
	}
}
