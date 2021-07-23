package com.model;

import java.util.List;

//주문서(주문내역) 클래스
//하나의 주문은 여러개의 상품을 가질수 있다

//**********************플젝으로 따지면 뭐가 해당될까요?
//board : reply 관계
//하나의 게시글은 여러개의 댓글을 가질 수 있다
//**********************

//**********************
//하나의 은행은 여러개의 계좌를 가질 수 있다
//**********************


public class OrderCommand {
	private List<OrderItem> orderItem;	//타입을 class 타입쓰는걸 어려워하지말아요

	public List<OrderItem> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(List<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}
	
}

/*
 주문발생
 OrderCommand command = new OrderCommand();
 
 List<OrderItem> itemlist = new ArrayList<>();
 itemlist.add(new OrderItem(1,10,"파손주의");
 itemlist.add(new OrderItem(10,1,"리모콘은 별도구매");
 
 command.setOrderItem(itemlist);
 */