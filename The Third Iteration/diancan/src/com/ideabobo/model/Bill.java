package com.ideabobo.model;

/**
 * Bill entity. @author MyEclipse Persistence Tools
 */

public class Bill implements java.io.Serializable {

	// Fields

	private Integer id;
	private String gids;
	private String pirce;
	private String user;
	private String uid;
	private String shop;
	private String bill;
	private String openid;
	private String ndate;
	private String total;
	private String way;
	private String gnames;
	private String sid;
    private String tel;
    private String address;
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    // Constructors

	public String getSid() {
		return sid;
	}

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setSid(String sid) {
		this.sid = sid;
	}

	/** default constructor */
	public Bill() {
	}

	/** full constructor */
	public Bill(String gids, String pirce, String user, String uid,
			String shop, String bill, String openid, String ndate,
			String total, String way, String gnames) {
		this.gids = gids;
		this.pirce = pirce;
		this.user = user;
		this.uid = uid;
		this.shop = shop;
		this.bill = bill;
		this.openid = openid;
		this.ndate = ndate;
		this.total = total;
		this.way = way;
		this.gnames = gnames;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGids() {
		return this.gids;
	}

	public void setGids(String gids) {
		this.gids = gids;
	}

	public String getPirce() {
		return this.pirce;
	}

	public void setPirce(String pirce) {
		this.pirce = pirce;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getShop() {
		return this.shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public String getBill() {
		return this.bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNdate() {
		return this.ndate;
	}

	public void setNdate(String ndate) {
		this.ndate = ndate;
	}

	public String getTotal() {
		return this.total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getWay() {
		return this.way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getGnames() {
		return this.gnames;
	}

	public void setGnames(String gnames) {
		this.gnames = gnames;
	}

}