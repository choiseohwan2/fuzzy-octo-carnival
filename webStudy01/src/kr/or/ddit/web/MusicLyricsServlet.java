package kr.or.ddit.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
// I/O -> 입출력
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MusicLyricsServlet extends HttpServlet {  //클래스파일 만들 때 httpServlet 상속시킨 상태로 만든다.
   @Override
   protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      super.service(req, resp); //req는 클라이언트로부터의 요청, resp는 그에 대한 응답
   }
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     
     ServletContext context= req.getServletContext(); //어떤 파일이 넘어 올지 모르므로 클라이언트가 선택한 마임타입을 가져오기 위한 방법
      
     File folder= new File("d:/contents"); // File객체 인스턴스화 하고 folder라는 변수에 d:/contents경로를 대입
      String[] filenames=folder.list(new FilenameFilter() { //FilenameFilter는 파일리스트로 특정한 확장자의 파일만 가져올 때 사용,filenames에 대입
    //File[]이 아닌 String[]인 이유?? 타입을 파일명으로 가져오기 때문
         @Override
         public boolean accept(File dir, String name) { //boolean으로 특정한 확장자의 파일이면 true, 아니면 false반환
            String mime =context.getMimeType(name);
            if(mime!=null) {
               
               return mime.startsWith("text/"); //endsWith ".txt"로도 가능
            }
            return false;
         }
      });
      StringBuffer sb= new StringBuffer(); //StringBuffer를 쓰면 메모리의 효율?낭비가 적어짐 String으로 쓰면 heap에 계속해서 쌓이기 때문에 메모리 사용효율이 떨어짐
      resp.setContentType("text/html;charset=UTF-8"); //메인타입text/서브html ;charset UTF-8로 인코딩
      
     //Class.getResourceAsStream()는 해당 클래스의 위치에서 파일을 찾음. 참고;("/파일명")은 절대경로
     InputStream in= this.getClass().getResourceAsStream("music.html");//char (바이트스트림 -> 문자스트림)
      InputStreamReader isr= new InputStreamReader(in,"UTF-8");//cahr
      BufferedReader br = new BufferedReader(isr);//효율성을 위한 버퍼
      StringBuffer html= new StringBuffer();
      String temp=null;
      while((temp=br.readLine())!=null){ //readLine은 한 줄을 통째로 읽음 //모든 줄을 다 읽을 때까지 읽어나감.
         html.append(temp+"\n"); //append 누적
      }
      String pattern="<option>%s</option>\n"; // html코드를 동적으로 사용자가 선택한것을 추가해주기 위한 것. <option>은 select를 의미
      										  //%s는 파일 이름을 담음
      for (int i = 0; i < filenames.length; i++) {
         sb.append(String.format(pattern, filenames[i])); //filenames[i]가 위의 %s로 들어감
      }
      int start= html.indexOf("@img"); //music.html select
      int end =start + "@img".length();
      String replace= sb.toString();
      html.replace(start,end,replace); //치환
      
      PrintWriter out=resp.getWriter();//출력해주기위한 타입 //OutputStream <-> Writer , InputStream <-> Reader
      out.println(html.toString());
      out.close(); //출력하고 닫기
     
     //contents폴더 -> html파일
   }
   @Override //post전송
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      req.setCharacterEncoding("UTF-8");
      String name=req.getParameter("kk"); //파라미터 확보 "kk"는 music.html의 select name.
      if(name==null ||name.trim().length()==0) { //trim()좌우공백제거
         resp.sendError(400); //조건문이 true면 클라이언트에게 에러메세지보냄
         return;//doGet메서드 자체가 호출이기 때문에 리턴시 메서드 종료 
      }//오류 걸러내기 부분 
      File img=new File("d:/contents/"+name); //??설명필요
      if(!img.exists()) {
         resp.sendError(404);
         return;//doGet메서드 자체가 호출이기 때문에 리턴시 메서드 종료 
      }
      //html 읽어 오는 인풋 스트림
      InputStream in= this.getClass().getResourceAsStream("musicText.html");//res에 있는 경로를 찾아서 가져오는거
      InputStreamReader isr= new InputStreamReader(in,"UTF-8");//cahr //바이트스트림 -> 문자스트림
      BufferedReader br = new BufferedReader(isr);//효율성을 위한 버퍼
      StringBuffer htmltext= new StringBuffer();
      String temptext=null;
      while((temptext=br.readLine())!=null){
         htmltext.append(temptext+"\n");
      }
     
    
      
      
      ServletContext context=req.getServletContext();//어떤 파일이 넘어 올지 모르므로 클라이언트가 선택한 마임타입을
      //가져오기위한 방법
      //파일 읽어 오는 인풋스트림
      FileReader fileReader= new FileReader(img);
     
     //FileInputStream(img)로 d:/contents의 있는 html파일들을 읽어오고 euc-kr로 인코딩
     //InputStreamReader로 바이트스트림 -> 문자스트림으로 다시 BufferedReader로 읽은 것을 reader라는 변수에 대입
     //위에 html 읽어 오는 인풋스트림을 한 줄로 나타낸것.
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(img), "euc-kr"));
      resp.setContentType(context.getMimeType(name)); //설명필요
      StringBuffer html= new StringBuffer();
      String temp=null;
      while((temp=reader.readLine())!=null){
         html.append(temp+"<br>");
      }
      reader.close();
      resp.setCharacterEncoding("UTF-8");
      resp.setContentType("text/html;charset=UTF-8");
      int start= htmltext.indexOf("@text");
      int end =start + "@text".length();
      String replace= html.toString();
      htmltext.replace(start,end,replace);
      PrintWriter out=resp.getWriter();//출력해주기위한 타입
      out.println(htmltext.toString());
      out.close();
     
     
   }
}