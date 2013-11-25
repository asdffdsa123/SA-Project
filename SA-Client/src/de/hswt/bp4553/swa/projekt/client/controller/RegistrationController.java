package de.hswt.bp4553.swa.projekt.client.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.SwingUtilities;

import com.google.common.base.Charsets;

import de.hswt.bp4553.swa.projekt.client.view.RegistrationView;
import de.hswt.bp4553.swa.projekt.model.Registration;
import de.hswt.bp4553.swa.projekt.rmi.RMIClient;
import de.hswt.bp4553.swa.projekt.socket.ServerConfig;
import de.hswt.bp4553.swa.projekt.socket.SocketClient;

public class RegistrationController implements RegistrationHandler{
	
	private RegistrationView view;
	
	public RegistrationController() {
		super();
		view = new RegistrationView(this);
		view.setVisible(true);
	}
	
	private void registerWithSockets(Registration reg){
		try{
			SocketClient client = new SocketClient(ServerConfig.getInstance());
			view.showRegistrations(client.register(reg));
		}catch(Exception e){
			view.showError(e);
		}
	}
	
	private void groupRegisterWithSockets(File file){
	       try{
	            SocketClient client = new SocketClient(ServerConfig.getInstance());
	            view.showRegistrations(client.groupRegister(Files.readAllLines(file.toPath(), Charsets.UTF_8)));
	        }catch(Exception e){
	            view.showError(e);
	        }
	}
	
	private void registerWithRMI(Registration reg){
	    try {
            RMIClient client = new RMIClient();
            view.showRegistrations(client.insert(reg));
        }
        catch (MalformedURLException | RemoteException | NotBoundException e) {
            view.showError(e);
        }
	}
	
	private void groupRegisterWithRMI(File file){
        try{
            RMIClient client = new RMIClient();
            view.showRegistrations(client.groupInsert(Files.readAllLines(file.toPath(), Charsets.UTF_8)));
        }catch(Exception e){
            view.showError(e);
        }
	}

	public void addRegistrationPressed(final Registration reg, final ConnectionType type) {
	    new Thread(new Runnable() {
            
            @Override
            public void run() {
                switch(type){
                case Socket:
                    registerWithSockets(reg);
                    break;
                case rmi:
                    registerWithRMI(reg);
                    break;
                default:
                    throw new RuntimeException("Not Implemented");
                }                
            }
        }).start();
	}

    public void addGroupPressed(final File selectedFile, final ConnectionType type) {
        view.setBusy(true);
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                switch(type){
                case Socket:
                    groupRegisterWithSockets(selectedFile);
                    break;
                case rmi:
                    groupRegisterWithRMI(selectedFile);
                    break;
                default:
                    throw new RuntimeException("Not Implemented");
                }           
                SwingUtilities.invokeLater(new Runnable() {
                    
                    @Override
                    public void run() {
                        view.setBusy(false);
                    }
                });
            }
        }).start();
    }

	@Override
	public void add(Registration reg) {
		addRegistrationPressed(reg, view.getSelectedConnectionType());
	}

	@Override
	public void groupAdd(File file) {
		addGroupPressed(file, view.getSelectedConnectionType());
	}

}
