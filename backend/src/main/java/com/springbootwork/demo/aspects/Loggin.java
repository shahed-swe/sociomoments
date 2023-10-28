package com.springbootwork.demo.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Loggin {


    @Before("execution(* *.*.helloworld())")
    public void saveLogger(){
        System.out.println("hello this is doing newly");
    }
}
