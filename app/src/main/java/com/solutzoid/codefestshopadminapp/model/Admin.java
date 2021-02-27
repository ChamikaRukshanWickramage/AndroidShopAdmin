package com.solutzoid.codefestshopadminapp.model;

public class Admin {

    String googleId;
    String authId;
    String name;
    String nic;
    String email;
    String mobile;
    String gender;
    String address;
    String userStatus;
    String imageUrl;
    String FcmId;


    public Admin(){}

    public Admin(String googleId, String authId, String name, String nic, String email, String mobile, String gender, String address, String userStatus, String imageUrl, String fcmId) {
        this.googleId = googleId;
        this.authId = authId;
        this.name = name;
        this.nic = nic;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.address = address;
        this.userStatus = userStatus;
        this.imageUrl = imageUrl;
        FcmId = fcmId;
    }

    public Admin(String authId, String name, String email, String mobile, String address, String userStatus) {
        this.authId = authId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.userStatus = userStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFcmId() {
        return FcmId;
    }

    public void setFcmId(String fcmId) {
        FcmId = fcmId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }


}
