package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Post;
import com.trungtamjava.model.SearchPostDTO;

public interface PostDao {
	void add(Post post);

	void delete(Post post);

	void update(Post post);

	Post getPostId(Long id);

	List<Post> find(SearchPostDTO searchPostDTO);

	Long count(SearchPostDTO searchPostDTO);

	Long countTotal(SearchPostDTO searchPostDTO);

}
