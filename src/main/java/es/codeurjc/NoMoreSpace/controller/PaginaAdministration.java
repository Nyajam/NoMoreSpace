package es.codeurjc.NoMoreSpace.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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

@CacheConfig(cacheNames="users")
@Controller
public class PaginaAdministration
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
	
	//Parte comun del adm
	private String admESTC(Model model, User usuario)
	{
		model.addAttribute("userName","test");
		model.addAttribute("panelCSS",false);
		model.addAttribute("adm",true);
		model.addAttribute("admin",usuario.isAdmin()); //SI EL USUARIO ES ADMINISTRADOR
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Administration");
		List<User> userList=repo.findAll();
		model.addAttribute("userList",userList);
		model.addAttribute("systemUsers",repo.findAll().size());
		model.addAttribute("systemUsersAdm",repo.findByAdmin(true).size());
		return "administration";
	}
	
	//Pagina de administracion - Privada
	@GetMapping("/adm")
	public String admPage(Model model, HttpServletRequest sesion)
	{
		User usuario;
		usuario=userOP.chkSession(sesion);
		if(!usuario.isAdmin())
			return userOP.noAuth(model,sesion.getSession());
		return admESTC(model,usuario);
	}
	
	//Pagina de administracion, ajustes de usuarios - Privada
	@CacheEvict(allEntries=true)
	@RequestMapping("/adm")
	public String admPageProcessUsers(Model model, HttpServletRequest sesion, @RequestParam String passwd, @RequestParam String passwd2, @RequestParam String lock, @RequestParam String adm, @RequestParam String mail, @RequestParam String userName)
	{
		User usuario;
		usuario=userOP.chkSession(sesion);
		if(!usuario.isAdmin())
			return userOP.noAuth(model,sesion.getSession());
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
