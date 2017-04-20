package org.seckill.service.impl;


import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;

import org.seckill.dao.model.SeckillModel;
import org.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeckillServiceImpl implements SeckillService {
	
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKillDao;

	@Transactional
	public SeckillModel getById(long seckillId) {
		
		return seckillDao.queryById(seckillId);
		
	}

}
