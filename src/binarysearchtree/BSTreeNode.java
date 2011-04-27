package binarysearchtree;

import binarysearchtree.BSTree.Color;


public class BSTreeNode {
	public BSTreeNode(Integer key) {
		this.key = key;
	}
	public BSTreeNode parent;
	public BSTreeNode left;
	public BSTreeNode right;
	//public Comparable<Integer> key;
	public Integer key;
	public Color color;
	
	public String toString() {
		String returnStr = "key:";
		if(key != null) {
			returnStr = returnStr + key.toString() + " color:";
		}
		if(color != null) {
			returnStr = returnStr + color.toString();
		}
		return returnStr; 
	}
}
