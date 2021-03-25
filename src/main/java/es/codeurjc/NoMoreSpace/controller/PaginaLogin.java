package es.codeurjc.NoMoreSpace.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.NoMoreSpace.repository.UserRepository;
import es.codeurjc.NoMoreSpace.services.BlockDependencies;
import es.codeurjc.NoMoreSpace.services.FileDependencies;
import es.codeurjc.NoMoreSpace.services.PanelDependencies;
import es.codeurjc.NoMoreSpace.services.PoolDependencies;
import es.codeurjc.NoMoreSpace.services.UserDependencies;

@Controller
public class PaginaLogin
{
	@Autowired
	private UserRepository repo;
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
	

	//Pagina de apertura de login - Publica
	@GetMapping("/login")
	public String loginPage(Model model, HttpSession sesion)
	{
		//Si el usuario tiene una sesion abierta
		if(userOP.chkSession(sesion)!=null)
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
		if(userOP.chkSession(sesion)!=null)
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

		switch(userOP.login(user,passwd,sesion))
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
	
}
