package com.ivantimarket.ivanti.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection="downloads")
public class Download {

    @Transient
    public static final String SEQUENCE_NAME = "download_sequence";

    @Id
    private long id;

    @NotNull
    private long userId;

    @NotNull
    private long packageId;

    @NotNull
    private String versionName;

    public Download(long id, long userId, long packageId, String versionName) {
        this.id = id;
        this.userId = userId;
        this.packageId = packageId;
        this.versionName = versionName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public String getVersionName() {return versionName;}

    public void setVersionName(String versionName) {this.versionName = versionName;}
}
