package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.event.EventType;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public class WalletOpenedEvent implements PlatformEvent {
    private UUID walletId;
    private EventType eventType;
    private EventSource eventSource;
    
    public void setWalletId (UUID walletId){
        this.walletId = walletId;               
    }
    
    public UUID getWalletId() {
        return this.walletId;        
    }
    
    public WalletOpenedEvent (EventType eventType){
        this.eventType = eventType;        
    }


    @Override
    public EventType getEventType() {
        return this.eventType;
    }

    @Override
    public void setSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    @Override
    public EventSource getSource() {
        return this.eventSource;
    }
}
