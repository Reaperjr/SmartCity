package nuno.estg.smartcity.ui_main;

import java.io.Serializable;

public class SubmissionModel implements Serializable {

    private int id_submission;
    private String assunto;
    private double lat;
    private double lng;
    private String obs;
    private String img;
    private int id_user;
    private String data;

    public SubmissionModel(int id_submission, String assunto, double lat, double lng, String obs, String img, int id_user, String data) {
        this.id_submission = id_submission;
        this.assunto = assunto;
        this.lat = lat;
        this.lng = lng;
        this.obs = obs;
        this.img = img;
        this.id_user = id_user;
        this.data = data;
    }

    public SubmissionModel() {

    }

    public String toString() {
        return "id : " + id_submission + "\nassunto : " + assunto + "\nlat : " + lat + "\nlng : " + lng + "\nobs : " + obs + "\nidUser : " + id_user + "\ndata : " + data;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public void remove(int position) {
    }
}
