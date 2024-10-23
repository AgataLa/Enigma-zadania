package org.enigma;

public class Main {
    public static void main(String[] args) {
        final Trunk oakTrunk = new Trunk(0.5, 20, 1, 0.05, TrunkColor.LIGHT_BROWN);
        final DeciduousTree oak = new DeciduousTree(oakTrunk, 40, 5, 400, LeafColor.GREEN);

        final Trunk pineTrunk = new Trunk(0.2, 30, 2, 0.05, TrunkColor.DARK_BROWN);
        final ConiferousTree pine = new ConiferousTree(pineTrunk, 30, 5, 5000);

        oak.grow();
        pine.grow();

        System.out.println(oak.getLeaves());
        System.out.println(pine.getNeedles());

        oak.defoliate();

        System.out.println(oak.getLeaves());
    }
}