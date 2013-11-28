package de.hswt.bp4553.swa.projekt.client.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.google.common.base.Charsets;

import de.hswt.bp4553.swa.projekt.client.RegistrationRemoteClient;
import de.hswt.bp4553.swa.projekt.client.model.RegistrationModel;
import de.hswt.bp4553.swa.projekt.client.socket.SocketClient;
import de.hswt.bp4553.swa.projekt.client.view.RegistrationView;
import de.hswt.bp4553.swa.projekt.model.Registration;
import de.hswt.bp4553.swa.projekt.rmi.RMIClient;

/**
 * Der Controller des MVC patterns.
 * @author bp4553
 *
 */
public class RegistrationController implements RegistrationHandler{
	
	private final RegistrationView view;
	private final RegistrationModel model;
	
	/**
	 * Erstellt einen neuen Controller, und zeigt die zugehörige View an.
	 */
	public RegistrationController() {
		super();
		model = new RegistrationModel();
		view = new RegistrationView(this);
		model.addRegistrationObserver(view);
		view.setVisible(true);
	}
	
	private void groupRegister(File file, RegistrationRemoteClient client){
	       try{
	            model.setRegistrations(client.groupRegister(Files.readAllLines(file.toPath(), Charsets.UTF_8)));
	        }catch(Exception e){
	            view.showError(e);
	        }
	}
	
	private void register(Registration reg, RegistrationRemoteClient client){
	    try {
            model.setRegistrations(client.register(reg));
        }
        catch (Exception e) {
            view.showError(e);
        }
	}
	
	private RegistrationRemoteClient getClient(ConnectionType type) throws MalformedURLException, RemoteException, NotBoundException{
		switch(type){
		case Socket:
			return new SocketClient();
		case rmi:
			return new RMIClient();
		default:
			throw new RuntimeException("Not Implemented");
		}
	}

	/**
	 * 
	 * @param reg
	 * @param type
	 */
	public void addRegistrationPressed(final Registration reg, final ConnectionType type) {
		view.setBusy(true);
	    new Thread(new Runnable() {
            
            @Override
            public void run() {
            	try {
					register(reg, getClient(type));
				} catch (Exception e) {
					view.showError(e);
				}  
            	view.setBusy(false);
            }
        }).start();
	}

    public void addGroupPressed(final File selectedFile, final ConnectionType type) {
        view.setBusy(true);
        new Thread(new Runnable() {
            
            @Override
            public void run() {
            	try {
					groupRegister(selectedFile, getClient(type));
				} catch (Exception e) {
					view.showError(e);
				} 
                view.setBusy(false);
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
