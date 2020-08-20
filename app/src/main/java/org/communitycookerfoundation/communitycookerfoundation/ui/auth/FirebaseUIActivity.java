package org.communitycookerfoundation.communitycookerfoundation.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.communitycookerfoundation.communitycookerfoundation.MainActivity;
import org.communitycookerfoundation.communitycookerfoundation.R;

import java.util.Arrays;
import java.util.List;

public class FirebaseUIActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    public static final String SIGNED_IN = "user_signed_in";
    private static final String TAG = "UNKNOWN_ERROR";

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_ui);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startSignedInActivity();
            // already signed in
        } else {
            createSignInIntent();
            // not signed in
        }
    }

    private void startSignedInActivity() {
        Intent intent  = new Intent(FirebaseUIActivity.this, MainActivity.class);
        intent.putExtra(SIGNED_IN, 1);
        startActivity(intent);
        finish();
    }


    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                startSignedInActivity();


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showToast(R.string.sign_in_cancelled);
                    finish();

                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showToast(R.string.no_internet_connection);
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.ERROR_USER_DISABLED) {
                    showToast(R.string.account_disabled);
                    return;
                }

                showToast(R.string.unknown_error);
                Log.e(TAG, "Sign-in error: ", response.getError());
            }
        }
    }

    private void showToast(int errorIntRes) {
        Toast myToast = Toast.makeText(this, errorIntRes, Toast.LENGTH_LONG);
        myToast.show();


    }

    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_signout]
    }

    public void delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_delete]
    }



}

