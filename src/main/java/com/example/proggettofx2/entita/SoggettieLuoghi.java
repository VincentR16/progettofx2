package com.example.proggettofx2.entita;

import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

public class SoggettieLuoghi
{

    private List<String> soggetti;
    private List<String> categorie;

    public SoggettieLuoghi()
    {
        soggetti= new ArrayList<>();
        categorie= new ArrayList<>();
    }

    public List<String> getCategorie() {
        return categorie;
    }

    public List<String> getSoggetti() {
        return soggetti;
    }

    public void setSoggetti(List<String> soggetti) {
        this.soggetti = soggetti;
    }

    public void setCategorie(List<String> categorie) {
        this.categorie = categorie;
    }

    public void setBox(ComboBox<String> comboBox)
    {
        for (String s : categorie)
        {
            comboBox.getItems().add(s);
        }
    }
}
