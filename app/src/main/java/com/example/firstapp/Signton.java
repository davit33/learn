package com.example.firstapp;

public class Signton {

    private int a;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    //create an object of SingleObject
    private static Signton instance = new Signton();

    //make the constructor private so that this class cannot be
    //instantiated
    private Signton(){}

    //Get the only object available
    public static Signton getInstance(){
        return instance;
    }

    public void showMessage(){
        System.out.println("Hello World!");
    }
}