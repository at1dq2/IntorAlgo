package binarysearchtree;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

import org.junit.Assert;
/**
Let x be a node in a binary search tree.
If y is a node in the left subtree of x, then key[y] <= key[x].
If y is a node in the right subtree of x, then key[x] <= key[y].
*/
public class BSTree {
	public enum Color {
		RED,BLACK;
		
	}
	/*
	public BSTreeNode NIL;
	{
		NIL = new BSTreeNode(null);
		NIL.color = Color.BLACK;
	}
	*/
	
	public void inorderTreeWalk(BSTreeNode t) {
		if(t != null) {
			inorderTreeWalk(t.left);
			System.out.println(" " + t.toString());
			inorderTreeWalk(t.right);
		}
	}
	
	public BSTreeNode treeSearch(BSTreeNode root, Comparable<Integer> searchKey) {
		if(root == null || searchKey.equals(root.key)) {
			return root;
		}
		if(searchKey.compareTo(root.key) < 0) {
			//find it on left tree
			return treeSearch(root.left, searchKey);
		}
		else {
			//find it on right tree
			return treeSearch(root.right, searchKey);
		}
	}
	
	/**
	 * same logic as treeSearch, but unroll recursion
	 * @param root
	 * @param searchKey
	 * @return
	 */
	public BSTreeNode iterativeTreeSearch(BSTreeNode root, Comparable<Object> searchKey) {
		while(root != null && !searchKey.equals(root.key)) {
			if(searchKey.compareTo(root.key) < 0) {
				root = root.left;
			}
			else {
				root = root.right;
			}
		}
		return root;
	}
	
	public BSTreeNode treeMin(BSTreeNode root) {
		while(root.left != null) {
			root = root.left;
		}
		return root;
	}
	
	public BSTreeNode treeMax(BSTreeNode root) {
		while(root.right != null) {
			root = root.right;
		}
		return root;
	}
	
	public BSTreeNode treeSuccessor(BSTreeNode x) {
		if(x.right != null) {
			return treeMin(x.right);
		}
		BSTreeNode y = x.parent;
		while(y != null && y.right == x) {
			x = y;
			y = y.parent;
		}
		return x;
	}
	
	public BSTreeNode treePredecessor(BSTreeNode x) {
		if(x.left != null) {
			return treeMax(x.left);
		}
		BSTreeNode y = x.parent;
		while(y != null && y.left == x) {
			x = y;
			y = y.parent;
		}
		return x;
	}
	
	
	public void treeInsert(BSTreeNode root, BSTreeNode z) {
		BSTreeNode y = null;
		BSTreeNode x = root;
		while(x != null) {
			y = x;
			if(z.key.compareTo(x.key) < 0) {
				x = x.left;
			}
			else {
				x = x.right;
			}
		}
		z.parent = y;
		if(y == null) {
			root = z;
		}
		else if(z.key.compareTo(y.key) < 0){
			y.left = z;
		}
		else {
			y.right = z;
		}
	}
	
	
	/**
	 * Red-black trees are one of many search-tree schemes that are
	"balanced" in order to guarantee that basic dynamic-set operations take O(lg n) time in the
	worst case.
	1. Every node is either red or black.
	2. The root is black.
	3. Every leaf (NIL) is black.
	4. If a node is red, then both its children are black.
	5. For each node, all paths from the node to descendant leaves contain the same number
	of black nodes.
	return root node
	**/
	public BSTreeNode rbTreeInsert(BSTreeNode root, BSTreeNode z) {
		treeInsert(root, z);
		z.color = Color.RED;
		return rbInsertFixUp(root, z);
	}
	public BSTreeNode rbInsertFixUp(BSTreeNode root, BSTreeNode z) {
		//z is not root, because z is always red,
		//so while exit condition is z's parent color is different
		if(z != root) {
			//z parent means z is null right now after some rotations
			while(z.parent != null && z.parent.color == Color.RED) {
				if(z.parent == z.parent.parent.left) {
					BSTreeNode y = z.parent.parent.right;
					if(y != null && y.color == Color.RED) {
						//case 1
						z.parent.color = Color.BLACK;
						y.color = Color.BLACK;
						z.parent.parent.color = Color.RED;
						z = z.parent.parent;
					}
					else {
						if(z == z.parent.right) {
						//case 2
							z = z.parent;
							root = leftRotate(root, z);
						}
						//case 3
						z.parent.color = Color.BLACK;
						z.parent.parent.color = Color.RED;
						root = rightRotate(root, z.parent.parent);
					}
				}
				else {
					BSTreeNode y = z.parent.parent.left;
					if(y != null && y.color == Color.RED) {
						//case 1
						z.parent.color = Color.BLACK;
						y.color = Color.BLACK;
						z.parent.parent.color = Color.RED;
						z = z.parent.parent;
					}
					else {
						if(z == z.parent.left) {
						//case 2
							z = z.parent;
							root = rightRotate(root, z);
						}
						//case 3
						z.parent.color = Color.BLACK;
						z.parent.parent.color = Color.RED;
						root = leftRotate(root, z.parent.parent);
					}
				}
			}
		}
		root.color = Color.BLACK;
		return root;
	}

