package planning;

import blocksworld.BlocksWorld;

public abstract class BlocksWorldHeuristic implements Heuristic{
    
    protected BlocksWorld blocksWorld;

    public BlocksWorldHeuristic(BlocksWorld blocksWorld) {
        this.blocksWorld = blocksWorld;
    }

    public BlocksWorld getBlocksWorld() {
        return blocksWorld;
    }
}
