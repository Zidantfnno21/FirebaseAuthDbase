package com.example.tixid;

import android.os.Parcel;
import android.os.Parcelable;

public class DataRV implements Parcelable {
    private String namaData;
    private String hargaData;
    private String targetData;
    private String linkGambarData;
    private String linkData;
    private String deskripsiData;
    private String dataID;

    protected DataRV(Parcel in) {
        namaData = in.readString();
        hargaData = in.readString();
        targetData = in.readString();
        linkGambarData = in.readString();
        linkData = in.readString();
        deskripsiData = in.readString();
        dataID = in.readString();
    }

    public static final Creator<DataRV> CREATOR = new Creator<DataRV>() {
        @Override
        public DataRV createFromParcel(Parcel in) {
            return new DataRV(in);
        }

        @Override
        public DataRV[] newArray(int size) {
            return new DataRV[size];
        }
    };

    public String getNamaData() {
        return namaData;
    }

    public void setNamaData(String namaData) {
        this.namaData = namaData;
    }

    public String getHargaData() {
        return hargaData;
    }

    public void setHargaData(String hargaData) {
        this.hargaData = hargaData;
    }

    public String getTargetData() {
        return targetData;
    }

    public void setTargetData(String targetData) {
        this.targetData = targetData;
    }

    public String getLinkGambarData() {
        return linkGambarData;
    }

    public void setLinkGambarData(String linkGambarData) {
        this.linkGambarData = linkGambarData;
    }

    public String getLinkData() {
        return linkData;
    }

    public void setLinkData(String linkData) {
        this.linkData = linkData;
    }

    public String getDeskripsiData() {
        return deskripsiData;
    }

    public void setDeskripsiData(String deskripsiData) {
        this.deskripsiData = deskripsiData;
    }

    public String getDataID() {
        return dataID;
    }

    public void setDataID(String dataID) {
        this.dataID = dataID;
    }

    public DataRV(String namaData, String hargaData, String targetData, String linkGambarData, String linkData, String deskripsiData, String dataID) {
        this.namaData = namaData;
        this.hargaData = hargaData;
        this.targetData = targetData;
        this.linkGambarData = linkGambarData;
        this.linkData = linkData;
        this.deskripsiData = deskripsiData;
        this.dataID = dataID;
    }



    public DataRV(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(namaData);
        parcel.writeString(hargaData);
        parcel.writeString(targetData);
        parcel.writeString(linkGambarData);
        parcel.writeString(linkData);
        parcel.writeString(deskripsiData);
        parcel.writeString(dataID);
    }
}
