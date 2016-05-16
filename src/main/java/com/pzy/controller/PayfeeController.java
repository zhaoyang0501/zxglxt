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

import com.pzy.entity.Payfee;
import com.pzy.entity.Task;
import com.pzy.entity.User;
import com.pzy.service.CategoryService;
import com.pzy.service.PayfeeService;
import com.pzy.service.TaskService;
import com.pzy.service.TranService;
import com.pzy.service.UserService;
/***
 * 培训
 * @author panchaoyang
 *qq 263608237
 */
@Controller
@RequestMapping("/admin/payfee")
public class PayfeeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PayfeeService payfeeService;
	@Autowired
	private TranService tranService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TaskService taskService;
	
	@RequestMapping("index")
	public String index(Model model) {
		return "admin/payfee/index";
	}
	@RequestMapping("taskindex")
	public String taskindex(Model model) {
		return "admin/payfee/taskindex";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Model model,Long  id) {
		try {
			payfeeService.delete(id);
			model.addAttribute("state","success");
			model.addAttribute("tip","删除成功！");
		} catch (Exception e) {
			model.addAttribute("state","success");
			model.addAttribute("tip","删除失败，外键约束！");
		}
		return "admin/payfee/index";
	}
	
	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {

		model.addAttribute("users",userService.findAll());
		model.addAttribute("trans",tranService.findAll());
		return "admin/payfee/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model,Payfee payfee) {
		payfeeService.save(payfee);
		model.addAttribute("state","success");
		model.addAttribute("tip","添加成功！");
		return "admin/payfee/create";
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
		Page<Payfee> payfees = payfeeService.findAll(pageNumber, pageSize, name);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", payfees.getContent());
		map.put("iTotalRecords", payfees.getTotalElements());
		map.put("iTotalDisplayRecords", payfees.getTotalElements());
		//map.put("echo", echo);
		return map;
	}
	
	
}
