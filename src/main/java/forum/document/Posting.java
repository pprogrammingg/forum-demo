package forum.document;

public class Posting {
	private String messageBody;

	public Posting() {
		
	}
	
	public Posting(String messageBody) {
		super();
		this.messageBody = messageBody;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	@Override
	public String toString() {
		return "Posting [messageBody=" + messageBody + "]";
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
