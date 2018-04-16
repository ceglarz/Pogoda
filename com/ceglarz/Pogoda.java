package com.ceglarz;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 *
 * @author Oskar
 */

public class Pogoda extends Application {
    
     //http://api.openweathermap.org/data/2.5/forecast?q=London&mode=xml&APPID=55d9f2190839e8c710cc3df680eecac1
        private String forecastUrl = "http://api.openweathermap.org/data/2.5/forecast?"
            + "q=CITY&mode=xml&units=metric&APPID=55d9f2190839e8c710cc3df680eecac1";
        private Document document;
        
        final Spinner<Integer> dni = new Spinner<>();
        
        Button pobierzButton = new Button("Pobierz Dane");
        Label dniLabel = new Label("Liczba dni: ");
        CheckBox temperatura = new CheckBox("temperatura");
        CheckBox wilgotnosc = new CheckBox("wilgotnosc");
        CheckBox cisnienie = new CheckBox("cisnienie");
            
        
        VBox siatkaGlowna = new VBox(10);
        HBox buttonsHBox = new HBox(10);
        
        VBox choiceboxsVBox = new VBox(10);
        HBox citiesHBox = new HBox(10);
        
        HBox daneHBox = new HBox(10);
        
        TableView table1 = new TableView<>();
        TableView table2 = new TableView<>();
        TableView table3 = new TableView<>();
        
        VBox miasto1VBox = new VBox();
        VBox miasto2VBox = new VBox();
        VBox miasto3VBox = new VBox();
        
        Label m1Label = new Label();
        Label m2Label = new Label();
        Label m3Label = new Label();
        
        ScrollPane sp1 = new ScrollPane();
        ScrollPane sp2 = new ScrollPane();
        ScrollPane sp3 = new ScrollPane();
        
        
        TableColumn m1data = new TableColumn("Data");
        TableColumn m1t = new TableColumn("T");
        TableColumn m1w = new TableColumn("W");
        TableColumn m1c = new TableColumn("C");
        TableColumn m2data = new TableColumn("Data");
        TableColumn m2t = new TableColumn("T");
        TableColumn m2w = new TableColumn("W");
        TableColumn m2c = new TableColumn("C");
        TableColumn m3data = new TableColumn("Data");
        TableColumn m3t = new TableColumn("T");
        TableColumn m3w = new TableColumn("W");
        TableColumn m3c = new TableColumn("C");
        
        
        ObservableList<Day> dzienDoTabeli = FXCollections.observableArrayList(
        );
        ObservableList<Day> dzienDoTabeli2 = FXCollections.observableArrayList(
        );
        ObservableList<Day> dzienDoTabeli3 = FXCollections.observableArrayList(
        );
  
        
    @Override
    public void start(Stage primaryStage) {
        

        siatkaGlowna.setPadding(new Insets(10,10,10,10));

        // Pierwszy przycisk

        final int initialValue = 2;
        // Value factory.
        SpinnerValueFactory<Integer> valueFactory = //
        new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 5, initialValue);
        dni.setValueFactory(valueFactory);
        
 
            buttonsHBox.getChildren().addAll( pobierzButton, dniLabel, dni, temperatura, wilgotnosc, cisnienie);

         
            Label miastoLabel = new Label("Wybierz miasta: ");
            List<String> citiesList = new ArrayList<String>();
            citiesList.add("Lublin");
            citiesList.add("Warszawa");
            citiesList.add("Wrocław");
            citiesList.add("Berlin");
            citiesList.add("Miami");
            citiesList.add("New York");
 
          ChoiceBox miasto1 = new ChoiceBox();
          miasto1.getItems().addAll(citiesList);
          ChoiceBox miasto2 = new ChoiceBox();
          miasto2.getItems().addAll(citiesList);
          ChoiceBox miasto3 = new ChoiceBox();
          miasto3.getItems().addAll(citiesList);

            choiceboxsVBox.getChildren().addAll( miastoLabel, miasto1, miasto2, miasto3 );
            
            sp1.setContent(table1);
            sp2.setContent(table2);
            sp3.setContent(table3);
             
            m1data.setSortable(true);
            m1t.setSortable(false);
            m1c.setSortable(false);
            m1w.setSortable(false);
            m2data.setSortable(true);
            m2t.setSortable(false);
            m2c.setSortable(false);
            m2w.setSortable(false);
            m3data.setSortable(true);
            m3t.setSortable(false);
            m3c.setSortable(false);
            m3w.setSortable(false);
            
        
           table1.getColumns().addAll(m1data, m1t, m1w, m1c);
           table2.getColumns().addAll(m2data, m2t, m2w, m2c);
           table3.getColumns().addAll(m3data, m3t, m3w, m3c);
           miasto1VBox.getChildren().addAll( m1Label, sp1);
           miasto2VBox.getChildren().addAll( m2Label, sp2);
           miasto3VBox.getChildren().addAll( m3Label, sp3);
           citiesHBox.getChildren().addAll( miasto1VBox, miasto2VBox, miasto3VBox );
           
