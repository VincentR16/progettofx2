
#    README

Progetto 2023,  
OOBD_T2G17 traccia numero 2.

## Descrizione generale 

Progettazione e Implementazione di un sistema software dedito alla archiviazione e alla gestione 
di foto.  
Il software si avvale di un database locale, creato e gestito tramite PostgreSQL.       

### Scelta del linguaggio

Il software è stato programmato tramite javafx e non java-swing, in modo tale da poter approfondire e implementare al meglio la rappresentazione grafica del progetto.
Javafx propone numerosi strumenti per la gestione dell' output d' immagini, facilitando così la gestione delle foto.


## Composizione del software
Il software è composto da diverse [pagine](src/main/resources/com/example/proggettofx2) create mediante [SceneBuilder](https://gluonhq.com/products/scene-builder/).
Le pagine sono tradotte automaticamente in [xml](https://it.wikipedia.org/wiki/XML).   
Abbiamo progettato personalmente tutte le pagine, cercando di dare un' identità univoca a quest'ultime e provando, per quanto non sia nelle nostre competenze,
a mantenere un design semplice e intuitivo così da risultare **user friendly**.

### Il Modello 

Il software è suddiviso in package, package DAO,Controller,Entita,Gui.

Il software è stato organizzato  sul modello *BCED*,
Tutte le informazioni della GUI  sono contenunte all'interno dei *file.xml*, i quali comunicano per costruzione
solamente con il corrispettivo *Controller*.
Inoltre FX le componenti, come ad esempio 
 
    @FXML
    private TextField textfiled;
sono legate all 'url stesso della pagina, per questo se si prova a definirli in una classe diversa dal proprio
*controller*, la componente stessa perde di significato, non comparendo a schermo e risultando spoglia rispetto alle modifiche 
fatte tramite SceneBuilder.


il modello del progetto su base *BCE* è quindi organizzato in questo modo ***file.xml --> Controller_del_file--> Classe_entità***.

In particolar modo abbiamo deciso di non utilizzare un singolo Controller genrale, ma di mantenere tutte le singole classi *controller*.
classe chiamata *MenuController* estende tutti i Controller e si occupa del passaggio di controllo tra un controllo e l'altro tramite la classe *Mystage*.


### Il Codice 

Se da una parte lavorare con **Javafx** ha semplificato la gestione delle foto, dall' altra è risultato notevolmente 
difficile dover imparare un nuovo linguaggio, così simile, ma allo stesso tempo così differente da java-swing. 
Infatti nonostante provengano entrambi da java, non sono in nessun modo compatibili (se non con librerie esterne, o metodi molto articolati).          
Risulta per questo quasi impossibile inserire un componente swing in uno **stage** di FX o viceversa.
É stata necessaria, una fase preliminare volta alla comprensione del linguaggio.
Durante questo processo il gruppo si è avvalso sia delle nozioni presenti sul libro di corso(in particolare dei capitoli scaricabili), sia delle nozioni presenti 
on-line su siti [ufficiali](https://fxdocs.github.io/docs/html5/) e [video-corsi](https://www.youtube.com/watch?v=_7OM-cMYWbQ&list=PLZPZq0r_RZOM-8vJA3NQFZB7JroDcMwev).

Nonostante la mole d'informazioni a disposizione, è stato necessario e stimolante confrontarsi con
altri programmatori tramite [StackOverflow](https://stackoverflow.com/).

Il codice è stato rimaneggiato molteplici volte, talvolta del tutto stravolto.
Ciò è dovuto sicuramente all'inesperienza, infatti in molti casi l' approccio principale ai problemi era sicuramente
poco fluido.
Abbiamo cercato quindi, ogniqualvolta la nostra esperienza progrediva, di rivedere il lavoro svolto **semplificando e snellendo il codice**.

### Librerie esterne e gestione delle foto

Il codice si avvale di due **librerie** sotto forma di file.jar. Per entrambe è stato necessario dover modificare le dipendenze
del codice nel file [module-info.java](src/main/java/module-info.java), solo per la seconda si è dovuto
anche modificare le dipendenze del file [maven](pom.xml).

La prima libreria è quella del diver ***JDBC***, che viene utilizzata per mettere in collegamento il database e Intelij.    
La seconda si chiama **SwingFXUtils**

    requires javafx.swing;

e viene utilizzata per mettere in collegamento Javafx con Java, in particolare per poter trasformare una buffered image (sottoclasse di java.awt.Image) in un' immagine writable
sottoclasse di image(javafx.scene.Image).
Ovvero,in altre parole, la classe Image di javafx è completamente differente dalla classe Image di Java 'classico'.  
Per quanto possa risultare anomalo le due classi, non solo sono incompatibili, ma hanno anche sottoclassi ben differenti e con scopi assolutamente non sovrapponibili.

A tal proposito e a causa della necessità di trasformare dei dati **bytea** in immagini,è risultato obbligatorio aggiungere questa libreria esterna.    
I dati sono stati elaborati in questo modo:


        InputStream in = new ByteArrayInputStream(binaryData);                                                                  
        BufferedImage Bimage = ImageIO.read(in);

        ImageView imageView= new ImageView();
        imageView.setUserData(id_foto)

        imageView.setImage( SwingFXUtils.toFXImage(Bimage,null));  



Nella prima riga i dati **binaryData**  ricevuti dal database vengono trasformati in **InputStream**.
Successivamente, grazie al metodo *read* di *ImageIo*, i dati vegono letti e inseriti all' interno di una **BufferedImage**.

Quest'ultima viene convertita in una ***WritableImage*** tramite il metodo *SwingFXUtils.toFXImage* e inserite all' interno di una *ImageView*.
Il processo viene eseguito per ogni foto.   
Inoltre si necessitava far corrispondere ogni foto al proprio id; per semplicità si è voluto evitare l'utilizzo di
una lista **ID** da far scorrere, e le si è preferito un piccolo stratagemma, ovvero utilizzare *setUsersData* per immagazzinare i dati
e *getUsersData* per riceverli.

### Output delle foto

La gestione dell'output delle foto si sviluppa su più livelli.
In primo luogo si è avuta l'esigenza di trovare una componente in javafx adatta.
Le componenti Javafx, vengono considerate come dei nodi in un albero, e di conseguenza, strettamente collegate tra di
loro; non conoscendo *xml* l'unico modo per interagire con quest'ultimi è tramite **SceneBuilder**.
Da qui deriva la principale difficoltà; ovvero come si possono inserire **N Imageview** in modo ordinato, senza poter in alcun
modo modificare la posizione dei nodi.
L'idea iniziale era quella di racchiudere tutte le Imageview in un' altra componente. È stato deciso di utilizzare un **Tilepane** all' interno di uno **Scrollpane**.
Grazie all'uso dello **Scrollpane** si è potuto creare una *scene* dinamica, che si allarga e si restringe all'occorrenza.
L'utilizzo del *Tilepane* risultò però confusionario e poco ordinato, è stato per questo sostituito con un *Gridpane*, grazie al quale si è potuto impostare lo spazio tra una foto e l 'altra.

Infine dopo alcune prove si è visto che inserendo il *Gridpane* attraverso *SceneBuilder* quest'ultimo risultava solamente
sovrapposto allo **Scrollpane** e non contenuto in esso, si è quindi optato per aggiungerlo manualmente nel codice, tramite il 
metodo *setContent*.


### Video

La creazione del video presente nell' applicazione si è svolta in più passaggi.
Inizialmente si è avuto un processo di progettazione, le idee fondamentalmente erano due, la prima quella di utilizzare
una librerie esterna chiamata *Mediaplayer*, che desse la possibilità all'utente di creare dei video con sottofondi musicali.
La seconda ipotesi era quella di far scorrere delle foto al passare del tempo.  
Si è scelta la seconda per semplicità. 
Per realizzarla si è dovuto inserire in **SceneBuilder** una *imageview*, e si è
dovuto creare un piccola animazione.


    final long[] inizio = {System.currentTimeMillis()};
    final int[] indice = {0};

        animationTimer = new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                long tempocorrente= System.currentTimeMillis();

                if(tempocorrente- inizio[0] >= 4000)
                {
                    if(indice[0] <images.size()){videoview.setImage(images.get(indice[0]));}
                    else {indice[0]=-1;}

                    indice[0]++;

                    inizio[0] =tempocorrente;
                }
            }
        };

Il primo passo è quello d'inserire all'interno di un **array** il tempo corrente. Successivamente si va a definire
***animationTimer*** specificando sia le istruzioni sia la condizione per essere eseguita.
In questo specifico caso ***l'animazione*** si avvia ogni ***4 secondi***.
Infatti a tener conto del tempo passato è la differenza tra *tempocorrente* e *inzio*.
Inoltre grazie alle seguenti istruzioni:

        if(indice[0] <images.size())
            else {indice[0]=-1;}

una volta terminate tutte le foto, l'animazione riparte da capo, in modo tale che sia solo *l'utente* a poter scegliere 
di far fermare il video.

In altre parole questa piccola animazione, si comporta come un **listner** e tramite il metodo **handle(l)** tiene conto di ogni 
cambiamento della variabile **tempocorrente** svolgendo il codice solo quando le condizioni create sono rispettate.


