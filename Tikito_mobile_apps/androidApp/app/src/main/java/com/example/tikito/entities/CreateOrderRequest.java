package com.example.tikito.entities;

public class CreateOrderRequest
{
    private Double amount;

    private String receipt;

    private String currency;

    public CreateOrderRequest(Double amount, String receipt, String currency)
    {
        this.amount = amount;
        this.receipt = receipt;
        this.currency = currency;
    }

    public CreateOrderRequest()
    {

    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
