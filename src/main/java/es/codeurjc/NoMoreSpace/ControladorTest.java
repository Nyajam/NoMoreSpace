package es.codeurjc.NoMoreSpace;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControladorTest
{
	@Autowired
	private UserRepository repo;
	
	private final int TIEMPO_SESION_MINUTOS=20;
	
	//
	//METODOS COMUNES 
	//
	
	//Comprueba si la sesion es valida, retorna el usuario
	private User chkSession(HttpSession sesion)
	{
		String id=(String) sesion.getAttribute("token");
		if(id==null)
			return null;
		List<User> usuario = repo.findByUsername(id);
		return usuario.get(0);
	}
	
	//Retornar el retorno de esta funcion en caso de sesion caducada/inexsistente
	private String noSession(Model model, HttpSession sesion)
	{
		model.addAttribute("URL","/logout");
		return "hook";
	}
	
	//Login de un usuario, retorna 0 todo bien, 1 bloqueado, 2 passwd incorrecta, 3 usuario inexistente
	private int login(String user, String passwd, HttpSession sesion)
	{
		//Comprobacion en la base de datos
		List<User> usuario = repo.findByUsername(user);
		//Si usuario esta en la base de datos
		if( !usuario.isEmpty() )
		{
			//Si la passwd es correcta
			if( usuario.get(0).getPassword().equals(passwd) )
			{
				//Si el usuario no esta bloqueado
				if(!usuario.get(0).isBloqueado())
				{
					if(chkSession(sesion)!=null)
						sesion.invalidate();
					sesion.setAttribute("token", user);
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
	
	//Indica si un mail es valido o no
	private boolean chkMail(String mail)
	{
		return mail.contains("@")&&mail.substring(mail.indexOf("@")).contains(".");
	}
	
	//
	//PAGINAS - estructuras comunes
	//
	
	private String myuserESTC(Model model, User usuario)
	{
		int size=0;
		LinkedList<FileTest> filesUser=new LinkedList();
		for(int i=0;i<20;i++)
		{
			filesUser.add(new FileTest("file_"+i,i));
			size=size+i*1000;
		}
		model.addAttribute("userName",usuario.getUsername());
		model.addAttribute("filesUser",filesUser);
		
		model.addAttribute("userSizePoolMax","10GB");
		model.addAttribute("userSizePool",size/1024+"MB");
		model.addAttribute("userSizePoolFiles",filesUser.size());
		model.addAttribute("userSizePoolPercent",(size/1024.0)/10240.0);
		model.addAttribute("userMail",usuario.getMail());
		
		model.addAttribute("panelCSS",false);
		model.addAttribute("myuser",true);
		model.addAttribute("admin",usuario.isAdmin());
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","My User");
		return "myuser";
	}
	
	//
	//PAGINAS - direcciones
	//
	
	//Pagina del Index - Publica
	@GetMapping("/")
	public String indexPage(Model model)
	{
		model.addAttribute("panelCSS",true);
		model.addAttribute("index",true);
		model.addAttribute("Index",true);
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		return "index";
	}
	
	//Pagina de apertura de login - Publica
	@GetMapping("/login")
	public String loginPage(Model model, HttpSession sesion)
	{
		//Si el usuario tiene una sesion abierta
		if(chkSession(sesion)!=null)
		{
			model.addAttribute("URL","/home");
			return "hook";
		}
		model.addAttribute("panelCSS",true);
		model.addAttribute("index",true);
		model.addAttribute("login",true);
		model.addAttribute("access",false);
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Login");
		return "login";
	}
	
	//Pagina del logout - Publica
	@GetMapping("/logout")
	public String logoutPage(Model model, HttpSession sesion)
	{
		model.addAttribute("panelCSS",true);
		model.addAttribute("index",true);
		model.addAttribute("login",true);
		model.addAttribute("access",false);
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Login");
		//Si existe una sesion
		if(chkSession(sesion)!=null)
		{
			sesion.invalidate();
			model.addAttribute("msgError","Sesion cerrada");
			model.addAttribute("expulse",true);
		}
		else
		{
			model.addAttribute("msgError","Sesion caducada");
			model.addAttribute("expulse",true);
		}
		return "login";
	}
	
	//Pagina gestion de login - Publica
	@RequestMapping("/login")
	public String loginPageProcess(Model model, HttpSession sesion, @RequestParam String user, @RequestParam String passwd)
	{
		//Elementos basicos de la pagina
		model.addAttribute("panelCSS",true); //Si emplea la plantilla general o un panel de informacion
		model.addAttribute("login",true);
		model.addAttribute("index",true);
		model.addAttribute("titleApp","NoMoreSpacePlease!"); //Titulo de la web
		model.addAttribute("titlePage","Login"); //Titulo de la pagina
		model.addAttribute("expulse",false);
		model.addAttribute("access",false);

		switch(login(user,passwd,sesion))
		{
			case 0:
				model.addAttribute("expulse",true);
				model.addAttribute("URL","/home");
				return "hook";
			case 1:
				model.addAttribute("msgError","Usuario bloqueado");
				model.addAttribute("expulse",true);
				break;
			case 2:
				model.addAttribute("msgError","Password incorrecta");
				model.addAttribute("expulse",true);
			case 3:
				model.addAttribute("msgError","Usuario incorrecto");
				model.addAttribute("expulse",true);
				break;
			default:
				model.addAttribute("msgError","Error en login");
				model.addAttribute("expulse",true);
		}
		return "login";
	}
	
	//Pagina de registro - Publica
	@GetMapping("/newuser")
	public String newuserPage(Model model, HttpSession sesion)
	{
		model.addAttribute("userName","test");
		model.addAttribute("panelCSS",true);
		model.addAttribute("newuser",true);
		model.addAttribute("index",true);
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Register");
		return "newuser";
	}
	
	//Pagina gestion de registro - Publica
	@RequestMapping("/newuser")
	public String newuserPageProcess(Model model, @RequestParam String user, @RequestParam String passwd, @RequestParam String passwd2, @RequestParam String mail, HttpSession sesion)
	{
		model.addAttribute("userName","test");
		model.addAttribute("panelCSS",true);
		model.addAttribute("newuser",true);
		model.addAttribute("index",true);
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Register");
		
		//Verificar contraseñas iguales
		if(!passwd.equals(passwd2))
		{
			model.addAttribute("msg","Error on password");
			return "newuser";
		}
		//Verificar mail valido
		if(!chkMail(mail))
		{
			model.addAttribute("msg","Error on mail");
			return "newuser";
		}
		//Verificar que el usuario no exista
		if(repo.findByUsername(user).size()!=0)
		{
			model.addAttribute("msg","Username is use");
			return "newuser";
		}
		repo.save(new User(mail,passwd,user,false,false));
		if(login(user,passwd,sesion)!=0)
			model.addAttribute("msg","Usuario creado con exito, pero ha habido un problema de login");
		else
			model.addAttribute("msg","Usuario creado!");
		return "newuser";
	}
	
	//Pagina del home del usuario - Privada
	@GetMapping("/home")
	public String homePage(Model model, HttpSession sesion)
	{
		User usuario;
		usuario=chkSession(sesion);
		if(usuario==null)
		{
			return noSession(model, sesion);
		}
		List<FileTest> filesUser=new LinkedList();
		for(int i=0;i<20;i++)
			filesUser.add(new FileTest("file_"+i,i));
		model.addAttribute("userName",usuario.getUsername());
		model.addAttribute("filesUser",filesUser);
		model.addAttribute("panelCSS",false);
		model.addAttribute("admin",usuario.isAdmin()); //SI EL USUARIO ES ADMINISTRADOR
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Home");
		model.addAttribute("home",true);
		return "home";
	}
	
	//Pagina de gestion del usuario - Privada
	@GetMapping("/myuser")
	public String myuserPage(Model model, HttpSession sesion)
	{
		User usuario;
		usuario=chkSession(sesion);
		if(usuario==null)
		{
			return noSession(model, sesion);
		}
		return myuserESTC(model, usuario);
	}
	
	//Pagina de gestion del usuario, cambio de mail - Privada
	@RequestMapping("/myuser/mail")
	public String myuserPageProcessMail(Model model, HttpSession sesion, @RequestParam String newMail)
	{
		User usuario;
		usuario=chkSession(sesion);
		if(usuario==null)
		{
			return noSession(model, sesion);
		}
		//Cambio de mail
		if(newMail!=null)
		{
			if(chkMail(newMail))
			{
				usuario.setMail(newMail);
				repo.save(usuario);
			}
			else
				model.addAttribute("msgMail","The email is no valid");
		}
		return myuserESTC(model, usuario);
	}

	//Pagina de gestion del usuario, cambio de password - Privada
	@RequestMapping("/myuser/password")
	public String myuserPageProcessPassword(Model model, HttpSession sesion, @RequestParam String passwd, @RequestParam String passwd2)
	{
		User usuario;
		usuario=chkSession(sesion);
		if(usuario==null)
		{
			return noSession(model, sesion);
		}
		//Cambio de contraseña
		if(passwd!=null)
		{
			if(passwd.equals(passwd2))
			{
				usuario.setPassword(passwd);
				repo.save(usuario);
			}
			else
				model.addAttribute("msgPassword","The password are not same!");
		}
		return myuserESTC(model, usuario);
	}
	
	//Pagina de administracion - Privada
	@GetMapping("/adm")
	public String admPage(Model model, HttpSession sesion)
	{
		if(chkSession(sesion)==null)
		{
			return noSession(model, sesion);
		}
		List<FileTest> filesUser=new LinkedList();
		for(int i=0;i<20;i++)
			filesUser.add(new FileTest("file_"+i,i));
		model.addAttribute("userName","test");
		model.addAttribute("filesUser",filesUser);
		model.addAttribute("panelCSS",false);
		model.addAttribute("adm",true);
		model.addAttribute("admin",true); //SI EL USUARIO ES ADMINISTRADOR
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Administration");
		return "administration";
	}
}
