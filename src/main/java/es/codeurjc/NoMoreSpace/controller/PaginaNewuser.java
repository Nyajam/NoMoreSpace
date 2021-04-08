package es.codeurjc.NoMoreSpace.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.NoMoreSpace.model.User;
import es.codeurjc.NoMoreSpace.repository.UserRepository;
import es.codeurjc.NoMoreSpace.services.BlockDependencies;
import es.codeurjc.NoMoreSpace.services.FileDependencies;
import es.codeurjc.NoMoreSpace.services.PanelDependencies;
import es.codeurjc.NoMoreSpace.services.PoolDependencies;
import es.codeurjc.NoMoreSpace.services.UserDependencies;

@Controller
public class PaginaNewuser
{
	
	@Autowired
	private UserDependencies userOP;
	@Autowired
	private PanelDependencies panelOP;
	@Autowired
	private PoolDependencies poolOP;
	@Autowired
	private FileDependencies fileOP;
	@Autowired
	private BlockDependencies blockOP;

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
		if(!userOP.chkMail(mail))
		{
			model.addAttribute("msg","Error on mail");
			return "newuser";
		}
		//Verificar que el usuario no exista
		if(!userOP.userNameUQ(user))
		{
			model.addAttribute("msg","Username is use");
			return "newuser";
		}
		if(userOP.createUser(user, mail, passwd))
		{
			if(userOP.login(user,passwd,sesion)!=0)
				model.addAttribute("msg","Usuario creado con exito, pero ha habido un problema de login");
			else
				model.addAttribute("msg","Usuario creado!");
		}
		else
			model.addAttribute("msg","Error al crear el usuario");
		return "newuser";
	}
	
}
