package com.suseok.run.jwtutill;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRequired {

}

// 이 클래스는 특정 메소드에 적용하여 해당 메소드가 실행되기 전에 인증이 필요하는 것을 명시하는 사용자 정의 어노테이션이다.
// 이 어노테이션을 활용하여 메소드 수준에서 인증 로직을 적용할 수 있다.
// 주요 역할
// 	1. 인증 요구 명시
//	 - `AuthRequired` 어노테이션은 특정 메소드가 실행되기 전에 인증이 필요하다는 것을 명시한다.
//	   이를 통해 어플리케이션의 보안 정책을 메소드 단위로 적용할 수 있다.
//	2. 런타임 유지
//	 - `@Retention(RetentionPolicy.RUNTIME)` 어노테이션은 `AuthRequired` 어노테이션이 런타임 동안 유지된다는 것을 나타낸다.
//	   이를 통해 런타임에 리플렉션(reflection)을 사용하여 이 어노테이션을 확인하고 처리할 수 있다.
//	3. 메소드에 적용
//	  - `@Target({ElementType.METHOD})` 어노테이션은 `AuthRequired` 어노테이션이 메소드에만 적용될 수 있음을 나타낸다.
//	    이를 통해 클래스나 필드가 아닌 메소드에만 이 어노테이션을 사용할 수 있다.