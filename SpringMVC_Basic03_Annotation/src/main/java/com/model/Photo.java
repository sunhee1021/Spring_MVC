package com.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;


/*
 DB에
 create table photo(
   name
   age
   image >> 업로드한 파일명 >> 1.jpg , 2.png 이런 파일명
 )
 */

public class Photo {
	private String name;
	private int age;
	private String image; //이미지 실 파일명
	
	//POINT
	private CommonsMultipartFile file;  //업로드한 파일 정보를 담는 변수
	//단, 조건은 input 태그에서 name을 <input type="file" name="file" 로 해줘야함

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	//POINT!!!
	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "Photo [name=" + name + ", age=" + age + ", image=" + image + ", file=" + file + "]";
	}
	
	
}
