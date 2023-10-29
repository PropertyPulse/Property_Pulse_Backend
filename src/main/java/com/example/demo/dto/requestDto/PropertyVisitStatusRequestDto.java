package com.example.demo.dto.requestDto;

public class PropertyVisitStatusRequestDto {
    private int propertyId;
    private boolean visitStatus;

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public boolean isVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(boolean visitStatus) {
        this.visitStatus = visitStatus;
    }
}
