package com.model;


//상품테이블과 1:1 매핑되는 DTO
public class OrderItem {
	private int itemid;
	private int number;
	private String remark;
	
	public int getItemid() {
		return itemid;
	}
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "OrderItem [itemid=" + itemid + ", number=" + number + ", remark=" + remark + "]";
	}
	
	
}
