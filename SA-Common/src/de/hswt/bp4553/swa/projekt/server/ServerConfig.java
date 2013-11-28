package de.hswt.bp4553.swa.projekt.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Laed die Konfiguration des Servers, und stellt standard werte bereit.
 * Diese Config wird auch von den Clients verwendet, um Adressen, Port, usw zu erhalten.
 * Diesen Objekt ist ein Singleton.
 * @author bp4553
 *
 */
public class ServerConfig {
	
	private static final Logger log = Logger.getLogger(ServerConfig.class.getName());
	
	private static final ServerConfig INST = new ServerConfig();
	
	private final Properties prop = new Properties();
	
	private ServerConfig(){
		InputStream in = null;
		try{
			in = ServerConfig.class.getResourceAsStream("server.config");
			if(in == null){
				throw new FileNotFoundException();
			}
			prop.load(in);
		} catch (IOException e) {
			log.log(Level.WARNING, "Could not read config file", e);
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {}
			}
		}
	}
	
	/**
	 * Der Port auf dem der SocketServer hört.
	 * @return
	 */
	public int getSocketPort(){
		return Integer.parseInt(prop.getProperty("socket-port", "36552"));
	}
	
	/**
	 * Der Port auf dem der RMI Server hört.
	 * @return
	 */
	public int getRMIPort(){
	    return Integer.parseInt(prop.getProperty("rmi-port", "55223"));
	}
	
	/**
	 * Die Adresse des Servers.
	 * @return
	 */
	public String getHost(){
		return prop.getProperty("address", "localhost");
	}

	/**
	 * Der name des RMI Services.
	 * @return
	 */
    public String getRMIServiceName() {
        return prop.getProperty("rmi-service-name", "service");
    }

    /**
     * Die Persistenzmethode die der Server verwendet.
     * @return
     */
	public RegistrationPersistenceType getRegistrationPersistenceType() {
		String str = prop.getProperty("persistence");
		if(str == null){
			return RegistrationPersistenceType.Java;
		}
		return RegistrationPersistenceType.valueOf(str);
	}
	
	public static ServerConfig getInstance(){
		return INST;
	}

}
