package de.fsu.Sword2Tester.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.abdera.model.Entry;


public class EntryViewModel implements TreeNode {

    private org.apache.abdera.model.Entry entry;
    private TreeNode parent;
    private List<TreeNode> treeChildren = new ArrayList<>();

    public EntryViewModel(org.apache.abdera.model.Entry e, TreeNode parent) {
        this(e, parent, Collections.EMPTY_LIST);
    }

    public EntryViewModel(org.apache.abdera.model.Entry e, TreeNode parent, List<Entry> children) {
        this.entry = e;
        this.parent = parent;

        children.stream()
                .map(child -> new EntryViewModel(child, this))
                .forEach(this.treeChildren::add);
    }

    public Entry getEntry() {
        return entry;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return treeChildren.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return treeChildren.size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return treeChildren.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration children() {
        return Collections.enumeration(treeChildren);
    }

    @Override
    public String toString() {
        return this.entry.getId().toString();
    }
}
