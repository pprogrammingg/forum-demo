package forum.document;

import java.util.Date;

public class Posting {
	
	public String id;
	public String messageBody;
	public Date createDateTime;

	public Posting() {
		
	}
	
	public Posting(String messageBody, Date createDateTime) {
		super();
		this.messageBody = messageBody;
		this.createDateTime = createDateTime;
	}
	
	public Posting(String messageBody) {
		super();
		this.messageBody = messageBody;
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

	@Override
	public String toString() {
		return String.format(
				"Customer[id=%s, messageBody='%s', createDateTime='%s']",
				id, messageBody, createDateTime.toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageBody == null) ? 0 : messageBody.hashCode());
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
		if (messageBody == null) {
			if (other.messageBody != null)
				return false;
		} else if (!messageBody.equals(other.messageBody))
			return false;
		return true;
	}
}
