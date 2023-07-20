package net.evara.prison;

public class Test {

    public static void main(String[] args) {

    }

    public static double getCurrentChanceOfActivating(double baseChance, double maxChance, long maxLevel, long level) {
        return (baseChance + (level * (maxChance - baseChance) / maxLevel));
    }

    private static class TestObject implements Comparable<TestObject> {

        private final int id;
        private int blocksMined;

        public TestObject(int id, int blocksMined) {
            this.id = id;
            this.blocksMined = blocksMined;
        }

        public void addBlocksMined(int amount) {
            blocksMined += amount;
        }

        public void removeBlocksMined(int amount) {
            blocksMined -= amount;
        }

        @Override
        public int compareTo(TestObject o) {
            return Integer.compare(blocksMined, o.blocksMined);
        }

    }

}
