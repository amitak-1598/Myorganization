package com.myorganization.service;

import java.time.LocalDateTime;

import com.myorganization.modelclass.Audit;

import io.micronaut.context.annotation.Factory;

@Factory
public class AuditField {

	String parentid;
	String modifiedby;
	String parenttable;

	public void setInitials(String parentid, String parenttable, String modifiedby) {

		this.parentid = parentid;
		this.modifiedby = modifiedby;
		this.parenttable = parenttable;
	}

	public Audit genAudit(String newValue, String oldvalue, String fieldname) {

		Audit audit = new Audit();
		audit.setNewValue(newValue);
		audit.setOldValue(oldvalue);
		audit.setFieldName(fieldname);
		audit.setParentId(parentid);
		audit.setParentTable(parenttable);
		audit.setUpdatedBy(modifiedby);
		audit.setDeleted(0);
		audit.setUpdatedAt(LocalDateTime.now());
		return audit;
	}

}
