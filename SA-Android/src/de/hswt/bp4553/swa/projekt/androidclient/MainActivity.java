package de.hswt.bp4553.swa.projekt.androidclient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import de.hswt.bp4553.swa.projekt.client.socket.SocketClient;
import de.hswt.bp4553.swa.projekt.model.Fakulty;
import de.hswt.bp4553.swa.projekt.model.Gender;
import de.hswt.bp4553.swa.projekt.model.Registration;
import de.hswt.bp4553.swa.projekt.server.ServerConfig;

/**
 * Die GUI des Clients, mit welcher eine Registrierung eingegeben werden kann.
 * @author bp4553
 *
 */
public class MainActivity extends Activity {
	
	private static final DateFormat FORMAT = SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN);
	
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
	
	/**
	 * Startet den Speichervorgang der Registierung.
	 * Wird vom Speichern Button aufgerufen.
	 * @param view
	 */
	public void save(View view){
		try {
			Registration reg = parseRegistration();
			new SaveTask(this, reg, client).execute();
		} catch (Exception e) {
			Toast.makeText(this, "Fehler beim Einlesen: "+e.getMessage(), Toast.LENGTH_SHORT).show();
			return;
		} 

	}
	
	/**
	 * Zeigt Progressbar an oder versteckt sie.
	 * @param b
	 */
	public void setBusy(boolean b){
		ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
		int v;
		if(b){
			v = View.VISIBLE;
		}else{
			v = View.INVISIBLE;
		}
		bar.setVisibility(v);
	}
	
	/**
	 * Erzeugt aus den eingegenen Daten ein Registration objekt.
	 * @return
	 * @throws ParseException
	 */
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
