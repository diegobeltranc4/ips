package com.ips.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ips.dto.UsersDto;
import com.ips.entity.User;
import com.ips.repository.UserRepository;
import com.ips.service.*;
import com.ips.entity.User;
import com.ips.entity.Document;

@Controller
public class AppController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	UserImpl userImpl;

	@Autowired
	Files files;

	@GetMapping({ "/", "/login" })
	public String index() {
		return "login";
	}

	//@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping({ "/valid" })
	public ModelAndView valid(Model model, Authentication aut) {

		files.fileAll();
		
		String userName = aut.getName();

		UsersDto u = userService.findByUsername(userName);

		String tipoU = u.getTipoUsuario();

		String name = u.getNombre1() + " " + u.getNombre2() + " " + u.getApellido1() + " " + u.getApellido2();

		String document = u.getNumeroDocumento();
		
		List<Document> documentos = files.fileAll();
		
		List<Document> documentoUser = files.file(document);
		
		model.addAttribute("documentos",documentos);
		
		model.addAttribute("documentoUser",documentoUser);
		
		System.out.println(documentos.toString());

		// System.out.println(tipoU);

		model.addAttribute("nombre", name);
		
		switch (tipoU) {
		case "admin":
			return new ModelAndView("/paneladmon");
		case "user":
			return new ModelAndView("/paneluser");
		case "med":
			return new ModelAndView("panelmed");
		default:
			return new ModelAndView("/login");
		}	
	}

//	@PostMapping("/admbus")
//	public ModelAndView admbus(Model model, Authentication aut, @RequestParam("document") String document) {
//
//		String userName = aut.getName();
//
//		UsersDto u = userService.findByUsername(userName);
//
//		String tipoU = u.getTipoUsuario();
//
//		String name = u.getNombre1() + " " + u.getNombre2() + " " + u.getApellido1() + " " + u.getApellido2();
//
//		// String document = u.getNumeroDocumento();
//
//		// System.out.println(tipoU);
//
//		model.addAttribute("nombre", name);
//
//		//String[] resultado = files.file(document);
//
//		//String document1 = resultado[0];
//		//String document2 = resultado[1];
//		//String document3 = resultado[2];
//
//		// System.out.println("Documento 1: " + document1);
//		// System.out.println("Documento 2: " + document2);
//		// System.out.println("Documento 3: " + document3);
//
//		model.addAttribute("archivo", document3);
//
//		model.addAttribute("archivo1", document1);
//
//		model.addAttribute("archivo2", document2);
//
//		model.addAttribute("archivo3", document3);
//
//		List<Document> documentos = files.fileAll();
//		System.out.println(documentos.toString());
//
//		return new ModelAndView("paneladmon");
//	}

	@GetMapping("/paneladmon")
	public ModelAndView paneladmon(Model model, Authentication aut) {

		List<Document> documentos = files.fileAll();
		
		model.addAttribute("documentos",documentos);
		
		System.out.println(documentos.toString());
		
		return new ModelAndView("paneladmon");
	}

	@GetMapping("/paneluser")
	public ModelAndView paneluser(Model model, Authentication aut) {

		return new ModelAndView("paneluser");
	}

	@GetMapping("/registro")
	public ModelAndView registro(Model model, Authentication aut, User user) {

		String userName = aut.getName();

		UsersDto u = userService.findByUsername(userName);

		String tipoU = u.getTipoUsuario();

		switch (tipoU) {
			case "admin":
				return new ModelAndView("/registro");
			case "user":
				return new ModelAndView("/paneluser");
			case "med":
				return new ModelAndView("panelmed");
			default:
				return new ModelAndView("/login");
		}
	}

	@GetMapping({ "/pacienteRegistro" })
	public ModelAndView preg(Model model, User user) {
		return new ModelAndView("/pacienteRegistro");
	}

	@PostMapping("/guardar")
	public String guardar(UsersDto user) {

		userImpl.save(user);

		// userRepository.save(user);
		return "redirect:/valid";
	}

	@PostMapping("/guardarUsuario")
	public String guardarUsuario(UsersDto user) {

		user.setTipoUsuario("PAC");
		userImpl.save(user);

		// userRepository.save(user);
		return "redirect:/valid";
	}

}