            m1data.prefWidthProperty().bind(table1.widthProperty().divide(4)); // w * 1/4
            m1t.prefWidthProperty().bind(table1.widthProperty().divide(4)); // w * 1/4
            m1w.prefWidthProperty().bind(table1.widthProperty().divide(4)); // w * 1/4
            m1c.prefWidthProperty().bind(table1.widthProperty().divide(4)); // w * 1/4
            
            m2data.prefWidthProperty().bind(table1.widthProperty().divide(4)); // w * 1/4
            m2t.prefWidthProperty().bind(table1.widthProperty().divide(4)); // w * 1/4
            m2w.prefWidthProperty().bind(table1.widthProperty().divide(4)); // w * 1/4
            m2c.prefWidthProperty().bind(table1.widthProperty().divide(4)); // w * 1/4
            
            m3data.prefWidthProperty().bind(table1.widthProperty().divide(4)); // w * 1/4
            m3t.prefWidthProperty().bind(table1.widthProperty().divide(4)); // w * 1/4
            m3w.prefWidthProperty().bind(table1.widthProperty().divide(4)); // w * 1/4
            m3c.prefWidthProperty().bind(table1.widthProperty().divide(4)); // w * 1/4
   
           

            
           //przycisk
           temperatura.setSelected(true);
            temperatura.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(temperatura.isSelected()){
                table1.getColumns().add(m1t);
                table2.getColumns().add(m2t);
                table3.getColumns().add(m3t);
                }
                else {
                table1.getColumns().remove(m1t);
                table2.getColumns().remove(m2t);
                table3.getColumns().remove(m3t);}
            }
        });//koniec przycisku
            
            //przycisk
           wilgotnosc.setSelected(true);
            wilgotnosc.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(wilgotnosc.isSelected()){
                table1.getColumns().add(m1w);
                table2.getColumns().add(m2w);
                table3.getColumns().add(m3w);
                }
                else {
                table1.getColumns().remove(m1w);
                table2.getColumns().remove(m2w);
                table3.getColumns().remove(m3w);}
            }
        });//koniec przycisku
            
            //przycisk
           cisnienie.setSelected(true);
            cisnienie.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(cisnienie.isSelected()){
                table1.getColumns().add(m1c);
                table2.getColumns().add(m2c);
                table3.getColumns().add(m3c);
                }
                else {
                table1.getColumns().remove(m1c);
                table2.getColumns().remove(m2c);
                table3.getColumns().remove(m3c);}
            }
        });//koniec przycisku
           
           
          //przycisk
          // Ustawiamy akcję
        pobierzButton.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
            System.out.println(dni.getValue());
            System.out.println(temperatura.isSelected());
            System.out.println(wilgotnosc.isSelected());
            System.out.println(cisnienie.isSelected());
  
 // tabela 1
            if(!((String)miasto1.getValue()).equals("null")){
                System.out.println(miasto1.getValue());
                m1Label.setText((String)miasto1.getValue());
                
            if(!dzienDoTabeli.isEmpty())  dzienDoTabeli.remove(0, dzienDoTabeli.size());
            
                wczytajDane((String)miasto1.getValue(), 1 ,dni.getValue());
                
                m1data.setCellValueFactory(
                        new PropertyValueFactory<Day, String>("dayProperty")
                );
                
                m1c.setCellValueFactory(
                        new PropertyValueFactory<Day, String>("cisProperty")
                );
                                 
                m1t.setCellValueFactory(
                        new PropertyValueFactory<Day, String>("temProperty")
                );
                 
                m1w.setCellValueFactory(
                        new PropertyValueFactory<Day, String>("wilgProperty")
                );
                
                table1.setItems(dzienDoTabeli);
            }
            else{}
//koniec tabeli

 // tabela 2
            if(!((String)miasto2.getValue()).equals("null")){
                System.out.println(miasto2.getValue());
                m2Label.setText((String)miasto2.getValue());
                
            if(!dzienDoTabeli2.isEmpty())  dzienDoTabeli2.remove(0, dzienDoTabeli2.size());
            

                wczytajDane((String)miasto2.getValue(), 2 , dni.getValue());
                
                m2data.setCellValueFactory(
                        new PropertyValueFactory<Day, String>("dayProperty")
                );
                
                m2c.setCellValueFactory(
                        new PropertyValueFactory<Day, String>("cisProperty")
                );
                                 
                m2t.setCellValueFactory(
                        new PropertyValueFactory<Day, String>("temProperty")
                );
                 
                m2w.setCellValueFactory(
                        new PropertyValueFactory<Day, String>("wilgProperty")
                );
                
                table2.setItems(dzienDoTabeli2);
            }
            else{}
//koniec tabeli

 // tabela 3
            if(!((String)miasto3.getValue()).equals("null")){
                System.out.println(miasto3.getValue());
                m3Label.setText((String)miasto3.getValue());
                
            if(!dzienDoTabeli3.isEmpty())  dzienDoTabeli3.remove(0, dzienDoTabeli3.size());
            

                wczytajDane((String)miasto3.getValue(), 3 , dni.getValue());
                
                m3data.setCellValueFactory(
                        new PropertyValueFactory<Day, String>("dayProperty")
                );
                
                m3c.setCellValueFactory(
                        new PropertyValueFactory<Day, String>("cisProperty")
                );
                                 
                m3t.setCellValueFactory(
                        new PropertyValueFactory<Day, String>("temProperty")
                );
                 
                m3w.setCellValueFactory(
                        new PropertyValueFactory<Day, String>("wilgProperty")
                );
                
                table3.setItems(dzienDoTabeli3);
            }
            else{}
