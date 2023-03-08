package com.example.androidjuttu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

//nää taas yritystä yhdistää tietokantaan.. saa poistaa :D
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class MainActivity extends AppCompatActivity {
    //(yritys siitä miten sen yhdistää siihen, ei toimi.)
//    String path = "tietokannan tiedostosijainti?";
//    SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //tää tässä alla kutsutaan kun joku painaa jotain niistä napeista
    // //(pitäisikö jokaiselle napille olla oma kutsunsa?)
    public class painallus {
        {
            int x = 2;
            if (x == 0) { //oikeavastaus = se nappi jota just painettiin*/
                // change button.color to green ja +1 piste//
            }
            //else change.button.color to red ?*/
        }
    }
}
