package nuno.estg.smartcity.ui_notes.notes;

import java.io.Serializable;

public class NotesModel implements Serializable {

    private String assunto;
    private String rua;
    private String local;
    private String codpostal;
    private String data;
    private String obs;
    private int id = 0;

    public NotesModel(String assunto, String rua, String local, String codpostal, String data, String obs, int id) {
        this.assunto = assunto;
        this.rua = rua;
        this.local = local;
        this.codpostal = codpostal;
        this.data = data;
        this.obs = obs;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getCodpostal() {
        return codpostal;
    }

    public void setCodpostal(String codpostal) {
        this.codpostal = codpostal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}
