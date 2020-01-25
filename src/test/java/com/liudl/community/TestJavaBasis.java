package com.liudl.community;

import lombok.Data;

/**
 * Created by TwistedFate on 2020/1/25 14:04
 */
@Data
public class TestJavaBasis {
    private int i;
    public void back(int i) {
        this.i = i;
        i = i +1;
    }
    public static void main(String[] args) {
        TestJavaBasis testJavaBasis = new TestJavaBasis();
        testJavaBasis.back(2);
        System.out.println(testJavaBasis.getI());
    }
}
