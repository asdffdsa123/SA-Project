package de.hswt.bp4553.swa.projekt.socket;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerConfig {
	
	private static final Logger log = Logger.getLogger(ServerConfig.class.getName());
	
	private static final ServerConfig INST = new ServerConfig();
	
	private final Properties prop = new Properties();
	
	private ServerConfig(){
		try(InputStream in = ServerConfig.class.getResourceAsStream("server.config")){
			if(in == null){
				throw new FileNotFoundException();
			}
			prop.load(in);
		} catch (IOException e) {
			log.log(Level.WARNING, "Could not read config file", e);
		}
	}
	
	public int getSocketPort(){
		return Integer.parseInt(prop.getProperty("socket-port", "36552"));
	}
	
	public int getRMIPort(){
	    return Integer.parseInt(prop.getProperty("rmi-port", "55223"));
	}
	
	public String getHost(){
		return prop.getProperty("address", "localhost");
	}
	
	public static ServerConfig getInstance(){
		return INST;
	}

    public String getRMIServiceName() {
        return prop.getProperty("rmi-service-name", "service");
    }

}
