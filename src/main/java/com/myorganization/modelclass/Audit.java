package com.myorganization.modelclass;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="audit")
public class Audit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="fieldname")
	private String fieldName;
	@Column(name="oldvalue")
	private String oldValue;
	@Column(name="newvalue")
	private String newValue;
	@Column(name="parenttable")
	private String parentTable;
	@Column(name="updatedby")
	private String updatedBy;
	@Column(name="deleted")
	private Integer deleted;
	@Column(name="updatedat")
	private LocalDateTime  updatedAt;
	@Column(name="parentid")
	private String parentId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getParentTable() {
		return parentTable;
	}
	public void setParentTable(String parentTable) {
		this.parentTable = parentTable;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@Override
	public String toString() {
		return "Audit [id=" + id + ", fieldName=" + fieldName + ", oldValue=" + oldValue + ", newValue=" + newValue
				+ ", parentTable=" + parentTable + ", updatedBy=" + updatedBy + ", deleted=" + deleted + ", updatedAt="
				+ updatedAt + "]";
	}
	
	
	

}
