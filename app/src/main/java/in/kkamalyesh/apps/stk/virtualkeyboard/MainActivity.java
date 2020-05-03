package in.kkamalyesh.apps.stk.virtualkeyboard;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
//import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    TextView startTV, startTvVersion;
    private View mControlsView;
    private boolean mVisible;
    private static boolean isReady=false;
    private View mContentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String languageToLoad  = "en-IN";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        setContentView(R.layout.activity_main);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        mContentView.setBackground(getResources().getDrawable(R.mipmap.ic_launcher));

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startKB();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        startTV = (TextView) findViewById(R.id.start_tv);
        startTvVersion = (TextView) findViewById(R.id.start_tv_version);
        startTvVersion.setText(BuildConfig.VERSION_NAME);
        tvStartMsg=(TextView)findViewById(R.id.start_message);
        btnStart=(Button)findViewById(R.id.select_kalafalaka_btn);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSettings = new Intent();
                if(openSettings.getComponent()==null){
                    openSettings = new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS);
                }
                try {
                    startActivity(openSettings);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        btnHelp = (Button) findViewById(R.id.helpButton);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "https://sites.google.com/view/kalafalaka/help";
                Intent openHelp = new Intent();
                openHelp.setAction(Intent.ACTION_VIEW);
                openHelp.setData(Uri.parse(uri));
                startActivity(openHelp);
            }
        });
    }



    TextView tvStartMsg;
    Button btnStart, btnHelp;
    boolean kbSelected=false;
    boolean kbDefault=false;
    private void init() {
        InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        List<InputMethodInfo> list = manager.getEnabledInputMethodList();


        kbSelected=false;
        for(int i=0; i<list.size(); ++i){
            if(list.get(i).getPackageName().contains(getPackageName())){
                kbSelected=true;

            }
        }
        PackageManager packageManager = getPackageManager();

        if(kbSelected){
            tvStartMsg.setVisibility(View.GONE);
            btnStart.setVisibility(View.GONE);
            mContentView.setVisibility(View.VISIBLE);
            startKB();

        }else{
            tvStartMsg.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            mContentView.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void startKB() {
        if(kbSelected) {
            if (!KServiceManager.checkServiceRunning((ActivityManager) getSystemService(ACTIVITY_SERVICE), SoftKeyboard.class)) {
                Intent callService = new Intent(getApplicationContext(), SoftKeyboard.class);
                startService(callService);
                Toast t = Toast.makeText(getApplicationContext(), getString(R.string.app_name) + " is now ready", Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
            }
        }
    }
}
