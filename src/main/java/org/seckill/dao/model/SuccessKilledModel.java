package org.seckill.dao.model;

import java.util.Date;

public class SuccessKilledModel {
	
	private int userId;
	private long seckillId;
	private long userPhone;
	private short state;
	private Date createTime;
	private SeckillModel seckill;
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public long getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}
	public long getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}
	public short getState() {
		return state;
	}
	public void setState(short state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public SeckillModel getSeckill() {
		return seckill;
	}
	public void setSeckill(SeckillModel seckill) {
		this.seckill = seckill;
	}
	
	@Override
	public String toString() {
		return "SuccessKilledModel [userId=" + userId + ", seckillId=" + seckillId + ", userPhone=" + userPhone
				+ ", state=" + state + ", createTime=" + createTime + "]";
	}
	
	
	
	

}
