package util.web;

/**
 *
 * implements a method launched on response of server
 */
public interface WebContentRequester {
    public abstract void recieveWebContent(String content, int identifier);
}
