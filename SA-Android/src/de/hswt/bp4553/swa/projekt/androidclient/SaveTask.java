package de.hswt.bp4553.swa.projekt.androidclient;

import java.io.IOException;

import android.os.AsyncTask;
import android.widget.Toast;
import de.hswt.bp4553.swa.projekt.client.socket.SocketClient;
import de.hswt.bp4553.swa.projekt.model.Registration;

/**
 * Dieser asynchrone Task speichert eine Registrierung auf dem Server.
 * @author bp4553
 *
 */
public class SaveTask extends AsyncTask<Object, Object, Exception>{
	
	private final Registration reg;
	private final MainActivity activity;
	private final SocketClient client;
	
	/**
	 * 
	 * @param activity Die Activity auf der ein Ergebnis Popup angezeigt wird
	 * @param reg Die Registrierung die abgepeichert werden soll
	 * @param client Der Client der verwendet wird, um mit dem Server kontakt aufzunehmen
	 */
	public SaveTask(MainActivity activity, Registration reg, SocketClient client){
		this.activity = activity;
		this.reg = reg;
		this.client = client;
	}

	@Override
	protected void onPreExecute() {
		activity.setBusy(true);
	}

	@Override
	protected Exception doInBackground(Object... params) {
		try {
			client.register(reg);
		} catch (IOException e) {
			return e;
		}
		return null;
	}

	@Override
	protected void onPostExecute(Exception result) {
		activity.setBusy(false);
		String text;
		if(result != null){
			text = "Fehler beim Verbinden mit dem Server: "+result.getMessage();
		}else{
			text = "Die Person wurde Erfolgreich registriert!";
		}
		Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
	}
	
	

}
