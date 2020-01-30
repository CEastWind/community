package com.liudl.community;

/**
 * Created by TwistedFate on 2020/1/25 14:04
 */
public class Teacher extends Person{
    private String name="tom";
    public Teacher(){
        super("hhh");
        System.out.println("this is a teacher");
    }
    public static void main(String[] args){
        Teacher teacher = new Teacher();
        //System.out.println(this.name);
    }

}