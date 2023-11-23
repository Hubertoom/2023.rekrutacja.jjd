import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

interface Structure {
    // zwraca dowolny element o podanym kolorze
    Optional<Block> findBlockByColor(String color);

    // zwraca wszystkie elementy z danego materiału
    List<Block> findBlocksByMaterial(String material);

    //zwraca liczbę wszystkich elementów tworzących strukturę
    int count();
}

public class Wall implements Structure {
    private List<Block> blocks;


    @Override
    public Optional<Block> findBlockByColor(String color) {
        return blocks.stream().filter(block -> block.getColor().equals(color))
                .findAny();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return getAllBlocks().stream()
                .filter(block -> block.getMaterial().equals(material))
                .toList();
    }

    @Override
    public int count() {
        return getAllBlocks().size();
    }

    private List<Block> getAllBlocks() {
        List<Block> allBlocks = new ArrayList<>();
        blocks.forEach(block -> collectAllBlocks(allBlocks, block));
        return allBlocks;
    }

    private void collectAllBlocks(List<Block> allBlocks, Block block) {
        if (Objects.isNull(block)) {
            return;
        }
        if (block instanceof CompositeBlock) {
            allBlocks.add(block);
            ((CompositeBlock)block).getBlocks().forEach(b -> collectAllBlocks(allBlocks, b));
        } else {
            allBlocks.add(block);
        }
    }
}

interface Block {
    String getColor();
    String getMaterial();
}

interface CompositeBlock extends Block {
    List<Block> getBlocks();
}