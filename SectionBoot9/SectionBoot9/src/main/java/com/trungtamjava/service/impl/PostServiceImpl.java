package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.CategoryDao;
import com.trungtamjava.dao.PostDao;
import com.trungtamjava.entity.Category;
import com.trungtamjava.entity.Post;
import com.trungtamjava.model.PostDTO;
import com.trungtamjava.model.SearchPostDTO;
import com.trungtamjava.service.PostService;
import com.trungtamjava.utils.DateTimeUtils;

@Service
@Transactional
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDao postDao;

	@Autowired
	private CategoryDao cateDao;

	public void addPost(PostDTO postDTO) {
		Post post = new Post();
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setImages(postDTO.getImages());

		Category category = cateDao.get(postDTO.getCategoryId());
		post.setCategory(category);

		postDao.add(post);
		postDTO.setId(post.getId());
	}

	public void delPost(Long id) {
		Post postEntity = postDao.getPostId(id);
		postDao.delete(postEntity);
	}

	public void updatePost(PostDTO postDTO) {
		Post post = postDao.getPostId(postDTO.getId());
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setImages(postDTO.getImages());

		Category category = cateDao.get(postDTO.getCategoryId());
		post.setCategory(category);

		postDao.update(post);

	}

	public PostDTO getPostId(Long id) {
		Post post = postDao.getPostId(id);

		return convertToDTO(post);
	}

	@Override
	public List<PostDTO> find(SearchPostDTO searchPostDTO) {
		List<Post> postList = postDao.find(searchPostDTO);
		List<PostDTO> postDTOs = new ArrayList<PostDTO>();
		postList.forEach(posts -> {
			postDTOs.add(convertToDTO(posts));
		});
		return postDTOs;
	}

	private PostDTO convertToDTO(Post post) {
		PostDTO postDTO = new PostDTO();
		postDTO.setId(post.getId());
		postDTO.setTitle(post.getTitle());
		postDTO.setDescription(post.getDescription());
		if (post.getImages() != null) {
			postDTO.setImages(new ArrayList<String>(post.getImages()));
		}
		if (post.getCreatedBy() != null) {
			postDTO.setCreateBy(post.getCreatedBy().getName());
		}
		if (post.getCreatedDate() != null) {
			postDTO.setCreatedDate(DateTimeUtils.formatDate(post.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
		}
		if (post.getCategory() != null) {
			postDTO.setCategoryId(post.getCategory().getId());
		}
		return postDTO;
	}

	@Override
	public Long count(SearchPostDTO searchPostDTO) {
		return postDao.count(searchPostDTO);
	}

	@Override
	public Long countTotal(SearchPostDTO searchPostDTO) {
		return postDao.countTotal(searchPostDTO);
	}

}
