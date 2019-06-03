package model;

import android.os.Build;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class user {

    @SerializedName("studentNumber")
    public int studentNumber;

    @SerializedName("studentNumber")
    public String name;

    @SerializedName("studentNumber")
    public String firstName;


    @SerializedName("studentNumber")
    public String status;


    @SerializedName("studentNumber")
    public String notif;

    @SerializedName("studentNumber")
    public String mail;

    @SerializedName("studentNumber")
    public String password;

    @SerializedName("studentNumber")
    public String birthday;

    @SerializedName("studentNumber")
    public String gender;


    @SerializedName("studentNumber")
    public String nationality;

    @SerializedName("studentNumber")
    public String address;

    @SerializedName("studentNumber")
    public String city;

    @SerializedName("studentNumber")
    public String phone;

    @SerializedName("studentNumber")
    public String EducationStream;



    public int getStudentNumber() {
        return studentNumber;
    }

    public String getFirstName() {
        return firstName;
    }
}
