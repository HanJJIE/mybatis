package com.study.pojo;

public class User {

    private Long user_id;
    private String user_name;

    public Long getuser_id() {
        return user_id;
    }

    public void setuser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}