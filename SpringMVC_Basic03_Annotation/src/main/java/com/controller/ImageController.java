package com.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.model.Photo;

@Controller
@RequestMapping("/image/upload.do")
public class ImageController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String form() {
		return "image/image";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String submit(Photo photo, HttpServletRequest request) throws IOException {
		/*
		 * 파라미터로 HttpServletRequest 타입까지 적는 이유는
		 * 경로를 지정해주기 위해서, 밑에 보면 image값은 자동 주입이 안된다고 했잖아
		 * 그래서 경로지정때문에 쓰이는거래
		 * 
		 1.   Photo DTO 타입으로 데이터 받기
		 1-1. 자동화 : name 속성값이 Photo 타입 클래스의 memberfield명과 동일
		 2.   내부적으로 public String submit(Photo photo)
		 2-1. Photo photo = new Photo();
		 2-2. 자동주입
		  	  photo.setName("홍길동");
		  	  photo.setAge(50);
		  	  photo.setImage() >> 파일이름은 multipart 안에 들어있기때문에 자동주입이 안돼요 >> 업로드한 파일명 
		  	                   >> 가공작업 해야함 >>CommonsMultipartFile 이름 추출
		  	  photo.setFile(
		 */
		//System.out.println(photo.toString());
		//Photo [name=aaa, age=1111, image=null, file=org.springframework.web.multipart.commons.CommonsMultipartFile@3793ef36]
		//image 는 자동주입이 안된다고 했지~~~~~~~~~~~왜인지는 공부해~~~~~~~~~~~
		
		CommonsMultipartFile imagefile = photo.getFile();
		System.out.println("imagefile.name : " + imagefile.getName());
		System.out.println("imagefile.getContentType : " + imagefile.getContentType());
		System.out.println("imagefile.getOriginalFilename : " + imagefile.getOriginalFilename());
		System.out.println("imagefile.getBytes : " + imagefile.getBytes().length);
		
		//POINT !!! DB에 들어갈 파일명
		photo.setImage(imagefile.getName());
		
		//cos.jar 자동 파일 업로드
		//실제 파일 업로드 구현 (
		String filename = imagefile.getOriginalFilename();
		String path = request.getServletContext().getRealPath("/upload"); //배포된 서버 경로
		String fpath = path + "\\" + filename;
		System.out.println(fpath);
		
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(fpath);
			fs.write(imagefile.getBytes());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//DB작업 했다치고 ... 파일 업로드 완료
		return "image/image";
	}
}
