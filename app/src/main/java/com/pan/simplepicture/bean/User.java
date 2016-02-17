package com.pan.simplepicture.bean;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 962690267020203971L;
	public String location;
	public String gender;
	public String screen_name;
	public String profile_image_url;
	public User(String location, String gender, String screen_name,
			String profile_image_url) {
		super();
		this.location = location;
		this.gender = gender;
		this.screen_name = screen_name;
		this.profile_image_url = profile_image_url;
	}
	public User(){}
}
