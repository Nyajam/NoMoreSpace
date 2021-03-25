package es.codeurjc.NoMoreSpace.controller;

import java.util.Optional;

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
	private String myuserESTC(Model model, Optional <User> usuario)
	{
		model.addAttribute("userName",usuario.get().getUsername());
		model.addAttribute("userSizePoolMax","10GB");
		model.addAttribute("userSizePool",poolOP.sizeOfPool(usuario.get())+"B");
		if(usuario.get().getPool()!=null)
		{
			if(usuario.get().getPool().getFile()!=null)
				model.addAttribute("userSizePoolFiles",usuario.get().getPool().getFile().size());
			else
				model.addAttribute("userSizePoolFiles",0);
		}
		else
			model.addAttribute("userSizePoolFiles",0);
		model.addAttribute("userSizePoolPercent",poolOP.sizeOfPool(usuario.get())/10240.0);
		model.addAttribute("userMail",usuario.get().getMail());
		
		model.addAttribute("panelCSS",false);
		model.addAttribute("myuser",true);
		model.addAttribute("admin",usuario.get().isAdmin());
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","My User");
		return "myuser";
	}
	
	//Pagina de gestion del usuario - Privada
	@GetMapping("/myuser")
	public String myuserPage(Model model, HttpSession sesion)
	{
		Optional <User> usuario;
		usuario=userOP.chkSession(sesion);
		if(usuario==null)
		{
			return userOP.noSession(model, sesion);
		}
		return myuserESTC(model, usuario);
	}
	
	//Pagina de gestion del usuario, cambio de mail - Privada
	@RequestMapping("/myuser/mail")
	public String myuserPageProcessMail(Model model, HttpSession sesion, @RequestParam String newMail)
	{
		Optional <User> usuario;
		usuario=userOP.chkSession(sesion);
		if(usuario==null)
		{
			return userOP.noSession(model, sesion);
		}
		//Cambio de mail
		if(newMail!=null)
		{
			if(userOP.chkMail(newMail))
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
		usuario=userOP.chkSession(sesion);
		if(usuario==null)
		{
			return userOP.noSession(model, sesion);
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
}
