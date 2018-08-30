package com.example.sabari.hostel_app;

public class hostel_students {

    String name,rno;

    hostel_students(){

    }

    public hostel_students(String name, String rno) {
        this.name = name;
        this.rno = rno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRno() {
        return rno;
    }

    public void setRno(String rno) {
        this.rno = rno;
    }
}
