<html>
	<head>
		<%@ page import="de.hswt.bp4553.swa.projekt.model.*" %>
		<meta charset="utf-8">
		<title>Wettkampf Registrierung</title>
	</head>
	<body>
		<form>
			<input type = "text" name = "name" placeholder = "Vorname"/> <br/>
			<input type = "text" name = "lastname" placeholder = "Nachname"/> <br/>
			<input type = "date" name = "birthday" placeholder = "Geburtstag"/> <br/>
			<select name="fakulty" size="3">
			<%
				Fakulty[] fs = Fakulty.values();
				for(int i = 0; i < fs.length; i++){
					out.write(String.format("<option>%s</option>", fs[i].name()));
				}
			%>
		    </select> <br/>
		    <input type = "radio" name = "gender" value = "Mann"> Mann </input>
		    <input type = "radio" name = "gender" value = "Frau"> Frau </input> <br/>
		    <input type = "hidden" name = "inputtype" value = "registration"/>
			<button type="submit">Anmelden</button>
		</form>
	</body>
	
</html>