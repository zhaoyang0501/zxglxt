package com.pzy.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pzy.entity.Category;
import com.pzy.repository.CategoryRepository;

/***
 * 
 * @author qq:263608237
 *
 */
@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> findAll() {
		return (List<Category>) categoryRepository.findAll(new Sort(
				Direction.DESC, "id"));
	}

	public List<Category> findByType(final String type) {
		Specification<Category> spec = new Specification<Category>() {
			public Predicate toPredicate(Root<Category> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (type != null) {
					predicate.getExpressions().add(
							cb.equal(root.get("type").as(String.class), type));
				}
				return predicate;
			}
		};
		return categoryRepository.findAll(spec);
	}

	public void delete(Long id) {
		categoryRepository.delete(id);
	}

	public Category find(Long id) {
		return categoryRepository.findOne(id);
	}

	public void save(Category category) {
		categoryRepository.save(category);
	}
}