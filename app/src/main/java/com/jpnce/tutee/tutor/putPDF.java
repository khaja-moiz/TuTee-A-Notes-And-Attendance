package com.jpnce.tutee.tutor;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
    public class putPDF{
        public String name, url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public putPDF(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }
//no prevoius activity add

