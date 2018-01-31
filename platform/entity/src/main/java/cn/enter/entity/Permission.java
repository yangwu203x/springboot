package cn.enter.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 权限实体类;
 * @author Angel(QQ:412887952)
 * @version v.0.1
 */
@Entity
@Table(name = "sys_permission")
public class Permission implements Serializable{
	private static final long serialVersionUID = -5770620814570447286L;

	@Id @GeneratedValue
	private long id;//主键.
	private String name;//名称.
	
	@Column(columnDefinition="enum('menu','button')")
	private String resourceType;//资源类型，[menu|button]
	private String url;//资源路径.
	private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    private Long parentId; //父编号
    private String parentIds; //父编号列表
    private Boolean available = Boolean.FALSE;
    private String icon;//图标
	@Transient
	private Set<Permission> subPermission;

    
    @ManyToMany
	@JoinTable(name="sys_role_permission",joinColumns={@JoinColumn(name="permissionId")},inverseJoinColumns={@JoinColumn(name="roleId")})
    private List<Role> roles;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Set<Permission> getSubPermission() {
		return subPermission;
	}
	public void setSubPermission(Set<Permission> subPermission) {
		this.subPermission = subPermission;
	}

	@Override
	public String toString() {
		return "Permission{" +
				"id=" + id +
				", name='" + name + '\'' +
				", resourceType='" + resourceType + '\'' +
				", url='" + url + '\'' +
				", permission='" + permission + '\'' +
				", parentId=" + parentId +
				", parentIds='" + parentIds + '\'' +
				", available=" + available +
				", icon='" + icon + '\'' +
				'}';
	}

	/**
	 * 递归解析权限
	 */
	public static Set<Permission> resolvePermission(Set<Permission> permissionSet,Long parentPermissionId){
		Set<Permission> subPermissionSet = new HashSet<>();
		Iterator<Permission> it = permissionSet.iterator();
		while (it.hasNext()){
			Permission subPermission = it.next();
			if(subPermission.getParentId().equals(parentPermissionId)){
				//递归解析菜单
				Set<Permission> menus = resolvePermission(permissionSet, subPermission.getId());
				//保存已经解析的子菜单
				subPermission.setSubPermission(menus);
				//保存父菜单
				subPermissionSet.add(subPermission);
			}
		}
		return subPermissionSet;
	}
}