	public BSTreeNode rbTreeDelete(BSTreeNode root, BSTreeNode z) {
		BSTreeNode y;
		if(z.left == null || z.right == null) {
			y = z;
		}
		else {
			// z has two children
			y = treeSuccessor(z);
		}
		BSTreeNode x;
		if(y.left != null) {
			x = y.left;
		}
		else {
			x = y.right;
		}
		x.parent = y.parent;
		if(y.parent == null) {
			root = x;
		}
		else {
			if(y == y.parent.left) {
				y.parent.left = x;
			}
			else {
				y.parent.right = x;
			}
		}
		if(y != z) {
			z.key = y.key;
		}
		if(y.color == Color.BLACK) {
			root = rbDeleteFixUp(root, x);
		}
		return root;
	}
	
	public BSTreeNode rbDeleteFixUp(BSTreeNode root, BSTreeNode x) {
		while(x != root && x.color == Color.BLACK) {
			if(x == x.parent.left) {
				BSTreeNode w = x.parent.right;
				if(w.color == Color.RED) {
					//case 1
					w.color = Color.BLACK;
					x.parent.color = Color.RED;
					root = this.leftRotate(root, x.parent);
					w = x.parent.right;
				}
				if(w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
					//case 2
					w.color = Color.RED;
					x = x.parent;
				}
				else {
					if(w.right.color == Color.BLACK) {
						//case 3
						w.left.color = Color.BLACK;
						w.color = Color.RED;
						root = this.rightRotate(root, w);
						w = x.parent.right;
					}
					//case 4
					w.color = x.parent.color;
					x.parent.color = Color.BLACK;
					w.right.color = Color.BLACK;
					this.leftRotate(root, x.parent);
					x = root;
				}
			}
			else {
				BSTreeNode w = x.parent.left;
				if(w.color == Color.RED) {
					//case 1
					w.color = Color.BLACK;
					x.parent.color = Color.RED;
					root = this.rightRotate(root, x.parent);
					w = x.parent.left;
				}
				if(w.right.color == Color.BLACK && w.left.color == Color.BLACK) {
					//case 2
					w.color = Color.RED;
					x = x.parent;
				}
				else {
					if(w.left.color == Color.BLACK) {
						//case 3
						w.right.color = Color.BLACK;
						w.color = Color.RED;
						root = this.leftRotate(root, w);
						w = x.parent.left;
					}
					//case 4
					w.color = x.parent.color;
					x.parent.color = Color.BLACK;
					w.left.color = Color.BLACK;
					this.rightRotate(root, x.parent);
					x = root;
				}
			}
		}
		return root;
	}
	
	@org.junit.Test
	public void rbTreeCreation() {
		//BSTreeNode NIL = new BSTreeNode(null);
		//NIL.color = Color.BLACK;
		BSTreeNode root = new BSTreeNode(new Integer(11));
		//root.parent = NIL;
		root.color = Color.BLACK;
		
		BSTree bsTree = new BSTree();
		
		//1, 2, 4, 5, 7, 8, 14, 15
		BSTreeNode node1 = new BSTreeNode(new Integer(1));
		BSTreeNode node2 = new BSTreeNode(new Integer(2));
		BSTreeNode node3 = new BSTreeNode(new Integer(4));
		BSTreeNode node4 = new BSTreeNode(new Integer(5));
		BSTreeNode node5 = new BSTreeNode(new Integer(7));
		BSTreeNode node6 = new BSTreeNode(new Integer(8));
		BSTreeNode node7 = new BSTreeNode(new Integer(14));
		BSTreeNode node8 = new BSTreeNode(new Integer(15));
		
		root = bsTree.rbTreeInsert(root, node1);
		root = bsTree.rbTreeInsert(root, node2);
		root = bsTree.rbTreeInsert(root, node3);
		root = bsTree.rbTreeInsert(root, node4);
		root = bsTree.rbTreeInsert(root, node5);
		root = bsTree.rbTreeInsert(root, node6);
		root = bsTree.rbTreeInsert(root, node7);
		root = bsTree.rbTreeInsert(root, node8);
		
		node6.color = Color.BLACK;
		
		assertTreeIsRedBlackTreeRecurisive(root);
	}
	
