package e.wilso.cloudfirestore;

public class User {
   private static int num = 0;
   private String nickname, email, age, gender;
   private int uid;

   public User(String nickname, String email, String age, String gender) {
      this.uid = num;
      this.nickname = nickname;
      this.email = email;
      this.gender = gender;
      this.age = age;
      num++;
   }

   public int getUid() {
      return uid;
   }

   public String getNickname() {
      return nickname;
   }

   public String getEmail() {
      return email;
   }

   public String getGender() {
      return gender;
   }

   public String getAge() {
      return age;
   }
}
