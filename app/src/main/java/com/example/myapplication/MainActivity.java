package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQ_CAMERA_IMAGE = 101;
    private LinearLayout parentLl;

    private List<Ticket> mTicketList = new ArrayList<>();
    private Animation rotateDown, rotateUp;

    File mediaFile;
    String timeStamp;
    Uri fileUri;
    String imageClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initAnimation();
        Ticket ticket = new Ticket();
        ticket.setTicketHeading("Substation-wall");
        ticket.setStringListOption(Arrays.asList("Damaged", "Not Applicable", "Clear"));

        mTicketList.add(ticket);

        Ticket ticket1 = new Ticket();
        ticket1.setTicketHeading("Water Logging");
        ticket1.setStringListOption(Arrays.asList("Prone", "Clear"));

        mTicketList.add(ticket1);

        Ticket ticket2 = new Ticket();
        ticket2.setTicketHeading("Vegetation");
        ticket2.setStringListOption(Arrays.asList("Moderate", "high", "clear"));

        mTicketList.add(ticket2);

        Ticket ticket3 = new Ticket();
        ticket3.setTicketHeading("Two");
        ticket3.setStringListOption(Arrays.asList("Direct Theft", "On In Day Time", "clear"));

        mTicketList.add(ticket3);

        Ticket ticket4 = new Ticket();
        ticket4.setTicketHeading("Three");
        ticket4.setStringListOption(Arrays.asList("Direct Theft", "On In Day Time", "clear"));

        mTicketList.add(ticket4);

        Ticket ticket5 = new Ticket();
        ticket5.setTicketHeading("FIVE");
        ticket5.setStringListOption(Arrays.asList("Direct Theft", "On In Day Time", "clear"));

        mTicketList.add(ticket5);

        Ticket ticket6 = new Ticket();
        ticket6.setTicketHeading("Six");
        ticket6.setStringListOption(Arrays.asList("Direct Theft", "On In Day Time", "clear"));
        mTicketList.add(ticket6);



        Ticket ticket7 = new Ticket();
        ticket7.setTicketHeading("Seven");
        ticket7.setStringListOption(Arrays.asList("Direct Theft", "On In Day Time", "clear"));

        mTicketList.add(ticket7);

        Ticket ticket8 = new Ticket();
        ticket8.setTicketHeading("Eight Light");
        ticket8.setStringListOption(Arrays.asList("Direct Theft", "On In Day Time", "clear"));

        mTicketList.add(ticket8);

        Ticket ticket9 = new Ticket();
        ticket9.setTicketHeading("Nine Light");
        ticket9.setStringListOption(Arrays.asList("Direct Theft", "On In Day Time", "clear"));

        mTicketList.add(ticket9);

        parentLl = findViewById(R.id.parent_ll);

        for (Ticket t : mTicketList) {

            addLayout(t.getTicketHeading(), t.getStringListOption());
        }

    }

    private void initAnimation() {

        rotateDown = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_down);
        rotateUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_up);
    }

    private void addLayout(String heading, List<String> mStringListOption) {
        View view = LayoutInflater.from(this).inflate(R.layout.one_layout, parentLl, false);

        TextView headingTv = view.findViewById(R.id.heading_tv);


        ImageView iv_photo = view.findViewById(R.id.taken_iv);

        RadioGroup radioGroupOption = view.findViewById(R.id.option_rg);


        headingTv.setText(heading);


        for (String s : mStringListOption) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(s);
            radioGroupOption.addView(radioButton);
        }


        headingTv.setOnClickListener(mOnClickListener);
        iv_photo.setOnClickListener(mOnClickListener);

        iv_photo.setTag(heading);

        parentLl.addView(view);
    }


    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.heading_tv:

                    String text = ((TextView) v).getText().toString();


                    for (Ticket t : mTicketList) {

                        if (t.getTicketHeading().equals(text)) {

                            Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();

                            LinearLayout linearLayout = parentLl.getChildAt(mTicketList.indexOf(t)).findViewById(R.id.child_layout_ll);
                            ImageView ivDropDown = parentLl.getChildAt(mTicketList.indexOf(t)).findViewById(R.id.iv_drop_down);


                            if (linearLayout.getVisibility() == View.GONE) {
                                linearLayout.setVisibility(View.VISIBLE);
                                AnimClass.expand(linearLayout);
                                ivDropDown.startAnimation(rotateUp);
                            } else {
                                AnimClass.collapse(linearLayout);
                                ivDropDown.startAnimation(rotateDown);


                            }
                        } else {
                            LinearLayout linearLayout = parentLl.getChildAt(mTicketList.indexOf(t)).findViewById(R.id.child_layout_ll);
                            ImageView ivDropDown = parentLl.getChildAt(mTicketList.indexOf(t)).findViewById(R.id.iv_drop_down);
                            if (linearLayout.getVisibility() == View.VISIBLE) {
                                AnimClass.collapse(linearLayout);
                                ivDropDown.startAnimation(rotateDown);
                            }
                        }
                    }
                    break;

                case R.id.taken_iv:

                    imageClicked = v.getTag().toString();

                    Intent intent = new Intent(
                            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFile();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, REQ_CAMERA_IMAGE);

                    break;
            }
        }
    };

    private Uri getOutputMediaFile() {
        try {
            File mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "demo");
            // Create a media file name
            timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(new Date());
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + timeStamp + ".jpg");
            if (mediaFile.exists() == false) {
                mediaFile.getParentFile().mkdirs();
                mediaFile.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", mediaFile);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQ_CAMERA_IMAGE) {
            if (resultCode == RESULT_OK) {
                Log.e(TAG, "onActivityResult: " + mediaFile);


                for (Ticket t : mTicketList) {
                    if (t.getTicketHeading().equals(imageClicked)) {
                        ImageView imageTaken = parentLl.getChildAt(mTicketList.indexOf(t)).findViewById(R.id.taken_iv);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 8;

                        Bitmap bMap11 = BitmapFactory.decodeFile(mediaFile.getPath(), options);

                        imageTaken.setImageBitmap(bMap11);

                    }
                }


            }

        }
    }
}
