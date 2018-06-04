package e.wilso.cloudfirestore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;

public class AddMemberActivity extends AppCompatActivity {

   private EditText ed_nickname, ed_email, ed_age, ed_gender;
   private Button btnadd;
   private String ednickname, edmail, edage, edgender;
   Intent intent;
   FirebaseFirestore db = FirebaseFirestore.getInstance();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_add_member);

      //directAdd();
      addUsers();
      findView();

      btnadd.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            parseString();
            if(ednickname.isEmpty() || edmail.isEmpty() || edage.isEmpty() || edgender.isEmpty()) {
               AlertDialog.Builder builder = new AlertDialog.Builder(AddMemberActivity.this);
               builder.setMessage("Event Cannot Be Empty")
                       .setNegativeButton("Retry", null)
                       .create()
                       .show();
            }
            else {
               // add information into Cloud Firebase
               addAction(ednickname, edmail, edage, edgender);
               // 回傳result回上一個activity
               AddMemberActivity.this.setResult(RESULT_OK, intent);
               // 關閉activity
               AddMemberActivity.this.finish();
            }
         }
      });
   }

   private void findView() {
      ed_nickname = findViewById(R.id.ednickname);
      ed_email = findViewById(R.id.edemail);
      ed_age = findViewById(R.id.edage);
      ed_gender = findViewById(R.id.edgender);
      btnadd = findViewById(R.id.button3);
   }

   private void parseString() {
      ednickname = ed_nickname.getText().toString();
      edmail = ed_email.getText().toString();
      edage = ed_age.getText().toString();
      edgender = ed_gender.getText().toString();
      Log.d("parseString", "parseString good");
   }

   private void addAction(String ednickname, String edmail, String edage, String edgender) {
      User user = new User(ednickname, edmail, edage, edgender);

      /*FirebaseFirestore db = FirebaseFirestore.getInstance();
      Map<String, Object> users = new HashMap<String, Object>();
      users.put("uid", user.getUid());
      users.put("nickname", user.getNickname());
      users.put("email", user.getEmail());
      users.put("age", user.getAge());
      users.put("gender", user.getGender());*/

      // add object directly
      db.collection("users").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
         @Override
         public void onComplete(@NonNull Task<DocumentReference> task) {
            if(task.isSuccessful()) Log.d("onComplete", "User Added");
            else Log.d("onComplete", "Error when adding user");
         }
      });
   }

   private void directAdd() {
      User wilson = new User("Wilson", "wilson155079@gmail.com", "28", "Male");

      db.collection("users").add(wilson).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
         @Override
         public void onComplete(@NonNull Task<DocumentReference> task) {
            if(task.isSuccessful()) {
               Log.d("onComplete", "User Added");
            }
         }
      });
   }

   private void addUsers() {
      List<User> users = Arrays.asList(
              new User("Wilson", "wilson155079@gmail.com", "28", "Male"),
              new User("Jane", "jane155079@gmail.com", "54", "Female"),
              new User("Alex", "alex155079@gmail.com", "23", "Male"),
              new User("Bob", "bob155079@gmail.com", "98", "Male")
      );

      for(User user: users) {
         db.collection("users").add(user);
      }

      CollectionReference userss = db.collection("users");
      Query query = userss.limit(100);
      query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
         @Override
         public void onComplete(@NonNull Task<QuerySnapshot> task) {
            QuerySnapshot querySnapshot = task.isSuccessful() ? task.getResult() : null;

            int n = 0;

            // TODO: check if snapshot is null
            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
               //User user = document.toObject(User.class);
               User user = new User("Wilson", "wilson155079@gmail.com", "28", "Male");
               n++;
               Log.d("onComplete", "onCreate: user: " + user.getNickname() + " / " + n);
            }
         }
      });
   }
}
