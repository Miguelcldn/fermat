package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetada;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by root on 11/01/16.
 */
public class MockChat implements ChatMetada {

    private UUID idChat;

    private UUID idObjecto;

    private String localActorType;

    private String localActorPubKey;

    private String remoteActorType;

    private String remoteActorPubKey;

    private String chatName;

    private ChatMessageStatus chatMessageStatus;

    private Timestamp date;

    private UUID idMessage;

    private String message;

    private DistributionStatus distributionStatus;

    @Override
    public UUID getIdChat() {
        return idChat;
    }

    public void setIdChat(UUID idChat) {
        this.idChat = idChat;
    }

    @Override
    public UUID getIdObjecto() {
        return idObjecto;
    }

    public void setIdObjecto(UUID idObjecto) {
        this.idObjecto = idObjecto;
    }

    @Override
    public String getLocalActorType() {
        return localActorType;
    }

    public void setLocalActorType(String localActorType) {
        this.localActorType = localActorType;
    }

    @Override
    public String getLocalActorPubKey() {
        return localActorPubKey;
    }

    public void setLocalActorPubKey(String localActorPubKey) {
        this.localActorPubKey = localActorPubKey;
    }

    @Override
    public String getRemoteActorType() {
        return remoteActorType;
    }

    public void setRemoteActorType(String remoteActorType) {
        this.remoteActorType = remoteActorType;
    }

    @Override
    public String getRemoteActorPubKey() {
        return remoteActorPubKey;
    }

    public void setRemoteActorPubKey(String remoteActorPubKey) {
        this.remoteActorPubKey = remoteActorPubKey;
    }

    @Override
    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    @Override
    public ChatMessageStatus getChatMessageStatus() {
        return chatMessageStatus;
    }

    public void setChatMessageStatus(ChatMessageStatus chatMessageStatus) {
        this.chatMessageStatus = chatMessageStatus;
    }

    @Override
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public UUID getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(UUID idMessage) {
        this.idMessage = idMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public DistributionStatus getDistributionStatus() {
        return distributionStatus;
    }

    public void setDistributionStatus(DistributionStatus distributionStatus) {
        this.distributionStatus = distributionStatus;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
