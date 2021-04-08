package es.codeurjc.NoMoreSpace.controller;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.NoMoreSpace.model.File;
import es.codeurjc.NoMoreSpace.model.Panel;
import es.codeurjc.NoMoreSpace.model.Pool;
import es.codeurjc.NoMoreSpace.model.User;
import es.codeurjc.NoMoreSpace.repository.PanelRepository;
import es.codeurjc.NoMoreSpace.repository.UserRepository;
import es.codeurjc.NoMoreSpace.services.BlockDependencies;
import es.codeurjc.NoMoreSpace.services.FileDependencies;
import es.codeurjc.NoMoreSpace.services.PanelDependencies;
import es.codeurjc.NoMoreSpace.services.PoolDependencies;
import es.codeurjc.NoMoreSpace.services.UserDependencies;

@Controller
public class PaginaHomeuser
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

	//Pagina del home del usuario - Privada
	@GetMapping("/home")
	public String homePage(Model model, HttpSession sesion)
	{
		Optional <User> usuario;
		usuario=userOP.chkSession(sesion);
		if(usuario==null)
		{
			return userOP.noSession(model, sesion);
		}
		model.addAttribute("actualPanel",usuario.get().getPanel().get(0).getName());
		model.addAttribute("panels",usuario.get().getPanel());
		model.addAttribute("filesUser",usuario.get().getPanel().get(0).getFile());
		return homeESTC(model,usuario);
	}
	
	//Pagina del home del usuario - Privada
	@RequestMapping("/home")
	public String homePageProcess(Model model, HttpSession sesion, @RequestParam String actualPanel, @RequestParam String goToPanel)
	{
		Optional <User> usuario;
		usuario=userOP.chkSession(sesion);
		if(usuario==null)
		{
			return userOP.noSession(model, sesion);
		}
		Panel panels = panelOP.getPanelByPath(usuario.get(),actualPanel+"/"+goToPanel);
		model.addAttribute("actualPanel",actualPanel+"/"+goToPanel);
		model.addAttribute("panels", panels);
		model.addAttribute("filesUser",panels.getFile());
		model.addAttribute("actualPanel",goToPanel);
		return homeESTC(model,usuario);
	}
	
	//Pagina del home del usuario, creacion de panel - Privada
	@RequestMapping("/home/newpanel")
	public String homePageNewPanel(Model model, HttpSession sesion, @RequestParam String nameNewPanel, @RequestParam String actualPanel)
	{
		Optional <User> usuario;
		usuario=userOP.chkSession(sesion);
		if(usuario==null)
		{
			return userOP.noSession(model, sesion);
		}
		model.addAttribute("actualPanel",actualPanel);
		panelOP.panelOnPanel(usuario.get(), actualPanel, nameNewPanel);
		model.addAttribute("panels",panelOP.getPanelByPath(usuario.get(), actualPanel));
		return homeESTC(model,usuario);
	}
	
	//Pagina del home del usuario, borrar panel - Privada
	@RequestMapping("/home/deletepanel")
	public String homePageSharePanel(Model model, HttpSession sesion, @RequestParam String actualPanel)
	{
		Optional <User> usuario;
		usuario=userOP.chkSession(sesion);
		if(usuario==null)
		{
			return userOP.noSession(model, sesion);
		}
		
		String victima=actualPanel.split("/")[actualPanel.split("/").length-1];
		String posicion=actualPanel.substring(0, actualPanel.length()-(victima.length()+1));
		
		panelOP.deletePanelOnPanel(usuario.get(), posicion, victima);
		
		model.addAttribute("panels",usuario.get().getPanel());
		model.addAttribute("actualPanel",usuario.get().getPanel().get(0).getName());
		return homeESTC(model,usuario);
	}
	
	//Pagina del home del usuario, subida de ficheros - Privada
	@PostMapping("/home/upfiles")
	public String homePageUploadFile(Model model, HttpSession sesion, @RequestParam("filesUpload") MultipartFile filesUpload, @RequestParam String actualPanel)
	{
		Optional <User> usuario;
		usuario=userOP.chkSession(sesion);
		if(usuario==null)
		{
			return userOP.noSession(model, sesion);
		}
		
		fileOP.uploadFile(usuario.get(), filesUpload, panelOP.getPanelByPath(usuario.get(), actualPanel));
		model.addAttribute("panels",usuario.get().getPanel());
		model.addAttribute("actualPanel",usuario.get().getPanel().get(0).getName());
		return homeESTC(model,usuario);
	}
	
	//Pagina del home del usuario, descarga de ficheros - Privada
	@RequestMapping("/home/downfiles")
	public String homePageDownFiles(Model model, HttpSession sesion)
	{
		Optional <User> usuario;
		usuario=userOP.chkSession(sesion);
		if(usuario==null)
		{
			return userOP.noSession(model, sesion);
		}
		model.addAttribute("actualPanel",usuario.get().getPanel().get(0).getName());
		model.addAttribute("panels",usuario.get().getPanel());
		return homeESTC(model,usuario);
	}
	
}
