package com.parking.dto;

import java.util.List;

public class ResponseDTO<T> extends DataTransferObject {
    
    private  boolean ok;
    private  T  result;
    private  List<T> items;
    
    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public ResponseDTO() {
        //this.setResult("Success");
        //this.result = "Success";
        this.ok = true;
    }

    public ResponseDTO(T result) {
        this.result = result;
        this.ok = true;
    }    

    public ResponseDTO(List<T> items) {
        this.items = items;
        this.ok = true;
    }

    public ResponseDTO(T result, boolean allOk) {
        this.result = result;
        this.ok = allOk;
    }

    public ResponseDTO(List<T> items, boolean allOk) {
        this.items = items;
        this.ok = allOk;
    }
}