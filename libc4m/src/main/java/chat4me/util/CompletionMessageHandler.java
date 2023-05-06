package chat4me.util;

public interface CompletionMessageHandler {
	public void onReceiveCompletion(int status, String msg);
}
