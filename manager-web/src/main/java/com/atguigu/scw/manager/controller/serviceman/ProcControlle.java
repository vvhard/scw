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
     * produces="text/html;charset=utf-8"相当于给响应头加Content-Type能解决乱码
     * @Description (TODO这里用一句话描述这个方法的作用)
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
	 * 异步分页查询,以json形式返回数据
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
        //将这个对象需要在页面展示的属性提取出来
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        System.out.println(list.size());
        ProcessDefinition processDefinition = null;

		try {
	        // 全部查询后，伪分页
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
			// 总页数
			int totalsize = list.size();
			// 总页数
			int totalno = ((totalsize % pagesize) == 0) ? (totalsize / pagesize) : (totalsize / pagesize) + 1;
			// 分页对象
			Page<Map<String, Object>> procPage = new Page<>();
			procPage.setDatas(pd);
			procPage.setPageno(pageno);
			procPage.setTotalno(totalno);
			procPage.setTotalsize(totalsize);
			result.setData(procPage);
			result.setSuccess(true);

		} catch (Exception e) {
			// 查询失败
			System.out.println(e);
			result.setSuccess(false);
		}
		
		return result;
	}
   
    
    @RequestMapping("/list")
    public String list(){       
        return "manager/serviceman/process";
    }
    
    //使用部署id能获取到流程图；
    //按照流程id找到部署id，按照部署id查询流程的二进制信息
    @ResponseBody
    @RequestMapping(value="/process.jpg")
    public byte[] getProImg(@RequestParam("id")String processDefinetionId) throws Exception{
        //1、拿到流程定义
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
            .processDefinitionId(processDefinetionId)
            .singleResult();
        //2、拿到部署id
        String deploymentId = definition.getDeploymentId();
        //按照部署id查出部署信息
        List<String> list = repositoryService.getDeploymentResourceNames(deploymentId);
        String pngName = "";
        //拿到流程图片的名字
        for (String string : list) {
            if(string.endsWith(".png")){
                pngName = string;
            }
        }
        
        //流程定义--一一对应----部署信息---按照部署查出资源
        //3、按照部署id和图片名查出这个图片
        InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, pngName);
        byte[] bs = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return bs;
    }
}
