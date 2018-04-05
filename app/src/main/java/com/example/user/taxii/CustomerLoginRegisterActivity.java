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

public class CustomerLoginRegisterActivity extends AppCompatActivity
{
    private Button CustomerLoginButton;
    private Button CustomerRegisterButton;
    private TextView CustomerRegisterLink;
    private TextView CustomerStatus;
    private EditText EmailCustomer;
    private EditText PasswordCustomer;

    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_register);


        CustomerLoginButton = (Button) findViewById(R.id.customer_login_btn);
        CustomerRegisterButton = (Button) findViewById(R.id.customer_register_btn);
        CustomerRegisterLink = (TextView) findViewById(R.id.register_customer_link);
        CustomerStatus = (TextView) findViewById(R.id.customer_status);
        EmailCustomer = (EditText) findViewById(R.id.email_customer);
        PasswordCustomer = (EditText) findViewById(R.id.password_customer);

        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();


        CustomerRegisterButton.setVisibility(View.INVISIBLE);
        CustomerRegisterButton.setEnabled(false);



        //if someone click on the customer register link
        CustomerRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //make customerLoginByn & register link invisible
                CustomerLoginButton.setVisibility(View.INVISIBLE);
                CustomerRegisterLink.setVisibility(View.INVISIBLE);
                CustomerStatus.setText("Register Customer");

                CustomerRegisterButton.setVisibility(View.VISIBLE);
                CustomerRegisterButton.setEnabled(true);

            }
        });

        CustomerRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String email = EmailCustomer.getText().toString();
                String password = PasswordCustomer.getText().toString();

                CustomerRegister(email, password);

            }
        });


        CustomerLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String email = EmailCustomer.getText().toString();
                String password = PasswordCustomer.getText().toString();

                SignInCustomer(email, password);
            }
        });


    }



    private void SignInCustomer(String email, String password)
    {
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this, "Please enter your Email...", Toast.LENGTH_SHORT).show();

        }

        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this, "Please enter your Password...", Toast.LENGTH_SHORT).show();
        }

        else
        {

            loadingBar.setTitle("Customer Login");
            loadingBar.setMessage("Please wait, While we are checking your credentials");
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(CustomerLoginRegisterActivity.this, "Customer Logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent driverIntent = new Intent(CustomerLoginRegisterActivity.this, CustomersMapActivity.class);
                                startActivity(driverIntent);
                            }

                            else
                            {
                                Toast.makeText(CustomerLoginRegisterActivity.this, "Something gone wrong...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }



    private void CustomerRegister(String email, String password)
    {
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this, "Please enter your Email...", Toast.LENGTH_SHORT).show();

        }

        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this, "Please enter your Password...", Toast.LENGTH_SHORT).show();
        }

        else
        {

            loadingBar.setTitle("Customer Registration");
            loadingBar.setMessage("Please wait, Registering your data is in progress");
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(CustomerLoginRegisterActivity.this, "Customer Registered Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent driverIntent = new Intent(CustomerLoginRegisterActivity.this, CustomersMapActivity.class);
                                startActivity(driverIntent);
                            }

                            else
                            {
                                Toast.makeText(CustomerLoginRegisterActivity.this, "Something gone wrong...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }
}
