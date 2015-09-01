package com.bitdubai.sub_app.wallet_store.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.wallet_store.common.workers.InstallWalletWorker;
import com.bitdubai.sub_app.wallet_store.common.workers.InstallWalletWorkerCallback;
import com.bitdubai.sub_app.wallet_store.common.UtilsFuncs;
import com.bitdubai.sub_app.wallet_store.common.adapters.ImagesAdapter;
import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;
import com.bitdubai.sub_app.wallet_store.common.workers.UninstallWalletWorker;
import com.bitdubai.sub_app.wallet_store.common.workers.UninstallWalletWorkerCallback;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;
import com.wallet_store.bitdubai.R;

import java.util.ArrayList;
import java.util.UUID;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.BASIC_DATA;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.DEVELOPER_NAME;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.LANGUAGE_ID;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.PREVIEW_IMGS;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.SKIN_ID;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.WALLET_VERSION;


/**
 * Fragment que luce como un Activity donde se muestra parte de los detalles de una Wallet seleccionada en el catalogo de MainActivityFragment
 *
 * @author Nelson Ramirez
 * @version 1.0
 */
public class DetailsActivityFragment extends FermatFragment {
    private final String TAG = "DetailsActivityFragment";

    // MODULE
    private WalletStoreModuleManager moduleManager;

    private ErrorManager errorManager;


    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static DetailsActivityFragment newInstance() {
        return new DetailsActivityFragment();
    }

    @Override
    public void setSubAppsSession(SubAppsSession subAppsSession) {
        super.setSubAppsSession(subAppsSession);

        WalletStoreSubAppSession session = (WalletStoreSubAppSession) subAppsSession;
        moduleManager = session.getWalletStoreModuleManager();
        errorManager = subAppsSession.getErrorManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wallet_store_fragment_details_activity, container, false);

        ArrayList<Bitmap> walletPreviewImgList = (ArrayList) subAppsSession.getData(PREVIEW_IMGS);
        final WalletStoreListItem catalogItem = (WalletStoreListItem) subAppsSession.getData(BASIC_DATA);
        String developerAlias = (String) subAppsSession.getData(DEVELOPER_NAME);


        FermatTextView developerName = (FermatTextView) rootView.findViewById(R.id.wallet_developer_name);
        developerName.setText(developerAlias);


        FermatTextView shortDescription = (FermatTextView) rootView.findViewById(R.id.wallet_short_description);
        shortDescription.setText(catalogItem.getDescription());


        FermatTextView walletName = (FermatTextView) rootView.findViewById(R.id.wallet_name);
        walletName.setText(catalogItem.getWalletName());


        ImageView walletIcon = (ImageView) rootView.findViewById(R.id.wallet_icon);
        walletIcon.setImageBitmap(catalogItem.getWalletIcon());


        ImageView walletBanner = (ImageView) rootView.findViewById(R.id.wallet_banner);
        walletBanner.setImageBitmap(catalogItem.getWalletIcon()); // TODO Obtener valor correcto


        FermatTextView publisherName = (FermatTextView) rootView.findViewById(R.id.wallet_publisher_name);
        publisherName.setText("Publisher Name"); // TODO Obtener valor correcto


        FermatTextView totalInstalls = (FermatTextView) rootView.findViewById(R.id.wallet_total_installs);
        totalInstalls.setText("10"); // TODO Obtener valor correcto


        FermatTextView readMoreLink = (FermatTextView) rootView.findViewById(R.id.read_more_link);
        readMoreLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "To MoreDetailActivity", Toast.LENGTH_SHORT).show();
            }
        });


        FermatButton installButton = (FermatButton) rootView.findViewById(R.id.wallet_install_button);
        InstallationStatus installStatus = catalogItem.getInstallationStatus();
        int installStatusResId = UtilsFuncs.INSTANCE.getInstallationStatusStringResource(installStatus);
        installStatusResId = (installStatusResId == R.string.wallet_status_installed) ? R.string.wallet_status_open : installStatusResId;
        installButton.setText(installStatusResId);
        installButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Installing...", Toast.LENGTH_SHORT).show();

                InstallWalletWorkerCallback callback = new InstallWalletWorkerCallback(getActivity(), errorManager);
                InstallWalletWorker installWalletWorker = new InstallWalletWorker(getActivity(), callback, moduleManager, subAppsSession);
                installWalletWorker.run();
            }
        });


        if (installStatusResId != R.string.wallet_status_install) {
            FermatButton uninstallButton = (FermatButton) rootView.findViewById(R.id.wallet_uninstall_button);
            uninstallButton.setText(R.string.wallet_status_uninstall);
            uninstallButton.setVisibility(View.VISIBLE);
            uninstallButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Uninstalling...", Toast.LENGTH_SHORT).show();

                    UninstallWalletWorkerCallback callback = new UninstallWalletWorkerCallback(getActivity(), errorManager);
                    UUID catalogueId = catalogItem.getId();
                    UninstallWalletWorker installWalletWorker = new UninstallWalletWorker(getActivity(), callback, moduleManager, catalogueId);
                    installWalletWorker.run();
                }
            });
        }


        RecyclerView previewImagesRecyclerView = (RecyclerView) rootView.findViewById(R.id.wallet_screenshots_recycler_view);
        if (walletPreviewImgList != null) {
            LinearLayoutManager layout = new LinearLayoutManager(getActivity(), HORIZONTAL, false);
            previewImagesRecyclerView.setLayoutManager(layout);

            ImagesAdapter adapter = new ImagesAdapter(getActivity(), walletPreviewImgList);
            previewImagesRecyclerView.setAdapter(adapter);

        } else {
            previewImagesRecyclerView.setVisibility(View.GONE);

            FermatTextView noPreviewImages = (FermatTextView) rootView.findViewById(R.id.no_preview_images);
            noPreviewImages.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

}

