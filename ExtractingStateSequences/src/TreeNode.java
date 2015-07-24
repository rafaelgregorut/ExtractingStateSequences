import java.util.ArrayList;


public class TreeNode {

	private ArrayList<TreeNode> children;
	
	private TreeNode parent;
	
	private Event content;
	
	//Creates a node with content
	TreeNode(Event cont) {
		children = new ArrayList<TreeNode>();
		parent = null;
		content = cont;
	}
	
	//Creates empty tree node. Different than leaf!
	TreeNode() {
		children = new ArrayList<TreeNode>();
		parent = null;
		content = null;
	}

	public ArrayList<TreeNode> getChildren() {
		return children;
	}
	
	public boolean childrenContains(Event item) {
		for (int i = 0; i < children.size(); i++) {
			if (item.equals(children.get(i).getContent()))
				return true;
		}
		return false;
	}
	
	public TreeNode getChildWith(Event item) {
		for (int i = 0; i < children.size(); i++) {
			if (item.equals(children.get(i).getContent()))
				return children.get(i);
		}
		return null;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public Event getContent() {
		return content;
	}

	public void setContent(Event content) {
		this.content = content;
	}
	
	public boolean isLeaf() {
		return (children.size() == 0);
	}
	
	public void addChild(Event childContent) {
		TreeNode child = new TreeNode(childContent);
		child.setParent(this);
		this.children.add(child);
	}
}
