package com.pj.jwt.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = "role")
public class Role implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
	private Set<User> users;

	public Role()
	{
	}

	public Role(String name)
	{
		this.name = name;
	}

	public Role(String name, Set<User> users)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "Role{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
