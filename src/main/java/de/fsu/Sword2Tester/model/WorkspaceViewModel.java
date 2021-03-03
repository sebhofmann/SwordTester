package de.fsu.Sword2Tester.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;
import org.swordapp.client.SWORDWorkspace;

public class WorkspaceViewModel implements TreeNode {

    public WorkspaceViewModel(SWORDWorkspace workspace, SwordViewModel parent){
        this.parent = parent;
        this.workspace = workspace;
    }

    private final SWORDWorkspace workspace;
    private final SwordViewModel parent;
    private final List<CollectionViewModel> collectionEntries = new ArrayList<>();

    public List<CollectionViewModel> getCollectionEntries() {
        return collectionEntries;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return this.getCollectionEntries().get(childIndex);
    }

    @Override
    public int getChildCount() {
        return this.getCollectionEntries().size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return this.getCollectionEntries().indexOf(node);
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
        return Collections.enumeration(this.getCollectionEntries());
    }

    @Override
    public String toString() {
        return this.workspace.getTitle();
    }
}
