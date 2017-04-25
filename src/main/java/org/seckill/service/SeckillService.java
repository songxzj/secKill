package org.seckill.service;


import org.seckill.dao.model.SeckillModel;


public interface SeckillService {

	SeckillModel getById(long seckillId) throws Exception;

	 void showSeckill(long seckillId, int num);
	

}
