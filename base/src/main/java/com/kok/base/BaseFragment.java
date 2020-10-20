package com.kok.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

public abstract class BaseFragment<K extends BasePresenter> extends Fragment {

    private View mRootView;
    protected K mPresenter;
    private Toolbar mToolBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (setLayoutId() != 0) {
            return inflater.inflate(setLayoutId(), container, false);
        } else {
            throw new NullPointerException(this.getClass().getSimpleName() + "   setResId()方法还没有设置View");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //默认设置信号栏字体颜色为黑色
        setTopBarTextMode(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        this.mRootView = view;

        mPresenter = setPresenter();
        initView();
        initData();


        if (setStatusBar()) {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            Window window = getActivity().getWindow();
            View decorView = window.getDecorView();
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected <T extends View> T findViewById(int id) {
        return (T) mRootView.findViewById(id);
    }

    /**
     * 设置fragment布局View
     *
     * @return
     */
    protected abstract int setLayoutId();

    /**
     * 初始化View
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    public K setPresenter() {

        try {
            Type superClass = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
            Class<?> clazz = getRawType(type);
            if (this instanceof BaseView) {
                Constructor<?>[] constructors = clazz.getConstructors();
                if (constructors.length >= 0) {
                    return (K) constructors[0].newInstance(this);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // type不能直接实例化对象，通过type获取class的类型，然后实例化对象
    public Class<?> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            return (Class) rawType;
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        } else if (type instanceof TypeVariable) {
            return Object.class;
        } else if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
        }
    }

    /**
     * 开启activity
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public Intent getIntent() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            return activity.getIntent();
        } else {
            return null;
        }
    }

    /**
     * 设置状态栏颜色
     * <p>
     * ps：如果使用沉浸状态栏模式，请勿使用此方法
     *
     * @param color
     */
    protected void setTopBarColor(@ColorRes int color) {
        getActivity().getWindow().setStatusBarColor(getResources().getColor(color));
    }

    /**
     * 设置状态栏，白色/黑色 字体颜色切换
     * View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //黑色
     * View.vSYSTEM_UI_FLAG_VISIBLE           //白色
     *
     * <p>
     * 使用此方法，会取消掉沉浸状态栏模式，如果需要保持沉浸状态栏，请按如下方式调用：
     * <p>
     * setTopBarTextMode(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | withTranslucentStatus())
     *
     * @param visibility
     */
    protected void setTopBarTextMode(int visibility) {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(visibility);
    }

    /**
     * 设置信号栏字体类型时，如果需要沉浸，请调用这个方法
     * setTopBarTextMode(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | withTranslucentStatus())
     *
     * @return
     * @see BaseFragment#setTopBarTextMode(int)
     */
    protected int withTranslucentStatus() {
        return View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    }

    /**
     * 控制开启沉浸,true开启，false关闭
     * 需要控制是否开启沉浸时，可以重写该方法，返回对应值即可
     */
    public boolean setStatusBar() {
        return false;
    }


    public BaseFragment setToolBar(Toolbar toolBar) {
        mToolBar = toolBar;

        mToolBar.setTitle("");
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return this;
    }

    /**
     * 隐藏返回按钮
     * <p>
     * 更改返回按钮的图片
     * app:navigationIcon="@drawable/qy_sz"
     * android:navigationIcon="@drawable/qy_sz"
     */
    public void hideNavigationBack() {
        if (mToolBar != null) {
            mToolBar.setNavigationOnClickListener(null);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

}
