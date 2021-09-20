package com.example.electronic_voting;

public class Party {
    private String Name;
    private String Image;
    private String Vote;


    public Party() {
    }

    public Party(String Name, String Image,String Vote) {
        this.Name = Name;
        this.Image = Image;
        this.Vote = Vote;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getVote() {
        return Vote;
    }

    public void setVote(String vote) {
        Vote = vote;
    }

}