	@org.junit.Test
	public void rbTreeDeletion() {
		//BSTreeNode NIL = new BSTreeNode(null);
		//NIL.color = Color.BLACK;
		BSTreeNode root = new BSTreeNode(new Integer(11));
		//root.parent = NIL;
		root.color = Color.BLACK;
		
		BSTree bsTree = new BSTree();
		
		//1, 2, 4, 5, 7, 8, 14, 15
		BSTreeNode node1 = new BSTreeNode(new Integer(1));
		BSTreeNode node2 = new BSTreeNode(new Integer(2));
		BSTreeNode node3 = new BSTreeNode(new Integer(4));
		BSTreeNode node4 = new BSTreeNode(new Integer(5));
		BSTreeNode node5 = new BSTreeNode(new Integer(7));
		BSTreeNode node6 = new BSTreeNode(new Integer(8));
		BSTreeNode node7 = new BSTreeNode(new Integer(14));
		BSTreeNode node8 = new BSTreeNode(new Integer(15));
		BSTreeNode node9 = new BSTreeNode(new Integer(11));
		
		root = bsTree.rbTreeInsert(root, node1);
		root = bsTree.rbTreeInsert(root, node2);
		root = bsTree.rbTreeInsert(root, node3);
		root = bsTree.rbTreeInsert(root, node4);
		root = bsTree.rbTreeInsert(root, node5);
		root = bsTree.rbTreeInsert(root, node6);
		assertTreeIsRedBlackTreeRecurisive(root);
		root = bsTree.rbTreeInsert(root, node7);
		root = bsTree.rbTreeInsert(root, node8);
		root = bsTree.rbTreeInsert(root, node9);
		
		//node6.color = Color.BLACK;
		
		assertTreeIsRedBlackTreeRecurisive(root);
	}
	
