package ncontroller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.log.UserDataHelper.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dao.NoticeDao;
import vo.Notice;

@Controller
@RequestMapping("/customer/")
public class CustomerController {

	
	private NoticeDao noticedao;
	
	@Autowired
	public void setNoticedao(NoticeDao noticedao) {
		this.noticedao = noticedao;
	}

	/*
	 1. method 안에서 return type [String] 리턴값이 뷰의 주소
	 
	 2. public ModelAndView notices ... > ModelAndView 객체 생성 > 데이터, 뷰 설정 > return
	 
	 3. public String notices(Model model){함수실행시 내부적으로 Model 객체의 주소가 들어온다} //Model에 있는 interface를 통해서
	 
	 
	 
	 
	 */
	
	//public List<Notice> getNotices(int page, String field, String query) 
	@RequestMapping("notice.htm")
	public String notices(String pg , String f , String q, Model model) {
		
		//default 값 설정
		int page = 1;
		String field="TITLE";
		String query = "%%";
		
		if(pg != null   && ! pg.equals("")) {
			page  = Integer.parseInt(pg);
		}
		
		if(f != null   && ! f.equals("")) {
			field = f;
		}

		if(q != null   && ! q.equals("")) {
			query = q;
		}
		
		//DAO 작업
		List<Notice> list = null;
		try {
			list = noticedao.getNotices(page, field, query);
		} catch (ClassNotFoundException e) {
					e.printStackTrace();
		} catch (SQLException e) {
					e.printStackTrace();
		}
		
		
		//Spring  적용
		
		/* 위에 파라미터에 Model model를 해줘서 주석처리 해줘야함
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.setViewName("notice.jsp");  //여기 jsp를 썼기 때문에 dispatcher-servlet.xml 에 resolver를 안해줘도 된다
		return mv;
		*/
		model.addAttribute("list",list);	//자동으로 notice.jsp forward
		/*
		 <c:forEach items="${list}" var="n"> ...
		 
		 */
		return "notice.jsp";
	}
	
