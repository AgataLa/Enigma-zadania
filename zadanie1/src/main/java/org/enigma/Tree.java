package org.enigma;

public abstract class Tree {
    private Trunk trunk;
    private int branches;
    private double branchesIncreaseRate;

    public Tree(final Trunk trunk, final int branches, final double branchesIncreaseRate) {
        this.trunk = trunk;
        this.branches = branches;
        this.branchesIncreaseRate = branchesIncreaseRate;
    }

    protected abstract void produceLeaves(final int newBranches);

    public void grow() {
        this.trunk.grow();
        final int newBranches = (int)(branches * branchesIncreaseRate);
        this.branches += newBranches;
        this.produceLeaves(newBranches);
    }

    public double getHeight() {
        return this.trunk.getHeight();
    }

    public int getBranches() {
        return branches;
    }

    public void setBranches(final int branches) {
        this.branches = branches;
    }

    public Trunk getTrunk() {
        return trunk;
    }

    public void setTrunk(final Trunk trunk) {
        this.trunk = trunk;
    }

    public double getBranchesIncreaseRate() {
        return branchesIncreaseRate;
    }

    public void setBranchesIncreaseRate(final double branchesIncreaseRate) {
        this.branchesIncreaseRate = branchesIncreaseRate;
    }
}