	/**
	 * 
	 * @param root
	 * @return suppose left child and right child have same black nodes number, and return the number
	 */
	public int assertTreeIsRedBlackTreeRecurisive(BSTreeNode root) {
		if(root != null) {
			if(root.color == Color.RED) {
				if((root.left != null && root.right == null) || 
						(root.right != null && root.left == null)) {
					Assert.fail("parnet is red, then child should both exist or both non-exist and be black");
				}
				if(root.left != null) {
					//Assert.assertEquals(Color.BLACK, root.left.color);
					String message = "root key is "+root.key+"; root left child key is "+root.left.key;
					Assert.assertEquals(message, Color.BLACK, root.left.color);
				}
				if(root.right != null) {
					//Assert.assertEquals(Color.BLACK, root.right.color);
					String message = "root key is "+root.key+"; root right child key is "+root.right.key;
					Assert.assertEquals(message, Color.BLACK, root.right.color);
				}
			}
			
			int leftNum =  assertTreeIsRedBlackTreeRecurisive(root.left);
			int rightNum = assertTreeIsRedBlackTreeRecurisive(root.right);
			if(leftNum != rightNum) {
				Assert.fail("voliate property 5");
				return 0;
			}
			else {
				return root.color == Color.RED?leftNum:(leftNum+1);
			}
		}
		else {
			return 0;
		}
	}
	/**
	 * Red-black trees are one of many search-tree schemes that are
	"balanced" in order to guarantee that basic dynamic-set operations take O(lg n) time in the
	worst case.
	1. Every node is either red or black.
	2. The root is black.
	3. Every leaf (NIL) is black.
	4. If a node is red, then both its children are black.
	5. For each node, all paths from the node to descendant leaves contain the same number
	of black nodes.
	**/
	public void assertTreeIsRedBlackTree(BSTreeNode root) {
		class PassedbsTreeNode {
			BSTreeNode bsTreeNode;
			boolean isLeftVisited;
			boolean isRightVisited;
			public PassedbsTreeNode(BSTreeNode bsTreeNode) {
				this.bsTreeNode = bsTreeNode;
			}
			public String toString() {
				return bsTreeNode.toString() + "isLeftVisited:" + 
						isLeftVisited + " isRightVisited:" + isRightVisited;
			}
		}
		Assert.assertEquals(Color.BLACK, root.color);
		//PassedbsTreeNode nextCheckNode = root;
		
		Hashtable<BSTreeNode, Integer[]> numOfBlackNodeFromEachTreeNode = 
				new Hashtable<BSTreeNode, Integer[]>();
		ArrayList<PassedbsTreeNode> leafNodes = new ArrayList<PassedbsTreeNode>();
		
		PassedbsTreeNode nextCheckNode = new PassedbsTreeNode(root);
		Stack<PassedbsTreeNode> passedNodes = new Stack<PassedbsTreeNode>();
		while(nextCheckNode != null) {
			//check property  If a node is red, then both its children are black.
			if(nextCheckNode.bsTreeNode.color == Color.RED) {
				if((nextCheckNode.bsTreeNode.left != null && nextCheckNode.bsTreeNode.right == null) || 
						(nextCheckNode.bsTreeNode.right != null && nextCheckNode.bsTreeNode.left == null)) {
					Assert.fail("parnet is red, then child should both exist and be black");
				}
				if(nextCheckNode.bsTreeNode.left != null) {
					Assert.assertEquals(Color.BLACK, nextCheckNode.bsTreeNode.left.color);
				}
				if(nextCheckNode.bsTreeNode.right != null) {
					Assert.assertEquals(Color.BLACK, nextCheckNode.bsTreeNode.right.color);
				}
			}
			//iterate all node and save leaf node to list for further compute black node num on one path
			if(nextCheckNode.bsTreeNode.left == null && nextCheckNode.bsTreeNode.right == null) {
				leafNodes.add(nextCheckNode);
				PassedbsTreeNode tempNode = passedNodes.pop();
				if(tempNode.isLeftVisited && !tempNode.isRightVisited) {
					tempNode.isRightVisited = true;
					nextCheckNode = new PassedbsTreeNode(tempNode.bsTreeNode.right);
					passedNodes.add(tempNode);
				}
				else if(!tempNode.isLeftVisited && tempNode.isRightVisited) {
					tempNode.isLeftVisited = true;
					nextCheckNode = new PassedbsTreeNode(tempNode.bsTreeNode.left);
					passedNodes.add(tempNode);
				}
				else if(tempNode.isLeftVisited && tempNode.isRightVisited){
					if(tempNode.bsTreeNode.parent != null) {
						//tempNode2 is tempNode's parent
						PassedbsTreeNode tempNode2 = passedNodes.pop();
						nextCheckNode = tempNode2; 
					}
				}
			}
			//left is first priority, go left
			if(nextCheckNode.bsTreeNode.left != null && !nextCheckNode.isLeftVisited) {
				nextCheckNode.isLeftVisited = true;
				passedNodes.add(nextCheckNode);
				nextCheckNode = new PassedbsTreeNode(nextCheckNode.bsTreeNode.left);
			}
			else if(nextCheckNode.bsTreeNode.right != null && !nextCheckNode.isRightVisited) {
				//go right
				nextCheckNode.isRightVisited = true;
				passedNodes.add(nextCheckNode);
				nextCheckNode = new PassedbsTreeNode(nextCheckNode.bsTreeNode.right);
			}
			else {
				//go parent
				nextCheckNode = passedNodes.pop();
				if(nextCheckNode.bsTreeNode.parent == null) {
					//this node is root
					break;
				}
			}
		}
		
		for(PassedbsTreeNode leafNode:leafNodes) {
			BSTreeNode childNode = leafNode.bsTreeNode;
			while(childNode.parent != null) {
				if(childNode.color == Color.BLACK) {
					Integer[] blackNum = numOfBlackNodeFromEachTreeNode.get(childNode.parent);
					if(blackNum == null) {
						blackNum = new Integer[2];
						blackNum[0] = new Integer(0);
						blackNum[1] = new Integer(0);
					}
					if(childNode == childNode.parent.left) {
						blackNum[0] = blackNum[0] + 1;
					}	
					else if(childNode == childNode.parent.right) {
						blackNum[1] = blackNum[1] + 1;
					}
				}
				childNode = childNode.parent;
			}
		}
		
		System.out.println("out of loop");
		
	}
	
