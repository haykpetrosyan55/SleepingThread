package hayk.sleepingthread;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AsyncDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment, container, false);
        final ProgressBar bar = view.findViewById(R.id.proBar);
        bar.setVisibility(View.VISIBLE);
        TextView textView = (view).findViewById(R.id.text_dialog);
        if (getArguments() != null) {
            String argument = getArguments().getString(MainActivity.BUNDLE_KEY);
            textView.setText(getString(R.string.dialog_text, argument));
        }
        return view;
    }
}
