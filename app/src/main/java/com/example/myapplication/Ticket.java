package com.example.myapplication;

import java.util.List;

public class Ticket {

    private String ticketHeading;
    private List<String> mStringListOption;

    public String getTicketHeading() {
        return ticketHeading;
    }

    public void setTicketHeading(String ticketHeading) {
        this.ticketHeading = ticketHeading;
    }

    public List<String> getStringListOption() {
        return mStringListOption;
    }

    public void setStringListOption(List<String> stringListOption) {
        mStringListOption = stringListOption;
    }


}
