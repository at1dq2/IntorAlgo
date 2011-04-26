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
}
