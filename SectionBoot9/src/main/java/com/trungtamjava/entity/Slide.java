package com.trungtamjava.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the slides database table.
 * 
 */
@Entity
@Table(name = "slides1")
@NamedQuery(name = "Slide.findAll", query = "SELECT s FROM Slide s")
public class Slide implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String caption;

	private String content;

	private String img;

	public Slide() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaption() {
		return this.caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}