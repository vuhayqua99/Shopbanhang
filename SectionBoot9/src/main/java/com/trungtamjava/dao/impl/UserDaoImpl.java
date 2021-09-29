package com.trungtamjava.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.UserDao;
import com.trungtamjava.entity.User;
import com.trungtamjava.model.SearchUserDTO;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void insert(User user) {
		entityManager.persist(user);
	}

	@Override
	public void update(User user) {
		entityManager.merge(user);
	}

	@Override
	public void delete(User user) {
		entityManager.remove(user);
	}

	@Override
	public User get(Long id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public User getByUserName(String userName) {
		String jql = "select u from User u where u.username =: uname";
		return entityManager.createQuery(jql, User.class).setParameter("uname", userName).getSingleResult();
	}

	@Override
	public List<User> search(String findName, int start, int length) {
		String jql = "select u from User u where name  =:name";
		Query query = entityManager.createQuery(jql, User.class);
		query.setParameter("name", findName);
		query.setFirstResult(start).setMaxResults(length);
		return query.getResultList();
	}

	@Override
	public List<User> getAllUsers(int start, int length) {
		String jql = "select u from User u";
		return entityManager.createQuery(jql, User.class).setFirstResult(start).setMaxResults(length).getResultList();
	}

	@Override
	public long countGetAll() {
		String jql = "select count(u) from User u";
		return entityManager.createQuery(jql, Long.class).getSingleResult();
	}

	@Override
	public long countSearch(String name) {
		String jql = "select count(u) from User u where u.name  =:name";
		return entityManager.createQuery(jql, Long.class).setParameter("name", name).getSingleResult();
	}

	@Override
	public void enable(User user) {
		entityManager.merge(user);
	}

	@Override
	public List<User> find(SearchUserDTO searchUserDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);

		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (StringUtils.isNotBlank(searchUserDTO.getKeyword())) {
			Predicate predicate1 = builder.like(builder.lower(root.get("phone")),
					"%" + searchUserDTO.getKeyword().toLowerCase() + "%");
			Predicate predicate2 = builder.like(builder.lower(root.get("name")),
					"%" + searchUserDTO.getKeyword().toLowerCase() + "%");

			predicates.add(builder.or(predicate2, predicate1));
		}

		if (searchUserDTO.getRoleList() != null) {
			predicates.add(root.join("roles").in(searchUserDTO.getRoleList()));
		}

		if (searchUserDTO.getEnabled() != null) {
			predicates.add(builder.equal(root.get("enabled"), searchUserDTO.getEnabled()));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		// order
		if (StringUtils.equals(searchUserDTO.getSortBy().getData(), "id")) {
			if (searchUserDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchUserDTO.getSortBy().getData(), "name")) {
			if (searchUserDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("name")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("name")));
			}
		} else if (StringUtils.equals(searchUserDTO.getSortBy().getData(), "createdDate")) {
			if (searchUserDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("createdDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("createdDate")));
			}
		}

		TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery.select(root).distinct(true));
		if (searchUserDTO.getStart() != null) {
			typedQuery.setFirstResult(searchUserDTO.getStart());
			typedQuery.setMaxResults(searchUserDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public long count(SearchUserDTO searchUserDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from(User.class);

		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (StringUtils.isNotBlank(searchUserDTO.getKeyword())) {
			Predicate predicate1 = builder.like(builder.lower(root.get("phone")),
					"%" + searchUserDTO.getKeyword().toLowerCase() + "%");
			Predicate predicate2 = builder.like(builder.lower(root.get("name")),
					"%" + searchUserDTO.getKeyword().toLowerCase() + "%");

			predicates.add(builder.or(predicate2, predicate1));
		}

		if (searchUserDTO.getRoleList() != null) {
			predicates.add(root.join("roles").in(searchUserDTO.getRoleList()));
		}

		if (searchUserDTO.getEnabled() != null) {
			predicates.add(builder.equal(root.get("enabled"), searchUserDTO.getEnabled()));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public long countTotal(SearchUserDTO searchUserDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from(User.class);

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}
}
