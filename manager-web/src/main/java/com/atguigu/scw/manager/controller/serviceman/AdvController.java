package com.atguigu.scw.manager.controller.serviceman;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.scw.manager.bean.TAdvertisement;
import com.atguigu.scw.manager.bean.TUser;
import com.atguigu.scw.manager.bean.tool.AJAXResult;
import com.atguigu.scw.manager.bean.tool.Page;
import com.atguigu.scw.manager.service.AdvService;

@RequestMapping("/serviceman/adv")
@Controller
public class AdvController {
	@Autowired
	private AdvService advServiceImpl;

	@RequestMapping("/list")
	public String list() {
		return "manager/serviceman/advertisement";
	}
	@ResponseBody
	@RequestMapping("/check")
	public Object check() {
		AJAXResult result = new AJAXResult();
		return result;
	}
	@ResponseBody
	@RequestMapping("/addAdv")
	public Object addAdv() {
		AJAXResult result = new AJAXResult();
		return result;
	}
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete() {
		AJAXResult result = new AJAXResult();
		return result;
	}
	@ResponseBody
	@RequestMapping("/edit")
	public Object edit() {
		AJAXResult result = new AJAXResult();
		return result;
	}
	

    /**
     * produces="text/html;charset=utf-8"�൱�ڸ���Ӧͷ��Content-Type�ܽ������
     * @Description (TODO������һ�仰�����������������)
     * @param session
     * @param file
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/upload")
    public Object upload(HttpSession session, @RequestParam("ad")MultipartFile file, String name) {
		AJAXResult result = new AJAXResult();
        // 1����ȡĳ���ļ����ڷ������ϵ���ʵ·��
        ServletContext context = session.getServletContext();
       
        // 2��ʹ��context�����ȡ��ʵ·��
        String adPath = context.getRealPath("/adv");
        System.out.println(adPath);
        // 3�����ļ��ϴ������λ��
        String filename = UUID.randomUUID().toString().replace("-", "").substring(0, 10) + "_file_"
                + file.getOriginalFilename();
        try {
            // �ļ��ϴ��������λ��
            String netUrl = "adv/" + filename;
            file.transferTo(new File(adPath + "/" + filename));
            TAdvertisement advertisement = new TAdvertisement();
            advertisement.setName(name);
            advertisement.setUrl(netUrl);
            advertisement.setUserid(((TUser)session.getAttribute("loginUser")).getId());
            // ���浽���ݿ���
            boolean flag = advServiceImpl.addAdver(advertisement);
            if (flag) {
            	result.setSuccess(true);
            } else {
            	result.setSuccess(false);
            }
        } catch (IOException e) {
            System.out.println("����ϴ��쳣��"+e);
            result.setSuccess(false);
        }
        return result;
		
    }
	/**
	 * �첽��ҳ��ѯ,��json��ʽ��������
	 * 
	 * @param queryText
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String queryText, Integer pageno, Integer pagesize) {
		AJAXResult result = new AJAXResult();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("start", (pageno - 1) * pagesize);
			map.put("size", pagesize);
			map.put("queryText", queryText);
			List<TAdvertisement> advs = advServiceImpl.pageQueryData(map);
			// ��ҳ��
			int totalsize = advServiceImpl.pageQueryCount(map);
			// ��ҳ��
			int totalno = ((totalsize % pagesize) == 0) ? (totalsize / pagesize) : (totalsize / pagesize) + 1;
			// ��ҳ����
			Page<TAdvertisement> advPage = new Page<>();
			advPage.setDatas(advs);
			advPage.setPageno(pageno);
			advPage.setTotalno(totalno);
			advPage.setTotalsize(totalsize);
			result.setData(advPage);

		} catch (Exception e) {
			e.printStackTrace();
			// ��ѯʧ��
			result.setSuccess(false);
		}
		result.setSuccess(true);
		return result;
	}
	
	
	
}
