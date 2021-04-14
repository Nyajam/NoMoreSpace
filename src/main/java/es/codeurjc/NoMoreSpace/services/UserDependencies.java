package es.codeurjc.NoMoreSpace.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import es.codeurjc.NoMoreSpace.model.User;
import es.codeurjc.NoMoreSpace.repository.UserRepository;

@Component
public class UserDependencies
{
	@Autowired
	private UserRepository repo;
	
	private final int TIEMPO_SESION_MINUTOS=20;

	//Comprueba si la sesion es valida, retorna el usuario
	public User chkSession(HttpServletRequest sesion)
	{
		List<User> usuario = repo.findByUsername(sesion.getUserPrincipal().getName());
		if(usuario.size()==1)
			return usuario.get(0);
		else
			return null;
	}
	
	//Retornar el retorno de esta funcion en caso de sesion caducada/inexsistente
	public String noSession(Model model, HttpSession sesion)
	{
		model.addAttribute("URL","/logout");
		return "hook";
	}
	
	//Retornar el retorno de esta funcion en caso de usuario no autorizado
	public String noAuth(Model model, HttpSession sesion)
	{
		model.addAttribute("URL","/home");
		return "hook";
	}
	
	/*
	//Login de un usuario, retorna 0 todo bien, 1 bloqueado, 2 passwd incorrecta, 3 usuario inexistente
	public int login(String user, String passwd, HttpSession sesion)
	{
		//Comprobacion en la base de datos
		List<User> usuario = repo.findByUsername(user);
		//Si usuario esta en la base de datos
		if( !usuario.isEmpty() )
		{
			//Si la passwd es correcta
			if( usuario.get(0).comparePasswd(passwd) ) //.getPassword().equals(passwd) )
			{
				//Si el usuario no esta bloqueado
				if(!usuario.get(0).isBloqueado())
				{
					if(sesion==null)
						return 0;
					if(chkSession(sesion)!=null)
						sesion.invalidate();
					sesion.setAttribute("token", usuario.get(0).getId());
					sesion.setMaxInactiveInterval(60*TIEMPO_SESION_MINUTOS);
					return 0;
				}
				else
					return 1;
			}
			else
				return 2;
		}
		else
			return 3;
	}
	*/
	
	//Indica si un mail es valido o no
	public boolean chkMail(String mail)
	{
		return mail.contains("@")&&mail.substring(mail.indexOf("@")).contains(".");
	}
	
	//Crea un nuevo usuario en la db
	public boolean createUser(String name, String mail, String passwd)
	{
		try
		{
			User nuevo=new User(name,mail,passwd);
			repo.save(nuevo);
			return true;
		}
		catch(Exception e) //IllegalArgumentException
		{
			return false;
		}
	}
	
	//Verifica que un nombre de usuario sea unico, true -> cierto, false -> no es unico
	public boolean userNameUQ(String name)
	{
		return repo.findByUsername(name).isEmpty();
	}
}
