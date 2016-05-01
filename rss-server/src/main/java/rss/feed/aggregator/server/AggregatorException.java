package rss.feed.aggregator.server;

public class AggregatorException extends Exception {

	private static final long serialVersionUID = 7701951320240004563L;
	
	public AggregatorException() {
		super();
	}
	
	public AggregatorException(String message) {
		super(message);
	}
	
	public AggregatorException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AggregatorException(Throwable cause) {
		super(cause);
	}

}
