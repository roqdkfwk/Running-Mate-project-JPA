package com.suseok.run.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

// 클래스가 하나 이상의 빈(Bean) 정의를 포함하고 있음을 나타낸다.
// Spring 컨테이너는 이 클래스를 설정 파일로 인식하고 빈을 등록한다.
@Configuration

// MyBatis의 매퍼 인터페이스를 스캔할 패키지를 지정한다.
// MyBatis 매퍼 인터인터페이스는 Dao를 의미한다. >> Dao에서는 데이터베이스의 CRUD를 추상화함
// com.suseok.run.model.dao 패키지 안에 있는 모든 매퍼 인터페이스를 스캔하여 Mybatis와 연동할 수 있도록 한다.
@MapperScan(basePackages = "com.suseok.run.model.dao")
public class DBConfig {

}
