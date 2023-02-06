

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

Il codice del software è composto da diversi file, dei quali solo uno non è accompagnato dalla scritta di *Controller*.
La scelta d'inserire all'interno dei nomi questa *qualifica* è dovuta al fatto, che questi file non si possono definire,
in termini stretti, come delle semplici classi in java.
I controller infatti sono dei veri e propri punti d' ingresso rispetto alle azioni che l'utente svolge
in quello **specifico momento**.

Il codice è stato originariamente pensato sul modello *BCE*, ci siamo però imbattuti in diverse difficoltà, la principale
è stata la sovrabbondaza di file ridondanti.  
Ciò poichè se in java-swing il file della classe del corrispettivo *stage* aggiunge informazioni essenziali riguardanti
la composizione stessa.
In javafx tutte le informazioni necessarie sono contenunte all'interno dei *file.xml*, il quale comunica per costruzione
solamente con il corrispettivo *Controller*.
Inoltre in FX le componenti come ad esempio 
#### Blocco Codice 
    @FXML
    private TextField textfiled;
sono legate all 'url stesso della pagina, per questo se si prova a definirli in un classe diversa dal proprio
*controller*, la componente stessa perde di significato, non comparendo a schermo e risultando spoglia rispetto alle modifiche 
fatte tramite SceneBuilder.


In precedenta ogni pagina di output corrispondeva rispettivamente a ***file.xml --> Controller_del_file--> Classe_identità***.

Questo sistema non era adatto a Javafx, e informandoci è stato deciso, di semplifiacare in codice in 
questo modo ***file.xml--> Controller_del_file_--> ClasseController_principale***. 

In particolar modo abbiamo deciso di eliminare tutte le singole classi che si riferivano al rispettivo controller, e di aggiungere, un'unica 
classe chiamata *MainController* che svolga tutte le opeazioni di calcolo e tutte le operazioni ripetute in più controller.


