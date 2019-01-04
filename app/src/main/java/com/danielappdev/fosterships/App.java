package com.danielappdev.fosterships;

import android.app.Application;

public class App extends Application {
        Event E = new Event();

        public void setEvent(Event e){
            E = e;
        }
        public Event getEvent(){
            return E;
        }
}
