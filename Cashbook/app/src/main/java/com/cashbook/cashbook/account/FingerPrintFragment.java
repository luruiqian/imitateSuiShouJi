package com.cashbook.cashbook.account;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.activity.FingerPrintActivity;

import javax.crypto.Cipher;

/**
 * @author luruiqian
 */
@TargetApi(23)
public class FingerPrintFragment extends DialogFragment implements View.OnClickListener {
    private Cipher mCipher;
    private TextView errorMsg;
    private TextView mFingerPrintTv;
    private FingerprintManager fingerprintManager;
    private CancellationSignal mCancellationSignal;

    private FingerPrintActivity mActivity;


    /**
     * 标识是否是用户主动取消的认证。
     */
    private boolean isSelfCancelled;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fingerprintManager = getContext().getSystemService(FingerprintManager.class);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
        show(getFragmentManager(),"");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
        errorMsg = (TextView) view.findViewById(R.id.error_msg);
        TextView cancel = (TextView) view.findViewById(R.id.finger_print_cancel_tv);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                stopFingerPrintListening();
            }
        });
    }

    private void initView(View view) {
        mFingerPrintTv = (TextView) view.findViewById(R.id.finger_print_tv);
    }

    private void initListener() {
        mFingerPrintTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.finger_print_tv) {
            startFingerPrintListening(mCipher);
        }
    }

    public void setCipher(Cipher cipher) {
        mCipher = cipher;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (FingerPrintActivity) getActivity();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 停止指纹认证监听
        stopFingerPrintListening();
    }

    private void stopFingerPrintListening() {
        if (mCancellationSignal != null) {
            mCancellationSignal.cancel();
            mCancellationSignal = null;
            isSelfCancelled = true;
        }
    }

    private void startFingerPrintListening(Cipher cipher) {
        isSelfCancelled = false;
        mCancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fingerprintManager.authenticate(new FingerprintManager.CryptoObject(cipher), mCancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                if (!isSelfCancelled) {
                    errorMsg.setText(errString);
                    if (errorCode == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT) {
                        Toast.makeText(mActivity, errString, Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                errorMsg.setText(helpString);
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                Toast.makeText(mActivity, "指纹认证成功", Toast.LENGTH_SHORT).show();
                mActivity.onAuthenticated();
            }

            @Override
            public void onAuthenticationFailed() {
                errorMsg.setText("指纹认证失败，请再试一次");
            }
        }, null);
    }

}
