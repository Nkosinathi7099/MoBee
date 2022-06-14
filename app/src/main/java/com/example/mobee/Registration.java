package com.example.mobee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Registration extends Activity {


    EditText textInputEditTextMobile,textInputEditTextUsername,textInputEditTextpassword,textInputEditTextEmail;
    Button buttonSignup;
    TextView textViewLogin;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button btnSelect;
    private ImageView ivImage;
    private String userChoosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

        textInputEditTextEmail = findViewById(R.id.editTextEmail);
        textInputEditTextMobile = findViewById(R.id.editTextMobile);
        textInputEditTextUsername = findViewById(R.id.editTextName);
        textInputEditTextpassword =  findViewById(R.id.editTextPassword);
        buttonSignup = findViewById(R.id.cirLoginButton);

        buttonSignup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String Mobile,Username,Password,Email;

                Mobile = String.valueOf(textInputEditTextMobile.getText());
                Username = String.valueOf(textInputEditTextUsername.getText());
                Email = String.valueOf(textInputEditTextEmail.getText());
                Password = String.valueOf(textInputEditTextpassword.getText());


                if (Mobile.equals("")) {
                    textInputEditTextMobile.setError("Empty mobile number");
                    return;
                } else if (Username.equals("")) {
                    textInputEditTextUsername.setError("Empty sername");
                    return;
                } else if (Email.equals("")) {
                    textInputEditTextEmail.setError("Empty email");
                    return;
                } else if (Password.equals("")) {
                    textInputEditTextpassword.setError("Empty password");
                    return;
                }else {



                    Doctor doctor;
                    doctor = new Doctor(Mobile, Username, Email, Password);

                    FirebaseUser firebaseDoctor = new FirebaseUser(doctor, "users", Registration.this);
                    firebaseDoctor.doctorFirestoreReg();


                }

                /*if(!Mobile.equals("") && !Username.equals("") && !Password.equals("") && !Email.equals("")) {

                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "username";
                            field[1] = "mobile";
                            field[2] = "email";
                            field[3] = "password";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = Username;
                            data[1] = Mobile;
                            data[2] = Email;
                            data[3] = Password;

                            PutData putData = new PutData("https://10.249.64.180/Reg/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")){

                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),Login.class);
                                        startActivity(intent);
                                        finish();

                                    }else {
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    }


                                }
                            }//End Write and Read data with U

                        }

                    });
                }else {
                    Toast.makeText(getApplicationContext(),"All field required",Toast.LENGTH_SHORT).show();
                }

                 */


            }
        });




        //btnSelect = (Button) findViewById(R.id.btnSelectPhoto);
        btnSelect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        ivImage = (ImageView) findViewById(R.id.ivImage);
        ivImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(Registration.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ivImage.setImageBitmap(bm);
        
           
    }
    class Doctor {
        public String ID;
        public String fname;
        public String email;
        public String cell_no;
        public ArrayList<String> patient_ID;


        public Doctor(String ID, String fname, String email, String cell_no) {

            this.ID = ID;
            this.fname = fname;

            this.email = email;
            this.cell_no = cell_no;

            this.patient_ID = new ArrayList<>();

        }

        // VERY IMPORTANT: Java JSON deserialization needs a no-argument constructor in order to deserialize custom objects.
        // If you do not include one, your app will crash when you try to deserialize a custom class.
        public Doctor() {

        }


    }

    static class DInfo extends MainActivity.DInfo {

        public String fname;
        public String cell_no;
        public String ema;
        public String doc_ID;
        //public String qual;

        public DInfo(String fname, String doc_ID,String cell_no, String ema){
            this.fname=fname;
            this.cell_no = cell_no;
            this.doc_ID = doc_ID;
            this.ema = ema;

        }

        public DInfo(){

        }

    }

}
