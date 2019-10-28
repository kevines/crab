package com.wentuo.crab.core.wx;

public class NoAuthorizedException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4177302412005925937L;

	public NoAuthorizedException(){
		super("用户未授权");
	}
}
