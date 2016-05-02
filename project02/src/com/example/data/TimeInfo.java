package com.example.data;

public class TimeInfo {
	private int id;
	private String date;		// 日期 xx-xx-xx格式
	private String endDate;		// 结课日期
	private int dayType;		// 上午 下午 全天
	private String remark;		// 备注
	
	public TimeInfo(){
		
	}
	
	public TimeInfo(int id,String date,String endDate,int dayType,String remark){
		this.id = id;
		this.date = date;
		this.endDate = endDate;
		this.dayType = dayType;
		this.remark = remark;
	}
	
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the dayType
	 */
	public int getDayType() {
		return dayType;
	}
	/**
	 * @param dayType the dayType to set
	 */
	public void setDayType(int dayType) {
		this.dayType = dayType;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
