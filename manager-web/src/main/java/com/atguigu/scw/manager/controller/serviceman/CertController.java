package com.atguigu.scw.manager.controller.serviceman;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.scw.manager.bean.TAccountTypeCert;
import com.atguigu.scw.manager.bean.TAcctType;
import com.atguigu.scw.manager.bean.TCert;
import com.atguigu.scw.manager.bean.tool.AJAXResult;
import com.atguigu.scw.manager.bean.tool.Page;
import com.atguigu.scw.manager.service.TAcctTypeService;
import com.atguigu.scw.manager.service.TCertService;

@Controller
@RequestMapping("/serviceman/cert")
public class CertController {

	@Autowired
	private TCertService tCertServiceImpl;
	@Autowired
	private TAcctTypeService tAcctTypeServiceImpl;

	@RequestMapping("/list")
	public String list() {
		return "manager/serviceman/cert";
	}

	@RequestMapping("/type_list")
	public String typeList(Model model) {
		List<TCert> certs = tCertServiceImpl.getAllCerts();
		List<TAcctType> acctTypes = tAcctTypeServiceImpl.getAllAcctTypes();
		// 初始化设置每种账户类型拥有的资质都为空，设置为false
		for (TAcctType tAcctType : acctTypes) {
			// 查询账户类型需要的资质
			List<TCert> list = tAcctTypeServiceImpl.getAcctCertsByName(tAcctType.getName());
			for (TCert tCert : certs) {
				boolean flag = false; // 标记是否需要资质
				for (TCert cert : list) {
					if (cert.getName().equals(tCert.getName())) {
						flag = true;
						break;
					}
				}
				if (flag)
					tAcctType.getCertList().add(true);
				else
					tAcctType.getCertList().add(false);
			}
		}
		model.addAttribute("certs", certs);
		model.addAttribute("acctTypes", acctTypes);

		return "manager/serviceman/type";
	}

	@ResponseBody
	@RequestMapping("/type_update")
	public Object typeUpdate(int accttypeid,int[] certids) {
		AJAXResult result = new AJAXResult();
		try {
			tAcctTypeServiceImpl.deleteAcctCertById(accttypeid);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			return result;
		}
		for (int certid : certids) {
			TAccountTypeCert tact = new TAccountTypeCert();
			tact.setAccttype(accttypeid);
			tact.setCertid(certid);
			try {
				tAcctTypeServiceImpl.upadteAcctCert(tact);
			} catch (Exception e) {
				result.setSuccess(false);
				e.printStackTrace();
				return result;
			}
		}
		result.setSuccess(true);
		return result;
	}

	@RequestMapping("/add")
	public String add() {
		return "manager/serviceman/cert_add";
	}

	@ResponseBody
	@RequestMapping("/addCert")
	public Object addCert(TCert cert) {
		AJAXResult result = new AJAXResult();
		if (tCertServiceImpl.addCert(cert) == 1)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
	}

	@RequestMapping("/edit")
	public String edit(int certid, Model model) {
		model.addAttribute("cert", tCertServiceImpl.getCertById(certid));
		return "manager/serviceman/cert_edit";
	}

	@ResponseBody
	@RequestMapping("/save")
	public Object save(TCert cert) {
		AJAXResult result = new AJAXResult();
		if (tCertServiceImpl.updateCert(cert) == 1)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(int certid) {
		AJAXResult result = new AJAXResult();
		if (tCertServiceImpl.deleteCertById(certid) >= 1)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
	}

	@ResponseBody
	@RequestMapping("/deletes")
	public Object deletes(int[] certid) {
		AJAXResult result = new AJAXResult();
		if (tCertServiceImpl.deleteCertBatch(certid) != 0)
			result.setSuccess(true);
		else
			result.setSuccess(false);
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
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("start", (pageno - 1) * pagesize);
			map.put("size", pagesize);
			map.put("queryText", queryText);
			List<TCert> certs = tCertServiceImpl.pageQueryData(map);
			// 总页数
			int totalsize = tCertServiceImpl.pageQueryCount(map);
			// 总页数
			int totalno = ((totalsize % pagesize) == 0) ? (totalsize / pagesize) : (totalsize / pagesize) + 1;
			// 分页对象
			Page<TCert> certPage = new Page<>();
			certPage.setDatas(certs);
			certPage.setPageno(pageno);
			certPage.setTotalno(totalno);
			certPage.setTotalsize(totalsize);
			result.setData(certPage);

		} catch (Exception e) {
			e.printStackTrace();
			// 查询失败
			result.setSuccess(false);
		}
		result.setSuccess(true);
		return result;
	}
}
