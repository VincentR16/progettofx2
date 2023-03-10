package com.example.proggettofx2.entita;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.util.ArrayList;
import java.util.List;

public class Video
{
    private  List<Image> images;
    private  List<String>dispositivo;
    private  List<String>city;
    private  List<String>soggetti;

    public Video()  {setimagini();}
    public List<String> getSoggetti() {return soggetti;}
    public List<Image> getImages() {return images;}
    public List<String> getCity() {return city;}
    public List<String> getDispositivo() {return dispositivo;}
    public void setSoggetti(List<String> soggetti) {this.soggetti = soggetti;}
    public void setCity(List<String> city) {this.city = city;}
    public void setDispositivo(List<String> dispositivo) {this.dispositivo = dispositivo;}

    public void setimagini()
    {
        images = new ArrayList<>();

        Fotografie foto = Fotografie.getInstance();

        for (ImageView imageView : foto.getListafoto())
        {
            images.add(imageView.getImage());
        }
    }
}
