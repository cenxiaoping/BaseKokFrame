package com.kok.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    protected T mPresenter;
    private Toolbar mToolBar;

    /**
     * 设置fragment布局View
     *
     * @return
     */
    protected abstract int setLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //是否开启全屏
        if (isFullScreen()) {
            fullScreen();
        }

        //默认设置信号栏字体颜色为黑色
        setTopBarTextMode(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected void setTopBarColor(@ColorRes int color) {
        getWindow().setStatusBarColor(getResources().getColor(color));
    }

    /**
     * 设置状态栏，白色/黑色 字体颜色切换
     * View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //黑色
     * View.vSYSTEM_UI_FLAG_VISIBLE           //白色
     * <p>
     * 使用此方法，会取消掉沉浸状态栏模式，如果需要保持沉浸状态栏，请按如下方式调用：
     * <p>
     * setTopBarTextMode(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | withTranslucentStatus())
     *
     * @param mode
     */
    protected void setTopBarTextMode(int mode) {
        getWindow().getDecorView().setSystemUiVisibility(mode);
    }

    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 设置状态栏透明
     */
    @TargetApi(19)
    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            Window window = getWindow();
            View decorView = window.getDecorView();
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //导航栏颜色也可以正常设置
            //window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            attributes.flags |= flagTranslucentStatus;
            //int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            //attributes.flags |= flagTranslucentNavigation;
            window.setAttributes(attributes);
        }
    }

    /**
     * 设置信号栏字体类型时，如果需要沉浸，请调用这个方法
     * setTopBarTextMode(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | withTranslucentStatus())
     *
     * @return
     */
    protected int withTranslucentStatus() {
        return View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        mPresenter = initPresenter();
        initView();
        initData();

        //是否沉浸
        if (setStatusBar()) {
            setTranslucentStatus();
        }
    }


    /**
     * 变更状态栏字体颜色
     */
    protected void changeStateTextColor(boolean isBlack) {
        View decor = getWindow().getDecorView();
        if (isBlack) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

    }

    /**
     * 是否开启 点击输入框外自动收起键盘，如需关闭请重写该方法 retur false;
     * 默认为开启点击输入框外自动收起键盘
     *
     * @return
     */
    protected boolean isAutoHideKeyBord() {
        return true;
    }

    /**
     * 处理点击软键盘之外的空白处，隐藏软件盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isAutoHideKeyBord()) {
                View v = getCurrentFocus();
                if (isShouldHide(v, ev)) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                return super.dispatchTouchEvent(ev);
            }

        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }

//        return super.dispatchTouchEvent(ev);

        return onTouchEvent(ev);
    }


    private boolean isShouldHide(View v, MotionEvent event) {
        //这里是用常用的EditText作判断参照的,可根据情况替换成其它View
        if (v != null &&
                (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            boolean b = event.getX() > left &&
                    event.getX() < right &&
                    event.getY() > top &&
                    event.getY() < bottom;
            return !b;
        }
        return false;
    }

    /**
     * 控制开启沉浸,true开启，false关闭
     * 需要控制是否开启沉浸时，可以重写该方法，返回对应值即可
     */
    public boolean setStatusBar() {
        return false;
    }

    /**
     * 控制开启全屏
     */
    public boolean isFullScreen() {
        return false;
    }

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
        intent.setClass(this, clz);
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
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * 全屏
     */
    private void fullScreen() {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public abstract void initData();

    public abstract void initView();

    /**
     * 通过反射初始化Preseneter
     *
     * @return
     */
    private T initPresenter() {
        try {
            Type superClass = getClass().getGenericSuperclass();
            Type[] actualTypeArguments = ((ParameterizedType) superClass).getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                Type type = actualTypeArguments[0];
                Class<?> clazz = getRawType(type);
                if (this instanceof BaseView) {
                    Constructor<?>[] constructors = clazz.getConstructors();
                    if (constructors.length >= 0) {
                        return (T) constructors[0].newInstance(this);
                    }
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

    public Activity getActivity() {
        return this;
    }

    public Context getContext() {
        return this;
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    public BaseActivity setToolBar(Toolbar toolBar) {
        mToolBar = toolBar;

        mToolBar.setTitle("");
//        setHasOptionsMenu(true);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
}
