package com.pj.jwt.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Table(name = "`user`")
public class User
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired;

	@Column(name = "account_non_locked")
	private Boolean accountNonLocked;

	@Column(name = "account_non_expired")
	private Boolean accountNonExpired;

	@Column(name = "password", nullable = false)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
	)
	private Set<Role> roles=new HashSet<>();

	public User()
	{
	}

	public User(String username, String password, Boolean active)
	{
		this.username = username;
		this.password = password;
		this.active = active;
	}

	@Override
	public String toString()
	{
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", active=" + active +
				", password='" + password + '\'' +
				'}';
	}
}
