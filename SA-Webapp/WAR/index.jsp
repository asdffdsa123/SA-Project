
<html>
	<head>
		<%@ page import="de.hswt.bp4553.swa.projekt.model.*" %>
		<%@ page import="de.hswt.bp4553.swa.projekt.client.socket.*" %>
		<%@ page import="java.util.*" %>
		<%@ page import="java.text.*" %>
		<meta charset="utf-8">
		<title>Wettkampf Registrierung</title>
	</head>
	<body>
		<h1> Wettkampf Registrierung </h1>
		<div>
				<h2> Einzelanmeldung </h2>
				<form method="post"> 
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
				<br/>
				<h2> Gruppenanmeldung </h2>
				<form method="post" action= "register" enctype="multipart/form-data">  
					<input type = "file" name = "file"></input> <br/>
					<input type = "hidden" name = "inputtype" value = "groupregistration"/>
					<button type = "submit">Hochladen</button>
				</form>
		</div>
			<%!
				String tdWrap(String str){
					return String.format("<td>%s</td>", str);
				}
			%>
			<br/>
			<h2> Anmeldungen </h2>
			<table border='0'>
				<thead>
					<tr>
						<th> Vorname </th>
						<th> Nachname </th>
						<th> Geburtstag </th>
						<th> Fakultaet </th>
						<th> Geschlecht </th>
					</tr>
				</thead>
			<%
				SocketClient client = new SocketClient();
				String inputType = request.getParameter("inputtype");
				if("registration".equals(inputType)){
					String firstname = request.getParameter("name");
					String lastname = request.getParameter("lastname");
					Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthday"));
					Fakulty fakulty = Fakulty.valueOf(request.getParameter("fakulty"));
					Gender gender = Gender.valueOf(request.getParameter("gender"));
					Registration toInsert = new Registration(firstname, lastname, birthday, fakulty, gender);
					client.register(toInsert);
				}
				Collection<Registration> regs = client.getAll();
				for(Registration reg : regs){
					out.write("<tr>");
					out.write(tdWrap(reg.getFirstname()));
					out.write(tdWrap(reg.getLastname()));
					out.write(tdWrap(SimpleDateFormat.getDateInstance().format(reg.getBirthday())));
					out.write(tdWrap(reg.getFakulty().name()));
					out.write(tdWrap(reg.getGender().name()));
					out.write("</tr>");
				}
			%>
			</table> <br/>
	</body>
	
</html>