package com.zhangwenchao.cms.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangwenchao.cms.pojo.Article;
import com.zhangwenchao.cms.pojo.Channel;
import com.zhangwenchao.cms.pojo.Comlain;
import com.zhangwenchao.cms.pojo.User;
import com.zhangwenchao.cms.service.ArticleService;
import com.zhangwenchao.cms.service.ComlainService;
import com.zhangwenchao.cms.service.UserService;

@Controller
@RequestMapping("/admin/")
public class AdminController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ComlainService ComlainService;
	
	/**
	 * @Title: login   
	 * @Description: 后台登录   
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping("/")
	public String login() {
		return "admin/login";
	}
	/**
	 * @Title: home   
	 * @Description: 后台首页  
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping("/home")
	public String home() {
		return "admin/home";
	}
	/**
	 * @Title: welcome   
	 * @Description: 后台欢迎页面   
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping("/welcome")
	public String welcome() {
		return "admin/welcome";
	}
	/**
	 * @Title: user   
	 * @Description: 用户管理   
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping("/user")
	public String user(User user,Model model,
			@RequestParam(value="pageNum",defaultValue="1") int pageNum,@RequestParam(value="pageSize",defaultValue="3") int pageSize) {
		PageInfo<User> pageInfo = userService.getPageInfo(user,pageNum,pageSize);
		model.addAttribute("pageInfo", pageInfo);
		return "admin/user";
	}
	/**
	 * @Title: locked   
	 * @Description: 禁用用户   
	 * @param: @param userId
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	@RequestMapping("/user/locked")
	@ResponseBody
	public boolean locked(Integer userId) {
		boolean locked = userService.locked(userId);
		return locked;
	}
	/**
	 * @Title: unLocked   
	 * @Description: 启用  
	 * @param: @param userId
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	@RequestMapping("/user/unLocked")
	@ResponseBody
	public boolean unLocked(Integer userId) {
		boolean locked = userService.unLocked(userId);
		return locked;
	}
	
	/**
	 * @Title: article   
	 * @Description: 文章管理     
	 * @param: @param article
	 * @param: @param model
	 * @param: @param pageNum
	 * @param: @param pageSize
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping("/article")
	public String article(Article article,Model model,
			@RequestParam(value="pageNum",defaultValue="1") int pageNum,@RequestParam(value="pageSize",defaultValue="3") int pageSize) {
		//设置文章状态
		article.setStatusIds("0,-1,1");
		PageInfo<Article> pageInfo = articleService.getPageInfo(article,pageNum,pageSize);
		model.addAttribute("pageInfo", pageInfo);
		List<Channel> channelList = articleService.getChannelList();
		model.addAttribute("channelList", channelList);
		return "admin/article";
	}
	
	/**
	 * @Title: updateArticleStatus   
	 * @Description: 修改文章状态   
	 * @param: @param article
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	@RequestMapping("/article/update/status")
	@ResponseBody
	public boolean updateArticleStatus(Article article) {
		return articleService.updateStatus(article.getId(), article.getStatus());
	}
	/**
	 * @Title: addHot  
	 * @Description: 文章加热
	 * @param: @param article
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	@RequestMapping("/article/addHot")
	@ResponseBody
	public boolean addHot(Article article) {
		return articleService.addHot(article.getId());
	}
	
	@RequestMapping("/comlain")
	public String showComlatin(Model m,String complaintype,Integer num,Integer num2,@RequestParam(defaultValue="1")Integer currentPage) {
		
		  PageHelper.startPage(currentPage,4);
		List<Comlain> list= ComlainService.comlainList(complaintype,num,num2);
		  for (Comlain comlain : list) {
			  System.out.println(comlain);
			
		}
		  
		  PageInfo pageInfo = new PageInfo<>(list);
		  m.addAttribute("list", list);
		  m.addAttribute("pageInfo", pageInfo);
		  m.addAttribute("complaintype", complaintype);
		  m.addAttribute("num", num);
		  m.addAttribute("num2", num2);
		
		return "admin/comlain";	
	}
	@RequestMapping("/show")
	 public String  show(Integer id,Model m,@RequestParam(defaultValue="1")Integer currentPage) {
		
		 List<Comlain> list =  ComlainService.show(id);
		 PageInfo pageInfo = new PageInfo<>(list);
		  m.addAttribute("list", list);
		  m.addAttribute("pageInfo", pageInfo);
		 
		return "admin/show"; 
	 }
	

}
