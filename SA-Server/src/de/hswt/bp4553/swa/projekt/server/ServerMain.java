package de.hswt.bp4553.swa.projekt.server;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hswt.bp4553.swa.projekt.rmi.RMIServer;
import de.hswt.bp4553.swa.projekt.socket.ServerConfig;
import de.hswt.bp4553.swa.projekt.socket.SocketServer;

public class ServerMain {
    
    private static final Logger log = Logger.getLogger(ServerMain.class.getName());
    
    public static void main(String[] args){
    	ServerConfig prop = ServerConfig.getInstance();
    	new SocketServer().start();
    	try {
            new RMIServer();
        }
        catch (RemoteException | MalformedURLException e) {
            log.log(Level.SEVERE, "Could not start RMI server", e);
        }
    }

}
