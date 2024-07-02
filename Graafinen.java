package com.example.harjoitustyo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

public class Graafinen extends Application implements Serializable{

    //Luodaan tyhja lista
    private ArrayList <Kortti> korttilista = new ArrayList<Kortti>();

    public void start(Stage ikkuna) {

        //Alapaneeli Nosta kortti-ja Katso nostot-napeille
        HBox alapaneeli = new HBox();
        Button nosta = new Button("Nosta kortti!");
        Button katso = new Button("Katso oikein arvatut nostot!");
        alapaneeli.setSpacing(30);
        alapaneeli.setAlignment(Pos.BOTTOM_CENTER);
        alapaneeli.getChildren().addAll(nosta, katso);
        alapaneeli.setStyle("-fx-border-color: red");
        nosta.setStyle("-fx-border-color: red");
        katso.setStyle("-fx-border-color: red");

        //Sivupaneeli arvausboxille
        Label label = new Label("Arvaa! ");
        Label label2 = new Label("Rasti = yli 6 ");
        Label label3 = new Label("Ei rastia = alle 6 ");
        Label label4 = new Label("Yli vai alle 6? ");
        VBox sivupaneeli = new VBox();
        CheckBox yli = new CheckBox();
        sivupaneeli.setSpacing(20);
        sivupaneeli.setAlignment(Pos.CENTER);
        sivupaneeli.getChildren().addAll(label,label2,label3, yli,label4);

        Text voitto = new Text("Jee, arvasit oikein :)");
        voitto.setFont(new Font("Arial", 30));
        Text havio = new Text("Höh, arvauksesi meni väärin :(");
        havio.setFont(new Font("Arial", 30));
        voitto.setFill(Color.RED);
        havio.setFill(Color.RED);

        //Keskipaneeli kortin arvolle
        HBox keskipaneeli = new HBox();
        Text tyhja = new Text(" ");
        tyhja.setFont(new Font("Arial", 20));
        keskipaneeli.setAlignment(Pos.CENTER);
        keskipaneeli.getChildren().add(tyhja);

        //Paapaneeli, johon yhdistetaan kaikki edelliset paneelit
        BorderPane paneeli = new BorderPane();
        paneeli.setBottom(alapaneeli);
        paneeli.setRight((sivupaneeli));
        paneeli.setCenter(keskipaneeli);
        paneeli.setStyle("-fx-border-color: red");

        //kehys ja paneeli toiselle ikkunalle
        VBox poplaatikko = new VBox();

        Scene kehys2 = new Scene(poplaatikko,500, 400);
        Button takaisin = new Button("Takaisin peliin");

        Scene kehys1 = new Scene(paneeli, 500, 400);
        ikkuna.setTitle("Suurempi vai pienempi?");
        ikkuna.setScene(kehys1);
        ikkuna.show();

        //Toiminto nosta kortti-napille
        //Toiminnot arvausboxille ja nostettuun korttiin vertaamiselle
        nosta.setOnAction(e ->{
            Korttipakka korttipakka = new Korttipakka();
            Kortti uusikortti = korttipakka.nostaKortti();
            tyhja.setText(String.valueOf(uusikortti));

            if(uusikortti.Tarkista(yli.isSelected())){
                voitto.setText(voitto.getText());
                paneeli.setTop(voitto);

                korttilista.add(uusikortti);
                ObjectOutputStream ktiedosto = null;
                try {
                    Kortti[] array = korttilista.toArray(new Kortti[0]);
                    ktiedosto = new ObjectOutputStream(new FileOutputStream("teksti.dat"));
                    ktiedosto.writeObject(array);
                    ktiedosto.close();
                    
                } catch (IOException ax) {
                    ax.printStackTrace();
                }
            }
            else {
                havio.setText(havio.getText());
                paneeli.setTop(havio);
            }
            });

        //Lisataan toisen ikkunan paneeliin TableView
        TableView table = new TableView();

        //Lisataan sarakkeet
        TableColumn<Kortti, String> sarake1 = new TableColumn<>("Kortin maa");
        sarake1.setCellValueFactory(new PropertyValueFactory("maa"));
        TableColumn<Kortti, String> sarake2 = new TableColumn<>("Kuva-arvo");
        sarake2.setCellValueFactory(new PropertyValueFactory("kuvakortti"));
        table.getColumns().addAll(sarake1, sarake2);

        Label otsikko = new Label("Oikein arvatut nostot!");
        otsikko.setFont(new Font("Arial",20));
        table.setEditable(false);

        poplaatikko.setAlignment(Pos.BOTTOM_CENTER);
        poplaatikko.getChildren().addAll(otsikko, table, takaisin);
        poplaatikko.setStyle("-fx-border-color: red");
        takaisin.setStyle("-fx-border-color: red");


        //toiminto katso nostot-napille
        katso.setOnAction(e->{
            ObjectInputStream ltiedosto = null;
            Kortti[] klista = null;
            try{
                ltiedosto = new ObjectInputStream(new FileInputStream("teksti.dat"));
                klista= (Kortti[])ltiedosto.readObject();
                ltiedosto.close();

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            ikkuna.setScene(kehys2);

            for (Kortti k: klista
                 ) {
                System.out.println(k.getMaa() + " " + k.getKuvakortti());
                table.getItems().add(k);
            }
        });

        //Toiminto lukuikkunasta takaisin peliin siirtymiseen
        takaisin.setOnAction(e->{
            ikkuna.setScene(kehys1);
        });

    }
    public static void main(String[] args) {
        launch(args);
    }
}

