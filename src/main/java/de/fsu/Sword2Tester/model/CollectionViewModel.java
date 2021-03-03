package de.fsu.Sword2Tester.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;
import org.swordapp.client.SWORDCollection;

public class CollectionViewModel implements TreeNode {

    public CollectionViewModel(SWORDCollection collection, WorkspaceViewModel workspaceViewModel){
        this.collection = collection;
        this.workspaceViewModel = workspaceViewModel;
    }

    public SWORDCollection getCollection() {
        return collection;
    }

    public void setCollection(SWORDCollection collection) {
        this.collection = collection;
    }

    private SWORDCollection collection;
    private WorkspaceViewModel workspaceViewModel;

    public List<EntryViewModel> getEntryList() {
        return entryList;
    }

    private List<EntryViewModel> entryList = new ArrayList<>();

    @Override
    public TreeNode getChildAt(int childIndex) {
        return entryList.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return entryList.size();
    }

    @Override
    public TreeNode getParent() {
        return this.workspaceViewModel;
    }

    @Override
    public int getIndex(TreeNode node) {
        return entryList.indexOf(node);
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
        return Collections.enumeration(this.entryList);
    }

    @Override
    public String toString() {
        return this.collection.getTitle();
    }
}
