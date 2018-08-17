package hayk.sleepingthread;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_FRAG = "tag";
    public static final String BUNDLE_KEY = "bundle_kay";
    private EditText editText;
    private TextView endText;
    private DialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button_id);
        dialogFragment = new AsyncDialogFragment();
        editText = findViewById(R.id.edit_text_id);
        endText = findViewById(R.id.text_end);
        startTask(button);
    }

    private void startTask(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Async mt = new Async(MainActivity.this);
                if (!editText.getText().toString().isEmpty()) {
                    mt.execute();
                } else {
                    Toast.makeText(MainActivity.this,
                            R.string.put_number, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    static class Async extends AsyncTask<Void, Void, Void> {
        private WeakReference<MainActivity> activityReference;
        private String str;

        private Async(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        private MainActivity getReference() {
            return activityReference.get();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            str = getReference().editText.getText().toString();
            FragmentManager fragmentTransaction = getReference().getFragmentManager();
            getReference().dialogFragment.setCancelable(false);
            getReference().dialogFragment.show(fragmentTransaction, KEY_FRAG);
            Bundle bundle = new Bundle();
            bundle.putString(BUNDLE_KEY, str);
            getReference().dialogFragment.setArguments(bundle);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(Integer.valueOf(str));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            getReference().dialogFragment.dismiss();
            getReference().endText.setText(getReference().getString(R.string.text_main, str));
        }
    }
}
