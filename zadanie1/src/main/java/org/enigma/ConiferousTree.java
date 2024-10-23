package org.enigma;

public class ConiferousTree extends Tree {
    private static final int NEEDLES_INCREASE_PER_BRANCH = 100;
    private int needles;

    public ConiferousTree(final Trunk trunk, final int branches, final double branchesIncrease, final int needles) {
        super(trunk, branches, branchesIncrease);
        this.needles = needles;
    }

    @Override
    protected void produceLeaves(final int newBranches) {
        this.needles += newBranches * NEEDLES_INCREASE_PER_BRANCH;
    }

    public int getNeedles() {
        return needles;
    }

    public void setNeedles(final int needles) {
        this.needles = needles;
    }
}
