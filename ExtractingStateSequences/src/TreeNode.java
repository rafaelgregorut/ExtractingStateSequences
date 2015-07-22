import java.util.ArrayList;


public class TreeNode<T> {

	private ArrayList<TreeNode<T>> children;
	
	private TreeNode<T> parent;
	
	private T content;
	
	TreeNode(T cont) {
		children = new ArrayList<TreeNode<T>>();
		parent = null;
		content = cont;
	}

	public ArrayList<TreeNode<T>> getChildren() {
		return children;
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}
	
	public boolean isLeaf() {
		return (children.size() == 0);
	}
	
	public void addChild(T childContent) {
		TreeNode<T> child = new TreeNode<T>(childContent);
		child.setParent(this);
		this.children.add(child);
	}
	
}
