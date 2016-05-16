package com.pzy.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.pzy.entity.Company;
import com.pzy.service.CompanyService;
import com.pzy.service.UserService;
/***
 * 培训
 * @author panchaoyang
 *qq 263608237
 */
@Controller
@RequestMapping("/admin/company")
public class CompanyController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping("index")
	public String index(Model model) {
		return "admin/company/index";
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		
		model.addAttribute("users",userService.findAll());
		return "admin/company/create";
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model,Company company) {
		companyService.save(company);
		model.addAttribute("state","success");
		model.addAttribute("tip","添加成功！");
		return "admin/company/create";
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
			String xq
			) throws ParseException {
		int pageNumber = (int) (start / length) + 1;
		int pageSize = length;
		Page<Company> companys = companyService.findAll(pageNumber, pageSize, name);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", companys.getContent());
		map.put("iTotalRecords", companys.getTotalElements());
		map.put("iTotalDisplayRecords", companys.getTotalElements());
		//map.put("echo", echo);
		return map;
	}
	
}
