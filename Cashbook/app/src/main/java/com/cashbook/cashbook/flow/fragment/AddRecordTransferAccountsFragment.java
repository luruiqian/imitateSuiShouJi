package com.cashbook.cashbook.flow.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.flow.AddRecordActivity;
import com.cashbook.cashbook.flow.MyEventBus;
import com.cashbook.cashbook.interfaces.IPickerViewData;
import com.cashbook.cashbook.interfaces.MyInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddRecordTransferAccountsFragment extends Fragment {
    private static String pageName;
    private TextView mTextView;
    String str = "abc";

    private static AddRecordTransferAccountsFragment mTemplateFragment;

    public AddRecordTransferAccountsFragment() {
    }

    public static AddRecordTransferAccountsFragment newInstance(String name) {
        pageName = name;

        if (mTemplateFragment == null) {
            mTemplateFragment = new AddRecordTransferAccountsFragment();
        }
        return mTemplateFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_record_template, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        //syncGetloadData();
//        asyGetData();
//        setfunction(str);
//        Log.e("skdjfaksfdask", str);
        //retrofitGet();
//        eventbusGet();
//        glideLoad();
        RxJavaDemo();
    }

    private void RxJavaDemo() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);

            }
        });
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i("aaaaa", "onSubscribe");

            }

            @Override
            public void onNext(Integer value) {
                Log.i("aaaaa", value + "");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("aaaaa", "onError");

            }

            @Override
            public void onComplete() {
                Log.i("aaaaa", "onComplete");

            }
        };
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer + "";
                    }
                })
                ;
    }

    private void glideLoad() {
//        Glide.with(getActivity()).load("").skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT).into(mTextView);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void eventbusGet() {
        EventBus.getDefault().post(new MyEventBus("hello eventbus"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MyEventBus event) {
        Toast.makeText(getActivity(), event.message, Toast.LENGTH_SHORT).show();
    }

    private void retrofitGet() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("dskjfhksadfhk")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MyInterface myInterface = retrofit.create(MyInterface.class);

        retrofit2.Call call = myInterface.getCall();
        call.enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(retrofit2.Call call, retrofit2.Response response) {

            }

            @Override
            public void onFailure(retrofit2.Call call, Throwable t) {

            }
        });
    }


    //同步get请求
    private void syncGetloadData() {
        OkHttpClient client = new OkHttpClient.Builder().cache(new Cache(new File("llllll"), 1024))
                .readTimeout(50, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().get()
                .url("http://www.baidu.com").build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //异步get请求
    private void asyGetData() {
        final String CACHE_PATH
                = getContext().getCacheDir()
                + "/okcache";
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(50, TimeUnit.SECONDS)
                .cache(new Cache(new File(CACHE_PATH), 1024 * 1024 * 10))
                .build();
        Request request = new Request.Builder().get()
                .url("http://publicobject.com/helloworld.txt")
                .cacheControl(new CacheControl.Builder().maxStale(60 * 5, TimeUnit.SECONDS).build())
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("haohai", "请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("haohai", "请求成功");
                String responseBody = response.body().string();
                Log.e("haohai", " response = " + responseBody);
                Log.e("haohai", " cache response:    " + response.cacheResponse());
                Log.e("haohai", " network response:  " + response.networkResponse());

            }
        });

    }


    private void initView(View view) {
        mTextView = (TextView) view.findViewById(R.id.add_record_tab_tv);
        mTextView.setText(pageName);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    private void setfunction(String str) {
        str = "bac";
        Log.e("skdjfaksfdask", str);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    class innerclass extends AddRecordActivity implements IPickerViewData {

        @Override
        public String getPickerViewText() {
            return null;
        }
    }

    void method(final int a) {
        final int y = 4;
        class Inner {
            void function() {
                System.out.println(y);

            }
        }

        new Inner().function();

    }


}
