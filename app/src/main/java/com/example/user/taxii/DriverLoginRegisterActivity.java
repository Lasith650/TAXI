package com.example.user.taxii;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DriverLoginRegisterActivity extends AppCompatActivity {

    private Button DriverLoginButton;
    private Button DriverRegisterButton;
    private TextView DriverRegisterLink;
    private TextView DriverStatus;
    private EditText EmailDriver;
    private EditText PasswordDriver;

    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_register);


        mAuth = FirebaseAuth.getInstance();


        DriverLoginButton = (Button) findViewById(R.id.driver_login_btn);
        DriverRegisterButton = (Button) findViewById(R.id.driver_register_btn);
        DriverRegisterLink = (TextView) findViewById(R.id.driver_register_link);
        DriverStatus = (TextView) findViewById(R.id.driver_status);
        EmailDriver = (EditText) findViewById(R.id.email_driver);
        PasswordDriver = (EditText) findViewById(R.id.password_driver);

        loadingBar = new ProgressDialog(this);


        DriverRegisterButton.setVisibility(View.INVISIBLE);
        DriverRegisterButton.setEnabled(false);



        //if someone click on the customer register link
        DriverRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //make customerLoginByn & register link invisible
                DriverLoginButton.setVisibility(View.INVISIBLE);
                DriverRegisterLink.setVisibility(View.INVISIBLE);
                DriverStatus.setText("Register Customer");

                DriverRegisterButton.setVisibility(View.VISIBLE);
                DriverRegisterButton.setEnabled(true);

            }
        });


        //if someone click on the driver register button
        DriverRegisterButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //get the email and the password from the user
                String email = EmailDriver.getText().toString();
                String password = PasswordDriver.getText().toString();

                RegisterDriver(email, password);
            }
        });


        //if someone click on the driver login button
        DriverLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //get the email and the password from the user
                String email = EmailDriver.getText().toString();
                String password = PasswordDriver.getText().toString();

                SignInDriver(email, password);
            }
        });

    }



    //this is the sign in driver method
    private void SignInDriver(String email, String password)
    {
        //validations- forget to write the email
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(DriverLoginRegisterActivity.this, "Please enter the Email...", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(DriverLoginRegisterActivity.this, "Please enter the Password...", Toast.LENGTH_SHORT).show();
        }

        else
        {

            loadingBar.setTitle("Driver Login");
            loadingBar.setMessage("Please wait, While we are checking your credentials");
            loadingBar.show();

            //authentication(fire base auth is necessary)
            //then create the user
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(DriverLoginRegisterActivity.this, "Driver Logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent driverIntent = new Intent(DriverLoginRegisterActivity.this, DriversMapActivity.class);
                                startActivity(driverIntent);
                            }

                            else
                            {
                                Toast.makeText(DriverLoginRegisterActivity.this, "Something gone wrong...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });

        }
    }




    //this is the register driver method
    private void RegisterDriver(String email, String password)
    {
        //validations- forget to write the email
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(DriverLoginRegisterActivity.this, "Please enter the Email...", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(DriverLoginRegisterActivity.this, "Please enter the Password...", Toast.LENGTH_SHORT).show();
        }

        else
        {

            loadingBar.setTitle("Driver Registration");
            loadingBar.setMessage("Please wait, Registering your data is in progress");
            loadingBar.show();

            //authentication(fire base auth is necessary)
            //then create the user
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(DriverLoginRegisterActivity.this, "Driver Registered Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent driverIntent = new Intent(DriverLoginRegisterActivity.this, DriversMapActivity.class);
                                startActivity(driverIntent);
                            }

                            else
                            {
                                Toast.makeText(DriverLoginRegisterActivity.this, "Something gone wrong...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });

        }

    }
}
