package jpabook;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class Period {
    private LocalDateTime stateDate;
    private LocalDateTime endDate;

    public LocalDateTime getStateDate() {
        return stateDate;
    }

    public void setStateDate(LocalDateTime stateDate) {
        this.stateDate = stateDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
