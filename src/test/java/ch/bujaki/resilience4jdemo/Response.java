package ch.bujaki.resilience4jdemo;

public class Response {

	private final String content;

	public Response(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	public boolean hasFailed() {
		return (content == null);
	}

	@Override
	public String toString() {
		if (hasFailed()) {
			return "Response [FAILED]";
		}
		
		return "Response [content=" + content + "]";
	}
	
}
