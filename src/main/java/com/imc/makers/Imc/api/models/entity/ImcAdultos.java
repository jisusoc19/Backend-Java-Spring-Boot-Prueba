package com.imc.makers.Imc.api.models.entity;

import java.io.Serializable;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Imc_Adultos")
public class ImcAdultos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 2, message ="el tamaño tiene que estar minimo 2")
	@Column(name = "nombre")
	private String nombre;
	
	@NotEmpty(message = "no puede estar vacio")
	@Size(max = 4 , message ="Tiene que tener un maximo de 4 caracteres")
	@Column(name = "estatura")
	private Double estatura;
	
	@NotEmpty(message = "no puede estar vacio")
	@Size(max = 4, message ="Tiene que tener un maximo de 4 caracteres")
	@Column(name = "peso")
	private Double peso;
	
	@NotEmpty(message = "no puede estar vacio")
	@Email(message="no es una dirrecion de correo bien formada ")
	@Column(name = "email", unique = true)
	private String email;
	
	private String foto;
	
	@Column(name = "ImcA")
	private Double ImcA;
	
	@NotEmpty(message = "no puede estar vacio")
	@Column(name="sexo")
	private String sexo;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getEstatura() {
		return estatura;
	}
	public void setEstatura(Double estatura) {
		this.estatura = estatura;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Double getImcA() {
		return ImcA;
	}
	public void setImcA(Double imcA) {
		ImcA = imcA;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	
	


}
