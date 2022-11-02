public class QuestionNode {
   public QuestionNode right;
   public QuestionNode left;
   public String data;

   public QuestionNode(String data) {
      this.data = data;
   }
 
   public QuestionNode(String data, QuestionNode left, QuestionNode right) {
      this(data);
      this.left = left;
      this.right = right;
   }
   
   public QuestionNode() {
      this(null);
   }
   
}