package net.serg.currencyraterequestsprocessor.model;

public enum RateType {
    ECB("ecb");

    String serviceName;

    RateType(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
