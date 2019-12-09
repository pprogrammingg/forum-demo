package forum.document;

import java.util.Date;

public class Posting {
	
	public String id;
	public String messageBody;
	public String userFirstName;
	public String userLastName;
	public Date createDateTime;
	
	/* Original Post Id is used for postings that are comments. 
	If null means that posting is original and not a comment. */
	public String originalPostingId;

	public Posting() {
		
	}
	
	public Posting(String messageBody, Date createDateTime) {
		super();
		this.messageBody = messageBody;
		this.createDateTime = createDateTime;
	}
	
	public Posting(String messageBody) {
		this.messageBody = messageBody;
	}

	public Posting(String messageBody, String userFirstName, String userLastName, Date createDateTime) {
		super();
		this.messageBody = messageBody;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.createDateTime = createDateTime;
	}
	
	public Posting(String messageBody, String userFirstName, String userLastName, Date createDateTime, String originalPostingId) {
		super();
		this.messageBody = messageBody;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.createDateTime = createDateTime;
		this.originalPostingId = originalPostingId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	
	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
  }
 
	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getOriginalPostId() {
		return originalPostingId;
	}

	public void setOriginalPostId(String originalPostId) {
		this.originalPostingId = originalPostId;
	}

	@Override
	public String toString() {
		return String.format(
			"Posting[id=%s, messageBody='%s', userFirstName='%', userLastName='%', createDateTime='%s', 'originalPostingId'='%s']",
			id, messageBody, userFirstName, userLastName, createDateTime.toString(), originalPostingId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createDateTime == null) ? 0 : createDateTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((messageBody == null) ? 0 : messageBody.hashCode());
		result = prime * result + ((originalPostingId == null) ? 0 : originalPostingId.hashCode());
		result = prime * result + ((userFirstName == null) ? 0 : userFirstName.hashCode());
		result = prime * result + ((userLastName == null) ? 0 : userLastName.hashCode());
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
		Posting other = (Posting) obj;
		if (createDateTime == null) {
			if (other.createDateTime != null)
				return false;
		} else if (!createDateTime.equals(other.createDateTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (messageBody == null) {
			if (other.messageBody != null)
				return false;
		} else if (!messageBody.equals(other.messageBody))
			return false;
		if (originalPostingId == null) {
			if (other.originalPostingId != null)
				return false;
		} else if (!originalPostingId.equals(other.originalPostingId))
			return false;
		if (userFirstName == null) {
			if (other.userFirstName != null)
				return false;
		} else if (!userFirstName.equals(other.userFirstName))
			return false;
		if (userLastName == null) {
			if (other.userLastName != null)
				return false;
		} else if (!userLastName.equals(other.userLastName))
			return false;
		return true;
	}
}