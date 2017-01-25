package com.citizen.calculator2017.utils;


import com.citizen.calculator2017.BuildConfig;

public class ProductOut {
    public String LeftOperand;
    public String StepNum;
    public String answer;
    public int checkMode;
    public String operation;

    public ProductOut() {



        this.answer = BuildConfig.FLAVOR;
        this.operation = BuildConfig.FLAVOR;
        this.StepNum = "0";
        this.LeftOperand = BuildConfig.FLAVOR;
    }
}
