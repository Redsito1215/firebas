package com.example.firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database ;
    EditText txt_temperatura_edit, txt_humedad_edit;
    DatabaseReference HumedadRef, presionRef, VelocidadRef, TemperauraRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        database = FirebaseDatabase.getInstance();
        HumedadRef = 	database.getReference("sensores/humedad");
        presionRef = database.getReference("sensores/presion");
        VelocidadRef = 	database.getReference("sensores/velocidad");
        TemperauraRef = 	database.getReference("sensores/temperatura");

        TextView txtTemp = findViewById(R.id.valorTemperatura);
        TemperauraRef.addValueEventListener(setListener(txtTemp, "°C"));
        TextView txtHumedad = findViewById(R.id.valorHumedad);
        HumedadRef.addValueEventListener(setListener(txtHumedad, "%"));
        TextView txtPresion = findViewById(R.id.valorPresion);
        presionRef.addValueEventListener(setListener(txtPresion, "hPa"));
        TextView txtVelocidad = findViewById(R.id.valorVelocidad);
        VelocidadRef.addValueEventListener(setListener(txtVelocidad, "Km/h"));

        txt_temperatura_edit = findViewById(R.id.edtTemperatura);
        txt_humedad_edit = findViewById(R.id.edtHumedad);

    }
    public void clickBotonTemp(View view){
        TemperauraRef.setValue(Float.parseFloat(txt_temperatura_edit.getText().toString()));
    }

    public void clickBotonHumedad(View view){
        HumedadRef.setValue(Float.parseFloat(txt_humedad_edit.getText().toString()));
    }

    public ValueEventListener setListener(TextView txt, String UnidadMedida){

        return (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txt.setText(snapshot.getValue().toString() + " " + UnidadMedida);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                txt.setText("");
            }
        });
    }
}