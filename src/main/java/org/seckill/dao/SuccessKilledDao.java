package org.seckill.dao;


import org.seckill.dao.model.SuccessKilledModel;

public interface SuccessKilledDao {
	
	//插入购买明细，可过滤重复
	int insertSuccessKilled(SuccessKilledModel seckillId);
	
	//根据id查询SuccessKilled并携带秒杀产品对象实体
	SuccessKilledModel queryByIdWithSeckill(long seckillId);

}
