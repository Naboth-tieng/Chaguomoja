package com.example.vote_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    String county;
    TextView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            county= extras.getString("county");
        }
        about = findViewById(R.id.fact);
        Toast.makeText(MainActivity2.this,county,Toast.LENGTH_SHORT).show();
        //about.setText("did you know Kisumu, which literally means a place of barter trade 'sumo' and officially known as Kisumu City, is the Kenyan inland port city on Lake Victoria and the capital city of Kisumu County, Kenya. It is the third largest city in Kenya after the capital, Nairobi, and the coastal city of Mombasa.");


        if(county.equals("Kisumu")){
            about.setText("did you know Kisumu, which literally means a place of barter trade 'sumo' and officially known as Kisumu City, is the Kenyan inland port city on Lake Victoria and the capital city of Kisumu County, Kenya. It is the third largest city in Kenya after the capital, Nairobi, and the coastal city of Mombasa.");
        }else if (county.equals("Nairobi")){
            about.setText("Did you know that for two consecutive years in the recent past, Nairobi won the title of being the ‘Most Intelligent City.’");
        }else if(county.equals("Kiambu")){
            about.setText("Kiambu is characteristic of fertile soils and plenty of rainfall. There are numerous high potential small holder farms, which pack enough potential to not only feed the county, but also supply Nairobi, Kitui and Kajiado with dairy products, foodstuffs, green vegetables and fresh fruit.");
        }
    }
}