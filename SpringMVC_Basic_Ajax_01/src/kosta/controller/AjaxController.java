package kosta.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import kosta.vo.Employee;
import kosta.vo.TestVO;

@Controller
public class AjaxController {

	//org.springframework.web.servlet.view.json.MappingJackson2JsonView
	@Autowired
	private View jsonview;
	
	//command=AjaxTest&name=java&arr="+array
	@RequestMapping(value="json.kosta")
	public View jsonkosta(String command , String name, String[] arr , ModelMap map){
										//배열로 넘겼으니까 배열로 받는거야
		System.out.println("command : " + command);
		System.out.println("name : " + name);
		System.out.println("arr : " + arr);
		System.out.println(Arrays.toString(arr));
		
		ArrayList<String> list = new ArrayList<>();
		list.add("치킨맥주");
		list.add("돈까스");
		list.add("치킨피자");
		
		map.addAttribute("menu", list);
						//key,   value
		
		//json으로 바꿔서 보내고 싶은거야
		
		
		//{menu: Array(3)}
		//menu: (3) ["치킨맥주", "돈까스", "치킨피자"]
		
		//private View jsonview 타입으로 리턴 //[Model , ModelMap , ModelAndView]
		//데이터를 객체(JSON)담아 놓으면  자동 ..... 
		return jsonview;  
		//return되는 값이
		//맨위에 있는 autowired에 있는값이랑 같고
		//(servlet-context.xml에 bean 이름이랑 같으면)
		//internal어쩌고-resolver를 태우지 않고
		//객체 형태로 자동변환 된대
		//[Model , ModelMap , ModelAndView] 이런 타입으로
	}
	@RequestMapping(value="json2.kosta")
	public View jsonkosta(String command , String name, ModelMap map){
		System.out.println("command : " + command);
		System.out.println("name : " + name);
		
		ArrayList<TestVO> list = new ArrayList<>();
		
		TestVO vo = new TestVO();
		vo.setBeer("라거");
		vo.setFood("골뱅이");
		
		list.add(vo);
		
		TestVO vo2 = new TestVO();
		vo2.setBeer("카스");
		vo2.setFood("치킨");
		
		list.add(vo2);
		
		map.addAttribute("menu", list);
		
		//{"menu":[{"beer":"라거","food":"골뱅이"},{"beer":"카스","food":"치킨"}]}
		
		
		return jsonview;  //private View jsonview 타입으로 리턴
	}
	
	@RequestMapping(value="/json3.kosta" , method = RequestMethod.POST)
	public View add(HttpServletRequest request, HttpServletResponse response,ModelMap map){
		
		Employee employee = new Employee();
		employee.setFirstname("kg");
		employee.setLastname("lim");
		employee.setEmail("i7027@naver.com");
		
		ArrayList<Employee> list = new ArrayList<>();
		list.add(employee);
		
		Employee employee2 = new Employee();
		employee2.setFirstname("h");
		employee2.setLastname("ong");
		employee2.setEmail("hong@naver.com");
		list.add(employee2);
		
		map.addAttribute("data", list);
		//{data:[{},{}]}
		
		
		
		//{"data":[{"firstname":"kg","lastname":"lim","email":"i7027@naver.com"},{"firstname":"h","lastname":"ong","email":"hong@naver.com"}]}
		return jsonview;
		
		
	}
	
	//data : {aaa:aaa, bbb:bbb, ccc:ccc}
	@RequestMapping(value="/json4.kosta" , method = RequestMethod.POST)
	public void testType(
			@RequestParam(value="aaa[]", required=false) String[] aaa,
			@RequestParam(value="bbb", required=true) String bbb,
			String ccc
			) 
	{
			Map<String, Object> result = new HashMap<String, Object>(); 
			      
			// Array Request
			try{
				for(int i=0,cnt=aaa.length; i<cnt; i++) System.out.println(aaa[i]);
			}catch(Exception e) {
				
			}
			 
			// String Request
			System.out.println(bbb);
			System.out.println(ccc); 
			// JSONArray Request
			/*JSONArray jArr = new JSONArray();
			try {
			jArr = new JSONArray(ccc);
			} catch (JSONException e1) {
			e1.printStackTrace();
			}
			 

			// control JSONObject
			JSONObject jObj = new JSONObject();
			try {
			jObj.put("이름","철수");
			} catch (JSONException e1) {
			e1.printStackTrace();
			}
			try {
			jObj.put("주소","서울");
			} catch (JSONException e1) {
			e1.printStackTrace();
			} 
			String data = jObj.toString();
			System.out.println(data);
			 
			// control JSONArray
			JSONArray ja = new JSONArray();
			JSONObject order = new JSONObject();
			String rrr = "";
			String Json = "[{\"Product\" : \"Mouse\", \"Maker\":\"Samsung\", \"Price\":23000},"
			               + "{\"Product\" : \"KeyBoard\", \"Maker\":\"LG\", \"Price\":12000},"
			               + "{\"Product\":\"HDD\", \"Maker\":\"Western Digital\", \"Price\":156000}]";
			try{
			ja = new JSONArray(Json);
			for (int i = 0; i < ja.length(); i++){
			order = ja.getJSONObject(i);
			rrr += "product: " + order.getString("Product") + ", maker: " + order.getString("Maker") +
			", price: " + order.getInt("Price") + "\n";
			}
			}
			catch (JSONException e){ ;}

			return result;  */
		
			}
			 
			
}
