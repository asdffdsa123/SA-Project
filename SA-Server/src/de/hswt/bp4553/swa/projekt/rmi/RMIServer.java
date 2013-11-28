package de.hswt.bp4553.swa.projekt.rmi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.base.Throwables;

import de.hswt.bp4553.swa.projekt.client.rmi.RegistrationService;
import de.hswt.bp4553.swa.projekt.model.Registration;
import de.hswt.bp4553.swa.projekt.server.GroupRegistrationParser;
import de.hswt.bp4553.swa.projekt.server.ServerConfig;
import de.hswt.bp4553.swa.projekt.server.persistence.RegistrationPersistence;
import de.hswt.bp4553.swa.projekt.server.persistence.RegistrationPersistenceFactory;

/**
 * Implementiert den RegistrationService, und stellt das interface für RMI Clients bereit.
 * @author bp4553
 *
 */
public class RMIServer extends UnicastRemoteObject
    implements RegistrationService{

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(RMIServer.class.getName());
    
    public RMIServer() throws RemoteException, MalformedURLException{
        startRMIRegistration();
        ServerConfig conf = ServerConfig.getInstance();
        String uri = String.format("//%s:%d/%s", conf.getHost(), conf.getRMIPort(), conf.getRMIServiceName());
        Naming.rebind(uri, this);
        log.info("Service bound: " + uri);
    }
    
    private static void startRMIRegistration(){
        try{
            LocateRegistry.createRegistry(ServerConfig.getInstance().getRMIPort());
            log.info("RMI Registry created");
        }catch(RemoteException e){
            log.info("RMI Registry existed and was not created");
        }
    }

    @Override
    public Collection<Registration> anmelden(Registration reg) throws RemoteException {
		//Delay every request to simulate hard work
		delay();
        RegistrationPersistence pers = RegistrationPersistenceFactory.getRegistrationPersistence();
        try {
            pers.insert(reg);
            return pers.getAll();
        }
        catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public Collection<Registration> anmelden(List<String> lines) throws RemoteException {
		//Delay every request to simulate hard work
		delay();
        RegistrationPersistence pers = RegistrationPersistenceFactory.getRegistrationPersistence();
        try {
            for(Registration r : GroupRegistrationParser.parse(lines)){
                pers.insert(r);
            }
            return pers.getAll();
        }
        catch (IOException | ParseException e) {
            throw Throwables.propagate(e);
        }
        
    }
    
	private static void delay(){
	       try {
	            Thread.sleep(2000);
	        }
	        catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
}
