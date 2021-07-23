package com.model;

public class NewArticleCommand {
//DB에 Article 테이블이 있다고 가정하고 1:1로 매핑 되는 클래스 입니다
	private int parentId;  //'form태그안에 있는 name값'하고 'db에 있는 컬럼명' 하고 'dto에 있는 멤버필드명'하고 
						   // !!!!!!!!!다 똑같이 맞출거에요!!!!!!!!! 자동화를 위해서!!!!!!!!!
	private String title;
	private String content;		//이제는 생성자, getter,setter도 안만들거래요, 그걸 자동으로 만들어주는 애들이 생겼대요 미친
	

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "NewArticleCommand [parentId=" + parentId + ", title=" + title + ", content=" + content + "]";
	}
	
	
	
}	
