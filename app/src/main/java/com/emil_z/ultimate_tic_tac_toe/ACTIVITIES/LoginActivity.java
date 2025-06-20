package com.emil_z.ultimate_tic_tac_toe.ACTIVITIES;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.emil_z.helper.TextInputLayoutUtil;
import com.emil_z.helper.inputValidators.Rule;
import com.emil_z.helper.inputValidators.RuleOperation;
import com.emil_z.helper.inputValidators.Validator;
import com.emil_z.ultimate_tic_tac_toe.ACTIVITIES.BASE.BaseActivity;
import com.emil_z.ultimate_tic_tac_toe.R;
import com.emil_z.viewmodel.UsersViewModel;

/**
 * Activity that handles user login.
 * <p>
 * Validates user credentials, and navigates to the main activity upon successful login.
 */
public class LoginActivity extends BaseActivity {
	private EditText etEmail;
	private EditText etPassword;
	private Button btnSignIn;
	private Button btnBack;

	private UsersViewModel viewModel;

	/**
	 * Sets up click listeners for registration and back buttons.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		EdgeToEdge.enable(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});

		initializeViews();
		setListeners();
		setViewModel();
	}

	/**
	 * Initializes view components for login.
	 */
	@Override
	protected void initializeViews() {
		etEmail = findViewById(R.id.etEmail);
		etPassword = findViewById(R.id.etPassword);
		btnSignIn = findViewById(R.id.btnSignIn);
		btnBack = findViewById(R.id.btnBack);
	}

	/**
	 * Sets up click listeners for sign-in and back buttons.
	 */
	@Override
	protected void setListeners() {
		btnSignIn.setOnClickListener(v -> {
			if (validate()) {
				String username = etEmail.getText().toString();
				String password = etPassword.getText().toString();
				viewModel.logIn(username, password);
			}
		});
		btnBack.setOnClickListener(v -> finish());
	}

	/**
	 * Initializes the ViewModel and observes login success.
	 */
	@Override
	protected void setViewModel() {
		viewModel = new ViewModelProvider(this).get(UsersViewModel.class);

		viewModel.getLiveDataSuccess().observe(this, success -> {
			if (!success) {
				Toast.makeText(LoginActivity.this, R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
			}
		});

		viewModel.getLiveDataEntity().observe(this, user -> {
			if (user != null) {
				BaseActivity.currentUser = user;
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
			}
		});
	}

	/**
	 * Sets up validation rules for login fields.
	 */
	public void setValidation() {
		Validator.clear();
		Validator.add(new Rule(etEmail, RuleOperation.REQUIRED, getString(R.string.no_username)));
		Validator.add(new Rule(etPassword, RuleOperation.REQUIRED, getString(R.string.no_password)));
	}

	/**
	 * Validates login fields and updates error messages.
	 *
	 * @return true if all fields are valid, false otherwise.
	 */
	public boolean validate() {
		setValidation();
		boolean isValid = Validator.validate();

		TextInputLayoutUtil.transferErrorsToTextInputLayout(etEmail);
		TextInputLayoutUtil.transferErrorsToTextInputLayout(etPassword);
		return isValid;
	}
}