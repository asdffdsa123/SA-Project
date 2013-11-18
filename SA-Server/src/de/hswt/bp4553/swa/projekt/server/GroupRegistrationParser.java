package de.hswt.bp4553.swa.projekt.server;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import de.hswt.bp4553.swa.projekt.model.Fakulty;
import de.hswt.bp4553.swa.projekt.model.Gender;
import de.hswt.bp4553.swa.projekt.model.Registration;

public class GroupRegistrationParser {
    
    private static final DateFormat FORMAT = SimpleDateFormat.getDateInstance();

    public static Collection<Registration> parse(List<String> lines) throws ParseException{
        Collection<Registration> result = new ArrayList<>();
        for(String line : lines){
            String[] tokens = line.split("\\s");
            Registration reg = new Registration(
                    tokens[0], 
                    tokens[1], 
                    FORMAT.parse(tokens[2]), 
                    Fakulty.valueOf(tokens[3]), 
                    Gender.valueOf(tokens[4]));
            result.add(reg);
        }
        return result;
    }
    
}
