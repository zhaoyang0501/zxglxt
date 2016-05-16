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
import com.pzy.entity.Task;
import com.pzy.entity.User;
import com.pzy.repository.TaskRepository;

/***
 * 
 * @author qq:263608237
 *
 */
@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;

	public List<Task> findAll() {
		return (List<Task>) taskRepository.findAll(new Sort(
				Direction.DESC, "id"));
	}
    public Page<Task> findAll(final int pageNumber, final int pageSize,final User user,final Project project){
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
        Specification<Task> spec = new Specification<Task>() {
             public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
             Predicate predicate = cb.conjunction();
             if (user != null) {
                 predicate.getExpressions().add(cb.equal(root.get("user").as(User.class),user));
              }
             if (project != null) {
                 predicate.getExpressions().add(cb.equal(root.get("project").as(Project.class),project));
              }
             return predicate;
             }
        };
        Page<Task> result = (Page<Task>) taskRepository.findAll(spec, pageRequest);
        return result;
    	}
    
	public List<Task> findByType(final String type) {
		Specification<Task> spec = new Specification<Task>() {
			public Predicate toPredicate(Root<Task> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (type != null) {
					predicate.getExpressions().add(
							cb.equal(root.get("type").as(String.class), type));
				}
				return predicate;
			}
		};
		return taskRepository.findAll(spec);
	}

	public void delete(Long id) {
		taskRepository.delete(id);
	}

	public Task find(Long id) {
		return taskRepository.findOne(id);
	}

	public void save(Task task) {
		taskRepository.save(task);
	}
}