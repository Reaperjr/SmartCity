package nuno.estg.smartcity.ui_main;

import java.io.Serializable;

public class SubmissionModelList implements Serializable {

    private int id_submission;
    private String assunto;
    private double lat;
    private double lng;
    private String obs;
    private int id_user;
    private String data;

    public SubmissionModelList(int id_submission, String assunto, double lat, double lng, String obs, int id_user, String data) {
        this.id_submission = id_submission;
        this.assunto = assunto;
        this.lat = lat;
        this.lng = lng;
        this.obs = obs;
        this.id_user = id_user;
        this.data = data;
    }

    public SubmissionModelList() {

    }

    public int getId_submission() {
        return id_submission;
    }

    public void setId_submission(int id_submission) {
        this.id_submission = id_submission;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}