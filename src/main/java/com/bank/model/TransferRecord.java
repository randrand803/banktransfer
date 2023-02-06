package com.bank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransferRecord {

    @ApiModelProperty(value = "Transaction ID")
    private int id;

    @ApiModelProperty(value = "User ID")
    private int userid;

    @ApiModelProperty(value = "holdMoney")
    private double holdMoney;

    @ApiModelProperty(value = "EURO,USD")
    private String type;

    @ApiModelProperty(value = "equivalentRMB")
    private double holdMoneyRMB;

    @ApiModelProperty(value = "bank account number")
    private String bankAccount;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "transaction date")
    private Date transferTime;

    @ApiModelProperty(value = "description")
    private String description;

    public TransferRecord(){}

    public TransferRecord(int id, int userid, double holdMoney, String type, double holdMoneyRMB, String bankAccount, String transferTime, String description) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.id = id;
        this.userid = userid;
        this.holdMoney = holdMoney;
        this.type = type;
        this.holdMoneyRMB = holdMoneyRMB;
        this.bankAccount = bankAccount;
        try {
            this.transferTime = format.parse(transferTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public double getHoldMoney() {
        return holdMoney;
    }

    public void setHoldMoney(double holdMoney) {
        this.holdMoney = holdMoney;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getHoldMoneyRMB() {
        return holdMoneyRMB;
    }

    public void setHoldMoneyRMB(double holdMoneyRMB) {
        this.holdMoneyRMB = holdMoneyRMB;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return id +
                "," + userid +
                "," + holdMoney +
                "," + type +
                "," + holdMoneyRMB +
                "," + bankAccount +
                "," + format.format(transferTime) +
                "," + description;
    }
}
