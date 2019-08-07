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
			return AJAXResult.success("��ѯ�ɹ�", list, null);
		}else {
			return AJAXResult.fail("��ѯʧ��", null, null);
		}
		
	}
	private String uploadFile(String webPath,MultipartFile file,HttpSession session) {
		ServletContext context = session.getServletContext();
		String realPath = context.getRealPath(webPath);
		System.out.println("��ʵ��ַ:" + realPath);
		String filename = UUID.randomUUID().toString().replace("-", "").substring(0, 10) + "_file_"
                + file.getOriginalFilename();
		
		try {
            //webPath�����ڵ�����±��봴��
            File file2 = new File(realPath);
            if(!file2.exists()){
                //����Ŀ¼
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
				String path = uploadFile("/auth_img", multipartFile,session); // �ϴ��������ڷ������еĵ�ַ
				System.out.println("�������еĵ�ַ:" + path);
				tmc.setCertid(certid[i]);
				tmc.setMemberid(memberid);
				tmc.setIconpath(path);
				list.add(tmc);
			}
			try {
				memberCertServiceImpl.saveCerts(list);
				return AJAXResult.success("����ɹ�", list, null);
			}catch (Exception e) {
				e.printStackTrace();
				return AJAXResult.fail("����ʧ��", null, null);
			}
		}else {
			return AJAXResult.fail("����ʧ��", null, null);
		}
	}
	
	@RequestMapping("/saveBasicInfo")
	public AJAXResult<Object> saveBasicInfo(TMemeber memeber){
		try{
			System.out.println(memeber);
			memberService.saveBasicInfo(memeber);
			return AJAXResult.success("����ɹ�", null, null);
		}catch (Exception e) {
			e.printStackTrace();
			return AJAXResult.fail("����ʧ��", null, null);
		}
	}
	@RequestMapping("/sendEmail")
	public AJAXResult<Object> sendEmail(String email,int memberid,String username){
		// �û��������ݿ��ѯ����
		// д�������ڸ�
		try {
			memberService.sendEmail(email, username, memberid);
			return AJAXResult.success("���ͳɹ�", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			return AJAXResult.fail("����ʧ��", null, null);
		}
		
	}
	@RequestMapping("/validateCode")
	public AJAXResult<Object> validateCode(String code,int memberid){
		try {
			TMemeberTicket tmt = tMemberTicketServiceImpl.getInstanceByMemberId(memberid);
			// ��ȡ�����������
			Task task = taskService.createTaskQuery().processInstanceId(tmt.getTicketid()).singleResult();
			// �������
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usercode", code);
			taskService.complete(task.getId(), map);
			// �����������뵱ǰ��ɵ��������ֲ�һ����Ϊ����ɹ�
			Task newTask = taskService.createTaskQuery().processInstanceId(tmt.getTicketid()).singleResult();
			if(newTask.getName().equals(task.getName()))
				return AJAXResult.success("����ɹ�", null, null);
			else 
				return AJAXResult.fail("����ʧ��", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			return AJAXResult.fail("����ʧ��", null, null);
		}
		
	}
	
	
}
