package com.pzy.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "t_Payfee")
public class Payfee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date paydate;
	
	private Double fee;
	
	 @ManyToOne
	private Tran tran;
	 
	private String paytype;
	
	private String num;
	
	private String user;
	
	private String remark;
	
	private String remark1;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPaydate() {
		return paydate;
	}

	public void setPaydate(Date paydate) {
		this.paydate = paydate;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Tran getTran() {
		return tran;
	}

	public void setTran(Tran tran) {
		this.tran = tran;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
