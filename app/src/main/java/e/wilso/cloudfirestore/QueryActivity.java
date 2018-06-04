package e.wilso.cloudfirestore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class QueryActivity extends AppCompatActivity {

   FirebaseFirestore db = FirebaseFirestore.getInstance();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_query);

      CollectionReference documentReference = db.collection("users");
      documentReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
         @Override
         public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
               for (DocumentSnapshot document : task.getResult()) {
                  Log.d("DocumentSnapshot", document.getId() + " => " + document.getData());
               }
            }
            else {
               Log.d("DocumentSnapshot", "Error getting documents: ", task.getException());
            }
         }
      });

      /*CollectionReference users = db.collection("users");
      Query query = users.limit(100);
      query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
         @Override
         public void onComplete(@NonNull Task<QuerySnapshot> task) {
            QuerySnapshot querySnapshot = task.isSuccessful() ? task.getResult() : null;

            // TODO: check if snapshot is null
            for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
               User user = documentSnapshot.toObject(User.class);
               Log.d("onComplete", "onCreate: user: " + user.getNickname());
            }
         }
      });*/
   }
}
