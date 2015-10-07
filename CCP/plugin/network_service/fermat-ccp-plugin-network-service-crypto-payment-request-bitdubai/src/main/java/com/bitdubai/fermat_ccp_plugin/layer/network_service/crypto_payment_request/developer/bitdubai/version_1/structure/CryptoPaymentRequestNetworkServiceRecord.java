package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.CryptoPaymentRequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.CryptoPaymentRequestType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequest;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure.CryptoPaymentRequestNetworkServiceRecord</code>
 * is the representation of a Crypto Payment Request Record in database.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public class CryptoPaymentRequestNetworkServiceRecord implements CryptoPaymentRequest {

    private final UUID                       requestId        ;
    private final String                     identityPublicKey;
    private final Actors                     identityType     ;
    private final String                     actorPublicKey   ;
    private final Actors                     actorType        ;
    private final String                     description      ;
    private final CryptoAddress              cryptoAddress    ;
    private final long                       amount           ;
    private final long                       startTimeStamp   ;
    private final CryptoPaymentRequestType   type             ;
    private final CryptoPaymentRequestAction action           ;
    private final RequestProtocolState       protocolState    ;
    private final BlockchainNetworkType      networkType      ;

    public CryptoPaymentRequestNetworkServiceRecord(final UUID                       requestId        ,
                                                    final String                     identityPublicKey,
                                                    final Actors                     identityType     ,
                                                    final String                     actorPublicKey   ,
                                                    final Actors                     actorType        ,
                                                    final String                     description      ,
                                                    final CryptoAddress              cryptoAddress    ,
                                                    final long                       amount           ,
                                                    final long                       startTimeStamp   ,
                                                    final CryptoPaymentRequestType   type             ,
                                                    final CryptoPaymentRequestAction action           ,
                                                    final RequestProtocolState       protocolState    ,
                                                    final BlockchainNetworkType      networkType      ) {

        this.requestId         = requestId        ;
        this.identityPublicKey = identityPublicKey;
        this.identityType      = identityType     ;
        this.actorPublicKey    = actorPublicKey   ;
        this.actorType         = actorType        ;
        this.description       = description      ;
        this.cryptoAddress     = cryptoAddress    ;
        this.amount            = amount           ;
        this.startTimeStamp    = startTimeStamp   ;
        this.type              = type             ;
        this.action            = action           ;
        this.protocolState     = protocolState    ;
        this.networkType       = networkType      ;
    }

    @Override
    public UUID getRequestId() {
        return requestId;
    }

    @Override
    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

    @Override
    public Actors getIdentityType() {
        return identityType;
    }

    @Override
    public String getActorPublicKey() {
        return actorPublicKey;
    }

    @Override
    public Actors getActorType() {
        return actorType;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    @Override
    public CryptoPaymentRequestType getType() {
        return type;
    }

    @Override
    public CryptoPaymentRequestAction getAction() {
        return action;
    }

    @Override
    public RequestProtocolState getProtocolState() {
        return protocolState;
    }

    @Override
    public BlockchainNetworkType getNetworkType() {
        return networkType;
    }

}
