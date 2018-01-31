package cn.enter.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * 系统角色实体类;
 * @author Angel(QQ:412887952)
 * @version v.0.1
 */
@Entity
@Table(name = "sys_role")
public class Role implements Serializable{
	private static final long serialVersionUID = -894582630204018396L;

	@Id @GeneratedValue
	private Long id; // 编号
	private String role; // 角色标识 程序中判断使用,如"admin",这个是唯一的:
	private String description; // 角色描述,UI界面显示使用
	private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户
	
	//角色 -- 权限关系： 多对多关系;
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="sys_role_permission",joinColumns={@JoinColumn(name="roleId")},inverseJoinColumns={@JoinColumn(name="permissionId")})
	private List<Permission> permissions;
	
	// 用户 - 角色关系定义;
	@ManyToMany
	@JoinTable(name="sys_account_role",joinColumns={@JoinColumn(name="roleId")},inverseJoinColumns={@JoinColumn(name="uid")})
	private List<Account> accounts;// 一个角色对应多个用户
	
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + ", description=" + description + ", available=" + available
				+  "]";
	}
}
