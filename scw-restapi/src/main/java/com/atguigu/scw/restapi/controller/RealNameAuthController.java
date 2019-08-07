package com.atguigu.scw.restapi.controller;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.atguigu.scw.manager.bean.TCert;
import com.atguigu.scw.manager.bean.TMemberCert;
import com.atguigu.scw.manager.bean.TMemeber;
import com.atguigu.scw.manager.bean.TMemeberTicket;
import com.atguigu.scw.restapi.bean.AJAXResult;
import com.atguigu.scw.restapi.service.MemberService;
import com.atguigu.scw.restapi.service.TCertService;
import com.atguigu.scw.restapi.service.TMemberTicketService;
import com.atguigu.scw.restapi.service.TMemeberCertService;

@RestController
public class RealNameAuthController {
	@Autowired
	private TCertService certServiceImpl;
	@Autowired
	private MemberService memberService;
	@Autowired
	private TMemeberCertService memberCertServiceImpl;
	@Autowired
	private TaskService taskService;
	@Autowired
	private TMemberTicketService tMemberTicketServiceImpl;
	
	@RequestMapping("/getcerts")
	public AJAXResult<List<TCert>> getCerts(String accttype){
		List<TCert> list = certServiceImpl.getCertsByAcctType(accttype);
		if(list != null) {
			return AJAXResult.success("查询成功", list, null);
		}else {
			return AJAXResult.fail("查询失败", null, null);
		}
		
	}
	private String uploadFile(String webPath,MultipartFile file,HttpSession session) {
		ServletContext context = session.getServletContext();
		String realPath = context.getRealPath(webPath);
		System.out.println("真实地址:" + realPath);
		String filename = UUID.randomUUID().toString().replace("-", "").substring(0, 10) + "_file_"
                + file.getOriginalFilename();
		
		try {
            //webPath不存在的情况下必须创建
            File file2 = new File(realPath);
            if(!file2.exists()){
                //创建目录
                file2.mkdirs();
            }
			file.transferTo(new File(realPath+"/" + filename));
			return webPath+"/" + filename;
		} catch (Exception e) {
			return null;
		}
		
	}
	@RequestMapping("/upload")
	public AJAXResult<List<TMemberCert>> certUpload( @RequestParam("file")MultipartFile[] file,@RequestParam("certid")int[] certid,int memberid,HttpSession session){
		if(file != null && file.length > 0) {
			List<TMemberCert> list = new ArrayList<TMemberCert>();
			for(int i = 0; i < certid.length;i++) {
				TMemberCert tmc = new TMemberCert();
				MultipartFile multipartFile = file[i];
				String path = uploadFile("/auth_img", multipartFile,session); // 上传，返回在服务器中的地址
				System.out.println("服务器中的地址:" + path);
				tmc.setCertid(certid[i]);
				tmc.setMemberid(memberid);
				tmc.setIconpath(path);
				list.add(tmc);
			}
			try {
				memberCertServiceImpl.saveCerts(list);
				return AJAXResult.success("保存成功", list, null);
			}catch (Exception e) {
				e.printStackTrace();
				return AJAXResult.fail("保存失败", null, null);
			}
		}else {
			return AJAXResult.fail("保存失败", null, null);
		}
	}
	
	@RequestMapping("/saveBasicInfo")
	public AJAXResult<Object> saveBasicInfo(TMemeber memeber){
		try{
			System.out.println(memeber);
			memberService.saveBasicInfo(memeber);
			return AJAXResult.success("保存成功", null, null);
		}catch (Exception e) {
			e.printStackTrace();
			return AJAXResult.fail("保存失败", null, null);
		}
	}
	@RequestMapping("/sendEmail")
	public AJAXResult<Object> sendEmail(String email,int memberid,String username){
		// 用户名由数据库查询出来
		// 写死，后期改
		try {
			memberService.sendEmail(email, username, memberid);
			return AJAXResult.success("发送成功", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			return AJAXResult.fail("发送失败", null, null);
		}
		
	}
	@RequestMapping("/validateCode")
	public AJAXResult<Object> validateCode(String code,int memberid){
		try {
			TMemeberTicket tmt = tMemberTicketServiceImpl.getInstanceByMemberId(memberid);
			// 领取任务，完成任务
			Task task = taskService.createTaskQuery().processInstanceId(tmt.getTicketid()).singleResult();
			// 完成任务
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usercode", code);
			taskService.complete(task.getId(), map);
			// 新任务名称与当前完成的任务名字不一样则为申请成功
			Task newTask = taskService.createTaskQuery().processInstanceId(tmt.getTicketid()).singleResult();
			if(newTask.getName().equals(task.getName()))
				return AJAXResult.success("申请成功", null, null);
			else 
				return AJAXResult.fail("申请失败", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			return AJAXResult.fail("申请失败", null, null);
		}
		
	}
	
	
}
