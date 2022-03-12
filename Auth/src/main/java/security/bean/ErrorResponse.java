package security.bean;

public class ErrorResponse extends BaseResponse {

	private String message;

	public ErrorResponse(String message) {
		this.ok = false;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
}
