package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.SlidesDao;
import com.trungtamjava.entity.Slide;
import com.trungtamjava.model.SlidesDTO;
import com.trungtamjava.service.SlidesService;

@Service
@Transactional
public class SlidesServiceImpl implements SlidesService {

	@Autowired
	SlidesDao slidesDao;

	@Override
	public List<SlidesDTO> getAllSlides() {
		List<Slide> slides = slidesDao.getAllSlides();
		List<SlidesDTO> slidesDTOs = new ArrayList<SlidesDTO>();
		for (Slide slide : slides) {
			SlidesDTO slidesDTO = new SlidesDTO();

			slidesDTO.setCaption(slide.getCaption());
			slidesDTO.setContent(slide.getContent());
			slidesDTO.setImg(slide.getImg());

			slidesDTOs.add(slidesDTO);

		}

		return slidesDTOs;
	}

	@Override
	public void addSlides(SlidesDTO slidesDTO) {
		Slide s = new Slide();
		s.setCaption(slidesDTO.getCaption());
		s.setContent(slidesDTO.getContent());
		s.setImg(slidesDTO.getImg());

		slidesDao.addSlides(s);

	}

	@Override
	public void deleteSlides(int id) {
		Slide slide = slidesDao.getSlides(id);
		if (slide != null) {
			slidesDao.deleteSlides(slide);
		}
	}

	@Override
	public void updateSlides(SlidesDTO slidesDTO) {
		Slide slide = slidesDao.getSlides(slidesDTO.getId());
		if (slide != null) {
			slide.setCaption(slidesDTO.getCaption());
			slide.setContent(slidesDTO.getContent());
			slide.setImg(slidesDTO.getImg());

			slidesDao.updateSlides(slide);
		}
	}

	@Override
	public SlidesDTO getSlides(int id) {
		Slide slide = slidesDao.getSlides(id);
		SlidesDTO slidesDTO = new SlidesDTO();

		slidesDTO.setCaption(slide.getCaption());
		slidesDTO.setContent(slide.getContent());
		slidesDTO.setImg(slide.getImg());

		return slidesDTO;
	}

}
