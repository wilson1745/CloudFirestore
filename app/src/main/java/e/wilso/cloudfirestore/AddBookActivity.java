package e.wilso.cloudfirestore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddBookActivity extends AppCompatActivity {

   private EditText ed_book, ed_author;
   private DatePicker ed_date;
   private Button button;
   private String sbook, sname, sdate;
   Intent intent;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_add_book);

      findView();

      button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // parse date into String
            parseString();

            if(sbook.isEmpty() || sname.isEmpty()) {
               AlertDialog.Builder builder = new AlertDialog.Builder(AddBookActivity.this);
               builder.setMessage("Item Cannot Be Empty")
                       .setNegativeButton("Retry", null)
                       .create()
                       .show();
            }

            else {
               // add information into Cloud Firebase
               addAction(sbook, sname, sdate);
               // 回傳result回上一個activity
               AddBookActivity.this.setResult(RESULT_OK, intent);
               // 關閉activity
               AddBookActivity.this.finish();
            }
         }
      });
   }

   private void findView() {
      ed_book = findViewById(R.id.edbookname);
      ed_author = findViewById(R.id.edauthor);
      ed_date = findViewById(R.id.eddate);
      button = findViewById(R.id.button2);
      Log.d("findView", "findView good");
   }

   private void parseString() {
      switchDate();
      sbook = ed_book.getText().toString();
      sname = ed_author.getText().toString();
      Log.d("parseString", "parseString good");
   }

   private void switchDate() {
      int year = ed_date.getYear();
      int month = ed_date.getMonth();
      int day = ed_date.getDayOfMonth();
      sdate = year + "年" + month + "月" + day + "日";
   }

   public void addAction(String sbook, String sname, String sdate) {
      FirebaseFirestore db = FirebaseFirestore.getInstance();
      Map<String, Object> book = new HashMap<String, Object>();

      book.put("title", sbook);
      book.put("author", sname);
      book.put("release", sdate);

      db.collection("books").add(book).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
         @Override
         public void onComplete(@NonNull Task<DocumentReference> task) {
            if(task.isSuccessful()) Log.d("onComplete", "Book Added!");
            else Log.d("onComplete", "Error when adding book");
         }
      });
   }
}
