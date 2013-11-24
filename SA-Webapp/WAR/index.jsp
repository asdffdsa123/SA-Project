<html>
	<head>
		<%@ page import="de.hswt.bp4553.swa.projekt.model.*" %>
		<%@ page import="de.hswt.bp4553.swa.projekt.socket.*" %>
		<%@ page import="java.util.*" %>
		<%@ page import="java.text.*" %>
		<meta charset="utf-8">
		<title>Wettkampf Registrierung</title>
	</head>
	<body>
		<div style = 'float:left;'> 
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
		</div>
		<div style = 'float:left;'>
			<form method="post" enctype="multipart/form-data">  
				<input type = "file" name = "file"> Datei </input> <br/>
				<input type = "hidden" name = "inputtype" value = "groupregistration"/>
				<button type = "submit">Gruppen Datei hochladen</button>
			</form>
		</div> <br/>
			<%
				String inputType = request.getParameter("inputtype");
				if("registration".equals(inputType)){
					String firstname = request.getParameter("name");
					String lastname = request.getParameter("lastname");
					Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthday"));
					Fakulty fakulty = Fakulty.valueOf(request.getParameter("fakulty"));
					Gender gender = Gender.valueOf(request.getParameter("gender"));
					Registration toInsert = new Registration(firstname, lastname, birthday, fakulty, gender);
					SocketClient client = new SocketClient(ServerConfig.getInstance());
					Collection<Registration> regs = client.register(toInsert);
					out.write("<table border='0'>");
					for(Registration reg : regs){
						out.write("<tr>");
						out.write("<td>");
						out.write(reg.toString());
						out.write("</td>");
						out.write("</tr>");
					}
					out.write("</table>");
				}else if("groupregistration".equals(inputType)){
					
				}
			%>
			<br/>
	</body>
	
</html>