package com.example.hitesh054.ifsc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hitesh054 on 14-02-2018.
 */

public class data implements Parcelable {
    String strbank;
    String strifsc;
    String strBranch;
    String strAddress;
    String strContact;
    String strCity;
    String strRtgs;
    String strDistrict;
    String strState;

    public data(String strbank, String strifsc, String strBranch, String strAddress, String strContact, String strCity,
                String strRtgs, String strDistrict, String strState) {
        this.strbank = strbank;
        this.strifsc = strifsc;
        this.strBranch = strBranch;
        this.strAddress = strAddress;
        this.strContact = strContact;
        this.strCity = strCity;
        this.strDistrict = strDistrict;
        this.strState = strState;
        this.strRtgs = strRtgs;


    }

    public String getStrbank() {
        return strbank;
    }

    public void setStrbank(String strbank) {
        this.strbank = strbank;
    }

    public String getStrifsc() {
        return strifsc;
    }

    public void setStrifsc(String strifsc) {
        this.strifsc = strifsc;
    }

    public String getStrBranch() {
        return strBranch;
    }

    public void setStrBranch(String strBranch) {
        this.strBranch = strBranch;
    }

    public String getStrAddress() {
        return strAddress;
    }

    public void setStrAddress(String strAddress) {
        this.strAddress = strAddress;
    }

    public String getStrContact() {
        return strContact;
    }

    public void setStrContact(String strContact) {
        this.strContact = strContact;
    }

    public String getStrCity() {
        return strCity;
    }

    public void setStrCity(String strCity) {
        this.strCity = strCity;
    }

    public String getStrRtgs() {
        return strRtgs;
    }

    public void setStrRtgs(String strRtgs) {
        this.strRtgs = strRtgs;
    }

    public String getStrDistrict() {
        return strDistrict;
    }

    public void setStrDistrict(String strDistrict) {
        this.strDistrict = strDistrict;
    }

    public String getStrState() {
        return strState;
    }

    public void setStrState(String strState) {
        this.strState = strState;
    }

    protected data(Parcel in) {
        strbank = in.readString();
        strifsc = in.readString();
        strBranch = in.readString();
        strAddress = in.readString();
        strContact = in.readString();
        strCity = in.readString();
        strRtgs = in.readString();
        strDistrict = in.readString();
        strState = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(strbank);
        dest.writeString(strifsc);
        dest.writeString(strBranch);
        dest.writeString(strAddress);
        dest.writeString(strContact);
        dest.writeString(strCity);
        dest.writeString(strRtgs);
        dest.writeString(strDistrict);
        dest.writeString(strState);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<data> CREATOR = new Parcelable.Creator<data>() {
        @Override
        public data createFromParcel(Parcel in) {
            return new data(in);
        }

        @Override
        public data[] newArray(int size) {
            return new data[size];
        }
    };
}