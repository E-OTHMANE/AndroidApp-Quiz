package com.example.quizapp_c31.entities;

public class Questions {

    private  int id;
    private String libeller;
    private String reponse;
    private String imageURL;


    public void setId(int id) {
        this.id = id;
    }

    public void setLibeller(String libeller) {
        this.libeller = libeller;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public Questions(String libeller, String reponse, String imageURL) {
        this.libeller = libeller;
        this.reponse = reponse;
        this.imageURL = imageURL;
    }

    public String getLibeller() {
        return libeller;
    }

    public String getReponse() {
        return reponse;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Questions() {
    }

    @Override
    public String toString() {
        return "Question{" +
                "libeller='" + libeller + '\'' +
                ", reponse='" + reponse + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
