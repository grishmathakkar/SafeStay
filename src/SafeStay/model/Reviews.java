package SafeStay.model;

public class Reviews {
	protected int ReviewId;
	protected String content;
	protected EndUsers endusers;
	protected Address address;

	public Reviews(int ReviewId, String content, EndUsers endusers, Address address) {
		this.ReviewId = ReviewId;
		this.content = content;
		this.endusers = endusers;
		this.address = address;
	}

	public Reviews(String content, EndUsers endusers, Address address) {
		this.content = content;
		this.endusers = endusers;
		this.address = address;
	}

	public Reviews(int reviewId) {
		this.ReviewId = reviewId;
	}

	public int getReviewId() {
		return ReviewId;
	}

	public void setReviewId(int reviewId) {
		this.ReviewId = reviewId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public EndUsers getEndusers() {
		return endusers;
	}

	public void setEndusers(EndUsers endusers) {
		this.endusers = endusers;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
