package com.trungtamjava.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamjava.model.PostDTO;
import com.trungtamjava.model.ResponseDTO;
import com.trungtamjava.model.SearchPostDTO;
import com.trungtamjava.service.PostService;
import com.trungtamjava.utils.FileStore;

@CrossOrigin(origins = "*", maxAge = -1)
@RestController
@RequestMapping("/api")
public class PostAPIController {

	@Autowired
	private PostService postService;

	@PostMapping(value = "/post/search")
	public ResponseDTO<PostDTO> find(@RequestBody SearchPostDTO searchPostDTO) {
		ResponseDTO<PostDTO> responseDTO = new ResponseDTO<PostDTO>();
		responseDTO.setData(postService.find(searchPostDTO));
		responseDTO.setRecordsFiltered(postService.count(searchPostDTO));
		responseDTO.setRecordsTotal(postService.countTotal(searchPostDTO));
		return responseDTO;
	}

	@PostMapping("/member/post/add")
	public PostDTO add(@ModelAttribute PostDTO postDTO) {
		postDTO.setImages(FileStore.getFilePaths(postDTO.getImageFile(), "post-"));
		postService.addPost(postDTO);
		return postDTO;
	}

	@PostMapping(value = "/admin/post/update")
	public void updateUsers(@ModelAttribute PostDTO postDTO) {
		postDTO.setImages(FileStore.getFilePaths(postDTO.getImageFile(), "post-"));
		postService.updatePost(postDTO);
	}

	@GetMapping(value = "/post/{id}")
	public PostDTO get(@PathVariable(name = "id") Long id) {
		return postService.getPostId(id);
	}

	@DeleteMapping(value = "/admin/post/delete")
	public void delete(@RequestParam(name = "id") Long id) {
		postService.delPost(id);
	}

	@DeleteMapping(value = "/admin/post/delete-multi")
	public void del(@RequestParam(name = "ids") List<Long> ids) {
		for (Long id : ids) {
			try {
				postService.delPost(id);
			} catch (Exception e) {
				// DO NOTHING
			}
		}
	}

}
