package com.june.webservice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.mifmif.common.regex.Generex;
@RunWith(SpringRunner.class)
public class TestStringClass {

	@Test
    public void keygenByString()
    {
    	String key = "";
    	String regex = "[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}";
    	
    	Generex generex = new Generex(regex);
    	key = generex.random();
    	
    	System.out.println("생성된 키" + key ) ;
    	
    	//return key;
    }
    
	
}
