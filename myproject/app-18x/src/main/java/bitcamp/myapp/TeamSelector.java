package bitcamp.myapp;

import java.util.ArrayList;
import java.util.Iterator;

public class TeamSelector {
  public static void main(String[] args) throws Exception{
    String[] students = new String[] {
            //"강승민", "김도현", "김주영",
            //"박기웅", "신동억", "조연식", "조왕휘", "차기석", "황보빈", "황하성"
            "배태선",
            "이다훈",
            "임하형"

//            "김정현",
//            "김태현",
//            "임성철"
            };

    selectTeam(students);
  }

  public static void selectTeam(String[] students) throws Exception {
    ArrayList<String> list = new ArrayList<>();
    for (String student : students) {
      list.add(student);
    }

    int count = 1;
    while (list.size() > 0) {
      int r = (int) (Math.random() * 100 + 1);
      int no = 0;
      for(int i = 0; i < r; i++) {
        no = (int) (Math.random() * list.size());
      }
      System.out.print(list.remove(no) + " ");
      if (count % 3 == 0) {
        System.out.println();
      }
      count++;
      Thread.sleep(5000);
    }

  }

}
