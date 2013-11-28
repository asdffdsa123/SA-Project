package de.hswt.bp4553.swa.projekt.server;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hswt.bp4553.swa.projekt.model.Fakulty;
import de.hswt.bp4553.swa.projekt.model.Gender;
import de.hswt.bp4553.swa.projekt.model.Registration;

public class GroupRegistrationParser {

	/**
	 * Parst eine CSV Datei im Format "Vorname, Nachname, Geburtstag (zb 22.11.1980), Fakultaet (zb BB), Geschlecht (zb Mann)"
	 * @param lines Die Zeilen der CSV Datei
	 * @return
	 * @throws ParseException Wenn die Datei nicht im richtigen Format ist
	 */
    public static Collection<Registration> parse(List<String> lines) throws ParseException{
    	DateFormat format = SimpleDateFormat.getDateInstance();
        Collection<Registration> result = new ArrayList<>();
        for(String line : lines){
            String[] tokens = line.split("\\s+");
            Registration reg = new Registration(
                    tokens[0], 
                    tokens[1], 
                    format.parse(tokens[2]), 
                    Fakulty.valueOf(tokens[3]), 
                    Gender.valueOf(tokens[4]));
            result.add(reg);
        }
        return result;
    }
    
}
