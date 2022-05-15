package com.example.fypmallmanagmentsystemandips.Models;

public class Admin {
    String adminMail,adminPass;

    public Admin(String adminMail, String adminPass) {
        this.adminMail = adminMail;
        this.adminPass = adminPass;
    }

    public String getAdminMail() {
        return adminMail;
    }

    public void setAdminMail(String adminMail) {
        this.adminMail = adminMail;
    }

    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }
}
