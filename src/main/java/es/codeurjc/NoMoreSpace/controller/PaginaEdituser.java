package es.codeurjc.NoMoreSpace.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
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
public class PaginaEdituser
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
	
	//Parte comun de gestion de usuario
	private String myuserESTC(Model model, User usuario)
	{
		model.addAttribute("userName",usuario.getUsername());
		model.addAttribute("userSizePoolMax","10GB");
		model.addAttribute("userSizePool",poolOP.sizeOfPool(usuario)+"B");
		if(usuario.getPool()!=null)
		{
			if(usuario.getPool().getFile()!=null)
				model.addAttribute("userSizePoolFiles",usuario.getPool().getFile().size());
			else
				model.addAttribute("userSizePoolFiles",0);
		}
		else
			model.addAttribute("userSizePoolFiles",0);
		model.addAttribute("userSizePoolPercent",poolOP.sizeOfPool(usuario)/10240.0);
		model.addAttribute("userMail",usuario.getMail());
		
		model.addAttribute("panelCSS",false);
		model.addAttribute("myuser",true);
		model.addAttribute("admin",usuario.isAdmin());
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","My User");
		return "myuser";
	}
	
	//Pagina de gestion del usuario - Privada
	@GetMapping("/myuser")
	public String myuserPage(Model model, HttpServletRequest sesion)
	{
		User usuario;
		usuario=userOP.chkSession(sesion);
		return myuserESTC(model, usuario);
	}
	
	//Pagina de gestion del usuario, cambio de mail - Privada
	@RequestMapping("/myuser/mail")
	public String myuserPageProcessMail(Model model, HttpServletRequest sesion, @RequestParam String newMail)
	{
		User usuario;
		usuario=userOP.chkSession(sesion);
		//Cambio de mail
		if(newMail!=null)
		{
			if(userOP.chkMail(newMail))
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
	public String myuserPageProcessPassword(Model model, HttpServletRequest sesion, @RequestParam String passwd, @RequestParam String passwd2)
	{
		User usuario;
		usuario=userOP.chkSession(sesion);
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
}