	//public Notice getNotice(String seq)
	@RequestMapping("noticeDetail.htm")
	public String noticesDetail(String seq,Model model) {
	
		Notice notice = null;
		try {
			notice = noticedao.getNotice(seq);
		} catch (ClassNotFoundException e) {
						e.printStackTrace();
		} catch (SQLException e) {
						e.printStackTrace();
		}
		
		/*
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("notice", notice);
		mv.setViewName("noticeDetail.jsp");
		*/
		
		model.addAttribute("notice",notice);
		//Model addAttribute(String name, Object value)
		//value객체를 name이름으로 추가한다, 뷰 코드에서는 name으로 지정한 이름을 통해서 value를 사용


		return "noticeDetail.jsp";
	}
	
	
	//글쓰기화면 (GET)
	//http://localhost:8090/SpringMVC_Basic04_WebSite_Annotation/customer/notice.htm
	@RequestMapping(value="noticeReg.htm", method=RequestMethod.GET)
	public String noticeReg() {
		//글쓰기 요청 들어왔을때
		return "noticeReg.jsp"; //mapping 이 noticeReg.htm 으로 들어왔을때 get방식이면 noticeReg.jsp 화면을 보여줘라
	}
	
	
	 //글쓰기 처리 (POST)
	   @RequestMapping(value="noticeReg.htm", method=RequestMethod.POST)
	   public String noticeReg(Notice n, HttpServletRequest request) {
	      
	      //System.out.println(n.toString());
	      //Notice >> DTO 에서 변경사항이 발생 (다중파일 업로드)
		  //private List<CommonsMultipartFile> files
		   
		  //files[0]		>>     1.jpg
		  //files[1]		>>     2.jpg
		   
		  List<CommonsMultipartFile> files = n.getFiles();
		  List<String> filenames = new ArrayList<String>(); //파일명만 따로 관리
		  
		  //파일을 업로드할수도 안할수도 한개만 할수도 여러개 할수도 있지
		  if(files != null & files.size() > 0) {	//1개라도 업로드된 파일이 존재하면
			  for(CommonsMultipartFile multifile : files) {
				  String filename = multifile.getOriginalFilename();
				  String path = request.getServletContext().getRealPath("/customer/upload"); //배포된 서버 경로
			      String fpath = path + "\\" + filename;
			      System.out.println(fpath);
			      
			      if(!filename.equals("")) {  //웹 서버에 실 파일 업로드
			    	  
			    	  FileOutputStream fs = null;
				      try {
				         fs = new FileOutputStream(fpath);
				         fs.write(multifile.getBytes());
				         
				         filenames.add(filename);	//db에 입력될 파일명
				         
				      } catch (Exception e) {
				         e.printStackTrace();
				      } finally {
				         try {
				            fs.close();
				         } catch (IOException e) {
				            e.printStackTrace();
				         }
				      }
			      }
			  }
		  }
	      
	      //파일명(DTO) //파일 업로드를 2개다 한다는 전제조건하에 (1개만 넣거나 하는경우 조건을 걸어줘야함)
	      n.setFileSrc(filenames.get(0));		//string값 뽑기
	      n.setFileSrc2(filenames.get(1));
	      
	      try {
	         noticedao.insert(n); //DB insert
	      } catch (ClassNotFoundException e) {
	         e.printStackTrace();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	      
	      //insert나 update 하고 나면 ...(F5 누르면 계속 글이 ..Write)
	      //리스트로 옮겨야한다. (location.href or redirect)
	      //서버에게 새로운 요청 ...목록보기
	      //Spring : redirect:notice.htm  
	      //Servlet, jsp : location.href or response.sendRedirect
	      
	      return "redirect:notice.htm";
	   }
	   
	 //글수정하기 (화면) GET 글쓰기랑 다르게 글 상세페이지를 보여줘야 하잖아
	   @RequestMapping(value="noticeEdit.htm", method = RequestMethod.GET)
	   public String noticeEdit(String seq , Model model) {
		  //controller 클래스 안의 @RequestMapping 어노테이션이 부여된
		  //method들은 사용자에게 응답할 view를 생성하는 역할을 하는
		  //즉, controller 메소드입니다.
		  //controller는 model을 이용해 데이터를 가져오고 view에 데이터를 넘겨
		  //적절한 view를 생성하는 역할
	      Notice notice=null;
	      try {
	         notice = noticedao.getNotice(seq);
	      } catch( Exception e) {
	            e.printStackTrace();
	      } 
	      
	      model.addAttribute("notice", notice);
	      //key,value 쌍으로 전달할 수 있음
	      
	      return "noticeEdit.jsp";
	   }
	   //글수정 (처리) POST ,파일 업로드 기능도 있어야 함
	   @RequestMapping(value="noticeEdit.htm", method = RequestMethod.POST)
	   public String noticeEdit(Notice n , HttpServletRequest request) {
	       
		   List<CommonsMultipartFile> files = n.getFiles();
			  List<String> filenames = new ArrayList<String>(); //파일명만 따로 관리
			  
			  //파일을 업로드할수도 안할수도 한개만 할수도 여러개 할수도 있지
			  if(files != null & files.size() > 0) {	//1개라도 업로드된 파일이 존재하면
				  for(CommonsMultipartFile multifile : files) {
					  String filename = multifile.getOriginalFilename();
					  String path = request.getServletContext().getRealPath("/customer/upload"); //배포된 서버 경로
				      String fpath = path + "\\" + filename;
				      System.out.println(fpath);
				      
				      if(!filename.equals("")) {  //웹 서버에 실 파일 업로드
				    	  
				    	  FileOutputStream fs = null;
					      try {
					         fs = new FileOutputStream(fpath);
					         fs.write(multifile.getBytes());
					         
					         filenames.add(filename);	//db에 입력될 파일명
					         
					      } catch (Exception e) {
					         e.printStackTrace();
					      } finally {
					         try {
					            fs.close();
					         } catch (IOException e) {
					            e.printStackTrace();
					         }
					      }
				      }
				  }
			  }
		      
		      //파일명(DTO) //파일 업로드를 2개다 한다는 전제조건하에 (1개만 넣거나 하는경우 조건을 걸어줘야함)
		      n.setFileSrc(filenames.get(0));		//string값 뽑기
		      n.setFileSrc2(filenames.get(1));
		      
		      try {
		         noticedao.update(n); //DB insert
		      } catch (ClassNotFoundException e) {
		         e.printStackTrace();
		      } catch (SQLException e) {
		         e.printStackTrace();
		      }
		      
		      return "redirect:noticeDetail.htm?seq=" +n.getSeq();
		      //글번호를 가지고 넘어가야 하는데 seq
	   }
	   
	   @RequestMapping("noticeDel.htm") // /customer/noticeDel.htm
		public String noticeDel(String seq){
				try {
					noticedao.delete(seq);
				} catch (Exception e) {
				}
			return "redirect:notice.htm";
		}
	}














