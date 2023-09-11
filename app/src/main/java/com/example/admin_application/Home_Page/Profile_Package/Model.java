package com.example.admin_application.Home_Page.Profile_Package;

public class Model {


    private String username,name,lastname,phone,image;
    private int id;
    private String get_Response;


    public Model(String username, String name, String lastname, String phone, String image, int id, String get_Response) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.image = image;
        this.id = id;
        this.get_Response = get_Response;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGet_Response() {
        return get_Response;
    }

    public void setGet_Response(String get_Response) {
        this.get_Response = get_Response;
    }
}
