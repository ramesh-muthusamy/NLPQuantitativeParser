package com.isb.ta.core.dto;


public class EvaluationBean {

    private String owner;
    private String content;
    private Double quantity;
    private String verb;

    public EvaluationBean(String owner){
        this.owner=owner;
    }
    public EvaluationBean(String owner,String verb){
        this(owner);
        this.verb=verb;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }
}
