package exception;

public class RedirectException extends RuntimeException {
	private String url;
	public RedirectException(String msg, String url) {
		super(msg);
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	
}
