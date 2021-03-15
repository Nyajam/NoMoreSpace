package es.codeurjc.NoMoreSpace;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaginaNewuser
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
		if(repo.findByUsername(user).size()!=0)
		{
			model.addAttribute("msg","Username is use");
			return "newuser";
		}
		User nuevo=new User(user,mail,passwd);
		repo.save(nuevo);
		if(userOP.login(user,passwd,sesion)!=0)
			model.addAttribute("msg","Usuario creado con exito, pero ha habido un problema de login");
		else
			model.addAttribute("msg","Usuario creado!");
		return "newuser";
	}
	
}
