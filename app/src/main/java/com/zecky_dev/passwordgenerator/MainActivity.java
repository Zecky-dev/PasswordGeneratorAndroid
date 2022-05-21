package com.zecky_dev.passwordgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText passLengthET,generatedPasswordET;
    private String generatedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String lowercase_letters = "abcdefghijklmnoprstuvyzwx";
        char[] lowercase_letters_arr = lowercase_letters.toCharArray();
        String uppercase_letters = "ABCDEFGHIJKLMNOPRSTUVYZX";
        char[] uppercase_letters_arr = uppercase_letters.toCharArray();
        String specialCharacters = " !\"#$%&'%()*+,-./:;<=>?@[\\]^_`{|}~";
        char[] specialChars_arr = specialCharacters.toCharArray();
        int[] numbers_arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayList<Boolean> checkedCheckBoxes = new ArrayList<>();
        CheckBox lowerCaseCB = findViewById(R.id.lowerCaseCB);
        CheckBox upperCaseCB = findViewById(R.id.upperCaseCB);
        CheckBox numbersCB = findViewById(R.id.numbersCB);
        CheckBox specialCharsCB = findViewById(R.id.specialCharactersCB);
        passLengthET = findViewById(R.id.passwordLengthET);
        generatedPasswordET = findViewById(R.id.generatedPassET);
        Button generatePassBTN = findViewById(R.id.generatePassBTN);
        Button copyPassBTN = findViewById(R.id.copyPasswordBTN);
        Random random = new Random();
        ArrayList<String> checkedBoxes = new ArrayList<>();
        checkedBoxes.add("lowerCase");
        lowerCaseCB.setChecked(true);
        lowerCaseCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkedBoxes.add("lowerCase");
                }
                else{
                    checkedBoxes.remove("lowerCase");
                }
                System.out.println(checkedBoxes.size());
            }
        });
        upperCaseCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkedBoxes.add("upperCase");
                }
                else{
                    checkedBoxes.remove("upperCase");
                }
                System.out.println(checkedBoxes.size());
            }
        });
        numbersCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkedBoxes.add("numbers");
                }
                else{
                    checkedBoxes.remove("numbers");
                }
                System.out.println(checkedBoxes.size());
            }
        });
        specialCharsCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkedBoxes.add("specialChars");
                }
                else{
                    checkedBoxes.remove("specialChars");
                }
                System.out.println(checkedBoxes.size());
            }
        });
        generatePassBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkedBoxes.size()==0){
                    Toast.makeText(MainActivity.this, "You must check at least 1 option.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!passLengthET.getText().toString().isEmpty()){
                        int passLength,randomOption,randomIndex;
                        generatedPassword = "";
                        passLength = Integer.parseInt(passLengthET.getText().toString());
                        for(int i=0;i<passLength;i++){
                            randomOption = random.nextInt(checkedBoxes.size());
                            System.out.println(randomOption);
                            if(checkedBoxes.get(randomOption).equals("lowerCase")){
                                randomIndex = random.nextInt(lowercase_letters.length());
                                generatedPassword+=String.valueOf(lowercase_letters_arr[randomIndex]);
                            }
                            if(checkedBoxes.get(randomOption).equals("upperCase")){
                                randomIndex = random.nextInt(uppercase_letters.length());
                                generatedPassword += String.valueOf(uppercase_letters_arr[randomIndex]);
                            }
                            if(checkedBoxes.get(randomOption).equals("numbers")){
                                randomIndex = random.nextInt(numbers_arr.length);
                                generatedPassword += String.valueOf(numbers_arr[randomIndex]);
                            }
                            if(checkedBoxes.get(randomOption).equals("specialChars")){
                                randomIndex = random.nextInt(specialChars_arr.length);
                                generatedPassword += String.valueOf(specialChars_arr[randomIndex]);
                            }
                            generatedPasswordET.setText(generatedPassword);
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Please fill blank area!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        copyPassBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!generatedPasswordET.getText().toString().isEmpty()){
                    ClipboardManager clipBoard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("generatedPassword",generatedPassword);
                    clipBoard.setPrimaryClip(clip);
                    Dialog dialog = new Dialog(MainActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.custom_alert_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    TextView dialogTW = dialog.findViewById(R.id.dialogTextTW);
                    dialogTW.setText(generatedPassword + "\ncopied to clipboard!");
                    dialog.findViewById(R.id.closeDialogBTN).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Firstly generate a password to copy!", Toast.LENGTH_SHORT).show();
                }

            }
        });





    }
}