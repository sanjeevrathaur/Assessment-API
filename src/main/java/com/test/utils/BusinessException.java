package com.test.utils;

/**
 * This class is used for display any business Exception.
 * @author Sanjeev
 *
 * */
public class BusinessException extends Exception {

    public BusinessException(){
        super("Some unknown error occur. Please contact dev team.");
    }

    public BusinessException(String message){
        super(message);
    }


}
