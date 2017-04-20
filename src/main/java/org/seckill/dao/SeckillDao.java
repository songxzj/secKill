package org.seckill.dao;


import org.seckill.dao.model.SeckillModel;

import java.util.List;


public interface SeckillDao {
	
	//减库存
	int reduceNumber(SeckillModel Seckill);
	
	//根据id查询秒杀对象
	SeckillModel queryById(long seckillId);
	

}
