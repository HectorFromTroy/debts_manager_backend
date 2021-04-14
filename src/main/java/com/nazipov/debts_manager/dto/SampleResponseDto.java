package com.nazipov.debts_manager.dto;

public class SampleResponseDto <DataType> {
    // response status
    private boolean status;
    // type of data to return
    private DataType data;
    // request processing error message
    private String error;

    public SampleResponseDto() {}

    SampleResponseDto (boolean s, DataType d, String e) {
        status = s;
        data = d;
        error = e;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean s) {
        status = s;
    }

    public DataType getData() {
        return data;
    }

    public void setData(DataType d) {
        data = d;
    }

    public String getError() {
        return error;
    }

    public void setError(String e) {
        error = e;
    }

    public static class Builder <DataType> {
        private boolean status;
        private DataType data;
        private String error;

        public Builder<DataType> setStatus(boolean s) {
            status = s;
            return this;
        }

        public Builder<DataType> setData(DataType d) {
            data = d;
            return this;
        }

        public Builder<DataType> setError(String e) {
            error = e;
            return this;
        }

        public SampleResponseDto<DataType> build() {
            return new SampleResponseDto<>(status, data, error);
        }
    }
}
