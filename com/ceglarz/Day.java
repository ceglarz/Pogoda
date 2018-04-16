package com.ceglarz;


import javafx.beans.property.SimpleStringProperty;

public class Day {
 
        private  SimpleStringProperty dayProperty;
        private  SimpleStringProperty temProperty;
        private  SimpleStringProperty wilgProperty;
        private  SimpleStringProperty cisProperty;
 
        public Day(String dProp, String tProp, String wProp, String cProp ){
            this.dayProperty = new SimpleStringProperty(dProp);
            this.temProperty = new SimpleStringProperty(tProp);
            this.wilgProperty = new SimpleStringProperty(wProp);
            this.cisProperty = new SimpleStringProperty(cProp);
        }
        
           public String getDayProperty() {
            return dayProperty.get();
        }
 
        public void setDayProperty(String d) {
            dayProperty.set(d);
        }
 
        public String getTemProperty() {
            return temProperty.get();
        }
 
        public void setTemProperty(String t) {
            temProperty.set(t);
        }
 
        public String getWilgProperty() {
            return wilgProperty.get();
        }
 
        public void setWilgProperty(String w) {
           wilgProperty.set(w);
        }
 
        public String getCisProperty() {
            return cisProperty.get();
        }
 
        public void setCisProperty(String c) {
            cisProperty.set(c);
        }
    }