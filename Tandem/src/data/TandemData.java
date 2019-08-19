package data;

import java.util.Date;

public class TandemData {

	private String ncStatus;
	
	private Integer MSN;
	
	private String constituentAssembly;

	private String natcoType;
	
	private String reference;
	
	private String description;
	
	private String escalationPriority;
	
	private String escalationStatus;
	
	private String comment;
	
	private String oldConversation;
	
	private String conversation;
	
	private String myComment;
	
	private String detailedComment;
	
	private Date deadlineDate;
	
	private String tag;
	
	private String Status;
	
	public TandemData() {
		
	}

	public String getNcStatus() {
		return ncStatus;
	}

	public void setNcStatus(String ncStatus) {
		this.ncStatus = ncStatus;
	}

	public Integer getMSN() {
		return MSN;
	}

	public void setMSN(Integer mSN) {
		MSN = mSN;
	}

	public String getConstituentAssembly() {
		return constituentAssembly;
	}

	public void setConstituentAssembly(String constituentAssembly) {
		this.constituentAssembly = constituentAssembly;
	}

	public String getNatcoType() {
		return natcoType;
	}

	public void setNatcoType(String natcoType) {
		this.natcoType = natcoType;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEscalationPriority() {
		return escalationPriority;
	}

	public void setEscalationPriority(String escalationPriority) {
		this.escalationPriority = escalationPriority;
	}

	public String getEscalationStatus() {
		return escalationStatus;
	}

	public void setEscalationStatus(String escalationStatus) {
		this.escalationStatus = escalationStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getConversation() {
		return conversation;
	}

	public void setConversation(String conversation) {
		this.conversation = conversation;
	}

	public Date getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(Date deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	
	public String getOldConversation() {
		return oldConversation;
	}

	public void setOldConversation(String oldConversation) {
		this.oldConversation = oldConversation;
	}
	

	public String getMyComment() {
		return myComment;
	}

	public void setMyComment(String myComment) {
		this.myComment = myComment;
	}

	public String getDetailedComment() {
		return detailedComment;
	}

	public void setDetailedComment(String detailedComment) {
		this.detailedComment = detailedComment;
	}
	

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((MSN == null) ? 0 : MSN.hashCode());
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TandemData other = (TandemData) obj;
		if (MSN == null) {
			if (other.MSN != null)
				return false;
		} else if (!MSN.equals(other.MSN))
			return false;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TandemData [natcoType=" + natcoType + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
