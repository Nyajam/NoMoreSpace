package es.codeurjc.NoMoreSpace;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class Controlador
{
	@Autowired
	private UserRepository repo;
	
	private final int TIEMPO_SESION_MINUTOS=20;
	
	
	//
	//METODOS COMUNES 
	//
	
	//Comprueba si la sesion es valida, retorna el usuario
	private Optional <User> chkSession(HttpSession sesion)
	{
		if(sesion.getAttribute("token")==null)
			return null;
		if(!(sesion.getAttribute("token") instanceof Long))
			return null;
		Optional <User> usuario = repo.findById( ((Long)sesion.getAttribute("token")).longValue() );
		if(usuario.isPresent())
			return usuario;
		else
			return null;
	}
	
	//Retornar el retorno de esta funcion en caso de sesion caducada/inexsistente
	private String noSession(Model model, HttpSession sesion)
	{
		model.addAttribute("URL","/logout");
		return "hook";
	}
	
	//Retornar el retorno de esta funcion en caso de usuario no autorizado
	private String noAuth(Model model, HttpSession sesion)
	{
		model.addAttribute("URL","/home");
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
	
	//Indica si un mail es valido o no
	private boolean chkMail(String mail)
	{
		return mail.contains("@")&&mail.substring(mail.indexOf("@")).contains(".");
	}
	
	//Retorna el panel del path especificado de un usuario <-- Cambiar a Panel
	private Panel getPanelByPath(User usuario, String path)
	{
		Panel workdir;
		workdir=usuario.getPanel().get(0);
		String[] dir;
		dir=path.split("/");
		for(int i=0;i<dir.length;i++)
		{
			for(int j=0;j<workdir.getPanel().size();j++)
			{
				//workdir.getName().equals(dir[i]);
				//Si el workdir es el panel
				if(workdir.getPanel().get(j).getName().equals(dir[i]))
				{
					//El encontrado pasa a ser el nuevo workdir y sale del bucle de recorrido del panel
					workdir=workdir.getPanel().get(j);
					break;
				}
			}
		}
		if(workdir.getName().equals(dir[dir.length-1]))
			return workdir;
		else
			return null;
	}
	
	//Retorna el peso en bytes de los files del usuario
	private int sizeOfPool(User usuario)
	{
		int size=0;
		//for(int i=0;i<usuario.getPool().getFile().size();i++)
		//{
		//	size=usuario.getPool().getFile().get(i);
		//}
		if(usuario.getPool()==null)
			return 0;
		size=usuario.getPool().getFile().size();
		return size;
	}
	
	//
	//PAGINAS - estructuras comunes
	//
	
	//Parte comun de gestion de usuario
	private String myuserESTC(Model model, Optional <User> usuario)
	{
		model.addAttribute("userName",usuario.get().getUsername());
		model.addAttribute("userSizePoolMax","10GB");
		model.addAttribute("userSizePool",sizeOfPool(usuario.get())+"B");
		if(usuario.get().getPool()!=null)
		{
			if(usuario.get().getPool().getFile()!=null)
				model.addAttribute("userSizePoolFiles",usuario.get().getPool().getFile().size());
			else
				model.addAttribute("userSizePoolFiles",0);
		}
		else
			model.addAttribute("userSizePoolFiles",0);
		model.addAttribute("userSizePoolPercent",sizeOfPool(usuario.get())/10240.0);
		model.addAttribute("userMail",usuario.get().getMail());
		
		model.addAttribute("panelCSS",false);
		model.addAttribute("myuser",true);
		model.addAttribute("admin",usuario.get().isAdmin());
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","My User");
		return "myuser";
	}
	
	//Parte comun del home del usuario
	private String homeESTC(Model model, Optional <User> usuario)
	{
		model.addAttribute("userName",usuario.get().getUsername());
		model.addAttribute("panelCSS",false);
		model.addAttribute("admin",usuario.get().isAdmin()); //SI EL USUARIO ES ADMINISTRADOR
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Home");
		model.addAttribute("home",true);
		return "home";
	}
	
	//Parte comun del adm
	private String admESTC(Model model, Optional <User> usuario)
	{
		model.addAttribute("userName","test");
		model.addAttribute("panelCSS",false);
		model.addAttribute("adm",true);
		model.addAttribute("admin",usuario.get().isAdmin()); //SI EL USUARIO ES ADMINISTRADOR
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Administration");
		List<User> userList=repo.findAll();
		model.addAttribute("userList",userList);
		model.addAttribute("systemUsers",repo.findAll().size());
		model.addAttribute("systemUsersAdm",repo.findByAdmin(true).size());
		return "administration";
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
		User nuevo=new User(user,mail,passwd);
		repo.save(nuevo);
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
		Optional <User> usuario;
		usuario=chkSession(sesion);
		if(usuario==null)
		{
			return noSession(model, sesion);
		}
		model.addAttribute("actualPanel",usuario.get().getPanel().get(0).getName());
		model.addAttribute("panels",usuario.get().getPanel());
		return homeESTC(model,usuario);
	}
	
	//Pagina del home del usuario - Privada
	@RequestMapping("/home")
	public String homePageProcess(Model model, HttpSession sesion, @RequestParam String actualPanel, @RequestParam String goToPanel)
	{
		Optional <User> usuario;
		usuario=chkSession(sesion);
		if(usuario==null)
		{
			return noSession(model, sesion);
		}
		//model.addAttribute("actualPanel",actualPanel+"/"+goToPanel);
		//model.addAttribute("panels", getPanelByPath(usuario.get(),actualPanel+"/"+goToPanel).getPanel() );
		
		model.addAttribute("actualPanel",goToPanel);
		model.addAttribute("panels",usuario.get().getPanel());
		
		return homeESTC(model,usuario);
	}
	
	//Pagina del home del usuario, creacion de panel - Privada
	@RequestMapping("/home/newpanel")
	public String homePageNewPanel(Model model, HttpSession sesion, @RequestParam String nameNewPanel, @RequestParam String actualPanel)
	{
		Optional <User> usuario;
		usuario=chkSession(sesion);
		if(usuario==null)
		{
			return noSession(model, sesion);
		}
		model.addAttribute("actualPanel",actualPanel);
		Panel workdir, nuevo;
		workdir=getPanelByPath(usuario.get(),actualPanel);
		for(int i=0;i<workdir.getPanel().size();i++)
			if(workdir.getPanel().get(i).getName().equals(nameNewPanel))
			{
				model.addAttribute("panels",usuario.get().getPanel());
				return homeESTC(model,usuario);
			}
		if(workdir!=null)
		{
			nuevo=new Panel(usuario.get(),nameNewPanel);
			workdir.getPanel().add(nuevo);
			//usuario.get().addPanel(workdir);
			repo.save(usuario.get());
		}
		model.addAttribute("panels",usuario.get().getPanel());
		return homeESTC(model,usuario);
	}
	
	//Pagina del home del usuario, borrar panel - Privada
	@RequestMapping("/home/deletepanel")
	public String homePageSharePanel(Model model, HttpSession sesion, @RequestParam String actualPanel)
	{
		Optional <User> usuario;
		usuario=chkSession(sesion);
		if(usuario==null)
		{
			return noSession(model, sesion);
		}
		for(int i=0;i<usuario.get().getPanel().size();i++)
			if(usuario.get().getPanel().get(i).getName().equals(actualPanel))
			{
				//usuario.get().getPanel().get(i).setUser(null);
				//usuario.get().getPanel().remove(i);
				usuario.get().removePanel(usuario.get().getPanel().get(i));
				repo.save(usuario.get());
				break;
			}
		
		model.addAttribute("panels",usuario.get().getPanel());
		model.addAttribute("actualPanel",usuario.get().getPanel().get(0).getName());
		return homeESTC(model,usuario);
	}
	
	//Pagina del home del usuario, gestion de ficheros - Privada
	@PostMapping("/home/upfiles")
	public String homePageMyFiles(Model model, HttpSession sesion, @RequestParam("filesUpload") MultipartFile filesUpload, @RequestParam String actualPanel)
	{
		Optional <User> usuario;
		usuario=chkSession(sesion);
		if(usuario==null)
		{
			return noSession(model, sesion);
		}
		for(int i=0;i<usuario.get().getPanel().size();i++)
			if(usuario.get().getPanel().get(i).getName().equals(actualPanel))
			{
				if(usuario.get().getPool()==null)
				{
					Pool pol = new Pool();
					pol.setUser(usuario.get());
					usuario.get().setPool(pol);
				}
				File file = new File();
				file.setFilename(filesUpload.getName());
				//file.setFilename("fichero");
				usuario.get().getPanel().get(i).addFile(file);
				if(usuario.get().getPool().getFile()==null)
					usuario.get().getPool().setFile(new ArrayList());
				usuario.get().getPool().getFile().add(file);
				repo.save(usuario.get());
				model.addAttribute("filesUser",usuario.get().getPanel().get(i).getFile());
				break;
			}
		model.addAttribute("panels",usuario.get().getPanel());
		model.addAttribute("actualPanel",usuario.get().getPanel().get(0).getName());
		return homeESTC(model,usuario);
	}
	
	//Pagina del home del usuario, descarga de ficheros - Privada
	@RequestMapping("/home/downfiles")
	public String homePageDownFiles(Model model, HttpSession sesion)
	{
		Optional <User> usuario;
		usuario=chkSession(sesion);
		if(usuario==null)
		{
			return noSession(model, sesion);
		}
		model.addAttribute("actualPanel",usuario.get().getPanel().get(0).getName());
		model.addAttribute("panels",usuario.get().getPanel());
		return homeESTC(model,usuario);
	}
	
	//Pagina de gestion del usuario - Privada
	@GetMapping("/myuser")
	public String myuserPage(Model model, HttpSession sesion)
	{
		Optional <User> usuario;
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
		Optional <User> usuario;
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
				usuario.get().setMail(newMail);
				repo.save(usuario.get());
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
		Optional <User> usuario;
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
				usuario.get().setPassword(passwd);
				repo.save(usuario.get());
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
		Optional <User> usuario;
		usuario=chkSession(sesion);
		if(usuario==null)
		{
			return noSession(model, sesion);
		}
		if(!usuario.get().isAdmin())
			return noAuth(model,sesion);
		return admESTC(model,usuario);
	}
	
	//Pagina de administracion, ajustes de usuarios - Privada
	@RequestMapping("/adm")
	public String admPageProcessUsers(Model model, HttpSession sesion, @RequestParam String passwd, @RequestParam String passwd2, @RequestParam String lock, @RequestParam String adm, @RequestParam String mail, @RequestParam String userName)
	{
		Optional <User> usuario;
		usuario=chkSession(sesion);
		if(usuario==null)
		{
			return noSession(model, sesion);
		}
		if(!usuario.get().isAdmin())
			return noAuth(model,sesion);
		if(repo.findByUsername(userName).isEmpty())
			model.addAttribute("msg","Error, usuario no encontrado");
		else
		{
			User victima;
			victima=repo.findByUsername(userName).get(0);
			if(!mail.equals(""))
				victima.setMail(mail);
			if(passwd.equals(passwd2)&&!passwd.equals(""))
				victima.setPassword(passwd);
			victima.setBloqueado(lock.equals("Y"));
			victima.setAdmin(adm.equals("Y"));
			repo.save(victima);
		}
		return admESTC(model,usuario);
	}
}
