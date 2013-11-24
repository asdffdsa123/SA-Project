package de.hswt.bp4553.swa.projekt.androidclient;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import de.hswt.bp4553.swa.projekt.model.Fakulty;
import de.hswt.bp4553.swa.projekt.model.Gender;
import de.hswt.bp4553.swa.projekt.model.Registration;
import de.hswt.bp4553.swa.projekt.socket.ServerConfig;
import de.hswt.bp4553.swa.projekt.socket.SocketClient;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final DateFormat FORMAT = SimpleDateFormat.getDateInstance();
	
	private SocketClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Spinner genderSpinner = (Spinner) findViewById(R.id.gender_spinner);
		genderSpinner.setAdapter(createAdapter(Gender.values()));
		Spinner fakultySpinner = (Spinner) findViewById(R.id.faculty_spinner);
		fakultySpinner.setAdapter(createAdapter(Fakulty.values()));
		client = new SocketClient(ServerConfig.getInstance());
	}
	
	private ArrayAdapter<String> createAdapter(Object[] objs){
		String[] strs = new String[objs.length];
		for(int i = 0; i < strs.length; i++){
			strs[i] = objs[i].toString();
		}
		return new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void save(View view){
		Registration reg;
		try {
			reg = parseRegistration();
		} catch (Exception e) {
			Toast.makeText(this, "Fehler beim Einlesen: "+e.getMessage(), Toast.LENGTH_SHORT).show();
			return;
		} 
		try {
			client.register(reg);
		} catch (IOException e) {
			Toast.makeText(this, "Fehler beim Verbinden mit dem Server", Toast.LENGTH_SHORT).show();
		}
	}

	private Registration parseRegistration() throws ParseException{
		return new Registration(s(R.id.firstname), s(R.id.lastname), FORMAT.parse(s(R.id.birthday)), 
								Fakulty.values()[i(R.id.faculty_spinner)], Gender.values()[i(R.id.gender_spinner)]);
	}
	
	private String s(int id){
		return ((EditText)findViewById(id)).getText().toString();
	}
	
	private int i(int id){
		return ((Spinner)findViewById(id)).getSelectedItemPosition();
	}
}
