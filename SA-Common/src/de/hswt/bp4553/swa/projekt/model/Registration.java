package de.hswt.bp4553.swa.projekt.model;

import java.io.Serializable;
import java.util.Date;
import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.*;

public final class Registration 
	implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	private String vorname;
	private String nachname;
	private Date geburtstag;
	private Fakulty fakulty;
	private Gender gender;

	public Registration(String vorname, String nachname, Date geburtstag,
			Fakulty fakulty, Gender gender) {
		super();
		setVorname(vorname);
		setNachname(nachname);
		setGeburtstag(geburtstag);
		setFakulty(fakulty);
		setGender(gender);
	}

	public String getVorname() {
		return vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public Date getGeburtstag() {
		return geburtstag;
	}

	public Fakulty getFakulty() {
		return fakulty;
	}

	public Gender getGender() {
		return gender;
	}

	private void setVorname(String vorname) {
		checkArgument(!isNullOrEmpty(vorname));
		this.vorname = vorname;
	}

	private void setNachname(String nachname) {
		checkArgument(!isNullOrEmpty(nachname));
		this.nachname = nachname;
	}

	private void setGeburtstag(Date geburtstag) {
		checkNotNull(geburtstag);
		this.geburtstag = geburtstag;
	}

	private void setFakulty(Fakulty fakulty) {
		checkNotNull(fakulty);
		this.fakulty = fakulty;
	}

	private void setGender(Gender gender) {
		checkNotNull(gender);
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Registration [vorname=" + vorname + ", nachname=" + nachname
				+ ", geburtstag=" + geburtstag + ", fakultaet=" + fakulty
				+ ", gender=" + gender + "]";
	}

}
