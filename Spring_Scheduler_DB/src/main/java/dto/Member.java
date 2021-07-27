package dto;

import lombok.Data;

@Data
public class Member {
	private String id;
	private String name;
	private int point;

}
/*
springuser계정에 테스트 테이블생성

CREATE TABLE scheduler 
( 
    id         VARCHAR2(10),
    name       VARCHAR2(10),
    point      NUMBER(10)
);
*/