package ch.bujaki.resilience4jdemo;

/**
 * Wrapper for {@link String} responses.
 */
class Response {

	private final String content;

	/**
	 * Constructor.
	 * 
	 * @param content
	 *         The content of the {@link Response}.
	 */
	public Response(String content) {
		this.content = content;
	}

	/**
	 * @return the content of the {@link Response} (<code>null</code> in case of failure).
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * @return true, if the content is null.
	 */
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
