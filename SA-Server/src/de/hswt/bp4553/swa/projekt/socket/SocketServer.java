package de.hswt.bp4553.swa.projekt.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Throwables;

import de.hswt.bp4553.swa.projekt.model.Registration;
import de.hswt.bp4553.swa.projekt.server.GroupRegistrationParser;
import de.hswt.bp4553.swa.projekt.server.ServerConfig;
import de.hswt.bp4553.swa.projekt.server.persistence.RegistrationPersistence;
import de.hswt.bp4553.swa.projekt.server.persistence.RegistrationPersistenceFactory;

public class SocketServer extends Thread{

	private static final Logger log = Logger.getLogger(SocketServer.class.getName());

	public SocketServer(){
		
	}
	
	@Override
	public void run(){
		log.log(Level.INFO, "Socket Server started");
		try(ServerSocket ss = new ServerSocket(ServerConfig.getInstance().getSocketPort())){
			while(true){
				try {
					final Socket socket = ss.accept();
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							try {
								handleClient(socket);
								log.info("Client sucessfully handled");
							} catch (Exception e) {
								log.log(Level.SEVERE, Throwables.getStackTraceAsString(e));
							}
						}
					}).start();
				} catch (IOException e) {
					log.log(Level.SEVERE, Throwables.getStackTraceAsString(e));
				}

			}
		} catch (IOException e1) {
			throw Throwables.propagate(e1);
		}
	}
	
	@SuppressWarnings("unchecked")
    private void handleClient(Socket socket) throws IOException, ParseException{
	    RegistrationPersistence registrationPersistence = RegistrationPersistenceFactory.getRegistrationPersistence();
		try(ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())){
			String type = (String) in.readObject();
			if(type.equals("register")){
				Registration reg = (Registration) in.readObject();
				registrationPersistence.insert(reg);
				writeEnd(registrationPersistence, out);
			}else if(type.equals("registerGroup")){
				Collection<Registration> regs = GroupRegistrationParser.parse((List<String>) in.readObject());
				for(Registration r : regs){
				    registrationPersistence.insert(r);
				}
				writeEnd(registrationPersistence, out);
			}else if(type.equals("getAll")){
				writeEnd(registrationPersistence, out);
			}else{
				out.writeObject("invalid request");
			}
		}catch(ClassNotFoundException e){
			throw Throwables.propagate(e);
		}
	}
	
	private void writeEnd(RegistrationPersistence registrationPersistence, ObjectOutputStream out) throws IOException{
		out.writeObject(registrationPersistence.getAll());
		out.writeObject("Ok");
	}

}
