package com.example.sabari.hostel_app;

public class hostel_students {

    String name,rno,room_no;

    public hostel_students(){

    }

    public hostel_students(String name, String rno, String room_no) {
        this.name = name;
        this.rno = rno;
        this.room_no = room_no;
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

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }
}
