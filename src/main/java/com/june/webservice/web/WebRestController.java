package com.june.webservice.web;

import javax.persistence.EntityNotFoundException;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.june.webservice.domain.key.Key;
import com.june.webservice.domain.key.KeyRepository;
import com.june.webservice.domain.register.Register;
import com.june.webservice.domain.register.RegisterRepository;
import com.june.webservice.service.PostsService;
import com.mifmif.common.regex.Generex;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class WebRestController {

    private PostsService postsService;
    private RegisterRepository keyProfileRepository;
    private KeyRepository keyRepository;

    @GetMapping("/hello")
    public String hello() {
        return "HelloWorld";
    }

    @PostMapping("/posts")
    public void savePosts(@RequestBody PostsSaveRequestDto dto){
    	postsService.save(dto);
    }
    // POST 호출 받을 메서드 ( 키 등록 )
    @PostMapping("/register")
    public String registerKey(@RequestBody RegisterSaveRequestDto dto){
    	if(keyProfileRepository.existsById(dto.getKey()))
    	{
    		return "이미등록되어있는 키 입니다" + dto.getKey();
    	}
    	else
    	{
    		Register rtn = keyProfileRepository.save(dto.toEntity());
    		
    		if(null != rtn)
    		{
    			return "200 OK";
    		}
    	}
    	
    	return null;
    }
    
    // POST 호출 받을 메서드 ( 키 발급 )
    @PostMapping("/request")
    public String reqKey(@RequestBody RegisterSaveRequestDto dto){
    	Register register = null;	// 변수생성
    	String generatedkey = "";	// 발급된 KEY
    	
    	// 발급요청 받을 KEY 를 조회해 온다.
    	register = this.getRegisterByKey(dto.getKey());
    	
    	// 등록이 되어있는 KEY일 경우 발급방법에 따른 KEY 를 생성한다.
    	if(null!=register)
    	{
    		if(StringUtils.hasText(register.getType()))
    		{
	    		String keyType = register.getType();
	    		String generator = register.getGenerator();
	    		int    minLength = register.getMinLength();
	    		
	    		System.out.println("조회 된 데이터\n" + register.getType() + "\n"
	    			          						  + register.getGenerator() + "\n"
	    			          						  + register.getMinLength() + "\n");
	    		 
	    		if("string".equals(keyType))
	    		{
	    			generatedkey = this.keygenByString(); // 문자 KEY 발급 처리를 한다.
	    		}
	    		else if("number".equals(keyType))
	    		{
	    			// mySql 방식
	    			if("mySql".equals(generator))
	    			{
	    				// TO-DO 구현을 해야되는데 못함!!
	    				return "개발중";
	    			}
	    			// generic 방식
	    			else if("generic".equals(generator))
	    			{
	    				// 랜덤함수로 구현해서 키 중복체크
	    				generatedkey = this.keygenByNumber(minLength);
	    			}
	    			else
	    			{
	    				return "정의되지 않은 발급방법 입니다";
	    			}
	    		}
	    	}
    	}
    	else
    	{
    		return "등록되어있는 키가 아닙니다.";
    	}
    	
    	
    	return generatedkey;
    	
    }    
    
    
    // 문자열 키를 발급하는 메서드
    private String keygenByString()
    {
    	boolean genOk = true;
    	String generatedKey = "";
    	
    	while(genOk)
    	{
    		generatedKey = this.keyGeneratorByString();
    	
    		// 발급된 키의 중복체크
    		if(!keyRepository.existsById(generatedKey))
    		{
    			genOk = false;
    			
    			// 발급된 키를 Insert
    			Key key = null; 
    			key = key.builder().key(generatedKey).build();
    			keyRepository.save(key);
    		}
    	}
    	return generatedKey;	// 키 반환
	}
    
    // 정규식을 이용한 키 발급
    private String keyGeneratorByString()
    {
    	String key = "";
    	String regex = "[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}";	// 숫자 알파벳이 결합된 정규식
    	
    	Generex generex = new Generex(regex);
    	key = generex.random();	// 정규식생성
    	
    	System.out.println("생성된 키" + key ) ;
    	
    	return key;
    }
    
    
    // 등록 KEY 를 조회하는 private 메서드
    private Register getRegisterByKey(String key)
    {
    	Register item = null ;
    	
    	if(keyProfileRepository.existsById(key))
    	{
    		item = keyProfileRepository.getOne(key);
    	}
    	else
    	{
    		System.out.println("KEY가 테이블에 존재하지않음");
    		
    	}	
    	
    	
    	System.out.println("조회된키는 무엇?" + item.getKey());
    	
    	return item;
    }
    
    // 발급된 KEY 를 조회하는 private 메서드
//    private Key getRegisteredKey(String registeredkey)
//    {
//    	Key item = null;
//    	try {
//    		item = keyRepository.getOne(registeredkey);
//    	}
//    	catch (EntityNotFoundException e)
//    	{
//    		System.out.println("KEY가 테이블에 존재하지않음");
//    		
//    		item = null;
//    	}
//    	
//    	System.out.println("조회된키는 무엇?" + item.getKey());
//    	
//    	
//    	return item;
//    }
    
    // 숫자 키를 발급하는 메서드
    private String keygenByNumber(int scale)
    {
    	String generatedKey = "";
    	boolean genOk = true;

    	
    	while(genOk)
    	{
    		long numberKey = (long)((Math.random()*1000*scale)%scale*10);
    		generatedKey =  Long.toString(numberKey);	// 형변환
    	
    		System.out.println("발급된숫자형KEY " + generatedKey);
    		
    		// 발급된 키의 중복체크
    		if(!keyRepository.existsById(generatedKey))
    		{
    			genOk = false;
    			
    			// 발급된 키를 Insert
    			Key key = null; 
    			key = key.builder().key(generatedKey).build();
    			keyRepository.save(key);
    		}
    	}
		return generatedKey;	// 키 반환  
    }

}