	public void treeDelete(BSTreeNode root, BSTreeNode z) {
		if(z != null) {
			if(z.left == null && z.right == null) {
				//z is leaf node, just delete it
				if(z.parent == null) {
					//z is root node
					root = null;
					z = null;
				}
				else {
					if(z.parent.left == z) {
						z.parent.left = null;
					}
					else if(z.parent.right == z) {
						z.parent.right = null;
					}
				}
			}
			else if(z.left == null || z.right == null) {
				//z has only one child
				BSTreeNode childNode = z.right!=null?z.right:z.left;
				childNode.parent = z.parent;
				if(z.parent.left == z) {
					z.parent.left = childNode;
				}
				else {
					z.parent.right = childNode;
				}
			}
			else {
				//z has two children, copy successor's data to z place and remove successor. or change pointer
				//here code is change pointer
				BSTreeNode successorNode = treeSuccessor(z);
				//change link on z to successor node 
				if(z.parent != null) {
					if(z.parent.left == z) {
						z.parent.left = successorNode;
					}
					else {
						z.parent.right = successorNode;
					}
				}
				z.left.parent = successorNode;
				if(z.right != successorNode) {
					z.right.parent = successorNode;
				}	
				
				BSTreeNode childChildNode  = successorNode.left != null?successorNode.left:successorNode.right;
				if(successorNode.parent.left == successorNode) {
					successorNode.parent.left = childChildNode;
				}
				else {
					successorNode.parent.right = childChildNode;
				}
				
				successorNode.parent = z.parent;
				successorNode.left = z.left;
				if(z.right != successorNode) {
					successorNode.right = z.right;
				}
			}
		}	
	}
	
	/**
	 * 
	 * @param root
	 * @param z, is pointer to deleted one
	 * @return
	 */
	public BSTreeNode updateTreeDelete(BSTreeNode root, BSTreeNode z) {
		BSTreeNode y;
		BSTreeNode x;
		if(z.left == null || z.right == null) {
			//if z is leaf node or single-child node
			y = z;
		}
		else {
			y = treeSuccessor(z);
		}
		//now, y is to be deleted node and y has only one child, left or right.
		//then connect y's child's parent to y's parent
		if(y.left != null) {
			x = y.left;
		}
		else {
			x = y.right;
		}
		//now, x is to be repoint to y's parent
		if(x != null) {
			x.parent = y.parent;
		}
		if(y.parent != null) {
			if(y == y.parent.left) {
				y.parent.left = x;
			}
			else {
				y.parent.right = x;
			}
		}
		//now copy data
		if(y != z) {
			//means z has two children, so y is successor of z
			z.key = y.key;
		}
		return y;
	}
	
	/**
	 * return root item
	 * @param root
	 * @param x
	 */
	public BSTreeNode leftRotate(BSTreeNode root, BSTreeNode x) {
		if(root != null && x != null && x.right != null) {
			BSTreeNode y = x.right;
			y.parent = x.parent;
			x.right = y.left;
			if(y.left != null) {
				y.left.parent = x;
			}
			if(x.parent == null) {
				//x is root
				root = y;
			}
			else if(x == x.parent.left) {
				x.parent.left = y;
			}
			else {
				x.parent.right = y;
			}
			y.left = x;
			x.parent = y;
		}
		return root;
	}
	
	/**
	 * return root item
	 * @param root
	 * @param x
	 */
	public BSTreeNode rightRotate(BSTreeNode root, BSTreeNode x) {
		if(root != null && x != null && x.left != null) {
			BSTreeNode y = x.left;
			y.parent = x.parent;
			x.left = y.right;
			if(y.right != null) {
				y.right.parent = x;
			}
			if(x.parent == null) {
				//x is root
				root = y;
			}
			else if(x == x.parent.left) {
				x.parent.left = y;
			}
			else {
				x.parent.right = y;
			}
			y.right = x;
			x.parent = y;
		}
		return root;
	}
	
	public static void main(String[] args) {
		BSTree bsTree = new BSTree();
		//BSTree.BSTreeNode bsTreeNode = bsTree.new BSTreeNode(new Integer("12"));
		BSTreeNode root = null;
		BSTreeNode bsTreeNode1 = new BSTreeNode(new Integer("5"));
		BSTreeNode bsTreeNode2 = new BSTreeNode(new Integer("2"));
		BSTreeNode bsTreeNode3 = new BSTreeNode(new Integer("9"));
		
		//root
		//bsTree.treeInsert(null, bsTreeNode);
		
		bsTree.treeInsert(root, bsTreeNode1);
		root = bsTreeNode1;
		bsTree.treeInsert(root, bsTreeNode2);
		bsTree.treeInsert(root, bsTreeNode3);
		
		//bsTree.treeDelete(bsTreeNode, bsTreeNode1);
		//bsTree.updateTreeDelete(bsTreeNode, bsTreeNode1);
		
		root = bsTree.leftRotate(root, bsTreeNode1);
		
		root = bsTree.rightRotate(root, bsTreeNode3);
		
	}
	
}
