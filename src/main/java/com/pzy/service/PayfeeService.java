package com.pzy.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pzy.entity.Payfee;
import com.pzy.entity.Project;
import com.pzy.repository.PayfeeRepository;

/***
 * 
 * @author qq:263608237
 *
 */
@Service
public class PayfeeService {
	@Autowired
	private PayfeeRepository payfeeRepository;

	public List<Payfee> findAll() {
		return (List<Payfee>) payfeeRepository.findAll(new Sort(
				Direction.DESC, "id"));
	}
	public Page<Payfee> findAll(final int pageNumber, final int pageSize,final String name){
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
        Specification<Payfee> spec = new Specification<Payfee>() {
             public Predicate toPredicate(Root<Payfee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
             Predicate predicate = cb.conjunction();
             if (name != null) {
                  predicate.getExpressions().add(cb.like(root.get("tran").get("dept").as(String.class), "%"+name+"%"));
             }
             return predicate;
             }
        };
        Page<Payfee> result = (Page<Payfee>) payfeeRepository.findAll(spec, pageRequest);
        return result;
    	}
	public List<Payfee> findByType(final String type) {
		Specification<Payfee> spec = new Specification<Payfee>() {
			public Predicate toPredicate(Root<Payfee> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (type != null) {
					predicate.getExpressions().add(
							cb.equal(root.get("type").as(String.class), type));
				}
				return predicate;
			}
		};
		return payfeeRepository.findAll(spec);
	}

	public void delete(Long id) {
		payfeeRepository.delete(id);
	}

	public Payfee find(Long id) {
		return payfeeRepository.findOne(id);
	}

	public void save(Payfee payfee) {
		payfeeRepository.save(payfee);
	}
}