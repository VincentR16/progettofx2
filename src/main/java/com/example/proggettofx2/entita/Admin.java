package com.example.proggettofx2.entita;

import javafx.scene.control.Label;


public class Admin
{
    private String Password;
    private Label labelfoto;
    private Label labelutenti;
    private String selectedusers;

    public Admin(){}
    public Admin(Label labelfoto1,Label labelutent1i )
    {
        labelfoto = labelfoto1;
        labelutenti = labelutent1i;
    }

    public String getSelectedusers() {return selectedusers;}

    public void setSelectedusers(String selectedusers) {this.selectedusers = selectedusers;}

    public Label getLabelfoto() {return labelfoto;}
    public Label getLabelutenti() {return labelutenti;}
    public void setPassword(String S)  {Password=S;}
    public String getPassword() {return Password;}
}
