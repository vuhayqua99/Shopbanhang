package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Slide;

public interface SlidesDao {
	public List<Slide> getAllSlides();

	public void addSlides(Slide slide);

	public void deleteSlides(Slide slide);

	public void updateSlides(Slide slide);
	
	public Slide getSlides(int id);
	
	

}
