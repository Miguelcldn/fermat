package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/10/15.
 */
public abstract class AbstractDigitalAssetSwap implements DigitalAssetSwap {

    AssetVaultManager assetVaultManager;
    public BitcoinNetworkManager bitcoinNetworkManager;
    PluginFileSystem pluginFileSystem;
    UUID pluginId;
    public AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager;
    public CryptoTransaction cryptoTransaction;

    public AbstractDigitalAssetSwap(UUID pluginId,
                                    PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    public void setAssetTransmissionNetworkServiceManager(AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager) {
        this.assetTransmissionNetworkServiceManager = assetTransmissionNetworkServiceManager;
    }

    public void setBitcoinCryptoNetworkManager(BitcoinNetworkManager bitcoinNetworkManager) {
        this.bitcoinNetworkManager = bitcoinNetworkManager;
    }

    public void setAssetVaultManager(AssetVaultManager assetVaultManager) {
        this.assetVaultManager = assetVaultManager;
    }

    public abstract void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws DAPException;

    public boolean isDigitalAssetHashValid(DigitalAssetMetadata digitalAssetMetadata) throws CantGetCryptoTransactionException, DAPException {
        /**
         * I will get the Genesis Transaction from the Crypto Network
         */
        CryptoTransaction cryptoTransaction = bitcoinNetworkManager.getCryptoTransactionFromBlockChain(digitalAssetMetadata.getGenesisTransaction(), digitalAssetMetadata.getGenesisBlock());
        if(cryptoTransaction==null){
            throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE,null,"Getting the genesis transaction from Crypto Network","The crypto transaction received is null");
        }
        this.cryptoTransaction=cryptoTransaction;

        String digitalAssetMetadataHash = digitalAssetMetadata.getDigitalAssetHash();
        System.out.println("ASSET DISTRIBUTION OR RECEPTION DAM - Hash:" + digitalAssetMetadataHash);
        String digitalAssetGenesisTransaction = digitalAssetMetadata.getGenesisTransaction();
        System.out.println("ASSET DISTRIBUTION OR RECEPTION DAM - Genesis Transaction:" + digitalAssetGenesisTransaction);

        System.out.println("ASSET DISTRIBUTION OR RECEPTION DAM - Crypto Transaction from CryptoNetwork:" + cryptoTransaction);
        String hashFromCryptoTransaction = cryptoTransaction.getOp_Return();
        System.out.println("ASSET DISTRIBUTION OR RECEPTION DAM - Crypto Transaction OP_return:" + hashFromCryptoTransaction);


        /**
         * I will compare the OP_Return value of the GenesisTransaction against the Hash of the digital Asset Metadata recieved from the issuer
         */
        return digitalAssetMetadataHash.equals(hashFromCryptoTransaction);
    }

    private CryptoTransaction getCryptoTransactionFromCryptoNetwork(String genesisTransaction) throws DAPException {
        //Todo: get the list from BitcoinCryptoNetwork
        List<CryptoTransaction> cryptoTransactionList = new ArrayList<>();
        for (CryptoTransaction cryptoTransaction : cryptoTransactionList) {
            if (cryptoTransaction.getTransactionHash().equals(genesisTransaction)) {
                return cryptoTransaction;
            }
        }
        throw new DAPException("The genesis transaction doesn't exists in the crypto network");
    }

    public abstract void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser actorAssetUser) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException;

    public abstract void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, String senderId) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException;

    public boolean isAvailableBalanceInAssetVault(long genesisAmount, String genesisTransaction) {
        //I will hardcode this control for testing
        System.out.println("ASSET DISTRIBUTION OR RECEPTION Check available balance is hardcoded");
        return true;
        /*long availableBalanceForTransaction=this.assetVaultManager.getAvailableBalanceForTransaction(genesisTransaction);
        return availableBalanceForTransaction<genesisAmount;*/
    }

    public boolean isValidContract(DigitalAssetContract digitalAssetContract) {
        //For now, we going to check, only, the expiration date
        ContractProperty contractProperty = digitalAssetContract.getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE);
        Timestamp expirationDate = (Timestamp) contractProperty.getValue();
        System.out.println("ASSET DISTRIBUTION OR RECEPTION Contract expiration date timestamp:" + expirationDate);
        Date date = new Date();
        Timestamp actualDate = new Timestamp(date.getTime());
        System.out.println("ASSET DISTRIBUTION OR RECEPTION Actual timestamp:" + actualDate);
        return expirationDate.after(actualDate);
    }

    public abstract void persistInLocalStorage(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateDigitalAssetFileException;

    public abstract void setDigitalAssetLocalFilePath(DigitalAssetMetadata digitalAssetMetadata);
}
