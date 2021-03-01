package es.codeurjc.NoMoreSpace;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControladorTest
{
	//Pagina del Index
	@GetMapping("/")
	public String indexPage(Model model)
	{
		model.addAttribute("panelCSS",true);
		model.addAttribute("index",true);
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		return "index";
	}
	
	//Pagina de apertura de login
	@GetMapping("/login")
	public String loginPage(Model model)
	{
		model.addAttribute("panelCSS",true);
		model.addAttribute("index",true);
		model.addAttribute("login",true);
		model.addAttribute("expulse",false);
		model.addAttribute("access",false);
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Login");
		return "login";
	}
	
	//Pagina gestion de login
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
		
		model.addAttribute("expulse",user.equals("nobody"));
		model.addAttribute("access",!user.equals("nobody"));
		model.addAttribute("msgError","Usuario incorrecto");
		/*
		//Comprobacion en la base de datos
		Optional<User> usuario = posts.findById(id);
		//Si usuario esta en la base de datos
		if( usuario.isPresent() )
		{
			//Si la passwd es correcta
			if( usuario.getPassword().equals(passwd) )
			{
				//Si el usuario no esta bloqueado
				if(usuario.getLock())
				{
					model.addAttribute("expulse",true);
					sesion.setAttribute("token", user);
				}
				else
				{
					model.addAttribute("msgError","Usuario bloqueado");
					model.addAttribute("access",true);
				}
			}
			else
			{
				model.addAttribute("msgError","Password incorrecta");
				model.addAttribute("expulse",true);
			}
		}
		else
		{
			model.addAttribute("msgError","Usuario incorrecto");
			model.addAttribute("expulse",true);
		}
		*/
		if(user.equals("nobody"))
			return "login";
		else
		{
			model.addAttribute("URL","/home");
			return "hook";
		}
	}
	
	//Pagina de registro
	@GetMapping("/newuser")
	public String newuserPage(Model model)
	{
		model.addAttribute("userName","test");
		model.addAttribute("panelCSS",true);
		model.addAttribute("newuser",true);
		model.addAttribute("index",true);
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Register");
		return "newuser";
	}
	
	//Pagina gestion de registro
	@RequestMapping("/newuser")
	public String newuserPageProcess(Model model, @RequestParam String user, @RequestParam String passwd, @RequestParam String passwd2, @RequestParam String mail)
	{
		model.addAttribute("userName","test");
		model.addAttribute("msg","Usuario creado!");
		model.addAttribute("panelCSS",true);
		model.addAttribute("newuser",true);
		model.addAttribute("index",true);
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Register");
		return "newuser";
	}
	
	//Pagina del home del usuario
	@GetMapping("/home")
	public String homePage(Model model)
	{
		List<FileTest> filesUser=new LinkedList();
		for(int i=0;i<20;i++)
			filesUser.add(new FileTest("file_"+i,i));
		model.addAttribute("userName","test");
		model.addAttribute("filesUser",filesUser);
		model.addAttribute("panelCSS",false);
		model.addAttribute("admin",true); //SI EL USUARIO ES ADMINISTRADOR
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Home");
		model.addAttribute("home",true);
		return "home";
	}
	
	//Pagina de gestion del usuario
	@GetMapping("/myuser")
	public String myuserPage(Model model)
	{
		int size=0;
		LinkedList<FileTest> filesUser=new LinkedList();
		for(int i=0;i<20;i++)
		{
			filesUser.add(new FileTest("file_"+i,i));
			size=size+i*1000;
		}
		model.addAttribute("userName","test");
		model.addAttribute("filesUser",filesUser);
		
		model.addAttribute("userSizePoolMax","10GB");
		model.addAttribute("userSizePool",size/1024+"MB");
		model.addAttribute("userSizePoolFiles",filesUser.size());
		model.addAttribute("userSizePoolPercent",(size/1024.0)/10240.0);
		model.addAttribute("userMail","test@urjc.es");
		
		model.addAttribute("panelCSS",false);
		model.addAttribute("myuser",true);
		model.addAttribute("admin",true); //SI EL USUARIO ES ADMINISTRADOR
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","My User");
		return "myuser";
	}
	
	//Pagina de administracion
	@GetMapping("/adm")
	public String admPage(Model model)
	{
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
