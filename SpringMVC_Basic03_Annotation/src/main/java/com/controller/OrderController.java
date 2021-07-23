package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model.OrderCommand;

/*
 하나의 요청주소로 2개 업무처리
 /order/order.do
 
 화면 : GET
 처리 : POST
 */

@Controller
@RequestMapping("/order/order.do")
public class OrderController {
	
	@RequestMapping(method=RequestMethod.GET)	//static final 상수값이 정의된 어쩌고 라면
	public String form() {
		return "order/OrderForm"; //view 주소를 리턴 resolver가 만들어주죠
		
		// /WEB-INF/views/order/OrderForm.jsp
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String submit(OrderCommand ordercommand) { //제너릭타입이 자동화가 되나......? 자동화가 된대
													  
		
		/*
		 OrderCommand command = new OrderCommand();
		 
		 List<OrderItem> itemlist = new ArrayList<>();
		 itemlist.add(new OrderItem(1,10,"파손주의");
		 itemlist.add(new OrderItem(10,1,"리모콘은 별도구매");
		 
		 command.setOrderItem(itemlist);
		 
		 forward 되는 객체 key 값은 >> orderCommand 가 되겠지 (@Model어쩌고 어노테이션써서 바꿔주지 않는한)
		 
		 이 과정이 자동으로 add되고 set되서 넘어간대
		 */
		
		return "order/OrderCommited";
	}
}
