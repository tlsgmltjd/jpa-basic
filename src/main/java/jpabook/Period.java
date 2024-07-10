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

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Period(LocalDateTime stateDate, LocalDateTime endDate) {
        this.stateDate = stateDate;
        this.endDate = endDate;
    }

    public Period() {
    }
}
