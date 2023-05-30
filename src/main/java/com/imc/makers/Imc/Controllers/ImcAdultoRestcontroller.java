package com.imc.makers.Imc.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import com.imc.makers.Imc.api.models.entity.ImcAdultos;

import com.imc.makers.Imc.models.services.IimcAdultoService;
@CrossOrigin(origins = {"http://localhost:4200","*"})
@RequestMapping("/api")
@RestController
public class ImcAdultoRestcontroller {
	@Autowired
	private IimcAdultoService imcAdultoservice;
	
	@GetMapping("/imcadulto")
	public List<ImcAdultos> index(){
		return imcAdultoservice.findAll();
	}
	@GetMapping("/imcadulto/page/{page}")
	public Page<ImcAdultos> index(@PathVariable Integer page){
		return imcAdultoservice.findAll(PageRequest.of(page, 4));
	}
	@GetMapping("/imcadulto/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> show(@PathVariable Long id) {
		ImcAdultos imcadul = null;
		Map<String, Object> response = new HashMap<>();
		try {
			imcadul=imcAdultoservice.findById(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		 
		if(imcadul==null) {
			response.put("mensaje", "El Usuario Id : ".concat(id.toString().concat(" no Existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
	
		return  new ResponseEntity<ImcAdultos>(imcadul, HttpStatus.OK);
	}
	@PostMapping("/imcadulto")
	public ResponseEntity<?>  create(@Valid  @RequestBody ImcAdultos imcAdultos, BindingResult result) {
		
	    ImcAdultos IMcanew =null; 
	    Map<String, Object> response = new HashMap<>();
	    if(result.hasErrors()) {
	    	
	    	/*List<String> errors = new ArrayList<>();
	    	for(FieldError err: result.getFieldErrors()) {
	    		errors.add("el campo " + err.getField() + " " +err.getDefaultMessage());
	    	}*/
	    	List<String> errors = result.getFieldErrors()
	    			.stream()
	    			.map(err ->"el campo " + err.getField() + " " +err.getDefaultMessage())
	    			.collect(Collectors.toList());
	    	
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
	    }
	    try {
	    	Double imc =  (double) Math.round(imcAdultos.getPeso() / (imcAdultos.getEstatura() * imcAdultos.getEstatura()));
	    	imcAdultos.setImcA(imc); 
			IMcanew = imcAdultoservice.save(imcAdultos);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
	    response.put("mensaje", "el usuario ha sido creado con exito");
	    response.put("ImcAdulto",IMcanew);
	    return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/imcadulto/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody ImcAdultos imcAdultos, BindingResult result, @PathVariable Long id ) {
		ImcAdultos imsAactual = imcAdultoservice.findById(id);
		ImcAdultos imsnew= null;
		Map<String, Object> response = new HashMap<>();
	    if(result.hasErrors()) {
	    	List<String> errors = result.getFieldErrors()
	    			.stream()
	    			.map(err ->"el campo " + err.getField() + " " +err.getDefaultMessage())
	    			.collect(Collectors.toList());
	    	
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
	    }
		
		if(imsAactual==null) {
			response.put("mensaje", "Error: no se pudo editar, El Usuario Id : ".concat(id.toString().concat(" no Existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
	    try {
	    	Double imc =  (double) Math.round(imcAdultos.getPeso() / (imcAdultos.getEstatura() * imcAdultos.getEstatura()));
	    	imsAactual.setNombre(imcAdultos.getNombre());
	    	imsAactual.setEstatura(imcAdultos.getEstatura());
	    	imsAactual.setPeso(imcAdultos.getPeso());
	    	imsAactual.setEmail(imcAdultos.getEmail());
	    	imsAactual.setSexo(imcAdultos.getSexo());
	    	imsAactual.setImcA(imc);
	    	
			imsnew = imcAdultoservice.save(imsAactual);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al ACTUALIZAR en la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
	    response.put("mensaje", "el usuario ha sido actualizado con exito");
	    response.put("ImcAdulto",imsnew);
	    return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
		
		
		
	}
	@DeleteMapping("/imcadulto/{id}")
	
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		ImcAdultos imsAactual = imcAdultoservice.findById(id);
		if(imsAactual==null) {
			response.put("mensaje", "Error: no se pudo editar, El Usuario Id : ".concat(id.toString().concat(" no Existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		try {
			String nombrefotoant = imsAactual.getFoto();
			if(nombrefotoant!=null && nombrefotoant.length()>0) {
				Path rutafotoanterior= Paths.get("upload").resolve(nombrefotoant).toAbsolutePath();
				File archivofotoanterior=rutafotoanterior.toFile();
				if(archivofotoanterior.exists()&& archivofotoanterior.canRead()) {
					archivofotoanterior.delete();
				}
			}
			imcAdultoservice.delete(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al Eliminar en la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "el usuario elminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	@PostMapping("/imcadulto/upload")
	public ResponseEntity<?>upload(@RequestParam("archivo") MultipartFile archivo,@RequestParam("id") Long id){
		Map<String, Object> response = new HashMap<>();
		ImcAdultos imsa= imcAdultoservice.findById(id);
		
		if(!archivo.isEmpty()) {
			String nombrea=UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
			Path rutaArchivo = Paths.get("upload").resolve(nombrea).toAbsolutePath();
			try {
				Files.copy(archivo.getInputStream(),rutaArchivo);
			}catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen en la base de datos " + nombrea);
				response.put("error", e.getMessage().concat(":").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			String nombrefotoant = imsa.getFoto();
			if(nombrefotoant!=null && nombrefotoant.length()>0) {
				Path rutafotoanterior= Paths.get("upload").resolve(nombrefotoant).toAbsolutePath();
				File archivofotoanterior=rutafotoanterior.toFile();
				if(archivofotoanterior.exists()&& archivofotoanterior.canRead()) {
					archivofotoanterior.delete();
				}
			}
			imsa.setFoto(nombrea);
			imcAdultoservice.save(imsa);
			response.put("Usuario", imsa);
			response.put("mensaje", "has subido correctamente la imagen:"+ nombrea);
			
		}
		
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
		
	}
	@GetMapping("/upload/img/{nombrefoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombrefoto){
	    Path rutaArchivo = Paths.get("upload").resolve(nombrefoto).toAbsolutePath();
	    Resource recurso = null;
	    try {
	        recurso = new UrlResource(rutaArchivo.toUri());
	    } catch (MalformedURLException e) {
	        // Manejar la excepción de manera adecuada
	        e.printStackTrace();
	    }
	    if (recurso == null || !recurso.exists() || !recurso.isReadable()) {
	    	rutaArchivo = Paths.get("./src/main/resources/static/img").resolve("nonombre.png").toAbsolutePath();
		    try {
		        recurso = new UrlResource(rutaArchivo.toUri());
		    } catch (MalformedURLException e) {
		        // Manejar la excepción de manera adecuada
		        e.printStackTrace();
		    }
	        throw new RuntimeException("Error: no se pudo cargar la imagen " + nombrefoto);
	    }
	    HttpHeaders cabecera = new HttpHeaders();
	    cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
	    return new ResponseEntity<>(recurso, cabecera, HttpStatus.OK);
	}
	@Configuration
	public class CorsConfig implements WebMvcConfigurer {

	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowedOrigins("http://localhost:4200","*")
	                .allowedMethods("GET", "POST", "PUT", "DELETE")
	                .allowedHeaders("*")
	                .allowCredentials(true);
	    }
	}

		
}
	