//koniec tabeli
            

        });//koniec przycisku
        

         daneHBox.getChildren().addAll(choiceboxsVBox, citiesHBox );
        siatkaGlowna.getChildren().addAll(buttonsHBox, daneHBox);
        
        StackPane root = new StackPane();
        root.getChildren().add(siatkaGlowna);
        Scene scene = new Scene(root, 1000, 300);
        
        primaryStage.setTitle("Pogoda");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
     public void wczytajDane(String city, int tabela , int day){
        
        
         forecastUrl = forecastUrl.replaceFirst("CITY", city);

                URL url;
                URLConnection connection;
                try {
                    url = new URL(forecastUrl);
                    connection = url.openConnection();
                    document = parseXML(connection.getInputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //String miasto, kiedy, temperatura, cisnienie, wilgotnosc;
               String miasto = "Prognoza pogody dla miasta: " + document.getElementsByTagName("name").
                       item(0).getTextContent();
                String kiedy = "Data: " + formatDate(document.getElementsByTagName("time").item(0).getAttributes()
                        .getNamedItem("from").getNodeValue().replaceFirst("T"," "));
                String temp = "Temperatura: " + document.getElementsByTagName("temperature")
                        .item(0).getAttributes().getNamedItem("value").getNodeValue() + "°C";
                String cisn = "Ciśnienie: " + document.getElementsByTagName("pressure")
                        .item(0).getAttributes().getNamedItem("value").getNodeValue() + "hPa";
                String wilgo = "Wilgotność: " + document.getElementsByTagName("humidity")
                        .item(0).getAttributes().getNamedItem("value").getNodeValue() + "%";
                   System.out.println( miasto + " " + kiedy + " " + temp +  " " + cisn + " " + wilgo );
                   
                   
                   switch(tabela)
                   {
                      case 1:
                          for (int i = 0 ; i  < day ; i++){
                   dzienDoTabeli.add(new Day(
                           formatDate(document.getElementsByTagName("time")
                            .item(0+i*8).getAttributes().getNamedItem("from").getNodeValue().replaceFirst("T"," ")),
                           (String)(document.getElementsByTagName("temperature")
                           .item(0+i*8).getAttributes().getNamedItem("value").getNodeValue()) + "°C",
                           (String)(document.getElementsByTagName("pressure")
                            .item(0+i*8).getAttributes().getNamedItem("value").getNodeValue()) + "hPa",
                           (String)(document.getElementsByTagName("humidity")
                            .item(0+i*8).getAttributes().getNamedItem("value").getNodeValue()) + "%"));
                   }
                      break;
                      
                      case 2:
                          for (int i = 0 ; i  < day ; i++){
                   dzienDoTabeli2.add(new Day(
                           formatDate(document.getElementsByTagName("time")
                            .item(0+i*8).getAttributes().getNamedItem("from").getNodeValue().replaceFirst("T"," ")),
                           (String)(document.getElementsByTagName("temperature")
                           .item(0+i*8).getAttributes().getNamedItem("value").getNodeValue()) + "°C",
                           (String)(document.getElementsByTagName("pressure")
                            .item(0+i*8).getAttributes().getNamedItem("value").getNodeValue()) + "hPa",
                           (String)(document.getElementsByTagName("humidity")
                            .item(0+i*8).getAttributes().getNamedItem("value").getNodeValue()) + "%"));
                   }
                      break;
                      
                      case 3:
                           for (int i = 0 ; i  < day ; i++){
                   dzienDoTabeli3.add(new Day(
                           formatDate(document.getElementsByTagName("time")
                            .item(0+i*8).getAttributes().getNamedItem("from").getNodeValue().replaceFirst("T"," ")),
                           (String)(document.getElementsByTagName("temperature")
                           .item(0+i*8).getAttributes().getNamedItem("value").getNodeValue()) + "°C",
                           (String)(document.getElementsByTagName("pressure")
                            .item(0+i*8).getAttributes().getNamedItem("value").getNodeValue()) + "hPa",
                           (String)(document.getElementsByTagName("humidity")
                            .item(0+i*8).getAttributes().getNamedItem("value").getNodeValue()) + "%"));
                   }
                      break;
                   }
                   
                   
                   
                //przywrocenie starego url, zeby nastepnym razem mogl zamienic 'city' na nowa nazwe miasta wybranego
                // z listy
                forecastUrl = "http://api.openweathermap.org/data/2.5/forecast?"
                        + "q=CITY&mode=xml&units=metric&APPID=55d9f2190839e8c710cc3df680eecac1";
                
}

     

private Document parseXML(InputStream stream) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();

        return builder.parse(stream);
    }

 private String formatDate(String pubDate){
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(pubDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new SimpleDateFormat("dd.MM").format(date);
    }
    

}//koniec klasy
