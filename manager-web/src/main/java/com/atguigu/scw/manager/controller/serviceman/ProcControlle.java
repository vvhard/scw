package com.atguigu.scw.manager.controller.serviceman;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.atguigu.scw.manager.bean.tool.AJAXResult;
import com.atguigu.scw.manager.bean.tool.Page;

@Controller
@RequestMapping("/serviceman/proc")
public class ProcControlle {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private TaskService taskService;
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
    public Object upload( @RequestParam("proc_file")MultipartFile file) {
		AJAXResult result = new AJAXResult();
        try {
            Deployment deploy = repositoryService.createDeployment()
                .addInputStream(file.getOriginalFilename(), file.getInputStream())
                .deploy();
            result.setSuccess(true);
        } catch (Exception e) {
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
        List<Map<String, Object>> pd = new ArrayList<Map<String,Object>>();
        //�����������Ҫ��ҳ��չʾ��������ȡ����
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        System.out.println(list.size());
        ProcessDefinition processDefinition = null;

		try {
	        // ȫ����ѯ��α��ҳ
	        for(int i = (pageno-1)*pagesize;i < list.size() ;i++) {
	        	if(i < pageno * pagesize) {
		        	processDefinition = list.get(i);
		        	System.out.println(processDefinition + ":" + i);
		        	Map<String, Object> values = new HashMap<String, Object>();
		            values.put("id", processDefinition.getId());
		            values.put("name", processDefinition.getName());
		            values.put("key", processDefinition.getKey());
		            values.put("version", processDefinition.getVersion());
		            pd.add(values);
		            continue;
	        	}
	        	break;

	        }
			// ��ҳ��
			int totalsize = list.size();
			// ��ҳ��
			int totalno = ((totalsize % pagesize) == 0) ? (totalsize / pagesize) : (totalsize / pagesize) + 1;
			// ��ҳ����
			Page<Map<String, Object>> procPage = new Page<>();
			procPage.setDatas(pd);
			procPage.setPageno(pageno);
			procPage.setTotalno(totalno);
			procPage.setTotalsize(totalsize);
			result.setData(procPage);
			result.setSuccess(true);

		} catch (Exception e) {
			// ��ѯʧ��
			System.out.println(e);
			result.setSuccess(false);
		}
		
		return result;
	}
   
    
    @RequestMapping("/list")
    public String list(){       
        return "manager/serviceman/process";
    }
    
    //ʹ�ò���id�ܻ�ȡ������ͼ��
    //��������id�ҵ�����id�����ղ���id��ѯ���̵Ķ�������Ϣ
    @ResponseBody
    @RequestMapping(value="/process.jpg")
    public byte[] getProImg(@RequestParam("id")String processDefinetionId) throws Exception{
        //1���õ����̶���
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
            .processDefinitionId(processDefinetionId)
            .singleResult();
        //2���õ�����id
        String deploymentId = definition.getDeploymentId();
        //���ղ���id���������Ϣ
        List<String> list = repositoryService.getDeploymentResourceNames(deploymentId);
        String pngName = "";
        //�õ�����ͼƬ������
        for (String string : list) {
            if(string.endsWith(".png")){
                pngName = string;
            }
        }
        
        //���̶���--һһ��Ӧ----������Ϣ---���ղ�������Դ
        //3�����ղ���id��ͼƬ��������ͼƬ
        InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, pngName);
        byte[] bs = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return bs;
    }
}
