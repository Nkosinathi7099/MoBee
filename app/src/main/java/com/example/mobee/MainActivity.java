package com.example.mobee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(MainActivity.this,Login.class));
                finish();
            }
        },3000);
    }

    static class DInfo{

        public String fname;
        public String cell_no;
        public String ema;
        public String doc_ID;
        //public String qual;

        public DInfo(String fname, String doc_ID,String cell_no, String ema){
            this.fname=fname;
           this.cell_no = cell_no;
            this.doc_ID = doc_ID;
            this.ema = ema;

        }

        public DInfo(){

        }

    }
}
