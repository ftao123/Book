package com.xinxue.xutils3;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "info")
public class StudentInfo {
	@Column(name = "id", isId = true)
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "age")
	private int age;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
