package de.hswt.bp4553.swa.projekt.client;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import de.hswt.bp4553.swa.projekt.model.Registration;

/**
 * Dieses interface abstrahiert einen Client welcher mit dem Server kommunizieren kann.
 * Wie die Kommunikation funktioniert obligt der Subklasse.
 * SocketClient und RMIClient implementieren dieses interface.
 * @author bp4553
 *
 */
public interface RegistrationRemoteClient {
	
	/**
	 * Registriert eine einzelne Person beim Server.
	 * @param reg
	 * @return
	 * @throws IOException
	 */
	Collection<Registration> register(Registration reg) throws IOException;
	
	/**
	 * Registriert eine Guppe beim Server. Es wird eine CSV übergeben in der die Personen zeilenweise aufgelistet sind.
	 * @param lines Die Zeilen einer CSV Datei
	 * @return
	 * @throws IOException
	 */
	Collection<Registration> groupRegister(List<String> lines) throws IOException;

}
