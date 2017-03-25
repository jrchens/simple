package me.simple.exception;

public class ServiceException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -6068591514545238824L;

    public ServiceException() {
	super();
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
	// TODO Auto-generated constructor stub
    }

    public ServiceException(String message, Throwable cause) {
	super(message, cause);
    }

    public ServiceException(String message) {
	super(message);
    }

    public ServiceException(Throwable cause) {
	super(cause);
    }

}
