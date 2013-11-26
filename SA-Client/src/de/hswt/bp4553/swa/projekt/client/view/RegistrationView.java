package de.hswt.bp4553.swa.projekt.client.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import de.hswt.bp4553.swa.projekt.client.controller.ConnectionType;
import de.hswt.bp4553.swa.projekt.client.controller.RegistrationHandler;
import de.hswt.bp4553.swa.projekt.client.model.Observer;
import de.hswt.bp4553.swa.projekt.model.Fakulty;
import de.hswt.bp4553.swa.projekt.model.Gender;
import de.hswt.bp4553.swa.projekt.model.Registration;

public class RegistrationView extends JFrame
	implements Observer<Collection<Registration>>{

	private static final long serialVersionUID = 1L;
	
	private JTextField nameField;
	private JTextField lastNameField;
	private JTextField birthdayField;
	private JComboBox<String> fakBox;
	private JComboBox<String> genderBox;
	private JComboBox<String> conTypeBox;
	private JList<String> regisList;
	private DefaultListModel<String> regisListModel;
	
	private JButton addButton;
	private JButton groupAddButton;
	
	private BusyDialog busyDialog;
	
	private RegistrationHandler handler;
	
	public RegistrationView(RegistrationHandler handler){
		this.setLayout(new BorderLayout());
		this.getContentPane().add(regisList(), BorderLayout.CENTER);
		this.getContentPane().add(rightPanel(), BorderLayout.EAST);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setSize(700, 400);
		this.handler = handler;
	}
	
	private JPanel rightPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(registPanel(), BorderLayout.NORTH);
		panel.add(new JPanel(), BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel registPanel(){
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout();
		layout.setRows(7);
		panel.setLayout(layout);
		panel.add(new JLabel("Name"));
		panel.add(nameField());
		panel.add(new JLabel("Nachname"));
		panel.add(lastNameField());
		panel.add(new JLabel("Geburtstag"));
		panel.add(birhdayField());
		panel.add(new JLabel("Geschlecht"));
		panel.add(genderBox());
		panel.add(new JLabel("Fakultät"));
		panel.add(fakBox());
		panel.add(new JLabel("Kommunikation"));
		panel.add(conTypeBox());
		panel.add(addBtn());
		panel.add(groupAddBtn());
		return panel;
	}
	
	private JButton addBtn(){
		addButton = new JButton("Hinzufügen");
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					handler.add(parseRegistration());
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(RegistrationView.this, String.format("Fehlerhafte Eingabe: %s", e.getMessage()));
				}
			}
		});
		return addButton;
	}
	
	private JButton groupAddBtn(){
	    groupAddButton = new JButton("Gruppe hinzufügen");
		groupAddButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                if(chooser.showOpenDialog(RegistrationView.this) == JFileChooser.APPROVE_OPTION){
                    handler.groupAdd(chooser.getSelectedFile());
                }
			}
		});
	    return groupAddButton;
	}

	public ConnectionType getSelectedConnectionType(){
		return ConnectionType.values()[conTypeBox.getSelectedIndex()];
	}
	
	private JTextField nameField(){
		nameField = new JTextField();
		return nameField;
	}
	
	private JTextField lastNameField(){
		lastNameField = new JTextField();
		return lastNameField;
	}
	
	private JTextField birhdayField(){
		birthdayField = new JTextField();
		return birthdayField;
	}
	
	private JComboBox<String> fakBox(){
		fakBox = new JComboBox<>();
		for(Fakulty f : Fakulty.values()){
			fakBox.addItem(f.name());
		}
		return fakBox;
	}
	
	private JComboBox<String> conTypeBox(){
		conTypeBox = new JComboBox<>();
		for(ConnectionType t : ConnectionType.values()){
		    conTypeBox.addItem(t.name());
		}
		return conTypeBox;
	}
	
	private JComboBox<String> genderBox(){
		genderBox = new JComboBox<>();
		for(Gender g : Gender.values()){
			genderBox.addItem(g.name());
		}
		return genderBox;
	}
	
	private JList<String> regisList(){
		regisList = new JList<>();
		regisListModel = new DefaultListModel<>();
		regisList.setModel(regisListModel);
		return regisList;
	}
	
	private Registration parseRegistration() throws ParseException{
		DateFormat format = SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN);
		Gender gen = Gender.values()[genderBox.getSelectedIndex()];
		Fakulty fak = Fakulty.values()[fakBox.getSelectedIndex()];
		return new Registration(
				nameField.getText(), 
				lastNameField.getText(), 
				format.parse(birthdayField.getText()), 
				fak, gen);
	}

	public void showError(final Exception e) {
	    SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                JOptionPane.showMessageDialog(RegistrationView.this, e.getMessage());
            }
        });
		
	}
	
	public void setBusy(final boolean busy){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
			    if(busy){
			        if(busyDialog == null){
			            busyDialog = new BusyDialog(RegistrationView.this);
			        }
			    }else{
			        if(busyDialog != null){
			            busyDialog.dispose();
			            busyDialog = null;
			        }
			    }				
			}
		});
	}

	public void showRegistrations(final Collection<Registration> register) {
	       SwingUtilities.invokeLater(new Runnable() {
	            
	            @Override
	            public void run() {
	                regisListModel.clear();
	                for(Registration reg : register){
	                    regisListModel.addElement(reg.toString());
	                }
	            }
	        });
	}

	@Override
	public void changed(Collection<Registration> registrations) {
		showRegistrations(registrations);
	}
}
