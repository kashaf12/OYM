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
    private String ph_rating;
    private String ph_rate;
    private String ph_extra;
    private String ph_location;
    private String ph_no_pic;
    private String ph_speciality;
    private String ph_website;
    private String ph_description;
    private String ph_likes;

    public Photographer(){

    }
    public Photographer(ArrayList<String> ph_photo, String ph_profile_image_url, String ph_name,
                        String ph_email, String ph_experience, String ph_rating, String ph_rate,
                        String ph_extra, String ph_location, String ph_no_pic, String ph_speciality,
                        String ph_website, String ph_description, String ph_likes) {
        this.ph_photo = ph_photo;
        this.ph_profile_image_url = ph_profile_image_url;
        this.ph_name = ph_name;
        this.ph_email = ph_email;
        this.ph_experience = ph_experience;
        this.ph_rating = ph_rating;
        this.ph_rate = ph_rate;
        this.ph_extra = ph_extra;
        this.ph_location = ph_location;
        this.ph_no_pic = ph_no_pic;
        this.ph_speciality = ph_speciality;
        this.ph_website = ph_website;
        this.ph_description = ph_description;
        this.ph_likes = ph_likes;
    }

    public void setPh_photo(ArrayList<String> ph_photo) {
        this.ph_photo = ph_photo;
    }

    public void setPh_profile_image_url(String ph_profile_image_url) {
        this.ph_profile_image_url = ph_profile_image_url;
    }

    public void setPh_name(String ph_name) {
        this.ph_name = ph_name;
    }

    public void setPh_email(String ph_email) {
        this.ph_email = ph_email;
    }

    public void setPh_experience(String ph_experience) {
        this.ph_experience = ph_experience;
    }

    public void setPh_rating(String ph_rating) {
        this.ph_rating = ph_rating;
    }

    public String getPh_extra() {
        return ph_extra;
    }

    public void setPh_extra(String ph_extra) {
        this.ph_extra = ph_extra;
    }

    public String getPh_location() {
        return ph_location;
    }

    public void setPh_location(String ph_location) {
        this.ph_location = ph_location;
    }

    public String getPh_no_pic() {
        return ph_no_pic;
    }

    public void setPh_no_pic(String ph_no_pic) {
        this.ph_no_pic = ph_no_pic;
    }

    public void setPh_website(String ph_website) {
        this.ph_website = ph_website;
    }


    public void setPh_description(String ph_description) {
        this.ph_description = ph_description;
    }


    public String getPh_likes() {
        return ph_likes;
    }

    public void setPh_likes(String ph_likes) {
        this.ph_likes = ph_likes;
    }

    public String getPh_speciality() {
        return ph_speciality;
    }

    public void setPh_speciality(String ph_speciality) {
        this.ph_speciality = ph_speciality;
    }

    public String getPh_rate() {
        return ph_rate;
    }

    public void setPh_rate(String ph_rate) {
        this.ph_rate = ph_rate;
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

    public String getPh_rating() {
        return ph_rating;
    }

    public String getPh_description() {
        return ph_description;
    }

    public String getPh_website() {
        return ph_website;
    }


}
