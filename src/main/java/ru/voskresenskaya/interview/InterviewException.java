package ru.voskresenskaya.interview;

public class InterviewException extends RuntimeException {
    private Long errorCode;

    public InterviewException(String errorMessage) {
        super(errorMessage);
    }

    public InterviewException(Long errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public InterviewException(Long errorCode, String errorMessage, Exception e) {
        super(errorMessage, e);
        this.errorCode = errorCode;
    }

    public Long getErrorCode() {
        return errorCode;
    }
}
