package office.management.system.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import office.management.system.R;

public class SplashScreenActivity extends AppCompatActivity {

    private long splashtime = 900, ms = 0;
    private boolean splashactive = true, pause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().setFlags(WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES,
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
        }else{*/
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //}
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        /*final RelativeLayout ri = findViewById(R.id.rilay);
        Thread thread = new Thread() {
            public void run() {
                try {
                    while (splashactive && ms < splashtime) {
                        if (!pause)
                            ms = ms + 100;
                        sleep(100);
                    }
                } catch (Exception e) {
                } finally {
                    if (!isOnline()) {
                        Snackbar skb = Snackbar.make(ri, "No internet access !", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Re-try", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        recreate();
                                    }
                                });
                        skb.show();
                    } else {
                        goToNextScreen();
                    }
                }
            }
        };
        thread.start();*/

        if (!isOnline()) {
            /*Snackbar skb = Snackbar.make(ci,"No internet access !",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Re-try", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recreate();
                        }
                    });
            skb.show();*/
            successDilogue();

        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    goToNextScreen();
                }
            }, 1500);

        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void goToNextScreen() {
        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SplashScreenActivity.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
        startActivity(intent, bndlanimation);
        finish();
    }

    public void successDilogue() {
        Vibrator vibrator = (Vibrator) getSystemService(SplashScreenActivity.this.VIBRATOR_SERVICE);
        vibrator.vibrate(500);
        final Dialog dialog = new Dialog(SplashScreenActivity.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.no_internet_dialog);
        Button button = dialog.findViewById(R.id.btnsucces);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                recreate();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
