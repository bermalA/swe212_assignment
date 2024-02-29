package org.student.swe212.model;

import java.sql.Date;

public class Lecture {
    private Long id;
    private String name;
    private int credit;

    public Lecture(Long id, String name, int credit){
        this.id= id;
        this.name= name;
        this.credit= credit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
