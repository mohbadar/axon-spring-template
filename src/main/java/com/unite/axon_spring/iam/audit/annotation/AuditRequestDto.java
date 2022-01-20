package com.unite.axon_spring.iam.audit.annotation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * The Audit Request class with required fields to be captured and recorded
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuditRequestDto {

	@NotNull
	@Size(min = 1, max = 64)
	private String eventId;

	@NotNull
	@Size(min = 1, max = 128)
	private String eventName;

	@NotNull
	@Size(min = 1, max = 64)
	private String eventType;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd'T'HH:mm:ss.SSS'Z'")
	private LocalDateTime actionTimeStamp;

	@NotNull
	@Size(min = 1, max = 128)
	private String hostName;

	@NotNull
	@Size(min = 1, max = 16)
	private String hostIp;

	@NotNull
	@Size(min = 1, max = 64)
	private String applicationId;

	@NotNull
	@Size(min = 1, max = 128)
	private String applicationName;

	@NotNull
	@Size(min = 1, max = 256)
	private String sessionUserId;

	@Size(min = 1, max = 128)
	private String sessionUserName;

	@NotNull
	@Size(min = 1, max = 64)

	private String id;
	@NotNull
	@Size(min = 1, max = 64)
	private String idType;

	@NotNull
	@Size(min = 1, max = 256)
	private String createdBy;

	@Size(max = 128)
	private String moduleName;

	@Size(max = 64)
	private String moduleId;

	@Size(max = 2048)
	private String description;


	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public LocalDateTime getActionTimeStamp() {
		return actionTimeStamp;
	}

	public void setActionTimeStamp(LocalDateTime actionTimeStamp) {
		this.actionTimeStamp = actionTimeStamp;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getSessionUserId() {
		return sessionUserId;
	}

	public void setSessionUserId(String sessionUserId) {
		this.sessionUserId = sessionUserId;
	}

	public String getSessionUserName() {
		return sessionUserName;
	}

	public void setSessionUserName(String sessionUserName) {
		this.sessionUserName = sessionUserName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
