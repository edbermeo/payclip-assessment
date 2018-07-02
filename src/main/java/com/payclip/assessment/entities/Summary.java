package com.payclip.assessment.entities;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Summary {

    double sum;
    @JsonProperty(value = "user_id")
    Long userId;

    public Summary(Long userId, double sum) {
        this.sum = sum;
        this.userId = userId;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
