/**
 * 
 */
package com.abimulia.hotel.sharedkernel.service;

/**
 * 
 */
public class State<T,V> {
	private T value;
	private V error;
	private StatusNotification status;
	
	
	/**
	 * @param value
	 * @param error
	 * @param status
	 */
	public State(StatusNotification status, T value, V error) {
		this.value = value;
		this.error = error;
		this.status = status;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}
	/**
	 * @return the error
	 */
	public V getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(V error) {
		this.error = error;
	}
	/**
	 * @return the status
	 */
	public StatusNotification getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusNotification status) {
		this.status = status;
	}
	
	

}
