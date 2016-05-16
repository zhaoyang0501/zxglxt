package com.pzy.controller;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pzy.entity.Project;
import com.pzy.entity.Task;
import com.pzy.entity.User;
import com.pzy.service.CategoryService;
import com.pzy.service.ProjectService;
import com.pzy.service.TaskService;
import com.pzy.service.TranService;
import com.pzy.service.UserService;
/***
 * 培训
 * @author panchaoyang
 *qq 263608237
 */
@Controller
@RequestMapping("/admin/project")
public class ProjectController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private TranService tranService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TaskService taskService;
	
	@RequestMapping("index")
	public String index(Model model) {
		return "admin/project/index";
	}
	@RequestMapping("taskindex")
	public String taskindex(Model model) {
		return "admin/project/taskindex";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Model model,Long  id) {
		try {
			projectService.delete(id);
			model.addAttribute("state","success");
			model.addAttribute("tip","删除成功！");
		} catch (Exception e) {
			model.addAttribute("state","success");
			model.addAttribute("tip","删除失败，外键约束！");
		}
		return "admin/project/index";
	}
	
	
	
	@RequestMapping(value = "/tasksubmit", method = RequestMethod.GET)
	public String tasksubmit(Model model,Long id) {
		model.addAttribute("task",taskService.find(id));
		return "admin/project/tasksubmit";
	}
	@RequestMapping(value = "/projectend", method = RequestMethod.GET)
	public String projectend(Model model) {
		model.addAttribute("projects",projectService.findAll());
		return "admin/project/projectend";
	}
	
	@RequestMapping(value = "/projectend", method = RequestMethod.POST)
	public String projectend(Model model,Project project) {
		Project bean=projectService.find(project.getId());
		bean.setState("已结项");
		bean.setRemark1(project.getRemark1());
		projectService.save(bean);
		model.addAttribute("tip","完工报告提交成功！");
		model.addAttribute("projects",projectService.findAll());
		return "admin/project/projectend";
	}
	
	@RequestMapping(value = "/tasksubmit", method = RequestMethod.POST)
	public String tasksubmit(Model model,Task task,@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
		 System.out.println("开始");  
	        String path = request.getSession().getServletContext().getRealPath("upload");  
	        String fileName = file.getOriginalFilename();  
	        System.out.println(path);  
	        File targetFile = new File(path, fileName);  
	        if(!targetFile.exists()){  
	            targetFile.mkdirs();  
	        }  
	  
	        //保存  
	        try {  
	            file.transferTo(targetFile);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
		Task newtask=taskService.find(task.getId());
		newtask.setDocpath(fileName);
		newtask.setState("已提交底稿");
		newtask.setRemark(task.getRemark());
		newtask.setRemark1(task.getRemark1());
		taskService.save(newtask);
		model.addAttribute("tip","审计底稿提交成功！");
		return "admin/project/taskindex";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {

		model.addAttribute("users",userService.findAll());
		model.addAttribute("trans",tranService.findAll());
		return "admin/project/create";
	}
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String order(Model model,Long id) {
		model.addAttribute("project",projectService.find(id));
		model.addAttribute("users",userService.findAll());
		model.addAttribute("trans",tranService.findAll());
		model.addAttribute("categorys1",categoryService.findByType("1"));
		model.addAttribute("categorys2",categoryService.findByType("2"));
		model.addAttribute("categorys3",categoryService.findByType("3"));
		return "admin/project/order";
	}
	@RequestMapping(value = "/task", method = RequestMethod.POST)
	public String task(Model model,Task task) {
		task.setState("未处理");
		task.setUser(userService.find(task.getUser().getId()));
		task.setProject(projectService.find(task.getProject().getId()));
		taskService.save(task);
		model.addAttribute("state","success");
		model.addAttribute("tip","任务分配成功！");
		return "admin/project/index";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model,Project project) {
		project.setState("进行中");
		projectService.save(project);
		model.addAttribute("state","success");
		model.addAttribute("tip","添加成功！");
		return "admin/project/create";
	}
	
	@InitBinder  
	protected void initBinder(HttpServletRequest request,   ServletRequestDataBinder binder) throws Exception {   
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true)); 
	}  
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "length", defaultValue = "10") int length, 
			String name,
			String xq,
			HttpSession httpSession
			) throws ParseException {
		int pageNumber = (int) (start / length) + 1;
		int pageSize = length;
		User user=(User)httpSession.getAttribute("adminuser");
		Page<Project> projects = projectService.findAll(pageNumber, pageSize, name,user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", projects.getContent());
		map.put("iTotalRecords", projects.getTotalElements());
		map.put("iTotalDisplayRecords", projects.getTotalElements());
		//map.put("echo", echo);
		return map;
	}
	
	
	@RequestMapping(value = "/tasklist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> tasklist(
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "length", defaultValue = "10") int length, 
			String name,
			String xq,
			HttpSession httpSession
			) throws ParseException {
		int pageNumber = (int) (start / length) + 1;
		int pageSize = length;
		User user=(User)httpSession.getAttribute("adminuser");
		user=userService.find(user.getId());
		Page<Task> projects = taskService.findAll(pageNumber, pageSize, user,null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", projects.getContent());
		map.put("iTotalRecords", projects.getTotalElements());
		map.put("iTotalDisplayRecords", projects.getTotalElements());
		return map;
	}
	@RequestMapping(value = "/workindex", method = RequestMethod.GET)
	public String worklist(Model model) {
		return "admin/project/workindex";
	}
	
	@RequestMapping(value = "/worklist")
	@ResponseBody
	public Map<String, Object> worklist(
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "length", defaultValue = "10") int length, 
			String name,
			String xq,
			Long pid,
			
			HttpSession httpSession
			) throws ParseException {
		int pageNumber = (int) (start / length) + 1;
		int pageSize = length;
		Project project=projectService.find(pid);
		Page<Task> projects = taskService.findAll(pageNumber, pageSize, null,project);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", projects.getContent());
		map.put("iTotalRecords", projects.getTotalElements());
		map.put("iTotalDisplayRecords", projects.getTotalElements());
		return map;
	}
}
