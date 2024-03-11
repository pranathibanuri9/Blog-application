package com.example.demo.execeptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundExeception  extends RuntimeException{
    String resourceName;
    String fieldName;
    long fieldvalue;

    public ResourceNotFoundExeception(String resourceName,String fieldName,int fieldvalue) {
        super( String.format("%s not found with %s : %s",resourceName,fieldName,fieldvalue));
        this.resourceName=resourceName;
        this.fieldName = fieldName;
        this.fieldvalue=fieldvalue;

    }
}
