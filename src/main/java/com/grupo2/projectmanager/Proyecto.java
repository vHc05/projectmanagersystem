package com.grupo2.projectmanager;

public class Proyecto {
    private int id;
    private String projectName;
    private String responsable;
    private String startdate;
    private String enddate;
    private String status;

    public Proyecto(int id, String projectName, String responsable, String startdate, String enddate, String status) {
        this.id = id;
        this.projectName = projectName;
        this.responsable = responsable;
        this.startdate = startdate;
        this.enddate = enddate;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Proyecto{" + "id=" + id + ", projectName=" + projectName + ", responsable=" + responsable + ", startdate=" + startdate + ", enddate=" + enddate + ", status=" + status + '}';
    }

    
}
