
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

import com.pzy.entity.Project;
import com.pzy.entity.User;
import com.pzy.repository.ProjectRepository;
/***
 * 
 * @author qq:263608237
 *
 */
@Service
public class ProjectService {
     @Autowired
     private ProjectRepository projectRepository;

 	public List<Project> findTop3() {
 		return projectRepository.findAll(
 				new PageRequest(0, 15, new Sort(Direction.DESC, "createDate")))
 				.getContent();
 	}
     public List<Project> findAll() {
         return (List<Project>) projectRepository.findAll(new Sort(Direction.DESC, "id"));
     }
     public Page<Project> findAll(final int pageNumber, final int pageSize,final String name,final User user){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
         Specification<Project> spec = new Specification<Project>() {
              public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (name != null) {
                   predicate.getExpressions().add(cb.like(root.get("sn").as(String.class), "%"+name+"%"));
              }
              return predicate;
              }
         };
         Page<Project> result = (Page<Project>) projectRepository.findAll(spec, pageRequest);
         return result;
     	}
     
     public Page<Project> findAll(final int pageNumber, final int pageSize,final Integer type ){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
         Specification<Project> spec = new Specification<Project>() {
              public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (type != null) {
                  predicate.getExpressions().add(cb.equal(root.get("type").as(Integer.class),type));
               }
              return predicate;
              }
         };
         Page<Project> result = (Page<Project>) projectRepository.findAll(spec, pageRequest);
         return result;
     	}
		public void delete(Long id){
			projectRepository.delete(id);
		}
		public Project find(Long id){
			  return projectRepository.findOne(id);
		}
		public void save(Project project){
			projectRepository.save(project);
		}
}