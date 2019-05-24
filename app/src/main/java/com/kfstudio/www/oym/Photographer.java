package com.kfstudio.www.oym;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Photographer {
    private ArrayList<String> ph_photo;
    private String ph_profile_image_url;
    private String ph_name;
    private String ph_email;
    private String ph_experience;
    private String ph_location;
    private String ph_rating;
    private String ph_description;
    private String ph_website;
    private String ph_phone_number;
    private String ph_time;

    public Photographer(){

    }
    public Photographer(String ph_phone_number,String ph_profile_image_url,String ph_name,String ph_email,String ph_experience,String ph_location,String
            ph_rating,String ph_description,String ph_website,ArrayList<String> ph_photo,String ph_time){
            this.ph_profile_image_url=ph_profile_image_url;
            this.ph_name=ph_name;
            this.ph_email=ph_email;
            this.ph_experience=ph_experience;
            this.ph_location=ph_location;
            this.ph_rating=ph_rating;
            this.ph_description=ph_description;
            this.ph_website=ph_website;
            this.ph_photo=ph_photo;
            this.ph_phone_number=ph_phone_number;
            this.ph_time=ph_time;
    }

    public String getPh_email() {
        return ph_email;
    }

    public ArrayList<String> getPh_photo() {
        return ph_photo;
    }

    public String getPh_profile_image_url() {
        return ph_profile_image_url;
    }

    public String getPh_name() {
        return ph_name;
    }

    public String getPh_experience() {
        return ph_experience;
    }

    public String getPh_location() {
        return ph_location;
    }

    public String getPh_rating() {
        return ph_rating;
    }

    public String getPh_description() {
        return ph_description;
    }

    public String getPh_website() {
        return ph_website;
    }

    public String getPh_phone_number() {
        return ph_phone_number;
    }

    public String getPh_time() {
        return ph_time;
    }
}
