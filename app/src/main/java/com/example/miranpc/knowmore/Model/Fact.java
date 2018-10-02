package com.example.miranpc.knowmore.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Fact implements Parcelable {

    @PrimaryKey
    private int id;
    private String topic;
    private String resource;
    private String fact;


    public Fact(int id, String topic, String resource, String fact) {
        this.id = id;
        this.topic = topic;
        this.resource = resource;
        this.fact = fact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public static Creator<Fact> getCREATOR() {
        return CREATOR;
    }


    protected Fact(Parcel in) {
        topic = in.readString();
        resource = in.readString();
        fact = in.readString();
    }

    public static final Creator<Fact> CREATOR = new Creator<Fact>() {
        @Override
        public Fact createFromParcel(Parcel in) {
            return new Fact(in);
        }

        @Override
        public Fact[] newArray(int size) {
            return new Fact[size];
        }
    };

    @Ignore
    public Fact(String topic, String resource, String fact) {
        this.topic = topic;
        this.resource = resource;
        this.fact = fact;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(topic);
        parcel.writeString(resource);
        parcel.writeString(fact);
    }
}
