package abkrino.eg.baytaleaala;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {
    ImageView logo, logo2, logo4;
    TextView text;
    private Animation animation, animation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        calling();
        showAnim1();
        startAnimation();

    }

    public void calling() {
        logo = findViewById(R.id.logo);
        logo2 = findViewById(R.id.logo2);
        logo4 = findViewById(R.id.logo4);
        text = findViewById(R.id.text);
    }

    public void startAnimation() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                endAnimation1();
                                showAnim2();
                                startAnimation2();
                            }
                        }, 4000);

                    }
                });
            }
        });
        t1.start();
    }
    public void startAnimation2() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                           showAni3();
                           startAnimation3();
                            }
                        }, 4000);

                    }
                });
            }
        });
        t1.start();
    }
    public void startAnimation3() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                              showAni4();
                              startAnimation4();
                            }
                        }, 4000);

                    }
                });
            }
        });
        t1.start();
    }
    public void startAnimation4() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                goToMain();
                            }
                        }, 8000);

                    }
                });
            }
        });
        t1.start();
    }

    public void endAnimation1() {
        logo.clearAnimation();
        logo.setVisibility(View.GONE);
    }

    public void showAnim1() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        logo.setVisibility(View.VISIBLE);
        logo.startAnimation(animation);
    }

    public void showAnim2() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        text.setVisibility(View.VISIBLE);
        text.startAnimation(animation);
    }

    public void showAni3() {

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        logo2.setVisibility(View.VISIBLE);
        logo2.startAnimation(animation2);
    }

    public void showAni4() {
        logo2.clearAnimation();
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        logo4.setVisibility(View.VISIBLE);
        logo4.startAnimation(animation2);

    }

    public void goToMain() {
        startActivity(new Intent(StartActivity.this, MainActivity.class));
        finish();
    }
}