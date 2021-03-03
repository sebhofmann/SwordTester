package de.fsu.Sword2Tester.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

public class SwordViewModel implements TreeNode {

    public SwordViewModel(String sdURI) {
        this.sdURI = sdURI;
    }

    private final String sdURI;



    private final List<WorkspaceViewModel> workspaceViewModels = new ArrayList<>();

    public List<WorkspaceViewModel> getWorkspaceViewModels() {
        return workspaceViewModels;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return workspaceViewModels.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return workspaceViewModels.size();
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return workspaceViewModels.indexOf(node);
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
        return Collections.enumeration(this.workspaceViewModels);
    }

    @Override
    public String toString() {
        return this.sdURI;
    }
}
