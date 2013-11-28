package de.hswt.bp4553.swa.projekt.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import de.hswt.bp4553.swa.projekt.client.RegistrationRemoteClient;
import de.hswt.bp4553.swa.projekt.client.rmi.RegistrationService;
import de.hswt.bp4553.swa.projekt.model.Registration;
import de.hswt.bp4553.swa.projekt.server.ServerConfig;

/**
 * Implementiert das RegistrationRemoteClient interface mit RMI Aufrufen.
 * @author bp4553
 *
 */
public class RMIClient implements RegistrationRemoteClient{
    
    private final RegistrationService regist;
    
    public RMIClient() throws MalformedURLException, RemoteException, NotBoundException{
        ServerConfig conf = ServerConfig.getInstance();
        regist = (RegistrationService) Naming.lookup(String.format("//%s:%d/%s", conf.getHost(), conf.getRMIPort(), conf.getRMIServiceName()));
    }
    
    public Collection<Registration> register(Registration reg) throws RemoteException{
        return regist.anmelden(reg);
    }
    
    public Collection<Registration> groupRegister(List<String> lines) throws RemoteException{
        return regist.anmelden(lines);
    }

}
