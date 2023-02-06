

#    README

Progetto 2023,  
OOBD_T2G02 traccia numero 2.

## Descrizione generale 

Progettazione e Implementazione di un sistema software dedito alla archiviazione e alla gestione 
di foto.  
Il software si avvale di un database locale, creato e gestito tramite PostgreSQL.       

### Scelta del linguaggio

Il software è stato programmato tramite javafx e non java-swing.
In modo tale da poter approfondire e implementare al meglio la rappresentazione grafica del progetto.
Javafx propone numerosi strumenti per la gestione dell' output d' immagini, facilitando cosi la gestione delle immagini.


### Composizione del software
Il software si compone di diverse [pagine](src/main/resources/com/example/proggettofx2) create mediante [SceneBuilder](https://gluonhq.com/products/scene-builder/).
Le pagine sono tradotte automaticamente in [xml](https://it.wikipedia.org/wiki/XML).   
Abbiamo progettato personalmente tutte le pagine, cercando di dare un identità univoca a quest'ultime e provando, per quanto non sia nelle nostre competenze,
di mantenere un design semplice e intuitivo cosi da risultare **user friendly**.

#### Il codice 

Il codice è stato originariamente pensato sul modello *BCE*, ci siamo però imbattuti in diverse difficoltà, la principale
è stata la sovrabbondaza di file ridondanti.  
Infatti ogni pagina di output corrispondeva rispettivamente a ***file.xml --> Controller_del_file--> Classe_identità*** 
![](https://user-images.githubusercontent.com/119407465/216968160-ebc5bf2f-0439-47dc-90b0-edb1f6f9908c.png | )

![](https://user-images.githubusercontent.com/119407465/216968167-d7613bf8-e673-4e3b-a916-e8f324cf909d.png | )



Il codice del software è composto da diversi file, dei quali solo uno non è accompagnato dalla scritta di *Controller*.
La scelta d'inserire all'interno dei nomi questa *qualifica* è dovuta al fatto, che questi file non si possono definire,
in termini stretti, come delle semplici classi in java. 
I controller infatti sono dei veri e propri punti d' ingresso rispetto alle azioni che l'utente svolge 
in quello **specifico momento**.

Trovandoci difronte a numerose classi di questo tipo è stato deciso d'implementare un'ulteriore classe chiamata 

