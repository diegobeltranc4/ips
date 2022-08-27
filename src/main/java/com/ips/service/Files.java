package com.ips.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ips.dto.UsersDto;
import com.ips.entity.User;
import com.ips.repository.UserRepository;
import com.ips.service.*;
import com.ips.entity.User;
import com.ips.entity.Document;

@Service
public class Files {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	UserImpl userImpl;
	
	//@CrossOrigin(origins = "http://localhost:8080")
	public List<Document> file(String document) {

		File route = new File("src/main/resources/static/docs/" + File.separator );//"src/main/resources/static/docs/" + File.separator  //   "C:" + File.separator + "prueba" + File.separator

		String[] fileNames = route.list();

		int l = fileNames.length;

		List<UsersDto> usuarios = userService.findAll();

		List<Document> documentos = new ArrayList<>();
		
		String docAux = null;
		String nomAux = null;
		
		for(int i = 0; i<usuarios.size();i++) {
			
			if(usuarios.get(i).getNumeroDocumento()==document) {
				docAux = usuarios.get(i).getNumeroDocumento();
				nomAux = (usuarios.get(i).getNombre1() + " " + usuarios.get(i).getNombre2() + " "
							+ usuarios.get(i).getApellido1() + " " + usuarios.get(i).getApellido2());				
			}
			
		}
		
		for(int i = 0 ; i <fileNames.length; i++) {
			
			Document documento = new Document();
			
			if (fileNames[i].contains(document)) {
				
				documento.setExamen(route.getPath().toString() + File.separator + fileNames[i]);
				documento.setNexamen(fileNames[i]);
				documento.setNumeroDocumento(docAux);
				documento.setNombre(nomAux);
				
				documentos.add(documento);
			}
			
		}

		return documentos;
	}
	
	//@CrossOrigin(origins = "http://localhost:8080")
	public List<Document> fileAll() {

		File route = new File("src/main/resources/static/docs/" + File.separator );

		String[] fileNames = route.list();

		int l = fileNames.length;

		List<UsersDto> usuarios = userService.findAll();

		List<Document> documentos = new ArrayList<>();

		int s = 0;

		for (int i = 0; i < fileNames.length; i++) {

			Document documento = new Document();

			for (int j = 0; j < usuarios.size(); j++) {
				if (fileNames[i].contains(usuarios.get(j).getNumeroDocumento())) {

					documento.setExamen(route.getPath().toString() + File.separator + fileNames[i]);// route.getAbsolutePath()
					documento.setNombre(usuarios.get(j).getNombre1() + " " + usuarios.get(j).getNombre2() + " "
							+ usuarios.get(j).getApellido1() + " " + usuarios.get(j).getApellido2());
					documento.setNumeroDocumento(usuarios.get(j).getNumeroDocumento());
					documento.setNexamen(fileNames[i]);

					documentos.add(documento);

					// System.out.println(documento.getExamen());
					// System.out.println(documento.getNombre());
					// System.out.println(documento.getNumeroDocumento());
				}
			}
		}

		// System.out.println(documentos.toString());

		// System.out.println(usuarios);

		return documentos;
	}
}