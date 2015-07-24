import java.util.ArrayList;
import java.util.Iterator;


public class PrefixTreeAcceptor {

	private ArrayList<EventList> eventLists;
	
	private TreeNode root;
	
	PrefixTreeAcceptor(ArrayList<EventList> els) {
		this.eventLists = els;
		buildTree();
	}
	
	private void buildTree() {
		root = new TreeNode();
		for (Iterator<EventList> i = eventLists.iterator(); i.hasNext();) {
			insereLista(i.next(),root);
		}
	}
	
	private void insereLista(EventList el, TreeNode t) {
		for (Iterator<Event> i = el.iterator(); i.hasNext();) {
			insereEvento(i.next(),t);
		}
	}
	
	private void insereEvento(Event e, TreeNode t) {
		if (t.isLeaf() || !(t.childrenContains(e))) {
			t.addChild(e);
		} else {
			insereEvento(e,t.getChildWith(e));
		}
	}
	
	public TreeNode getTree() {
		return root;
	}
	
	public void printTree() {
		printTreeRec(root,0);
	}
	
	private void printTreeRec(TreeNode t, int quantEspacos) {
		for (int i = 0; i < quantEspacos; i++)
			System.out.print(" ");
		if (t.getContent() != null)
			System.out.println(t.getContent().getName());
		else
			System.out.println("nullContent");
		if(!(t.isLeaf())) {
			ArrayList<TreeNode> children = t.getChildren();
			for (int j = 0; j < children.size(); j++) {
				printTreeRec(children.get(j),quantEspacos+1);
			}
		}
	}
}
