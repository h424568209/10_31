
import java.util.*;

public class LeeCode {
    public int calPoints(String[] ops) {
        Stack<Integer> stack = new Stack<>();
        for(String op:ops){
            if(op.equals("+")){
                int t = stack.pop();
                int x = stack.peek()+t;
                stack.push(t);
                stack.push(x);
            }
            else if(op.equals("D")){
                stack.push(stack.peek()*2);
            }
            else if(op.equals("C")){
                stack.pop();
            }else{
                stack.push(Integer.valueOf(op));
            }
        }
        int sum = 0 ;
        while(!stack.isEmpty()){
            sum+=stack.pop();
        }
        return sum;
    }
    /**
     * 删除字符串中所有相邻的重复项
     * @param S 字符串
     * @return 删除后的字符串 字符串的原顺序不变
     */
    public static String removeDuplicates(String S) {
        Stack<Character> stack = new Stack<>();
        char[]res= S.toCharArray();
        StringBuilder sb = new StringBuilder();
       for(int i = 0 ;i<res.length; i ++){
           char c= res[i];
           if(stack.isEmpty()){
               stack.push(c);
               continue;
           }
           if(c == stack.peek()){
               stack.pop();
           }else{
               stack.push(c);
           }
       }
       while(!stack.isEmpty()){
           sb.append(stack.pop());
       }
       return sb.reverse().toString();
    }
    /**
     * 验证二叉树前序序列化
     * @param preorder 关于二叉树的字符串
     * @return 判断是不是二叉树的前序序列化
     */
    public boolean isValidSerialization(String preorder) {
        String[] s = preorder.split(",");
        if(s.length ==1 && s[0].equals("#")) return true;
        if(s[0].equals("#")&&s.length%2==0) return  false;
        int count = 1;
        for (String value : s) {
            count--;
            if (!value.equals("#")) {
                count += 2;
            }
            if (count < 0) return false;
        }

        return count == 0;
    }
    /**
     * 二叉树的锯齿层次遍历
     * 将二叉树的遍历基础上进行判断 若为奇数则进行逆序，否则直接进行插入
     * @param root 根节点
     * @return 遍历的结果
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> list=  new ArrayList<>();
        if(root==null){
            return null;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;
        while(!queue.isEmpty()){
            List<Integer> tem = new LinkedList<>();
            int l = queue.size();
            for(int i = 0 ;i<l ;i++){
                TreeNode node = queue.poll();
                if(level%2 == 0){
                    tem.add(node.val);
                }else{
                    tem.add(0,node.val);
                }
                if(node.left !=null){
                    queue.add(node.left);
                }
                if(node.right!=null){
                    queue.add(node.right);
                }
            }
            level++;
            list.add(tem);
        }
        return list;
    }

    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
  TreeNode root = null;
    private TreeNode createTree(){
        TreeNode a = new TreeNode(1);
        TreeNode b = new TreeNode(2);
        TreeNode c = new TreeNode(4);
        TreeNode d = new TreeNode(3);
        TreeNode e = new TreeNode(5);
        a.left = b; a.right = c;
        c.left= d;
        c.right = e;
        return a;

    }

    /**
     * 当访问的结点大于结点的层次就加一个新的空链表，将当前的结点插入到链表中
     *  二叉树的层次遍历
     * @param root 根节点
     * @return 二叉树的层次遍历
     */
    public List<List<Integer>> zigzagLevelOrderD(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
      if(root == null){
          return list;
      }
      helper(list,root,0);
      return list;
    }

    private void helper(List<List<Integer>> list, TreeNode node, int level) {
        if(list.size() == level){
            list.add(new ArrayList<>());
        }
        list.get(level).add(node.val);
        if(node.left!=null)
        helper(list,node.left,level+1);
        if(node.right!=null)
        helper(list,node.right,level+1);
    }

    /**
     *  非递归层次遍历二叉树
     *  将树上的节点按照层次放在队列中，队列中的元素遵循先进先出的原则；
     *  初始化队列中只有一个根节点，和一个层次编号level
     *  当队列不空的时候，在链表中添加一个新的链表，开始当前层次的算法；当前的元素个数就是队列的大小；
     *  将这些元素弹出，并且加入到链表中对应的层次中
     *  将它的孩子结点进行进队列
     *  进入下一层level++
     * @param root  根节点
     * @return 递归的结果
     */
    public List<List<Integer>> zigzagLevelOrderF(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        if(root == null){
            return list;
        }
        Queue<TreeNode>queue = new LinkedList<>();
        queue.add(root);
        int level = 0;
        while(!queue.isEmpty()){
            list.add(new ArrayList<>());
            int l = queue.size();
            for(int i = 0 ;i<l;i++){
                TreeNode node = queue.remove();
                list.get(level).add(node.val);
                if(node.left!=null){
                    queue.add(node.left);
                }
                if(node.right!=null ){
                    queue.add(node.right);
                }
            }
            level++;
        }
        return list;
    }
    /**
     *  逆波兰表达式求值
     * @param tokens 包括数字和加减乘除符号的数组
     * @return  计算结果
     */
    public static int evalRPN(String[] tokens) {
            Stack<Integer> stack = new Stack<>();
            for(int i = 0 ;i<tokens.length; i++){
                int sum = 0;
                    switch (tokens[i]){
                        case "*":
                            sum = stack.pop()*stack.pop();
                            stack.push(sum);
                            break;
                        case "+":
                            sum = stack.pop()+stack.pop();
                            stack.push(sum);
                            break;
                        case "-":
                            int t = stack.pop();
                            sum = stack.pop()-t;
                            stack.push(sum);
                            break;
                        case "/":
                            int temp = stack.pop();
                            sum = stack.pop()/temp;
                            stack.push(sum);
                            break;
                            default:stack.push(Integer.valueOf(tokens[i]));break;
                    }
                }
            return stack.peek();
    }

    public static void main(String[] args) {
 //       String []tokens = {"4", "13", "5", "/", "+"};
   //     System.out.println(LeeCode.evalRPN(tokens));
        LeeCode l = new LeeCode();
        TreeNode root = l.createTree();
        System.out.println(l.zigzagLevelOrderF(root));
        System.out.println(l.zigzagLevelOrder(root));
        System.out.println(LeeCode.removeDuplicates("abbaca"));
        String []a = {"5","2","C","D","+"};
        System.out.println(l.calPoints(a));
    }
}
