package com.danielappdev.fosterships;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class selectmenu extends AppCompatActivity {

    Button btnJoin;
    Button btnBook;
    Button btnAdminPage;
    Button btnDum;
    EditText inviteCode;
    TextView textview2;
    Integer eventID;
    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference defReference = database.getReference("Events");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectmenu);
        //Log.d("selectmenu", "runs on selectmenu");
        inviteCode = findViewById(R.id.txtInviteCode);
        btnJoin = findViewById(R.id.btnJoinSelect);
        btnBook = findViewById(R.id.btnBook);
        btnDum = findViewById(R.id.btnDummy);
        btnAdminPage = findViewById(R.id.btnAdminPage);
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        textview2 = findViewById(R.id.textView2);
        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        HashMap<String, Object> Member = new HashMap<>();
        Member.put("Player",android_id);
        defReference.child("Events").child("3518").updateChildren(Member);
        Log.d("t", android_id);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer input = Integer.parseInt((inviteCode.getText().toString()));
                if(input > 0){
                    CheckFireData(defReference, input);
                }
                else{
                    inviteCode.setError("Check if you have the proper invite code");
                }

            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookingActivity.class);
                startActivity(intent);
            }
        });
        btnAdminPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminPage.class);
                startActivity(intent);
            }
        });
        btnAdminPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminPage.class);
                startActivity(intent);
            }
        });
        btnDum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PutDummyData();
            }
        });




    }
    private void ShowDialog(String title,String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(selectmenu.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    private void CheckFireData(final DatabaseReference reference, final Integer eventIDCurrent) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot snapshot) { //snapshot is the root reference
                int test = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    int eventID = (ds.child("eventID").getValue(Integer.class));
                    if (eventID == eventIDCurrent) {
                        Event NewEvent = new Event(eventID);
                        mEditor = mPref.edit();
                        mEditor.putInt("EventID",eventID);
                        String android_id = Settings.Secure.getString(getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        mEditor.putString("AndroidID",android_id);
                        mEditor.commit();
                        Log.d("myTag", "This is my message");
                        HashMap<String, Object> Member = new HashMap<>();
                        Member.put(android_id,android_id);
                        defReference.child(String.valueOf(eventID)).child("Players").updateChildren(Member);
                        Intent intent = new Intent(getApplicationContext(), normaluserwaitingscreen.class);

                        startActivity(intent);

                        test = 1;
                        break;
                    }
//                    else {
//
//                        ShowDialog("EventID not found!","EventID was not found... Please try again or look for your event organisers.");
//
//                    }
                }
                Log.d("test", "test"+test);
                if (test==0){
                    ShowDialog("EventID not found!","EventID was not found... Please try again or look for your event organisers.");}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void PutDummyData(){
        HashMap<String, Object> EventID = new HashMap<>();
        HashMap<String, Object> EventStatus = new HashMap<>();
        EventID.put("EventID",3518);
        EventStatus.put("GameStatus","Ready");
        defReference.child("3518").updateChildren(EventID);
        defReference.child("3518").updateChildren(EventStatus);

        HashMap<String, Object> TeamName = new HashMap<>();
        HashMap<String, Object> TeamAuthcode = new HashMap<>();
        HashMap<String, Object> NoOFAuths = new HashMap<>();
        HashMap<String, Object> Score = new HashMap<>();
        HashMap<String, Object> Round = new HashMap<>();
        TeamName.put("TeamName","Team banana");
        TeamAuthcode.put("TeamAuthCode","#frx61");
        NoOFAuths.put("NoOfAuths",3);
        Score.put("Score",0);
        Round.put("Round",1);
        defReference.child("3518").child("Teams").child("Team banana").updateChildren(TeamName);
        defReference.child("3518").child("Teams").child("Team banana").updateChildren(TeamAuthcode);
        defReference.child("3518").child("Teams").child("Team banana").updateChildren(NoOFAuths);
        defReference.child("3518").child("Teams").child("Team banana").updateChildren(Score);
        defReference.child("3518").child("Teams").child("Team banana").updateChildren(Round);

        HashMap<String, Object> Member1role = new HashMap<>();
        HashMap<String, Object> Member1round = new HashMap<>();
        Member1role.put("Role",1);
        Member1round.put("Round",1);
        HashMap<String, Object> Member2role = new HashMap<>();
        HashMap<String, Object> Member2round = new HashMap<>();
        Member2role.put("Role",2);
        Member2round.put("Round",1);
        defReference.child("3518").child("Teams").child("Team banana").child("Members").child("9258d7d86f9b8932").updateChildren(Member1role);
        defReference.child("3518").child("Teams").child("Team banana").child("Members").child("9258d7d86f9b8932").updateChildren(Member1round);
        defReference.child("3518").child("Teams").child("Team banana").child("Members").child("8a8c1ff831636d1e").updateChildren(Member2role);
        defReference.child("3518").child("Teams").child("Team banana").child("Members").child("8a8c1ff831636d1e").updateChildren(Member2round);

    }


}
