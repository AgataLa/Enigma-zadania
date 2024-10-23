package org.enigma;

public class DeciduousTree extends Tree {
    private static final int LEAVES_INCREASE_PER_BRANCH = 10;
    private int leaves;
    private LeafColor leafColor;

    public DeciduousTree(final Trunk trunk, final int branches, final double branchesIncrease, final int leaves,
                         final LeafColor leafColor) {
        super(trunk, branches, branchesIncrease);
        this.leaves = leaves;
        this.leafColor = leafColor;
    }

    public void changeColor(final LeafColor leafColor) {
        this.leafColor = leafColor;
    }

    public void defoliate() {
        this.leaves = 0;
    }

    @Override
    protected void produceLeaves(final int newBranches) {
        this.leaves += newBranches * LEAVES_INCREASE_PER_BRANCH;
    }

    public int getLeaves() {
        return leaves;
    }

    public void setLeaves(final int leaves) {
        this.leaves = leaves;
    }

    public LeafColor getLeafColor() {
        return leafColor;
    }

    public void setLeafColor(final LeafColor leafColor) {
        this.leafColor = leafColor;
    }
}
