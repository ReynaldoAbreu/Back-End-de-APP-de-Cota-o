package com.bootcamp.bootcamp.exception;

import com.bootcamp.bootcamp.utill.MessageUtill;

public class NotFoundException extends RuntimeException {
    public NotFoundException(){
        super(MessageUtill.NO_RECORDS_FOUND);
    }
}
