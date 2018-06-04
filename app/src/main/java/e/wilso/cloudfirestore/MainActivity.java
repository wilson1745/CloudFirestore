package e.wilso.cloudfirestore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

   String[] item = {"新增書籍", "會員資料"};

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      final ListView list = findViewById(R.id.list);
      final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, item);
      list.setAdapter(adapter);
      list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(MainActivity.this, adapter.getItem(position).toString(), Toast.LENGTH_LONG).show();
            action(position);
         }
      });
   }

   private void action(int position) {
      switch (position) {
         case 0:
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivity(intent);
            break;
         case 1:
            break;
         default:
            break;
      }
   }
}
