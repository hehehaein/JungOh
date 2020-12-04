package com.example.jung_oh;

public class List_data {
    private String Name;
    private String ImgUrl;
    public List_data(){}

    public List_data(String name, String imgUrl){
        if(name.trim().equals(""))
            Name="No name";
        Name = name;
        ImgUrl = imgUrl;
    }
    public String getName(){
        return Name;

    }
    public void setName(String name){
        Name = name;
    }
    public String getImgUrl(){
        return ImgUrl;
    }
    public void setImgUrl(String imgUrl){
        ImgUrl = imgUrl;
    }
}
