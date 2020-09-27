package com.pj.jwt.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
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
	@JsonBackReference
	private Set<User> users = new HashSet<>();

	public Role()
	{
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Role role = (Role) o;
		return id.equals(role.id) &&
				name.equals(role.name);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id, name);
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
