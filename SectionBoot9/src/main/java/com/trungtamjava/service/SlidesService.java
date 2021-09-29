package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.SlidesDTO;

public interface SlidesService {
	public List<SlidesDTO> getAllSlides();

	public void addSlides(SlidesDTO slidesDTO);

	public void deleteSlides(int id);

	public void updateSlides(SlidesDTO slidesDTO);
	
	public SlidesDTO getSlides(int id);
}
