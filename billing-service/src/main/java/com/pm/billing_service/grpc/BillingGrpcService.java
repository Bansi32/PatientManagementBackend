package com.pm.billingservice.grpc;

import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(BillingGrpcService.class);
    @Override
    public void createBillingAccout(BillingRequest billingRequest,StreamObserver<BillingResponse> responseObserver)
    {
        log.info("creatingBillingAccount request received {}",billingRequest.toString());
        BillingResponse response = new BillingResponse.newBuilder().setAccountId("12345").setStatus("ACTIVE").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}