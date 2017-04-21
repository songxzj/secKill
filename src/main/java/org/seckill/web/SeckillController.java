package org.seckill.web;

import org.seckill.dao.model.SeckillModel;
import org.seckill.service.SeckillService;
import org.seckill.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sec")
public class SeckillController {
	
	@Autowired
	private SeckillService seckillService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public SeckillModel list(){
		/*String from = "songxu@ewell.cc";
		String to = "928277484@qq.com";
		String subject = "hello！";
		String text = "你好！";
		MailUtil.sendEmail(from, to, subject, text);*/
		
		SeckillModel seckill = seckillService.getById(1000);
		return seckill;


	}

}