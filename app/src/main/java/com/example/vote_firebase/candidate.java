package com.example.vote_firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class candidate {
    public String deputy;
    public String name;
    public String party;
    public String image;
    public boolean isSelected;
    public candidate(){

    }
    public candidate(String deputy,String name,String party,String image,boolean isSelected){
        this.deputy=deputy;
        this.name=name;
        this.party=party;
        this.image = image;
        this.isSelected = isSelected;
    }

    public String getDeputy() {
        return deputy;
    }

    public boolean isSelected(){return isSelected;}

    public void setSelected(boolean selected){isSelected=selected;}

    public String getname() {
        return name;
    }

    public String getParty() {
        return party;
    }
    public String getimage(){return image;}
}
