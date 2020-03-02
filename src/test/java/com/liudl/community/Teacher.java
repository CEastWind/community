package com.liudl.community;

import com.alibaba.fastjson.JSON;
import com.liudl.community.dto.AccessTokenDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Created by TwistedFate on 2020/1/25 14:04
 */
public class Teacher extends Person{
    private String name="tom";
    public Teacher(){
        System.out.println("this is a teacher");
    }

    public void change(int b) {
        b = b - 1;
    }
    public static void main(String[] args){
        System.out.println(System.currentTimeMillis());
        if (true){

        }
    }

}