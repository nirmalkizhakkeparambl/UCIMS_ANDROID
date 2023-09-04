package com.gisfy.unauthorizedconstructions.SQLite;

public class Model {
    int id;
    String draftsman;
    String ownername;
    String fathername;
    String phoneno;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }


    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public Model(int id, String draftsman, String ownername, String fathername, String phoneno) {
        this.id = id;
        this.draftsman = draftsman;
        this.ownername = ownername;
        this.fathername = fathername;
        this.phoneno = phoneno;
    }
}