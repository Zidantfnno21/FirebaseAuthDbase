package com.example.tixid;

import android.os.Parcel;
import android.os.Parcelable;

public class UserRV implements Parcelable {
    private String username;
    private String email;
    private String password;
    private String idUsername;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRV(String username, String email, String password, String idUsername) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.idUsername = idUsername;
    }

    public UserRV(){

    }

    protected UserRV(Parcel in) {
        username = in.readString();
        password = in.readString();
        email = in.readString();
        idUsername = in.readString();
    }

    public static final Creator<UserRV> CREATOR = new Creator<UserRV>() {
        @Override
        public UserRV createFromParcel(Parcel in) {
            return new UserRV(in);
        }

        @Override
        public UserRV[] newArray(int size) {
            return new UserRV[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdUsername() {
        return idUsername;
    }

    public void setIdUsername(String idUsername) {
        this.idUsername = idUsername;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(idUsername);
        parcel.writeString(email);
    }
}
