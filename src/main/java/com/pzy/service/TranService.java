
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

import com.pzy.entity.Tran;
import com.pzy.repository.TranRepository;
/***
 * 
 * @author qq:263608237
 *
 */
@Service
public class TranService {
     @Autowired
     private TranRepository tranRepository;

 	public List<Tran> findTop3() {
 		return tranRepository.findAll(
 				new PageRequest(0, 15, new Sort(Direction.DESC, "createDate")))
 				.getContent();
 	}
     public List<Tran> findAll() {
         return (List<Tran>) tranRepository.findAll(new Sort(Direction.DESC, "id"));
     }
     public Page<Tran> findAll(final int pageNumber, final int pageSize,final String name){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
         Specification<Tran> spec = new Specification<Tran>() {
              public Predicate toPredicate(Root<Tran> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (name != null) {
                   predicate.getExpressions().add(cb.like(root.get("name").as(String.class), "%"+name+"%"));
              }
              return predicate;
              }
         };
         Page<Tran> result = (Page<Tran>) tranRepository.findAll(spec, pageRequest);
         return result;
     	}
     
     public Page<Tran> findAll(final int pageNumber, final int pageSize,final Integer type ){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
         Specification<Tran> spec = new Specification<Tran>() {
              public Predicate toPredicate(Root<Tran> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (type != null) {
                  predicate.getExpressions().add(cb.equal(root.get("type").as(Integer.class),type));
               }
              return predicate;
              }
         };
         Page<Tran> result = (Page<Tran>) tranRepository.findAll(spec, pageRequest);
         return result;
     	}
		public void delete(Long id){
			tranRepository.delete(id);
		}
		public Tran find(Long id){
			  return tranRepository.findOne(id);
		}
		public void save(Tran tran){
			tranRepository.save(tran);
		}
}