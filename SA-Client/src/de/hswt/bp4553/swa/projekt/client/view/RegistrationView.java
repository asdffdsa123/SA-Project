package de.hswt.bp4553.swa.projekt.client.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

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

import de.hswt.bp4553.swa.projekt.client.logic.ConnectionType;
import de.hswt.bp4553.swa.projekt.client.logic.RegistrationLogic;
import de.hswt.bp4553.swa.projekt.model.Fakulty;
import de.hswt.bp4553.swa.projekt.model.Gender;
import de.hswt.bp4553.swa.projekt.model.Registration;

public class RegistrationView extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private static final DateFormat FORMAT = SimpleDateFormat.getDateInstance();
	
	private JTextField nameField;
	private JTextField lastNameField;
	private JTextField birthdayField;
	private JComboBox<String> fakBox;
	private JComboBox<String> genderBox;
	private JComboBox<String> conTypeBox;
	private JList<String> regisList;
	private DefaultListModel<String> regisListModel;
	
	private RegistrationLogic logic;
	
	private BusyDialog busyDialog;
	
	public RegistrationView(){
		this.setLayout(new BorderLayout());
		this.getContentPane().add(regisList(), BorderLayout.CENTER);
		this.getContentPane().add(rightPanel(), BorderLayout.EAST);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setSize(400, 400);
		logic = new RegistrationLogic(this);
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
		JButton button = new JButton("Hinzufügen");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Registration reg = parseRegistration();
					logic.addRegistrationPressed(reg, ConnectionType.values()[conTypeBox.getSelectedIndex()]);
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(RegistrationView.this, String.format("Fehlerhafte Eingabe: %s", e.getMessage()));
				}
				
			}
		});
		return button;
	}
	
	private JButton groupAddBtn(){
	    JButton button = new JButton("Gruppe hinzufügen");
	       button.addActionListener(new ActionListener() {
	            
	            @Override
	            public void actionPerformed(ActionEvent arg0) {
	                JFileChooser chooser = new JFileChooser();
	                if(chooser.showOpenDialog(RegistrationView.this) == JFileChooser.APPROVE_OPTION){
	                    logic.addGroupPressed(chooser.getSelectedFile(), ConnectionType.values()[conTypeBox.getSelectedIndex()]);
	                }
	            }
	        });
	        return button;
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
		Gender gen = Gender.values()[genderBox.getSelectedIndex()];
		Fakulty fak = Fakulty.values()[fakBox.getSelectedIndex()];
		return new Registration(
				nameField.getText(), 
				lastNameField.getText(), 
				FORMAT.parse(birthdayField.getText()), 
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
	
	public void setBusy(boolean busy){
	    if(busy){
	        if(busyDialog == null){
	            busyDialog = new BusyDialog(this);
	        }
	    }else{
	        if(busyDialog != null){
	            busyDialog.dispose();
	            busyDialog = null;
	        }
	    }
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
}
