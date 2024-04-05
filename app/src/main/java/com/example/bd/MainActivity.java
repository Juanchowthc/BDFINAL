package com.example.bd;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String urlServidor = "http://192.168.1.60/Registro/insertar.php";

    EditText Nombre, Email, Password;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Nombre = findViewById(R.id.Nombre);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);

        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(v -> {
            String nombre = Nombre.getText().toString();
            String email = Email.getText().toString();
            String password = Password.getText().toString();

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("nombre", nombre);
                jsonObject.put("email", email);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlServidor, jsonObject,
                    response -> {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                Toast.makeText(MainActivity.this, "Cliente registrado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Error al registrar cliente", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        Log.e(TAG, "Error de conexión", error);
                        Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    });

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObjectRequest);
        });
    }
}