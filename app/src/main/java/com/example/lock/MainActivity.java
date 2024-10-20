package com.example.lock;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordGenerator;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {


    Toolbar toolbars;
    FloatingActionButton fab;
    dataBaseHelper dataBaseHelper;
    private CheckBox includeLetters, includeSymbols, includeNumbers;
    private Button generateButton;
    EditText passwords;
    LinearLayout llnotes;
    ImageView imageView;
    RecyclerView recyclerView;

    private TextView passwordTextView, passwordStrength;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dataBaseHelper=dataBaseHelper.getDB(this);
        llnotes=findViewById(R.id.llnotes);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageView=findViewById(R.id.lockImage);


        toolbars = findViewById(R.id.toolbar);
        fab = findViewById(R.id.addFloat);



        show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog);

                EditText userName=dialog.findViewById(R.id.userName);
                 passwords=dialog.findViewById(R.id.password);
                EditText name=dialog.findViewById(R.id.name);
                Button add=dialog.findViewById(R.id.addItem);
                passwordStrength=dialog.findViewById(R.id.strength);



                includeLetters = dialog.findViewById(R.id.includeLetters);
                includeSymbols =  dialog.findViewById(R.id.includeSymbols);
                includeNumbers =  dialog.findViewById(R.id.includeNumbers);
                generateButton =  dialog.findViewById(R.id.generateButton);

               passwords.addTextChangedListener(new TextWatcher() {
                   @Override
                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                   }

                   @Override
                   public void onTextChanged(CharSequence s, int start, int before, int count) {

                       int so= evaluatePasswordStrength(passwords.getText().toString());

                       if(so==0) {
                           passwordStrength.setText("Weak");
                           passwordStrength.setTextColor(Color.parseColor("#F44336"));

                       }
                       else{
                           passwordStrength.setText("Strong");
                           passwordStrength.setTextColor(Color.parseColor("#4CAF50"));

                       }

                   }

                   @Override
                   public void afterTextChanged(Editable s) {

                       int so= evaluatePasswordStrength(passwords.getText().toString());

                       if(so==0) {
                           passwordStrength.setText("Weak");
                           passwordStrength.setTextColor(Color.parseColor("#F44336"));

                       }
                       else{
                           passwordStrength.setText("Strong");
                           passwordStrength.setTextColor(Color.parseColor("#4CAF50"));

                       }

                   }
               });

                generateButton.setOnClickListener(view -> {
                    String password = generatePassword();
                    passwords.setText(password); // Set generated password in EditText
                   int s= evaluatePasswordStrength(password);
                    if(s==0) {
                        passwordStrength.setText("Weak");
                        passwordStrength.setTextColor(Color.parseColor("#F44336"));

                    }
                    else{
                        passwordStrength.setText("Strong");
                        passwordStrength.setTextColor(Color.parseColor("#4CAF50"));

                    }

                });



                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String names=name.getText().toString();
                        String user=userName.getText().toString();
                        String pass=passwords.getText().toString();

                        if(!user.isEmpty() && !pass.isEmpty()){

                            dataBaseHelper.storeDoe().add(new Store(user,pass,names));
                            show();
                            dialog.dismiss();

                        }
                        else {
                            Toast.makeText(MainActivity.this, "Please Valid Enter Data", Toast.LENGTH_SHORT).show();
                        }





                    }
                });


                dialog.show();

            }
        });


    }

    public  void show(){
        ArrayList<Store> arr=(ArrayList<Store>) dataBaseHelper.storeDoe().getAllStore();

        if(arr.size()>0){
            llnotes.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new adapterRecycle(this,arr,dataBaseHelper));

        }
        else {
            llnotes.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

    }


    private String generatePassword() {
        PasswordGenerator generator = new PasswordGenerator();
        List<CharacterRule> rules = new ArrayList<>();

        // Add character rules based on checkboxes
        if (includeLetters.isChecked()) {
            rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 2)); // 2 uppercase letters
            rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 2)); // 2 lowercase letters
        }
        if (includeNumbers.isChecked()) {
            rules.add(new CharacterRule(EnglishCharacterData.Digit, 2)); // 2 digits
        }
        if (includeSymbols.isChecked()) {
            rules.add(new CharacterRule(EnglishCharacterData.Special, 2)); // 2 special characters
        }

       if(!includeLetters.isChecked() && !includeNumbers.isChecked() && !includeSymbols.isChecked()) {

           Toast.makeText(MainActivity.this,"Please Check box",Toast.LENGTH_SHORT).show();

           return "";
       }
        else return generator.generatePassword(12, rules);
    }

    private int evaluatePasswordStrength(String password) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 16), // Password must be between 8 and 16 characters long
                new CharacterRule(EnglishCharacterData.UpperCase, 1), // At least 1 uppercase letter
                new CharacterRule(EnglishCharacterData.LowerCase, 1), // At least 1 lowercase letter
                new CharacterRule(EnglishCharacterData.Digit, 1),     // At least 1 digit
                new CharacterRule(EnglishCharacterData.Special, 1),   // At least 1 special character
                new WhitespaceRule(), // No whitespace allowed
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false), // No more than 5 alphabetical sequences
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),    // No more than 5 numerical sequences
                new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false)
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        // Set the strength text
        if (result.isValid()) {
            return 1;
        } else {
            return 0;
        }
    }

}